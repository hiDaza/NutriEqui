package com.mycompany.service;

import com.mycompany.domain.*;
import com.mycompany.repository.ConsumoRepository;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoService {

    private static final double ED_MANUTENCAO = 0.033; 
    private final ConsumoRepository consumoRepository;

    public AvaliacaoService() {
        this.consumoRepository = new ConsumoRepository();
    }

    public DiagnosticoNutricional avaliarEquino(Equino equino) {
        double edExigida = calcularExigencia(equino);
        List<Consumo> consumos = consumoRepository.buscarPorEquino(equino);
        double edFornecida = calcularFornecimento(consumos);

        
        double custoDiario = 0.0;
        for (Consumo c : consumos) {
            Double preco = c.getAlimento().getPrecoPorKg();
            if (preco != null && preco > 0) {
                custoDiario += c.getQuantidadeKgPorDia() * preco;
            }
        }
        double custoMensal = custoDiario * 30;

        
        List<String> alertas = gerarAlertasSeguranca(equino, consumos);

        double saldo = edFornecida - edExigida;
        double tolerancia = 0.5;

        String classificacao;
        String recomendacao;

        if (saldo < -tolerancia) {
            classificacao = "DÉFICIT ENERGÉTICO";
            recomendacao = gerarSugestaoAumento(consumos, saldo * -1);
        } else if (saldo > tolerancia) {
            classificacao = "EXCESSO ENERGÉTICO";
            recomendacao = gerarSugestaoReducao(consumos);
        } else {
            classificacao = "ADEQUADO. Dentro da tolerância";
            recomendacao = "A dieta está equilibrada em energia.";
        }

        String alertaAdicional = getAlertaNutricional(equino);
        if (!alertaAdicional.isEmpty()) {
            recomendacao = recomendacao + "\n" + alertaAdicional;
        }

        return new DiagnosticoNutricional(equino, edExigida, edFornecida, saldo, 
                                     classificacao, recomendacao, custoDiario, custoMensal, alertas);
    }

    private double calcularExigencia(Equino equino) {
        double peso = equino.getPeso();
        double base = peso * ED_MANUTENCAO;

        return switch (equino.getCategoria()) {
            case MANTENCAO -> base;
            case ATLETA_LEVE -> base * 1.4;
            case ATLETA_MODERADO -> base * 1.6;
            case ATLETA_INTENSO -> base * 1.8;
            case GESTANTE_INICIO -> base * 1.0;
            case GESTANTE_FINAL -> base * 1.2;
            case LACTANTE -> base * 1.8;
            case POTRO_DESMAME -> base * 2.0;
            case POTRO_ATE_1_ANO -> base * 1.8;
            case POTRO_ATE_2_ANOS -> base * 1.4;
            case GARANHAO_MONTA -> base * 1.4;
            default -> base;
        };
    }

    private double calcularFornecimento(List<Consumo> consumos) {
        double total = 0.0;
        for (Consumo c : consumos) {
            if (!c.isIncluiNoCalculoEnergetico()) {
                continue;
            }
            total += c.getAlimento().getEnergiaDigestivel() * c.getQuantidadeKgPorDia();
        }
        return total;
    }

    private String gerarSugestaoAumento(List<Consumo> consumos, double deficit) {
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.VOLUMOSO) {
                double kg = deficit / c.getAlimento().getEnergiaDigestivel();
                return String.format("Adicionar +%.2f kg/dia de %s (volumoso)", kg, c.getAlimento().getNome());
            }
        }
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.RACAO) {
                double kg = deficit / c.getAlimento().getEnergiaDigestivel();
                return String.format("Adicionar +%.2f kg/dia de %s (ração)", kg, c.getAlimento().getNome());
            }
        }
        return "Cadastre um alimento (volumoso ou ração) para receber sugestões.";
    }

    private String gerarSugestaoReducao(List<Consumo> consumos) {
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.RACAO) {
                double excesso = c.getQuantidadeKgPorDia() * 0.1;
                return String.format("Reduza %.2f kg/dia de %s (ração) – ou reavalie a dieta.", excesso, c.getAlimento().getNome());
            }
        }
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.VOLUMOSO) {
                double excesso = c.getQuantidadeKgPorDia() * 0.05;
                return String.format("Reduza %.2f kg/dia de %s (volumoso) – ou reavalie a dieta.", excesso, c.getAlimento().getNome());
            }
        }
        return "Revise a dieta para reduzir a energia fornecida.";
    }

    private String getAlertaNutricional(Equino equino) {
        return switch (equino.getCategoria()) {
            case GESTANTE_INICIO, GESTANTE_FINAL, LACTANTE,
                 POTRO_DESMAME, POTRO_ATE_1_ANO, POTRO_ATE_2_ANOS ->
                "A análise energética isolada não é suficiente. Avalie também proteína, lisina, cálcio, fósforo, cobre, zinco, selênio e vitamina E.";
            default -> "";
        };
    }

    private List<String> gerarAlertasSeguranca(Equino equino, List<Consumo> consumos) {
    List<String> alertas = new ArrayList<>();

    double peso = equino.getPeso();
    double minimoVolumoso = peso * 0.015;
    double totalVolumoso = 0.0;
    double maiorConcentradoRefeicao = 0.0;
    int contadorSuplementos = 0;
    int numRefeicoes = Math.max(equino.getNumeroRefeicoesPorDia() > 0 ? equino.getNumeroRefeicoesPorDia() : 2, 1);

    for (Consumo c : consumos) {
        Alimento a = c.getAlimento();

        if (a.getEnergiaDigestivel() <= 0 && c.isIncluiNoCalculoEnergetico()) {
            alertas.add(String.format("⚠️ Composição incompleta: O alimento '%s' está sem o valor de Energia Digestível (ED) cadastrado.", a.getNome()));
        }

        if (a.getTipo() == TipoAlimento.VOLUMOSO) {
            totalVolumoso += c.getQuantidadeKgPorDia();
        } else if (a.getTipo() == TipoAlimento.RACAO) {
            double porRefeicao = c.getQuantidadeKgPorDia() / numRefeicoes;
            if (porRefeicao > maiorConcentradoRefeicao) {
                maiorConcentradoRefeicao = porRefeicao;
            }
        } else if (a.getTipo() == TipoAlimento.SUPLEMENTO) {
            contadorSuplementos++;
        }
    }

    
    if (totalVolumoso < minimoVolumoso) {
        alertas.add(String.format("Baixo fornecimento de volumoso! Ofertado: %.2f kg | Mínimo recomendado: %.2f kg (1,5%% do PV). Risco de distúrbios digestivos e cólica.", totalVolumoso, minimoVolumoso));
    }

    
    double limiteConcentrado = (peso / 100.0) * 0.5;
    if (maiorConcentradoRefeicao > limiteConcentrado) {
        alertas.add(String.format("Excesso de concentrado por refeição! Maior trato: %.2f kg | Limite seguro: %.2f kg. Divida a ração em mais refeições diárias.", maiorConcentradoRefeicao, limiteConcentrado));
    }


    if (contadorSuplementos > 1) {
        alertas.add("Possível sobreposição de suplementos! Mais de um suplemento cadastrado na dieta. Verifique o risco de duplicidade de minerais e vitaminas.");
    }


    CategoriaFisiologica cat = equino.getCategoria();
    if (cat == CategoriaFisiologica.GESTANTE_INICIO ||
        cat == CategoriaFisiologica.GESTANTE_FINAL ||
        cat == CategoriaFisiologica.LACTANTE ||
        cat == CategoriaFisiologica.POTRO_DESMAME ||
        cat == CategoriaFisiologica.POTRO_ATE_1_ANO ||
        cat == CategoriaFisiologica.POTRO_ATE_2_ANOS) {
        alertas.add("Categoria especial: A análise energética isolada é insuficiente. Avalie também Proteína Bruta, Lysina, Cálcio, Fósforo e Microminerais.");
    }

    return alertas;
}
}
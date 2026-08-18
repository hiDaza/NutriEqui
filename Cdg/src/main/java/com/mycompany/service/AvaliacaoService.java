/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

/**
 *
 * @author daza
 */

import com.mycompany.domain.*;
import com.mycompany.repository.AvaliacaoHistoricoRepository;
import com.mycompany.repository.ConsumoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoService {

    private static final double ED_MANUTENCAO = 0.033; // Mcal/kg PV
    private final ConsumoRepository consumoRepository;
    private final AvaliacaoHistoricoRepository avaliacaoHistoricoRepository;
    
    public AvaliacaoService() {
        this.consumoRepository = new ConsumoRepository();
        this.avaliacaoHistoricoRepository = new AvaliacaoHistoricoRepository();
    }
    
    
    //para injecao
    public AvaliacaoService(ConsumoRepository consumoRepository,
                            AvaliacaoHistoricoRepository avaliacaoHistoricoRepository) {
        this.consumoRepository = consumoRepository;
        this.avaliacaoHistoricoRepository = avaliacaoHistoricoRepository;
    }
    
    

    private double calcularExigencia(Equino equino) {
        double peso = equino.getPeso();
        double base = peso * ED_MANUTENCAO;

        return switch (equino.getCategoria()) {
            // adultos
            case MANTENCAO -> base;

            // atletas com níveis
            case ATLETA_LEVE -> base * 1.4;
            case ATLETA_MODERADO -> base * 1.6;
            case ATLETA_INTENSO -> base * 1.8;

            // gestacao
            case GESTANTE_INICIO -> base * 1.0;
            case GESTANTE_FINAL -> base * 1.2; // terceiro terço final da gestação

            // lactacao
            case LACTANTE -> base * 1.8; //mês inicial 

            // Potros 
            case POTRO_DESMAME -> base * 2.0; // 4 a 6 meses
            case POTRO_ATE_1_ANO -> base * 1.8;
            case POTRO_ATE_2_ANOS -> base * 1.4; // 1 e meio 2 anos

            // garanhões
            case GARANHAO_MONTA -> base * 1.4; //
                
            // idoso 
            case IDOSO -> base; //conforme a condição corporal

            // se nao mapeado retorna com aviso
            default -> base;
        };
    }


    private String getAlertaNutricional(Equino equino) {
        return switch (equino.getCategoria()) {
            case GESTANTE_INICIO, GESTANTE_FINAL, LACTANTE,
                 POTRO_DESMAME, POTRO_ATE_1_ANO, POTRO_ATE_2_ANOS ->
                "A análise energética isolada não é suficiente. Avalie também proteína, lisina, cálcio, fósforo, cobre, zinco, selênio e vitamina E.";
            
            case IDOSO -> "Recomendada avaliação dentária e clínica para equino idoso";

            default -> "";
        };
    }

    public DiagnosticoNutricional avaliarEquino(Equino equino) {
        double edExigida = calcularExigencia(equino);
        List<Consumo> consumos = consumoRepository.buscarPorEquino(equino);
        double edFornecida = calcularFornecimento(consumos);
        
        
        // Cálculo de custo
        double custoDiario = 0.0;
        
        double saldo = edFornecida - edExigida;
        
        for (Consumo c : consumos) {
            Double preco = c.getAlimento().getPrecoPorKg();
            if (preco != null && preco > 0) {
                custoDiario += c.getQuantidadeKgPorDia() * preco;
            }
        }
        double custoMensal = custoDiario * 30;

        // Geração de alertas de segurança 
        List<String> alertas = gerarAlertasSeguranca(equino, consumos,saldo);
        


        // Tolerância de ±0,5 Mcal
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

        String alerta = getAlertaNutricional(equino);
        if (!alerta.isEmpty()) {
            recomendacao = recomendacao + "\n" + alerta;
        }

        DiagnosticoNutricional diagnostico = new DiagnosticoNutricional(
                equino, edExigida, edFornecida, saldo, classificacao, recomendacao, custoDiario, custoMensal, alertas
        );

        String dietaConsumida = montarResumoDieta(consumos);
        AvaliacaoHistorico registroHistorico = new AvaliacaoHistorico(
                equino,
                LocalDateTime.now(),
                equino.getPeso(),
                equino.getScoreCorporal(),
                equino.getCategoria(),
                dietaConsumida,
                saldo,
                recomendacao
        );
        avaliacaoHistoricoRepository.salvar(registroHistorico);

        return diagnostico;
    }

    private double calcularFornecimento(List<Consumo> consumos) {
        double total = 0.0;
        for (Consumo c : consumos) {
            total += c.getAlimento().getEnergiaDigestivel() * c.getQuantidadeKgPorDia();
        }
        return total;
    }

    private String gerarSugestaoAumento(List<Consumo> consumos, double deficit) {
        // Prioriza volumoso
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.VOLUMOSO) {
                double kg = deficit / c.getAlimento().getEnergiaDigestivel();
                return String.format("Adicionar +%.2f kg/dia de %s (volumoso)", kg, c.getAlimento().getNome());
            }
        }
        // sugere raçao caso nao esteva volumoso
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.RACAO) {
                double kg = deficit / c.getAlimento().getEnergiaDigestivel();
                return String.format("Adicionar +%.2f kg/dia de %s (ração)", kg, c.getAlimento().getNome());
            }
        }
        return "Cadastre um alimento (volumoso ou ração) para receber sugestões.";
    }

    private String gerarSugestaoReducao(List<Consumo> consumos) {
        // prioriza a redução da ração
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.RACAO) {
                double excesso = c.getQuantidadeKgPorDia() * 0.1; // reduz 10%
                return String.format("Reduza %.2f kg/dia de %s (ração) – ou reavalie a dieta.", excesso, c.getAlimento().getNome());
            }
        }
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.VOLUMOSO) {
                double excesso = c.getQuantidadeKgPorDia() * 0.05; // reduz 5%
                return String.format("Reduza %.2f kg/dia de %s (volumoso) – ou reavalie a dieta.", excesso, c.getAlimento().getNome());
            }
        }
        return "Revise a dieta para reduzir a energia fornecida.";
    }

    private String montarResumoDieta(List<Consumo> consumos) {
        if (consumos == null || consumos.isEmpty()) {
            return "Nenhum consumo registrado para este equino.";
        }

        StringBuilder sb = new StringBuilder();
        for (Consumo consumo : consumos) {
            sb.append(consumo.getAlimento().getNome())
              .append(": ")
              .append(consumo.getQuantidadeKgPorDia())
              .append(" kg/dia; ");
        }
        return sb.toString().trim();
    }
    

    private List<String> gerarAlertasSeguranca(Equino equino, List<Consumo> consumos, double saldo) {
        List<String> alertas = new ArrayList<>();

        double peso = equino.getPeso();
        double minimoVolumoso = peso * 0.015;
        double totalVolumoso = 0.0;
        double maiorConcentradoRefeicao = 0.0;
        int contadorSuplementos = 0;
        int numRefeicoes = Math.max(equino.getNumeroRefeicoesPorDia() > 0 ? equino.getNumeroRefeicoesPorDia() : 2, 1);

        for (Consumo c : consumos) {
            Alimento a = c.getAlimento();

            // Alerta: ED faltando
            if (a.getEnergiaDigestivel() <= 0 && c.isIncluiNoCalculoEnergetico()) {
                alertas.add(String.format("Composição incompleta: O alimento '%s' está sem o valor de Energia Digestível (ED) cadastrado.", a.getNome()));
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

        //Baixo volumoso
        if (totalVolumoso < minimoVolumoso) {
            alertas.add(String.format("Baixo fornecimento de volumoso! Ofertado: %.2f kg | Mínimo recomendado: %.2f kg (1,5%% do PV). Risco de distúrbios digestivos e cólica.", totalVolumoso, minimoVolumoso));
        }

        //Excesso de concentrado por refeição
        double limiteConcentrado = (peso / 100.0) * 0.5;
        if (maiorConcentradoRefeicao > limiteConcentrado) {
            alertas.add(String.format("Excesso de concentrado por refeição! Maior trato: %.2f kg | Limite seguro: %.2f kg. Divida a ração em mais refeições diárias.", maiorConcentradoRefeicao, limiteConcentrado));
        }

        // 3. Sobreposição de suplementos
        if (contadorSuplementos > 1) {
            alertas.add("Possível sobreposição de suplementos! Mais de um suplemento cadastrado na dieta. Verifique o risco de duplicidade de minerais e vitaminas.");
        }

        //categorias especiais (gestante, lactante, potro)
        CategoriaFisiologica cat = equino.getCategoria();
        if (cat == CategoriaFisiologica.GESTANTE_INICIO ||
            cat == CategoriaFisiologica.GESTANTE_FINAL ||
            cat == CategoriaFisiologica.LACTANTE ||
            cat == CategoriaFisiologica.POTRO_DESMAME ||
            cat == CategoriaFisiologica.POTRO_ATE_1_ANO ||
            cat == CategoriaFisiologica.POTRO_ATE_2_ANOS) {
            alertas.add("Categoria especial: A análise energética isolada é insuficiente. Avalie também Proteína Bruta, Lisina, Cálcio, Fósforo e Microminerais.");
        }

        //Idoso com perda de peso
        if (cat == CategoriaFisiologica.IDOSO) {
            alertas.add("Recomendada avaliação dentária e clínica para equino idoso.");
        }

        //Excesso energético + ECC alto (>= 7)
        if (saldo > 0.5 && equino.getScoreCorporal() >= 7) {
            alertas.add("Excesso energético combinado com ECC elevado. Evitar o aumento de ração; reavaliar o manejo.");
        }

        return alertas;
    }
}
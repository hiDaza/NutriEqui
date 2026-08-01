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
import com.mycompany.repository.ConsumoRepository;
import java.util.List;

public class AvaliacaoService {

    private static final double ED_MANUTENCAO = 0.033; // Mcal/kg PV
    private final ConsumoRepository consumoRepository;

    public AvaliacaoService() {
        this.consumoRepository = new ConsumoRepository();
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

            // se nao mapeado retorna com aviso
            default -> base;
        };
    }


    private String getAlertaNutricional(Equino equino) {
        return switch (equino.getCategoria()) {
            case GESTANTE_INICIO, GESTANTE_FINAL, LACTANTE,
                 POTRO_DESMAME, POTRO_ATE_1_ANO, POTRO_ATE_2_ANOS ->
                "A análise energética isolada não é suficiente. Avalie também proteína, lisina, cálcio, fósforo, cobre, zinco, selênio e vitamina E.";

            default -> "";
        };
    }

    public DiagnosticoNutricional avaliarEquino(Equino equino) {
        double edExigida = calcularExigencia(equino);
        List<Consumo> consumos = consumoRepository.buscarPorEquino(equino);
        double edFornecida = calcularFornecimento(consumos);
        double saldo = edFornecida - edExigida;

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

        return new DiagnosticoNutricional(equino, edExigida, edFornecida, saldo, classificacao, recomendacao);
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
}
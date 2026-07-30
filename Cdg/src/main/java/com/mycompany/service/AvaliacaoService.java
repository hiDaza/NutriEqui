/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

/**
 *
 * @author daza
 */


import static com.mycompany.domain.CategoriaFisiologica.ATLETA_INTENSO;
import static com.mycompany.domain.CategoriaFisiologica.ATLETA_LEVE;
import static com.mycompany.domain.CategoriaFisiologica.ATLETA_MODERADO;
import static com.mycompany.domain.CategoriaFisiologica.GESTANTE_FINAL;
import static com.mycompany.domain.CategoriaFisiologica.LACTANTE;
import static com.mycompany.domain.CategoriaFisiologica.MANTENCAO;
import com.mycompany.domain.Consumo;
import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.repository.ConsumoRepository;
import java.util.List;

public class AvaliacaoService {

    private ConsumoRepository consumoRepository;

    public AvaliacaoService() {
        this.consumoRepository = new ConsumoRepository();
    }

    public DiagnosticoNutricional avaliarEquino(Equino equino) {
        double edExigida = calcularExigencia(equino);
        List<Consumo> consumos = consumoRepository.buscarPorEquino(equino);
        double edFornecida = calcularFornecimento(consumos);
        double saldo = edFornecida - edExigida;

        String classificacao;
        String recomendacao;

        if (saldo < -0.5) {
            classificacao = "DÉFICIT ENERGÉTICO";
            recomendacao = gerarSugestaoAumento(consumos, saldo * -1);
        } else if (saldo > 0.5) {
            classificacao = "EXCESSO ENERGÉTICO";
            recomendacao = "Reduza a quantidade de ração ou volumoso na dieta.";
        } else {
            classificacao = "ADEQUADO";
            recomendacao = "A dieta está equilibrada em energia.";
        }

        return new DiagnosticoNutricional(equino, edExigida, edFornecida, saldo, classificacao, recomendacao);
    }

    private double calcularExigencia(Equino equino) {
        double peso = equino.getPeso();
        double base = peso * 0.033; // Mcal/dia para manutenção

        return switch (equino.getCategoria()) {
            case MANTENCAO -> base;
            case ATLETA_LEVE -> base * 1.4;
            case ATLETA_MODERADO -> base * 1.6;
            case ATLETA_INTENSO -> base * 1.8;
            case GESTANTE_FINAL -> base * 1.2;
            case LACTANTE -> base * 1.8;
            default -> base; // Para outras categorias, usa manutenção + aviso (pode ser melhorado depois)
        };
    }

    private double calcularFornecimento(List<Consumo> consumos) {
        double total = 0.0;
        for (Consumo c : consumos) {
            total += c.getAlimento().getEnergiaDigestivel() * c.getQuantidadeKgPorDia();
        }
        return total;
    }

    private String gerarSugestaoAumento(List<Consumo> consumos, double deficit) {
        // Procura primeiro um volumoso
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.VOLUMOSO) {
                double kg = deficit / c.getAlimento().getEnergiaDigestivel();
                return String.format("Adicionar +%.2f kg/dia de %s", kg, c.getAlimento().getNome());
            }
        }
        // Se não achou volumoso, sugere ração
        for (Consumo c : consumos) {
            if (c.getAlimento().getTipo() == TipoAlimento.RACAO) {
                double kg = deficit / c.getAlimento().getEnergiaDigestivel();
                return String.format("Adicionar +%.2f kg/dia de %s", kg, c.getAlimento().getNome());
            }
        }
        return "Cadastre um alimento (volumoso ou ração) para receber sugestões.";
    }
}

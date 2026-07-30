/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

/**
 *
 * @author daza
 */

import com.mycompany.domain.Alimento;
import com.mycompany.domain.Consumo;
import com.mycompany.domain.Equino;
import com.mycompany.repository.AlimentoRepository;
import com.mycompany.repository.ConsumoRepository;
import com.mycompany.repository.EquinoRepository;

public class ConsumoService {

    private EquinoRepository equinoRepository;
    private AlimentoRepository alimentoRepository;
    private ConsumoRepository consumoRepository;

    public ConsumoService() {
        this.equinoRepository = new EquinoRepository();
        this.alimentoRepository = new AlimentoRepository();
        this.consumoRepository = new ConsumoRepository();
    }

    /**
     * Registra o consumo de um alimento para um equino.
     * 
     * @param nomeEquino    nome do equino
     * @param nomeAlimento  nome do alimento
     * @param quantidadeKg  quantidade em kg/dia (deve ser > 0)
     * @return mensagem de sucesso ou erro
     */
    public String registrarConsumo(String nomeEquino, String nomeAlimento, double quantidadeKg) {
        // 1. Validações
        if (quantidadeKg <= 0) {
            return "Erro: Quantidade inválida. Deve ser maior que zero.";
        }

        Equino equino = equinoRepository.buscarPorNome(nomeEquino);
        if (equino == null) {
            return "Erro: Equino não encontrado.";
        }

        Alimento alimento = alimentoRepository.buscarPorNome(nomeAlimento);
        if (alimento == null) {
            return "Erro: Alimento não encontrado.";
        }

        // 2. Cria e persiste o consumo
        Consumo consumo = new Consumo(equino, alimento, quantidadeKg);
        consumoRepository.salvar(consumo);
        return "Consumo registrado com sucesso!";
    }
}

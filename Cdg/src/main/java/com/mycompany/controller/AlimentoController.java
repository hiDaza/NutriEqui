/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */

import com.mycompany.domain.Alimento;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.repository.AlimentoRepository;

public class AlimentoController {

    private AlimentoRepository alimentoRepository;

    public AlimentoController() {
        this.alimentoRepository = new AlimentoRepository();
    }

    public String cadastrarAlimento(String nome, TipoAlimento tipo, double energiaDigestivel) {
        if (alimentoRepository.buscarPorNome(nome) != null) {
            return "Erro: Já existe um alimento com este nome.";
        }
        Alimento alimento = new Alimento(nome, tipo, energiaDigestivel);
        alimentoRepository.salvar(alimento);
        return "Alimento cadastrado com sucesso! ID: " + alimento.getId();
    }
}

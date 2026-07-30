/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */

import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.Equino;
import com.mycompany.repository.EquinoRepository;

public class EquinoController {

    private EquinoRepository equinoRepository;

    public EquinoController() {
        this.equinoRepository = new EquinoRepository();
    }

    public String cadastrarEquino(String nome, double peso, int escore, CategoriaFisiologica categoria) {
        if (equinoRepository.buscarPorNome(nome) != null) {
            return "Erro: Já existe um equino com este nome.";
        }
        Equino equino = new Equino(nome, peso, escore, categoria);
        equinoRepository.salvar(equino);
        return "Equino cadastrado com sucesso! ID: " + equino.getId();
    }

}

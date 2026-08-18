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
import com.mycompany.domain.Propriedade;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.PropriedadeRepository;

public class EquinoController {

    private final EquinoRepository equinoRepository;

    public EquinoController() {
        this(new EquinoRepository());
    }

    public EquinoController(EquinoRepository equinoRepository) {
        this.equinoRepository = equinoRepository;
    }

    public String cadastrarEquino(String nome, double peso, int score, CategoriaFisiologica categoria,String nomePropriedade) {
        if (nome == null) {
            return "Erro: Informe um nome válido para o equino.";
        }

        String nomeNormalizado = nome.trim();
        if (nomeNormalizado.isEmpty()) {
            return "Erro: Informe um nome válido para o equino.";
        }

        if (peso <= 0) {
            return "Erro: O peso deve ser maior que zero.";
        }

        if (score < 1 || score > 9) {
            return "Erro: O score corporal deve estar entre 1 e 9.";
        }

        if (categoria == null) {
            return "Erro: Selecione uma categoria fisiológica.";
        }

        if (equinoRepository.buscarPorNome(nomeNormalizado) != null) {
            return "Erro: Já existe um equino com este nome.";
        }
        
        Propriedade propriedade = null;
        if(nomePropriedade != null && !nomePropriedade.trim().isEmpty()){
            propriedade = new PropriedadeRepository().buscarPorNome(nomePropriedade.trim());
            if(propriedade == null){
                return "Err: Propriedade não encontrada. Cadastre a Propriedade primeiro.";
            }
        }

        Equino equino = new Equino(nomeNormalizado, peso, score, categoria);
        equino.setPropriedade(propriedade);
        try {
            equinoRepository.salvar(equino);
        } catch (Exception e) {
            return "Erro: Não foi possível salvar o equino. Verifique o banco de dados.";
        }

        return "Equino cadastrado com sucesso! ID: " + equino.getId();
    }

}

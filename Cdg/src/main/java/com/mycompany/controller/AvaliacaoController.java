/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */

import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.service.AvaliacaoService;

public class AvaliacaoController {

    private EquinoRepository equinoRepository;
    private AvaliacaoService avaliacaoService;

    public AvaliacaoController() {
        this.equinoRepository = new EquinoRepository();
        this.avaliacaoService = new AvaliacaoService();
    }

    public DiagnosticoNutricional avaliarEquino(String nomeEquino) {
        Equino equino = equinoRepository.buscarPorNome(nomeEquino);
        if (equino == null) {
            return null;
        }
        return avaliacaoService.avaliarEquino(equino);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */

import com.mycompany.domain.AvaliacaoHistorico;
import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.repository.AvaliacaoHistoricoRepository;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.service.AvaliacaoService;
import java.util.Collections;
import java.util.List;

public class AvaliacaoController {

    private EquinoRepository equinoRepository;
    private AvaliacaoService avaliacaoService;
    private AvaliacaoHistoricoRepository avaliacaoHistoricoRepository;

    public AvaliacaoController() {
        this.equinoRepository = new EquinoRepository();
        this.avaliacaoService = new AvaliacaoService();
        this.avaliacaoHistoricoRepository = new AvaliacaoHistoricoRepository();
    }

    public DiagnosticoNutricional avaliarEquino(String nomeEquino) {
        Equino equino = equinoRepository.buscarPorNome(nomeEquino);
        if (equino == null) {
            return null;
        }
        return avaliacaoService.avaliarEquino(equino);
    }

    public List<AvaliacaoHistorico> buscarHistoricoPorEquino(String nomeEquino) {
        Equino equino = equinoRepository.buscarPorNome(nomeEquino);
        if (equino == null) {
            return Collections.emptyList();
        }
        return avaliacaoHistoricoRepository.buscarPorEquino(equino);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */
import com.mycompany.domain.Propriedade;
import com.mycompany.service.PropriedadeService;
import java.util.List;

public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    public PropriedadeController() {
        this.propriedadeService = new PropriedadeService();
    }

    public String cadastrarPropriedade(String nome, String endereco, String telefone, String responsavel) {
        return propriedadeService.salvarPropriedade(nome, endereco, telefone, responsavel);
    }

    public List<Propriedade> listarPropriedades() {
        return propriedadeService.listarPropriedades();
    }

    public Propriedade buscarPropriedadePorNome(String nome) {
        return propriedadeService.buscarPorNome(nome);
    }
    
    public Propriedade buscarPropriedadeAtual() {
        return propriedadeService.buscarPropriedadeAtual();
    }
}
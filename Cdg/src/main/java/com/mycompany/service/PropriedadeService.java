/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

/**
 *
 * @author daza
 */
import com.mycompany.domain.Propriedade;
import com.mycompany.repository.PropriedadeRepository;
import java.util.List;

public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeService() {
        this.propriedadeRepository = new PropriedadeRepository();
    }

    public String salvarPropriedade(String nome, String endereco, String telefone, String responsavel) {
        Propriedade propriedade = new Propriedade(nome, endereco, telefone, responsavel);
        try {
            propriedadeRepository.salvar(propriedade);
            return "Propriedade cadastrada com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar propriedade: " + e.getMessage();
        }
    }

    public Propriedade buscarPorNome(String nome) {
        return propriedadeRepository.buscarPorNome(nome);
    }

    public List<Propriedade> listarPropriedades() {
        return propriedadeRepository.listarTodos();
    }

    public Propriedade buscarPropriedadeAtual() {
        return propriedadeRepository.buscarUltima();
    }
}

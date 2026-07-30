/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */
import com.mycompany.domain.*;
import com.mycompany.repository.*;
import com.mycompany.service.NutricaoService;

public class NutriEquiController {

    private EquinoRepository equinoRepository;
    private AlimentoRepository alimentoRepository;
    private ConsumoRepository consumoRepository;
    private NutricaoService nutricaoService;

    public NutriEquiController() {
        this.equinoRepository = new EquinoRepository();
        this.alimentoRepository = new AlimentoRepository();
        this.consumoRepository = new ConsumoRepository();
        this.nutricaoService = new NutricaoService(consumoRepository);
    }

    public String cadastrarEquino(String nome, double peso, int escore, CategoriaFisiologica categoria) {
        if (equinoRepository.buscarPorNome(nome) != null) {
            return "Erro: Já existe um equino com este nome.";
        }
        Equino equino = new Equino(nome, peso, escore, categoria);
        equinoRepository.salvar(equino);
        return "Equino cadastrado com sucesso! ID: " + equino.getId();
    }

    public String cadastrarAlimento(String nome, TipoAlimento tipo, double ed) {
        if (alimentoRepository.buscarPorNome(nome) != null) {
            return "Erro: Já existe um alimento com este nome.";
        }
        Alimento alimento = new Alimento(nome, tipo, ed);
        alimentoRepository.salvar(alimento);
        return "Alimento cadastrado com sucesso! ID: " + alimento.getId();
    }

    public String registrarConsumo(String nomeEquino, String nomeAlimento, double kg) {
        Equino equino = equinoRepository.buscarPorNome(nomeEquino);
        if (equino == null) return "Erro: Equino não encontrado.";
        Alimento alimento = alimentoRepository.buscarPorNome(nomeAlimento);
        if (alimento == null) return "Erro: Alimento não encontrado.";
        Consumo consumo = new Consumo(equino, alimento, kg);
        consumoRepository.salvar(consumo);
        return "Consumo registrado com sucesso!";
    }

    public DiagnosticoNutricional avaliarEquino(String nomeEquino) {
        Equino equino = equinoRepository.buscarPorNome(nomeEquino);
        if (equino == null) return null;
        return nutricaoService.avaliarEquino(equino);
    }
}
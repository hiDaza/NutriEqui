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
import com.mycompany.domain.TipoAlimento;
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

    public String registrarConsumo(String nomeEquino, String nomeAlimento, double quantidadeKg) {
        Equino equino = obterEquino(nomeEquino);
        if (equino == null) {
            return "Erro: Equino não encontrado.";
        }

        Alimento alimento = obterAlimento(nomeAlimento);
        if (alimento == null) {
            return "Erro: Alimento não encontrado.";
        }

        if (!verificarPeso(quantidadeKg)) {
            return "Erro: Quantidade inválida. Deve ser maior que zero.";
        }

        Consumo consumo = new Consumo(equino, alimento, quantidadeKg);
        consumoRepository.salvar(consumo);

        return "Consumo registrado com sucesso!";
    }

    public String registrarSuplemento(String nomeEquino, String nomeSuplemento, double doseDiaria, boolean incluiNoCalculoEnergetico) {
        Equino equino = obterEquino(nomeEquino);
        if (equino == null) {
            return "Erro: Equino não encontrado.";
        }

        Alimento suplemento = obterAlimentoPorTipo(nomeSuplemento, TipoAlimento.SUPLEMENTO);
        if (suplemento == null) {
            return "Erro: Suplemento não encontrado.";
        }

        if (!verificarPeso(doseDiaria)) {
            return "Erro: Dose diária inválida. Deve ser maior que zero.";
        }

        Consumo consumo = new Consumo(equino, suplemento, doseDiaria, incluiNoCalculoEnergetico);
        consumoRepository.salvar(consumo);

        return "Suplemento registrado com sucesso!";
    }


        //busca equino pelo nome
        private Equino obterEquino(String nomeEquino) {
            return equinoRepository.buscarPorNome(nomeEquino);
        }

        //busaa alimento pelo nome
        private Alimento obterAlimento(String nomeAlimento) {
            return alimentoRepository.buscarPorNome(nomeAlimento);
        }

        private Alimento obterAlimentoPorTipo(String nomeAlimento, TipoAlimento tipo) {
            Alimento alimento = alimentoRepository.buscarPorNome(nomeAlimento);
            if (alimento != null && alimento.getTipo() == tipo) {
                return alimento;
            }
            return null;
        }

        // verifica se quantidade é valida
        private boolean verificarPeso(double quantidadeKg) {
            return quantidadeKg > 0;
        }
}
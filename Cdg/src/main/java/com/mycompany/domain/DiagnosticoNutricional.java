/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.domain;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author daza
 */

public class DiagnosticoNutricional {
    private Equino equino;
    private double edExigida;
    private double edFornecida;
    private double saldo;
    private String classificacao;
    private String recomendacao;
    private double custoDiario;
    private double custoMensal;
    private List<String> alertas;

    public DiagnosticoNutricional(Equino equino, double edExigida, double edFornecida,
                                  double saldo, String classificacao, String recomendacao, double custoDiario, double custoMensal, List<String> alertas) {
        this.equino = equino;
        this.edExigida = edExigida;
        this.edFornecida = edFornecida;
        this.saldo = saldo;
        this.classificacao = classificacao;
        this.recomendacao = recomendacao;
        this.custoDiario = custoDiario;
        this.custoMensal = custoMensal;
        this.alertas = alertas != null ? alertas : Collections.emptyList();
    }

    // Getters
    public Equino getEquino() { return equino; }
    public double getEdExigida() { return edExigida; }
    public double getEdFornecida() { return edFornecida; }
    public double getSaldo() { return saldo; }
    public String getClassificacao() { return classificacao; }
    public String getRecomendacao() { return recomendacao; }
    public double getCustoDiario() { return custoDiario; }
    public double getCustoMensal() { return custoMensal; }
    public List<String> getAlertas() { return alertas; }
}   
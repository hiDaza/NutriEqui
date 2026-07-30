/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.domain;

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

    public DiagnosticoNutricional(Equino equino, double edExigida, double edFornecida,
                                  double saldo, String classificacao, String recomendacao) {
        this.equino = equino;
        this.edExigida = edExigida;
        this.edFornecida = edFornecida;
        this.saldo = saldo;
        this.classificacao = classificacao;
        this.recomendacao = recomendacao;
    }

    // Getters
    public Equino getEquino() { return equino; }
    public double getEdExigida() { return edExigida; }
    public double getEdFornecida() { return edFornecida; }
    public double getSaldo() { return saldo; }
    public String getClassificacao() { return classificacao; }
    public String getRecomendacao() { return recomendacao; }
}
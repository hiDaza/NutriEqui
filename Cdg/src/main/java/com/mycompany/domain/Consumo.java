/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.domain;

/**
 *
 * @author daza
 */


import jakarta.persistence.*;

@Entity
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equino_id")
    private Equino equino;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

    private double quantidadeKgPorDia;
    private boolean incluiNoCalculoEnergetico = true;

    public Consumo() {}

    public Consumo(Equino equino, Alimento alimento, double quantidadeKgPorDia) {
        this(equino, alimento, quantidadeKgPorDia, true);
    }

    public Consumo(Equino equino, Alimento alimento, double quantidadeKgPorDia, boolean incluiNoCalculoEnergetico) {
        this.equino = equino;
        this.alimento = alimento;
        this.quantidadeKgPorDia = quantidadeKgPorDia;
        this.incluiNoCalculoEnergetico = incluiNoCalculoEnergetico;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Equino getEquino() { return equino; }
    public void setEquino(Equino equino) { this.equino = equino; }
    public Alimento getAlimento() { return alimento; }
    public void setAlimento(Alimento alimento) { this.alimento = alimento; }
    public double getQuantidadeKgPorDia() { return quantidadeKgPorDia; }
    public void setQuantidadeKgPorDia(double quantidadeKgPorDia) { this.quantidadeKgPorDia = quantidadeKgPorDia; }
    public boolean isIncluiNoCalculoEnergetico() { return incluiNoCalculoEnergetico; }
    public void setIncluiNoCalculoEnergetico(boolean incluiNoCalculoEnergetico) { this.incluiNoCalculoEnergetico = incluiNoCalculoEnergetico; }
}
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
import java.util.ArrayList;
import java.util.List;

@Entity
public class Equino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double peso;
    private int scoreCorporal;
    private int numeroRefeicoesPorDia;
    @Enumerated(EnumType.STRING)
    private CategoriaFisiologica categoria;
    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    @OneToMany(mappedBy = "equino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consumo> consumos = new ArrayList<>();

    public Equino() {}

    public Equino(String nome, double peso, int scoreCorporal, CategoriaFisiologica categoria) {
        this.nome = nome;
        this.peso = peso;
        this.scoreCorporal = scoreCorporal;
        this.categoria = categoria;
        
    }
    
    public Equino(String nome, double peso, int scoreCorporal, 
              CategoriaFisiologica categoria, int numeroRefeicoesPorDia) {
    this.nome = nome;
    this.peso = peso;
    this.scoreCorporal = scoreCorporal;
    this.categoria = categoria;
    this.numeroRefeicoesPorDia = numeroRefeicoesPorDia > 0 ? numeroRefeicoesPorDia : 2;

    }
    
    public int getNumeroRefeicoesPorDia() {
        return numeroRefeicoesPorDia;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public int getScoreCorporal() { return scoreCorporal; }
    public void setScoreCorporal(int scoreCorporal) { this.scoreCorporal = scoreCorporal; }
    public CategoriaFisiologica getCategoria() { return categoria; }
    public void setCategoria(CategoriaFisiologica categoria) { this.categoria = categoria; }
    public List<Consumo> getConsumos() { return consumos; }
    public void setConsumos(List<Consumo> consumos) { this.consumos = consumos; }
    public Propriedade getPropriedade() { return propriedade; }
    public void setPropriedade(Propriedade propriedade) { this.propriedade = propriedade; }
    public void setNumeroRefeicoesPorDia(int numeroRefeicoesPorDia){ this.numeroRefeicoesPorDia = numeroRefeicoesPorDia; }
}
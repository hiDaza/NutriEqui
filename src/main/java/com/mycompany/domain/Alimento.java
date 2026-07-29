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
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoAlimento tipo;

    private double energiaDigestivel; // Mcal/kg

    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consumo> consumos = new ArrayList<>();

    public Alimento() {}

    public Alimento(String nome, TipoAlimento tipo, double energiaDigestivel) {
        this.nome = nome;
        this.tipo = tipo;
        this.energiaDigestivel = energiaDigestivel;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoAlimento getTipo() { return tipo; }
    public void setTipo(TipoAlimento tipo) { this.tipo = tipo; }
    public double getEnergiaDigestivel() { return energiaDigestivel; }
    public void setEnergiaDigestivel(double energiaDigestivel) { this.energiaDigestivel = energiaDigestivel; }
    public List<Consumo> getConsumos() { return consumos; }
    public void setConsumos(List<Consumo> consumos) { this.consumos = consumos; }
}
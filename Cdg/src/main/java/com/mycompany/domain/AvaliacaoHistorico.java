package com.mycompany.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class AvaliacaoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAvaliacao;
    private double pesoRegistrado;
    private int escoreCorporal;

    @Enumerated(EnumType.STRING)
    private CategoriaFisiologica categoriaNaEpoca;

    @Column(length = 2000)
    private String dietaConsumida;
    private double saldoEnergetico;
    @Column(length = 2000)
    private String recomendacao;

    @ManyToOne
    @JoinColumn(name = "equino_id")
    private Equino equino;

    public AvaliacaoHistorico() {
    }

    public AvaliacaoHistorico(Equino equino, LocalDateTime dataAvaliacao, double pesoRegistrado,
                              int escoreCorporal, CategoriaFisiologica categoriaNaEpoca,
                              String dietaConsumida, double saldoEnergetico, String recomendacao) {
        this.equino = equino;
        this.dataAvaliacao = dataAvaliacao;
        this.pesoRegistrado = pesoRegistrado;
        this.escoreCorporal = escoreCorporal;
        this.categoriaNaEpoca = categoriaNaEpoca;
        this.dietaConsumida = dietaConsumida;
        this.saldoEnergetico = saldoEnergetico;
        this.recomendacao = recomendacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public double getPesoRegistrado() {
        return pesoRegistrado;
    }

    public void setPesoRegistrado(double pesoRegistrado) {
        this.pesoRegistrado = pesoRegistrado;
    }

    public int getEscoreCorporal() {
        return escoreCorporal;
    }

    public void setEscoreCorporal(int escoreCorporal) {
        this.escoreCorporal = escoreCorporal;
    }

    public CategoriaFisiologica getCategoriaNaEpoca() {
        return categoriaNaEpoca;
    }

    public void setCategoriaNaEpoca(CategoriaFisiologica categoriaNaEpoca) {
        this.categoriaNaEpoca = categoriaNaEpoca;
    }

    public String getDietaConsumida() {
        return dietaConsumida;
    }

    public void setDietaConsumida(String dietaConsumida) {
        this.dietaConsumida = dietaConsumida;
    }

    public double getSaldoEnergetico() {
        return saldoEnergetico;
    }

    public void setSaldoEnergetico(double saldoEnergetico) {
        this.saldoEnergetico = saldoEnergetico;
    }

    public String getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }

    public Equino getEquino() {
        return equino;
    }

    public void setEquino(Equino equino) {
        this.equino = equino;
    }
}

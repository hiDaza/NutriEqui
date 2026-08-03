package com.mycompany.domain;

public enum CalculoEnergetico {
    SIM("Sim"),
    NAO("Não"),
    PARCIALMENTE("Parcialmente");

    private final String descricao;

    CalculoEnergetico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

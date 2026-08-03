package com.mycompany.domain;

public enum UnidadeSuplemento {
    POR_KG("Por kg"),
    POR_DOSE("Por dose"),
    POR_LITRO("Por litro");

    private final String descricao;

    UnidadeSuplemento(String descricao) {
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

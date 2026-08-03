package com.mycompany.domain;

public enum CategoriaVolumoso {
    A("A"),
    B("B"),
    C("C");

    private final String descricao;

    CategoriaVolumoso(String descricao) {
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

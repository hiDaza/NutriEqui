package com.mycompany.domain;

public enum CategoriaRacao {
    ATLETA("Atleta"),
    MANUTENCAO("Manutenção"),
    REPRODUCAO("Reprodução"),
    CRESCIMENTO("Crescimento"),
    SENIOR("Sênior");

    private final String descricao;

    CategoriaRacao(String descricao) {
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

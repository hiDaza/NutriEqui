package com.mycompany.domain;

public enum TipoVolumoso {
    TIFTON("Tifton"),
    COAST_CROSS("Coast-cross"),
    ALFAFA("Alfafa"),
    OUTRO("Outro");

    private final String descricao;

    TipoVolumoso(String descricao) {
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

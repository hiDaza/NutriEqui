package com.mycompany.domain;

public class AlertaSeguranca {

    public enum Nivel {
        INFO,
        ALERTA,
        CRITICO
    }

    private String titulo;
    private String mensagem;
    private Nivel nivel;

    public AlertaSeguranca(String titulo, String mensagem, Nivel nivel) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.nivel = nivel;
    }

    public String getTitulo() { 
        return titulo; 
    }

    public String getMensagem() { 
        return mensagem; 
    }

    public Nivel getNivel() { 
        return nivel; 
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", nivel, titulo, mensagem);
    }
}
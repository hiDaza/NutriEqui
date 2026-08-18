package com.mycompany.controller;

import com.mycompany.domain.RelatorioVisita;
import com.mycompany.service.RelatorioService;

public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController() {
        this.relatorioService = new RelatorioService();
    }

    public RelatorioVisita gerarRelatorioGeral() {
        return relatorioService.gerarRelatorioLoteCompleto();
    }

    public RelatorioVisita gerarRelatorioPorPropriedade(String nomePropriedade) {
        return relatorioService.gerarRelatorioPorPropriedade(nomePropriedade);
    }
}
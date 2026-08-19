/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */
import com.mycompany.domain.dto.AlimentoDTO;
import com.mycompany.service.LeituraRotuloService;
import com.mycompany.service.LeituraRotuloService.ResultadoLeituraRotulo;

import java.io.File;

public class LeituraRotuloController {

    private final LeituraRotuloService leituraRotuloService;

    public LeituraRotuloController() {
        this.leituraRotuloService = new LeituraRotuloService();
    }

    public LeituraRotuloService.ResultadoLeituraRotulo processarArquivo(File file, String nomeOriginal) throws Exception {
        return leituraRotuloService.processarArquivo(file, nomeOriginal);
    }
    
    public AlimentoDTO processarArquivoParaAlimento(File file, String nomeOriginal) throws Exception {
        ResultadoLeituraRotulo resultado = leituraRotuloService.processarArquivo(file, nomeOriginal);
        return leituraRotuloService.parseTextoParaAlimento(resultado.getTextoExtraido());
    }
}

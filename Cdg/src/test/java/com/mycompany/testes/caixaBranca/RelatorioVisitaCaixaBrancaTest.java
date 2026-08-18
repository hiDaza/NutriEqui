/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaBranca;

/**
 *
 * @author daza
 */
import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.RelatorioVisita;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RelatorioVisitaCaixaBrancaTest {

    @Test
    void testGerarTextoFormatado_RamoTotalEquinosZero() {
        RelatorioVisita relatorioVazio = new RelatorioVisita(
            LocalDate.now(), 0, 0, 0, 0, 0.0, 0.0, Collections.emptyList(), "Haras Vazio"
        );

        String texto = relatorioVazio.gerarTextoFormatado();

        assertFalse(texto.contains("Custo Médio Diário por Animal"));
    }

    @Test
    void testGerarTextoFormatado_RamoSemAlertasEmitidos() {
        Equino eq = new Equino("Trova", 400.0, 5, CategoriaFisiologica.MANTENCAO);
        DiagnosticoNutricional diagSemAlerta = new DiagnosticoNutricional(
            eq, 15.0, 15.0, 0.0, "Adequado", "Manter", 10.0, 300.0, Collections.emptyList()
        );

        RelatorioVisita relatorio = new RelatorioVisita(
            LocalDate.now(), 1, 1, 0, 0, 10.0, 300.0, List.of(diagSemAlerta), "Haras Modelo"
        );

        String texto = relatorio.gerarTextoFormatado();

        assertTrue(texto.contains("Nenhum alerta de segurança emitido."));
        assertFalse(texto.contains("ALERTAS DE SEGURANÇA NUTRICIONAL:"));
    }
}
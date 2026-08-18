/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaBranca;

/**
 *
 * @author daza
 */
import com.mycompany.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlimentoModelCaixaBrancaTest {

    @Test
    void testGetEnergiaDigestivel_RacaoComEdDeclarada() {
        Alimento racao = new Alimento("Ração Top", "Fab", CategoriaRacao.ATLETA);
        racao.setEdDec(3.4);
        racao.setEdEst(2.8);

        assertEquals(3.4, racao.getEnergiaDigestivel());
    }

    @Test
    void testGetEnergiaDigestivel_RacaoSemEdDeclarada_ComEdEstimada() {
        Alimento racao = new Alimento("Ração Top", "Fab", CategoriaRacao.ATLETA);
        racao.setEdDec(0.0);
        racao.setEdEst(2.8);

        assertEquals(2.8, racao.getEnergiaDigestivel());
    }

    @Test
    void testGetEnergiaDigestivel_RacaoSemNenhumaEd() {
        Alimento racao = new Alimento("Ração Top", "Fab", CategoriaRacao.ATLETA);
        racao.setEdDec(null);
        racao.setEdEst(null);

        assertEquals(0.0, racao.getEnergiaDigestivel());
    }

    @Test
    void testGetEnergiaDigestivel_Volumoso() {
        Alimento volumoso = new Alimento("Feno", TipoVolumoso.TIFTON, CategoriaVolumoso.A);
        volumoso.setEdVolumoso(2.1);

        assertEquals(2.1, volumoso.getEnergiaDigestivel());
    }

    @Test
    void testGetEnergiaDigestivel_SuplementoComEnergia() {
        Alimento suplemento = new Alimento();
        suplemento.setTipo(TipoAlimento.SUPLEMENTO);
        suplemento.setEnergiaSuplemento(3000.0);

        assertEquals(3.0, suplemento.getEnergiaDigestivel());
    }

    @Test
    void testGetEnergiaDigestivel_SuplementoSemEnergia() {
        Alimento suplemento = new Alimento();
        suplemento.setTipo(TipoAlimento.SUPLEMENTO);
        suplemento.setEnergiaSuplemento(null);

        assertEquals(0.0, suplemento.getEnergiaDigestivel());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaPreta;

/**
 *
 * @author daza
 */
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.Equino;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquinoModelCaixaPretaTest {
 

    @Test
    void testConstrutorEquino_NumeroRefeicoesPadraoEInvalido() {
        //testa valores de refeicoes invalidos 
        Equino eqZero = new Equino("Trova", 400.0, 5, CategoriaFisiologica.MANTENCAO, 0);
        assertEquals(2, eqZero.getNumeroRefeicoesPorDia());

        Equino eqNegativo = new Equino("Trova", 400.0, 5, CategoriaFisiologica.MANTENCAO, -3);
        assertEquals(2, eqNegativo.getNumeroRefeicoesPorDia());

        // Valor válido acima de zero
        Equino eqValido = new Equino("Trova", 400.0, 5, CategoriaFisiologica.MANTENCAO, 4);
        assertEquals(4, eqValido.getNumeroRefeicoesPorDia());
    }
}

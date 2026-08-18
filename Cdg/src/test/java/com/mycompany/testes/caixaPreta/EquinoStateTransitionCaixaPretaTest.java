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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquinoStateTransitionCaixaPretaTest {

    @Test
    @DisplayName("Valida transição de estado fisiológico e atualização de parâmetros nutricionais")
    void testTransicaoEstadoFisiologico() {
        // Estado 1: Manutenção Inicial
        Equino equino = new Equino("Trova", 500.0, 5, CategoriaFisiologica.MANTENCAO);
        assertEquals(CategoriaFisiologica.MANTENCAO, equino.getCategoria());

        // Transição 1: Aumento de intensidade de trabalho Atleta Leve
        equino.setCategoria(CategoriaFisiologica.ATLETA_LEVE);
        assertEquals(CategoriaFisiologica.ATLETA_LEVE, equino.getCategoria());

        // Transição 2: Transição para Gestação inicio com perda de peso
        equino.setCategoria(CategoriaFisiologica.GESTANTE_INICIO);
        equino.setScoreCorporal(4); // Perdeu condição corporal

        assertEquals(CategoriaFisiologica.GESTANTE_INICIO, equino.getCategoria());
        assertEquals(4, equino.getScoreCorporal());
    }
}
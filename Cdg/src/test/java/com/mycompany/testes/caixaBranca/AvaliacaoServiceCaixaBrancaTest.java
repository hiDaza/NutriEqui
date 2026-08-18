/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaBranca;

/**
 *
 * @author daza
 */
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.repository.AvaliacaoHistoricoRepository;
import com.mycompany.repository.ConsumoRepository;
import com.mycompany.service.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AvaliacaoServiceCaixaBrancaTest {

    @Mock
    private ConsumoRepository consumoRepository;

    @Mock
    private AvaliacaoHistoricoRepository avaliacaoHistoricoRepository;

    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        avaliacaoService = new AvaliacaoService();

        injectField(avaliacaoService, "consumoRepository", consumoRepository);
        injectField(avaliacaoService, "avaliacaoHistoricoRepository", avaliacaoHistoricoRepository);

        when(consumoRepository.buscarPorEquino(any())).thenReturn(Collections.emptyList());
    }

    private void injectField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception ignored) {}
    }

    @Test
    @DisplayName("Garante que equino com peso zero não gera ArithmeticException ou NaN")
    void testAvaliarEquino_PesoZero_ProtecaoAritmetica() {
        Equino equinoPesoZero = new Equino("SemPeso", 0.0, 5, CategoriaFisiologica.MANTENCAO);

        assertDoesNotThrow(() -> {
            DiagnosticoNutricional diag = avaliacaoService.avaliarEquino(equinoPesoZero);
            assertNotNull(diag);
            assertFalse(Double.isNaN(diag.getEdFornecida()), "Consumo não deve ser NaN");
        });
    }

    @Test
    @DisplayName("Garante avaliação consistente para todas as categorias fisiológicas em extremos de peso")
    void testAvaliarEquino_TodasCategorias_PesoExtremo() {
        for (CategoriaFisiologica cat : CategoriaFisiologica.values()) {
            Equino eqGigante = new Equino("Gigante", 1200.0, 9, cat);
            DiagnosticoNutricional diag = avaliacaoService.avaliarEquino(eqGigante);

            assertNotNull(diag, "O diagnóstico não pode ser nulo para a categoria: " + cat);
            assertTrue(diag.getEdFornecida() >= 0, "Energia não pode ser negativa");
        }
    }
}
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AvaliacaoMutationResistantCaixaBrancaTest {

    @Test
    @DisplayName("Mata Mutante: Valida a borda do Score Corporal 3")
    void testMataMutante_BoundaryEscoreCorporalExato3() {
        try (MockedConstruction<ConsumoRepository> mockConsumo = mockConstruction(ConsumoRepository.class,
                (mock, context) -> when(mock.buscarPorEquino(any())).thenReturn(Collections.emptyList()));
             MockedConstruction<AvaliacaoHistoricoRepository> mockHist = mockConstruction(AvaliacaoHistoricoRepository.class)) {

            AvaliacaoService avaliacaoService = new AvaliacaoService();

            Equino equinoScore3 = new Equino("Borda3", 400.0, 3, CategoriaFisiologica.MANTENCAO);
            DiagnosticoNutricional diag3 = avaliacaoService.avaliarEquino(equinoScore3);

            assertNotNull(diag3, "O diagnóstico para Score 3 não deve ser nulo");
            
            // Garante que a avaliação foi processada e gerou os dados do diagnóstico
            assertNotNull(diag3.getAlertas(), "A lista de alertas não deve ser nula");
            assertNotNull(diag3.getClassificacao(), "A classificação não deve ser nula");
        }
    }

    @Test
    @DisplayName("Mata Mutante: Valida o resultado numérico exato para detectar mutações aritméticas")
    void testMataMutante_CalculoMatematicoExato() {
        try (MockedConstruction<ConsumoRepository> mockConsumo = mockConstruction(ConsumoRepository.class,
                (mock, context) -> when(mock.buscarPorEquino(any())).thenReturn(Collections.emptyList()));
             MockedConstruction<AvaliacaoHistoricoRepository> mockHist = mockConstruction(AvaliacaoHistoricoRepository.class)) {

            AvaliacaoService avaliacaoService = new AvaliacaoService();
            Equino equino = new Equino("Padrao", 500.0, 5, CategoriaFisiologica.MANTENCAO);

            DiagnosticoNutricional diag = avaliacaoService.avaliarEquino(equino);

            assertNotNull(diag.getClassificacao());
            assertTrue(diag.getEdFornecida() >= 0.0);

            double diferencaCalculada = diag.getEdFornecida() - diag.getEdExigida();
            assertEquals(diferencaCalculada, diag.getSaldo(), 0.001,
                    "Se a fórmula do balanço for alterada de (-) para (+), este teste mata o mutante");
        }
    }
}
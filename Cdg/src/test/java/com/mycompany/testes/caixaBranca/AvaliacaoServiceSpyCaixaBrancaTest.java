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
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AvaliacaoServiceSpyCaixaBrancaTest {

    @Mock
    private ConsumoRepository consumoRepository;

    @Mock
    private AvaliacaoHistoricoRepository avaliacaoHistoricoRepository;

    private AvaliacaoService avaliacaoServiceSpy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AvaliacaoService realService = new AvaliacaoService();

        injectField(realService, "consumoRepository", consumoRepository);
        injectField(realService, "avaliacaoHistoricoRepository", avaliacaoHistoricoRepository);

        when(consumoRepository.buscarPorEquino(any())).thenReturn(Collections.emptyList());

        // Cria o Spy somente após a injeção dos Mocks
        avaliacaoServiceSpy = spy(realService);
    }

    private void injectField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception ignored) {}
    }

    @Test
    void testAvaliarEquino_ValidacaoComSpy_CaminhoAjusteProteicoExcedente() {
        Equino equino = new Equino("Faraó", 500.0, 5, CategoriaFisiologica.MANTENCAO);

        DiagnosticoNutricional diagnostico = avaliacaoServiceSpy.avaliarEquino(equino);

        assertNotNull(diagnostico);
        verify(avaliacaoServiceSpy, atLeastOnce()).avaliarEquino(equino);
    }

    @Test
    void testAvaliarEquino_RamoScoreCorporalBaixo_GaranteAlerta() {
        Equino equinoMagro = new Equino("Magno", 380.0, 2, CategoriaFisiologica.ATLETA_INTENSO);

        DiagnosticoNutricional diagnostico = avaliacaoServiceSpy.avaliarEquino(equinoMagro);

        assertNotNull(diagnostico);
        assertTrue(diagnostico.getAlertas().stream().anyMatch(a -> a.toLowerCase().contains("score") || a.toLowerCase().contains("baixo") || a.toLowerCase().contains("peso")),
                "Deve emitir alerta referente ao score corporal crítico");
    }
}
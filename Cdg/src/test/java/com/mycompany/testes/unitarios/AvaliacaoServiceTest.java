/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.unitarios;

/**
 *
 * @author daza
 */
import com.mycompany.domain.*;
import com.mycompany.repository.AvaliacaoHistoricoRepository;
import com.mycompany.repository.ConsumoRepository;
import com.mycompany.service.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private ConsumoRepository consumoRepository;

    @Mock
    private AvaliacaoHistoricoRepository historicoRepository;

    private AvaliacaoService service;
    private Equino equino;
    private Alimento feno;
    private Alimento racao;
    private Consumo consumoFeno;
    private Consumo consumoRacao;

    @BeforeEach
    void setUp() {
        // Injeta ambos os mocks
        service = new AvaliacaoService(consumoRepository, historicoRepository);

        equino = new Equino("Spirit", 500.0, 5, CategoriaFisiologica.ATLETA_MODERADO);
        equino.setNumeroRefeicoesPorDia(2);

        feno = new Alimento("Feno Tifton", TipoVolumoso.TIFTON, CategoriaVolumoso.B);
        feno.setEdVolumoso(2.1);
        feno.setPrecoPorKg(1.20);

        racao = new Alimento("Ração Atleta", "Fab", CategoriaRacao.ATLETA);
        racao.setEdDec(3.4);
        racao.setPrecoPorKg(3.50);

        consumoFeno = new Consumo(equino, feno, 10.0);
        consumoRacao = new Consumo(equino, racao, 3.0);
    }

    @Test
    void testCalculoExcesso() {
        when(consumoRepository.buscarPorEquino(equino))
                .thenReturn(Arrays.asList(consumoFeno, consumoRacao));

        DiagnosticoNutricional diag = service.avaliarEquino(equino);

        assertEquals(26.4, diag.getEdExigida(), 0.01);
        assertEquals(31.2, diag.getEdFornecida(), 0.01);
        assertEquals(4.8, diag.getSaldo(), 0.01);
        assertTrue(diag.getClassificacao().contains("EXCESSO"));
        // Custo: 10*1.20 + 3*3.50 = 12 + 10.5 = 22.5
        assertEquals(22.5, diag.getCustoDiario(), 0.01);
        assertEquals(675.0, diag.getCustoMensal(), 0.01);

        verify(consumoRepository, times(1)).buscarPorEquino(equino);
        verify(historicoRepository, times(1)).salvar(any(AvaliacaoHistorico.class));
    }

    @Test
    void testDietaVaziaDeficit() {
        when(consumoRepository.buscarPorEquino(equino))
                .thenReturn(Collections.emptyList());

        DiagnosticoNutricional diag = service.avaliarEquino(equino);

        assertEquals(26.4, diag.getEdExigida(), 0.01);
        assertEquals(0.0, diag.getEdFornecida(), 0.01);
        assertTrue(diag.getClassificacao().contains("DÉFICIT"));
        assertFalse(diag.getAlertas().isEmpty());
        assertEquals(0.0, diag.getCustoDiario(), 0.01);
    }
}
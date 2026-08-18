/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaBranca;

/**
 *
 * @author daza
 */
import com.mycompany.controller.AlimentoController;
import com.mycompany.domain.Alimento;
import com.mycompany.domain.CategoriaRacao;
import com.mycompany.repository.AlimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlimentoControllerAvancadoCaixaBrancaTest {

    @Mock
    private AlimentoRepository alimentoRepositoryMock;

    @InjectMocks
    private AlimentoController alimentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarRacao_InspecaoDeEstadoAtravesDeArgumentCaptor() {
        when(alimentoRepositoryMock.buscarRacaoPorNome("Racao Premium")).thenReturn(null);

        alimentoController.cadastrarRacao(
            "Racao Premium", "Premier", CategoriaRacao.ATLETA,
            10.0, 15.0, 4.0, 8.0, 10.0, 25.0,
            6.0, 1.2, 0.6, 0.3, null, 3.50
        );

        ArgumentCaptor<Alimento> alimentoCaptor = ArgumentCaptor.forClass(Alimento.class);
        verify(alimentoRepositoryMock).salvar(alimentoCaptor.capture());

        Alimento alimentoSalvo = alimentoCaptor.getValue();

        assertEquals("Racao Premium", alimentoSalvo.getNome());
        assertEquals(3.50, alimentoSalvo.getPrecoPorKg());
        assertNull(alimentoSalvo.getEdDec());
        assertNotNull(alimentoSalvo.getEdEst());
        assertTrue(alimentoSalvo.getEdEst() > 0);
    }

    @Test
    void testCalcularEdRacao_MetodoPrivado_GaranteValorNaoNegativo() throws Exception {
        Method method = AlimentoController.class.getDeclaredMethod(
            "calcularEdRacao", double.class, double.class, double.class, double.class
        );
        method.setAccessible(true);

        double resultadoNegativoSimulado = (double) method.invoke(alimentoController, 0.0, 0.0, 300.0, 300.0);

        assertEquals(0.0, resultadoNegativoSimulado, "Cálculo de ED nunca deve ser menor que zero");
    }
}
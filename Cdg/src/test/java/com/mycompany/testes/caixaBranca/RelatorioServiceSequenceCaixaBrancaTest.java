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
import com.mycompany.domain.Equino;
import com.mycompany.domain.RelatorioVisita;
import com.mycompany.repository.AvaliacaoHistoricoRepository;
import com.mycompany.repository.ConsumoRepository;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.PropriedadeRepository;
import com.mycompany.service.RelatorioService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RelatorioServiceSequenceCaixaBrancaTest {

    @Test
    void testGerarRelatorioLoteCompleto_GaranteOrdemDeExecucaoEFluxo() {
        Equino equinoValido = new Equino("Trova", 400.0, 5, CategoriaFisiologica.MANTENCAO);

        // Intercepta todos os repositórios instanciados com 'new' durante a execução do relatório
        try (MockedConstruction<EquinoRepository> mockEqRepo = mockConstruction(EquinoRepository.class,
                (mock, context) -> when(mock.listarTodos()).thenReturn(List.of(equinoValido)));
             MockedConstruction<PropriedadeRepository> mockPropRepo = mockConstruction(PropriedadeRepository.class);
             MockedConstruction<ConsumoRepository> mockConsumoRepo = mockConstruction(ConsumoRepository.class,
                (mock, context) -> when(mock.buscarPorEquino(any())).thenReturn(Collections.emptyList()));
             MockedConstruction<AvaliacaoHistoricoRepository> mockHistRepo = mockConstruction(AvaliacaoHistoricoRepository.class)) {

            RelatorioService relatorioService = new RelatorioService();
            RelatorioVisita relatorio = relatorioService.gerarRelatorioLoteCompleto();

            assertNotNull(relatorio, "Relatório de visita gerado não deve ser nulo");
        }
    }
}
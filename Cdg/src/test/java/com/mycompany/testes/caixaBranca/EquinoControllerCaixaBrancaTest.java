/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaBranca;

/**
 *
 * @author daza
 */
import com.mycompany.controller.EquinoController;
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.PropriedadeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EquinoControllerCaixaBrancaTest {

    @Test
    void testCadastrarEquino_PropriedadeInexistente() {
        // Intercepta qualquer "new PropriedadeRepository()" e "new EquinoRepository()" dentro do método
        try (MockedConstruction<PropriedadeRepository> mockProp = mockConstruction(PropriedadeRepository.class,
                (mock, context) -> when(mock.buscarPorNome("Propriedade Inexistente")).thenReturn(null));
             MockedConstruction<EquinoRepository> mockEq = mockConstruction(EquinoRepository.class,
                (mock, context) -> when(mock.buscarPorNome("Bravio")).thenReturn(null))) {

            EquinoController controller = new EquinoController();
            String resultado = controller.cadastrarEquino("Bravio", 450.0, 5, CategoriaFisiologica.MANTENCAO, "Propriedade Inexistente");

            assertEquals("Err: Propriedade não encontrada. Cadastre a Propriedade primeiro.", resultado);
        }
    }

    @Test
    void testCadastrarEquino_CapturaExcecaoRepository() {
        try (MockedConstruction<PropriedadeRepository> mockProp = mockConstruction(PropriedadeRepository.class);
             MockedConstruction<EquinoRepository> mockEq = mockConstruction(EquinoRepository.class,
                (mock, context) -> {
                    when(mock.buscarPorNome(anyString())).thenReturn(null);
                    doThrow(new RuntimeException("Erro de conexão com BD")).when(mock).salvar(any());
                })) {

            EquinoController controller = new EquinoController();
            String resultado = controller.cadastrarEquino("Bravio", 450.0, 5, CategoriaFisiologica.MANTENCAO, null);

            assertEquals("Erro: Não foi possível salvar o equino. Verifique o banco de dados.", resultado);
        }
    }
}
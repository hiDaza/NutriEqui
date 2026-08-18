/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaPreta;

/**
 *
 * @author daza
 */
import com.mycompany.controller.AlimentoController;
import com.mycompany.domain.*;
import com.mycompany.repository.AlimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlimentoControllerCaixaPretaTest {

    @Mock
    private AlimentoRepository alimentoRepositoryMock;

    private AlimentoController alimentoController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        alimentoController = new AlimentoController();
        
        Field field = AlimentoController.class.getDeclaredField("alimentoRepository");
        field.setAccessible(true);
        field.set(alimentoController, alimentoRepositoryMock);
    }


    @Test
    void testCadastrarVolumoso_Sucesso() {
        when(alimentoRepositoryMock.buscarVolumosoPorNomeETipo(anyString(), any())).thenReturn(null);

        String resultado = alimentoController.cadastrarVolumoso(
            TipoVolumoso.TIFTON, CategoriaVolumoso.A, 85.0, 12.0, 65.0, 35.0, 2.0, "SP", 1.50
        );

        assertTrue(resultado.contains("Volumoso cadastrado com sucesso!"));
    }

    @Test
    void testCadastrarVolumoso_ErroDuplicado() {
        // Valida partição de nome/tipo já cadastrado
        Alimento existente = new Alimento("Tifton - Categoria A", TipoVolumoso.TIFTON, CategoriaVolumoso.A);
        when(alimentoRepositoryMock.buscarVolumosoPorNomeETipo(anyString(), eq(TipoVolumoso.TIFTON))).thenReturn(existente);

        String resultado = alimentoController.cadastrarVolumoso(
            TipoVolumoso.TIFTON, CategoriaVolumoso.A, 85.0, 12.0, 65.0, 35.0, 2.0, "SP", 1.50
        );

        assertEquals("Erro: Já existe um volumoso com estas características.", resultado);
    }

        //validação de nome duplicado
    @Test
    void testCadastrarSuplemento_ErroNomeDuplicado() {
        Alimento existente = new Alimento();
        when(alimentoRepositoryMock.buscarSupplementoPorNome("Mineral Equino")).thenReturn(existente);

        String resultado = alimentoController.cadastrarSuplemento(
            "Mineral Equino", "NutriVet", "Mineral", UnidadeSuplemento.POR_KG,
            100.0, 100.0, null, null, null, null, null, null, null, null, null, null, null, CalculoEnergetico.SIM, 12.0
        );

        assertEquals("Erro: Já existe um suplemento com este nome.", resultado);
    }
}

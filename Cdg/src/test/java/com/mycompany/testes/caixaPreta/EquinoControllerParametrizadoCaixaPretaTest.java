/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaPreta;

/**
 *
 * @author daza
 */
import com.mycompany.controller.EquinoController;
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.repository.EquinoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquinoControllerParametrizadoCaixaPretaTest {

    @Mock
    private EquinoRepository equinoRepositoryMock;

    private EquinoController equinoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        equinoController = new EquinoController(equinoRepositoryMock);
    }
    
    //scores invalidos
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10, 99})
    void testCadastrarEquino_ScoresInvalidos_DeveRejeitar(int scoreInvalido) {
        String resultado = equinoController.cadastrarEquino("Apollo", 450.0, scoreInvalido, CategoriaFisiologica.MANTENCAO, null);
        assertEquals("Erro: O score corporal deve estar entre 1 e 9.", resultado);
    }

    //scores validos
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 8, 9})
    void testCadastrarEquino_ScoresValidos_DeveAceitar(int scoreValido) {
        when(equinoRepositoryMock.buscarPorNome(anyString())).thenReturn(null);

        String resultado = equinoController.cadastrarEquino("Apollo", 450.0, scoreValido, CategoriaFisiologica.MANTENCAO, null);
        assertTrue(resultado.contains("Equino cadastrado com sucesso!"));
    }

    //valores invaldios
    @ParameterizedTest
    @CsvSource({
        "0.0, Erro: O peso deve ser maior que zero.",
        "-10.5, Erro: O peso deve ser maior que zero."
    })
    void testCadastrarEquino_PesosInvalidos(double pesoInvalido, String mensagemEsperada) {
        String resultado = equinoController.cadastrarEquino("Apollo", pesoInvalido, 5, CategoriaFisiologica.MANTENCAO, null);
        assertEquals(mensagemEsperada, resultado);
    }
}

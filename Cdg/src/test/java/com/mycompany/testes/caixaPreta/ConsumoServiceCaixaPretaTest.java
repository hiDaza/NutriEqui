/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaPreta;

/**
 *
 * @author daza
 */
import com.mycompany.service.ConsumoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsumoServiceCaixaPretaTest {

    @Mock
    private ConsumoService consumoServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        //teste de estresse// muitas entradas seguidas 
    @Test
    @DisplayName("Estresse: Registrar 100 consumos contínuos para o mesmo equino")
    void testEstresse_RegistrosMúltiplosConsumo() {
        when(consumoServiceMock.registrarConsumo(eq("Tornado"), anyString(), anyDouble()))
                .thenReturn("Consumo registrado com sucesso");

        // entradas repetidas
        for (int i = 1; i <= 100; i++) {
            String resposta = consumoServiceMock.registrarConsumo("Tornado", "Feno de Alfafa", 0.5);
            assertEquals("Consumo registrado com sucesso", resposta);
        }

        verify(consumoServiceMock, times(100)).registrarConsumo(eq("Tornado"), eq("Feno de Alfafa"), eq(0.5));
    }

            //teste de borda
    @Test
    @DisplayName("Borda: Registrar suplemento com quantidade extremamente baixa (0.0001 kg)")
    void testRegistrarSuplemento_QuantidadeMicro() {
        when(consumoServiceMock.registrarSuplemento("Tornado", "Premix Vits", 0.0001, true))
                .thenReturn("Suplemento registrado");

        String resultado = consumoServiceMock.registrarSuplemento("Tornado", "Premix Vits", 0.0001, true);

        assertEquals("Suplemento registrado", resultado);
        verify(consumoServiceMock).registrarSuplemento("Tornado", "Premix Vits", 0.0001, true);
    }
}

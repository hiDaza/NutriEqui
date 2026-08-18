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
import com.mycompany.repository.AlimentoRepository;
import com.mycompany.repository.ConsumoRepository;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.service.ConsumoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumoServiceTest {

    @Mock private EquinoRepository equinoRepo;
    @Mock private AlimentoRepository alimentoRepo;
    @Mock private ConsumoRepository consumoRepo;

    private ConsumoService service;

    @BeforeEach
    void setUp() {
        service = new ConsumoService(equinoRepo, alimentoRepo, consumoRepo);
    }

    @Test
    void testRegistrarConsumoSucesso() {
        Equino eq = new Equino("Spirit", 500.0, 5, CategoriaFisiologica.ATLETA_MODERADO);
        Alimento al = new Alimento("Feno", TipoVolumoso.TIFTON, CategoriaVolumoso.B);
        al.setEdVolumoso(2.1);

        when(equinoRepo.buscarPorNome("Spirit")).thenReturn(eq);
        when(alimentoRepo.buscarPorNome("Feno")).thenReturn(al);

        String resultado = service.registrarConsumo("Spirit", "Feno", 10.0);
        assertEquals("Consumo registrado com sucesso!", resultado);
        verify(consumoRepo, times(1)).salvar(any(Consumo.class));
    }

    @Test
    void testRegistrarConsumoEquinoNaoEncontrado() {
        when(equinoRepo.buscarPorNome("X")).thenReturn(null);
        String resultado = service.registrarConsumo("X", "Feno", 10.0);
        assertEquals("Erro: Equino não encontrado.", resultado);
        verify(consumoRepo, never()).salvar(any());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.funcionais;

/**
 *
 * @author daza
 */
import com.mycompany.controller.*;
import com.mycompany.domain.*;
import com.mycompany.testes.integracao.JpaTestUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FluxoCompletoTest {

    private final EquinoController equinoController = new EquinoController();
    private final AlimentoController alimentoController = new AlimentoController();
    private final ConsumoController consumoController = new ConsumoController();
    private final AvaliacaoController avaliacaoController = new AvaliacaoController();

    @BeforeEach
    void limparBanco() {
        EntityManager em = JpaTestUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Consumo").executeUpdate();
            em.createQuery("DELETE FROM AvaliacaoHistorico").executeUpdate();
            em.createQuery("DELETE FROM Equino").executeUpdate();
            em.createQuery("DELETE FROM Alimento").executeUpdate();
            em.createQuery("DELETE FROM Propriedade").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Test
    void testFluxoCompletoComExcesso() {
        // 1. Cadastrar equino
        equinoController.cadastrarEquino("Spirit", 500.0, 5, CategoriaFisiologica.ATLETA_MODERADO, null);

        // 2. Cadastrar alimentos
        alimentoController.cadastrarVolumoso(TipoVolumoso.TIFTON, CategoriaVolumoso.B,
                90.0, 8.5, 68.0, 38.0, 2.1, "Sudeste", 1.20);
        alimentoController.cadastrarRacao("Ração Atleta", "Fab", CategoriaRacao.ATLETA,
                12.0, 14.0, 5.0, 10.0, 9.0, 18.0, 7.0, 0.8, 0.5, 0.3, 3.4, 3.50);

        // 3. Registrar consumos
        consumoController.registrarConsumo("Spirit", "Tifton - Categoria B", 10.0);
        consumoController.registrarConsumo("Spirit", "Ração Atleta", 3.0);

        // 4. Avaliar
        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino("Spirit");
        assertNotNull(diag);
        assertEquals(26.4, diag.getEdExigida(), 0.01);
        assertEquals(31.2, diag.getEdFornecida(), 0.01);
        assertTrue(diag.getClassificacao().contains("EXCESSO"));
    }

    @Test
    void testFluxoCompletoComDeficit() {
        equinoController.cadastrarEquino("Lua", 450.0, 4, CategoriaFisiologica.MANTENCAO, null);
        alimentoController.cadastrarVolumoso(TipoVolumoso.OUTRO, CategoriaVolumoso.C,
                85.0, 5.0, 75.0, 45.0, 1.6, "Sul", 0.90);
        consumoController.registrarConsumo("Lua", "Outro - Categoria C", 4.0);

        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino("Lua");
        assertNotNull(diag);
        assertEquals(14.85, diag.getEdExigida(), 0.01);
        assertEquals(6.4, diag.getEdFornecida(), 0.01);
        assertTrue(diag.getClassificacao().contains("DÉFICIT"));
        assertFalse(diag.getAlertas().isEmpty());
    }
}
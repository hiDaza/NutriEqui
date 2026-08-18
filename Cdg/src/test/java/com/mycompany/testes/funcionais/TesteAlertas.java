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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TesteAlertas {

    private final EquinoController equinoController = new EquinoController();
    private final AlimentoController alimentoController = new AlimentoController();
    private final ConsumoController consumoController = new ConsumoController();
    private final AvaliacaoController avaliacaoController = new AvaliacaoController();

    @BeforeEach
    void limpar() {
        var em = JpaTestUtil.getEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Consumo").executeUpdate();
        em.createQuery("DELETE FROM Equino").executeUpdate();
        em.createQuery("DELETE FROM Alimento").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    static void tearDown() {
        JpaTestUtil.close();
    }

    @Test
    void alertaBaixoVolumoso() {
        equinoController.cadastrarEquino("Spirit", 500.0, 5, CategoriaFisiologica.ATLETA_MODERADO, null);
        alimentoController.cadastrarVolumoso(TipoVolumoso.TIFTON, CategoriaVolumoso.B,
                90.0, 8.5, 68.0, 38.0, 2.1, "Sudeste", 1.20);
        consumoController.registrarConsumo("Spirit", "Tifton - Categoria B", 4.0);

        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino("Spirit");
        assertTrue(diag.getAlertas().stream().anyMatch(a -> a.contains("Baixo fornecimento de volumoso")));
    }

    @Test
    void alertaExcessoConcentrado() {
        equinoController.cadastrarEquino("Spirit", 500.0, 5, CategoriaFisiologica.ATLETA_MODERADO, null);
        alimentoController.cadastrarRacao("Ração X", "Fab", CategoriaRacao.ATLETA,
                12.0, 14.0, 5.0, 10.0, 9.0, 18.0, 7.0, 0.8, 0.5, 0.3, 3.4, 3.50);
        consumoController.registrarConsumo("Spirit", "Ração X", 6.0);

        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino("Spirit");
        assertTrue(diag.getAlertas().stream().anyMatch(a -> a.contains("Excesso de concentrado por refeição")));
    }
}
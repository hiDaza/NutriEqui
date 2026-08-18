/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.integracao;

/**
 *
 * @author daza
 */
import com.mycompany.domain.*;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.PropriedadeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquinoRepositoryTest {

    private final EquinoRepository equinoRepo = new EquinoRepository();
    private final PropriedadeRepository propRepo = new PropriedadeRepository();

    @BeforeEach
    void limpar() {
        var em = JpaTestUtil.getEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Consumo").executeUpdate();
        em.createQuery("DELETE FROM Equino").executeUpdate();
        em.createQuery("DELETE FROM Propriedade").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    static void tearDown() {
        JpaTestUtil.close();
    }

    @Test
    void testSalvarEBuscarEquino() {
        Propriedade prop = new Propriedade("Fazenda A", "Rua A", "123", "João");
        propRepo.salvar(prop);

        Equino eq = new Equino("Spirit", 500.0, 5, CategoriaFisiologica.ATLETA_MODERADO);
        eq.setPropriedade(prop);
        equinoRepo.salvar(eq);

        Equino encontrado = equinoRepo.buscarPorNome("Spirit");
        assertNotNull(encontrado);
        assertEquals("Spirit", encontrado.getNome());
        assertEquals(500.0, encontrado.getPeso());
    }
}

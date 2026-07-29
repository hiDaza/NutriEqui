/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

/**
 *
 * @author daza
 */

import com.mycompany.domain.Consumo;
import com.mycompany.domain.Equino;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ConsumoRepository {

    public void salvar(Consumo consumo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (consumo.getId() == null) {
                em.persist(consumo);
            } else {
                em.merge(consumo);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Consumo> buscarPorEquino(Equino equino) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Consumo> query = em.createQuery(
                    "SELECT c FROM Consumo c WHERE c.equino = :equino", Consumo.class);
            query.setParameter("equino", equino);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

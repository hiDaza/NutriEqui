/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

/**
 *
 * @author daza
 */

import com.mycompany.domain.Alimento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AlimentoRepository {

    public void salvar(Alimento alimento) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (alimento.getId() == null) {
                em.persist(alimento);
            } else {
                em.merge(alimento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Alimento buscarPorNome(String nome) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Alimento> query = em.createQuery(
                    "SELECT a FROM Alimento a WHERE a.nome = :nome", Alimento.class);
            query.setParameter("nome", nome);
            List<Alimento> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Alimento> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Alimento a", Alimento.class).getResultList();
        } finally {
            em.close();
        }
    }
}
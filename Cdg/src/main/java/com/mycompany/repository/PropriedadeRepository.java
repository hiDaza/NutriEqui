/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

/**
 *
 * @author daza
 */
import com.mycompany.domain.Propriedade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PropriedadeRepository {

    public void salvar(Propriedade propriedade) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (propriedade.getId() == null) {
                em.persist(propriedade);
            } else {
                em.merge(propriedade);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Propriedade buscarPorId(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Propriedade.class, id);
        } finally {
            em.close();
        }
    }

    public Propriedade buscarPorNome(String nome) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Propriedade> query = em.createQuery(
                    "SELECT p FROM Propriedade p WHERE p.nome = :nome", Propriedade.class);
            query.setParameter("nome", nome);
            List<Propriedade> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Propriedade> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Propriedade p", Propriedade.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Propriedade buscarUltima() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Propriedade> query = em.createQuery(
                    "SELECT p FROM Propriedade p ORDER BY p.id DESC", Propriedade.class);
            query.setMaxResults(1);
            List<Propriedade> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
}
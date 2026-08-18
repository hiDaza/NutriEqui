/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

/**
 *
 * @author daza
 */
import com.mycompany.domain.Equino;
import com.mycompany.domain.Propriedade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EquinoRepository {

    public void salvar(Equino equino) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (equino.getId() == null) {
                em.persist(equino);
            } else {
                em.merge(equino);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Equino buscarPorNome(String nome) {
        if (nome == null) {
            return null;
        }

        EntityManager em = JpaUtil.getEntityManager();
        try {
            String nomeNormalizado = nome.trim();
            TypedQuery<Equino> query = em.createQuery(
                    "SELECT e FROM Equino e WHERE lower(trim(e.nome)) = lower(:nome)", Equino.class);
            query.setParameter("nome", nomeNormalizado);
            List<Equino> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Equino> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Equino e", Equino.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Equino> buscarPorPropriedade(Propriedade propriedade) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Equino> query = em.createQuery(
                    "SELECT e FROM Equino e WHERE e.propriedade = :propriedade", Equino.class);
            query.setParameter("propriedade", propriedade);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Equino> buscarPorPropriedadeNome(String nomePropriedade) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Equino> query = em.createQuery(
                    "SELECT e FROM Equino e WHERE e.propriedade.nome = :nome", Equino.class);
            query.setParameter("nome", nomePropriedade);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
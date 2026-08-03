package com.mycompany.repository;

/**
 *
 * @author daza
 */

import com.mycompany.domain.Alimento;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.domain.TipoVolumoso;
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

    public Alimento buscarRacaoPorNome(String nome) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Alimento> query = em.createQuery(
                    "SELECT a FROM Alimento a WHERE a.nome = :nome AND a.tipo = :tipo", Alimento.class);
            query.setParameter("nome", nome);
            query.setParameter("tipo", TipoAlimento.RACAO);
            List<Alimento> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public Alimento buscarVolumosoPorNomeETipo(String nome, TipoVolumoso tipoVolumoso) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Alimento> query = em.createQuery(
                    "SELECT a FROM Alimento a WHERE a.nome = :nome AND a.tipoVolumoso = :tipoVolumoso", Alimento.class);
            query.setParameter("nome", nome);
            query.setParameter("tipoVolumoso", tipoVolumoso);
            List<Alimento> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public Alimento buscarSupplementoPorNome(String nome) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Alimento> query = em.createQuery(
                    "SELECT a FROM Alimento a WHERE a.nomeComercialSuplemento = :nome AND a.tipo = :tipo", Alimento.class);
            query.setParameter("nome", nome);
            query.setParameter("tipo", TipoAlimento.SUPLEMENTO);
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

    public List<Alimento> listarPorTipo(TipoAlimento tipo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Alimento> query = em.createQuery(
                    "SELECT a FROM Alimento a WHERE a.tipo = :tipo", Alimento.class);
            query.setParameter("tipo", tipo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
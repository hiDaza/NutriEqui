package com.mycompany.repository;

import com.mycompany.domain.AvaliacaoHistorico;
import com.mycompany.domain.Equino;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AvaliacaoHistoricoRepository {

    public void salvar(AvaliacaoHistorico avaliacao) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (avaliacao.getId() == null) {
                em.persist(avaliacao);
            } else {
                em.merge(avaliacao);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<AvaliacaoHistorico> buscarPorEquino(Equino equino) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<AvaliacaoHistorico> query = em.createQuery(
                    "SELECT a FROM AvaliacaoHistorico a WHERE a.equino = :equino ORDER BY a.dataAvaliacao DESC",
                    AvaliacaoHistorico.class);
            query.setParameter("equino", equino);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

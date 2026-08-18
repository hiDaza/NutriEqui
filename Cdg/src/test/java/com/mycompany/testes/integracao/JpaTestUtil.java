package com.mycompany.testes.integracao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daza
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaTestUtil {

    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("nutriEquiPU");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
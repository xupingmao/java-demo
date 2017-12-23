package com.xpm.jpa;

import com.xpm.jpa.entity.Doctor;
import com.xpm.jpa.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by xupingmao on 2017/5/4.
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("java-demo");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();
        try {
            new JpaTests(entityManager).runTests();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

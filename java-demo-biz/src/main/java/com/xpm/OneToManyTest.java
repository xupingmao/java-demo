package com.xpm;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xpm.jpa.entity.OneToManyMany;
import com.xpm.jpa.entity.OneToManyOne;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by xupingmao on 2017/6/20.
 */
public class OneToManyTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("java-demo");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();
        try {
            runTest(entityManager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    private static void runTest(EntityManager entityManager) {
        createColumns(entityManager);
        TypedQuery<OneToManyOne> query = entityManager.createQuery("SELECT one FROM OneToManyOne one WHERE one.oneId=?1", OneToManyOne.class);
        query.setParameter(1, "one-1");
        OneToManyOne singleResult = query.getSingleResult();
        System.out.println(JSON.toJSONString(singleResult, true));
    }

    private static void createColumns(EntityManager entityManager) {
        OneToManyMany many1 = new OneToManyMany();
        OneToManyMany many2 = new OneToManyMany();

        String many1Id = "many-1";
        String many2Id = "many-2";
        String oneId = "one-1";

        many1.setManyId(many1Id);
        many2.setManyId(many2Id);
        many1.setValue("many1");
        many2.setValue("many2");

        OneToManyOne one = new OneToManyOne();
        one.setValue("one");
        one.setOneId(oneId);

        one.setManyList(Lists.newArrayList(many1, many2));

        entityManager.merge(one);
    }
}

package com.xpm.jpa;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.xpm.jpa.entity.*;
import com.xpm.jpa.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by xupingmao on 2017/5/4.
 */
public class JpaTests {

    private final EntityManager manager;

    public JpaTests(EntityManager manager) {
        this.manager = manager;
    }

    public void createDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId("Doctor");
        doctor.setName("Doctor");
        doctor.setLevel(10);
        manager.merge(doctor);
    }

    public void createPerson() {
        Person person = new Person();
        person.setId("Test");
        person.setName("Test");
        manager.merge(person);
    }

    private void createLongNamePerson() {
        PersonCannotLongerThan31 person = new PersonCannotLongerThan31();
        person.setName("Long");
        person.setId("Long");
        manager.merge(person);
    }

    public Doctor findDoctor(String id) {
        return manager.find(Doctor.class, id);
    }

    public void runTests() {
        createPerson();
        createDoctor();
        createLongNamePerson();

        Doctor doctor = findDoctor("Doctor");
        System.out.println(doctor);
        Preconditions.checkState(doctor != null);

        Doctor test = findDoctor("Test");
        System.out.println(test);
        Preconditions.checkState(test == null);

        testManyToMany();
        testCriteria();

        testOneToOne();
    }

    private void testOneToOne() {
        Address address = new Address();
        address.setId("address");
        address.setAddress("测试地址");

        Address saved = manager.merge(address);
        Order order1 = new Order();
        Order order2 = new Order();

        order1.setId("order1");
        order2.setId("order2");
        order1.setValue("order1");
        order2.setValue("order2");

        manager.merge(order1);
        manager.merge(order2);

        TypedQuery<Address> query = manager.createQuery("SELECT ad FROM Address ad WHERE ad.id = ?1", Address.class);
        query.setParameter(1, "address");
        Address singleResult = query.getSingleResult();
        System.out.println(JSON.toJSONString(singleResult, true));
    }

    private void testCriteria() {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        Root<Person> from = query.from(Person.class);
        query.select(from);
        // WHERE 从句
//        query.where()
        TypedQuery<Person> typedQuery = manager.createQuery(query);
        List<Person> resultList = typedQuery.getResultList();
        for (Person p: resultList) {
            System.out.println(p);
        }
    }

    private void testManyToMany() {

        Teacher t1 = new Teacher();
        t1.setId("t1");
        t1.setName("t1");

        Teacher t2 = new Teacher();
        t2.setId("t2");
        t2.setName("t2");

        Clas clas1 = new Clas();
        clas1.setId("class1");
        clas1.setName("class1");

        Clas clas2 = new Clas();
        clas2.setName("class2");
        clas2.setId("class2");

        t1.setClasSet(Sets.newHashSet(clas1, clas2));
        t2.setClasSet(Sets.newHashSet(clas1, clas2));

        clas1.setTeacherSet(Sets.newHashSet(t1, t2));
        clas2.setTeacherSet(Sets.newHashSet(t1, t2));

        manager.merge(t1);
    }
}

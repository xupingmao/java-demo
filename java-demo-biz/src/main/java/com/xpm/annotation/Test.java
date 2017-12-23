package com.xpm.annotation;

import org.eclipse.persistence.internal.jpa.metadata.accessors.classes.EntityAccessor;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.TransientAccessor;
import org.eclipse.persistence.internal.jpa.metadata.accessors.objects.MetadataAnnotation;
import org.eclipse.persistence.internal.jpa.metadata.accessors.objects.MetadataClass;
import org.eclipse.persistence.internal.jpa.metadata.accessors.objects.MetadataField;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by xupingmao on 2017/6/9.
 */
public class Test {

    public static void main(String[] args) {
        ObjectA obj = new ObjectB();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("java-demo");
        EntityManager entityManager = emf.createEntityManager();



//        TransientAccessor transientAccessor = new TransientAccessor();
//        transientAccessor.setAccessibleObject(new MetadataClass());
//        transientAccessor.setClassAccessor(new EntityAccessor());

//        MetadataAnnotation annotation = transientAccessor.getAnnotation(ObjectB.class);
//        System.out.println(annotation);
    }
}

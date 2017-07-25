package com.xpm.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;
import java.util.Set;

/**
 * Created by xupingmao on 2017/7/18.
 */
public class TableStructureValidator implements ApplicationContextAware {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    private Logger LOGGER = LoggerFactory.getLogger(TableStructureValidator.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOGGER.info("检测Entity结构完整性");

        Set<EntityType<?>> entities = entityManagerFactory.getMetamodel().getEntities();
        for (EntityType<?> entity: entities) {
//            LOGGER.info("{}={}", entity.getName(), entity.getJavaType());
            String sql = String.format("SELECT e FROM %s e", entity.getName());
            LOGGER.info(sql);
            TypedQuery<?> query = entityManager.createQuery(sql, entity.getJavaType());
            query.setMaxResults(1);
            // 测试结构完整性
            query.getSingleResult();
        }
    }
}

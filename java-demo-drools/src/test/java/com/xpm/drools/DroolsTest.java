package com.xpm.drools;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.io.impl.UrlResource;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import sun.misc.Resource;
import sun.rmi.rmic.iiop.ClassPathLoader;

import java.net.URL;

/**
 * Created by xupingmao on 2017/12/4.
 */
public class DroolsTest {


    @Test
    public void test001(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        URL resource = getClass().getClassLoader().getResource("test001.rule");
        builder.add(new UrlResource(resource), ResourceType.DRL);
        Assert.assertFalse(builder.hasErrors());
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(builder.getKnowledgePackages());
    }
}

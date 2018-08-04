package com.xpm.spring;

import org.junit.Test;

/**
 * Created by xupingmao on 2018/1/4.
 */
public class AopTest extends BaseSpringTest {

    @Test
    public void test() {
        AopBean aopBean = context.getBean(AopBean.class);
        aopBean.test();
    }

    @Test
    public void adviceTest() {
        AopBean aopBean = context.getBean(AopBean.class);
        aopBean.adviceTest("test", 10);
    }

}

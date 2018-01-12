package com.xpm.spring;

import com.xpm.spring.annotation.AopMethod;
import com.xpm.spring.annotation.MyCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xupingmao on 2018/1/4.
 */
public class AopBean {

    private Logger logger = LoggerFactory.getLogger(AopBean.class);

    @AopMethod(name = "test")
    public void test() {
        logger.info("I'm a method");
    }

    @MyCache(key = "adviceTest")
    public void adviceTest(String name, int intArg) {
        logger.info("cache");
    }

}

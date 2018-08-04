package com.xpm.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AOP拦截器
 * Created by xupingmao on 2018/1/2.
 */
public class AopInterceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(AopInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("This is a proxy");
        return invocation.proceed();
    }
}

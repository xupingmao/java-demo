package com.xpm.spring;

import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by xupingmao on 2018/1/4.
 */
@Aspect
public class AopAdvice {

    private Logger logger = LoggerFactory.getLogger(AopAdvice.class);

    @Pointcut("@annotation(com.xpm.spring.annotation.MyCache)")
    public void handle() {

    }

    @Around("handle()")
    public void realHandle(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
        logger.info("args={}", Arrays.asList(args));
        jp.proceed();
    }
}

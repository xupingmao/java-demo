package com.xpm.spring;

import org.junit.BeforeClass;
import org.springframework.context.annotation.*;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Spring程序的入口
 * EnableXXX只有放在配置类上才生效
 * Created by xupingmao on 2017/12/21.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableRetry(proxyTargetClass = true)
@ImportResource(locations = "classpath:META-INF/spring-context.xml")
public class BaseSpringTest {

    static AnnotationConfigApplicationContext context;

    @Bean
    public MySpringRetry getMySpringRetry() {
        return new MySpringRetry();
    }

    @Bean
    public AopBean getAopBean() {
        return new AopBean();
    }

    @Bean
    public AopAdvice getAopAdvice() {
        return new AopAdvice();
    }

    @BeforeClass
    public static void init() {
        context = new AnnotationConfigApplicationContext(BaseSpringTest.class);
    }

}

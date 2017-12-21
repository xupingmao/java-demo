package com.xpm.spring;

import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Spring程序的入口
 * EnableXXX只有放在配置类上才生效
 * Created by xupingmao on 2017/12/21.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableRetry(proxyTargetClass = true)
public class BaseSpringTest {

    static AnnotationConfigApplicationContext context;

    @Bean
    public MySpringRetry getMySpringRetry() {
        return new MySpringRetry();
    }

    @BeforeClass
    public static void init() {
        context = new AnnotationConfigApplicationContext(BaseSpringTest.class);
    }

}

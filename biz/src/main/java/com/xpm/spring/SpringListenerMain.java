package com.xpm.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by xupingmao on 2017/7/12.
 */
@Configuration
public class SpringListenerMain {

    static class MyBean {

    }

    static class MyListener implements ApplicationListener<ApplicationContextEvent> {

        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            // 不一定能够捕捉到启动异常
            System.out.println(event);
        }
    }

    @Bean
    public MyBean getMyBean() {
        throw new RuntimeException("xxxx");
    }

    @Bean
    public MyListener getMyListener() {
        return new MyListener();
    }

    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringListenerMain.class);
            // 抛出了异常
        } catch (Exception ex) {
            // 抛出了异常
        }
    }
}

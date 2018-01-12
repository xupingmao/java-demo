package com.xpm.spring.annotation;

/**
 * Created by xupingmao on 2018/1/4.
 */
public @interface MyCache {

    String key() default "";

}

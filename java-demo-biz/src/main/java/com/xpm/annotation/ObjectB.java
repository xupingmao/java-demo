package com.xpm.annotation;

import javax.persistence.Column;

/**
 * Created by xupingmao on 2017/6/9.
 */
public class ObjectB extends ObjectA {

    @Column(name = "status")
    private int status;
}

package com.xpm.annotation;

import javax.persistence.Column;

/**
 * Created by xupingmao on 2017/6/9.
 */
public class ObjectA {
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;
}

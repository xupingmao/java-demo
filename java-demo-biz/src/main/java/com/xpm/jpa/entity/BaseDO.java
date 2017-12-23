package com.xpm.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xupingmao on 2017/5/4.
 */
@MappedSuperclass
public class BaseDO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

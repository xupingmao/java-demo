package com.xpm.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by xupingmao on 2017/6/29.
 */
@Entity
@Table
public class Address extends BaseDO{
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

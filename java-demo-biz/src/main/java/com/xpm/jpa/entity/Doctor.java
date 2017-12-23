package com.xpm.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created by xupingmao on 2017/5/4.
 */
@Entity
public class Doctor extends Person {

    @Column(name = "level")
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

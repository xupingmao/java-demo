package com.xpm.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by xupingmao on 2017/5/4.
 */
@Entity
public class PersonCannotLongerThan31 extends Person{
    @Column(name = "reason")
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

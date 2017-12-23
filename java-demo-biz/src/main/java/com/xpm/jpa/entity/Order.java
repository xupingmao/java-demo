package com.xpm.jpa.entity;

import javax.persistence.*;

/**
 * Created by xupingmao on 2017/6/29.
 */
@Entity
@Table(name = "t_order")
public class Order extends BaseDO {

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

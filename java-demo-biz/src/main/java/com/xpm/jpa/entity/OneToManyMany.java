package com.xpm.jpa.entity;


import javax.persistence.*;

/**
 * Created by xupingmao on 2017/6/20.
 */
@Entity
public class OneToManyMany extends BaseDO{

    @Column(name = "many_id")
    private String manyId;

    @Column(name = "one_id")
    private Long oneId;

    @Column(name = "value")
    private String value;

//    @ManyToOne(targetEntity = OneToManyOne.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "one_id", referencedColumnName = "one_id")
//    private OneToManyOne one;

    public String getManyId() {
        return manyId;
    }

    public void setManyId(String manyId) {
        this.manyId = manyId;
    }

    public Long getOneId() {
        return oneId;
    }

    public void setOneId(Long oneId) {
        this.oneId = oneId;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

//    public OneToManyOne getOne() {
//        return one;
//    }
//
//    public void setOne(OneToManyOne one) {
//        this.one = one;
//    }
}

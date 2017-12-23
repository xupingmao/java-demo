package com.xpm.jpa.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by xupingmao on 2017/6/20.
 */
@Entity
public class OneToManyOne extends BaseDO {

    @Column(name = "one_id")
    private String oneId;

    @Column(name = "value")
    private String value;

    @JoinColumn(name = "one_id", referencedColumnName = "one_id")
    @OneToMany(targetEntity = OneToManyMany.class, cascade = CascadeType.ALL)
    List<OneToManyMany> manyList;

    public String getOneId() {
        return oneId;
    }

    public void setOneId(String oneId) {
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

    public List<OneToManyMany> getManyList() {
        return manyList;
    }

    public void setManyList(List<OneToManyMany> manyList) {
        this.manyList = manyList;
    }
}

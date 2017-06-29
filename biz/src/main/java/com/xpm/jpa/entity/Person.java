package com.xpm.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xupingmao on 2017/5/4.
 */
@Entity
// SINGLE_TABLE 共用一张表 增加了一个字段DTYPE标识类型，包含所有子类的属性
// TABLE_PER_CLASS 每个类单独一张表，没有DTYPE类型
// JOINED 增加了一个字段DTYPE标识类型, 子类的属性在新表中
@Inheritance(strategy = InheritanceType.JOINED)
// DiscriminatorColumn可以指定存储类型的字段名称，默认是DTYPE
// DTYPE类型是varchar(31) 所以类名长度不能超过31
@DiscriminatorColumn()
// @DiscriminatorValue("Person") 可以指定类型的别名，否则默认使用类名
public class Person implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id=");
        sb.append(id);
        sb.append(",name=");
        sb.append(name);
        sb.append("}");
        return sb.toString();
    }
}

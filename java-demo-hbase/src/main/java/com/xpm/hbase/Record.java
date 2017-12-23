package com.xpm.hbase;

import java.io.Serializable;

/**
 * Created by xupingmao on 2017/9/25.
 */
public class Record implements Serializable {
    private String name;
    private Integer age;
    private String address;
    private long version = 0L;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("name=");
        sb.append(getName());
        sb.append(",age=");
        sb.append(getAge());
        sb.append(",address=");
        sb.append(getAddress());
        sb.append(",version=");
        sb.append(getVersion());
        sb.append('}');
        return sb.toString();
    }

}

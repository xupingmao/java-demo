package com.xpm.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by xupingmao on 2017/5/4.
 */
@Entity
public class Clas implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "name")
    private String name;

    @ManyToMany(targetEntity = Teacher.class)
    private Set<Teacher> teacherSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }
}

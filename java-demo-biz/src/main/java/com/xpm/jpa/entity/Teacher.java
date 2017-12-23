package com.xpm.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by xupingmao on 2017/5/4.
 */
@Entity
public class Teacher extends Person {

    // 自动生成关联表
    // TEACHER_CLAS
    @ManyToMany(targetEntity = Clas.class)
    private Set<Clas> clasSet;

    public void setClasSet(Set<Clas> clasSet) {
        this.clasSet = clasSet;
    }

    public Set<Clas> getClasSet() {
        return clasSet;
    }
}

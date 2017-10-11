package com.xpm.jdk;

import org.junit.Assert;

/**
 * Created by xupingmao on 2017/10/10.
 */
public class ClassCastTest {

    static class ClassA {
        private String name = "a";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static class ClassB extends ClassA {
        private int age = 10;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        ClassB b = new ClassB();
        ClassA a = b;
        System.out.println(a.getClass());
        Assert.assertEquals(a.getClass(), ClassB.class);
    }
}

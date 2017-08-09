package com.xpm.test.jdk;

import org.junit.Test;

/**
 * Created by xupingmao on 2017/8/9.
 */
public class ClassLoaderTest {


    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
        String className = "com.xpm.test.jdk.MyClass";

        Class<?> myClass = myClassLoader.loadClass(className);
        System.out.println(myClass.getName());
        Object o = myClass.newInstance();
        System.out.println(o);

        ClassLoader cl = myClassLoader;
        while (cl != null) {
            System.out.println(cl.getClass().getName());
            cl = cl.getParent();
        }
    }
}

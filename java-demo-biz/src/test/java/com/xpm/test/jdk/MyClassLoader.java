package com.xpm.test.jdk;

/**
 * Created by xupingmao on 2017/8/9.
 */
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // findClass不会主动加载
        System.out.println(String.format("findClass %s", name));
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println(String.format("loadClass %s", name));
        return super.loadClass(name);
    }
}

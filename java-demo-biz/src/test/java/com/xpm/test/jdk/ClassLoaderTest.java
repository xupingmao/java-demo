package com.xpm.test.jdk;

import com.xpm.test.LogUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by xupingmao on 2017/8/9.
 */
public class ClassLoaderTest {


    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
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

        LogUtils.printf("类加载器加载路径");
        Enumeration<URL> resources = myClassLoader.getResources(".");
        while(resources.hasMoreElements()) {
            LogUtils.printf("resource=%s", resources.nextElement());
        }
    }
}

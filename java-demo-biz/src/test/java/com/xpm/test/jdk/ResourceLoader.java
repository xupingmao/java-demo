package com.xpm.test.jdk;

import com.xpm.test.LogUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class ResourceLoader {

    @Test
    public void loadResource() {
        URL resource0 = getClass().getResource("a.txt");
        URL resource1 = getClass().getClassLoader().getResource("a.txt");
        LogUtils.printf("resource0=%s", resource0);
        LogUtils.printf("resource1=%s", resource1);

        LogUtils.printf("this.classLoader=%s", getClass().getClassLoader());
        LogUtils.printf("class.classLoader=%s", Class.class.getClassLoader());
        LogUtils.printf("classLoader.parent=%s", getClass().getClassLoader().getParent());
        LogUtils.printf("classLoader.parent.parent=%s", getClass().getClassLoader().getParent().getParent());

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("a.txt");
        Scanner sc = new Scanner(resourceAsStream);
        String next = sc.next();
        System.out.println(next);
    }
}

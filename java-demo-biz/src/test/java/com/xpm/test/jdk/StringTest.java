package com.xpm.test.jdk;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/9/25.
 */
public class StringTest {

    @Test
    public void compare() {
        String a = new String("test");
        String b = new String("test");
        Assert.assertFalse(a == b);
    }
}

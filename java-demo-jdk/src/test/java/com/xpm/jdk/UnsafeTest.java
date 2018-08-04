package com.xpm.jdk;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by xupingmao on 2017/10/13.
 */
public class UnsafeTest {

    private Logger LOG = LoggerFactory.getLogger(UnsafeTest.class);

    static final Unsafe UNSAFE;

    static {
        try {
            // Unsafe.getUnsafe() 拿不出来，进行了classloader校验，只有JDK自身能够加载
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void test() {
        long l = UNSAFE.allocateMemory(1024);
        long address = UNSAFE.getAddress(l); // 这个address不知道是什么
        LOG.info("l: {}, Address: {}", l, address);
        UNSAFE.putInt(l, 10);
        UNSAFE.putFloat(l+4, 1.5f);
        int anInt = UNSAFE.getInt(l);
        float aFloat = UNSAFE.getFloat(l+4);
        LOG.info("getInt = {}", anInt);
        LOG.info("getFloat = {}", aFloat);
        UNSAFE.freeMemory(l);
    }
}

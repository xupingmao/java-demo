package com.xpm.hbase;

import java.util.Random;

/**
 * Created by xupingmao on 2017/9/15.
 */
public class RandomUtils {

    private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private static Random random = new Random(System.currentTimeMillis());

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static Integer randomInt(int startIncluded, int endExcluded) {
        return random.nextInt(endExcluded-startIncluded) + startIncluded;
    }
}

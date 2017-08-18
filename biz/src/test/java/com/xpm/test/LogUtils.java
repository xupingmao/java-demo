package com.xpm.test;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class LogUtils {

    public static void printf(String fmt, Object...args) {
        if (!fmt.endsWith("\n")) {
            fmt = fmt + "\n";
        }
        System.out.printf(fmt, args);
    }
}

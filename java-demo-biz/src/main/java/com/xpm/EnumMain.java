package com.xpm;

/**
 * Created by xupingmao on 2017/7/26.
 */
public class EnumMain {

    enum Test {
        ONE,
        TWO,
    }

    public static void main(String[] args) {
        System.out.println(Test.ONE.toString());
    }
}

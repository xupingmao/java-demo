package com.xpm;

import java.util.UUID;

/**
 * Created by xupingmao on 2017/6/16.
 */
public class UUIDMain {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid.toString());
        }
    }
}

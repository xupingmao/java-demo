package com.xpm.test.jdk;

import org.junit.Test;

/**
 * Created by xupingmao on 2017/8/7.
 */
public class ObjectMonitorTest {

    private Object object = new Object();
    private int count = 0;

    class MyThread extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                count += 1;
                System.out.println(getName() + ",count=" + count);
                try {
                    object.notify();
                    object.wait();
                    System.out.println(getName() + ",wake up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        new MyThread().start();
        new MyThread().start();
        Thread.sleep(100);
    }
}

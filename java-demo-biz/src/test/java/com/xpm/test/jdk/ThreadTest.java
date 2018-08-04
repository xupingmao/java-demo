package com.xpm.test.jdk;

import org.junit.Test;

/**
 * Created by xupingmao on 2017/8/2.
 */
public class ThreadTest {

    class MyThread extends Thread {
        private boolean isRunning = true;
        private int count = 0;

        @Override
        public void run() {
            System.out.println(getName() + ", isDaemon:" + isDaemon());
            while (this.isRunning && count < 5) {
                count ++;
                System.out.println(this.getName()+ ",count:"+count+",priority:"+getPriority());
                Thread.yield();
            }
        }
    }

    static class WaitThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println(getName() + ", I'm going to sleep");
                Thread.sleep(   500);
                System.out.println(getName() + ", I'm awake now");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void interrupt() {
            super.interrupt();
        }

        @Override
        public void destroy() {
            super.destroy();
        }


    }

    @Test
    public void testYield() throws InterruptedException {
        new WaitThread().start();
        new WaitThread().start();
//        Thread.sleep(1000L);
//        Thread.dumpStack();
        // Junit主线程退出后其他线程也不执行了
    }

    public static void main(String[] args) throws InterruptedException {
        new WaitThread().start();
        new WaitThread().start();
//        Thread.sleep(1000L);
    }
}

package com.xpm.test.jdk;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by xupingmao on 2017/8/7.
 */
public class ProducerConsumer {

    // @see BlockingQueue

    private Queue<String> q = new ArrayDeque<String>(10);
    private Object object = new Object();

    class ProducerThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                synchronized (object) {

                    if (q.size() >= 10) {
                        try {
                            // 唤醒消费者
                            object.notify();
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        q.offer("product_" + i);
                        System.out.println(getName()+",product,"+i);
                    }
                }
            }
        }
    }

    class ConsumerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (object) {

                    if (q.isEmpty()) {
                        try {
                            // 唤醒生产者
                            object.notify();
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String poll = q.poll();
                        System.out.println(getName()+",consume,"+poll);
                    }
                }
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        new ProducerThread().start();
        new ConsumerThread().start();

        Thread.sleep(100);
    }
}

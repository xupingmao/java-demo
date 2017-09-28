package com.xpm.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by xupingmao on 2017/9/27.
 */
public class SynchronousQueueTest {

    static Logger logger = LoggerFactory.getLogger(SynchronousQueueTest.class);

    public static void main(String[] args) throws InterruptedException {
        // 没有缓冲区，只能放一个对象进去
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        new Thread() {
            @Override
            public void run() {
                try {
                    put(queue, "one");
                    put(queue, "two");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    String take = queue.take();
                    logger.info("value={}", take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        put(queue, "one");
        put(queue, "two");
    }

    private static void put(SynchronousQueue<String> queue, String one) throws InterruptedException {
        System.out.printf("Put %s:%s\n", Thread.currentThread().getName(), one);
        queue.put(one);
    }
}

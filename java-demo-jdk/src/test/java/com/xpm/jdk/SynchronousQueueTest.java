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
        // 没有缓冲区，必须有消费者才能把产品放入队列
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
                    take(queue);
                    take(queue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        put(queue, "one");
        put(queue, "two");
    }

    private static void put(SynchronousQueue<String> queue, String one) throws InterruptedException {
        String product = Thread.currentThread().getName() + "-" + one;
        logger.info("Put {}", product);
        queue.put(product);
    }

    private static String take(SynchronousQueue<String> queue) throws InterruptedException {
        String value = queue.take();
        logger.info("Take {}", value);
        return value;
    }
}

package com.xpm.test.jdk;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xupingmao on 2017/9/7.
 */
public class LockTest {

    ReentrantLock lock = new ReentrantLock();

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    class ReentrantLockTest extends Thread {
        int a = 0;

        public void run() {
            try {
                lock.lock();
                this.a += 10; // 在这里设断点
                this.a -= 10;
                System.out.println(this.getName()+ ":" + this.a);
            } finally {
                lock.unlock();
            }
        }
    }

    class SynchronizedTest extends Thread {
        int a = 0;

        public void run() {
            synchronized (SynchronizedTest.class) {
                this.a += 10; // 这里设断点
                this.a -= 10;
                System.out.println(this.getName()+ ":" + this.a);
            }
        }
    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.submit(new ReentrantLockTest());
        }
        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void testSynchronized() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.submit(new SynchronizedTest());
        }
        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS);
    }

}

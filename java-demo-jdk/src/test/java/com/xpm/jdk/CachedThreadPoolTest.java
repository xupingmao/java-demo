package com.xpm.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xupingmao on 2017/9/27.
 */
public class CachedThreadPoolTest {

    static Logger logger = LoggerFactory.getLogger(CachedThreadPoolTest.class);

    public static void main(String[] args) {
        logger.info("start");
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Runnable 1");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Runnable 2");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

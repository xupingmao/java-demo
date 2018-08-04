package com.xpm.redis.test;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description here
 *
 * @author pingmao.xpm
 * @version 1.0
 * @since 2018/6/14
 */
public class RedisLockWithSetTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void setNx() throws InterruptedException {
        final String lockKey = "lock_key_001";
        List<Thread> threadList = Lists.newArrayList();
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try (Jedis jedis = new Jedis()) {
                        String setResult = jedis.set(lockKey, UUID.randomUUID().toString(), "nx", "ex", 100);
                        logger.info("setResult = {}", setResult);
                        if ("OK".equals(setResult)) {
                            counter.getAndIncrement();
                            logger.info("I got the lock!!!");
                            logger.info("start to work");
                            sleep(500);
                            logger.info("release lock");
                            jedis.del(lockKey);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Assert.assertEquals(1, counter.get());
    }

}

package com.xpm.redis.test;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisDataException;

import java.io.IOException;
import java.util.List;

/**
 * Redis测试
 * Created by xupingmao on 2017/12/23.
 */
public class RedisTest {

    public void updateValue(String key, String value) {
        try (Jedis jedis = new Jedis()) {
            jedis.set(key, value);
            jedis.close();
        }
    }

    @Test
    public void watchAndUpdateOK() throws InterruptedException, IOException {
        try (Jedis jedis = new Jedis()) {
            jedis.watch("name");
            Transaction transaction = jedis.multi();
            Response<String> setResponse = transaction.set("name", "redis-test");
            transaction.exec();
            boolean success = false;
            try {
                // do your job
                String setResult = setResponse.get();
                System.out.println("Update successfully");
                success = true;
            } catch (JedisDataException ex) {
                // rollback your work
                System.out.println("Update failed");
                success = false;
            }

            Assert.assertTrue(success);
        }
    }

    @Test
    public void watchAndUpdateFail() throws InterruptedException, IOException {
        try (Jedis jedis = new Jedis()) {
            jedis.watch("name");
            updateValue("name", "no");
            Transaction transaction = jedis.multi();
            Response<String> setResponse = transaction.set("name", "redis-test");
            transaction.exec();

            boolean success = false;
            try {
                // do your job
                String setResult = setResponse.get();
                System.out.println("Update successfully");
                success = true;
            } catch (JedisDataException ex) {
                // rollback your work
                System.out.println("Update failed");
                success = false;
            }
            Assert.assertFalse(success);
        }
    }
}

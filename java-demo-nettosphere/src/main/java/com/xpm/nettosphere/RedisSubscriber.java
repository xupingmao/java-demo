package com.xpm.nettosphere;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by xupingmao on 2017/8/29.
 */
public class RedisSubscriber {

    private Jedis jedis = new Jedis();

    public void subscribe() {
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
            }
        }, "hello");
    }

}

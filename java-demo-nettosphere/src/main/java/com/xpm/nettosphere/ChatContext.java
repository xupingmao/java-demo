package com.xpm.nettosphere;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by xupingmao on 2017/8/30.
 */
public class ChatContext {

    private Jedis jedis;
    private JedisPubSub jedisPubSub;
    private String target;
    private String userId;

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public JedisPubSub getJedisPubSub() {
        return jedisPubSub;
    }

    public void setJedisPubSub(JedisPubSub jedisPubSub) {
        this.jedisPubSub = jedisPubSub;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

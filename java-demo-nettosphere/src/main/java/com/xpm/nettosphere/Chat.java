package com.xpm.nettosphere;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Maps;
import com.sun.jmx.snmp.tasks.ThreadService;
import org.atmosphere.config.service.*;
import org.atmosphere.cpr.*;
import org.atmosphere.handler.OnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@ManagedService(path="/chat")
public class Chat {
    private final Logger logger;

    private Map<String, ChatContext> chatContextMap = Maps.newConcurrentMap();
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private Jedis sendJedis = newJedis();

    public Chat()
    {
        this.logger = LoggerFactory.getLogger(Chat.class);
    }

    private Jedis newJedis() {
        return new Jedis();
    }

    public String buildId(String userId, String targetId) {
        userId = fixNull(userId);
        targetId = fixNull(targetId);
        String [] userIds = new String[] {userId, targetId};
        Arrays.sort(userIds);
        return userIds[0] + "-" + userIds[1];
    }

    private String fixNull(String targetId) {
        if (targetId == null) {
            return "null";
        }
        return targetId;
    }

    private JedisPubSub getPubsub(String userId, String targetId) {
        String id = buildId(userId, targetId);
        ChatContext context = chatContextMap.get(id);
        if (context != null) {
            return context.getJedisPubSub();
        }
        return null;
    }

    private Jedis getJedis(String channelKey) {
        ChatContext context = chatContextMap.get(channelKey);
        if (context != null) {
            return context.getJedis();
        }
        return null;
    }

    @Get
    public void onOpen(final AtmosphereResource r) {
        String target = r.getRequest().getParameter("target");
        String userId = getUserId(r.getRequest());
//        String userId = r.getRequest().getRemoteAddr();
        // 开启一个pubsub
        logger.info("{} want to talk to {}", userId, target);
        r.write("Server Time:" + new Date());

        if (chatContextMap.get(userId) == null) {
            chatContextMap.put(userId, newContext());
        }

        ChatContext context = chatContextMap.get(userId);
        Jedis jedis = context.getJedis();
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                logger.info("receive message {} from {}", message, channel);
                r.write(message);
            }
        };
        context.setJedisPubSub(jedisPubSub);
        String channelKey = buildChannelKey(r.getRequest());
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(jedisPubSub, channelKey);
            }
        });
    }

    private ChatContext newContext() {
        ChatContext context = new ChatContext();
        context.setJedis(newJedis());
        return context;
    }

    @Message
    public void onMessage(AtmosphereResource res, String message) {
        logger.info("receive Message: {}", message);

        String channel = buildChannelKey(res.getRequest());
        // TODO 还需要记录离线消息
        if (sendJedis != null) {
            String userId = getUserId(res.getRequest());
            sendJedis.publish(channel, String.format("%s says %s", userId, message));
        }
    }

    @Disconnect
    public void onClose(AtmosphereResourceEvent event) {
        logger.info("close connection {}", event.getResource().uuid());
        String userId = getUserId(event.getResource().getRequest());
        ChatContext context = chatContextMap.get(userId);
        if (context != null && context.getJedisPubSub() != null) {
            context.getJedisPubSub().unsubscribe();
            context.setJedisPubSub(null);
        }
    }

    private String getUserId(AtmosphereRequest request) {
        return request.getParameter("userId");
    }

    private String buildChannelKey(AtmosphereRequest request) {
//        String userId = request.getParameter("userId");
        String userId = request.getParameter("userId");
        String target = request.getParameter("target");
        return buildId(userId, target);
    }


}

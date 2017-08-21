package com.xpm.nettosphere;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Date;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Post;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedService(path="/chat")
public class Chat
{
    private final Logger logger;

    public Chat()
    {
        this.logger = LoggerFactory.getLogger(Chat.class);
    }

    @Get
    public void onOpen(final AtmosphereResource r)
    {
        r.addEventListener(new AtmosphereResourceEventListenerAdapter()
        {
            public void onSuspend(AtmosphereResourceEvent event)
            {
                Chat.this.logger.info("User {} connected.", r.uuid());
            }

            public void onDisconnect(AtmosphereResourceEvent event)
            {
                if (event.isCancelled()) {
                    Chat.this.logger.info("User {} unexpectedly disconnected", r.uuid());
                } else if (event.isClosedByClient()) {
                    Chat.this.logger.info("User {} closed the connection", r.uuid());
                }
            }
        });

        new Thread () {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        r.write("Greetings from server " + i);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Message
    public String onMessage(String message)
            throws IOException
    {
        logger.info("receive Message: {}", message);
        return message;
    }
}

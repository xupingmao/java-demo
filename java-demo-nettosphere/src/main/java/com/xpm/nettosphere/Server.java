package com.xpm.nettosphere;

import org.atmosphere.cpr.*;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Handler;
import org.atmosphere.nettosphere.Nettosphere;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.atmosphere.websocket.WebSocketProcessor;
import org.atmosphere.websocket.WebSocketProtocol;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by xupingmao on 2017/8/19.
 */
public class Server {

    public static void main(String[] args) {
        Nettosphere server = new Nettosphere.Builder().config(
                new Config.Builder()
                        .host("127.0.0.1")
                        .port(8089)
                        .resource(Chat.class)
                        .build())
                .build();
        server.start();
    }

}

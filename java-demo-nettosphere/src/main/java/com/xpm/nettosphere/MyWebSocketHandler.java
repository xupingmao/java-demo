/*
 * Copyright 2012 Nishant Chandra <nishant.chandra@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.xpm.nettosphere;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.atmosphere.cpr.*;
import org.atmosphere.pool.PoolableBroadcasterFactory;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.atmosphere.websocket.WebSocketProcessor;
import org.atmosphere.websocket.WebSocketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple server to client websocket based streaming handler. The server periodically
 * broadcasts current time to all the connected clients. The server also echoes back any message
 * the client sends to it.
 *
 * @author nishant
 *
 */
public class MyWebSocketHandler implements WebSocketProtocol {

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    private AtmosphereResource r;
    private final ConcurrentHashMap<String, Future<?>> futures = new ConcurrentHashMap<String, Future<?>>();
    private DefaultBroadcasterFactory broadcasterFactory = new PoolableBroadcasterFactory();

    private Broadcaster broadcaster;

    @Override
    public void configure(AtmosphereConfig config) {
        logger.info(String.format("configure(): {Config: %s}", config));
    }

    @Override
    public void onOpen(WebSocket webSocket) {

        logger.info(String.format(
                "onOpen(): {IP: %s} : {Port: %s}",
                webSocket.resource().getRequest().getRemoteAddr(),
                webSocket.resource().getRequest().getRemotePort()));

        // Accept the handshake by suspending the response.
        r = (AtmosphereResource) webSocket.resource();
        // 保持连接
        r.suspend(-1);
        r.write("Time " + new Date());
//
//        final Broadcaster b = lookupBroadcaster(((AtmosphereRequest) r.getRequest())
//                .getPathInfo());
//        r.setBroadcaster(b);
//        r.addEventListener(new WebSocketEventListenerAdapter());
//        r.setSerializer(new MyMessageSerializer());
//
//        //Setup a broadcaster to periodically send current time to all connected clients.
//        if (b.getAtmosphereResources().size() == 0) {
//            if (!futures.containsKey(((AtmosphereRequest) r.getRequest())
//                    .getPathInfo())) {
//
//                if(logger.isDebugEnabled())
//                    logger.debug("Broadcaster initialized.");
//
//                final Future<?> future = b.scheduleFixedBroadcast(
//                        new Callable<String>() {
//
//                            final String path = ((AtmosphereRequest) r.getRequest()).getPathInfo();
//
//                            public String call() throws Exception {
//                                //Is there a more elegant way?
//                                if(b.getAtmosphereResources().size() == 0) {
//                                    logger.info("No resources attached. Destroying broadcaster.");
//                                    futures.remove(path);
//                                    b.destroy();
//                                    return null;
//                                }
//
//                                return new Date().toString();
//                            }
//                        }, 2, TimeUnit.SECONDS);
//                logger.info("put(%s,%s)", r.getRequest().getPathInfo(), future);
//                futures.put(((AtmosphereRequest) r.getRequest()).getPathInfo(), future);
//            }
//        }
//
//        r.suspend(-1);
//        r.write("Hello,World");
    }

    @Override
    public List<AtmosphereRequest> onMessage(WebSocket webSocket, String message) {

        logger.info(String.format(
                "onMessage(): {IP: %s} : {Port: %s} : {Message: %s}",
                webSocket.resource().getRequest().getRemoteAddr(),
                webSocket.resource().getRequest().getRemotePort(),
                new String(message)));

        Broadcaster b = lookupBroadcaster(((AtmosphereRequest) r.getRequest())
                .getPathInfo());

        if (message != null) {
            //Do something with the message. Here, simply echo the message back.
            b.broadcast("Server said: " + message);
        }

        // Do not dispatch to another Container like Jersey
        return null;
    }

    @Override
    public List<AtmosphereRequest> onMessage(WebSocket webSocket,
                                             byte[] message, int offset, int length) {
        logger.info(String.format(
                "onMessage(): {IP: %s} : {Port: %s} : {Message: %s}", webSocket
                        .resource().getRequest().getRemoteAddr(), webSocket
                        .resource().getRequest().getRemotePort(),
                new String(message)));
        return null;
    }

    private Broadcaster lookupBroadcaster(String pathInfo) {
//        String[] decodedPath = pathInfo.split("/");
        return broadcaster;
//        Broadcaster b = broadcasterFactory.get(decodedPath[decodedPath.length - 1]);
//        return b;
    }

    @Override
    public void onClose(WebSocket webSocket) {
//        logger.info(String.format("onClose(): {IP: %s} : {Port: %s}",
//                webSocket.resource().getRequest().getRemoteAddr(),
//                webSocket.resource().getRequest().getRemotePort()));
        webSocket.resource().resume();
    }

    @Override
    public void onError(WebSocket webSocket, WebSocketProcessor.WebSocketException ex) {
        logger.error("Trouble");
        logger.error(String.format(ex.getMessage() + " Status {%s} Message {%s}",
                webSocket.resource().getResponse().getStatus(),
                ex.response().getStatusMessage()), ex);
    }
}
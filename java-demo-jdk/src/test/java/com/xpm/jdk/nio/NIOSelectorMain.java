package com.xpm.jdk.nio;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by xupingmao on 2017/9/29.
 */
public class NIOSelectorMain {

    private static Logger LOG = LoggerFactory.getLogger(NIOSelectorMain.class);

    public static void main(String[] args) throws IOException {
        int port = 8082;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        // backlog是最大连接数
        serverSocketChannel.bind(new InetSocketAddress(port), 1024);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        LOG.info("server started at port " + port);

        while (true) {
            int select = selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next != null) {
                    boolean shouldRemove = handleInput(next, serverSocketChannel, selector);
                    if (shouldRemove) {
                        iterator.remove();
                    }
                } else {
                    LOG.info("Remove null key");
                    iterator.remove();
                }
            }
        }
    }

    private static boolean handleInput(SelectionKey key, ServerSocketChannel serverSocketChannel, Selector selector)
            throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
                SocketChannel channel = socketChannel.accept();

                if (serverSocketChannel == socketChannel) {
                    LOG.info("channel equals");
                }
                try {
                    if (channel != null) {
                        // 注册Read操作，异常
                        // channel.register(selector, SelectionKey.OP_READ);

                        // HTTP协议头最多8K
                        ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
                        int read = channel.read(buffer);
                        if (read > 0) {
                            buffer.flip();
                            String input = new String(buffer.array(), 0, buffer.remaining());
                            String request = input.split("\r\n\r\n")[0];
                            HttpServletRequest httpServletRequest = assembleRequest(request);
                            LOG.info("Receive: \n" + input);
                            LOG.info("{}", httpServletRequest);
                            String response = "HTTP/1.1 200 OK\r\n\r\nDate: " + new DateTime();
                            response += "\nMethod: " + httpServletRequest.getMethod();
                            response += "\nProtocol: " + httpServletRequest.getProtocol();
                            response += "\nQueryString: " + httpServletRequest.getQueryString();
                            buffer.clear();
                            buffer.put(response.getBytes());
                            buffer.flip();
                            channel.write(buffer);
                            channel.finishConnect();
                        }
                    } else {
                        return true;
                    }
                } finally {
                    if (channel != null) {
                        channel.close();
                    } else {
                        LOG.info("Channel is null");
                    }
                }
            }
        }
        return false;
    }

    private static String findToken(String origin, int startPos, String stopChar) {
        return origin.substring(startPos, origin.indexOf(stopChar, startPos));
    }

    private static HttpServletRequest assembleRequest(String request) {
        StringTokenizer tokenizer = new StringTokenizer(request, "\r\n\f\t ");
        String method = tokenizer.nextToken();
        String queryString = tokenizer.nextToken();
        String protocol = tokenizer.nextToken();
        MyHttpServletRequest httpServletRequest = new MyHttpServletRequest();
        httpServletRequest.setMethod(method);
        httpServletRequest.setQueryString(queryString);
        httpServletRequest.setProtocol(protocol);
        return httpServletRequest;
    }
}

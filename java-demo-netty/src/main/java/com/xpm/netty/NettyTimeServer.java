package com.xpm.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by xupingmao on 2017/10/11.
 */
public class NettyTimeServer {

    private static Logger LOGGER = LoggerFactory.getLogger(NettyTimeServer.class);

    static class TimeServerHandler extends ChannelHandlerAdapter {
        static Charset charset = Charset.forName("UTF-8");

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            String body = buf.toString(charset);
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            LOGGER.info("Remote: {}", socketAddress);
            LOGGER.info("Receive {}", body);
            String responseBodyText = "Date: " + new DateTime();
            String response = assembleResponse(responseBodyText);
            ByteBuf byteBuf = Unpooled.copiedBuffer(response.getBytes());
            ctx.writeAndFlush(byteBuf);
            // ctx.close(); // 不关闭的话浏览器会一直刷新,不然使用Keep-Alive的模式返回Content-Length
        }

        private String assembleResponse(String responseBodyText) throws UnsupportedEncodingException {
            byte[] bytes = responseBodyText.getBytes("UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1 200 OK\r\n");
            sb.append("Connection:keep-alive\r\n");
            sb.append("Content-Length:").append(bytes.length).append("\r\n");
            sb.append("\r\n");
            sb.append(responseBodyText);
            return sb.toString();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }

    }

    static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            LOGGER.info("server start at port {}", port);
            ChannelFuture future = bootstrap.bind(port).sync();
            // 等待服务端端口关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyTimeServer server = new NettyTimeServer();
        server.bind(1222);
    }
}

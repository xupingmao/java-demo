package com.xpm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by xupingmao on 2017/10/18.
 */
public class MyDecoder extends MessageToMessageDecoder<ByteBuf> {
    static Charset charset = Charset.forName("UTF-8");

    // 第二个参数是输入类型
    // out是放在下一个pipeline handler的类型
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        out.add(buf.toString(charset));
    }
}

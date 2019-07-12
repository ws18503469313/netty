package com.itmuch.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @author polunzi
     * @Date: 2019/7/9
     * @Param: ctx : netty上下文
     * @return:
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "-" + msg);
        ctx.channel().writeAndFlush("from server:"+ UUID.randomUUID());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}

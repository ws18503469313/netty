package com.itmuch.protobuf.nettyTest;

import com.itmuch.protobuf.MessageData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtoBufServerHandler extends SimpleChannelInboundHandler<MessageData> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageData msg) throws Exception {
        System.out.println(msg.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}

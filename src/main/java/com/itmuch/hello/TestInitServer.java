package com.itmuch.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Date;

public class TestInitServer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());


    }


    public static void main(String args[])  throws Exception {
        Date date1 = new Date();

        Thread.sleep(60000);
        Date date2 = new Date();

        System.out.println((date2.getTime() - date1.getTime())/1000/60f);
    }
}

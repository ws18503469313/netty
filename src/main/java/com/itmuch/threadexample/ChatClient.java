package com.itmuch.threadexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient  {


    public static void main(String args[])  throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChatClentInitalizer());

            Channel channel = bootstrap.connect("localhost", 8050).sync().channel();

            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                String msg = bf.readLine();
                if(msg != null && msg.length() != 0)
                    channel.writeAndFlush(msg + "\r\n");
            }
        }finally {
            group.shutdownGracefully();
        }
    }

}

package com.itmuch.threadexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
    public static void main(String [] args) throws Exception {
        //netty 推荐两个线程组处理
        //死循环,不断地从客户端接受连接,不做任何处理,转给workergroup
        EventLoopGroup bossgroup   = new NioEventLoopGroup();
        EventLoopGroup  workergroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //关联处理器
            bootstrap.group(bossgroup, workergroup).channel(NioServerSocketChannel.class).childHandler(new ChatServerInitalizer());

            ChannelFuture future = bootstrap.bind(9000).sync();
            future.channel().closeFuture().sync();
            System.out.println("start");
        } finally {
            bossgroup.shutdownGracefully();
            workergroup.shutdownGracefully();
        }



    }
}

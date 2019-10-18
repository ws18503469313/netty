package com.itmuch.hello;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author polunzi
 * @Date: 2019/7/4 
 */
public class TestServer {


    public static void main(String [] args) throws Exception {
        //netty 推荐两个线程组处理
        //死循环,不断地从客户端接受连接,不做任何处理,转给workergroup
        EventLoopGroup bossgroup   = new NioEventLoopGroup();
        EventLoopGroup  workergroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
                                                                                            //关联处理器
            bootstrap.group(bossgroup, workergroup).channel(NioServerSocketChannel.class).childHandler(new TestInitServer());

            ChannelFuture future = bootstrap.bind(60000).sync();
            future.channel().closeFuture().sync();
            System.out.println("start");
        } finally {
            bossgroup.shutdownGracefully();
            workergroup.shutdownGracefully();
        }



    }

    public static void count() throws Exception {
        String filename = "D:/File/calcCharNum.txt";
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
        char [] line = new char[1024];
        int A = 0;
        int a = 0;
        while(reader.read(line) != -1){
            for(int i = 0; i < 1024; i++){
                if(line[i] == 'a'){
                    a++;
                }
                if(line[i] == 'A'){
                    A++;
                }
            }
        }
        System.out.println("a: " + a);
        System.out.println("A: " + A);
    }

}


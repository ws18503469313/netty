package com.itmuch.nio.selecotr;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class NioTest1 {

    public static void main(String args[]) throws Exception{
        int [] ports  = new int[5];
        for (int i = 50000, j = 0; j < ports.length; i++){
            ports[j++] = i;
        }
        Selector selector = Selector.open();

        System.out.println(SelectorProvider.provider().getClass());
        for (int i = 0; i < ports.length; i++){

            //建立通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);

            serverSocket.bind(address);

            //注册,
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            System.out.println("监听端口:" + ports[i]);
        }
        while(true){
            int num = selector.select();
            System.out.println("num:" + num);

            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> it = set.iterator();

            while (it.hasNext()){
                SelectionKey key = it.next();
                if(key.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);

                    System.out.println("获取到客户端连接:" + channel);
                    it.remove();
                }else if(key.isReadable()){
                    SocketChannel channel = (SocketChannel)key.channel();
                    int byteRead = 0;

                    while(true){
                        ByteBuffer buffer = ByteBuffer.allocate(64);
                        buffer.clear();
                        int read = channel.read(buffer);

                        if(read <= 0){
                            break;
                        }
                        buffer.flip();

                        channel.write(buffer);
                        byteRead += read;


                    }

                    System.out.println("读取来自客户端:" + channel + "的"+ byteRead );
                    it.remove();
                }

            }
        }
    }
}

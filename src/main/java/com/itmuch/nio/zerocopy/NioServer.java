package com.itmuch.nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServer {

    public static void main(String args[]) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8899));

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while(true){

            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readLength = 0, total = 0;

            while(-1 != readLength){
                readLength = socketChannel.read(buffer);
                System.out.println(readLength);
                total += readLength;
                buffer.rewind();
            }

            System.out.println("读取:"+total);


        }
    }
}

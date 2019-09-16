package com.itmuch.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String args[]) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        socketChannel.configureBlocking(true);

        String fileName = "D:/install_package/Anaconda3-2019.03-Windows-x86_64.exe";

        FileChannel channel = new FileInputStream(fileName).getChannel();
        long startTime = System.currentTimeMillis();
//        System.out.println(channel.size());
        long total = channel.transferTo(0, channel.size(), socketChannel);
        System.out.println("发送:"+total + "耗时:" +(System.currentTimeMillis() - startTime));

        channel.close();

        socketChannel.close();

    }
}

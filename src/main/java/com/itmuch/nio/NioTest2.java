package com.itmuch.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest2 {

    public static void main(String args[]) throws Exception{
        FileInputStream in = new FileInputStream(new File("niotest.txt"));
        FileChannel channel = in.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(in.available());
        channel.read(buffer);

        buffer.flip();

        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.println((char)b);
        }
        channel.close();
        in.close();
    }
}

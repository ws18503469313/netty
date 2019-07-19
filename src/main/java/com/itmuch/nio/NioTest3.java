package com.itmuch.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest3 {

    public static void main(String args[]) throws Exception{
        FileOutputStream out = new FileOutputStream(new File("niotest3.txt"));
        FileChannel channel = out.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);

        byte [] msg = "hello world nio test3".getBytes();
        for (int i = 0; i < msg.length; i++){
            buffer.put(msg[i]);
        }
        buffer.flip();
        channel.write(buffer);
        channel.close();
        out.close();

    }
}

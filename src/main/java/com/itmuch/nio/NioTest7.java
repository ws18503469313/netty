package com.itmuch.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class NioTest7 {

    public static void main1(String args[]) throws Exception{
        RandomAccessFile file = new RandomAccessFile("niotest7.txt", "rw");

        FileChannel channel = file.getChannel();

        MappedByteBuffer buffer  =  channel.map(FileChannel.MapMode.READ_WRITE,0, 5 );

        buffer.put(0, (byte)'a');
        buffer.put(0, (byte)'b');

        file.close();
    }


    public static void main(String args[]) throws Exception{
        RandomAccessFile file = new RandomAccessFile("niotest8.txt", "rw");

        FileChannel channel = file.getChannel();

        FileLock lock = channel.lock(3, 6, true);

    }

}

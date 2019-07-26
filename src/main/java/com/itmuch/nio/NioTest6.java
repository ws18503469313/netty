package com.itmuch.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest6 {
    public static void main(String args[]) throws Exception{
        FileInputStream in = new FileInputStream(new File("niotestread.txt"));
        FileOutputStream out = new FileOutputStream(new File("niotestwrite.txt"));

        FileChannel inchannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while(true){
           buffer.clear();
            int read = inchannel.read(buffer);
            System.out.println("read:"+ read);
            if(-1 == read){
                break;
            }
            buffer.putInt(2);
            buffer.flip();
            outChannel.write(buffer);
            buffer.flip();
        }
        inchannel.close();
        outChannel.close();
        in.close();
        out.close();
    }
}

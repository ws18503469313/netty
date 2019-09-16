package com.itmuch.nio;

import java.nio.ByteBuffer;

public class NioTest5 {

    public static void main1(String args[]) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(15);
        buffer.putLong(20000L);
        buffer.putDouble(5.0251);
        buffer.putChar('a');
        buffer.putShort((short)2);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }


    public static void main(String args[]) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++){
            buffer.put((byte)i);
        }
        buffer.position(2);
        buffer.limit(6);

        ByteBuffer slice = buffer.slice();

        for (int i = 0; i < slice.capacity(); i++){
            byte b = slice.get(i);
            b *=2;
            slice.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

    public static void main3(String args[]) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass());
        for (int i = 0; i < buffer.capacity(); i++){
            buffer.put((byte)i);
        }
        ByteBuffer readOnly = buffer.asReadOnlyBuffer();
        System.out.println(readOnly.getClass());


    }
}

package com.itmuch.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest1 {


    public static void main(String args[]) throws Exception{
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < 5; i++){
            int num = new SecureRandom().nextInt(20);
            buffer.put(num);
        }
        System.out.println("before filp:"+buffer.limit());
        buffer.flip();
        System.out.println("after flip :"+buffer.limit());
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
            System.out.println("position:" + buffer.position());
            System.out.println("limit:" + buffer.limit());
        }
        /**
         * 再次反转 limit位置不变,limit位置还在6上
         */
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++){
            int num = new SecureRandom().nextInt(20);
            System.out.println(i+"个");
            buffer.put(num);
        }
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
        buffer.clear();
        System.out.println("after clear position:"+ buffer.position());
        System.out.println("after clear limit:"+ buffer.limit());
        System.out.println("after clear capacity:"+ buffer.capacity());
        System.out.println("get by index"+buffer.get(2));
    }
}

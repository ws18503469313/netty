package com.itmuch.nio.selecotr.fullexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    public static void main(String args[]) throws Exception{

        try{

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));
            while(true){
                int num = selector.select();
                Set<SelectionKey> set = selector.selectedKeys();
                for (SelectionKey key : set){
                    SocketChannel channel = (SocketChannel)key.channel();
//
                    if(channel.isConnectionPending()){
                        channel.finishConnect();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put((LocalDateTime.now() + "连接成功").getBytes());
                        buffer.flip();

                        channel.write(buffer);
                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());

                        executorService.submit( ()->{
                            while (true) {

                                try{
                                    buffer.clear();

                                    InputStreamReader in = new InputStreamReader(System.in);

                                    BufferedReader reader = new BufferedReader(in);
                                    String msg = reader.readLine();
                                    buffer.put(msg.getBytes());
                                    buffer.flip();
                                    channel.write(buffer);

                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }finally{

                                }
                            }
                        });
                        channel.register(selector, SelectionKey.OP_READ);

                    }else if(key.isReadable()){
                        SocketChannel client = (SocketChannel)key.channel();
                        ByteBuffer read = ByteBuffer.allocate(1024);
                        int count = client.read(read);
                        if(count > 0){
                            String msg = new String(read.array(), 0 , count);
                            System.out.println(msg);
                        }
                    }
                }
                set.clear();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{

        }
    }
}

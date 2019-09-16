package com.itmuch.nio.selecotr.fullexample;

import com.google.common.collect.Maps;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Server {

    public static void main(String args[]) throws Exception{

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置未非阻塞
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();

        InetSocketAddress address = new InetSocketAddress(8899);

        serverSocket.bind(address);

        Selector selector = Selector.open();
        //把服务注册到selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        Map<String, SocketChannel> clientMap = Maps.newHashMap();
        Charset charset = Charset.forName("UTF-8");
        //网络程序都是死循环,都不会退出
        while (true){
            System.out.println("work dead loop  ");
            try{
                //返回事件关注的数量
                int num = selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for(SelectionKey key : keys){
                    //判断当前获取到的key的类型
                    final SocketChannel client;
                    try{
                        if(key.isAcceptable()){
                            //只有ServerSocketChannel 注册未op.ACCEPT状态,所以能强转
                            ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                            client = channel.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            //将连接保存起来
                            clientMap.put("["+UUID.randomUUID().toString()+"]", client);
                        }else if(key.isReadable()){
                            //只有SocketChannel 注册未op.read状态,所以能强转
                            client = (SocketChannel)key.channel();
                            StringBuilder content = new StringBuilder();
                            int count = 0;
                            ByteBuffer readBuf = ByteBuffer.allocate(512);
                            count = client.read(readBuf);
                            String rcvmsg = null;
                            if(count > 0){
                                readBuf.flip();

                                rcvmsg = String.valueOf(charset.decode(readBuf).array());
                                System.out.println(client + ":" + rcvmsg);
                            }
                            for (String sendKey : clientMap.keySet()){
                                readBuf.clear();
                                if(client == clientMap.get(sendKey)){

                                    readBuf.put(("自己的消息-" + sendKey + "-" + client +":" + rcvmsg).getBytes() );
                                    readBuf.flip();
                                    client.write(readBuf);
                                }else{
                                    readBuf.put(("别人的消息-" + sendKey + "-" + clientMap.get(sendKey) +":" + rcvmsg).getBytes() );
                                    readBuf.flip();
                                    clientMap.get(sendKey).write(readBuf);
                                }

                            }
                        }

                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{

                    }
                }

                keys.clear();
            }catch(Exception ex){
                ex.printStackTrace();
            }finally{

            }

        }


















    }
}

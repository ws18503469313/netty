package com.itmuch.nio.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServer {


    public static void main(String args[]) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);

        while(true){

            Socket socket = serverSocket.accept();

            DataInputStream in = new DataInputStream(socket.getInputStream());
            byte [] buf = new byte[1024];
            int len = 0, readLength = 0;

            while(true){
                if( (len = in.read(buf)) != -1){
                    try{
                        readLength += len;

                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{

                    }
                }else{
                    break;
                }

            }
            System.out.println("接受:" + readLength);
        }
    }
}

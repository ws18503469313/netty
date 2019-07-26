package com.itmuch.nio.selecotr;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tranditional {

    public static void main1(String args[]) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Socket socket = serverSocket.accept();

        while (true){
            executor.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }


    }


    public static void main(String args[]) throws Exception{
        System.out.println(1 >> 4);
    }
}

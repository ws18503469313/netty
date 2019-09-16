package com.itmuch.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class OldClient {

    public static void main(String args[]) throws Exception{
        Socket socket = new Socket("localhost", 8899);
        String fileName = "D:/install_package/Anaconda3-2019.03-Windows-x86_64.exe";
        InputStream in = new FileInputStream(fileName);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        byte [] buf = new byte[1024];
        int len , total = 0;
        long startTime = System.currentTimeMillis();

        while((len = in.read(buf)) != -1){
            total += len;
            out.write(buf);
        }
        System.out.println("发送{}"+ total);
        System.out.println("耗时:"+ (System.currentTimeMillis() - startTime));

        out.close();
        in.close();
        socket.close();

    }
}

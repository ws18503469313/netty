package com.itmuch.proto.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer  {

    private Server server;

    private void start()  throws Exception {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();
        System.out.println("服务器启动");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                GrpcServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private  void awaitTermition() throws Exception {
        if(server != null){
            this.server.awaitTermination();
        }
    }


    public static void main(String args[]) throws Exception {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermition();
    }
}

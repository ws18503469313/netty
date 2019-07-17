package com.itmuch.proto.grpc;

import com.itmuch.proto.StreamRequset;
import com.itmuch.proto.StreamResponse;
import com.itmuch.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

public class BinaryGrpcClient {


    public static void main(String args[]) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceStub serviceStub = StudentServiceGrpc.newStub(channel);
        StreamObserver<StreamRequset> requsetStreamObserver = serviceStub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println(value.getResponseinfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("dome");
            }
        });
        for (int i = 0; i < 10 ; ++i){
            requsetStreamObserver.onNext(StreamRequset.newBuilder().setRequestinfo(LocalDateTime.now().toString()).build());
            Thread.sleep(2000);
        }
        Thread.sleep(5000);
    }
}

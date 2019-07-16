package com.itmuch.proto.grpc;

import com.itmuch.proto.MyRequest;
import com.itmuch.proto.MyResponse;
import com.itmuch.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {


    /**
     * 会建立一个tcp scoket 连接
     * @param args
     * @throws Exception
     */
    public static void main(String args[])  throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub =  StudentServiceGrpc.newBlockingStub(channel);
        MyResponse response = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("xiaoniao").build());

        System.out.println(response.getRealname());

    }
}

package com.itmuch.proto.grpc;

import com.itmuch.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

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
        System.out.println("--------------------------");
        Iterator<StudentResponse> it = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
        while (it.hasNext()){
            StudentResponse studentResponse = it.next();
            System.out.println(studentResponse.toString());
        }
    }
}

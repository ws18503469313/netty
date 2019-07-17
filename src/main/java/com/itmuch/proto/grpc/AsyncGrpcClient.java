package com.itmuch.proto.grpc;

import com.itmuch.proto.StudentRequest;
import com.itmuch.proto.StudentResponse;
import com.itmuch.proto.StudentResponseList;
import com.itmuch.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class AsyncGrpcClient {

    /**
     * 会建立一个tcp scoket 连接
     * 客户端以流的方式向服务端发送request,必须的异步的方式
     * @param args
     * @throws Exception
     */
    public static void main(String args[])  throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();
        StudentServiceGrpc.StudentServiceStub serviceStub = StudentServiceGrpc.newStub(channel);
        StreamObserver<StudentResponseList> observer = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                for (StudentResponse response : value.getStudentResponseList()){
                    System.out.println(response.toString());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("done");
            }
        };
        StreamObserver<StudentRequest> requestStreamObserver = serviceStub.getStudentsWapperByAge(observer);

        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(22).build());

        requestStreamObserver.onCompleted();
        //异步的方式发送数据,数据开始发送的时候,开始执行下边的操作,主方法没执行完,就退出程序,导致数据发送失败
        Thread.sleep(5000);
    }

}

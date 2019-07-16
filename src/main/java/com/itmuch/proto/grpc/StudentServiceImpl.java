package com.itmuch.proto.grpc;

import com.itmuch.proto.MyRequest;
import com.itmuch.proto.MyResponse;
import com.itmuch.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    /**
     * 结果通过 responseObserver 返回
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("rcv message from client");
        //返回结果
        responseObserver.onNext(MyResponse.newBuilder().setRealname("polunzi").build());
        //表示方法执行结束
        responseObserver.onCompleted();
    }


}

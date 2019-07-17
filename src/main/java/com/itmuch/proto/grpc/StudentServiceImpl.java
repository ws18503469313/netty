package com.itmuch.proto.grpc;

import com.itmuch.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

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

    /**
     * 返回多个对象流
     * @param request
     * @param responseObserver
     */
    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {

        System.out.println("getStudentsByAge");
        System.out.println(request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan").setAge(23).setCity("beijing").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan2").setAge(23).setCity("beijing").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("zhangsan3").setAge(23).setCity("beijing").build());
        responseObserver.onCompleted();
    }


    @Override
    public StreamObserver<StudentRequest> getStudentsWapperByAge(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println(value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {

                StudentResponse response1 = StudentResponse.newBuilder().setName("zhangsan3").setAge(23).setCity("beijing").build();
                StudentResponse response2 = StudentResponse.newBuilder().setName("zhangsan3").setAge(23).setCity("beijing").build();
                StudentResponseList list = StudentResponseList.newBuilder().addStudentResponse(response1).addStudentResponse(response2).build();
                responseObserver.onNext(list);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamRequset> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequset>() {
            @Override
            public void onNext(StreamRequset value) {
                System.out.println(value.getRequestinfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseinfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}

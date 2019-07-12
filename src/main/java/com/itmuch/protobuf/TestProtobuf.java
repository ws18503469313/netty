package com.itmuch.protobuf;

public class TestProtobuf {

    public static void main(String args[])  throws Exception {
        DateInfo.Student student = DateInfo.Student.newBuilder()
                                    .setAge(1).setAddress("beijing").setName("polunzi")  .build();
        byte [] student2bytearr = student.toByteArray();

        DateInfo.Student s2 = DateInfo.Student.parseFrom(student2bytearr);

        System.out.println(s2.toString());


    }
}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package com.itmuch.proto;

public final class StudentProto {
  private StudentProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_MyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_MyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_MyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_MyResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_StudentRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_StudentRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_StudentResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_StudentResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_StudentResponseList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_StudentResponseList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_StreamRequset_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_StreamRequset_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_itmuch_proto_StreamResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_itmuch_proto_StreamResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\rStudent.proto\022\020com.itmuch.proto\"\035\n\tMyR" +
      "equest\022\020\n\010username\030\001 \001(\t\"\036\n\nMyResponse\022\020" +
      "\n\010realname\030\001 \001(\t\"\035\n\016StudentRequest\022\013\n\003ag" +
      "e\030\001 \001(\005\":\n\017StudentResponse\022\014\n\004name\030\001 \001(\t" +
      "\022\013\n\003age\030\002 \001(\005\022\014\n\004city\030\003 \001(\t\"Q\n\023StudentRe" +
      "sponseList\022:\n\017studentResponse\030\001 \003(\0132!.co" +
      "m.itmuch.proto.StudentResponse\"$\n\rStream" +
      "Requset\022\023\n\013requestinfo\030\001 \001(\t\"&\n\016StreamRe" +
      "sponse\022\024\n\014responseinfo\030\001 \001(\t2\375\002\n\016Student" +
      "Service\022T\n\025getRealNameByUsername\022\033.com.i" +
      "tmuch.proto.MyRequest\032\034.com.itmuch.proto" +
      ".MyResponse\"\000\022[\n\020getStudentsByAge\022 .com." +
      "itmuch.proto.StudentRequest\032!.com.itmuch" +
      ".proto.StudentResponse\"\0000\001\022e\n\026getStudent" +
      "sWapperByAge\022 .com.itmuch.proto.StudentR" +
      "equest\032%.com.itmuch.proto.StudentRespons" +
      "eList\"\000(\001\022Q\n\006BiTalk\022\037.com.itmuch.proto.S" +
      "treamRequset\032 .com.itmuch.proto.StreamRe" +
      "sponse\"\000(\0010\001B\"\n\020com.itmuch.protoB\014Studen" +
      "tProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_itmuch_proto_MyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_itmuch_proto_MyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_MyRequest_descriptor,
        new String[] { "Username", });
    internal_static_com_itmuch_proto_MyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_itmuch_proto_MyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_MyResponse_descriptor,
        new String[] { "Realname", });
    internal_static_com_itmuch_proto_StudentRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_itmuch_proto_StudentRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_StudentRequest_descriptor,
        new String[] { "Age", });
    internal_static_com_itmuch_proto_StudentResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_itmuch_proto_StudentResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_StudentResponse_descriptor,
        new String[] { "Name", "Age", "City", });
    internal_static_com_itmuch_proto_StudentResponseList_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_itmuch_proto_StudentResponseList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_StudentResponseList_descriptor,
        new String[] { "StudentResponse", });
    internal_static_com_itmuch_proto_StreamRequset_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_com_itmuch_proto_StreamRequset_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_StreamRequset_descriptor,
        new String[] { "Requestinfo", });
    internal_static_com_itmuch_proto_StreamResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_com_itmuch_proto_StreamResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_itmuch_proto_StreamResponse_descriptor,
        new String[] { "Responseinfo", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
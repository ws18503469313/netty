package com.itmuch.protobuf.nettyTest;

import com.itmuch.protobuf.MessageData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ProtobufClientHandler extends SimpleChannelInboundHandler<MessageData.Content> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageData.Content msg) throws Exception {

    }

    //    @Override
////    public void channelActive(ChannelHandlerContext ctx) throws Exception {
////        DateInfo.Student student = DateInfo.Student.newBuilder()
////                .setAge(1).setAddress("beijing").setName("polunzi")  .build();
////        ctx.channel().writeAndFlush(student);
////    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MessageData.Content content = null;
        Random ran = new Random();
        int num  = ran.nextInt(4);
        if(num == 0){
            content = MessageData.Content.newBuilder().setDateType(MessageData.Content.DateType.PersonType)
                    .setPerson(MessageData.Person.newBuilder().setName("polunzi").setAge(24).setAddress("shanxi").build()).build();
        }else if(num == 1){
            content = MessageData.Content.newBuilder().setDateType(MessageData.Content.DateType.DogType)
                    .setDog(MessageData.Dog.newBuilder().setName("xiaogou").setAge(2).build()).build();
        }else{
            content = MessageData.Content.newBuilder().setDateType(MessageData.Content.DateType.CatType)
                    .setCat(MessageData.Cat.newBuilder().setName("xiaomao").setAge(2).build()).build();
        }
        System.out.println(content.toString());
        ctx.channel().writeAndFlush(content);
    }
}

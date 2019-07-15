package com.itmuch.protobuf.nettyTest;

import com.itmuch.protobuf.MessageData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtoBufServerHandler extends SimpleChannelInboundHandler<MessageData.Content> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageData.Content msg) throws Exception {
       MessageData.Content.DateType dateType  = msg.getDateType();
       if(dateType == MessageData.Content.DateType.PersonType){
           MessageData.Person person = msg.getPerson();
           System.out.println(person.getName());
           System.out.println(person.hasAge());
           System.out.println(person.hasAddress());
       }else if(dateType == MessageData.Content.DateType.CatType){
           MessageData.Cat cat = msg.getCat();
           System.out.println(cat.getAge());
           System.out.println(cat.getName());
       }else{
           MessageData.Dog dog = msg.getDog();
           System.out.println(dog.getAge());
           System.out.println(dog.getName());
       }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}

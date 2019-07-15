package com.itmuch.thrift.thrift;

import com.itmuch.thrift.PersonService;
import com.itmuch.thrift.PersonServiceImpl;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class ThriftServer {

    /**
     * rpc方式--<strong>支持异构语言,异构平台</strong>
     *
     * Thrift 的传输格式
     *  {@link org.apache.thrift.protocol.TBinaryProtocol}--二进制格式
     *  {@link TCompactProtocol} --压缩格式***********使用最多
     *  {@link org.apache.thrift.protocol.TJSONProtocol} --json格式
     *  {@link org.apache.thrift.protocol.TSimpleJSONProtocol} json 只写协议(不方便解析),生成的文件很容易通过脚本语言解析
     *
     *  Server <==> Cline 使用的传输格式必须相同,否则无法解析.
     *
     *  Thrift 数据传输方式
     *  {@link org.apache.thrift.transport.TSocket} --阻塞式,原生
     *  {@link org.apache.thrift.transport.TFramedTransport} 以frame为单位进行传输,非阻塞服务
     *  {@link org.apache.thrift.transport.TFileTransport} 以文件形式传输
     *  {@link org.apache.thrift.transport.TMemoryInputTransport} 将内存用于I/O,java简单实现了ByteArrayOutputStream
     *  {@link org.apache.thrift.transport.TZlibTransport} 使用zlib进行压缩,与其他传输方式联合使用.
     *
     *  Thrift 支持的服务模型
     *  {@link org.apache.thrift.server.TSimpleServer} --简单服务模型,常用于测试
     *  {@link org.apache.thrift.server.TThreadPoolServer} --多线程服务模型,使用标准的阻塞式I/O
     *  {@link org.apache.thrift.server.TNonblockingServer} --多线程服务模型,使用非阻塞式I/O,需要使用TFramedTransport数据传输方式
     *  {@link THsHaServer extends TNonblockingServer} --THsHa引入了线程池去处理,把读写任务放到线程池去处理,Half-sync/Half-async的处理模式,Half-async是在
     *  处理I/O事件上(accept/read/write io),Half-sync用于handler对rpc的同步处理
     *
     * @author polunzi
     * @Date: 2019/7/15
     */
    public static void main(String args[])  throws Exception {
        //使用非阻塞式服务
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(8200);
        THsHaServer.Args arg = new THsHaServer.Args(serverSocket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());
        //设置协议层
        arg.protocolFactory(new TCompactProtocol.Factory());
        //设置传输层
        arg.transportFactory(new TFramedTransport.Factory());
        //设置处理层
        arg.processorFactory(new TProcessorFactory(processor));
        TServer tServer = new THsHaServer(arg);

        System.out.println("thrift server started");

        tServer.serve();
    }

}

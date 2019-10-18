**GREDLE**

    gradlew -- gradle warpper
        使用gradlew 系统可以不安装gradle,项目构建的时候,
    直接从gradle官网直接下载gradle的包到用户目录下的wapper目录下.
    
    使用gradlew的项目需要上传src/ gradle/  gradlew gradlew.bat build.gradle
    
    
**GRPC**

    1.传递的消息类型必须的封装的message类型
    2.流式的数据用stram表示,java用Iterator来实现
    3.传递的消息是message的集合需要使用*repeated*关键字
    
    4.配置生成文件位置
        protobuf {
            generatedFilesBaseDir = "src"
            protoc {
                artifact = "com.google.protobuf:protoc:3.7.1"
            }
            plugins {
                grpc {
                    artifact = 'io.grpc:protoc-gen-grpc-java:1.22.1'
                }
            }
            generateProtoTasks {
                all()*.plugins {
                    grpc {
                        outputSubDir = 'java'
                    }
                }
            }
        }
        
**NIO**

    
    io--流, 装饰器模式,InputStream引用的传递,一个类只能是  in/out  其中一种,面向流编程,数据直接从源读到程序中
    
    nio selector buffer channel 面向 block(buffer)编程
    
    buffer本身就是一块内存,是由数组实现的.
    
    nio将数据读取到buffer中,再从buffer中读取数据到内存中. buffer是双向的,既可以用于读取,也可以用于写入.====buffer.flip()来切换读写切换状态
    
    除了数组,buffer还提供了对于数据的结构化访问方式,并且可以追踪到系统的读写过程\
    
    java 8中基础数据类型,都由各自对应的Buffer类型(除了boolean)
    
    Channel 是可以向其写入/读取数据的对象,类似io中的stream, 但是操作对象是buffer,不能直接从channel中读写

    由于Channel是双向的,它能更好的反应底层操作系统的操作情况,linux底层操作系统流就是双向的
    
    关于buffer的状态属性:
        buffer.allocate(int capacity)
        <p> A buffer's <i>capacity</i> is the number of elements it contains.  The
         capacity of a buffer is never negative and never changes.  </p>
         
         <p> A buffer's <i>limit</i> is the index of the first element that should
          not be read or written.  A buffer's limit is never negative and is never
          greater than its capacity.  </p>
          
          <p> A buffer's <i>position</i> is the index of the next element to be
          read or written.  A buffer's position is never negative and is never
          greater than its limit.  </p>
          
          buffer.filp();
          从写到读======>将limit 指向当前position的位置,将操作指针放到最开始的位置
          从读到写======>limit位置不变,position归零
          
        buffer.clear()只是操作三个指针,buffer中的数据并不变.
        
        buffer.slice(); 返回的是原buffer的引用,操作的还是同一个buffer
        
        buffer.asReadOnlyBuffer(); 无法转回成可写的
        
**传统I/O操作**
   
    将磁盘种文件读取到内存种,再进行处理.
        
        用户空间向内核空间发送数据'读取'请求调用, 内核通过Direct Memory Access 读取到内核空间,再将数据拷贝到用户空间
        用户处理
        用户空间向内核空间发送内存'写'请求调用, 将数据拷贝到 内核,再将数据持久化到磁盘
        
        发生四次上下文空间切换 和 数据拷贝.(从文件-->内核-->用户空间, 使用 ,-->内核空间-->写出 socket/文件)
        
**NIO_ZERO_COPY**
    
        用户空间只向内核发送操作命令, 所有内存占用发生在内核空间,内核将内存映射(文件描述符: address, size)到用户空间,用户通过映射实际操作内核空间的数据.-->MappedByteBuff        
        
**DirectByteBuffer--ZERO_COPY**

      heapBytebuffer 会拷贝数据到jvm内存中
        
      java-jvm直接操纵的堆内存
      
      -------------------------------------
      
      系统本地的内存空间,native --malloc (*)  通过<p>long address;</p>来声明操作对外内存--操作系统内存.
      
      buffer的 scattering 与 gathering
      
      buffer数组
      
             
**Charset**

    ASCII (American standard code for infomation interchange)
        7位,128种
    iso-8859-1
        8位 8 bit (1 byte) 表示一个字符  256种
    gb2312
        对所有汉字进行编码,两个一个汉字==2byte
    GBK 对gb2312 进行扩展
    
    GB18030 对gbk进行扩展
    
    big5--台湾
    
    unicode--所有字符.两个byte表示一个字符 2^8 * 2^8,存储容量变大
        是一种编码格式
    utf-8
        unicode translation format 
        是一种存储方式是unicode的一种实现方式
        变长的字节表示形式
        用三个byte表示一个字符
        BOM(Byte Order Mark), 字节序标记,window存在
    utf-16 
        le little ending
        be  big ending
        大小端.
        
    
    
**Netty**

    EventLoopGroup==>用来注册channel
    
    //线程创建 任务处理  分离
    if (executor == null) {
        executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
    }
    
    Reactor模式(反应器模式)
        1.Scalable IO in Java
            read request 
            decode request
            process service
            Encode reply
            send reply
            Classic Service Designs
                每个请求对应一个线程,资源耗费
            Divide and Conquer
        
        2.Reactor角色构成:
        
            1.Handle(句柄/文件描述符),本质上是一种资源,由操作系统提供的;该资源表示一个个的事件:文件描述符,或网络编程中的socket描述符,
            事件既可以来自于内部:客户端的连接请求,数据发送;也可以来自于内部:操作系统产生的定时事件,它本质上是一个文件描述符
            handle是事件产生的发源地.
            
            2.Synchronous Event Demultiplexer(同步事件分离器): 它本身是一个系统调用,用于等待事件的发生(可能是一个事件,也可能是多个),
            调用方在调用它的时候会被阻塞,一直阻塞到同步事件分离器上有事件产生为止,对于linux来说,同步事件分离器指的是常用的I/O多路复用机制.
            对于java nio来说,就是Selector组件.对用的阻塞方法就是select()-->Set<SelectionKey>方法.
            
            3.Event Handler(事件处理器), 本事是由多个回调方法构成,这些回调方法构成了于应用相关的对应某个事件的反馈机制,(对于Nio中,
            没有组件与其对应,都是通过用户自定义),Netty相对于nio来说,在事件处理器这个角色上进行了升级,它为开发者提供了大量的回调方法,
            提供给开发人员在特定事件产生的时候通过相应的回调方法进行业务逻辑的处理.
            
            4.Concrete Event Handler(具体的事件处理器 == implents Event handler), 实现的事件处理器提供的各个回调方法,实现了特定的
            业务逻辑,本质上就是开发人员编写的一个个的处理实现.(对于Netty 就是用户自己编写的Handler)
            
            5.Initiation Dispatcher(初始分发器): 实际上就是Reactor角色,它本身定义了一些规范,用于控制事件的调度方式,同时又提供了应用进行
            事件处理器的注册,删除等设施.它本事是整个事件处理器的核心所在,它通过 Synchronous Event Demultiplexer 来等待事件的发生, 
            一旦事件发生, 它会分离出每一个事件,然后调用事件处理器,最后调用相关的回调方法来处理这些特定的事件.==>(channelRead0()是由workerGroup
            调用的,因此在业务处理的时候会阻塞workGroup, 因此在业务处理的时候,正确的打开方式需要用户自己开启线程/线程池中处理业务)
            
        3.运作流程:
                Reactor启动的时候,会将多个event handler 注册到自己上面.相当于noi中的register(InterestKey),开启事件循环;通过同步事件分离器
            获取到产生的事件集合,来调用Event handler的handler()方法.
            
            1.当应用向 Initiation Dispatcher 注册具体的事件处理器时, 应用会标识出事件处理器希望Initiation Dispatcher在某个事件发生时向其通知
            该事件,该事件与Handle关联
            
            2.Initiation Dispatcher会要求每个事件处理器向其传递内部的Handle,该Handle向操作系统表示了事件处理器.
            
            3.当所有的事件处理器注册完毕后,应用会调用Handler Event 方法,来启动Initiation Dispatcher 的事件循环,这时,Initiation Dispatcher
            会将每个注册是事件处理器的Handle关联起来,并使用Initiation Dispatcher来等待事件的发生: Tcp协议层会使用select同步事件分离器操作来等待
            客户端发送的数据到达连接的Socket handle上.
            
            4.当与某个事件对应的Handle变为ready时,比如TCP, socket变为等待读状态,同步事件分离器就会通知Initiation Dispatcher.
            
            5.Initiation Dispatcher会触发事件处理器的回调方法,从而响应这个处于ready状态的Handle,当事件发生时,Initiation Dispatcher会将被
            事件激活的Handle作为[key]来寻找并分发恰当的Event handler
            
            6.Initiation Dispatcher 会回调事件的Handle_event()来执行特定应用功能,从而响应这个事件,所发生的事件类型可以作为该方法的参数并被该方法
            内部使用额外的特定于服务的分离于分发.
        
    ChannelPipline
            
            
    
    

   
    
     
      
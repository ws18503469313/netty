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


    
     
      
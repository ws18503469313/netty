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
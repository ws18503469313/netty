**GREDLE**

    gradlew -- gradle warpper
        使用gradlew 系统可以不安装gradle,项目构建的时候,
    直接从gradle官网直接下载gradle的包到用户目录下的wapper目录下.
    
    使用gradlew的项目需要上传src/ gradle/  gradlew gradlew.bat build.gradle
    
    
**GRPC**

    1.传递的消息类型必须的封装的message类型
    2.流式的数据用stram表示,java用Iterator来实现
    3.传递的消息是message的集合需要使用*repeated*关键字
    
    
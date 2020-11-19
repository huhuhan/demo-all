# 练习项目归纳总结

> 将分散在多个仓库中的demo项目汇总，部分项目比较老，都经测试可运行才归纳，无法运行的直接删除了

由于maven父子依赖的关系，单个demo下载后，修改父类依赖，补充基础lombok依赖
```xml
<project>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
        </dependency>
    </dependencies>
</project>
```
## 清单
- demo spring boot starter
- demo 2018
    - demo activemq
    - demo netty
    - demo security
    - demo socket ： java IO、NIO、AIO
    - demo java ：java code by main method
- demo 2019
    - demo aop ：by xml 
    - demo redis
    - demo thread ：spring async annotation
    - demo annotation
    - demo kafka
- demo 2020
    - auth security ：基于demo 2018的demo security的完整版，熟悉源码内容
    - demo ureport : 开源报表依赖包，集成项目
- demo sc
    - sc eureka
    - sc clientA
    - sc feign
    - sc ribbon
    - sc zuul
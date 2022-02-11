# 练习项目归纳总结

> 将分散在多个仓库中的demo项目汇总，部分项目比较老，都经测试可运行才归纳，无法运行的直接删除了

## 清单
- demo spring boot starter
- demo 2018
    - demo activemq
    - demo netty
    - demo security
    - demo socket ： java IO、NIO、AIO
    - demo java ：java code by main method
- demo 2019
    - demo redis
    - demo springboot ：框架自带应用
       - 切面编程AOP，注解@Aspect
       - 异常线程（池），注解@Async
       - 事件、监听器，ApplicationListener
    - demo annotation
    - demo kafka
- demo 2020
    - auth security ：基于demo 2018的demo security的完整版，熟悉源码内容
    - demo ureport : 开源报表依赖包，集成项目
    - dynamic-datasource-starter :  动态数据库源切换的自动化配置包
- demo sc
    - sc eureka
    - sc clientA
    - sc feign
    - sc ribbon
    - sc zuul 
- demo 2021
    - demo-druid : 多数据源例子 + dynamic-datasource-starter的应用
    - demo-pdf：电子签章，pdf合成文档，可验签
- common-starter
    - 公共应用集成包，部分应用在[yh-cloud](https://github.com/huhuhan/yh-cloud)项目中，暂时用不到的放在这边。
    - common-message：邮箱、阿里短信、验证码。测试类中有使用参考
- demo 2022
    - demo-geo : geojson和shapefile的转换工具测试，基于geotools
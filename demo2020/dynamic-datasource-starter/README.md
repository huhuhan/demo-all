## 动态数据库源

 - 自动化配置依赖包，命名非规范化的xx-spring-boot-starter格式

 - 核心源码在`com.yh.demo.db.dynamic`目录下

 - 动态数据库源，SQL脚本：`demo2020/dynamic-datasource-starter/db`

 - 集成例子，参考`demo2021/demo-druid`
   
    - 引入该包
    
    - YAML配置：
    
        ```yaml
        spring:
          datasource:
            dynamic-yh:
              # 开启，默认不注入
              enabled: true
              primary: master
              # 默认加载规则
              datasource:
                master:
                  .....
        ```
    
    - 注入需要的Bean，提供默认的配置文件`com.yh.demo.db.config.DefaultMyConfig`，Import引入即可，包含如下。
    
        - 数据库源加载接口的默认实现类，参考`com.yh.demo.db.dynamic.MyDynamicDataSourceProvider`
        
        - 数据库源转发接口的默认实现类
        
        - 路由数据库源的拦截器（需要注册到MVC配置上）
        
        - 路由数据库源的切面注解
        
        > 以上实现类，都可以自定义重写

> 实际应用，建议用[mybatis-plus的动态数据库源](https://dynamic-datasource.com/)，集成更多的数据库连接池和其他很多功能
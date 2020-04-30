## Mybatis.tk

- 文档：[github](https://github.com/abel533/Mapper/wiki)
- 本项目集成方式：[spring-boot](https://github.com/abel533/Mapper/wiki/1.3-spring-boot)
- 其他集成方式：[通用](https://github.com/abel533/Mapper/wiki/4.1.mappergenerator)

### 操作
- 修改config.properties的实际需求属性
- 修改generatorConfig.xml的<table>标签生成表，默认整个数据库表
- 基于maven插件生成，查看pom文件了解，在pom文件这级目录运行
```
mvn mybatis-generator:generate
```
> IDEA的maven插件会多出一个运行命令，可直接点击运行
> 
### 使用
- 注解
```java
@MapperScan(basePackages = "com.yh.xx.mapper")
```
- 配置
```yml
#tk.mybatis，需指定mapper路径
mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  #返回map对象。默认为空字段不返回，需设置
  configuration:
    call-setters-on-nulls: true
#pagehelper配置
pagehelper:
  #支持分页参数传递
  support-methods-arguments: true
```

> [推荐](https://blog.csdn.net/isea533/article/details/104776347)
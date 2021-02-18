## 动态数据库源

 - 自动化配置依赖包，命名非规范化的xx-spring-boot-starter格式

 - 核心源码在`com.yh.demo.db.dynamic`目录下

 - 动态数据库源，SQL脚本：`demo2020/dynamic-datasource-starter/db`

 - 测试
   
     - 引入该包
     
    - 添加YAML配置
     
        ```yaml
        spring:
          application:
            # 子系统ID
       subsystem-id: 422006075242577921
          datasource:
            # 多数据库源
       dynamic-yh:
              enabled: true
              datasource:
                master:
                  source-type: com.alibaba.druid.pool.DruidDataSource
                  url: jdbc:mysql://127.0.0.1:3306/test_bpm_cg?useUnicode=true&autoReconnect=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
                  username: root
             password: yanghan
                  driver-class-name: com.mysql.cj.jdbc.Driver
                  
        ## 可以补充其他druid配置
        ```
     
        
     
     - MVC配置注册拦截器
       
        ```java
        @Configuration
        @EnableWebMvc
        @Import(DefaultInterceptorConfig.class)
        public class WebMvcConfig implements WebMvcConfigurer  {
     
            @Resource
            private DynamicDataSourceInterceptor dynamicDataSourceInterceptor;
     
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(dynamicDataSourceInterceptor);
            }
        }
        ```
     
        > 拦截判断，查看DynamicDataSourceInterceptor类源码；
        >
        > 或者自定义重写。参考DefaultInterceptorConfig，实现其中的接口和拦截器
        >
        > - 拦截器：DynamicDataSourceInterceptor
        > - 系统数据库源接口：SysDataSourceService
        > - 系统数据库源转化接口：DataSourceService






> 参考对比：[mybatis-plus的动态数据库源](https://dynamic-datasource.com/)
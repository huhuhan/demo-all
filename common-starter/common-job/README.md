## 说明

> 基于XXL-JOb

### 调度平台

参考部署：[docker-compose](https://raw.githubusercontent.com/huhuhan/demo-docker/master/xxl-job-admin/docker-compose.yml)

### 项目依赖

- 引入依赖

- 配置属性如下

    ```yaml
    xxl:
      job:
      	# 开启使用
        enabled: true
        # 认证令牌，和xxl-job-adimin服务部署时的认证令牌参数一致
        access-token:
        executor:
          # 自定义执行器名称
          app-name: demo-yh-job
          # 默认当前服务IP
          ip:
          port: 9999
          # 任务调度的日志
          log-path: /usr/xxl-job/data/applogs/demo-yh-job/jobhandler
          # 日志保留时间，30天
          log-retention-days: 30
        admin:
          # xxl-job-admin服务地址
          addresses: http://192.168.0.36:8080
    ```

- 使用例子：

    - **执行器项目**中，开发方法，加注解`@XxlJob("demoJobHandler")`
    - 方法中日志输出用`XxlJobHelper.log`
    - 在**调度中心-执行器管理**，添加执行器，配置中的自定义执行器名称，会自动注册
    - 在**调度平台-任务管理**，添加任务，其中**JobHandler**，就是上面注解的`demoJobHandler`
    - 调用测试即可
    - 依赖包名中有源代码任务例子`com.yh.common.job.handler.XxjJobDemoHandler`

- 更多使用方法，参考[官方文档](https://www.xuxueli.com/xxl-job/#%E3%80%8A%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E8%B0%83%E5%BA%A6%E5%B9%B3%E5%8F%B0XXL-JOB%E3%80%8B)
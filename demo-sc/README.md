# 微服务组件功能测试

## 参考资料

- [熔断超时配置](https://blog.csdn.net/mxmxz/article/details/84633098)
- [Zuul配置](https://blog.csdn.net/weixin_42323802/article/details/84645985), [配置2](https://www.jianshu.com/p/511db36c1b3e)
- [Eureka调优配置](https://blog.csdn.net/weixin_41922349/article/details/99655326)

> 部分配置，不确定新版Spring Cloud是否有更新变化，原本测试版本Finchley.M8



### 部署测试
#### docker部署
1. 打包
2. 复制jar包至docker/jar目录下
3. 运行docker-compose相关命令
> 192.168.0.82改为自己的docker主机IP
#### 测试接口
- client-hello：[http://192.168.0.82:7811/hi](http://192.168.0.82:7811/hi)
- client-feign：[http://192.168.0.82:7822/hi?name=feign](http://192.168.0.82:7822/hi?name=feign)
- client-ribbon：[http://192.168.0.82:7833/hi?name=ribbon](http://192.168.0.82:7833/hi?name=ribbon)
- client-zuul：
    - 无效访问：[http://192.168.0.82:7801/api/client-a/hi?](http://192.168.0.82:7801/api/client-a/hi?)
    - client-hello：[http://192.168.0.82:7801/api/client-a/hi?token=yh](http://192.168.0.82:7801/api/client-a/hi?token=yh)
    - client-feign：[http://192.168.0.82:7801/api/client-b/hi?name=feign&token=yh](http://192.168.0.82:7801/api/client-b/hi?name=feign&token=yh)
    - client-ribbon：[http://192.168.0.82:7801/api/client-c/hi?name=ribbon&token=yh](http://192.168.0.82:7801/api/client-c/hi?name=ribbon&token=yh)
    
### 直接运行
测试接口参考如上，将`192.168.0.82`改为自己的主机IP，还需修改访问端口，详细参考application文件
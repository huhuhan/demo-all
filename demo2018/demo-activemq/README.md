- 安装activemq并按默认端口启动
- [下载地址](http://activemq.apache.org/components/classic/download/)
- docker安装方式
```shell
docker search activemq

docker pull webcenter/activemq


docker run -d --name activemq -p 61616:61616 -p 8161:8161 webcenter/activemq

docker ps
```
- [本地访问地址](http://127.0.0.1:8161) 账户密码admin
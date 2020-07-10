package com.yh.demo.activemq.sample2;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    //默认的连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认的连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认的连接地址
    private static final String BROKER_URL = "tcp://192.168.0.82:61616";//ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory;
        //连接对象
        Connection connection;
        //会话，接受或者发送消息的线程
        Session session;
        //消息的目的地
        Queue queue;
        //消息的消费者
        MessageConsumer messageConsumer;
        //实例化连接工厂(指定连接用户名｜密码｜连接地址)
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();//启动连接
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//创建session
            queue = session.createQueue("YH-MQ-2");//创建连接的消息队列
            messageConsumer = session.createConsumer(queue);//创建消息消费者
            messageConsumer.setMessageListener(new MyListener());//注册消息监听
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

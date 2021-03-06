package com.yh.demo.activemq.sample2;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.*;

public class Producer {
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
        Connection connection = null;
        //会话，接受或者发送消息的线程
        Session session;
        //消息的目的地
        Queue queue;
        //消息的生产者
        MessageProducer messageProducer;
        //实例化连接工厂(指定连接用户名｜密码｜连接地址)
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();//启动连接
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);//创建session
            queue = session.createQueue("YH-MQ-2");//创建消息队列
            messageProducer = session.createProducer(queue);//创建消息生产者
            sendMessage(session, messageProducer);//发送消息
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //发送消息
    private static void sendMessage(Session session, MessageProducer messageProducer) {
        try {
            //创建消息Map<key,value>
            MapMessage message = session.createMapMessage();
            message.setString("userName", "syf");
            message.setInt("age", 30);
            message.setDouble("salary", 1000);
            message.setBoolean("isGirl", true);
            System.out.println("Sending:" + ((ActiveMQMapMessage)message).getContentMap());
            //发送消息
            messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

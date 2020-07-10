package com.yh.demo.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public abstract class AbstractJMS {
    //默认的连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认的连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认的连接地址
    private static final String BROKER_URL = "tcp://192.168.0.82:61616";//ActiveMQConnection.DEFAULT_BROKER_URL;

    //连接工厂
    private ConnectionFactory connectionFactory;
    //连接对象
    Connection connection;

    //连接事务
    Session session;

    AtomicInteger count = new AtomicInteger();

    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Destination getDestination() throws JMSException {
        Destination destination;
        if (this.isTopic()) {
            destination = session.createTopic(this.getDestinationName());
        } else {
            destination = session.createQueue(this.getDestinationName());
        }
        return destination;
    }

    protected abstract boolean isTopic();

    protected abstract String getDestinationName();

    public void close() throws JMSException {
        session.close();
        connection.close();
    }
}

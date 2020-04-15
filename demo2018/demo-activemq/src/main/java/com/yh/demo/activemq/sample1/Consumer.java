package com.yh.demo.activemq.sample1;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    ConnectionFactory connectionFactory;

    Connection connection;

    Session session;

    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
    AtomicInteger count = new AtomicInteger();

    private final int msgCount = 3;

    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void getMessage(String disname) {
        try {
            Queue queue = session.createQueue(disname);
            MessageConsumer consumer = null;

            if (threadLocal.get() != null) {
                consumer = threadLocal.get();
            } else {
                consumer = session.createConsumer(queue);
                threadLocal.set(consumer);
            }
            System.out.println(Thread.currentThread().getName() + "消费者，开始消费消息");
            for (int i = 0; i < msgCount; i++) {
                Thread.sleep(1000);
                TextMessage msg = (TextMessage) consumer.receive();
                if (msg != null) {
                    msg.acknowledge();
                    System.out.println(Thread.currentThread().getName() + ": Consumer:我是消费者，我正在消费Msg" + msg.getText() + "--->" + count.getAndIncrement());
                } else {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + "消费者，结束消费，共" + msgCount + "条记录");
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

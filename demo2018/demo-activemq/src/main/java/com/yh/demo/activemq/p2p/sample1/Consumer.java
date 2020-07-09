package com.yh.demo.activemq.p2p.sample1;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String BROKER_URL = "tcp://192.168.0.82:61616";//ActiveMQConnection.DEFAULT_BROKER_URL;
    //连接工厂
    ConnectionFactory connectionFactory;
    //连接对象
    Connection connection;
    //连接事务
    Session session;
    //本地线程池，存储消息消费者
    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
    AtomicInteger count = new AtomicInteger();

    /** 一次请求，消费的数量，即一次出队列的数量 */
    private final int msgCount = 100;

    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            //false非事务类型，消息确认类型
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
//                Thread.sleep(1000);
                //等候响应，直到获取消息
                TextMessage msg = (TextMessage) consumer.receive();
                if (msg != null) {
                    //确认已接收
                    msg.acknowledge();
                    System.out.println(Thread.currentThread().getName() + ": Consumer:我是消费者，我正在消费Msg【" + msg.getText() + "】--->" + count.getAndIncrement());
                } else {
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + "消费者，结束消费，一次请求共" + msgCount + "条记录");
            this.connectionClose();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectionClose(){
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

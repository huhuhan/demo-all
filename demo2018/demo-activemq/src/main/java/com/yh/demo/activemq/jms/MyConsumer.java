package com.yh.demo.activemq.jms;

import javax.jms.*;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public class MyConsumer<T> extends AbstractJMS {

    private MessageConsumer consumer;

    private boolean isTopic;

    private String destinationName;

    public MyConsumer(boolean isTopic, String destinationName) {
        this.isTopic = isTopic;
        this.destinationName = destinationName;
    }

    @Override
    public void init() {
        try {
            super.init();
            //false非事务类型，消息确认类型
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            consumer = session.createConsumer(this.getDestination());
            consumer.setMessageListener(new MyConsumerListener());//注册消息监听
            System.out.println(Thread.currentThread().getName() + "消费者，开始监听消息");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public boolean isTopic() {
        return isTopic;
    }

    public void setTopic(boolean topic) {
        isTopic = topic;
    }

    @Override
    public void close() {
        try {
            consumer.close();
            super.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

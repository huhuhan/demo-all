package com.yh.demo.activemq.jms;

import javax.jms.*;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public class MyProducer extends AbstractJMS {

    private boolean isTopic;

    private String destinationName;

    private MessageProducer producer;

    public MyProducer(boolean isTopic, String destinationName) {
        this.isTopic = isTopic;
        this.destinationName = destinationName;
    }

    @Override
    public void init() {
        try {
            super.init();
            //false非事务类型，消息确认类型
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            producer = session.createProducer(this.getDestination());
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

    public void sendMsg(String msgTT){
        try {
            TextMessage msg = session.createTextMessage(msgTT);
            System.out.println(msgTT);
            //发送消息
            producer.send(msg);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            producer.close();
            super.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

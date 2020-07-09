package com.yh.demo.activemq.p2p.sample2;

import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class MyListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("receive Message:" + ((ActiveMQMapMessage) message).getContentMap());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

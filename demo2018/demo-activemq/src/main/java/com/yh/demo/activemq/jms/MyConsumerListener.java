package com.yh.demo.activemq.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

public class MyConsumerListener extends AbstractMessageHandler implements MessageListener{

    @Override
    public void onMessage(Message message) {

        super.handMessage(message);
        //todo: 消费业务
    }
}

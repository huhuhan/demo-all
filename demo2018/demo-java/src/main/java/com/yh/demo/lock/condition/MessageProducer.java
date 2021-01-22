package com.yh.demo.lock.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghan
 * @date 2021/1/19
 */
public class MessageProducer implements Runnable {

    private Message message;

    public MessageProducer(Message msg) {
        message = msg;
    }

    @Override
    public void run() {
        produce();
    }

    public void produce() {
        List<String> msgs = new ArrayList<>();
        msgs.add("Begin");
        msgs.add("Msg1");
        msgs.add("Msg2");

        for (String msg : msgs) {
            message.produce(msg);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        message.produce("End");
        message.setEnd(true);
    }

}
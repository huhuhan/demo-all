package com.yh.demo.lock.condition;

/**
 * @author yanghan
 * @date 2021/1/19
 */
public class MessageConsumer implements Runnable {

    private Message message;

    public MessageConsumer(Message msg) {
        message = msg;
    }

    @Override
    public void run() {
        while (!message.isEnd()) {
            message.consume();
        }
    }

}
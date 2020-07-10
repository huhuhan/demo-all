package com.yh.demo.activemq.jms;

import lombok.SneakyThrows;

import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public abstract class AbstractMessageHandler implements JmsHandler {

    @SneakyThrows
    @Override
    public void handMessage(Message message) {
        /**
         * todo: 泛型指定消息类型
         */
        TextMessage msg = (TextMessage) message;
        System.out.println(Thread.currentThread().getName() + ": Consumer:我是消费者，我正在消费Msg【" + msg.getText() + "】--->" /*+ count.getAndIncrement()*/);
    }
}

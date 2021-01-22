package com.yh.demo.lock.condition;

/**
 * @author yanghan
 * @date 2021/1/19
 */
public class ConditionTest {
    public static void main(String[] args) {
        Message msg = new Message();
        Thread producer = new Thread(new MessageProducer(msg));
        Thread consumer = new Thread(new MessageConsumer(msg));
        producer.start();
        consumer.start();
    }
}

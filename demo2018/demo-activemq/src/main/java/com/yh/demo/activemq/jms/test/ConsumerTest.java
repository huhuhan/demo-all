package com.yh.demo.activemq.jms.test;

import com.yh.demo.activemq.jms.MyConsumer;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public class ConsumerTest {

    public static void main(String[] args) {
//        queueTest();
        topicTest2();
    }

    /**
     * 点对点模式，连接后，只要MQ队列有消息就开始消费
     */
    public static void queueTest() {
        MyConsumer myConsumer = new MyConsumer(false, "YH-MQ-queue");
        myConsumer.init();
    }

    public static void queueTest2() {
        MyConsumer myConsumer = new MyConsumer(false, "YH-MQ-queue");
        new Thread(new ConsumerTest().new ConsumerMq(myConsumer)).start();
        new Thread(new ConsumerTest().new ConsumerMq(myConsumer)).start();
    }

    /**
     * 订阅发布模式，启动之前生产者已发布的消息不可接收
     * 启动多个实例
     */
    public static void topicTest() {
        MyConsumer myConsumer = new MyConsumer(true, "YH-MQ-topic");
        myConsumer.init();
    }

    public static void topicTest2() {
        MyConsumer myConsumer = new MyConsumer(true, "YH-MQ-topic");
        new Thread(new ConsumerTest().new ConsumerMq(myConsumer)).start();
        new Thread(new ConsumerTest().new ConsumerMq(myConsumer)).start();
    }

    private class ConsumerMq implements Runnable {
        MyConsumer consumer;

        public ConsumerMq(MyConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                consumer.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



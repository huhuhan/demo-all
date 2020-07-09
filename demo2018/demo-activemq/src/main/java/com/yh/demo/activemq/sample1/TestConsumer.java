package com.yh.demo.activemq.sample1;

/**
 * 同个队列的多个消费者一般平均获取到消息数量，消费
 * 除非，多个消费者启动之间时间差距大，已有的队列直接分配给同个消费者，最大等待处理量默认1000
 */
public class TestConsumer {
    public static void main(String[] args) {
        //MQ可以看到2个连接，3个consumer对象

        Consumer consumer = new Consumer();
        consumer.init();
        new Thread(new TestConsumer().new ConsumerMq(consumer)).start();
//        new Thread(new TestConsumer().new ConsumerMq(consumer)).start();

        Consumer main = new Consumer();
        main.init();
        main.getMessage("YH-MQ");
    }

    private class ConsumerMq implements Runnable {
        Consumer consumer;

        public ConsumerMq(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                consumer.getMessage("YH-MQ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

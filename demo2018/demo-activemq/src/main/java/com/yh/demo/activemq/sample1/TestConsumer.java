package com.yh.demo.activemq.sample1;

public class TestConsumer {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.init();
        TestConsumer testConsumer = new TestConsumer();

        new Thread(testConsumer.new ConsumerMq(consumer)).start();
        new Thread(testConsumer.new ConsumerMq(consumer)).start();
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
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

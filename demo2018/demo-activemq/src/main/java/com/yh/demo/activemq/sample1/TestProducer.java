package com.yh.demo.activemq.sample1;

public class TestProducer {
    /** 发起请求次数 */
    private final int producerCount = 1;


    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.init();
        TestProducer testMq = new TestProducer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(testMq.new ProducerMq(producer)).start();
//        new Thread(testMq.new ProducerMq(producer)).start();
//        new Thread(testMq.new ProducerMq(producer)).start();
    }

    private class ProducerMq implements Runnable {
        Producer producer;

        public ProducerMq(Producer producer) {
            this.producer = producer;
        }

        @Override
        public void run() {
            for (int i = 0; i < producerCount; i++) {
                try {
                    producer.sendMessage("YH-MQ");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.yh.demo.activemq.p2p.sample1;

public class TestProducer {
    /** 发起请求次数 */
    private final int producerCount = 2;


    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new TestProducer().new ProducerMq(producer)).start();
    }

    private class ProducerMq implements Runnable {
        Producer producer;

        public ProducerMq(Producer producer) {
            this.producer = producer;
        }

        @Override
        public void run() {
            int allCount = 0;
            for (int i = 0; i < producerCount; i++) {
                try {
                    allCount += producer.sendMessage("YH-MQ");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "生产者，结束生产，所有请求共" + allCount + "条记录");
            producer.connectionClose();
        }
    }
}

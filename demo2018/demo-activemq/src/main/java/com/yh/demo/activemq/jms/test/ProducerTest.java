package com.yh.demo.activemq.jms.test;

import com.yh.demo.activemq.jms.MyProducer;

import java.util.Scanner;

/**
 * @author yanghan
 * @date 2020/7/9
 */
public class ProducerTest {

    public static void main(String[] args) {
//        queueTest();
        topicTest();
    }

    /**
     * 点对点模式，消息发送至MQ的队列中存储。和消费者启动顺序无关
     */
    public static void queueTest() {
        MyProducer myProducer = new MyProducer(false, "YH-MQ-queue");
        myProducer.init();

        for (int i = 0; i < 100; i++) {
            myProducer.sendMsg("m m m m m m ");
        }
    }

    /**
     * 发布订阅模式测试，先启动消费者
     */
    public static void topicTest() {
        MyProducer myProducer = new MyProducer(true, "YH-MQ-topic");
        myProducer.init();

        Scanner scanner = new Scanner(System.in);
        while (true){
            String scannerString = scanner.nextLine();
            if(scannerString.indexOf("end") != -1) {
                break;
            } else {
               myProducer.sendMsg(scannerString);
            }
        }

        scanner.close();
        myProducer.close();
    }
}

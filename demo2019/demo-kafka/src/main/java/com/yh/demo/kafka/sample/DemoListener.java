package com.yh.demo.kafka.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者Demo的监听器
 * @author yanghan
 * @date 2019/4/26
 */
@Component
public class DemoListener {
    private static final Logger log = LoggerFactory.getLogger(DemoListener.class);

    @KafkaListener(id = "demo", topics = "topic.quick.demo")
    public void listen(String msgData) {
        log.info("demo receive : " + msgData);
    }
}

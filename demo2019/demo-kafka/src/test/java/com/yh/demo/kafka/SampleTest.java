package com.yh.demo.kafka;

import com.yh.demo.kafka.sample.KafkaSendResultHandler;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Resource
    private KafkaTemplate kafkaTemplate;

    /**
     * 对应KafkaConfig
     * @throws InterruptedException
     */
    @Test
    public void testDemo() throws InterruptedException {
        kafkaTemplate.send("topic.quick.demo", "this is my first demo");
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(5000);
    }

    @Autowired
    private AdminClient adminClient;

    /**
     * 测试手动创建topic
     * @throws InterruptedException
     */
    @Test
    public void testCreateTopic() throws InterruptedException {
        NewTopic topic = new NewTopic("topic.quick.initial2", 1, (short) 1);
        adminClient.createTopics(Arrays.asList(topic));
        Thread.sleep(1000);
    }

    /**
     * 查询测试
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSelectTopicInfo() throws ExecutionException, InterruptedException {
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList("topic.quick.initial"));
        result.all().get().forEach((k,v)->System.out.println("k: "+k+" ,v: "+v.toString()+"\n"));
    }


    @Resource
    private KafkaTemplate defaultKafkaTemplate;
    @Test
    public void testDefaultKafkaTemplate() {
        //默认topic，发送到topic.quick.default
        defaultKafkaTemplate.sendDefault("I`m send msg to default topic");
    }

    @Test
    public void testTemplateSend() {
        //发送带有时间戳的消息
        kafkaTemplate.send("topic.quick.demo", 0, System.currentTimeMillis(), 0, "send message with timestamp");

        //使用ProducerRecord发送消息
        ProducerRecord record = new ProducerRecord("topic.quick.demo", "use ProducerRecord to send message");
        kafkaTemplate.send(record);

        //使用Message发送消息
        Map map = new HashMap();
        map.put(KafkaHeaders.TOPIC, "topic.quick.demo");
        map.put(KafkaHeaders.PARTITION_ID, 0);
        map.put(KafkaHeaders.MESSAGE_KEY, 0);
        GenericMessage message = new GenericMessage("use Message to send message",new MessageHeaders(map));
        kafkaTemplate.send(message);
    }

    /**
     * 异步发送消息，监听发送回调结果
     */
    @Autowired
    private KafkaSendResultHandler kafkaSendResultHandler;
    @Test
    public void testProducerListen() throws InterruptedException {
        //内部源码即实现了java自带的Future接口，支持返回值，
        kafkaTemplate.setProducerListener(kafkaSendResultHandler);
        kafkaTemplate.send("topic.quick.demo", "test producer listen");
        Thread.sleep(1000);
    }

    /**
     * 同步发送消息
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        //内部源码即实现了java自带的Future接口，支持返回值，future.get()
        kafkaTemplate.send("topic.quick.demo", "test sync send message").get();
    }

    /**
     * 1、配合spring的@Transactional事务注解进行事务管理（需配置）
     * @throws InterruptedException
     */
    @Test
    @Transactional
    public void testTransactionalAnnotation() throws InterruptedException {
        kafkaTemplate.send("topic.quick.tran", "test transactional annotation");
        throw new RuntimeException("fail");
    }

    /**
     * 2、直接使用事务方法执行
     * @throws InterruptedException
     */
    @Test
    public void testExecuteInTransaction() throws InterruptedException {
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
            @Override
            public Object doInOperations(KafkaOperations kafkaOperations) {
                kafkaOperations.send("topic.quick.tran", "test executeInTransaction");
                throw new RuntimeException("fail");
                //return true;
            }
        });
    }


    @Test
    public void testBeanListener() {
        kafkaTemplate.send("topic.quick.bean", "send msg to beanListener");
    }


//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//    @Test
//    public void testBatch() {
//        for (int i = 0; i < 12; i++) {
//            kafkaTemplate.send("topic.quick.batch", "test batch listener,dataNum-" + i);
//        }
//    }
}

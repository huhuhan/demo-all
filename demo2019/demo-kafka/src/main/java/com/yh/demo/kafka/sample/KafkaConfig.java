package com.yh.demo.kafka.sample;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    /**
     * 初始化启动创建TopicName为topic.quick.initial的Topic并设置分区数为8以及副本数为1
     * topic类似于一个队列queue，包含多个分区partition
     *
     * @return
     */
    @Bean
    public NewTopic initialTopic() {
        //return new NewTopic("topic.quick.initial", 8, (short) 1);
        //重启服务，扩大分区，数据不变
        return new NewTopic("topic.quick.initial", 11, (short) 1);
    }


    /**
     * 创建连接
     *
     * @return
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        KafkaAdmin kafkaAdmin = new KafkaAdmin(props);
        return kafkaAdmin;
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfig());
    }

//    /**
//     * kafkaTemplate工具类实现发送接收功能
//     * demoe4,加入@Primary注解优先使用该bean
//     *
//     * @return
//     */
//    @Bean
//    @Primary
//    public KafkaTemplate<Integer, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean("defaultKafkaTemplate")
//    public KafkaTemplate<Integer, String> defaultKafkaTemplate() {
//        KafkaTemplate template = new KafkaTemplate<Integer, String>(producerFactory());
//        template.setDefaultTopic("topic.quick.default");
//        return template;
//    }
//
//    /**
//     * 2、根据senderProps填写参数创建生产者工厂
//     *
//     * @return
//     */
//    @Bean
//    public ProducerFactory<Integer, String> producerFactory() {
//        DefaultKafkaProducerFactory defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(senderProps());
//        //开启事务管理
//        defaultKafkaProducerFactory.transactionCapable();
//        defaultKafkaProducerFactory.setTransactionIdPrefix("tran-");
//        return defaultKafkaProducerFactory;
//    }
//
//    //1、生产者配置
//    private Map<String, Object> senderProps() {
//        Map<String, Object> props = new HashMap<>();
//        //连接地址
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        //重试，0为不启用重试机制
//        props.put(ProducerConfig.RETRIES_CONFIG, 1);
//        //控制批处理大小，单位为字节
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
//        //批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
//        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
//        //生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024000);
//        //键的序列化方式
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
//        //值的序列化方式
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
}

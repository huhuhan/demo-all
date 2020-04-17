package com.yh.demo.redis.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;

/**
 * reids 相关bean的配置
 */
@Configuration
@EnableCaching
public class RedisConfig extends JCacheConfigurerSupport {

    private String host = "localhost";

    @Value("${spring.redis.port}")
    private Integer port;

    private Integer timeout = 10000;

    private Integer maxIdle = 10000;

    private Integer maxWaitMillis = 10000;

    private Integer maxActive = 10000;

    private Integer minIdle = 10000;

//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        rcm.setDefaultExpiration(30l);// 设置缓存时间
//        return rcm;
//    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(6379);


        jedisConnectionFactory.getPoolConfig().setMaxIdle(maxIdle);
        jedisConnectionFactory.getPoolConfig().setMaxTotal(maxActive);
        jedisConnectionFactory.getPoolConfig().setMaxWaitMillis(maxWaitMillis);

        return jedisConnectionFactory;
    }
//    @Bean
//    public JedisPool redisPoolConfig(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMinIdle(minIdle);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);
//        System.err.println("JedisPool注入成功！");
//        System.err.println("redis地址：" + host + ":" + port);
//        return  jedisPool;
//    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();





//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper om = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jacksonSeial.setObjectMapper(om);
//        // 值采用json序列化
//        template.setValueSerializer(jacksonSeial);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // 设置hash key 和value序列化模式
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jacksonSeial);
//        template.afterPropertiesSet();






        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

//    @Bean
//    @ConditionalOnMissingBean(StringRedisTemplate.class)
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }


    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
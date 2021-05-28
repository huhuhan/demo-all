package com.yh.demo.redis.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;

/**
 * redis 相关bean的配置
 */
//@Configuration
//@EnableCaching
public class RedisConfig2 extends JCacheConfigurerSupport {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    @Value("${spring.redis.dbindex}")
    private Integer dbindex;
    @Value("${spring.redis.pool.max-wait}")
    private Integer maxWaitMillis;
    @Value("${spring.redis.pool.max-total}")
    private Integer maxTotal;
    @Value("${spring.redis.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.pool.max-idle}")
    private Integer maxIdle;

//    @Bean
//    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
//        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        return cacheManager;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setTimeout(timeout);
//        jedisConnectionFactory.setDatabase(dbindex);
//
//        JedisPoolConfig jedisPoolConfig = jedisConnectionFactory.getPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        System.out.println("redis地址：" + host + ":" + port);
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(RedisTemplate.class)
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
}
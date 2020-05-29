package com.yh.demo.starter.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc 自定义自动配置加载类：
 * 1、编写属性配置类，读取application.properties文件初始化属性
 * 2、编写实体类。
 * 3、编写自定义自动加载配置类，matchIfMissing=true默认注入，否则需匹配值才注入
 * 4、在配置类中根据HelloProperties提供的参数，并通过ConditionalOnMissingBean判断
 * 容器中没有bean时，注入helloService。
 */
@Configuration
@EnableConfigurationProperties(HelloProperties.class)
@ConditionalOnClass(Hello.class)
@ConditionalOnProperty(
        prefix = "hello",
        value = "enabled",
        havingValue = "开启"
//        ,matchIfMissing = true
)
public class HelloAutoConfiguration {

    @Autowired
    private HelloProperties helloProperties;

    /**
     * 容器中没有指定的Hello实例时进行初始化
     */
    @Bean
    @ConditionalOnMissingBean(Hello.class)
    public Hello hello() {
        return new Hello(this.helloProperties.getMsg(), this.helloProperties.getMembers());
    }
}
package com.yh.common.message.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动化加载配置
 *
 * @author yanghan
 * @date 2021/12/28
 */
@Configuration
@EnableConfigurationProperties({MessageProperties.class, EmailProperties.class, SmsProperties.class})
@ConditionalOnProperty(
        name = MessageProperties.PREFIX + "." + "enabled",
        havingValue = "true"
)
public class MessageAutoConfiguration {

    @Bean
    public AppFactory appFactory(){
        return new AppFactory();
    }
}

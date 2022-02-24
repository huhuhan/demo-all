package com.yh.common.job.config;

import com.yh.common.job.annotation.YhScheduledAnnotationBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

/**
 * 定时任务调度，参考 {@link ScheduledAnnotationBeanPostProcessor}
 *
 * @author yanghan
 * @date 2022/2/24
 */
@ConditionalOnProperty(value = YhScheduledProperties.PREFIX + ".enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(YhScheduledProperties.class)
public class YhScheduledAutoConfiguration {

    @Bean
    public YhScheduledAnnotationBeanPostProcessor yhScheduledAnnotationBeanPostProcessor(YhScheduledProperties yhScheduledProperties) {
        return new YhScheduledAnnotationBeanPostProcessor(yhScheduledProperties.isEnabled());
    }
}

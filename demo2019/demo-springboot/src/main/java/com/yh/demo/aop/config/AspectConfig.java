package com.yh.demo.aop.config;

import com.yh.demo.aop.aspect.DemoAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yanghan
 * @date 2021/5/7
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class AspectConfig {

    @Bean
    public DemoAspect demoAspect() {
        return new DemoAspect();
    }
}

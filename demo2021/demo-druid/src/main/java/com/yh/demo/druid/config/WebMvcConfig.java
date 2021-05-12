package com.yh.demo.druid.config;

import com.yh.demo.db.interceptor.DynamicDataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * webmvc配置
 *
 * @author yanghan
 * @date 2019/6/21
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private DynamicDataSourceInterceptor dynamicDataSourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dynamicDataSourceInterceptor);
    }

}

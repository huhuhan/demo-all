package com.yh.demo.ureport.config;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Servlet;

/**
 * @author yanghan
 * @date 2020/11/18
 */
@ImportResource("classpath:ureport-console-context.xml")
@Configuration
public class UReportConfig {
    @Bean
    public ServletRegistrationBean uReportServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean<Servlet>(new UReportServlet());
        bean.addUrlMappings("/ureport/*");
        return bean;
    }
}

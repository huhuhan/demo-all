package com.yh.demo.design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目引入了sql数据库spring的相关依赖，会默认找dataSource配置未见，可选不包括该配置启动exclude = DataSourceAutoConfiguration.class
 */
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
public class DesignSpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(DesignSpringApplication.class, args);
    }
}

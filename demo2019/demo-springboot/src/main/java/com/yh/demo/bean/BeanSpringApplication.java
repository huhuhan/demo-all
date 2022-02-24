package com.yh.demo.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
public class BeanSpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(BeanSpringApplication.class, args);
    }
}

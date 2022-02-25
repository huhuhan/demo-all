package com.yh.demo.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
public class ScheduleSpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(ScheduleSpringApplication.class, args);
    }
}

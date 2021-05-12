package com.yh.demo.druid;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class DemoDruidSpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoDruidSpringApplication.class, args);

    }
}

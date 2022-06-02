package com.yh.demo.search;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableSwagger2Doc
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DemoSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSearchApplication.class, args);
    }

}

package com.yh.demo.ureport;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoUReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoUReportApplication.class, args);
        log.info("open http://localhost:8279/ureport/designer");
    }

}

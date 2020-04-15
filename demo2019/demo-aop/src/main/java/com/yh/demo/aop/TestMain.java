package com.yh.demo.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring_aop.xml");

        HelloWorldService helloWorldService = (HelloWorldService) ctx.getBean("helloWorldService");
        helloWorldService.printHelloWorld();
        System.err.println();
        helloWorldService.doPrint();
    }
}

package com.yh.demo.aop.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml形式配置aop
 */
public class TestMain {
    public static void main(String[] args) {
        // 直接加载xml
        ApplicationContext ctx = new ClassPathXmlApplicationContext("aop/spring_aop.xml");

        HelloWorldService helloWorldService = (HelloWorldService) ctx.getBean("helloWorldService");

        helloWorldService.printHelloWorld();
        System.err.println();
        helloWorldService.doPrint();
    }
}

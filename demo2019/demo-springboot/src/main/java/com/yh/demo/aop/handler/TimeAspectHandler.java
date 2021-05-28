package com.yh.demo.aop.handler;

public class TimeAspectHandler {
    public void printTime() {
        System.out.println("CurrentTime = " + System.currentTimeMillis());
    }
}

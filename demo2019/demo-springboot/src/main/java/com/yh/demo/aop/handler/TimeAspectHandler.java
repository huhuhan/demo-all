package com.yh.demo.aop.handler;

public class TimeAspectHandler {
    public void printTime() {
        System.err.println("CurrentTime = " + System.currentTimeMillis());
    }
}

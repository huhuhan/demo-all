package com.yh.demo.aop;

public class TimeAspectHandler {
    public void printTime() {
        System.err.println("CurrentTime = " + System.currentTimeMillis());
    }
}

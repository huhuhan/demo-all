package com.yh.demo.aop.xml;

public class TimeAspectHandler {
    public void printTime() {
        System.err.println("CurrentTime = " + System.currentTimeMillis());
    }
}

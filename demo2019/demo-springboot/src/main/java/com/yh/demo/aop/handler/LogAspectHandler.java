package com.yh.demo.aop.handler;

public class LogAspectHandler {
    public void LogBefore() {
        System.out.println("Log before method");
    }

    public void LogAfter() {
        System.out.println("Log after method");
    }
}

package com.yh.demo.aop.handler;

public class LogAspectHandler {
    public void LogBefore() {
        System.err.println("Log before method");
    }

    public void LogAfter() {
        System.err.println("Log after method");
    }
}

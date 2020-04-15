package com.yh.demo.aop;

public class HelloWorldService {
    public void printHelloWorld() {
        System.out.println("Enter HelloWorldService.printHelloWorld()");
    }

    public void doPrint() {
        System.err.println("Enter HelloWorldService.doPrint()");
        return;
    }
}

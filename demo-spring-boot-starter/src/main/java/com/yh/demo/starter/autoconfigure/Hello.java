package com.yh.demo.starter.autoconfigure;

import java.util.ArrayList;
import java.util.List;

public class Hello {
    private String msg;

    private List<String> members = new ArrayList<>();

    public Hello(String msg, List<String> members) {
        this.msg = msg;
        this.members = members;
    }

    public void sayHello() {
        members.forEach(s -> System.out.println("Hello " + this.msg + " I am " + s));
    }
}

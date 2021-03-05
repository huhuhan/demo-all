package com.yh.demo.design.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 事件
 * @author yanghan
 * @date 2021/3/5
 */
public class AgeEvent extends ApplicationEvent {

    private String age;

    public AgeEvent(Object source, String age) {
        super(source);
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
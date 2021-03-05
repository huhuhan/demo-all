package com.yh.demo.design.listener;

import org.springframework.context.ApplicationEvent;

/**
 * 事件
 * @author yanghan
 * @date 2021/3/5
 */
public class NameEvent extends ApplicationEvent {

    private String name;

    public NameEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
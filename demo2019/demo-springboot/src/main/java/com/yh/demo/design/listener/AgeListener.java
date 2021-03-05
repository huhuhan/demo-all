package com.yh.demo.design.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

/**
 * 观察者或接收方
 * 被观察者或发布者：{@link SimpleApplicationEventMulticaster }
 * @author yanghan
 * @date 2021/3/5
 */
@Component
public class AgeListener implements ApplicationListener<AgeEvent> {
    @Override
    public void onApplicationEvent(AgeEvent mySubjectEvent) {
        System.out.println("event source: " + mySubjectEvent.getSource());
        System.out.println("event age: " + mySubjectEvent.getAge());
    }
}

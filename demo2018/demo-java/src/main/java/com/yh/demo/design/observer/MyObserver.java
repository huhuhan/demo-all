package com.yh.demo.design.observer;

/**
 * 观察者接口
 * @author yanghan
 * @date 2021/3/5
 */
public abstract class MyObserver {
    MySubject subject;

    abstract void update();
}
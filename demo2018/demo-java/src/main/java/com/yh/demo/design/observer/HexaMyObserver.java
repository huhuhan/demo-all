package com.yh.demo.design.observer;

import java.util.Observable;

/**
 * 观察者
 * @author yanghan
 * @date 2021/3/5
 */
public class HexaMyObserver extends MyObserver implements java.util.Observer {

    public HexaMyObserver(MySubject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Hex: " + Integer.toHexString(subject.getState()).toUpperCase());
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("java.util.Observer|Hex: " + Integer.toHexString(subject.getState()).toUpperCase());
    }
}
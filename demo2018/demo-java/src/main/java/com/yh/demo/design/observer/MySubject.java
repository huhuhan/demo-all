package com.yh.demo.design.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 被观察者
 * @author yanghan
 * @date 2021/3/5
 */
public class MySubject extends Observable{

    private List<MyObserver> observers = new ArrayList<MyObserver>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllMyObservers();

        // jdk内置的被观察者对象
        setChanged();
        super.notifyObservers();
    }

    public void attach(MyObserver observer) {
        observers.add(observer);
    }

    public void notifyAllMyObservers() {
        for (MyObserver observer : observers) {
            observer.update();
        }
    }
}
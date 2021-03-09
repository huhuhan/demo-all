package com.yh.demo.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 观察者模式
 *
 * @author yanghan
 * @date 2021/3/5
 */
public class ObserverPattern {
    public static void main(String[] args) {
        //  自定义观察器
        MySubject subject = new MySubject();
        HexaMyObserver hexa = new HexaMyObserver(subject);
        BinaryMyObserver binary = new BinaryMyObserver(subject);


        // jdk内置
        subject.addObserver(hexa);
        subject.addObserver(binary);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);

    }
}



/**
 * 被观察者
 * @author yanghan
 * @date 2021/3/5
 */
class MySubject extends Observable {

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

/**
 * 观察者接口
 * @author yanghan
 * @date 2021/3/5
 */
abstract class MyObserver {
    MySubject subject;

    abstract void update();
}

/**
 * 观察者
 * @author yanghan
 * @date 2021/3/5
 */
class HexaMyObserver extends MyObserver implements java.util.Observer {

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


/**
 * 观察者
 *
 * @author yanghan
 * @date 2021/3/5
 */
class BinaryMyObserver extends MyObserver implements java.util.Observer {

    public BinaryMyObserver(MySubject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Binary: " + Integer.toBinaryString(subject.getState()));
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("java.util.Observer|Binary: " + Integer.toOctalString(subject.getState()));
    }
}
package com.yh.demo.design;

import com.yh.demo.design.observer.BinaryMyObserver;
import com.yh.demo.design.observer.HexaMyObserver;
import com.yh.demo.design.observer.MySubject;

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





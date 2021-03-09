package com.yh.demo.design;

import java.util.Date;
import java.util.function.Supplier;

/**
 * 中介者模式
 * @author yanghan
 * @date 2021/3/8
 */
public class MediatorPattern {
    public static void main(String[] args) {
        User robert = new User("Robert");
        User john = new User("John");

        robert.sendMessage("Hi! John!");
        john.sendMessage("Hello! Robert!");

        john.showMessageByFn("Hello! Robert!");
    }
}

/**
 * 中介者，实际操作对象，但不直接显现
 */
class ChatRoom {
    public static void showMessage(User user, String message) {
        System.out.println(new Date().toString()
                + " [" + user.getName() + "] : " + message);
    }

    public static void showMessage(Supplier<String> sendMsg) {
        System.out.println(new Date().toString()
                + " : " + sendMsg.get());
    }
}

class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    /**
     * 隐藏实际的调用对象，简化对象引用关系
     */
    public void sendMessage(String message) {
        ChatRoom.showMessage(this, message);
    }

    public void showMessageByFn(String message) {
        ChatRoom.showMessage(() -> "[" + name + "]" + message);
    }
}
package com.yh.demo.java8;

/**
 * 测试类
 * @author yanghan
 * @date 2020/12/14
 */
public class MySystemOutPrint<T> {
    public MySystemOutPrint() {
        System.out.println("无参构造方法");
    }

    public MySystemOutPrint(T t) {
        System.out.println("构造方法 | " + t);
    }

    public static void staticMyPrint(Object t) {
        System.out.println("静态方法 | " + t);
    }

    public void myPrint(T t) {
        System.out.println("实例方法 | " + t);
    }

}

package com.yh.demo.java8;

/**
 * 函数式接口，demo
 * 指定泛型，方便传参
 *
 * @author yanghan
 * @date 2020/12/14
 */
@FunctionalInterface
public interface MyFunctionalInterface<T> {

    /**
     * 唯一抽象方法
     *
     * @param t
     */
    void todo(T t);

    /**
     * 默认方法，可有多个
     *
     * @param t
     */
    default void defaultTodo(T t) {
        System.out.println("函数式接口，允许有【默认方法】| " + t);
    }

    /**
     * 静态方法，可有多个
     */
    static void staticTodo() {
        System.out.println("函数式接口，允许有【静态方法】");
    }
}

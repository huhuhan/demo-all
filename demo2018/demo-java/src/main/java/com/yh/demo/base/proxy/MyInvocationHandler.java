package com.yh.demo.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理，必须用到的接口，用于执行目标对象的方法
 *
 * @author yanghan
 * @date 2021/5/21
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * Debug模式下，会发现多出ToString的代理方法，因为生成的代理类($ProxyX)的toString方法内部执行也是通过invoke调用，才会循环调用。
     * 优化方式：method.invoke的执行用第三方工具类执行，或者用其他代理方式，比如CGLIB
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象的InvocationHandler将调用目标对象方法：" + method.getName());
        long starTime = System.nanoTime();
        // 执行目标对象的方法
        method.invoke(this.target, args);
        long endTime = System.nanoTime();
        System.out.println(String.format("%s的%s方法耗时(纳秒):%s", this.target.getClass(), method.getName(), endTime - starTime));
        return null;
    }
}

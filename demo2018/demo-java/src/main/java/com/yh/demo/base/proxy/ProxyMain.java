package com.yh.demo.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author yanghan
 * @date 2021/5/21
 */
public class ProxyMain {

    public static void main(String[] args) {
        jdkProxy();
    }

    /**
     * jdk动态代理例子
     * 特点：被代理类必须有实现接口，最终代理的实例对象，声明为接口类型
     */
    private static void jdkProxy() {
        // 目标类
        Class<?> targetClass = IAaServiceImpl.class;
        // 目标对象
        IAaServiceImpl target = new IAaServiceImpl();
        // 代理，必须的接口，关键方法method.invoke
        InvocationHandler invocationHandler = new MyInvocationHandler(target);
        // 代理对象，jdk要求只能代理接口类型，即声明类型只能是IAaService
        IAaService proxyInstance = (IAaService) Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), invocationHandler);
        proxyInstance.a1();


        // 代理类
        Class<?> proxyClass = Proxy.getProxyClass(targetClass.getClassLoader(), targetClass.getInterfaces());
        try {
            // 代理类的getConstructor获取代理对象
            IBbService bbProxy = (IBbService) proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
            bbProxy.b1();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

package com.yh.demo.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * jdk代理的特点：被代理类必须有实现接口，最终代理的实例对象，声明为接口类型
 * @author yanghan
 * @date 2021/5/21
 */
public class ProxyMain {

    public static void main(String[] args) {
        jdkStaticProxy();
        System.out.println("--------分割线---------------");
        jdkProxy();
    }

    /**
     * jdk动态代理
     * 编译阶段没有代理对象的class文件，实际运行时，生成class并加载到JVM中
     * 关键接口：{@link InvocationHandler} 其invoke方法通过反射机制调用目标方法
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


    /**
     * jdk静态代理
     * 代理对象已写好java文件，编译阶段生成class文件
     * 缺点：要提前写好大量代码
     * ps：代理模式的应用
     */
    public static void jdkStaticProxy(){
        new IAaServiceProxyImpl(new IAaServiceImpl()).a1();
    }
}

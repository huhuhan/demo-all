package com.yh.demo.aop.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * PointCut的不同用法例子
 * @author yanghan
 * @date 2021/5/7
 */
public class PointCutExample {

    /**
     * 匹配方法： 某类的xx右模糊方法，无参方法
     */
    @Pointcut("execution(* com.yh.demo.aop.service.impl.C2.c2*())")
    public void byExecution() {
        System.out.println("@Pointcut");
    }

    /**
     * 匹配方法： 某包下xx类右模糊的所有方法，有参方法
     */
    @Pointcut("execution(* com.yh.demo.aop.service.impl.C*.*(*))")
    public void byExecution2() {
    }

    /**
     * 匹配方法： 某包名下所有类的所有方法，有参或无参
     */
    @Pointcut("execution(* com.yh.demo.aop.service..*(..))")
    public void byExecution3() {
    }

    /**
     * 匹配Clazz类型： 某类
     */
    @Pointcut("within(com.yh.demo.aop.service.impl.C2)")
    public void byWithin() {
    }

    /**
     * 匹配Clazz类型： 某类以及所有子类
     */
    @Pointcut("within(com.yh.demo.aop.service.impl.C1+)")
    public void byWithin2() {
    }

    /**
     * 匹配: 代理对象的类型等于指定类型
     */
    @Pointcut("this(com.yh.demo.aop.service.impl.C3)")
    public void byThis() {
    }

    /**
     * 匹配：目标对象的类型等于指定类型
     */
    @Pointcut("target(com.yh.demo.aop.service.InterfaceC)")
    public void byTarget() {
    }

    /**
     * 测试无效
     */
    @Pointcut("args(String)")
    public void byArgs() {
    }

    /**
     * 有注解的方法
     */
    @Pointcut("@annotation(com.yh.demo.aop.annotation.DemoAop)")
    public void byAnnotation() {
    }

    /**
     * 有注解的类
     */
    @Pointcut("@within(com.yh.demo.aop.annotation.DemoAopClass)")
    public void byAtWithin() {
    }

    /**
     * 测试无效
     */
    @Pointcut("@target(com.yh.demo.aop.annotation.DemoAopClass)")
    public void byAtTarget() {
    }

    /**
     * 测试无效
     */
    @Pointcut("@args(com.yh.demo.aop.annotation.DemoAop)")
    public void byAtArgs() {
    }


    /**
     * 容器中的beanId
     */
    @Pointcut("bean(demoServiceImpl)")
    public void byBean() {
    }
}

package com.yh.demo.aop.service.impl;

import com.yh.demo.aop.annotation.DemoAop;

/**
 * @author yanghan
 * @date 2021/5/7
 */
public class C2 extends C1  {

    /**
     * 重写父类方法
     */
    @Override
    public void c1Todo() {
        System.err.println("by C2 extends C1");
    }

    @DemoAop
    public void c2Todo() {
        System.err.println("by C2");
    }

    public void c2TodoByParam(String param) {
        System.err.println("by C2 with " + param);
    }

}

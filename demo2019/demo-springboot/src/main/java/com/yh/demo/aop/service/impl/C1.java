package com.yh.demo.aop.service.impl;

import com.yh.demo.aop.annotation.DemoAopClass;

/**
 * @author yanghan
 * @date 2021/5/7
 */
@DemoAopClass
public class C1 {

    public void c1Todo(){
        System.err.println("by C1");
    }
}

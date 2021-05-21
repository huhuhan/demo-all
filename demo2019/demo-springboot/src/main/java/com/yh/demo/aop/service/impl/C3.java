package com.yh.demo.aop.service.impl;

import com.yh.demo.aop.service.InterfaceC;

/**
 * @author yanghan
 * @date 2021/5/7
 */
public class C3 implements InterfaceC {

    @Override
    public void todo() {
        System.err.println("by C3 extends C2 extends C1");
    }


    public String c3Todo(){
        System.err.println("by c3");
        this.todo();
        return "c3Todo";
    }
}

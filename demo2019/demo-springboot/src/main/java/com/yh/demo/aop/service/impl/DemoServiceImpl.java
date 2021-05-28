package com.yh.demo.aop.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author yanghan
 * @date 2021/5/7
 */
@Service
public class DemoServiceImpl {

    public String demo() throws Exception {
        System.out.println("by DemoServiceImpl");
        //throw new Exception(" Demo Error");
        return "demoValue";
    }
}

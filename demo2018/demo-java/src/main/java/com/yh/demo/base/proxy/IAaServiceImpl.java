package com.yh.demo.base.proxy;

import com.yh.demo.base.proxy.IAaService;

/**
 * @author yanghan
 * @date 2021/5/21
 */
public class IAaServiceImpl implements IAaService, IBbService {
    @Override
    public void a1() {
        System.out.println("this is IAaService.a1");
    }

    @Override
    public void b1() {
        System.out.println("this is IBbService.b1");
    }
}

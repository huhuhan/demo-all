package com.yh.demo.base.proxy;

/**
 * @author yanghan
 * @date 2021/5/26
 */
public class IAaServiceProxyImpl implements IAaService {

    private IAaServiceImpl target;

    public IAaServiceProxyImpl(IAaServiceImpl target) {
        this.target = target;
    }

    @Override
    public void a1() {
        System.err.println("静态代理：前置内容");

        target.a1();

        System.err.println("静态代理：后置内容");
    }
}

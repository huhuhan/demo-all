package com.yh.demo.aop;

import com.yh.demo.aop.service.impl.C3;
import org.junit.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;


/**
 * Cglib 代理，相对jdk代理，可以支持普通类，更高效，spring默认集成，根据配置判断自动选择代理方式，优先cglib
 * @author yanghan
 * @return
 * @date 2021/5/21
 */
public class CglibTests {
    @Test
    public void test1() throws Exception {
        // 通过Enhancer类创建代理类
        Enhancer enhancer = new Enhancer();
        // 指定目标类
        enhancer.setSuperclass(C3.class);

        Callback[] callbacks = {
                // 1. MethodInterceptor拦截器，调用代理对象的任何方法，都会拦截。可实现前置、后置、环绕的效果
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        System.out.println("调用方法：" + method);
                        // Super的意思，是因为cglib底层是创建目标类的子类，来覆盖目标类的方法。与jdk的反射机制不同
                        Object result = methodProxy.invokeSuper(o, objects);
                        return "111";
                    }
                } ,
                // 2. 直接执行代理对象的方法，不添加额外方法。
                NoOp.INSTANCE,
                // 3. 固定值
                new FixedValue() {
                    @Override
                    public Object loadObject() throws Exception {
                        return "cglib test";
                    }
                }
        };
        // 添加Callback接口
//        enhancer.setCallback(NoOp.INSTANCE);

        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                // Callback[]数组的index下标
                return 0;
            }
        });
        // 扩展，可以用CallbackHelper类

        C3 proxy = (C3) enhancer.create();
        proxy.todo();
        System.out.println(proxy.c3Todo());
    }
}
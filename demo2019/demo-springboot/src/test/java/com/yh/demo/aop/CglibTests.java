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

    /**
     * cglib的执行目标方法的原理：Fastclass机制
     */
    @Test
    public void fastClassTest(){
        //这里，tt可以看作目标对象，fc可以看作是代理对象；首先根据代理对象的getIndex方法获取目标方法的索引，
        //然后再调用代理对象的invoke方法就可以直接调用目标类的方法，避免了反射
        CglibFastclassTest1 tt = new CglibFastclassTest1();
        CglibFastclassTest2 fc = new CglibFastclassTest2();
        int index = fc.getIndex("g()V");
        fc.invoke(index, tt, null);
    }
}




class CglibFastclassTest1{
    void f(){
        System.out.println("f method");
    }

    void g(){
        System.out.println("g method");
    }
}
class CglibFastclassTest2{
    Object invoke(int index, Object o, Object[] ol){
        CglibFastclassTest1 t = (CglibFastclassTest1) o;
        switch(index){
            case 1:
                t.f();
                return null;
            case 2:
                t.g();
                return null;
        }
        return null;
    }
    //这个方法对CglibFastclassTest1类中的方法建立索引
    int getIndex(String signature){
        switch(signature.hashCode()){
            //
            case 3078479:
                System.out.println("f()V".hashCode());
                return 1;
            case 3108270:
                System.out.println("g()V".hashCode());
                return 2;
        }
        return -1;
    }
}
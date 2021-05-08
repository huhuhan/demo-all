package com.yh.demo.aop;

import com.yh.demo.aop.annotation.DemoAop;
import com.yh.demo.aop.annotation.DemoAopClass;
import com.yh.demo.aop.aspect.TestAspect;
import com.yh.demo.aop.service.InterfaceC;
import com.yh.demo.aop.service.ToDoService;
import com.yh.demo.aop.service.impl.C1;
import com.yh.demo.aop.service.impl.C2;
import com.yh.demo.aop.service.impl.C3;
import com.yh.demo.aop.service.impl.DemoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoAopApplicationTests {

    @Autowired
    private ToDoService toDoService;
    @Autowired
    private DemoServiceImpl demoServiceImpl;

    @Test
    public void testDemo() throws Exception {
        // 切面只拦截注入容器的Bean
        demoServiceImpl.demo();

        //new DemoServiceImpl().demo();
    }

    /**
     * 测试【@within】
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        // 父类
        C1 c1 = new C1();
        proxyFactory.setTarget(c1);
        proxyFactory.setProxyTargetClass(false);
        proxyFactory.addAspect(TestAspect.class);
        C1 c1Proxy = proxyFactory.getProxy();
        c1Proxy.c1Todo();


        // @within
        System.out.println("目标类上是否有 @DemoAopClass 注解：" + (c1.getClass().getAnnotation(DemoAopClass.class) != null));
    }

    /**
     * 测试【execution】【within】【@annotation】【】【】
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        // 子类
        C2 c2 = new C2();
        proxyFactory.setTarget(c2);
        proxyFactory.addAspect(TestAspect.class);
        C2 c2Proxy = proxyFactory.getProxy();
        c2Proxy.c1Todo();
        c2Proxy.c2Todo();
        c2Proxy.c2TodoByParam("yh");

        // @annotation
        System.out.println("目标方法上是否有 @DemoAop 注解：" + (c2Proxy.getClass().getAnnotation(DemoAop.class) != null));
    }

    /**
     * 测试【this】【target】
     * 代理接口，默认用JDK动态代理，${@link org.springframework.aop.framework.DefaultAopProxyFactory}
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        C3 c3 = new C3();
        proxyFactory.setTarget(c3);
        //获取目标对象上的接口列表
        Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(c3);
        //设置需要代理的接口
        proxyFactory.setInterfaces(allInterfaces);
        proxyFactory.addAspect(TestAspect.class);
        // 改用CGLib代理，切面才成实现
        proxyFactory.setProxyTargetClass(true);
        InterfaceC interfaceC = proxyFactory.getProxy();
        interfaceC.todo();

        // this：判断代理对象是否指定类型
        System.out.println("proxy是否是jdk动态代理对象：" + AopUtils.isJdkDynamicProxy(interfaceC));
        System.out.println("proxy是否是cglib代理对象：" + AopUtils.isCglibProxy(interfaceC));
        System.out.println("this用法：匹配代理对象类型 | " + InterfaceC.class.isAssignableFrom(interfaceC.getClass()));

        // target：判断目标对象是否指定类型
        System.out.println("target用法：匹配目标对象类型 | " + C3.class.isAssignableFrom(c3.getClass()));
    }


    @Test
    public void test() throws Exception {
        toDoService.toSelect();
        toDoService.toInsert();
        toDoService.toDelete();
        toDoService.toUpdate();
    }
}
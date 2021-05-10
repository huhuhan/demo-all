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
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

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


    /**
     * 源码理解案例
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        // 创建pointcut
        Pointcut pointcut = new Pointcut() {
            /**
             * 匹配类
             * @return
             */
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> aClass) {
                        System.out.println("待验证类：" + aClass.getName());
                        return aClass.isAssignableFrom(C2.class);
                    }
                };
            }

            /**
             * 匹配方法
             * @return
             */
            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {
                    /**
                     * 静态规则，优先匹配
                     * @param method
                     * @param targetClass
                     * @return
                     */
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        System.out.println("待验证类的方法：" + method.getName());
                        return method.getName().toLowerCase().contains("todo");
                    }

                    /**
                     * 是否开启运行时规则
                     * @return
                     */
                    @Override
                    public boolean isRuntime() {
                        return true;
                    }

                    /**
                     * 运行时规则，isRuntime()为true，才触发
                     * @param method
                     * @param targetClass
                     * @param args
                     * @return
                     */
                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        System.out.println("待验证类的方法的参数值：" + Arrays.toString(args));
                        // 根据实际参数判断是否切入增强
                        if (args.length == 1) {
                            String userName = (String) args[0];
                            return userName.contains("yh");
                        }
                        return false;
                    }
                };
            }
        };
        // 创建通知：用MethodBeforeAdvice类型，即前置通知
        MethodBeforeAdvice advice1 = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] objects, Object o) throws Throwable {
                System.out.println("这是" + method.getName() + "的前置通知！");
            }
        };
        //创建通知：用MethodInterceptor类型，灵活使用，这里模拟环绕通知
        MethodInterceptor advice2 = new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("环绕开始：" + invocation.getMethod());
                long starTime = System.nanoTime();
                // 调用方法
                Object result = invocation.proceed();
                System.out.println("环绕结束，方法耗时(纳秒)：" + (System.nanoTime() - starTime));
                return result;
            }
        };

        //创建Advisor，组装
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(pointcut, advice1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(pointcut, advice2);

        // 目标对象
        C2 target = new C2();
        //用代理工厂创建代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(advisor1);
        proxyFactory.addAdvisors(advisor2);
        C2 c2Proxy = (C2) proxyFactory.getProxy();
        //调用代理对象的方法
        c2Proxy.c2TodoByParam("yh");
    }
}
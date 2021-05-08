package com.yh.demo.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author yanghan
 * @date 2021/5/7
 */
@Aspect
public class TestAspect {

    /**
     * 环绕切面，测试选择不同的Pointcut
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("com.yh.demo.aop.aspect.PointCutExample.byWithin2() || com.yh.demo.aop.aspect.PointCutExample.byThis()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("===环绕开始===@Pointcut匹配到【" + joinPoint + "】");
        joinPoint.proceed();
        System.out.println("===环绕结束===");
        return null;
    }

}

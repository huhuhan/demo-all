package com.yh.demo.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Aspect的通知类型
 * @author yanghan
 * @date 2021/5/7
 */
@Aspect
public class DemoAspect {
    @PostConstruct
    public void init() {
        System.out.println("DemoAspect init");
    }

    /**
     * 容器中的beanId
     */
    @Pointcut("com.yh.demo.aop.aspect.PointCutExample.byBean())")
    public void toPointCut() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @throws Throwable
     * @return：目标方法的返回值
     */
    @Around("toPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=========######【使用AspectJ实现环绕通知】######开始=========");
        // joinPoint.proceed() 表示执行实际方法
        Object returnValue = joinPoint.proceed();
        System.out.println("=========######【使用AspectJ实现环绕通知】######结束=========");
        return returnValue;
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("toPointCut()")
    public void toBefore(JoinPoint joinPoint) {
        System.out.println("=========【使用AspectJ实现前置通知】开始=========");
        System.out.println("目标对象：" + joinPoint.getTarget());
        System.out.println("目标方法：" + joinPoint.getSignature().getName());
        System.out.println("参数列表：" + Arrays.toString(joinPoint.getArgs()));
        System.out.println("=========【使用AspectJ实现前置通知】结束=========");
    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param ex：异常对象
     */
    @AfterThrowing(value = "toPointCut()", throwing = "ex")
    public void throwingAdvice(JoinPoint joinPoint, Exception ex) {
        System.out.println("=========【使用AspectJ实现异常通知】开始=========");
        System.out.println("目标对象：" + joinPoint.getTarget());
        System.out.println("目标方法：" + joinPoint.getSignature().getName());
        System.out.println("出现异常：" + ex.getMessage());
        System.out.println("=========【使用AspectJ实现异常通知】结束=========");
    }

    /**
     * 后置通知(无返回值)
     *
     * @param joinPoint
     */
//    @After("toPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("=========【使用AspectJ实现后置通知】开始=========");
        System.out.println("目标对象：" + joinPoint.getTarget());
        System.out.println("目标方法：" + joinPoint.getSignature().getName());
        System.out.println("参数列表：" + Arrays.toString(joinPoint.getArgs()));
        System.out.println("=========【使用AspectJ实现后置通知】结束=========");
    }

    /**
     * 后置通知(有返回值)
     *
     * @param joinPoint
     * @param returnValue：返回值
     * @return：目标方法的返回值
     */
    @AfterReturning(value = "toPointCut()", returning = "returnValue")
    public Object afterReturningAdvice(JoinPoint joinPoint, Object returnValue) {
        System.out.println("=========【使用AspectJ实现后置通知】开始=========");
        System.out.println("目标对象：" + joinPoint.getTarget());
        System.out.println("目标方法：" + joinPoint.getSignature().getName());
        System.out.println("参数列表：" + Arrays.toString(joinPoint.getArgs()));
        System.out.println("返回值：" + returnValue);
        System.out.println("=========【使用AspectJ实现后置通知】结束=========");
        return returnValue;
    }


}

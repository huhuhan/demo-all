package com.yh.demo.db.interceptor;

import com.yh.demo.db.annotation.DS;
import com.yh.demo.db.dynamic.MyDynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * 数据源路由注解
 *
 * @author yanghan
 * @date 2021/5/6
 */
@Aspect
@Slf4j
public class DSAspect {

    /**
     * 切面扫描
     */
    @Pointcut("@annotation(com.yh.demo.db.annotation.DS)")
    public void dataSourcePointCut() {
    }

    /**
     * 扫描到切面后，先执行该方法
     *
     * @param joinPoint
     */
    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint) {
        try {
            // 方法注解
            DS ds = this.getDSByMethod(joinPoint);
            if (null != ds) {
                String dataSourceName = ds.value();
                MyDynamicDataSourceContextHolder.setDataSourceType(dataSourceName);
                log.info("切面【动态数据库源】，当前线程 " + Thread.currentThread().getName() + " 采用 " + dataSourceName + " 数据源");
            } else {
                log.info("切面【动态数据库源】，采用默认数据库源");
            }
        } catch (Exception e) {
            log.error("切面【动态数据库源】异常，当前线程：{}", Thread.currentThread().getName(), e);
        }
    }

    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint) {
        MyDynamicDataSourceContextHolder.clearDataSourceType();
    }


    private DS getDSByClazzInterface(JoinPoint joinPoint) {
        Optional<DS> optional = Arrays.stream(joinPoint.getClass().getInterfaces())
                .map(m -> m.getAnnotation(DS.class))
                .findFirst();
        return optional.orElse(null);
    }

    private DS getDSByClazz(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getAnnotation(DS.class);
    }

    private DS getDSByMethod(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (method != null && method.isAnnotationPresent(DS.class)) {
            return method.getAnnotation(DS.class);
        }
        return null;
    }

}

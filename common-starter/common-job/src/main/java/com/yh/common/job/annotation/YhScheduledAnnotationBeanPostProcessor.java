package com.yh.common.job.annotation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.yh.common.job.handler.YhScheduledExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务调度，参考 {@link ScheduledAnnotationBeanPostProcessor}
 * 基于 {@link cn.hutool.cron.CronUtil} 实现
 *
 * @author yanghan
 * @date 2022/2/24
 */
@Slf4j
public class YhScheduledAnnotationBeanPostProcessor implements BeanPostProcessor, DisposableBean, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    private boolean enableScheduled;

    private final Map<Object, Set<Method>> scheduledMethodMap = new HashMap<>(16);

    public YhScheduledAnnotationBeanPostProcessor(boolean enableScheduled) {
        this.enableScheduled = enableScheduled;
    }

    /**
     * 初始化后置通知，将定时任务调度注解的方法记录下来
     *
     * @param bean     1
     * @param beanName 1
     * @return 1
     * @throws BeansException 1
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (enableScheduled) {
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
            Set<Method> methods = MethodIntrospector.selectMethods(targetClass, (ReflectionUtils.MethodFilter) method -> AnnotationUtils.getAnnotation(method, YhScheduled.class) != null);
            if (CollUtil.isNotEmpty(methods)) {
                scheduledMethodMap.put(bean, methods);
            }
        }
        return bean;
    }

    @Override
    public void destroy() throws Exception {
        // 关闭
        CronUtil.stop();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext() == this.applicationContext) {
            this.finishRegistration();
        }
    }

    private synchronized void finishRegistration() {
        if (!CronUtil.getScheduler().isStarted()) {
            // 支持秒级别定时任务
            CronUtil.setMatchSecond(true);
            CronUtil.start();
        }

        // 获取定时任务调度异常处理类
        Map<String, YhScheduledExceptionHandler> handlerMap = applicationContext.getBeansOfType(YhScheduledExceptionHandler.class);

        for (Map.Entry<Object, Set<Method>> entry : scheduledMethodMap.entrySet()) {
            for (Method method : entry.getValue()) {
                YhScheduled scheduled = method.getAnnotation(YhScheduled.class);
                Object[] params = this.initialMethodParameterZero(method.getParameterTypes());
                CronTask cronTask = new CronTask(entry.getKey(), method, params, handlerMap.values());
                // 动态添加定时任务
                CronUtil.schedule(scheduled.cron(), cronTask);
            }
        }
        scheduledMethodMap.clear();
    }

    /**
     * 初始化方法参数零值
     *
     * @param parameterTypeClass 参数类型
     * @return 初始零值
     */
    private Object[] initialMethodParameterZero(Class[] parameterTypeClass) {
        if (parameterTypeClass == null || parameterTypeClass.length == 0) {
            return null;
        }
        Object[] parameter = new Object[parameterTypeClass.length];
        for (int i = 0, l = parameterTypeClass.length; i < l; i++) {
            Class parameterType = parameterTypeClass[i];
            Object value = null;
            if (boolean.class.equals(parameterType)) {
                value = false;
            } else if (parameterType.isPrimitive()) {
                value = (byte) 0;
            }
            parameter[i] = value;
        }
        return parameter;
    }

    private static class CronTask implements Task {

        private Object object;

        private Method method;

        private Object[] methodParameter;

        private Collection<YhScheduledExceptionHandler> yhScheduledExceptionHandlers;

        CronTask(Object object, Method method, Object[] methodParameter, Collection<YhScheduledExceptionHandler> yhScheduledExceptionHandlers) {
            this.object = object;
            this.method = method;
            this.methodParameter = methodParameter;
            this.yhScheduledExceptionHandlers = yhScheduledExceptionHandlers;
        }

        @Override
        public void execute() {
            try {
                // 反射方法，调用定时任务调度方法
                method.invoke(object, methodParameter);
            } catch (Throwable t) {
                this.notifyExceptionHandler(t);
            }
        }

        private void notifyExceptionHandler(Throwable throwable) {
            if (yhScheduledExceptionHandlers == null || yhScheduledExceptionHandlers.isEmpty()) {
                log.error("schedule {} exception", method.toString(), throwable);
            } else {
                // 异常通知处理
                for (YhScheduledExceptionHandler yhScheduledExceptionHandler : yhScheduledExceptionHandlers) {
                    yhScheduledExceptionHandler.exception(object, method, throwable);
                }
            }
        }
    }
}

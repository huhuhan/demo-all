package com.yh.auth.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * ApplicationContext 工具类
 *
 * @author yanghan
 * @date 2020/6/9
 */
@Component
@Slf4j
public class MySpringUtils implements ApplicationContextAware {


    private static ApplicationContext context;


    /**
     * spring 初始化，将容器上下文对象注入
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MySpringUtils.context = applicationContext;
    }

    /**
     * 获取spring容器上下文。
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicaitonContext() {
        return context;
    }

    /**
     * 根据beanId获取配置在系统中的对象实例。
     *
     * @param beanId
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanId) {
        try {
            if (context.containsBean(beanId)) {
                return (T) context.getBean(beanId);
            }
        } catch (Exception ex) {
            log.debug("getBean:" + beanId + "," + ex.getMessage());
        }
        return null;
    }

    /**
     * 获取Spring容器的Bean
     *
     * @param beanClass
     * @return T
     * @throws
     * @since 1.0.0
     */
    public static <T> T getBean(Class<T> beanClass) {
        try {
            return context.getBean(beanClass);
        } catch (Exception ex) {
            log.debug("getBean:" + beanClass + "," + ex.getMessage());
        }
        return null;
    }


    /**
     * 获取接口的实现类实例。
     *
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> getImplInstance(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }


    /**
     * 发布事件。
     *
     * @param event void
     */
    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            context.publishEvent(event);
        }
    }
}

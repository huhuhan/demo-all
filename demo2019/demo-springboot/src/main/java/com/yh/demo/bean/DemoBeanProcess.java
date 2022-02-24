package com.yh.demo.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * IOC 容器机制，Spring Bean 装配到容器中的过程
 *
 * @author yanghan
 * @date 2022/2/24
 */
@Component
public class DemoBeanProcess implements
        BeanNameAware,
        BeanFactoryAware,
        ApplicationContextAware,
        InitializingBean,
        BeanPostProcessor,
        ApplicationListener<ContextRefreshedEvent>,
        DisposableBean {

    private final ApplicationContext applicationContext;

    public DemoBeanProcess(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println(String.format("%s：DemoBeanProcess 实例化-设置属性", 1));
    }

    @Override
    public void setBeanName(String s) {
        System.out.println(String.format("%s：BeanNameAware.setBeanName 设置Bean ID或Name", 2));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(String.format("%s：BeanFactoryAware.setBeanFactory 设置Bean工厂", 3));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(String.format("%s：ApplicationContextAware.setApplicationContext 设置上下文对象", 4));
    }


    /**
     * java自带注解
     * BeanPostProcessor的实现类CommonAnnotationBeanPostProcessor的前置通知处理
     */
    @PostConstruct
    public void initTest() {
        System.out.println(String.format("%s：@PostConstruct java自带注解，服务器加载Servlet时触发", 5));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(String.format("%s：InitializingBean.afterPropertiesSet", 6));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(String.format("%s：BeanPostProcessor.BeforeInitialization - %s", 7, beanName));
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(String.format("%s：BeanPostProcessor.AfterInitialization - %s", 8, beanName));
        return null;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext() == this.applicationContext) {
            System.out.println(String.format("%s：ApplicationListener<ContextRefreshedEvent>.onApplicationEvent 实例化结束发动事件通知", 9));
        }

    }

    /**
     * java自带注解
     * BeanPostProcessor的实现类CommonAnnotationBeanPostProcessor的后置通知处理
     */
    @PreDestroy
    public void destroyTest() {
        System.out.println(String.format("%s：@PreDestroy java自带注解，服务器销毁Servlet时触发", 10));
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(String.format("%s：DisposableBean.destroy 最终销毁方法", 11));
    }

}

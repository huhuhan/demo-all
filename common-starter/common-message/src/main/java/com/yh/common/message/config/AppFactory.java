package com.yh.common.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author yanghan
 * @date 2022/1/20
 */
@Slf4j
public class AppFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        AppFactory.context = applicationContext;

    }

    /**
     * 获取Spring容器的Bean
     *
     * @author yanghan
     * @date 2022/1/20
     */
    public static <T> T getBean(Class<T> beanClass) {
        try {
            return context.getBean(beanClass);
        } catch (Exception ex) {
            log.debug("getBean:" + beanClass + "," + ex.getMessage());
        }
        return null;
    }

}

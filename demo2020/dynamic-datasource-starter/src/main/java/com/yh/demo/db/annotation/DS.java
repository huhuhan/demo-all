package com.yh.demo.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库源注解
 * 用于切面拦截解析
 *
 * @author yanghan
 * @date 2021/5/6
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {
    /** 数据库源，注入Bean的唯一ID */
    String value();
}

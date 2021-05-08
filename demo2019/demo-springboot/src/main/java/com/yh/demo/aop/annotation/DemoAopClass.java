package com.yh.demo.aop.annotation;

import java.lang.annotation.*;

/**
 * 类注解
 * @author yanghan
 * @date 2021/5/7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DemoAopClass {
    String value() default "xx";
}

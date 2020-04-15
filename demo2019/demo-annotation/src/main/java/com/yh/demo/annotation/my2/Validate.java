package com.yh.demo.annotation.my2;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Validate {
    int min() default 1;

    int max() default 10;

    boolean isNotNull() default true;
}
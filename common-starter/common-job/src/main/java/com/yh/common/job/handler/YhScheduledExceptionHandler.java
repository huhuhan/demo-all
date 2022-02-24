package com.yh.common.job.handler;

import java.lang.reflect.Method;

/**
 * 调度异常处理器
 *
 * @author wacxhs
 */
public interface YhScheduledExceptionHandler {


    /**
     * 异常处理
     *
     * @param o         调用对象
     * @param method    调用方法
     * @param throwable 异常信息
     */
    void exception(Object o, Method method, Throwable throwable);

}

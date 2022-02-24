package com.yh.common.job.annotation;

import org.springframework.scheduling.annotation.Scheduled;

import java.lang.annotation.*;

/**
 * 定时任务调度，参考 {@link Scheduled}
 *
 * @author yanghan
 * @date 2022/2/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface YhScheduled {
    /**
     * cron表达式
     *
     * @return cron表达式
     */
    String cron();
}

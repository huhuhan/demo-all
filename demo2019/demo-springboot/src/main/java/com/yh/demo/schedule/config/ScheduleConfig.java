package com.yh.demo.schedule.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author yanghan
 * @date 2022/2/24
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(10);
        //设定一个长度10的定时任务线程池
        scheduledTaskRegistrar.setScheduler(threadPool);
    }
}

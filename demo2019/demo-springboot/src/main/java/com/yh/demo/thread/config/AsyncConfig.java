package com.yh.demo.thread.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 启动异步调用
 * @author yanghan
 * @date 2020/4/21
 */
@EnableAsync
@Configuration
public class AsyncConfig {
    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    /**
     * 自定义异步线程池
     * @return
     */
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-Executor");
        executor.setMaxPoolSize(10);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        // 使用预定义的异常处理类
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        log.info("初始化自定义异步线程池");
        return executor;
    }

}

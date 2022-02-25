package com.yh.demo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author yanghan
 * @date 2022/2/24
 */
@Component
@Slf4j
public class DemoSchedule {

    @Scheduled(cron = "0/10 * * * * ?")
    public void test1() {
        log.info("10秒一次：{}", LocalDateTime.now());
    }

    @Scheduled(fixedRate = 5 * 1000)
    public void test2() throws Exception {
        log.info("固定频率，5秒一次，算上方法执行时间：{}", LocalDateTime.now());
        Thread.sleep(1000L);
    }

    @Scheduled(fixedDelay = 2 * 1000)
    public void test3() throws Exception {
        log.info("固定间隔时间（毫秒），2秒一次，算上方法执行时间：{}", LocalDateTime.now());
        Thread.sleep(1000L);
    }

    @Scheduled(initialDelay = 3 * 1000, fixedRate = 3 * 1000)
    public void test4() throws Exception {
        log.info("启动后延迟执行（毫秒），3秒后开始：{}", LocalDateTime.now());
    }
}

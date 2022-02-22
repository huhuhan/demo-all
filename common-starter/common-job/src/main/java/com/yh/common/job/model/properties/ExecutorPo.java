package com.yh.common.job.model.properties;

import lombok.Data;

/**
 * 执行器属性配置
 * @author yanghan
 * @date 2022/2/22
 */
@Data
public class ExecutorPo {
    /**
     * 应用名
     */
    private String appName;

    /**
     * IP
     */
    private String ip;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 日志存放目录
     */
    private String logPath;

    /**
     * 日志保留天数
     */
    private Integer logRetentionDays;
}

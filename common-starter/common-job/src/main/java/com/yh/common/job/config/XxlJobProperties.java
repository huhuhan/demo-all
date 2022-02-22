package com.yh.common.job.config;

import com.yh.common.job.model.properties.AdminPo;
import com.yh.common.job.model.properties.ExecutorPo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanghan
 * @date 2022/2/22
 */
@Data
@ConfigurationProperties(prefix = XxlJobProperties.PREFIX)
public class XxlJobProperties {
    public static final String PREFIX = "xxl.job";
    /**
     * 启用
     */
    private Boolean enabled;
    /**
     * 管理端属性
     */
    private AdminProperties admin;
    /**
     * 执行器
     */
    private ExecutorPo executor;
    /**
     * access token
     */
    private String accessToken;


    /**
     * 管理端属性
     * 方式一：静态内部类，默认升级顶级类，作为类属性
     */
    @Data
    public static class AdminProperties {

        /**
         * 管理后端地址
         */
        private String addresses;
    }

    /** 方式二：单独定义类，这里作用仅做配置提示 */
    @ConfigurationProperties(prefix = XxlJobProperties.PREFIX + "." + "executor")
    public ExecutorPo executor() {
        return new ExecutorPo();
    }
}

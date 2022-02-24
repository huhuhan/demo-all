package com.yh.common.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanghan
 * @date 2022/2/22
 */
@Data
@ConfigurationProperties(prefix = YhScheduledProperties.PREFIX)
public class YhScheduledProperties {
    public static final String PREFIX = "yh.schedule";
    /**
     * 启用
     */
    private boolean enabled;

}

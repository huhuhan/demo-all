package com.yh.common.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息配置
 *
 * @author yanghan
 * @date 2021/7/7
 */
@Data
@ConfigurationProperties(prefix = MessageProperties.PREFIX)
public class MessageProperties {
    public static final String PREFIX = "yh.message";
    public static final String PREFIX_EMAIL = PREFIX + ".email";
    public static final String PREFIX_SMS = PREFIX + ".sms";

    /** 是否开启 */
    private boolean enabled;
    /** 系统默认邮箱 */
    private EmailProperties email;
    /** 短信 */
    private SmsProperties sms;

}

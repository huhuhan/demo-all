package com.yh.common.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮箱属性配置
 *
 * @author yanghan
 * @date 2022/1/20
 */
@Data
@ConfigurationProperties(prefix = MessageProperties.PREFIX_EMAIL)
public class EmailProperties {
    /** 是否开启 */
    private boolean enabled;
    /** IP */
    private String host;
    /** 端口 */
    private Integer port;
    /** 别名 */
    private String nickName;
    /** 邮箱地址 */
    private String address;
    /** 邮箱密码 */
    private String password;
    /** 系统标题 */
    private String systemTitle;

}

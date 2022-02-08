package com.yh.common.message.config;

import com.yh.common.message.model.dto.SmsTemplateDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author yanghan
 * @date 2021/7/7
 */
@Data
@ConfigurationProperties(prefix = MessageProperties.PREFIX_SMS)
public class SmsProperties {
    /** 是否开启 */
    private boolean enabled;
    /** 短信模板 */
    private Map<String, SmsTemplateDTO> templates;
    /** 应用ID */
    private String appId;
    /** 应用密钥 */
    private String appKey;
    /** 域名 */
    private String endpoint;

}

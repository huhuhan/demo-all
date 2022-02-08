package com.yh.common.message.model.dto;

import lombok.Data;

/**
 * 参考阿里云短信服务所需属性
 *
 * @author yanghan
 * @date 2021/7/7
 */
@Data
public class SmsTemplateDTO {

    /** 签名密钥 */
    private String sign;
    /** 模板编码 */
    private String code;

}

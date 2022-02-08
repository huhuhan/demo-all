package com.yh.common.message.captcha;

import lombok.Data;

/**
 * @author yanghan
 * @date 2021/7/30
 */
@Data
public abstract class AbstractCaptchaGenerator implements ICaptchaGenerator {

    Integer length;
    String code;

    AbstractCaptchaGenerator(Integer length) {
        this.length = length;
    }
    /**
     * 验证码生成规则
     * @author yanghan
     * @return CodeGenerator
     * @date 2021/7/30
     */
//    protected abstract CodeGenerator createGenerator();
}

package com.yh.common.message.captcha;

import lombok.AllArgsConstructor;

import java.io.OutputStream;

/**
 * @author yanghan
 * @date 2021/7/30
 */
@AllArgsConstructor
public class CaptchaContext {

    private ICaptchaGenerator iCaptchaGenerator;

    public String getCode() {
        return iCaptchaGenerator.generatorCode();
    }

    public void write(OutputStream outputStream) {
        iCaptchaGenerator.write(outputStream);
    }
}

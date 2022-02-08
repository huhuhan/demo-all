package com.yh.common.message.captcha;

import java.io.OutputStream;

/**
 * @author yanghan
 * @date 2021/7/30
 */
public interface ICaptchaGenerator {

    /**
     * 生成文本验证码
     *
     * @return String
     */
    String generatorCode();

    /**
     * 写入图片验证码
     *
     * @param outputStream
     */
    void write(OutputStream outputStream);
}

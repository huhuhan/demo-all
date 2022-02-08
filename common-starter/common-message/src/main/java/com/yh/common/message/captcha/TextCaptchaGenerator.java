package com.yh.common.message.captcha;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import com.yh.common.message.model.constant.CaptchaConstant;
import com.yh.common.message.model.vo.TextCaptchaQueryVO;

import java.io.OutputStream;

/**
 * 文本验证码
 *
 * @author yanghan
 * @date 2021/7/30
 */
public class TextCaptchaGenerator extends AbstractCaptchaGenerator {
    /** 随机码类型 */
    private String codeType;

    public TextCaptchaGenerator(Integer length, String codeType) {
        super(length);
        this.codeType = codeType;
    }

    public TextCaptchaGenerator(TextCaptchaQueryVO textCaptchaQueryVo) {
        super(textCaptchaQueryVo.getLength());
        this.codeType = textCaptchaQueryVo.getCodeType();
    }


    @Override
    public String generatorCode() {
        if (null == this.code) {
            CodeGenerator generator = new RandomGenerator(this.initRandomData(), this.length);
            this.code = generator.generate();
        }
        return this.code;
    }

    @Override
    public void write(OutputStream outputStream) {
        throw new RuntimeException("不支持的方法");
    }


    private String initRandomData() {
        switch (this.codeType) {
            case CaptchaConstant.LETTER_CODE:
                return RandomUtil.BASE_CHAR;
            case CaptchaConstant.NUMBER_LETTER_CODE:
                return RandomUtil.BASE_CHAR_NUMBER;
            default:
                return RandomUtil.BASE_NUMBER;
        }
    }
}

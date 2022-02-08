package com.yh.common.message.captcha;

import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import com.yh.common.message.model.constant.CaptchaConstant;
import com.yh.common.message.model.vo.ImgCaptchaQueryVO;

import java.io.OutputStream;

/**
 * 图片验证码
 *
 * @author yanghan
 * @date 2021/7/30
 */
public class ImgCaptchaGenerator extends AbstractCaptchaGenerator {

    /** 随机码类型 */
    private String codeType;
    /** 干扰类型 */
    private String interferenceType;
    /** 验证码对象 */
    private ICaptcha iCaptcha;
    /** 图片宽度 */
    private Integer width;
    /** 图片高度 */
    private Integer height;

    public ImgCaptchaGenerator(Integer length, String codeType, String interferenceType, Integer width, Integer height) {
        super(length);
        this.codeType = codeType;
        this.interferenceType = interferenceType;
        this.width = width;
        this.height = height;
    }

    public ImgCaptchaGenerator(ImgCaptchaQueryVO imgCaptchaQueryVo) {
        super(imgCaptchaQueryVo.getLength());
        this.codeType = imgCaptchaQueryVo.getCodeType();
        this.interferenceType = imgCaptchaQueryVo.getInterferenceType();
        this.width = imgCaptchaQueryVo.getWidth();
        this.height = imgCaptchaQueryVo.getHeight();
    }

    @Override
    public String generatorCode() {
        RandomGenerator generator = this.getRandomGenerator();
        this.iCaptcha = this.getICaptcha(generator);
        this.code = iCaptcha.getCode();
        return this.code;
    }

    @Override
    public void write(OutputStream outputStream) {
        iCaptcha.write(outputStream);
    }

    private ICaptcha getICaptcha(RandomGenerator generator) {
        switch (this.interferenceType) {
            case CaptchaConstant.SHEAR_INTERFERENCE:
                ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(width, height);
                shearCaptcha.setGenerator(generator);
                return shearCaptcha;
            case CaptchaConstant.CIRCLE_INTERFERENCE:
                CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(width, height);
                circleCaptcha.setGenerator(generator);
                return circleCaptcha;
            default:
                LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height);
                lineCaptcha.setGenerator(generator);
                return lineCaptcha;
        }
    }

    private RandomGenerator getRandomGenerator() {
        String value;
        switch (this.codeType) {
            case CaptchaConstant.LETTER_CODE:
                value = RandomUtil.BASE_CHAR;
                break;
            case CaptchaConstant.NUMBER_LETTER_CODE:
                value = RandomUtil.BASE_CHAR_NUMBER;
                break;
            default:
                value = RandomUtil.BASE_NUMBER;
        }
        return new RandomGenerator(value, this.length);
    }

}

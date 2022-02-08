package com.yh.common.message.model.constant;

/**
 * @author yanghan
 * @date 2021/7/30
 */
public interface CaptchaConstant {
    String NUMBER_CODE = "number";
    String LETTER_CODE = "letter";
    String NUMBER_LETTER_CODE = "number-letter";

    String LINE_INTERFERENCE = "line";
    String CIRCLE_INTERFERENCE = "circle";
    String SHEAR_INTERFERENCE = "shear";

    int CACHE_TIME = 60;

    int IMG_WIDTH = 200;
    int IMG_HEIGHT = 100;


    /** 缓存KEY */
    String CACHE_KEY = "captcha:";
}

package com.yh.common.message.model.vo;


/**
 * 短信模板，变量参数对象
 * 需要设置参数的话，定义类并实现该接口
 *
 * @author yanghan
 * @date 2021/7/6
 */
public interface SmsDataVO {
    /**
     * 验证码之类
     *
     * @param code 随机数
     */
    void setCode(String code);
}

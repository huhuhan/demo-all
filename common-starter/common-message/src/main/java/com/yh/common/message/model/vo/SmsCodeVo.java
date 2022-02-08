package com.yh.common.message.model.vo;


import lombok.Data;

/**
 * 短信模板【流程待办通知】变量参数对象
 *
 * @author yanghan
 * @date 2021/7/6
 */
@Data
public class SmsCodeVo implements SmsDataVo {

    private String code;
}

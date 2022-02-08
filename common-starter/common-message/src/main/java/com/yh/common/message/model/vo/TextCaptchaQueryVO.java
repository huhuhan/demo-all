package com.yh.common.message.model.vo;

import com.yh.common.message.model.constant.CaptchaConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证码参数
 * @author yanghan
 * @date 2021/7/30
 */
@Data
public class TextCaptchaQueryVO {
    @ApiModelProperty("随机码长度，默认4")
    private Integer length;
    @ApiModelProperty("随机码类型，number（默认）、letter、number-letter")
    private String codeType;

    public TextCaptchaQueryVO() {
        this.length = 4;
        this.codeType = CaptchaConstant.NUMBER_CODE;
    }
}

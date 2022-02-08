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
public class ImgCaptchaQueryVO extends TextCaptchaQueryVO {

    @ApiModelProperty("干扰类型: line(默认）、shear、circle")
    private String interferenceType;
    @ApiModelProperty("图片宽度，默认200")
    private Integer width;
    @ApiModelProperty("图片高度，默认100")
    private Integer height;

    public ImgCaptchaQueryVO() {
        super();
        this.interferenceType = CaptchaConstant.LINE_INTERFERENCE;
        this.width = CaptchaConstant.IMG_WIDTH;
        this.height = CaptchaConstant.IMG_HEIGHT;
    }
}

package com.yh.auth.security.api.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户实体接口，保证基本字段
 *
 * @author yanghan
 * @date 2020/6/2
 */
public interface IUser extends Serializable {
    @ApiModelProperty("用户ID")
    String getUserId();

    void setUserId(String userId);

    @ApiModelProperty("用户名")
    String getFullname();

    void setFullname(String fullName);

    @ApiModelProperty("账户")
    String getAccount();

    void setAccount(String account);

    @ApiModelProperty("Email")
    String getEmail();

    @ApiModelProperty("手机号")
    String getMobile();

    @ApiModelProperty("是否启用 0/1")
    Integer getStatus();

}

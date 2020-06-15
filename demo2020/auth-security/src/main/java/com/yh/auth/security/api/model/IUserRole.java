package com.yh.auth.security.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色实体类接口，保证基本字段
 *
 * @author yanghan
 * @date 2020/6/2
 */
@ApiModel(description = "用户角色新 ")
public interface IUserRole {

    @ApiModelProperty("角色标识")
    String getAlias();

    @ApiModelProperty("用户名")
    String getFullname();

    @ApiModelProperty("角色名")
    String getRoleName();

    @ApiModelProperty("角色ID")
    String getRoleId();

    @ApiModelProperty("用户ID")
    String getUserId();

    @ApiModelProperty("用户账户")
    String getAccount();
}
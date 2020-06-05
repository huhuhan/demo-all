package com.yh.auth.security.api.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 组织实体类接口，保证基本字段
 *
 * @author yanghan
 * @date 2020/6/2
 */
public interface IGroup extends Serializable {

    @ApiModelProperty("组织ID")
    String getGroupId();

    @ApiModelProperty("组名字")
    String getGroupName();

    @ApiModelProperty("组CODE")
    String getGroupCode();

    @ApiModelProperty("组类型：org,role,post")
    String getGroupType();

    @ApiModelProperty("树形组 parentId")
    String getParentId();

}

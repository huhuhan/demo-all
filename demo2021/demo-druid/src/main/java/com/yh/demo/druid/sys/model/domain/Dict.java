package com.yh.demo.druid.sys.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.swing.*;

/**
 * <p>
 * 系统-字典表
 * </p>
 *
 * @author yanghan
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
@ApiModel(value="Dict对象", description="系统-字典表")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id_", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "key_")
    @ApiModelProperty(value = "分类")
    private String key;

    @TableField(value = "code_")
    @ApiModelProperty(value = "编码")
    private String code;

    @TableField(value = "name_")
    @ApiModelProperty(value = "名称")
    private String name;


}

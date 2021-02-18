package com.yh.demo.db.model.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 数据源属性配置对象
 */
@Data
public class SysDataSourceDefAttribute implements Serializable {

    /**
     * 名字
     */
    @NotEmpty
    private String name;
    /**
     * 描述
     */
    @NotEmpty
    private String comment;
    /**
     * 参数类型
     */
    @NotEmpty
    private String type;
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 数值
     */
    private String value;

}

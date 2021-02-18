package com.yh.demo.db.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 数据库源
 */
@Data
public class SysDataSource implements Serializable {


    private String id;
    /**
     * 数据源的别名
     */
    @NotEmpty
    private String key_;
    /**
     * 数据源的名字
     */
    @NotEmpty
    private String name;
    /**
     * 描述
     */
    private String desc_;
    /**
     * 数据库类型 枚举在com.dstz.base.api.db.DbType 的key
     */
    @NotEmpty
    private String dbType;
    /**
     * 类路径
     */
    @NotEmpty
    private String classPath;
    /**
     * <pre>
     * 属性字段json，为了简单就以json格式入库就行
     * 因为这个对象也不常用，这样保存是可以的，对于常用对象这样就不建议用这个了
     * </pre>
     */
    @NotEmpty
    private String attributesJson;

    /**
     * 属性字段
     */
    @NotNull
    @Valid
    private List<SysDataSourceDefAttribute> attributes;


    public void setAttributesJson(String attributesJson) {
        this.attributesJson = attributesJson;
        this.attributes = this.parseArray(attributesJson, SysDataSourceDefAttribute.class);
    }

    public void setAttributes(List<SysDataSourceDefAttribute> attributes) {
        this.attributes = attributes;
        this.attributesJson = JSON.toJSONString(attributes);
    }

    public <T> List<T> parseArray(String jsonStr, Class<T> cls) {
        if (jsonStr == null || "".equals(jsonStr)) {
            return null;
        }
        return JSON.parseArray(jsonStr, cls);
    }
}

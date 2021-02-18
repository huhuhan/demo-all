package com.yh.demo.db.service.impl;

import cn.hutool.core.util.ReflectUtil;
import com.yh.demo.db.model.entity.SysDataSource;
import com.yh.demo.db.model.entity.SysDataSourceDefAttribute;
import com.yh.demo.db.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author yanghan
 * @date 2021/2/3
 */
@Slf4j
public class MyDataSourceServiceImpl implements DataSourceService {
    @Override
    public DataSource toDataSource(SysDataSource sysDataSource) {
        // 数据源的属性配置对象
        List<SysDataSourceDefAttribute> sysDataSourceDefAttributes = sysDataSource.getAttributes();
        DataSource dataSource;
        try {
            String classPath = sysDataSource.getClassPath();
            Class<?> cls = Class.forName(classPath);
            // 数据源实例
            dataSource = (DataSource) cls.newInstance();
            // 反射机制，赋值
            for (SysDataSourceDefAttribute attribute : sysDataSourceDefAttributes) {
                if (StringUtils.isEmpty(attribute.getValue())) {
                    continue;
                }
                Object value = this.changeValue(attribute.getType(), attribute.getValue());
                String setMethodName = "set" + this.toFirst(attribute.getName(), true);
                ReflectUtil.invoke(dataSource, setMethodName, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("从数据库中匹配到数据源:「{}」", sysDataSource.getName());
        return dataSource;
    }

    private String toFirst(String str, boolean isUpper) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        char first = str.charAt(0);
        String firstChar = new String(new char[]{first});
        firstChar = isUpper ? firstChar.toUpperCase() : firstChar.toLowerCase();
        return firstChar + str.substring(1);
    }

    /**
     * 将字符串数据按照指定的类型进行转换。
     *
     * @param typeName 实际的数据类型
     * @param valStr   字符串值。
     * @return Object
     */
    private Object changeValue(String typeName, String valStr) {
        Object o = null;
        if (typeName.equals("int")) {
            o = Integer.parseInt(valStr);
        } else if (typeName.equals("short")) {
            o = Short.parseShort(valStr);
        } else if (typeName.equals("long")) {
            o = Long.parseLong(valStr);
        } else if (typeName.equals("float")) {
            o = Float.parseFloat(valStr);
        } else if (typeName.equals("double")) {
            o = Double.parseDouble(valStr);
        } else if (typeName.equals("boolean")) {
            o = Boolean.parseBoolean(valStr);
        } else if (typeName.equals("java.lang.String")) {
            o = valStr;
        } else {
            o = valStr;
        }
        return o;
    }
}

package com.yh.demo.ureport.properties;

import com.yh.demo.ureport.constant.BaseContants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanghan
 * @date 2020/3/29
 */
@Data
@ConfigurationProperties(prefix = BaseContants.BASE_PROVIDER_PREFIX)
public class DbSqlProperties {

    private String dbType = "mysql";
    /** 数据库昵称 */
    private String name = "默认数据库源";
    /** 文件前缀 */
    private String prefix = "yh";
    /** 默认不起效 */
    private boolean enabled;
}
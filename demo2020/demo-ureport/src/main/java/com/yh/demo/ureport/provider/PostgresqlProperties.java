package com.yh.demo.ureport.provider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghan
 * @date 2020/3/29
 */
@Data
@ConfigurationProperties(prefix = "ureport.provider.postgresql")
public class PostgresqlProperties {
    /** 数据库昵称 */
    private String name = "默认数据库源";
    /** 文件前缀 */
    private String prefix = "yh";
    /** 默认不起效 */
    private boolean disabled = true;
}
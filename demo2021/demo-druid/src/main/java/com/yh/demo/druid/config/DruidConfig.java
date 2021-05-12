package com.yh.demo.druid.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.yh.demo.db.config.DefaultMyConfig;
import com.yh.demo.db.dynamic.MyDynamicDataSourceProvider;
import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 数据库源配置
 */
@Configuration
@Import(DefaultMyConfig.class)
public class DruidConfig {

    /**
     * 主数据库源，默认
     *
     * @return
     */
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 多数据源-one
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 多数据源-two
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 指定某数据库源的JdbcTemplate，单独使用
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "jdbcTemplateOne")
    public JdbcTemplate jdbcTemplateOne(@Qualifier("dataSourceOne") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}


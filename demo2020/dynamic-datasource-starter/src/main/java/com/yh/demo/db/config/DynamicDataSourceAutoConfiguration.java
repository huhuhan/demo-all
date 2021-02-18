package com.yh.demo.db.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.yh.demo.db.dynamic.MyDynamicDataSourceProvider;
import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import com.yh.demo.db.dynamic.impl.DefaultDynamicDataSourceProvider;
import com.yh.demo.db.properties.DataSourceProperty;
import com.yh.demo.db.properties.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 自动化配置类
 * 优先于Druid自动化加载配置
 * @author yanghan
 * @date 2021/2/4
 */
@Configuration
@EnableConfigurationProperties({DynamicDataSourceProperties.class})
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class})
@ConditionalOnProperty(
        prefix = "spring.datasource.dynamic-yh",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@Slf4j
public class DynamicDataSourceAutoConfiguration {

    @Resource
    private DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public MyDynamicDataSourceProvider dynamicDataSourceProvider() {
        Map<String, DataSourceProperty> datasourceMap = this.properties.getDatasource();
        return new DefaultDynamicDataSourceProvider(datasourceMap);
    }


    /**
     * 默认数据库源
     * bean命名，区分默认的datasource，否则需要要移除其他数据库源的自动化配置
     * @param dynamicDataSourceProvider
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MyDynamicRoutingDataSource yhDataSource(MyDynamicDataSourceProvider dynamicDataSourceProvider) {
        MyDynamicRoutingDataSource dynamicRoutingDataSource = new MyDynamicRoutingDataSource();
        dynamicRoutingDataSource.setMyDynamicDataSourceProvider(dynamicDataSourceProvider);
        dynamicRoutingDataSource.setPrimary(this.properties.getPrimary());
        return dynamicRoutingDataSource;
    }

    @Bean
    @ConditionalOnMissingBean(JdbcTemplate.class)
    public JdbcTemplate jdbcTemplate(MyDynamicRoutingDataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}

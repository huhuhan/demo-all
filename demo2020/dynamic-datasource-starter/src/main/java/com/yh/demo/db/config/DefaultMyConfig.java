package com.yh.demo.db.config;

import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import com.yh.demo.db.interceptor.DSAspect;
import com.yh.demo.db.interceptor.DynamicDataSourceInterceptor;
import com.yh.demo.db.service.DataSourceService;
import com.yh.demo.db.service.SysDataSourceService;
import com.yh.demo.db.service.impl.DefaultUserDataSourceImpl;
import com.yh.demo.db.service.impl.MyDataSourceServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 默认配置，按需引用
 *
 * @author yanghan
 * @date 2021/2/9
 */
public class DefaultMyConfig {

    /**
     * 拦截器
     *
     * @param sysDataSourceService
     * @param dataSourceService
     * @param myDynamicRoutingDataSource
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor(SysDataSourceService sysDataSourceService, DataSourceService dataSourceService, MyDynamicRoutingDataSource myDynamicRoutingDataSource) {
        return new DynamicDataSourceInterceptor(sysDataSourceService, dataSourceService, myDynamicRoutingDataSource);
    }

    /**
     * 使用路由数据库源的JdbcTemplate
     *
     * @param dataSource
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(JdbcTemplate.class)
    public JdbcTemplate jdbcTemplate(MyDynamicRoutingDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 默认数据库源加载类
     *
     * @param dynamicRoutingDataSource
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SysDataSourceService sysDataSourceService(MyDynamicRoutingDataSource dynamicRoutingDataSource) {
        return new DefaultUserDataSourceImpl(dynamicRoutingDataSource);
    }

    /**
     * 默认数据库源配置解析类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceService dataSourceService() {
        return new MyDataSourceServiceImpl();
    }

    /**
     * 数据库源注解切面
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DSAspect dsAspect() {
        return new DSAspect();
    }


}

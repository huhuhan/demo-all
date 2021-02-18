package com.yh.demo.db.config;

import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import com.yh.demo.db.interceptor.DynamicDataSourceInterceptor;
import com.yh.demo.db.service.DataSourceService;
import com.yh.demo.db.service.SysDataSourceService;
import com.yh.demo.db.service.impl.DefaultUserDataSourceImpl;
import com.yh.demo.db.service.impl.MyDataSourceServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * 默认的拦截器、数据库源获取接口，数据库源转发接口
 * @author yanghan
 * @date 2021/2/9
 */
public class DefaultInterceptorConfig {

    @Bean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor(SysDataSourceService sysDataSourceService, DataSourceService dataSourceService, MyDynamicRoutingDataSource myDynamicRoutingDataSource) {
        return new DynamicDataSourceInterceptor(sysDataSourceService, dataSourceService, myDynamicRoutingDataSource);
    }

    @Bean
    public SysDataSourceService sysDataSourceService(MyDynamicRoutingDataSource dynamicRoutingDataSource) {
        return new DefaultUserDataSourceImpl(dynamicRoutingDataSource);
    }

    @Bean
    public DataSourceService dataSourceService() {
        return new MyDataSourceServiceImpl();
    }
}

package com.yh.demo.druid.config;

import com.yh.demo.db.dynamic.MyDynamicDataSourceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库源加载方式实现
 * @author yanghan
 * @date 2021/5/6
 */
@Component

@Configuration
public class DruidDynamicDataSourceProvider implements MyDynamicDataSourceProvider {

    private final DataSource dataSourceOne;
    private final DataSource dataSourceTwo;
    private final DataSource dataSource;

    public DruidDynamicDataSourceProvider(@Qualifier("dataSourceOne") DataSource dataSourceOne, @Qualifier("dataSourceTwo") DataSource dataSourceTwo, DataSource dataSource) {
        this.dataSourceOne = dataSourceOne;
        this.dataSourceTwo = dataSourceTwo;
        this.dataSource = dataSource;
    }

    @Override
    public Map<Object, Object> loadDataSources() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("dataSourceOne", dataSourceOne);
        dataSourceMap.put("dataSourceTwo", dataSourceTwo);
        dataSourceMap.put("master", dataSource);
        return dataSourceMap;
    }
}

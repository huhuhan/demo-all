package com.yh.demo.db.service.impl;

import com.yh.demo.db.dynamic.MyDynamicRoutingDataSource;
import com.yh.demo.db.model.entity.SysDataSource;
import com.yh.demo.db.service.SysDataSourceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

/**
 * 默认的查询系统数据库接口实现类
 * @author yanghan
 * @date 2021/2/3
 */
public class DefaultUserDataSourceImpl implements SysDataSourceService, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private MyDynamicRoutingDataSource myDynamicRoutingDataSource;

    public DefaultUserDataSourceImpl(MyDynamicRoutingDataSource myDynamicRoutingDataSource) {
        this.myDynamicRoutingDataSource = myDynamicRoutingDataSource;
    }

    private String getSql() {
        return "SELECT id_ as id, key_ as key_, name_ as name, desc_ as desc_, db_type_ as dbType, class_path_ as classPath, attributes_json_ as attributesJson from sys_data_source where id_ = ? ";
    }

    @Override
    public SysDataSource getSysDataSourceBySubSystemId(String subSystemId) {
        // jdbc连接主数据库，查询数据库源
        DataSource masterDataSource = myDynamicRoutingDataSource.getDefaultDataSource();
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        jdbcTemplate.setDataSource(masterDataSource);
        // 测试用，
        String[] params = new String[]{subSystemId};
        int[] types = new int[]{Types.VARCHAR};
        List<SysDataSource> sysDataSources = jdbcTemplate.query(this.getSql(), params, types, new BeanPropertyRowMapper<>(SysDataSource.class));

        if (sysDataSources.isEmpty()) {
            throw new RuntimeException("当前租户未分配数据源");
        }
        if (sysDataSources.size() > 1) {
            throw new RuntimeException("前租户分配了多个数据源，请配置一个数据源");
        }
        return sysDataSources.get(0);
    }

    @Override
    public SysDataSource getSysDataSourceByTenantId(String tenantId) {
        return null;
    }

    /**
     * 获取上下文对象（获取方式有很多种）
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

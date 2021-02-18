package com.yh.demo.db.service;

import com.yh.demo.db.model.entity.SysDataSource;

import javax.sql.DataSource;

/**
 * jdbc数据库源，加载接口
 *
 * @author yanghan
 * @date 2021/2/3
 */
public interface DataSourceService {

    /**
     * 业务数据库源对象，转为jdbc的数据库源
     * @param sysDataSource 数据库源封装
     * @return javax.sql.DataSource
     */
    DataSource toDataSource(SysDataSource sysDataSource);
}

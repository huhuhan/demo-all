package com.yh.demo.db.service;

import com.yh.demo.db.model.entity.SysDataSource;

/**
 * 业务数据库源，加载接口
 *
 * @author yanghan
 * @date 2021/2/3
 */
public interface SysDataSourceService {
    /**
     * 根据租户ID，获取系统数据库源对象
     *
     * @param tenantId 租户ID
     * @return com.yh.demo.db.model.entity.SysDataSource
     */
    SysDataSource getSysDataSourceByTenantId(String tenantId);

    /**
     * 根据子系统ID，获取系统数据库源对象
     *
     * @param subSystemId 租户ID
     * @return com.yh.demo.db.model.entity.SysDataSource
     */
    SysDataSource getSysDataSourceBySubSystemId(String subSystemId);

}

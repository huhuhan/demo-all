package com.yh.demo.security.service;

import com.yh.demo.security.bean.Resource;
import com.yh.demo.security.bean.Role;

import java.util.List;

public interface ResourceService {

    /**
     * 根据访问资源路径 查询资源对象
     * @param url 资源路径
     * @return
     */
    Resource getResourceByUrl(String url);

    /**
     * 根据资源id 查询所有拥有该资源的角色
     * @param resourceId 资源id
     * @return
     */
    List<Role> getRoles(String resourceId);

} 
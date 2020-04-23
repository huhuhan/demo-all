package com.yh.demo.security.service.impl;

import com.yh.demo.security.bean.Resource;
import com.yh.demo.security.bean.Role;
import com.yh.demo.security.repository.ResourceDao;
import com.yh.demo.security.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource getResourceByUrl(String url) {
        return resourceDao.findByUrl(url);
    }

    @Override
    public List<Role> getRoles(String resourceId) {
        return resourceDao.findRolesOfResource(resourceId);
    }
}
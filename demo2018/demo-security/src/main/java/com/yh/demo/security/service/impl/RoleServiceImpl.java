package com.yh.demo.security.service.impl;

import com.yh.demo.security.bean.Role;
import com.yh.demo.security.repository.RoleDao;
import com.yh.demo.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getRolesOfUser(String userName) {
        return roleDao.findRolesByUser(userName);
    }

}
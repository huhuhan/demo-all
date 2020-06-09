package com.yh.auth.security.authentication.access;

import com.yh.auth.security.authentication.IgnoreChecker;
import com.yh.auth.security.constants.AuthConstants;
import com.yh.auth.security.util.TempUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.HashSet;

/**
 * 扩展，权限元数据类
 * 默认FilterSecurityInterceptor的权限元数据类，参考 {@link DefaultFilterInvocationSecurityMetadataSource}
 *
 * @author yanghan
 * @date 2020/6/1
 */
@Slf4j
public class MySecurityMetadataSource extends IgnoreChecker implements FilterInvocationSecurityMetadataSource {

    /**
     * 重写方法，根据资源获取匹配的角色
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        //用户的请求地址
        String url = ((FilterInvocation) o).getRequestUrl();
        TempUtils.logDebug(log, "用户请求的地址是：" + o);

        ConfigAttribute currentRole = null;
        //匹配忽略的资源，提供匿名身份
        if (super.isIgnores(url)) {
            currentRole = AuthConstants.ROLE_CONFIG_ANONYMOUS;
        } else {
            //根据当前的URL获取所需要的角色
            //todo:前后端分离后，url作为前端路由，并不等于后端请求地址了，鉴权失效

            currentRole = AuthConstants.ROLE_CONFIG_PUBLIC;
        }

        TempUtils.logDebug(log, "用户请求的地址是：" + o);
        TempUtils.logDebug(log, "当前权限：" + currentRole.getAttribute());

        Collection<ConfigAttribute> configAttribute = new HashSet<>();
        configAttribute.add(currentRole);
        return configAttribute;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 为true才可用
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}

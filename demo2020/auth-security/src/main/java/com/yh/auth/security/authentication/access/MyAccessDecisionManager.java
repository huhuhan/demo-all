package com.yh.auth.security.authentication.access;

import com.yh.auth.security.authentication.provider.userdetails.MyUserDetails;
import com.yh.auth.security.constants.AuthConstants;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 扩展，鉴权决策类
 * 默认FilterSecurityInterceptor的鉴权决策类，参考 {@link AffirmativeBased}
 *
 * @author yanghan
 * @date 2020/6/1
 */
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 判断当前用户角色是否有权限访问
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes.contains(AuthConstants.ROLE_CONFIG_ANONYMOUS)) {
            return;
        }

        Object principal = authentication.getPrincipal();

        if (principal == null || "anonymousUser".equals(principal)) {
            throw new AccessDeniedException("请登录");
        }

        if (!(principal instanceof MyUserDetails)) {
            throw new AccessDeniedException("登录对象必须为LoginUser实现类");
        }

        // 获取当前用户的角色。
        UserDetails user = (UserDetails) principal;
        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) user.getAuthorities();

        // 超级访问
        if (roles.contains(AuthConstants.ROLE_GRANT_SUPER)) {
            return;
        }
        // 公开访问
        if (configAttributes.contains(AuthConstants.ROLE_CONFIG_PUBLIC)) {
            return;
        }
        // 授权访问
        // 遍历当前用户的角色，如果当前页面对应的角色包含当前用户的某个角色则有权限访问。
        for (GrantedAuthority hadRole : roles) {
            if (configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))) {
                return;
            }
        }
        throw new AccessDeniedException("没有权限!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

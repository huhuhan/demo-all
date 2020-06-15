package com.yh.auth.security.constants;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 权限角色常量
 * @author yanghan
 * @date 2020/5/29
 */
public class AuthConstants {
    /** 超级 */
    private final static String ROLE_SUPER = "ROLE_SUPER";
    /** 公共角色 */
    private final static String ROLE_PUBLIC = "ROLE_PUBLIC";
    /** 匿名级 */
    private final static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    public final static GrantedAuthority ROLE_GRANT_SUPER = new SimpleGrantedAuthority(ROLE_SUPER);
    public final static ConfigAttribute ROLE_CONFIG_PUBLIC = new SecurityConfig(ROLE_PUBLIC);
    public final static ConfigAttribute ROLE_CONFIG_ANONYMOUS = new SecurityConfig(ROLE_ANONYMOUS);

}

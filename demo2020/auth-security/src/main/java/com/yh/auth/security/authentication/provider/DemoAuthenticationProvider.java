package com.yh.auth.security.authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 源码：由ProviderManager验证管理器，调用AuthenticationProvider接口实现类逐一验证，又一项通过即可
 * 参考AuthenticationProvider的其他实现类，常用的就是UserDetailsService就是DaoAuthenticationProvider的父抽象类
 * 自定义验证类，适合验证码、短信等扩展
 *
 * @author yanghan
 * @date 2020/5/29
 */
@Component
public class DemoAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        //不管密码都伪装成admin用户
        if (name.equals("yh")) {
            Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
            authorityCollection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new UsernamePasswordAuthenticationToken("admin", password, authorityCollection);
        } else {
            return null;
        }
    }

    /**
     * 源码AuthenticationProvider 就是调用该方法判断身份认证对象Authentication
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

package com.yh.auth.security.authentication.provider.userdetails;

import com.yh.auth.security.constants.AuthConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 重写加载身份认证对象，实现UserDetailsService接口或UserDetailsManager接口
 * Spring Security 的认证，基于
 *
 * @author yanghan
 * @date 2020/5/29
 */
@Component
public class MyUserDetailsManager implements UserDetailsService {
    private final boolean temp_isAdmin = true;
    /**
     * 根据账户名称创建身份验证对象
     * 扩展todo：jwt、缓存
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails loginUser = null;
        // todo: jwt 模式缓存身份凭证

        //todo: 根据用户名查询用户，并封装成MyUserDetails
        /*MyUserDetails loginUser = new MyUserDetails(userService.getUserByAccount(username));*/
        loginUser = new MyUserDetails();
        loginUser.setAccount(username);
        if (loginUser == null) {
            throw new UsernameNotFoundException("用户：" + username + "不存在");
        }


        Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
        //todo: 数据库查询用户角色，遍历存到collection中
        GrantedAuthority role = new SimpleGrantedAuthority("temp_db_role");
        collection.add(role);

        //todo: 判断当前用户是否超级管理员
        //超级管理员权限
        if (temp_isAdmin) {
            collection.add(AuthConstants.ROLE_GRANT_SUPER);
        }
        loginUser.setAuthorities(collection);

        return loginUser;
    }
}

package com.yh.auth.security.authentication.provider.userdetails;

import com.yh.auth.security.api.model.IUser;
import com.yh.auth.security.api.model.dto.UserDTO;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义身份认证对象，实现UserDetails
 *
 * @author yanghan
 * @date 2020/5/29
 */
@NoArgsConstructor
public class MyUserDetails extends UserDTO implements UserDetails {

    private Collection<? extends GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

    private static final long serialVersionUID = 4962121675615445357L;

    MyUserDetails(IUser user) {
        this.id = user.getUserId();
        this.loginName = user.getAccount();
        this.userName = user.getFullname();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.status = user.getStatus();
    }

    /**
     * 设置角色。
     *
     * @param roles
     */
    void setAuthorities(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus() == 1;
    }

}

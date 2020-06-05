package com.yh.auth.security.authentication.provider.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 重写加载身份认证对象，实现UserDetailsService接口或UserDetailsManager接口
 * Spring Security 的认证，基于
 *
 * @author yanghan
 * @date 2020/5/29
 */
public class MyUserDetailsManager implements UserDetailsService {
    public static final String loginUserCacheKey = "bpm:loginUser:";
//    @Resource
//    private UserService userService;
//    @Resource
//    private ICache<MyUserDetails> loginUserCache;
//    @Autowired
//    private JWTService jwtService;

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
        // 只有 jwt 模式才有必要缓存用户角色
        /*if (jwtService.getJwtEnabled()) {
            //TODO 加上有效期
            loginUser = loginUserCache.getByKey(loginUserCacheKey.concat(username));

            if (loginUser != null) {
                return loginUser;
            }
        }

        IUser defaultUser = userService.getUserByAccount(username);

        if (defaultUser == null) {
            throw new UsernameNotFoundException("用户：" + username + "不存在");
        }

        loginUser = new MyUserDetails(defaultUser);

        //构建用户角色。
        List<? extends IUserRole> userRoleList = userService.getUserRole(loginUser.getUserId());
        Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
        for (IUserRole userRole : userRoleList) {
            GrantedAuthority role = new SimpleGrantedAuthority(userRole.getAlias());
            collection.add(role);
        }
        if (ContextUtil.isAdmin(loginUser)) {
            collection.add(PlatformConstants.ROLE_GRANT_SUPER);
        }
        loginUser.setAuthorities(collection);
        if (jwtService.getJwtEnabled()) {
            loginUserCache.add(loginUserCacheKey.concat(username), loginUser);
        }*/
        return loginUser;
    }
}

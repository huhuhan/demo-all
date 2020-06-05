package com.yh.auth.security.autoconfigure;

import com.yh.auth.security.authentication.handler.MyAccessDeniedHandler;
import com.yh.auth.security.authentication.handler.MyLoginUrlAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * WebSecurity配置类
 *
 * @author yanghan
 * @date 2020/6/1
 */
@EnableWebSecurity
public class YHWebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
    //    @Autowired
//    BackdoorAuthenticationProvider backdoorAuthenticationProvider;
//    @Autowired
//    MyUserDetailsService myUserDetailsService;
//    @Autowired
//    MyAccessDecisionManager myAccessDecisionManager;
//    @Autowired
//    MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyLoginUrlAuthenticationEntryPoint macLoginUrlAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 在内存中创建一个名为 "user" 的用户，密码为 "pwd"，拥有 "USER" 权限，密码使用BCryptPasswordEncoder加密
         */
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER");
        /**
         * 在内存中创建一个名为 "admin" 的用户，密码为 "pwd"，拥有 "USER" 和"ADMIN"权限
         */
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER", "ADMIN");
        //将自定义验证类注册进去
//        auth.authenticationProvider(backdoorAuthenticationProvider);
//        //加入数据库验证类，下面的语句实际上在验证链中加入了一个DaoAuthenticationProvider
//        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //security 直接忽略的请求
        web.ignoring()
                //框架默认的一些请求
                .antMatchers("/error", "/csrf")
                //swagger页面的请求
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs/**", "/webjars/**")
                //前端view页面请求，本项目的测试模块view包
                .antMatchers("/", "/index", "/login_y");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .authorizeRequests()
//                .withObjectPostProcessor(new ObjectPostProcessor<MySecurityInterceptor>() {
//                    @Override
//                    public <O extends MySecurityInterceptor> O postProcess(O object) {
//                        object.setSecurityMetadataSource(mySecurityMetadataSource);
//                        object.setAccessDecisionManager(myAccessDecisionManager);
//                        return object;
//                    }
//                }).anyRequest().authenticated()
//                .and()
                .authorizeRequests()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//                        object.setSecurityMetadataSource(mySecurityMetadataSource);
//                        object.setAccessDecisionManager(myAccessDecisionManager);
//                        return object;
//                    }
//                })
                .antMatchers("/").permitAll()
                .antMatchers("/depart2").hasAnyRole("ROLE_DEPART2")
                .anyRequest().authenticated();

        /** 登录页配置 */
        http.formLogin()
                //自己的登录页面
                .loginPage("/login_h")
//        .usernameParameter("myusername").passwordParameter("mypassword")
                .permitAll();

        /** 登出页配置 */
        http.logout()
                .logoutSuccessUrl("/login_p")
                .permitAll();

         /** 异常处理 */
        http.exceptionHandling()
                //未登录无权访问异常处理
                .authenticationEntryPoint(macLoginUrlAuthenticationEntryPoint)
                //已登录无权访问异常处理
                .accessDeniedHandler(myAccessDeniedHandler);

        /** 其他配置 */
        http.csrf().disable();

//        http.addFilterAfter(this.initMySecurityInterceptor(), FilterSecurityInterceptor.class);
    }

}

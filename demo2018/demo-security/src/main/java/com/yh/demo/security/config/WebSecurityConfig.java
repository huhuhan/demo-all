package com.yh.demo.security.config;

import com.yh.demo.security.security.MyAccessDecisionManager;
import com.yh.demo.security.security.MyAccessDeniedHandler;
import com.yh.demo.security.security.MyFilterInvocationSecurityMetadataSourceImpl;
import com.yh.demo.security.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //根据一个url请求，获得访问它所需要的roles权限
    @Autowired
    private MyFilterInvocationSecurityMetadataSourceImpl myFilterInvocationSecurityMetadataSource;

    //接收一个用户的信息和访问一个url所需要的权限，判断该用户是否可以访问
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    //403页面
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    /**
     * 定义认证用户信息获取来源，密码校验规则等；
     * Security5要求加密方式验证【{id}加密的密码】
     * id可以是bcrypt、sha256等，推荐bcrypt
     * 此处加密方式bCrypt，
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Security4及更早版本
//        auth.inMemoryAuthentication().withUser("user1").password("123123").roles("USER");
        /**有以下几种形式，使用第3种*/
        //inMemoryAuthentication 从内存中获取，可以用来测试
        //加密方式
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user1")
//                .password(new BCryptPasswordEncoder().encode("123123"))
//                .roles("USER");

        //jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构，可扩展性低。
        //usersByUsernameQuery 指定查询用户SQL
        //authoritiesByUsernameQuery 指定查询权限SQL
//        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);

        //注入userDetailsService，需要实现userDetailsService接口 并指定加密方式BCryptPasswordEncoder
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/noAuthenticate","/**/*.ico","/**/*.js");
//        web.ignoring().anyRequest()
        ;
    }

    /**定义安全策略*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()       //配置安全策略
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //根据一个url请求，获得访问它所需要的roles权限
                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                        //接收一个用户的信息和访问一个url所需要的权限，判断该用户是否可以访问
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
//                .antMatchers("/hello").hasAuthority("ADMIN")
                //.antMatchers("/js/**","/css/**","/images/*","/fonts/**","/**/*.png","/**/*.jpg","/**/*.ico").permitAll()
                .and()
                .formLogin()                        //配置登录页面
                .loginPage("/login")                //自定义登录页面
                .loginProcessingUrl("/user/login")  //自定义的登录接口
                .usernameParameter("username")      //指定页面中对应用户名的参数名称
                .passwordParameter("password")      //指定页面中对应密码的参数名称
                .permitAll()                        //配置登录页面所有人可以访问
                .failureHandler(new AuthenticationFailureHandler() {//登陆失败后的操作
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        StringBuffer sb = new StringBuffer();
                        sb.append("{\"status\":\"error\",\"msg\":\"");
                        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                            sb.append("用户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            sb.append("账户被禁用，登录失败，请联系管理员!");
                        } else {
                            sb.append("登录失败!");
                        }
                        sb.append("\"}");
                        out.write(sb.toString());
                        out.flush();
                        out.close();
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {//登录成功后的操作
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
//                        ObjectMapper objectMapper = new ObjectMapper();
                        //String s = "{\"status\":\"success\",\"msg\":"  + "}";
                        String s = "{\"status\":\"200\",\"data\":\"success\",\"msg\":\"登录成功\"}";
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout()       //登出
                .permitAll()    //所有人可以登出
                .and()
                .csrf()
                .disable()
                .exceptionHandling()        //配置自定义403响应
                .accessDeniedHandler(myAccessDeniedHandler);
    }

}

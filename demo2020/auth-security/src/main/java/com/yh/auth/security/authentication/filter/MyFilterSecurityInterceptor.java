package com.yh.auth.security.authentication.filter;


import com.yh.auth.security.authentication.access.MyAccessDecisionManager;
import com.yh.auth.security.authentication.access.MySecurityMetadataSource;
import lombok.Data;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.*;
import java.io.IOException;

/**
 * 扩展，自定义安全过滤器
 * 默认过滤器链，参考 {@link FilterSecurityInterceptor}
 *
 * @author yanghan
 * @date 2020/6/1
 */
@Data
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    /**
     * 初始化静态方法
     *
     * @return
     */
    public static MyFilterSecurityInterceptor init() {
        MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
        myFilterSecurityInterceptor.setAccessDecisionManager(new MyAccessDecisionManager());
        myFilterSecurityInterceptor.setSecurityMetadataSource(new MySecurityMetadataSource());
        return myFilterSecurityInterceptor;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        this.invoke(fi);
    }

    /**
     * 基于Servlet Filter技术
     *
     * @return
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    /**
     * 所有添加的filter都要过滤
     *
     * @param fi
     * @throws IOException
     * @throws ServletException
     */
    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        super.setRejectPublicInvocations(false);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }
}

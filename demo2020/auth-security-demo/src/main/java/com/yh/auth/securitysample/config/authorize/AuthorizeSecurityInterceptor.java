package com.yh.auth.securitysample.config.authorize;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 资源访问过滤器
 * <p>
 * 默认的过滤器是{@link FilterSecurityInterceptor}
 *
 * @author ygsama
 */
@Data
public class AuthorizeSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private AuthorizeSecurityMetadataSource authorizeSecurityMetadataSource;

    public static AuthorizeSecurityInterceptor init() {
        AuthorizeSecurityInterceptor intercept = new AuthorizeSecurityInterceptor();
        intercept.setAccessDecisionManager(new AuthorizeAccessDecisionManager());
        intercept.setAuthorizeSecurityMetadataSource(new AuthorizeSecurityMetadataSource());
        return intercept;
    }


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.authorizeSecurityMetadataSource;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);

        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("----------AuthorizeSecurityInterceptor.init()");
    }

    @Override
    public void destroy() {
        System.out.println("----------AuthorizeSecurityInterceptor.destroy()");
    }


}

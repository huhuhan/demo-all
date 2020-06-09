package com.yh.auth.security.authentication.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.yh.auth.security.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 扩展， jwt拦击
 *
 * @author yanghan
 * @date 2020/6/9
 */
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //todo：默认不开启拦截
        if (!JwtUtils.JWT_ENABLED) {
            chain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(JwtUtils.AUTHORIZATION);
        authorization = StringUtils.isEmpty(authorization) ? request.getHeader("token") : authorization;
        if (StringUtils.isEmpty(authorization)) {
            log.error("请求header缺失Authorization凭证");
            throw new AccessDeniedException("没有权限!");
        }

        String token = authorization.replace(JwtUtils.BEARER, "").trim();
        DecodedJWT jwt = JwtUtils.verifyToken(token, false);
        if (null == jwt) {
            log.error("无效token");
            throw new AccessDeniedException("无效token!");
        }

        try {
            String loginName = jwt.getSubject();
            log.info("当前请求的Token用户: {}", loginName);

            //从缓存中获取，获取失败则通过实现接口的方法获取用户
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginName);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.debug("authenticated user " + loginName + ", setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("获取当前用户信息失败");
            throw new AccessDeniedException("没有权限!");
        }
    }
}

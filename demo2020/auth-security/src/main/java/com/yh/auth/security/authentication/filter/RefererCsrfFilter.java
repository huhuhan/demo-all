package com.yh.auth.security.authentication.filter;

import com.yh.auth.security.authentication.IgnoreChecker;
import com.yh.auth.security.autoconfigure.YHSecurityProperties;
import com.yh.auth.security.autoconfigure.YHWebSecurityAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 扩展，防止CSRF跨站请求攻击
 *
 * @author yanghan
 * @date 2020/6/1
 */
@Slf4j
public class RefererCsrfFilter extends IgnoreChecker implements Filter {

    /**
     * todo: 配置读取，设置拦截白名单地址，其他过滤器参考这方法
     * @param yhSecurityProperties
     * @return
     */
    public static RefererCsrfFilter init(YHSecurityProperties yhSecurityProperties) {
        RefererCsrfFilter filter = new RefererCsrfFilter();
        List<String> ignores = new ArrayList();
        String csrfIgnores = yhSecurityProperties.getCsrfIgnores();
        if (!StringUtils.isEmpty(csrfIgnores)) {
            ignores = Arrays.asList(csrfIgnores.split(","));
        }
        //白名单地址
        filter.setIgnores(ignores);
        return filter;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        this.permitAll((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
        /*//判断是否外链。
        String referer = req.getHeader("Referer");
        String serverName = request.getServerName();
        //请求不是来自本网站。
        if (null != referer && referer.indexOf(serverName) < 0) {
            //是否包含当前URL
            boolean isIngoreUrl = this.isIngores(referer);
            if (isIngoreUrl) {
                chain.doFilter(request, response);
            } else {
            	String msg = String.format("系统不支持当前域名的访问，请联系管理员！<br> 服务器：%s,当前域名:%s",serverName ,referer);
            	 ResultMsg resultMsg = new ResultMsg<>(BaseStatusCode.PARAM_ILLEGAL,msg);
                 response.getWriter().print(JSON.toJSONString(resultMsg));
            }
        } else {
            chain.doFilter(request, response);
        }*/
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    private void permitAll(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Forward-For");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Max-Age", "1800");

        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            log.info("报文头[" + name + "]:[" + request.getHeader(name) + "]");
        }
    }
}

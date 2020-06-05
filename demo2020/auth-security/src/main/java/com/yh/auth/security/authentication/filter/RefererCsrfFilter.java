package com.yh.auth.security.authentication.filter;

import com.yh.auth.security.authentication.IgnoreChecker;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 扩展，防止CSRF跨站请求攻击
 *
 * @author yanghan
 * @date 2020/6/1
 */
public class RefererCsrfFilter extends IgnoreChecker implements Filter {


    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

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

}

package com.yh.auth.security.authentication.filter;

import com.yh.auth.security.util.TempUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 扩展，向 http请求中，设置当前request信息,以便在线程中使用request
 * 清除http中线程变量
 */
@Slf4j
public class RequestThreadFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            cleanThreadLocal();
            TempUtils.logDebug(log, "抱歉请求至本地缓存");
//            RequestContext.setHttpServletRequest((HttpServletRequest) request);
//            RequestContext.setHttpServletResponse((HttpServletResponse) response);
            chain.doFilter(request, response);
        } finally {
            cleanThreadLocal();
        }
    }

    private void cleanThreadLocal() {
//        RequestContext.clearHttpReqResponse();
//        ContextUtil.clearAll();
//        ThreadMsgUtil.clean();
//        DbContextHolder.setDefaultDataSource();
        //      ActivitiDefCache.clearLocal();
        //      BpmContext.cleanTread();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

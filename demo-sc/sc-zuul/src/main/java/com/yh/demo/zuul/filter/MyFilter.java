package com.yh.demo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 服务请求拦截过滤器
 * 其他spring集成的过滤器参考源码，比如:RibbonRoutingFilter
 *
 * @author yanghan
 * @date 2019/4/27
 */
@Component
public class MyFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(MyFilter.class);
    private static final String MY_TOKEN = "yh";

    /**
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * pre：路由之前
     * routing：路由之时
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder：过滤的顺序，多个过滤器，越小越优先
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return -4;
    }

    /**
     * shouldFilter：这里可以写逻辑判断，是否要过滤。
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info(String.format("%s >> %s >>> %s", request.getRemoteAddr(), request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if (null == accessToken) {
            this.tokenFilter(ctx, "token is empty");
        } else if (!MY_TOKEN.equals(accessToken.toString())) {
            this.tokenFilter(ctx, "token is error");
        }
        logger.info("ok");
        return null;
    }

    private void tokenFilter(RequestContext ctx, String msg) {
        logger.warn("-----------------------------------------------");
        logger.warn(msg);
        logger.warn("-----------------------------------------------");
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        try {
            ctx.getResponse().getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

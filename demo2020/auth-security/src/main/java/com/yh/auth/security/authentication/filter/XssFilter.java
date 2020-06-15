package com.yh.auth.security.authentication.filter;

import com.yh.auth.security.authentication.IgnoreChecker;
import com.yh.auth.security.util.TempUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扩展， XSS安全过滤器。
 * <pre>
 *  这个功能是为了放置XSS攻击。
 *  如果有Xss攻击：
 *  	1.表单提交方式，平台将去到一个提示页面。
 *  	2.AJAX提交方式，弹出提示信息。
 *  可以配置某些不需要检测的URL.
 * </pre>
 *
 * @author yanghan
 * @date 2020/6/1
 */
public class XssFilter extends IgnoreChecker implements Filter {

    private Pattern regex = Pattern.compile("<(\\S*?)[^>]*>.*?</\\1>|<[^>]+>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL | Pattern.MULTILINE);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        //页面是否忽略。
        if (super.isIgnores(req.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            //检测是否有XSS攻击。
            boolean hasXss = checkXss(req);
            if (hasXss) {
                //todo: 后续改为项目的统一消息对象封装
                String msg = "检测到提交内容含HTML代码，被拦截！";
                TempUtils.responseWriter(response, msg);
            } else {
                chain.doFilter(request, response);
            }
        }
    }


    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * 判断输入是否有XSS注入问题。
     *
     * @param request
     * @return
     */
    private boolean checkXss(HttpServletRequest request) {
        Enumeration<?> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement().toString();
            String val = this.join(request.getParameterValues(key), "");
            if (StringUtils.isEmpty(val)) {
                continue;
            }

            Matcher regexMatcher = regex.matcher(val);
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

    private String join(String[] vals, String separator) {
        if (vals == null || vals.length == 0) {
            return "";
        }
        StringBuffer val = new StringBuffer();
        for (int i = 0; i < vals.length; i++) {
            if (i == 0) {
                val.append(vals[i]);
            } else {
                val.append(separator);
                val.append(vals[i]);
            }
        }
        return val.toString();
    }

}

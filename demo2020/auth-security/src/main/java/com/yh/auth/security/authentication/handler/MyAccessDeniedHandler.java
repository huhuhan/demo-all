package com.yh.auth.security.authentication.handler;

import com.yh.auth.security.util.TempUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 扩展，权限异常处理类
 * 参考，默认为接口的其他实现类
 *
 * @author yanghan
 * @date 2020/6/1
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 已登录无权访问异常处理
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String msg = "权限不足，不可访问，请联系管理员!";
        TempUtils.responseWriter(httpServletResponse, msg);
    }
}

package com.yh.auth.security.authentication.handler;

import com.yh.auth.security.util.TempUtils;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 扩展，未登录访问异常处理类
 * 参考，其他子类，默认LoginUrlAuthenticationEntryPoint是跳转登录页面
 *
 * @author yanghan
 * @date 2020/6/1
 */
@Component
@NoArgsConstructor
public class MyLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 无身份验证对象访问，异常处理
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        httpServletRequest.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String msg = "未登陆或登录超时，请重新登录!";
        String msg2 = "xxxx!";
        TempUtils.responseWriter(httpServletResponse, msg, msg2);
    }
}


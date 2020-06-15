package com.yh.auth.security.view.controller;

import com.yh.auth.security.constants.AuthStatusCode;
import com.yh.auth.security.util.JwtUtils;
import com.yh.auth.security.util.SecurityUtils;
import com.yh.auth.security.util.TempUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义登录接口
 *
 * @author yanghan
 * @date 2020/6/3
 */
@Api(tags = "登陆服务接口")
@RestController
public class MyLoginController {
    /**
     * session认证，参考 {@link SessionManagementFilter}
     */
    SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

    @PostMapping(value = "/my/login")
    @ApiOperation(value = "用户登录", notes = "登录鉴权")
    public Map login(@RequestParam String account,
                     @RequestParam String password,
                     HttpServletRequest request, HttpServletResponse response) {
        String audience = "pc";
        //1、判断请求参数

        try {
            // 2、用Security工具类手动登录认证
            Authentication auth = SecurityUtils.login(request, account, password, false);

            //todo：jwt 模式 支持cookie模式和token调用形式
            if (false) {
                // 3.1、初始化Token
                String token = JwtUtils.createToken(account, audience, false);
                //直接写入 cookie，把cookie当做session来用
                this.writeJwtToken2Cookie(request, response, token);

                return TempUtils.R2success("登录成功！", token);
            } else {
                ////session认证s
                sessionStrategy.onAuthentication(auth, request, response);
                //执行记住密码动作。
                SecurityUtils.writeRememberMeCookie(request, response, account, password);
                this.writeToken(request, response);

                return TempUtils.R2success("登录成功！", null);
            }

            //todo: 异常改为业务封装异常对象
        } catch (BadCredentialsException e) {
            throw new RuntimeException("账号或密码错误");
        } catch (DisabledException e) {
            throw new RuntimeException("帐号已禁用");
        } catch (LockedException e) {
            throw new RuntimeException("帐号已锁定");
        } catch (AccountExpiredException e) {
            throw new RuntimeException("帐号已过期");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(AuthStatusCode.LOGIN_ERROR.getDesc(), ex);
        }
    }


    protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";

    private void writeToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

        if (token != null) {
            Cookie cookie = new Cookie("XSRF-TOKEN", token.getToken());
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
    }

    /**
     * 类似session的形式将 token 写入 cookie 设值
     *
     * @param request
     * @param response
     * @param token
     */
    private void writeJwtToken2Cookie(HttpServletRequest request, HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(JwtUtils.AUTHORIZATION, JwtUtils.BEARER + token);
        String contextPath = request.getContextPath();
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = "/";
        }
        cookie.setPath(contextPath);
        response.addCookie(cookie);
    }
}
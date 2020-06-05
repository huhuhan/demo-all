package com.yh.auth.security.authentication.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 成功退出登录处理
 *
 * @author yanghan
 * @date 2020/5/29
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("onLogoutSuccess");
//        JWTService jwtService = AppUtil.getBean(JWTService.class);
//        ICache icache = AppUtil.getBean(ICache.class);
//
//        response.setCharacterEncoding("UTF-8");
//
//        LoginUser user = (LoginUser) authentication.getPrincipal();
//        //删除组织缓存
//        icache.delByKey(ICurrentContext.CURRENT_ORG.concat(user.getId()));
//
//        //设置过期
//        if (jwtService.getJwtEnabled()) {
//            String authHeader = request.getHeader(jwtService.getJwtHeader());
//            if (StringUtil.isEmpty(authHeader)) {
//                authHeader = CookieUitl.getValueByName(jwtService.getJwtHeader(), request);
//            }
//            if (authHeader != null && authHeader.startsWith(jwtService.getJwtTokenHead())) {
//                String authToken = authHeader.substring(jwtService.getJwtTokenHead().length());
//                jwtService.logoutRedisToken(authToken);
//            }
//
//            //删除 UserDetails
//            icache.delByKey(UserDetailsServiceImpl.loginUserCacheKey.concat(user.getAccount()));
//            CookieUitl.delCookie(jwtService.getJwtHeader(), request, response);
//        }
//
//        ResultMsg resultMsg = new ResultMsg(BaseStatusCode.SUCCESS, "退出登录成功");
//        response.getWriter().print(JSONObject.toJSONString(resultMsg));
//        return;
    }

}

package com.yh.auth.security.util;

import com.yh.auth.security.crypto.CustomPwdEncoder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * security 工具类
 *
 * @author yanghan
 * @date 2020/6/9
 */
public class SecurityUtils {

    private final static String rememberPrivateKey = "rememberPrivateKey";


    /**
     * 按照 spring 提供的规则 SPRING_SECURITY_REMEMBER_ME_COOKIE。
     *
     * @param request
     * @param response
     * @param username
     * @param password
     */
    public static void writeRememberMeCookie(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        String rememberMe = request.getParameter("rememberMe");
        rememberMe = null != rememberMe ? rememberMe : "0";
        if (!"1".equals(rememberMe)) {
            return;
        }

        String enPassword = new CustomPwdEncoder().encode(password);

        // 14 days
        long tokenValiditySeconds = 1209600;
        long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
        String signatureValue = makeTokenSignature(tokenExpiryTime, username, enPassword);
        String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
        String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
        Cookie cookie = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, tokenValueBase64);
        // 5 years
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath(org.springframework.util.StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
        response.addCookie(cookie);
    }

    /**
     * 登录系统。
     *
     * @param request
     * @param userName    用户名
     * @param pwd         密码
     * @param isIgnorePwd 是否忽略密码。
     * @return
     */
    public static Authentication login(HttpServletRequest request, String userName, String pwd, boolean isIgnorePwd) {
        AuthenticationManager authenticationManager = MySpringUtils.getBean("authenticationManager");
        CustomPwdEncoder.setIgnore(isIgnorePwd);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, pwd);
        authRequest.setDetails(new WebAuthenticationDetails(request));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = authenticationManager.authenticate(authRequest);
        securityContext.setAuthentication(auth);
        return auth;
    }

    /**
     * 将过期时间:用户名：密码 和 rememberPrivateKey 进行MD5签名。
     *
     * @param tokenExpiryTime
     * @param username
     * @param password
     * @return
     */
    private static String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        String data = username + ":" + tokenExpiryTime + ":" + password + ":" + rememberPrivateKey;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(data.getBytes())));
    }
}

package com.yh.auth.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yanghan
 * @date 2019/7/31
 */
@Slf4j
public class JwtUtils {
    /** 是否启动jwt拦截 */
    public static final boolean JWT_ENABLED = false;

    /** token 的 key */
    public static final String AUTHORIZATION = "Authorization";
    /** token 的 key前缀 */
    public static final String BEARER = "Bearer-";
    /** 签发者 */
    public static final String ISSUER = "YH";
    /** 秘钥 */
    public static String SECRET = "auth-security-jwt";
    /** 差距时长 单位秒 */
    public static long leewayTime = 3 * 60 * 60;
    /** token额外信息的key */
    public static final String INFO_KEY = "user";



    /**
     * 验证token
     *
     * @param token
     * @return jwt对象，为空表示无效token
     */
    public static DecodedJWT verifyToken(String token, boolean withICache) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
            Verification verification = JWT.require(algorithm).withIssuer(JwtUtils.ISSUER);
            if (!withICache) {
                verification
                        //单位：秒，有效时长，无效依据：exp > (当前时间 - 有效时长)
                        .acceptExpiresAt(JwtUtils.leewayTime)
                        //单位：秒，有效时长，无效依据：iat < (当前时间 + 有效时长)
                        .acceptIssuedAt(JwtUtils.leewayTime)
                        //单位：秒，有效时长，无效依据：nbf < (当前时间 + 有效时长)
                        .acceptNotBefore(JwtUtils.leewayTime)
                        //单位：秒，通用有效时长, 同时给iat、exp、nbf添加时长，但不覆盖
                        .acceptLeeway(JwtUtils.leewayTime);
            }
            JWTVerifier verifier = verification.build();
            //验证失败报出异常
            DecodedJWT jwt = verifier.verify(token);
            if (withICache) {
                //todo：缓存中间件更新token时间
            }
            return jwt;
        } catch (JWTVerificationException exception) {
            log.error(exception.getMessage(), exception);
        }
        return null;
    }

    /**
     * 获取token中封装额外信息，比如登录用户对象json
     *
     * @param jwt
     * @param infoKey
     * @return
     */
    public static String getClaimInfo(DecodedJWT jwt, String infoKey) {
        infoKey = null == infoKey ? JwtUtils.INFO_KEY : infoKey;
        Claim claim = jwt.getClaim(infoKey);
        String json = claim.asString();
        return json;
    }

    /**
     * 创建token
     *
     * @param loginName
     * @param audience
     * @param withICache
     * @return
     */
    public static String createToken(String loginName, String audience, boolean withICache) {
        Assert.notNull(audience, "生成token 签发对象 不能为空");

        JWTCreator.Builder builder = JWT.create()
                // jwt签发者
                .withIssuer(JwtUtils.ISSUER)
                // 接收jwt的一方
                .withAudience(audience)
                .withSubject(loginName)
                //todo: 按业务扩展
                .withClaim(JwtUtils.INFO_KEY, "登录用户信息封装对象Json");

        // 由缓存负责续期，以及超时判断。且服务端可以注销令牌使用
        if (withICache) {
            //todo：缓存中间件设置Token过期时效
            TempUtils.logDebug(log, "缓存中间件设置Token过期时效，服务主动控制");
        } else {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.HOUR_OF_DAY, 3);
            TempUtils.logDebug(log, "默认设置Token固定时长");
            builder.withExpiresAt(now.getTime())
                    .withIssuedAt(now.getTime())
                    .withNotBefore(now.getTime());
        }
        Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
        return builder.sign(algorithm);
    }

    public static void main(String[] args) {
        String token = JwtUtils.createToken("yh", "pc", false);
        System.out.println(token);

        DecodedJWT jwt = JwtUtils.verifyToken(token, false);
        boolean flag = null != jwt;
        System.out.println("token is " + flag);
        if (flag) {
            System.out.println(jwt.getExpiresAt().after(new Date()));
        }
    }
}

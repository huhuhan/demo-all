package com.yh.auth.security.crypto;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

/**
 * 扩展，实现PasswordEncoder，Spring Security 5要求密码使用PasswordEncoder加密
 *
 * @author yanghan
 * @date 2020/5/29
 */
public class CustomPwdEncoder implements PasswordEncoder {

    private static ThreadLocal<Boolean> ignorePwd = new ThreadLocal<Boolean>();

    public static void setIgnore(boolean ignore) {
        ignorePwd.set(ignore);
    }

    /**
     * Encode the raw password.
     * Generally, a good encoding algorithm applies a SHA-1 or greater hash combined with an 8-byte or greater randomly
     * generated salt.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String pwd = rawPassword.toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(pwd.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(digest));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * Verify the encoded password obtained from storage matches the submitted raw password after it too is encoded.
     * Returns true if the passwords match, false if they do not.
     * The stored password itself is never decoded.
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (ignorePwd.get() == null || ignorePwd.get() == false) {
            String enc = this.encode(rawPassword);
            return enc.equals(encodedPassword);
        }
        return true;
    }

}

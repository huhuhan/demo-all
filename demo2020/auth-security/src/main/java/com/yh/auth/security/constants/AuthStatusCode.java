package com.yh.auth.security.constants;

import com.yh.auth.security.api.model.IStatusCode;

/**
 *
 * @author yanghan
 * @date 2020/6/2
 */
public enum AuthStatusCode implements IStatusCode {

    SUCCESS("200", "成功"),
    SYSTEM_ERROR("500", "系统异常"),
    TIMEOUT("401", "访问超时"),
    NO_ACCESS("403", "访问受限"),
    LOGIN_ERROR("405", "登录失败"),
    PARAM_ILLEGAL("100", "参数校验不通过");

    private String code;
    private String desc;
    private String system;

    private final String CURRENT_SYSTEM = "AUTH";

    AuthStatusCode(String code, String description) {
        this.code = code;
        this.desc = description;
        this.system = CURRENT_SYSTEM;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getSystem() {
        return null;
    }
}

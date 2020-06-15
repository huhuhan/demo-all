package com.yh.auth.security.api.model;

/**
 * 系统状态码定义抽象接口<br>
 * 子模块或者系统需要定义自己的系统状态码<br>
 *
 * @author yanghan
 * @date 2020/6/2
 */
public interface IStatusCode {
    /**
     * 状态码
     *
     * @return
     */
    String getCode();

    /**
     * 异常信息
     *
     * @return
     */
    String getDesc();

    /**
     * 系统编码
     *
     * @return
     */
    String getSystem();

}

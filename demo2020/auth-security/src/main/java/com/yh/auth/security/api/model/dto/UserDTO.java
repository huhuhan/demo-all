package com.yh.auth.security.api.model.dto;

import com.yh.auth.security.api.model.IUser;
import lombok.Data;

import java.util.Date;


/**
 * 实际数据库用户表实体类
 *
 * @author yanghan
 * @date 2020/6/2
 */
@Data
public class UserDTO implements IUser {

    /**
     * id_
     */
    protected String id;

    /**
     * 姓名
     */
    protected String userName;

    /**
     * 账号
     */
    protected String loginName;

    /**
     * 密码
     */
    protected String password;

    /**
     * 邮箱
     */
    protected String email;

    /**
     * 手机号码
     */
    protected String mobile;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 地址
     */
    protected String address;

    /**
     * 头像
     */
    protected String avatar;

    /**
     * 性别：男，女，未知
     */
    protected String sex;

    /**
     * 来源
     */
    protected String from;

    /**
     * 0:禁用，1正常
     */
    protected Integer status;

    /****************************相同意思的字段，赋值，满足不同业务******************/
    @Override
    public String getUserId() {
        return this.id;
    }

    @Override
    public void setUserId(String userId) {
        this.id = userId;
    }

    @Override
    public String getFullname() {
        return this.userName;
    }

    @Override
    public void setFullname(String fullName) {
        this.userName = fullName;
    }

    @Override
    public String getAccount() {
        return this.loginName;
    }

    @Override
    public void setAccount(String account) {
        this.loginName = account;
    }
}
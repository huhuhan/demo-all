package com.yh.demo.starter.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author yanghan
 * @date 2020/3/29
 */
@ConfigurationProperties(prefix = "hello")
public class HelloProperties {
    /** 开启starter的匹配属性，测试特意用String类型 */
    private String enabled;
    private final String MSG = "World";
    /** 信息 */
    private String msg = MSG;
    /** 成员集合 */
    private List<String> members = new ArrayList<>();

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getMSG() {
        return MSG;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
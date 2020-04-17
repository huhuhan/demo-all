package com.yh.demo.redis.sample;

import java.io.Serializable;
import java.util.List;

public class PersonVo implements Serializable{

    private String id;

    private List<String> data;

    private UserVo user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }
}

package com.yh.demo.annotation.my2;

public class User2 {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    @Init(value = "XiaoMing")
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    @Init(value = "23")
    public void setAge(String age) {
        this.age = age;
    }
}

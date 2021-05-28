package com.yh.demo.annotation.my1;

public class Person {

    @PersonAnnotation(name = "Xiaoming")
    private String name;

    private String age;

    @PersonAnnotation(name = "XiaoMing")
    public void sayWhat(String name) {
        System.out.println("sayWhat: " + name);
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

package com.yh.demo.annotation.my2;

public class Test {
    public static void main(String[] args) throws Exception {
        User user = new User();

        user.setName("liang");
        user.setAge("1");

        System.out.println(UserFactory.check(user));
    }

    public static void test1() {
        User2 user = UserFactory.create();

        System.out.println(user.getName());
        System.out.println(user.getAge());
    }
}

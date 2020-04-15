package com.yh.demo.annotation.my1;

import java.lang.reflect.Method;

public class TestFactory {
    public static Person create() {
        Person person = new Person();

        // 获取Person类中所有的方法（getDeclaredMethods也行）
        Method[] methods = Person.class.getMethods();

        try {
            for (Method method : methods) {
                // 如果此方法有注解，就把注解里面的数据赋值到user对象
                if (method.isAnnotationPresent(PersonAnnotation.class)) {
                    PersonAnnotation init = method.getAnnotation(PersonAnnotation.class);
                    method.invoke(person, init.name());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return person;
    }

    public static void main(String[] args) {
        System.err.println(TestFactory.create().getName());
    }
}

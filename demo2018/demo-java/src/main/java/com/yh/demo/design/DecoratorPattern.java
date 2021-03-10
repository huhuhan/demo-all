package com.yh.demo.design;

/**
 * 装饰者模式
 * @author yanghan
 * @date 2021/3/9
 */
public class DecoratorPattern {
    public static void main(String[] args) {

        Person teacher = new Father();
        teacher.say();

        System.out.println("***************");
        MathTeacherDecorator mathTeacherDecorator = new MathTeacherDecorator(new Father());
        mathTeacherDecorator.say();

    }
}

interface Person {
    void say();
}

class Father implements Person {

    @Override
    public void say() {
        System.out.println("I am a Father !");
    }
}

class Sun implements Person {

    @Override
    public void say() {
        System.out.println("I am a Sun !");
    }
}

abstract class TeacherDecorator implements Person {
    protected Person teacherDecorator;

    public TeacherDecorator(Person teacherDecorator) {
        this.teacherDecorator = teacherDecorator;
    }

    @Override
    public void say() {
        teacherDecorator.say();
    }
}

class MathTeacherDecorator extends TeacherDecorator {

    public MathTeacherDecorator(Person teacherDecorator) {
        super(teacherDecorator);
    }

    @Override
    public void say() {
        teacherDecorator.say();
        mySay(teacherDecorator);
    }

    private void mySay(Person teacherDecorator) {
        System.out.println("I am also a Math Teacher !");
    }
}
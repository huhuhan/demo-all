package com.yh.demo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行后根目录查看tgc.log日志
 * @author yanghan
 * @date 2021/6/8
 */
public class Demo {
    final int NUMBER = 20;
    private static Student student = new Student();

    //运行如下代码探究运行时常量池的位置
    public static void main(String[] args) throws Throwable {
        //用list保持着引用 防止full gc回收常量池
        List<String> list = new ArrayList<String>();
        int i = 0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
class Student {

    private String name;

    private Integer age;
}

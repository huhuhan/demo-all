package com.yh.demo.threadlocal;

/**
 * 父类线程私有数据，传递子线程
 * @author yanghan
 * @date 2021/3/17
 */
public class InheritableThreadLocalTest {
    private static ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        threadLocal.set("帅得一匹");
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println( "张三帅么 =" + threadLocal.get());
            }
        };
        t.start();
    }
}


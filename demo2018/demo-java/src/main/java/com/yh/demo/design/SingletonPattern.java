package com.yh.demo.design;

/**
 * 单例模式
 *
 * @author yanghan
 * @date 2021/3/9
 */
public class SingletonPattern {
    public static void main(String[] args) {

        //不合法的构造函数
        //编译时错误：构造函数 SingleObject() 是不可见的
//        SingleObject object = new SingleObject();

        //获取唯一可用的对象
        SingleObject object = SingleObject.getInstance();
        SingleObject object2 = SingleObject.getInstance();

        System.out.println(object == object2);
    }
}

class SingleObject {

    //创建 SingleObject 的一个对象
    private static SingleObject instance = new SingleObject();

    // 所有构造函数为 private，不可被实例化
    private SingleObject() {
    }

    public static synchronized SingleObject getInstance() {
        return instance;
    }
}

/**
 * 懒汉式，线程不安全，不推荐
 */
class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

/**
 * 懒汉式，线程安全，不推荐
 */
class Singleton2 {
    private static Singleton2 instance;

    private Singleton2() {
    }

    // 加同步锁
    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * 饿汉式，线程安全
 * jvm装载类时，单线程操作，线程安全
 */
class Singleton3 {
    /** 没有懒加载效果，即JVM装载类直接初始化，和内存有关（类属性都不推荐直接给初始值，尤其POJO类）**/
    private static Singleton3 instance = new Singleton3();
    private Singleton3 (){}
    public static Singleton3 getInstance() {
        return instance;
    }
}

/**
 * 双重校验锁，线程安全
 */
class Singleton4 {
    private volatile static Singleton4 singleton;
    private Singleton4 (){}
    public static Singleton4 getSingleton() {
        if (singleton == null) {
            synchronized (Singleton4.class) {
                if (singleton == null) {
                    singleton = new Singleton4();
                }
            }
        }
        return singleton;
    }
}

/**
 * 静态内部类，线程安全，且懒加载
 */
class Singleton5 {
    private static class SingletonHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }
    private Singleton5 (){}
    public static Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

/**
 * 枚举方式
 * 优点：简洁、枚举的自动序列化机制不用担心反序列化
 */
enum Singleton6 {
    INSTANCE;
}
package com.yh.demo.java8;

import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.yh.demo.java8.MyFunctionalInterface.staticTodo;

/**
 * @author yanghan
 * @date 2020/12/14
 */
public class Java8FunctionTest {
    public static void main(String[] args) {
    }

    @Test
    public void test1() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("普通写法|Runnable接口的run方法");
            }
        }).start();

        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(1000);
            System.out.println("函数式编程写法|Callable接口的call方法");
            return "call方法返回值";
        });
        futureTask.run();
        System.out.println(futureTask.get());

        new Thread(() -> System.out.println("函数式编程写法|若只有一行代码可以简化前后的{}符号")).start();
    }

    @Test
    public void test2() {
        String param = "测试参数";
        MyFunctionalInterface<String> my1 = new MyFunctionalInterface<String>() {
            @Override
            public void todo(String p) {
                System.out.println("普通写法：创建匿名内部类，重写【抽象方法】| " + p);
            }
        };
        my1.todo(param);

        MyFunctionalInterface<String> my2 = (p) -> {
            System.out.println("Lamada写法：函数，就是重写【抽象方法】| " + p);
        };
        my2.todo(param);
        // 默认方法
        my2.defaultTodo(param);
        // 静态方法
        staticTodo();
    }

    @Test
    public void test3() {
        String param = "测试参数";
        // 方法引用
        MyFunctionalInterface<String> my1 = System.out::println;
        my1.todo("方法引用写法：函数就是System.out.println()方法");

        // 引用的是静态方法staticMyPrint(Object t)
        MyFunctionalInterface<String> my2 = MySystemOutPrint::staticMyPrint;
        my2.todo(param);

        // 引用的是实例方法myPrint(T t)
        MySystemOutPrint<String> mySystemOutPrint = new MySystemOutPrint<>();
        MyFunctionalInterface<String> my3 = mySystemOutPrint::myPrint;
        my3.todo(param);

        // 引用的是构造方法MySystemOutPrint(T t)，因为函数接口的抽象方法需要传参
        MyFunctionalInterface<String> my4 = MySystemOutPrint::new;
        my4.todo(param);
    }

    /**
     * Predicate<T>，抽象方法test(T t)，返回boolean
     */
    @Test
    public void test4() {
        Predicate<String> predicate = Objects::nonNull;
        predicate.and((s) -> s.equals("yh"));
        predicate.or(String::isEmpty);

        System.out.println(predicate.test("yh"));
        System.out.println(predicate.test(""));
        // 反义negate()
        System.out.println(predicate.negate().test(null));
    }


    /**
     * Function<T,R>，抽象方法apply(T t)，返回R
     */
    @Test
    public void test5() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        Function<String, String> toYH = backToString.andThen((s) -> "yh");
        System.out.println(backToString.apply("123"));
        System.out.println(toYH.apply("123"));
    }

    /**
     * Supplier<T>，抽象方法get()，返回T
     */
    @Test
    public void test6() {
        Supplier<MySystemOutPrint> testSupplier = MySystemOutPrint::new;
        MySystemOutPrint my = testSupplier.get();
        System.out.println(my);
        // 每次获取对象不同，都是通过函数重新执行获得
        System.out.println(my.equals(testSupplier.get()));

        // 常规写法，直接传入参数
        int[] ints = new int[]{1, 2, 3, 4};
        System.out.println(mySupplierNormal(ints));

        // 通过传入函数，内部调用get()再计算获取结果
        System.out.println(mySupplier(() -> IntStream.of(ints).sum()));
        System.out.println(mySupplier(() -> IntStream.of(ints).reduce(1, (a, b) -> a * b)));
    }

    // 生产者，传函数，生成数据，具体怎么生成自己设计
    private static String mySupplier(Supplier<Integer> function) {
        return "yh | " + function.get();
    }

    // 生产者，传参，生成数据
    private static String mySupplierNormal(int... ints) {
        int sum = IntStream.of(ints).sum();
        return "yh | " + sum;
    }

    /**
     * Consumer<T>，抽象方法accept(T t)，无返回
     */
    @Test
    public void test7() {
        Consumer<String> my = (s) -> System.out.println("yh | " + s);
        my.accept("测试数据");
    }
}


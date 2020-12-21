package com.yh.demo.java8;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.yh.demo.java8.MyFunctionalInterface.staticTodo;

/**
 * @author yanghan
 * @date 2020/12/14
 */
public class Java8StreamTest {
    public static void main(String[] args) {
    }

    /**
     * 并发计算，parallel()
     * 1、多核服务器，默认为cpu核数
     * 2、复杂操作，比如需要多重筛选过滤等等
     */
    @Test
    public void test1() {
        int total = 1000_00_000;
        Supplier<Long> time = System::currentTimeMillis;

        System.out.println(String.format("本计算机的核数：%d", Runtime.getRuntime().availableProcessors()));

        // 产生N个随机数(1 ~ 100)，组成列表
        Random random = new Random();
        List<Integer> list = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            list.add(random.nextInt(100));
        }

        // 单核线程
        long prevTime = time.get();
        list.stream().reduce(Integer::sum).ifPresent(System.out::println);
        System.out.println(String.format("单线程计算耗时：%d", time.get() - prevTime));

        // 多核核线程，关键方法parallel()
        prevTime = time.get();
        list.stream().parallel().reduce(Integer::sum).ifPresent(System.out::println);
        System.out.println(String.format("多线程计算耗时：%d", time.get() - prevTime));
    }

    /**
     * stream的创建方式
     */
    @Test
    public void test2() {
        // 集合创建
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        Stream<String> listStream = list.stream();
        // 数组创建，of内部源码即Arrays.stream(values)
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        // 具体类型数组创建
        IntStream arrayStream = Arrays.stream(new int[]{1, 2, 3});
        // 使用random创建
        DoubleStream doubleStream = new Random().doubles().limit(10);
    }

    /**
     * 中间操作：基本过滤
     */
    @Test
    public void test3() {
        Stream<Integer> stream = Stream.of(2, 1, 3, 4, 5, 5, 6);
        stream
                // 过滤，Predicate接口返回boolean
                .filter(p -> p < 6)
                // 默认升序，可用带函数接口参数的方法，自行排序
                .sorted()
                // 去重
                .distinct()
                // 跳过前N个对象
                .skip(1)
                // 筛选前N个对象
                .limit(3)
                .forEach(System.out::println);
    }

    /**
     * 中间操作：映射类型，如map、flatMap相关函数接口
     */
    @Test
    public void test4() {
        Stream<String> stream = Stream.of("AA", "BB", "CC");
        stream
                .map(String::toLowerCase)
                .forEach(System.out::println);
        // 同一个流处理过就会关闭，不可重复使用
        stream = Stream.of("AA", "BB", "CC");
        Stream<String[]> mapStream = stream.map(m -> m.split(""));
        // 上面结果流的泛型是数组，可用flatXX方法扁平化，即集合数组合并为一个
        mapStream.flatMap(Arrays::stream)
                .forEach(System.out::println);
    }

    /**
     * 中间操作：peek，基于Consumer函数接口，只处理返回值
     */
    @Test
    public void test44() {
        // 惰性执行，没有输出
        Stream<String> peekStream = Stream.of("yh").peek(System.out::println);
        // 有最终操作，才执行peek方法
        peekStream.forEach(System.out::println);
    }

    /**
     * 最终操作：归约，reduce，适合重复操作，比如求和之类
     */
    @Test
    public void test5() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        System.out.println(numbers.stream().reduce(0, Integer::sum));
        System.out.println(numbers.stream().reduce(1, (a, b) -> a * b));
        System.out.println(numbers.stream().reduce(1, Integer::max));
        System.out.println(numbers.stream().reduce(1, Integer::min));
        System.out.println(numbers.stream().skip(3).count());
    }

    /**
     * 最终操作：查找匹配
     */
    @Test
    public void test6() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        System.out.println(numbers.stream().allMatch(p -> p == 2));
        System.out.println(numbers.stream().anyMatch(p -> p == 2));
        System.out.println(numbers.stream().noneMatch(p -> p == 5));
    }


    @Test
    public void test8() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        // 遍历
        numbers.stream().limit(1).forEach(System.out::println);
        // 集合，Collectors有许多好用的静态方法，如joining、groupBy等等
        List<Integer> filterList = numbers.stream().limit(3).collect(Collectors.toList());
        // 数组
        Integer[] filterArray = numbers.stream().sorted().toArray(Integer[]::new);
    }

    /**
     * 最终操作：返回Optional<T>对象，防止空指针异常
     * 以下情况可能因为空集合得到的流没有结果
     */
    @Test
    public void test9() {
        String isNull = "isNull";
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        // 按顺序匹配第一个结果
        Optional<Integer> anyInt = numbers.stream().filter(p -> p % 5 == 0).findAny();
        System.out.println(anyInt.orElse(0));
        System.out.println(anyInt.isPresent() ? anyInt.get() : isNull);
        // 最小
        Optional<Integer> minInt = numbers.stream().min(Integer::compareTo);
        // 最大
        Optional<Integer> maxInt = numbers.stream().max(Integer::compareTo);
        // 第一个值
        Optional<Integer> firstInt = numbers.stream().findFirst();
        // 没有初始值
        Optional<Integer> intA = numbers.stream().reduce(Integer::sum);
    }
}


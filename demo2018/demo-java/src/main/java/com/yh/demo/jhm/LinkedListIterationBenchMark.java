package com.yh.demo.jhm;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
/**
 * Java Microbenchmark Harness (微基准测试框架）JHM测试例子，java9内置，目前是引入依赖包
 * 基于Benchmark 函数测试，可精确到微秒级
 * @author yanghan
 * @date 2022/3/4
 */
// 状态，这里选择的是所有线程间共享
@State(Scope.Benchmark)
// 测试结果输出单位
@OutputTimeUnit(TimeUnit.SECONDS)
// 每个进程的测试现场数量
@Threads(Threads.MAX)
public class LinkedListIterationBenchMark {
    private static final int SIZE = 10000;
    /** 测试10000的循环遍历，哪种更快 */
    private List<String> list = new LinkedList<>();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 有include，自然也有exclude方法
                // 可以用方法名，也可以用XXX.class.getSimpleName()
                .include(LinkedListIterationBenchMark.class.getSimpleName())
                // 预热2轮
                .warmupIterations(2)
                // 代表正式计量测试做2轮，
                // 而每次都是先执行完预热再执行正式计量，
                // 内容都是调用标注了@Benchmark的代码。
                .measurementIterations(2)
                //  forks(3)指的是做3轮测试，
                // 因为一次测试无法有效的代表结果，
                // 所以通过3轮测试较为全面的测试，
                // 而每一轮都是先预热，再正式计量。
                .forks(3)
                // 结果输出到文件中，需要提前创建
                //.output("D:\\usr\\Benchmark.log")
                .build();

        new Runner(opt).run();
    }

    // 测试准备阶段
    @Setup
    public void setUp() {
        for (int i = 0; i < SIZE; i++) {
            list.add(String.valueOf(i));
        }
    }

    // 需要进行benchmark的对象，类型测@Test
    @Benchmark
    // 微基准测试类型，这里是Throughput模式(每段时间执行的次数，单位秒)
    @BenchmarkMode(Mode.Throughput)
    public void forIndexIterate() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
            System.out.print("");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forEachIterate() {
        for (String s : list) {
            System.out.print("");
        }
    }
}
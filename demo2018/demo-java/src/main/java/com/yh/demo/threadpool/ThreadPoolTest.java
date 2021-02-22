package com.yh.demo.threadpool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 线程池的创建方式
 *
 * @author yanghan
 * @date 2021/02/22
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
        testS1();
    }

    /**
     * 固定任务数量的线程池
     */
    public static void test1() {
        // 创建 2 个数据级的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // 创建任务
        Runnable runnable = () -> System.out.println("任务被执行,线程:" + Thread.currentThread().getName());

        // 线程池执行任务(一次添加 4 个任务)
        // 执行任务的方法有两种:submit 和 execute
        threadPool.submit(runnable);  // 执行方式 1:submit
        threadPool.execute(runnable); // 执行方式 2:execute
        threadPool.execute(runnable);
        threadPool.execute(runnable);

        threadPool.shutdown();
    }

    /**
     * 可缓存的线程池，线程用完一段时间自动回收
     */
    public static void test2() {
        // 创建线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        // 执行任务
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                System.out.println("任务被执行,线程:" + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
    }


    /**
     * 单线程-线程池，作用便于生命周期维护管理
     */
    public static void test3() {
        // 创建线程池
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // 执行任务
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threadPool.execute(() -> {
                System.out.println(index + ":任务被执行");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
    }

    /**
     * 可延迟执行的线程池
     */
    public static void test4() {
        // 创建线程池
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
        // 添加定时执行任务(1s 后执行)
        System.out.println("添加任务,时间:" + new Date());
        threadPool.schedule(() -> {
            System.out.println("任务被执行,时间:" + new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);

        threadPool.shutdown();
    }

    /**
     * 可延迟执行的单线程-线程池
     */
    public static void test5() {
        // 创建线程池
        ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
        // 添加定时执行任务(2s 后执行)
        System.out.println("添加任务,时间:" + new Date());
        threadPool.schedule(() -> {
            System.out.println("任务被执行,时间:" + new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.SECONDS);

        threadPool.shutdown();
    }

    /**
     * 抢占式线程池，任务执行的线程顺序不确定
     */
    public static void test6() {
        // 创建线程池
        ExecutorService threadPool = Executors.newWorkStealingPool();
        // 执行任务
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threadPool.execute(() -> {
                System.out.println(index + " 被执行,线程名:" + Thread.currentThread().getName());
            });
        }

        // 启动一次顺序关闭：当前线程池已提交的任务，按顺序都执行完毕后，线程池关闭
        threadPool.shutdown();
        // 抢占式，无关顺序；
        // 所以任务都执行完毕，isTerminated为true，必须先调用过shutdown()
        while (!threadPool.isTerminated()) {
            //System.out.println("......还有任务没有完成");
        }

    }


    /**
     * 自定义配置参数的线程池
     */
    public static void test7() {
        // 创建线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                //核心线程数，线程池中始终存活的线程数
                5,
                //最大线程数，线程池中允许的最大线程数，当线程池的任务队列满了之后可以创建的最大线程数
                10,
                //最大线程数可以存活的时间，当线程中没有任务执行时，最大线程就会销毁一部分，最终保持核心线程数量的线程
                100,
                // keepAliveTime的单位，共7种，天、小时、分、秒、毫秒、微秒、纳秒
                TimeUnit.SECONDS,
                // 一个阻塞队列，用来存储线程池等待执行的任务，均为线程安全，源码接口BlockingQueue的实现类
                new LinkedBlockingQueue<>(10)
                // 线程工厂，主要用来创建线程，
                // 参数6：默认为正常优先级、非守护线程
                // 拒绝策略，拒绝处理任务时的策略，源码接口RejectedExecutionHandler实现类
                // 参数7：默认AbortPolicy：拒绝并抛出异常
        );
        // 执行任务
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threadPool.execute(() -> {
                System.out.println(index + " 被执行,线程名:" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
    }

    public static void testS1() {
        // 任务的具体方法
        Runnable runnable = () -> {
            System.out.println("当前任务被执行,执行时间:" + new Date() +
                    " 执行线程:" + Thread.currentThread().getName());
            try {
                // 等待 1s
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 创建线程,线程的任务队列的长度为 1
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1,
                100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1),
//                new ThreadPoolExecutor.AbortPolicy()
                new ThreadPoolExecutor.DiscardPolicy()
        );
        // 添加并执行 4 个任务
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);

        threadPool.shutdown();
    }
}


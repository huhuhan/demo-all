package com.yh.demo.thread;

import com.yh.demo.thread.async.AsyncDemo;
import com.yh.demo.thread.async.AsyncExceptionDemo;
import com.yh.demo.thread.async.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest//(classes = AsyncApplicationWithAnnotation.class)
public class AsyncApplicationTests {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private AsyncDemo asyncDemo;

    /**
     * 异步线程测试
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsyncDemo() throws InterruptedException, ExecutionException {
        System.err.println("part1");
        asyncDemo.asyncInvokeSimplest();
        System.err.println("part2");
        asyncDemo.asyncInvokeWithParameter("test");
        System.err.println("part3");
        Future<String> future = asyncDemo.asyncInvokeReturnFuture(100);
        System.err.println("future get " + future.get());
    }


    @Autowired
    private AsyncExceptionDemo asyncExceptionDemo;

    /**
     * 测试异步线程异常抛出，当前线程的正常运行
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsyncExceptionDemo() throws InterruptedException, ExecutionException {
        System.err.println("part1");
        asyncExceptionDemo.asyncInvokeSimplest();
        System.err.println("part2");
        asyncExceptionDemo.asyncInvokeWithException("test");
        System.err.println("part3");
        Future<String> future = asyncExceptionDemo.asyncInvokeReturnFuture(100);
        System.err.println("future get " + future.get());
    }


    @Autowired
    private AsyncTask asyncTask;

    /**
     * 测试指定不同线程池的线程调用
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsyncTask() throws InterruptedException, ExecutionException {
        Future<String> task1 = asyncTask.doTask1();
        Future<String> task2 = asyncTask.doTask2();

        while (true) {
            if (task1.isDone() && task2.isDone()) {
                logger.info("Task1 result: {}", task1.get());
                logger.info("Task2 result: {}", task2.get());
                break;
            }
            Thread.sleep(1000);
        }

        logger.info("All tasks finished.");
    }
}
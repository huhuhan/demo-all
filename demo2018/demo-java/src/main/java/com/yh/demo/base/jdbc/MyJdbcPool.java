package com.yh.demo.base.jdbc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 连接池
 * @author yanghan
 * @date 2021/4/29
 */
public class MyJdbcPool {
    private int maxActive;
    private long maxWait;
    private int maxIdle;

    /** 数据库连接池里的Jdbc连接有2种状态，一是正在使用中，二是用过以后又被返还的空闲中 */
    private LinkedBlockingQueue<MyJdbcConnect> busy;
    private LinkedBlockingQueue<MyJdbcConnect> idle;
    /** 目前在池子中已创建的连接数（不能大于最大连接数maxActive） */
    private AtomicInteger createdCount = new AtomicInteger(0);

    /**
     * 连接池初始化
     *
     * @param maxActive 最大连接数量，连接数连不能超过该值
     * @param maxWait   超时等待时间以毫秒为单位 6000毫秒/1000等于60秒，当连接超过该时间便认为其实空闲连接
     * @param maxIdle   最大空闲连接，当空闲连接超过该值时就挨个关闭多余的连接，但不能小于minldle
     */
    public void init(int maxActive, long maxWait, int maxIdle) {
        this.maxActive = maxActive;
        this.maxWait = maxWait;
        this.maxIdle = maxIdle;
        this.busy = new LinkedBlockingQueue<>();
        this.idle = new LinkedBlockingQueue<>();
    }

    /**
     * 从连接池中获取数据库连接。忽略poll、offer的结果判断。
     */
    public MyJdbcConnect getResource() throws Exception {
        MyJdbcConnect myJdbcConnect = idle.poll();
        // 有空闲的可以用
        if (myJdbcConnect != null) {
            boolean offerResult = busy.offer(myJdbcConnect);
            return myJdbcConnect;
        }
        // 没有空闲的，看当前已建立的连接数是否已达最大连接数maxActive
        if (createdCount.get() < maxActive) {
            // 已建立9个，maxActive=10。3个线程同时进来..
            if (createdCount.incrementAndGet() <= maxActive) {
                myJdbcConnect = new MyJdbcConnect();
                boolean offerResult = busy.offer(myJdbcConnect);
                return myJdbcConnect;
            } else {
                createdCount.decrementAndGet();
            }
        }
        // 达到了最大连接数，需等待释放连接
        myJdbcConnect = idle.poll(maxWait, TimeUnit.MILLISECONDS);
        if (myJdbcConnect != null) {
            boolean offerResult = busy.offer(myJdbcConnect);
            return myJdbcConnect;
        } else {
            throw new Exception("等待超时！");
        }
    }

    /**
     * 将数据库连接返还给连接池。忽略poll、offer的结果判断。
     */
    public void returnResource(MyJdbcConnect jdbcConnect) {
        if (jdbcConnect == null) {
            return;
        }
        // 忽略连接状态的检查
        // jdbcConnect.getConnection().isClosed()
        boolean removeResult = busy.remove(jdbcConnect);
        if (removeResult) {
            // 控制空闲连接的数量
            if (maxIdle <= idle.size()) {
                jdbcConnect.close();
                createdCount.decrementAndGet();
                return;
            }
            boolean offerResult = idle.offer(jdbcConnect);
            if (!offerResult) {
                jdbcConnect.close();
                createdCount.decrementAndGet();
            }
        } else {
            // 无法复用
            jdbcConnect.close();
            createdCount.decrementAndGet();
        }
    }
}
package com.yh.demo.base.jdbc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * 测试演示
 *
 * @author yanghan
 * @date 2021/4/29
 */
@Slf4j
@Data
public class JdbcTestMain {
    private final static int THREAD_NUM = 200;
    private final static CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(THREAD_NUM);

    public static void main(String[] args) {
        queryByPool();
    }

    // 连接太多，无法正常调用
    private static void query(){
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    COUNT_DOWN_LATCH.await();
                    String sql = "select * from sys_dict limit 1";
                    Connection connection = new MyJdbcConnect().getConnection();
                    ResultSet resultSet = connection.createStatement().executeQuery(sql);
                    resultSet.next();
                    log.info("{} 查询结果：{}", Thread.currentThread().getName(), resultSet.getString("name_"));
                } catch (InterruptedException | SQLException e) {
                    log.error(e.getMessage());
                }
            }).start();
            COUNT_DOWN_LATCH.countDown();
        }
    }


    private static void queryByPool(){
        MyJdbcPool pool = new MyJdbcPool();
        pool.init(20, 2000, 10);
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                MyJdbcConnect connect = null;
                try {
                    COUNT_DOWN_LATCH.await();

                    String sql = "select * from sys_dict limit 1";
                    connect = pool.getResource();
                    Connection connection = connect.getConnection();
                    ResultSet resultSet = connection.createStatement().executeQuery(sql);
                    resultSet.next();
                    log.info("{} 查询结果：{}", Thread.currentThread().getName(), resultSet.getString("name_"));
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    pool.returnResource(connect);
                }
            }).start();
            COUNT_DOWN_LATCH.countDown();
        }
    }
}

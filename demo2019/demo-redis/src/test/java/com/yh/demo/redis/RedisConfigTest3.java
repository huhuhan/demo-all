package com.yh.demo.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest3 {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试 事物
     *
     * @return
     */
    @Test
    public void multi() {

        List<Object> objects = redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.multi();
                redisConnection.set("trans1".getBytes(), "事物1".getBytes());
                redisConnection.set("trans2".getBytes(), "trans2".getBytes());
                redisConnection.mGet("trans1".getBytes(), "trans2".getBytes());
                List<Object> list = redisConnection.exec();
                return list;
            }
        });

        List<byte[]> data = (ArrayList<byte[]>) objects.get(2);
        List<String> rData = data.stream()
                .map(x -> {
                    byte[] t = (byte[]) x;
                    System.out.println("数据------>" + new String(t));
                    return new String(t);
                }).collect(Collectors.toList());

        rData.forEach(System.out::println);
    }

    /**
     * 事务只能在所有被监视键都没有被修改的前提下执行，
     * 如果这个前提不能满足的话，事务就不会被执行。
     */
    @Test
    public void watch() {

        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.get("key".getBytes());
                return null;
            }
        });
        List<Object> objects = redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                List<Object> list = null;
                do {
                    //观察key
                    connection.watch("mykey".getBytes());
                    connection.multi();
                    //自增
                    connection.incrBy("mykey".getBytes(), 1);
                    connection.incrBy("mykey".getBytes(), 1);
                    //如果事物执行期间mykey的值没有被修改则执行成功,否则执行失败
                    list = connection.exec();
                    //如果执行失败  list.size()==0
                }
                //失败重试，直到执行成功
                while (list == null || list.size() == 0);
                return list;
            }
        });

        if (objects.size() > 0) {
            System.out.println("mykey-------->" + (long) objects.get(0));
        }

        if (objects.size() > 1) {
            System.out.println("mykey-------->" + (long) objects.get(1));
        }

    }
}
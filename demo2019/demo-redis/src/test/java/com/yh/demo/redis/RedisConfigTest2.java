package com.yh.demo.redis;

import com.yh.demo.redis.sample.PersonVo;
import com.yh.demo.redis.sample.RedisCache;
import com.yh.demo.redis.sample.RedisStringUtil;
import com.yh.demo.redis.sample.UserVo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 基于RedisTemplate模板自定义封装类测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest2 {

    @Autowired
    private RedisStringUtil redisString;
    @Autowired
    private RedisCache redisCache;

    @Test
    public void hello() {
        // redis操作
        redisString.setKey("hello2", "Hello Redis! by 2019.1.16");


        System.out.println("Hello Demo");
        System.out.println(redisString.getValue("hello"));
    }


    @Test
    public void redisCache() throws Exception {

        UserVo userVo = new UserVo();
        userVo.setAddress("fdsfsf");
        userVo.setName("Fsdf");

        PersonVo personVo = new PersonVo();
        personVo.setData(Lists.newArrayList("1","2"));
        personVo.setUser(userVo);
        personVo.setId("1111111111111111");
        // redis操作
        redisCache.updateCached(5, "PersonVo", personVo, null);


        System.out.println("Hello Demo");
        PersonVo personVo1 = (PersonVo) redisCache.getCached(5, "PersonVo2");
        System.out.println(personVo1);
    }

}
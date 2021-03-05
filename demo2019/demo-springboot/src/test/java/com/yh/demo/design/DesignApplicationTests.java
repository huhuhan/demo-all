package com.yh.demo.design;

import com.yh.demo.design.listener.AgeEvent;
import com.yh.demo.design.listener.NameEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = DesginSpringApplication.class)
@SpringBootTest//(classes = DesignSpringApplication.class/*, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class DesignApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testListener() {
        applicationContext.publishEvent(new AgeEvent("This is the source", "11"));
        // 没有指定的监听器，不触发
        applicationContext.publishEvent(new NameEvent("This is the source", "张三"));
    }
}

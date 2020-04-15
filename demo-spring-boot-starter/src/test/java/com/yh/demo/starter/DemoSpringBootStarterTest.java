package com.yh.demo.starter;

import com.yh.demo.starter.autoconfigure.Hello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSpringBootStarterTest {

    @Autowired
    private Hello hello;

    @Test
    public void InitData() {
        hello.sayHello();
    }
}

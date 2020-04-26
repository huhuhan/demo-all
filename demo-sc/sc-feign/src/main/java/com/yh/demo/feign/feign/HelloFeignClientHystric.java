package com.yh.demo.feign.feign;

import org.springframework.stereotype.Component;

/**
 * 熔断接口实现类
 * @author yanghan
 * @date 2019/4/26
 */
@Component
public class HelloFeignClientHystric implements HelloFeignClient {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry " + name + " request server is stop";

    }
}

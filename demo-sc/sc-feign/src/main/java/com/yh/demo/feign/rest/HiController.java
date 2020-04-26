package com.yh.demo.feign.rest;

import com.yh.demo.feign.feign.HelloFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author yanghan
 * @date 2020/4/26
 */
@RestController
public class HiController {
    @Autowired
    private HelloFeignClient helloFeignClient;

    @RequestMapping(value = "/hi")
    public String sayHi(@RequestParam String name){
        return helloFeignClient.sayHiFromClientOne(name);
    }
}

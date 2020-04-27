package com.yh.demo.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yanghan
 * @date 2020/4/26
 */
@Service
public class HelloService {
    private final String CLIENT_A = "SC-CLIENT-HELLO";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 基于RestTemplate实现服务请求
     * 添加熔断器功能，服务请求终端，返回固定值
     *
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://" + CLIENT_A + "/hi?name=" + name, String.class);
    }

    @HystrixCommand(commandKey = "clientA-2", fallbackMethod = "hiError")
    public String hiService2(String name) {
        return restTemplate.getForObject("http://" + CLIENT_A + "/hi?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "Hi," + name + ",sorry, error！";
    }
}

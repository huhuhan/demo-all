package com.yh.demo.feign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 指定请求的服务别名，同时配置请求失败时响应结果
 * @author yanghan
 * @date 2020/4/26
 */
@FeignClient(value = "sc-client-hello", fallback = HelloFeignClientHystric.class)
public interface HelloFeignClient {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

}

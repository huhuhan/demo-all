package com.yh.demo.zuul.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanghan
 * @date 2020/4/26
 */
@RestController
public class HelloController {
    /**
     * 访问/api/client-z/hello
     * @return
     */
    @GetMapping(value = "/test/hello")
    public String testForward() {
        return "本地跳转，test-forward !";
    }
}

package com.yh.demo.client.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanghan
 * @date 2020/4/26
 */
@RestController
public class HelloController {

    @GetMapping(value = "/hi")
    public String hi(@RequestParam(required = false) String name) {
        if (null != name) {
            return "Hello " + name + " !";
        }
        return "Hello World !";
    }

    /**
     * 测试Zuul路由忽略
     * @return
     */
    @GetMapping(value = "/route-ignore/zuul")
    public String routeIgnore() {
        return "route-ignore !";
    }

}

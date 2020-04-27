package com.yh.demo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 1、全局拦截，参考MyFilter
 * 2、路由配置，参考application.yml，默认采用eureka的负载均衡（ribbon实现，即部署多个不同端口的相同服务提供者）
 */
@EnableZuulProxy
@SpringBootApplication
public class DemoSCZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSCZuulApplication.class, args);
	}
}

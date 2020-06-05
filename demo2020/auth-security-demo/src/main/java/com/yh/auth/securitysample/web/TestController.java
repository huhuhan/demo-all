package com.yh.auth.securitysample.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个是一个用于测试的controller
 * 测试在不对其他业务代码进行任何修改的情况下，能否通过在数据库中的配置，实现对不同的链接进行不同的权限的操控的功能
 * @author virtualspider
 *
 */
@RestController
@ResponseBody
public class TestController {
	@GetMapping("/")
	public String home() {
		return "这个页面每个人都可以访问";
	}

	
	@GetMapping("/user")
	public String hello() {
		return "这个页面登陆后可以访问";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "这个页面只有admin角色才能访问";
	}
}

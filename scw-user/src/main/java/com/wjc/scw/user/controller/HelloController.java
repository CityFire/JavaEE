package com.wjc.scw.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试Swagger工具的helloworld
 * @author Administrator
 *
 */
@Slf4j
@Api(tags="测试Swagger工具的helloworld")
@RestController
public class HelloController {

	//Logger log = LoggerFactory.getLogger(HelloController.class);
	
	@ApiImplicitParams(value= {
			@ApiImplicitParam(value="姓名",name="name",required=true),
			@ApiImplicitParam(value="年龄",name="age",required=true)
	})
	@ApiOperation("演示接口调用")
	@GetMapping("/hello")
	public String hello(String name,Integer age) {
		
		log.debug("xxxx");
		
		return "OK:" + name +" - " + age;
	}
	
	@ApiImplicitParams(value= {
			@ApiImplicitParam(value="姓名",name="name",required=true),
			@ApiImplicitParam(value="年龄",name="age",required=true)
	})
	@ApiOperation("演示接口调用test")
	@GetMapping("/test")
	public User test(String name,Integer age) {
		User user = new User();
		user.setId(111);
		user.setUname("xxx");
		return user;
	}
}

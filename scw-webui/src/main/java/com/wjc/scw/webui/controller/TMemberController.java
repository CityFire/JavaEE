package com.wjc.scw.webui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TMemberController {

	@RequestMapping("/member/minecrowdfunding")
	public String myOrderList() {
		
		log.debug("支付后，同步请求处理.....");
		
		return "member/minecrowdfunding";
	}
	
}
package com.wjc.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.scw.webui.exp.handler.TMemberServiceFeignExceptionHandler;
import com.wjc.scw.webui.vo.resp.UserAddressVo;
import com.wjc.scw.webui.vo.resp.UserRespVo;

@FeignClient(value = "SCW-USER", fallback = TMemberServiceFeignExceptionHandler.class)
public interface TMemberServiceFeign {

	@PostMapping("/user/login")
	public AppResponse<UserRespVo> login(@RequestParam("loginacct") String loginacct,
			@RequestParam("password") String password);

	@GetMapping("/user/info/address")
	public AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken") String accessToken);

}

package com.wjc.scw.project.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wjc.scw.project.service.exp.handler.MemberServiceFeignExceptionHandler;
import com.wjc.scw.project.vo.resp.TMember;
import com.wjc.scw.vo.resp.AppResponse;

@FeignClient(value="SCW-USER",fallback=MemberServiceFeignExceptionHandler.class)
public interface MemberServiceFeign {

	@GetMapping("/user/info/getMebmerById")
	public AppResponse<TMember> getMebmerById(@RequestParam("id") Integer id);
	
}

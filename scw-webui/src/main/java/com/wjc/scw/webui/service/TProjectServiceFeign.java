package com.wjc.scw.webui.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.scw.webui.exp.handler.TProjectServiceFeignExceptionHandler;
import com.wjc.scw.webui.vo.resp.ProjectDetailVo;
import com.wjc.scw.webui.vo.resp.ProjectVo;
import com.wjc.scw.webui.vo.resp.ReturnPayConfirmVo;

@FeignClient(value = "SCW-PROJECT", fallback = TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {

	@GetMapping("/project/all")
	public AppResponse<List<ProjectVo>> all();

	@GetMapping("/project/details/info/{projectId}")
	public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId);

	@GetMapping("/project/confim/returns/{projectId}/{returnId}")
	public AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("projectId") Integer projectId,
			@PathVariable("returnId") Integer returnId);
}
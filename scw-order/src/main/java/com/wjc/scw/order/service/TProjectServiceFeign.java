package com.wjc.scw.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wjc.scw.order.service.exp.handler.TProjectServiceFeignExceptionHandler;
import com.wjc.scw.order.vo.req.TReturn;
import com.wjc.scw.vo.resp.AppResponse;

@FeignClient(value = "SCW-PROJECT", fallback = TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {

	@GetMapping("/project/details/returns/info/{returnId}")
	public AppResponse<TReturn> returnInfo(@PathVariable("returnId") Integer returnId);

}

package com.wjc.scw.webui.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.scw.webui.exp.handler.TOrderServiceFeignExceptionHandler;
import com.wjc.scw.webui.vo.req.OrderInfoSubmitVo;
import com.wjc.scw.webui.vo.resp.TOrder;

@FeignClient(value="SCW-ORDER",fallback=TOrderServiceFeignExceptionHandler.class)
public interface TOrderServiceFeign {

	/**
	 * 远程调用参数传递问题：
	 * 	①简单   @RequestParam    @PathVariable
	 *  ②复杂对象   @RequestBody
	 * @param vo  
	 * @return
	 */
	@RequestMapping("/order/saveOrder")
	AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo vo);

}

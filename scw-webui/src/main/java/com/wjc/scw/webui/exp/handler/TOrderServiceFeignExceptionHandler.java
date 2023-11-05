package com.wjc.scw.webui.exp.handler;

import org.springframework.stereotype.Component;

import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.scw.webui.service.TOrderServiceFeign;
import com.wjc.scw.webui.vo.req.OrderInfoSubmitVo;
import com.wjc.scw.webui.vo.resp.TOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TOrderServiceFeignExceptionHandler implements TOrderServiceFeign {

	@Override
	public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo vo) {
		AppResponse<TOrder> resp = AppResponse.fail(null);
		resp.setMsg("调用远程服务【保存订单失败】失败");
		log.error("调用远程服务【保存订单失败】失败");
		return resp;
	}

}


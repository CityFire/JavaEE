package com.wjc.scw.order.service.exp.handler;

import org.springframework.stereotype.Component;

import com.wjc.scw.order.service.TProjectServiceFeign;
import com.wjc.scw.order.vo.req.TReturn;
import com.wjc.scw.vo.resp.AppResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {

	@Override
	public AppResponse<TReturn> returnInfo(Integer returnId) {
		AppResponse<TReturn> resp = AppResponse.fail(null);
		resp.setMsg("调用远程服务SCW-PROJECT【根据回报id查询回报】异常");
		return resp;
	}

}

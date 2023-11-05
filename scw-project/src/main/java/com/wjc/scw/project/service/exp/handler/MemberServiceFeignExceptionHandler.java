package com.wjc.scw.project.service.exp.handler;

import org.springframework.stereotype.Component;

import com.wjc.scw.project.service.MemberServiceFeign;
import com.wjc.scw.project.vo.resp.TMember;
import com.wjc.scw.vo.resp.AppResponse;

@Component
public class MemberServiceFeignExceptionHandler implements MemberServiceFeign {

	@Override
	public AppResponse<TMember> getMebmerById(Integer id) {
		AppResponse<TMember> resp = AppResponse.fail(null);
		resp.setMsg("远程调用服务【根据id获取用户/发起人信息】失败");
		return resp;
	}

	
	
}

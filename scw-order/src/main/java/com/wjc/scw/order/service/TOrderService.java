package com.wjc.scw.order.service;

import com.wjc.scw.order.bean.TOrder;
import com.wjc.scw.order.vo.req.OrderInfoSubmitVo;

public interface TOrderService {
	
	TOrder saveOrder(OrderInfoSubmitVo vo);
	
}

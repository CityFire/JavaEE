package com.wjc.scw.order.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.wjc.scw.order.bean.TOrder;
import com.wjc.scw.order.mapper.TOrderMapper;
import com.wjc.scw.order.service.TOrderService;
import com.wjc.scw.order.service.TProjectServiceFeign;
import com.wjc.scw.order.vo.req.OrderInfoSubmitVo;
import com.wjc.scw.order.vo.req.TReturn;
import com.wjc.scw.util.AppDateUtils;
import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.user.enums.OrderStatusEnumes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TOrderServiceImpl implements TOrderService {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	TOrderMapper orderMapper;

	@Autowired
	TProjectServiceFeign projectServiceFeign;

	@Override
	public TOrder saveOrder(OrderInfoSubmitVo vo) {
		TOrder order = new TOrder();

		String accessToken = vo.getAccessToken();
		String memberId = stringRedisTemplate.opsForValue().get(accessToken);

		order.setMemberid(Integer.parseInt(memberId));

		order.setProjectid(vo.getProjectid());
		order.setReturnid(vo.getReturnid());

		String ordernum = UUID.randomUUID().toString().replaceAll("-", "");
		order.setOrdernum(ordernum);

		order.setCreatedate(AppDateUtils.getFormatTime());

		AppResponse<TReturn> respTReturn = projectServiceFeign.returnInfo(vo.getReturnid());// 调用远程服务
		TReturn retObj = respTReturn.getData();

		Integer totalMoney = vo.getRtncount() * retObj.getSupportmoney() + retObj.getFreight();

		order.setMoney(totalMoney);
		order.setRtncount(vo.getRtncount());
		order.setStatus(OrderStatusEnumes.UNPAY.getCode() + "");
		order.setAddress(vo.getAddress());
		order.setInvoice(vo.getInvoice().toString());
		order.setInvoictitle(vo.getInvoictitle());
		order.setRemark(vo.getRemark());

		orderMapper.insertSelective(order);

		log.debug("业务层保存订单={}", order);

		return order;
	}

}

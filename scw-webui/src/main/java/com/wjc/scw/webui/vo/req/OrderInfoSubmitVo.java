package com.wjc.scw.webui.vo.req;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderInfoSubmitVo implements Serializable {
	
	private String address;// 收货地址id
	private Byte invoice;// 0代表不要 1-代表要
	private String invoictitle;// 发票抬头
	private String remark;// 订单的备注

	private String accessToken;
	private Integer projectid;
	private Integer returnid;
	private Integer rtncount;

}

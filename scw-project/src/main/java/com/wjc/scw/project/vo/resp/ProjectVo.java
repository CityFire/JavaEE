package com.wjc.scw.project.vo.resp;

import java.io.Serializable;

import lombok.Data;

//首页，热点项目
@Data
public class ProjectVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343763854579197367L;

	private Integer memberid;// 会员id

	private Integer id;// 项目id

	private String name;// 项目名称
	private String remark;// 项目简介

	private String headerImage;// 项目头部图片

}

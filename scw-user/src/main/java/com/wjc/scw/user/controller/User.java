package com.wjc.scw.user.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Setter
@ApiModel
public class User {
	
	@ApiModelProperty(value="用户主键")
	private Integer id;
	
	@ApiModelProperty("用户姓名")
	private String uname;

//	public Integer getId() {
//		return id;
//	}

//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getUname() {
//		return uname;
//	}
//
//	public void setUname(String uname) {
//		this.uname = uname;
//	}
//
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", uname=" + uname + "]";
//	}

}

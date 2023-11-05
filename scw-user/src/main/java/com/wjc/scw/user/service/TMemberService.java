package com.wjc.scw.user.service;

import java.util.List;

import com.wjc.scw.user.bean.TMember;
import com.wjc.scw.user.bean.TMemberAddress;
import com.wjc.scw.user.vo.req.UserRegistVo;
import com.wjc.scw.user.vo.resp.UserRespVo;

public interface TMemberService {

	int saveTMember(UserRegistVo vo);

	UserRespVo getUserByLogin(String loginacct, String password);

	TMember getMebmerById(Integer id);

	List<TMemberAddress> listAddress(Integer memberId);

}

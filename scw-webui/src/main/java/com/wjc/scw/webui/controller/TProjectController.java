package com.wjc.scw.webui.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.scw.webui.service.TMemberServiceFeign;
import com.wjc.scw.webui.service.TProjectServiceFeign;
import com.wjc.scw.webui.vo.resp.ProjectDetailVo;
import com.wjc.scw.webui.vo.resp.ReturnPayConfirmVo;
import com.wjc.scw.webui.vo.resp.UserAddressVo;
import com.wjc.scw.webui.vo.resp.UserRespVo;

@Controller
public class TProjectController {

	@Autowired
	TProjectServiceFeign projectServiceFeign;

	@Autowired
	TMemberServiceFeign memberServiceFeign;

	// 去结算
	@RequestMapping("/project/confirm/order/{num}")
	public String confirmOrder(@PathVariable("num") Integer num, Model model, HttpSession session) {

		// 1.数据展示 - 地址信息
		UserRespVo userRespVo = (UserRespVo) session.getAttribute("loginMember");

		if (userRespVo == null) {

			session.setAttribute("preUrl", "/project/confirm/order/" + num);

			return "redirect:/login";
		}

		String accessToken = userRespVo.getAccessToken();

		AppResponse<List<UserAddressVo>> resp = memberServiceFeign.address(accessToken);
		List<UserAddressVo> list = resp.getData();
		model.addAttribute("memberAddressList", list);

		// 2.数据展示 - 回报信息（和上一个页面共享session数据）
		ReturnPayConfirmVo returnPayConfirmVo = (ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVoSession");

		returnPayConfirmVo.setNum(num);
		returnPayConfirmVo
				.setTotalPrice(new BigDecimal(num * returnPayConfirmVo.getPrice() + returnPayConfirmVo.getFreight()));

		session.setAttribute("returnPayConfirmVoSession", returnPayConfirmVo);

		return "project/pay-step-2";
	}

	// 支持
	@RequestMapping("/project/support/{projectId}/{returnId}")
	public String support(@PathVariable("projectId") Integer projectId, @PathVariable("returnId") Integer returnId,
			Model model, HttpSession session) {

		AppResponse<ReturnPayConfirmVo> resp = projectServiceFeign.returnInfo(projectId, returnId);
		ReturnPayConfirmVo data = resp.getData();

		model.addAttribute("returnPayConfirmVo", data);
		session.setAttribute("returnPayConfirmVoSession", data);

		return "project/pay-step-1";
	}

	@RequestMapping("/project/projectInfo")
	public String index(Integer id, Model model) {

		AppResponse<ProjectDetailVo> resp = projectServiceFeign.detailsInfo(id);
		ProjectDetailVo vo = resp.getData();
		model.addAttribute("projectDetailVo", vo);

		return "project/index";
	}

//	@RequestMapping("/project/projectInfo")
//	public String index() {
//		return "project/index";
//	}

}

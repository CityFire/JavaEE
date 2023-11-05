package com.wjc.scw.webui.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.scw.webui.service.TMemberServiceFeign;
import com.wjc.scw.webui.service.TProjectServiceFeign;
import com.wjc.scw.webui.vo.resp.ProjectVo;
import com.wjc.scw.webui.vo.resp.UserRespVo;

@Controller
public class DispatcherController {

	@Autowired
	TMemberServiceFeign memberServiceFeign;

	@Autowired
	TProjectServiceFeign projectServiceFeign;

	@Autowired
	RedisTemplate redisTemplate;

	@RequestMapping("/logout")
	public String logout(HttpSession session) {

		if (session != null) {
			session.removeAttribute("loginMember");
			session.invalidate();
		}

		return "redirect:/index";
	}

	@RequestMapping("/doLogin")
	public String doLogin(String loginacct, String userpswd, HttpSession session) {
		AppResponse<UserRespVo> resp = memberServiceFeign.login(loginacct, userpswd);

		UserRespVo data = resp.getData();

		if (data == null) {
			return "login";
		}

		session.setAttribute("loginMember", data);

		String preUrl = (String) session.getAttribute("preUrl");
		if (StringUtils.isEmpty(preUrl)) {
			return "redirect:/index";
		} else {
			return "redirect:" + preUrl;
		}
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	// 如果Controller方法只是跳转页面，不做其他操作。可以配置mvc:view-controller标签。
	// <mvc:view-controller path="/index" view-name="index"/>
	@RequestMapping("/index")
	public String index(Model model) {

		List<ProjectVo> data = (List<ProjectVo>)redisTemplate.opsForValue().get("projectInfo");

		if (data == null) {
			AppResponse<List<ProjectVo>> resp = projectServiceFeign.all();
			data = resp.getData();
			redisTemplate.opsForValue().set("projectInfo", data, 1, TimeUnit.HOURS);
		}

		model.addAttribute("projectVoList", data);

		return "index";
	}

//	@RequestMapping("/index")
//	public String index(Model model) {
//		
//		model.addAttribute("hello", "城市之光33333333333333");
//		
//		model.addAttribute("unames", Arrays.asList("zhangsan","lisi","wangwu"));
//		
//		return "index";
//	}

}

package com.wjc.scw.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.wjc.scw.user.bean.TMember;
import com.wjc.scw.user.bean.TMemberAddress;
import com.wjc.scw.user.service.TMemberService;
import com.wjc.scw.user.vo.resp.UserAddressAddVo;
import com.wjc.scw.user.vo.resp.UserAddressDeleteVo;
import com.wjc.scw.user.vo.resp.UserAddressUpdateVo;
import com.wjc.scw.user.vo.resp.UserAddressVo;
import com.wjc.scw.user.vo.resp.UserDetailVo;
import com.wjc.scw.user.vo.resp.UserProjectVo;
import com.wjc.scw.user.vo.resp.UserUpdateVo;
import com.wjc.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户个人信息模块")
@RequestMapping("/user/info")
@RestController
public class TMemberController {

	@Autowired
	TMemberService memberService;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@ApiOperation(value = "获取用户收货地址")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "访问令牌", name = "accessToken", required = true) })
	@GetMapping("/address")
	public AppResponse<List<UserAddressVo>> address(String accessToken) {

		String memberId = stringRedisTemplate.opsForValue().get(accessToken);

		if (StringUtils.isEmpty(memberId)) {
			return AppResponse.fail(null);
		}

		List<TMemberAddress> addressList = memberService.listAddress(Integer.parseInt(memberId));

		List<UserAddressVo> addressVoList = new ArrayList<UserAddressVo>();

		for (TMemberAddress tMemberAddress : addressList) {
			UserAddressVo vo = new UserAddressVo();
			vo.setId(tMemberAddress.getId());
			vo.setAddress(tMemberAddress.getAddress());
			addressVoList.add(vo);
		}

		return AppResponse.ok(addressVoList);
	}

	@ApiOperation(value = "查询会员信息")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "会员ID", name = "id", required = true) })
	@GetMapping("/getMebmerById")
	public AppResponse<TMember> getMebmerById(@RequestParam("id") Integer id) {
		TMember member = memberService.getMebmerById(id);
		return AppResponse.ok(member);
	}

	@ApiOperation(value = "获取个人信息")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "访问令牌", name = "accessToken", required = true) })
	@GetMapping("/")
	public AppResponse<UserDetailVo> info(String accessToken) {
		UserDetailVo detailVo = new UserDetailVo();

		return AppResponse.ok(detailVo);
	}

	@ApiOperation(value = "更新个人信息")
	@PostMapping("/")
	public AppResponse<Object> updateInfo(UserUpdateVo updateVo) {
		return AppResponse.ok(null);
	}

	@ApiOperation(value = "获取我支持的项目")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "访问令牌", name = "accessToken", required = true) })
	@GetMapping("/support/project")
	public AppResponse<List<UserProjectVo>> support(String accessToken) {
		UserProjectVo vo = new UserProjectVo();
		vo.setId(789);
		vo.setName("BAVOSN便携折叠移动电源台灯");
		UserProjectVo vo2 = new UserProjectVo();
		List<UserProjectVo> list = new ArrayList<>();
		list.add(vo2);
		list.add(vo);

		return AppResponse.ok(list);
	}

	@ApiOperation(value = "获取我关注的项目")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "访问令牌", name = "accessToken", required = true) })
	@GetMapping("/star/project")
	public AppResponse<List<UserProjectVo>> star(String accessToken) {
		UserProjectVo vo = new UserProjectVo();
		vo.setId(789);
		vo.setName("BAVOSN便携折叠移动电源台灯");
		List<UserProjectVo> list = new ArrayList<>();
		list.add(vo);
		return AppResponse.ok(list);
	}

	@ApiOperation(value = "获取我发起的项目")
	@ApiImplicitParams(value = { @ApiImplicitParam(value = "访问令牌", name = "accessToken", required = true) })
	@GetMapping("/create/project")
	public AppResponse<List<UserProjectVo>> create(String accessToken) {

		UserProjectVo vo = new UserProjectVo();
		vo.setId(789);
		vo.setName("BAVOSN便携折叠移动电源台灯");
		List<UserProjectVo> list = new ArrayList<>();
		list.add(vo);

		return AppResponse.ok(list);
	}

	@ApiOperation(value = "新增用户收货地址")
	@PostMapping("/address")
	public AppResponse<Object> addAddress(UserAddressAddVo addressAddVo) {
		return AppResponse.ok("ok");
	}

	@ApiOperation(value = "修改用户收货地址")
	@PutMapping("/address")
	public AppResponse<Object> updateAddress(UserAddressUpdateVo updateVo) {
		return AppResponse.ok("ok");
	}

	@ApiOperation(value = "删除用户收货地址")
	@DeleteMapping("/address")
	public AppResponse<Object> deleteAddress(UserAddressDeleteVo vo) {
		return AppResponse.ok("ok");
	}

	@ApiOperation(value = "查看我的订单")
	@GetMapping("/order")
	public AppResponse<Object> order() {
		return AppResponse.ok("ok");
	}

	@ApiOperation(value = "删除我的订单")
	@DeleteMapping("/order")
	public AppResponse<Object> deleteOrder() {
		return AppResponse.ok("ok");
	}

	@ApiOperation(value = "获取我的系统消息")
	@GetMapping("/message")
	public AppResponse<Object> message() {
		return AppResponse.ok("ok");
	}
}

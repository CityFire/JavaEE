package com.wjc.scw.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.wjc.scw.project.bean.TReturn;
import com.wjc.scw.project.component.OssTemplate;
import com.wjc.scw.project.constant.ProjectConstant;
import com.wjc.scw.project.service.TProjectService;
import com.wjc.scw.project.vo.req.BaseVo;
import com.wjc.scw.project.vo.req.ProjectBaseInfoVo;
import com.wjc.scw.project.vo.req.ProjectRedisStorageVo;
import com.wjc.scw.project.vo.req.ProjectReturnVo;
import com.wjc.scw.vo.resp.AppResponse;
import com.wjc.user.enums.ProjectStatusEnume;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "项目发起模块")
@RequestMapping("/project/create")
@RestController
public class ProjectCreateController {

	@Autowired
	OssTemplate ossTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	TProjectService projectService;

	@ApiOperation(value = "1-项目初始创建")
	@PostMapping("/init")
	public AppResponse<Object> init(BaseVo vo) {

		try {
			// 1.验证用户是否登录
			String accessToken = vo.getAccessToken();

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供assessToken值");
				return resp;
			}

			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请先登录系统,再发布项目！");
				return resp;
			}

			// 2.初始化 bigVO
			ProjectRedisStorageVo bigVO = new ProjectRedisStorageVo();

			BeanUtils.copyProperties(vo, bigVO);

			String projectToken = UUID.randomUUID().toString().replaceAll("-", "");
			bigVO.setProjectToken(projectToken);

			// 3.数据存储：将 bigVO数据存储到redis中
			String bigStr = JSON.toJSONString(bigVO); // fastjson组件

			stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken, bigStr);

			log.debug("大VO数据：{}", bigVO);

			return AppResponse.ok(bigVO); // jackson组件
		} catch (BeansException e) {
			e.printStackTrace();
			return AppResponse.fail(null);
		}
	}

	@ApiOperation(value = "2-项目基本信息")
	@PostMapping("/baseinfo")
	public AppResponse<Object> baseinfo(ProjectBaseInfoVo vo) {

		try {
			// 1.验证用户是否登录
			String accessToken = vo.getAccessToken();

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供assessToken值");
				return resp;
			}

			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请先登录系统,再发布项目！");
				return resp;
			}

			// 2.从Redis中获取 bigVO数据，将小vo封装到大vo中
			String bigStr = stringRedisTemplate.opsForValue()
					.get(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken());
			ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr, ProjectRedisStorageVo.class);

			BeanUtils.copyProperties(vo, bigVo);

			bigStr = JSON.toJSONString(bigVo);

			stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken(), bigStr);

			log.debug("大VO数据：{}", bigVo);

			return AppResponse.ok(bigVo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("表单处理失败", e.getMessage());
			return AppResponse.fail(null);
		}

	}

	@ApiOperation(value = "3-添加项目回报档位")
	@PostMapping("/return")
	public AppResponse<Object> returnDetail(@RequestBody List<ProjectReturnVo> pro) {

		try {
			// 1.验证用户是否登录
			String accessToken = pro.get(0).getAccessToken();

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供assessToken值");
				return resp;
			}

			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请先登录系统,再发布项目！");
				return resp;
			}

			// 2.从Redis中获取 bigVO数据，将小vo封装到大vo中
			String bigStr = stringRedisTemplate.opsForValue()
					.get(ProjectConstant.TEMP_PROJECT_PREFIX + pro.get(0).getProjectToken());
			ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr, ProjectRedisStorageVo.class);

			List<TReturn> projectReturns = bigVo.getProjectReturns();
			for (ProjectReturnVo projectReturnVo : pro) {
				TReturn returnObj = new TReturn();
				BeanUtils.copyProperties(projectReturnVo, returnObj);
				projectReturns.add(returnObj);
			}

			bigStr = JSON.toJSONString(bigVo);

			stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + pro.get(0).getProjectToken(),
					bigStr);

			log.debug("大VO数据：{}", bigVo);

			return AppResponse.ok(bigVo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("表单处理失败", e.getMessage());
			return AppResponse.fail(null);
		}
	}

	/**
	 * 文件上传表单要求： ①method="post" ②enctype="multipart/form-data" ③type="file"
	 * 
	 * SpringMVC框架集成commons-fileupload和commons-io组件，完成文件上传操作。 SpringMVC提供文件上传解析器。
	 * Controller处理文件上传时，通过MultipartFile接受文件。
	 */
	@ApiOperation(value = "上传图片")
	@PostMapping("/upload")
	public AppResponse<Object> upload(@RequestParam("uploadfile") MultipartFile[] files) {

		try {
			List<String> filepathList = new ArrayList<String>();

			for (MultipartFile multipartFile : files) {
				String filename = multipartFile.getOriginalFilename();

				filename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + filename;

				String filepath = ossTemplate.upload(filename, multipartFile.getInputStream());
				filepathList.add(filepath);
			}

			log.debug("上传文件路径={}", filepathList);

			return AppResponse.ok(filepathList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传文件出现异常");
			return AppResponse.fail(null);
		}
	}

	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "accessToken", value = "用户令牌", required = true),
		@ApiImplicitParam(name = "projectToken", value = "项目标识", required = true),
		@ApiImplicitParam(name = "ops", value = "用户操作类型   0-保存草稿   1-提交审核", required = true) 
	})
	@ApiOperation(value = "4-项目提交审核申请")
	@PostMapping("/submit")
	public AppResponse<Object> submit(String accessToken, String projectToken, String ops) {

		try {
			// 1.验证用户是否登录

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供assessToken值");
				return resp;
			}

			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请先登录系统,再发布项目！");
				return resp;
			}

			if ("0".equals(ops)) { // 保存草稿

				projectService.saveProject(accessToken, projectToken, ProjectStatusEnume.DRAFT.getCode());

				return AppResponse.ok("ok");
			} else if ("1".equals(ops)) { // 保存提交审核

				projectService.saveProject(accessToken, projectToken, ProjectStatusEnume.SUBMIT_AUTH.getCode());

				return AppResponse.ok("ok");
			} else {
				log.error("请求方式不支持");
				return AppResponse.fail("请求方式不支持");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("项目操作失败-{}", e.getMessage());
			return AppResponse.fail(null);
		}
	}

//	@ApiOperation(value = "删除项目回报档位")
//	@DeleteMapping("/return")
//	public AppResponse<Object> deleteReturnDetail() {
//		return AppResponse.ok("ok");
//	}

//	@ApiOperation(value = "4-确认项目法人信息")
//	@PostMapping("/confirm/legal")
//	public AppResponse<Object> legal() {
//		return AppResponse.ok("ok");
//	}

//	@ApiOperation(value = "项目草稿保存")
//	@PostMapping("/tempsave")
//	public AppResponse<Object> tempsave() {
//		return AppResponse.ok("ok");
//	}
}

package com.wjc.scw.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wjc.scw.project.bean.TProject;
import com.wjc.scw.project.bean.TProjectImages;
import com.wjc.scw.project.bean.TProjectImagesExample;
import com.wjc.scw.project.bean.TReturn;
import com.wjc.scw.project.bean.TReturnExample;
import com.wjc.scw.project.bean.TTag;
import com.wjc.scw.project.bean.TType;
import com.wjc.scw.project.mapper.TProjectImagesMapper;
import com.wjc.scw.project.mapper.TProjectMapper;
import com.wjc.scw.project.mapper.TReturnMapper;
import com.wjc.scw.project.mapper.TTagMapper;
import com.wjc.scw.project.mapper.TTypeMapper;
import com.wjc.scw.project.service.ProjectInfoService;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
	@Autowired
	TTypeMapper typeMapper;
	
	@Autowired
	TTagMapper tagMapper;
	@Autowired
	TProjectMapper projectMapper;
	
	@Autowired
	TProjectImagesMapper imageMapper; 

	@Autowired
	TReturnMapper returnMapper;
	@Override
	public List<TType> getProjectTypes() {
		return typeMapper.selectByExample(null);
	}

	@Override
	public List<TTag> getAllProjectTags() {
		return tagMapper.selectByExample(null);
	}

	@Override
	public TProject getProjectInfo(Integer projectId) {
		TProject project = projectMapper.selectByPrimaryKey(projectId);
		return project;
	}

	@Override
	public List<TReturn> getProjectReturns(Integer projectId) {
		TReturnExample example = new TReturnExample();
		example.createCriteria().andProjectidEqualTo(projectId);
		return returnMapper.selectByExample(example);
	}

	@Override
	public List<TProject> getAllProjects() {
		return projectMapper.selectByExample(null);
	}

	@Override
	public List<TProjectImages> getProjectImages(Integer id) {
		TProjectImagesExample example = new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(id);
		return imageMapper.selectByExample(example);
	}

	@Override
	public TReturn getProjectReturnById(Integer retId) {
		return returnMapper.selectByPrimaryKey(retId);
	}

}

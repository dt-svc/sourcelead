package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;

@Service
public class ProjectApproverHierarchyServiceImpl implements ProjectApproverHierarchyService {

	@Autowired
	private  ProjectApproverHierarchyDao  projectApproverHierarchyDao;

	
	@Override
	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)
			throws Exception {
		return projectApproverHierarchyDao.saveProjectApproverHierarchy(projectApproverHierarchy);
	}
	
	

	@Override
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy)
			throws Exception {
		return projectApproverHierarchyDao.updateProjectApproverHierarchy(projectApproverHierarchy);
	}

	
	@Override
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception {
		return projectApproverHierarchyDao.getLevelOneProjectApproverByProjectId(ProjectId);
	}


	@Override
	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception {
		return projectApproverHierarchyDao.getApproverUsersByProjectId(Id);
		
	}


	@Override
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId) {
		// TODO Auto-generated method stub
		return projectApproverHierarchyDao.getProjectHierarchyListByLoginUserId(userId);
	}

	@Override
	public List<Long> getProjectIdsByLoginUserId(Long userId) {
		// TODO Auto-generated method stub
		return projectApproverHierarchyDao.getProjectIdsByLoginUserId(userId);
	}

	
	

}

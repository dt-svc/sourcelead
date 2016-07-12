package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
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
	public Collection<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception {
		return projectApproverHierarchyDao.getApproverUsersByProjectId(Id);
		
	}

	

}

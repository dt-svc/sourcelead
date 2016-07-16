package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;

public interface ProjectApproverHierarchyService {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception;

	public Collection<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;
	
	public Collection<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId);

}

package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;

public interface ProjectApproverHierarchyDao {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getByProjectApproverHierarchyId(Long projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception;
	
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;

	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;
	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectId(long Id,long userId) throws Exception;
	
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId);
	
	public List<Long> getProjectIdsByLoginUserId(Long userId);
	
}

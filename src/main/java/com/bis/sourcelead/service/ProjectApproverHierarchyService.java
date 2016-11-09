package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;

public interface ProjectApproverHierarchyService {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getByProjectApproverHierarchyId(Long projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception;

	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;
	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectId(long projectId,long userId) throws Exception;
	
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId);
	
	public List<Long> getProjectIdsByLoginUserId(Long userId);
	
	Set<ProjectApproverHierarchy> prepareProjectApproverHierarchyListByJson(JSONObject jsonObj,HttpServletRequest request,Projects projects) throws Exception;
	

}

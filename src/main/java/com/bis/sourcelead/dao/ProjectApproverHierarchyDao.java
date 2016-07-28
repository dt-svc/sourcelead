package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;

public interface ProjectApproverHierarchyDao {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception;
	
	@Cacheable(cacheNames={"projectApproverHierarchy"})
	public Collection<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;
	
	public Collection<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId);
	
	public List<Long> getProjectIdsByLoginUserId(Long userId);
	

}

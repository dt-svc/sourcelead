package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;

public interface ProjectApproverHierarchyDao {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public Collection<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;

}

package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementApproverHierarchy;

public interface RequirementApproverHierarchyDao {

	
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyList(Long requirementId , Long approverUserId) throws Exception;
	
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementId(Long requirementId,Long approverUser) throws Exception;
	
	public RequirementApproverHierarchy storeRequirementApproverHierarchy(RequirementApproverHierarchy requirementApproverHierarchy) throws Exception;
	
	public Integer getRequirementApproverHierarachyWithHighestLevel(Long requirementId) throws Exception;
	
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyListByRequirementId(Long requirementId) throws Exception;

	
}


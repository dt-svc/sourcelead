package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface RequirementApproverHierarchyDao {

	
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyList(Long requirementId , Long approverUserId) throws Exception;
	
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementId(Long requirementId,Long approverUser) throws Exception;
	
	public RequirementApproverHierarchy storeRequirementApproverHierarchy(RequirementApproverHierarchy requirementApproverHierarchy) throws Exception;
	
	public Integer getRequirementApproverHierarachyWithHighestLevel(Long requirementId) throws Exception;
	
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyListByRequirementId(Long requirementId) throws Exception;

	Boolean deleteTheRequirementApproverByRequirementIdAndApproverId(RequirementApproverHierarchy requirementApproverHierarchy) throws Exception;
	
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(
			Long requirementId) throws Exception;
	
	public List<RequirementApproverHierarchy> getRequirementsAsApprover(Long userId,Long orgLocationId) throws Exception;

	public RequirementApproverHierarchy getApproverHierarchyByRequirementId(Long requirementId);
	
	public RequirementApproverHierarchy getRejectedApproverHierarchyByRequirementId(Long requirementId);
	
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(Long requirementApproverHierarchyId,Long requirementId) throws Exception;
	
	public Boolean getisRequirementApprover(Long userId,Long requirementId);

	
}


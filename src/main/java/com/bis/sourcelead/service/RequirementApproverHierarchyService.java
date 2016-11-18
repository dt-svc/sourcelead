package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;

public interface RequirementApproverHierarchyService {

	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyList(Long requirementId , Long approverUserId) throws Exception;
	
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementId(Long requirementId,Long approverUserId) throws Exception;
	
	public RequirementApproverHierarchy addRequirementApproverHierarchy(RequirementApproverHierarchy requirementApproverHierarchy) throws Exception;
	
	public Integer getRequirementApproverHierarachyWithHighestLevel(Long requirementId) throws Exception;
	
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyListByRequirementId(Long requirementId) throws Exception;

	Boolean deleteTheRequirementApproverByRequirementIdAndApproverId(RequirementApproverHierarchy requirementApproverHierarchy) throws Exception;
	
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(Long requirementId) throws Exception;
}

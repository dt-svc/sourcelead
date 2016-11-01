package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;

public interface RequirementDao {

	
	public Requirement storeRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,Long  userId) throws Exception;
	
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)throws Exception;

	public List<RequirementAssignTo> getUserAssignJobCount(Long userId)throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId,Long  userId) throws Exception;
	
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,Long  userId) throws Exception;
}
package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.User;

public interface RequirementDao {

	
	public Requirement storeRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,Long  userId) throws Exception;
	
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)throws Exception;
	
	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception;

	public List<RequirementAssignTo> getUserAssignJobCount(Long userId)throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,Long  userId) throws Exception;

	public List<Requirement> getRequirementListById(Long requirementId);
	
	public Requirement checkRequirementIsReleasedOrNot(Long requirementId,Long orgLocationId) throws Exception;
	
	
	Integer validateJobId(Long id,Long orgLocationId) throws Exception;

	public Boolean validateRequirementTitle(String jobName, Long orgLocationId);

	List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user, OrgLocations orgLocations) throws Exception;
	
	Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId,Long userId)  throws Exception;
	
}
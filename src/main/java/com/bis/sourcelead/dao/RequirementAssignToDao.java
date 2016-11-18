package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementAssignTo;

public interface RequirementAssignToDao {

	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId)throws Exception;
	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId,Long teamId)  throws Exception;
	
	
	public boolean deleteRequirementAssignToByRequirementId(Long requirementId)throws Exception;
	
	public Integer getAssignedTeamMembersCountByTeamMemberUserId(Long assignUserId,String jobType);
	
	public RequirementAssignTo getRequirementAssignToByRequirementId(Long requirementId)  throws Exception;

	Boolean checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long requirementId,Long userId)  throws Exception;
	
}

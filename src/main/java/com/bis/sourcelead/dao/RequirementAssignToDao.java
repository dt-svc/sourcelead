package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;
import com.bis.sourcelead.dto.RequirementAssignToDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.RequirementAssignTo;

public interface RequirementAssignToDao {

	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId)throws Exception;
	
	public ResponseList<RequirementAssignTo> loadRequirementAssignToListByRequirementId(RequirementAssignToDTO requirementAssignToDTO)throws Exception;
	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId,Long teamId)  throws Exception;
	
	public boolean deleteRequirementAssignToByRequirementId(Long requirementId)throws Exception;
	
	public Integer getAssignedTeamMembersCountByTeamMemberUserId(Long assignUserId,String jobType);
	
	public RequirementAssignTo getRequirementAssignToByRequirementId(Long requirementId)  throws Exception;

	Boolean checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long requirementId,Long userId)  throws Exception;

	public List<RequirementAssignTo> getAssignedToMembersByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocationId);

	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignedUserId(Long assignedUserId,Long requirementId)throws Exception;

	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationId(Long orgLocationId);

	public List<RequirementAssignTo> getAllAssignedRequirementByDateRange(Long orgLocationId, String userId,Date startDateUTC, Date endDateUTC);
	
	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationIdAndTeamId(Long orgLocationId,Long teamId);
	
	/*
	 * this method for job campaigns
	 */
	public List<RequirementAssignTo> getAllAssignRequirementByUserIdAndOrglocationId(String userId,Long orgLocationId) throws Exception;

	Integer getRequirementAssignToListCountByRequirementId(Long requirementId)throws Exception; 
	
	List<RequirementAssignTo> updateRequirementAssignToListByRequirementId(List<RequirementAssignTo> requirementAssignToList) throws Exception;
	
}

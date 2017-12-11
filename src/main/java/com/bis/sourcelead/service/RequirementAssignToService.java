package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.dto.RequirementAssignToDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;

public interface RequirementAssignToService {

	public List<RequirementAssignTo> getRequirementAssignToListByRequirementId(Long requirementId)throws Exception; 
	
	public ResponseList<RequirementAssignTo> loadRequirementAssignToListByRequirementId(RequirementAssignToDTO requirementAssignToDTO)throws Exception;
	
	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignToId(Long requirementId,Long assignToId)  throws Exception;
	
	public boolean deleteRequirementAssignToByRequirementId(Long requirementId)throws Exception;
	
	public Integer getAssignedTeamMembersCountByTeamMemberUserId(Long assignUserId, String jobType);
	
	public RequirementAssignTo getRequirementAssignToByRequirementId(Long requirementId)  throws Exception;
	
	Boolean checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long requirementId,Long userId)  throws Exception;

	public List<RequirementAssignTo> getAssignedToMembersByRequirementIdAndOrgLocationId(Long requirementId,Long orgLocation) throws Exception;

	public RequirementAssignTo getRequirementAssignToByRequirementIdandAssignedUserId(Long assignedUserId, Long requirementId) throws Exception;

	List<AssignedRequirementReports>  getAllAssignedRequirementByOrglocationId(Long orgLocationId, Timezone timezone) throws Exception;

	public List<RequirementAssignTo> getAllAssignedRequirementByDateRange(Long orgLocationId, String userId, Date startDateUTC,Date endDateUTC)throws Exception;
	
	public List<RequirementAssignTo> getAllAssignedRequirementByOrglocationIdAndTeamId(Long orgLocationId,Long teamId);
	
	public List<RequirementAssignTo> getAllAssignRequirementByUserIdAndOrglocationId(String userId,Long orgLocationId) throws Exception;

	Integer getRequirementAssignToListCountByRequirementId(Long requirementId)throws Exception; 
	
	List<RequirementAssignTo> updateRequirementAssignToListByRequirementId(String assignedIdVal, String requirementId, OrgLocations orgLocations, User user)throws Exception;


}

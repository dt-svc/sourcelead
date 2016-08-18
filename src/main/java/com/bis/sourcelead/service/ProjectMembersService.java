package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;

public interface ProjectMembersService {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception;
	
	public ProjectMembers getProjectMemberById(Long id) throws Exception;
	
	public Boolean deleteProjectMemberById(Long id) throws Exception;
	
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception;
	
	public Collection<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByemployeeId(Long employeeId) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;

	public List<ProjectMembers> getAllProjectsByUserId(Long userId);
	
	public Integer  getAllProjectMembersCountByProjectId(Long id) throws Exception;
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);

	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String mEMBER_ACTIVE);

	public Integer updateEndDateOfProjectMemberId(Date endDate1, Long projectMemberId);
	
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) ;
	
	public String storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(Long projectId,Long employeeId,String startDate,String endDate,String mailtoMembers,String loginOrgCode,User user,OrgLocations loginOrgLocation) throws Exception ;
}

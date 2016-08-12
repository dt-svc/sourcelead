package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.service.ProjectMembersService;

public interface ProjectMembersDao {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public ProjectMembers getProjectMemberById(Long id) throws Exception;
	
	public Boolean deleteProjectMemberById(Long id) throws Exception;
	
	Collection<ProjectMembers> getAllProjectMembers() throws Exception;
	
	Collection<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception;
	
	List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception;
	
	List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;
	
    List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;

	public List<ProjectMembers> getAllProjectsByUserId(Long userId);

	public Integer getAllProjectMembersCountByProjectId(Long id);
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);

	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status);

	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId);
	
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) ;
}

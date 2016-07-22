package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.ProjectMembers;

public interface ProjectMembersService {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public ProjectMembers getProjectMemberById(Long id) throws Exception;
	
	public Boolean deleteProjectMemberById(Long id) throws Exception;
	
	Collection<ProjectMembers> getAllProjectMembers() throws Exception;
	
	Collection<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception;
	
	List<Long> getAllProjectMembersIdsByemployeeId(Long employeeId) throws Exception;
		
    List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;

	public List<ProjectMembers> getAllProjectsByUserId(Long userId);
	
	public Integer  getAllProjectMembersCountByProjectId(Long id) throws Exception;
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);
	
	
}

package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.ProjectMembers;

public interface ProjectMembersService {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public ProjectMembers getProjectMemberById(Long id) throws Exception;
	
	public Boolean deleteProjectMemberById(Long id) throws Exception;
	
	Collection<ProjectMembers> getAllProjectMembers() throws Exception;
}

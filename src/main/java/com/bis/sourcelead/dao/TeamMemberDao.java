package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.TeamMembers;

public interface TeamMemberDao {

	public List<TeamMembers>  getTeamMembersListByTeamID(Long teamId) throws Exception;
	
	public TeamMembers getTeamMemberByIdAndLocationId(Long teamMmeberId,Long orgLocationId) throws Exception;
	
	public TeamMembers saveOrUpdateTeamMembers(TeamMembers teamMembers) throws Exception;

}

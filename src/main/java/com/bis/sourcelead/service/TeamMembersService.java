package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.TeamMembers;

/**
 * @author Venkat Kesav
 *
 */
public interface TeamMembersService {
 
	public List<TeamMembers>  getTeamMembersListByTeamID(Long teamId) throws Exception;
	
	public TeamMembers getTeamMemberByIdAndLocationId(Long teamMmeberId,Long orgLocationId) throws Exception;
	
	public TeamMembers saveOrUpdateTeamMembers(TeamMembers teamMembers) throws Exception;
}

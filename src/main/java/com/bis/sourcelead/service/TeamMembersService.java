package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.TeamDTO;
import com.bis.sourcelead.entity.TeamMembers;

/**
 * @author Venkat Kesav
 *
 */
public interface TeamMembersService {
 
	public List<TeamMembers>  getTeamMembersListByTeamID(Long teamId, Long orgLocationId) throws Exception;
	
	public TeamMembers getTeamMemberByIdAndLocationId(Long teamMmeberId,Long orgLocationId) throws Exception;
	
	public TeamMembers saveOrUpdateTeamMembers(TeamMembers teamMembers) throws Exception;
	
	public void saveTeamMembersByJsonData(TeamDTO teamDTO,Long teamId) throws Exception;
	
	public TeamMembers deleteTeamMemberById(Long teamId) throws Exception;
	
	public List<TeamMembers>  getTeamMembersListByLocationId(Long orgLocationId) throws Exception;
	
	public List<TeamMembers>  getTeamMembersListByUserIdAndLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public Boolean isUserBelongsCurrentTeamByUserIdAndTeamId(Long userId,Long teamId) throws Exception;
	
	public TeamMembers getTeamMemberByMemberUserIdAndLocationId(Long memberUserId,Long orgLocationId) throws Exception;
}

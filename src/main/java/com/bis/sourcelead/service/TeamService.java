package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;

/**
 * @author sarath kumar
 *
 */
public interface TeamService {
	
	public List<Team> getTeamListByOrgLocationID(Long orgLocationId) throws Exception;
	
	public Team storeTeam(JSONObject jsonObj,OrgLocations orgLocations)throws Exception;
	
	public Team getTeamByTeamId(Long teamId)throws Exception;
	
	public Team deleteTeamById(Long teamId) throws Exception;
	
	public Integer getTeamCountByTeamOwner(Long userId,Long orgLocationId) throws Exception;
	
	public List<Team> getTeamListByTeamOwner(Long userId,Long orgLocationId) throws Exception;
	
	public Team saveTeam(Team team)throws Exception;
	
	public List<Team> getUserAssignedTeamListByOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
}

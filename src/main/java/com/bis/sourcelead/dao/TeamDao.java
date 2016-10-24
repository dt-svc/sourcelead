package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;

public interface TeamDao {

	public List<Team>   getTeamListByOrgLocationID(Long orgLocationId) throws Exception;
    
	public Team storeTeam(Team team)throws Exception;
	
	public Team getTeamByTeamId(Long teamId)throws Exception;

	public Boolean getTeamOwnersListByUserId(User user, Long orgLocationId);
	
	
}

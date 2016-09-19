package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Team;

public interface TeamDao {

	public List<Team>   getTeamListByOrgLocationID(Long orgLocationId) throws Exception;
    
	public Team storeTeam(Team team)throws Exception;
	
	public Team getTeamByTeamId(Long teamId)throws Exception;
	
	public Boolean deleteTeamById(Long id) throws Exception;
	
}

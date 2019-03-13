package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;

public interface TeamDao {

	public List<Team>   getTeamListByOrgLocationID(Long orgLocationId) throws Exception;
    
	public Team storeTeam(Team team)throws Exception;
	
	public Team getTeamByTeamId(Long teamId)throws Exception;

	public Boolean getTeamOwnersListByUserId(User user, Long orgLocationId);
	
	public Integer getTeamCountByTeamOwner(Long userId,Long orgLocationId) throws Exception;
	
	public List<Team> getTeamListByTeamOwner(Long userId,Long orgLocationId) throws Exception;
	
	public List<Team> getUserAssignedTeamListByOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public Boolean getTeamOwnersListByUserIdAndRequirementId(User user, Long orgLocationId,Long requirement);
	
    public List<Long> getTeamIdsListByOrgLocationID(Long orgLocationId) throws Exception;
	
	public List<Long> getUserAssignedTeamIdsListByOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public Team findByTeamName(String name,Long orgLocationId) throws Exception;
	
	public List<Team> getTeamListByTeamOwnerAndStatus(Long userId, Long orgLocationId) throws Exception;
	
	List<Team> getTeamListByMultipleIds(String[] teamsids,Long locationId) throws Exception;
}

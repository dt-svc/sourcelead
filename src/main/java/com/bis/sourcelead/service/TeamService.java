package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Team;

/**
 * @author sarath kumar
 *
 */
public interface TeamService {
	
	public List<Team> getTeamListByOrgLocationID(Long orgLocationId) throws Exception;
	
}

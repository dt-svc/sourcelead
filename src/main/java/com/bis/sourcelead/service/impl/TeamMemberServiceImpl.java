package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author sarath kumar
 *
*/

@Service
public class TeamMemberServiceImpl implements TeamMembersService {
	
	@Autowired
	TeamMemberDao teamMemberDao;
	
	@Autowired
	CommonUtil commonUtil;

	 /**
	   * @author sarath kumar
	   * @since  sep 14 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting TeamList By OrgLocationID
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	 */
	
	 
	@Override
	public List<TeamMembers> getTeamMembersListByTeamID(Long teamId) throws Exception {
		
		return teamMemberDao.getTeamMembersListByTeamID(teamId);
		
	}

	@Override
	public TeamMembers getTeamMemberByIdAndLocationId(Long teamMmeberId, Long orgLocationId) throws Exception {
		return teamMemberDao.getTeamMemberByIdAndLocationId(teamMmeberId, orgLocationId);
	}

	@Override
	public TeamMembers saveOrUpdateTeamMembers(TeamMembers teamMembers) throws Exception {
		
		return teamMemberDao.saveOrUpdateTeamMembers(teamMembers);
		
	}
	
	 
}
	 

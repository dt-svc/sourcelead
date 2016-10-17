package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author sarath kumar
 *
*/

@Service
public class TeamServiceImpl implements TeamService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceImpl.class);
	
	@Autowired
	TeamDao teamDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TeamMembersService teamMembersService;

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
	public List<Team> getTeamListByOrgLocationID(Long orgLocationId) throws Exception {
	   return teamDao.getTeamListByOrgLocationID(orgLocationId);
	}
    
	/**
	   * @author prasad K
	   * @since  sep 19 2016
	   * @version 0.0.1
	   * 
	   * This method is used to Store Team details into DB
	   * 
	   *  
	   * @return team 
	   * 
	   * @throws NullPointer
	 */
	
	@Override
	public Team storeTeam(JSONObject jsonObj,OrgLocations orgLocations) throws Exception {
		
		User user = new User();
		user.setId(Long.parseLong(jsonObj.getString("ownerUserId")));
		
		Team team = new Team();
		if(orgLocations != null){
			team.setOrgLocationsId(orgLocations);;
			}
		
		if(jsonObj.has("name")){
			team.setName(jsonObj.getString("name"));
			}
		if(jsonObj.has("ownerUserId")){
			team.setOwnerUserId(user);
		}
		team.setStatus(Constants.TEAM_ACTIVE);
		team.setCreatedDate(commonUtil.currentDate());
		team.setUpdatedDate(commonUtil.currentDate());
		
		teamDao.storeTeam(team);
		teamMembersService.saveTeamMembersByJsonData(jsonObj, team.getId());
		
		return team;
		
	}

	@Override
	public Team getTeamByTeamId(Long teamId) throws Exception {
		logger.debug("In TeamServiceImpl class getTeamByTeamId method...");
		return teamDao.getTeamByTeamId(teamId);
	}

	@Override
	public Team deleteTeamById(Long teamId) throws Exception {
		
		Team team = teamDao.getTeamByTeamId(teamId);
		team.setStatus(Constants.TEAM_IN_ACTIVE);
		return teamDao.storeTeam(team);
	}
	
}
	 

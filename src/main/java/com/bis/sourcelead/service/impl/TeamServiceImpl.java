package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.dto.TeamDTO;
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
	TeamMemberDao teamMemberDao;
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	UserService userService;
	@Autowired
	TeamMembersService teamMembersService;
	
	/**
	 * @author sarath kumar
	 * @since sep 14 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to for getting TeamList By OrgLocationID
	 * 
	 * 
	 * @return string
	 * 
	 * @throws NullPointer
	 */
	@Override
	public List<Team> getTeamListByOrgLocationID(Long orgLocationId) throws Exception
	{
		return teamDao.getTeamListByOrgLocationID(orgLocationId);
	}
	
	/**
	 * @author prasad K
	 * @since sep 19 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to Store Team details into DB
	 * 
	 * 
	 * @return team
	 * 
	 * @throws NullPointer
	 */
	@Override
	public Team storeTeam(TeamDTO teamDTO, OrgLocations orgLocations) throws Exception
	{
		Team team = null;
		if (StringUtils.isNotBlank(teamDTO.getTeamId())) {
			team = teamDao.getTeamByTeamId(Long.parseLong(teamDTO.getTeamId()));
		}
		else {
			team = new Team();
		}
		if (StringUtils.isNotBlank(teamDTO.getEditOwnerUserId())) {
			User user = userService.getUserById(Long.parseLong(teamDTO.getEditOwnerUserId()));
			team.setOwnerUserId(user);
		}
		if (orgLocations != null) {
			team.setOrgLocationsId(orgLocations);;
		}
		if (StringUtils.isNotBlank(teamDTO.getName())) {
			team.setName(teamDTO.getName());
		}
		// URLDecoder.decode(request.getParameter("text"), "UTF-8");
		if (StringUtils.isNotBlank(teamDTO.getOwnerUserId())) {
			User user = new User();
			user.setId(Long.parseLong(teamDTO.getOwnerUserId()));
			team.setOwnerUserId(user);
		}
		team.setStatus(Constants.TEAM_ACTIVE);
		team.setCreatedDate(commonUtil.currentDate());
		team.setUpdatedDate(commonUtil.currentDate());
		teamDao.storeTeam(team);
		/*
		 * // For saving the Team Lead as a Team member TeamMembers teamlead =
		 * new TeamMembers(); User teamUser = new User();
		 * teamUser.setId(team.getOwnerUserId().getId());
		 * teamlead.setMemberUserId(teamUser); teamlead.setTeamId(team);
		 * teamlead.setStatus(Constants.MEMBER_ACTIVE);
		 * teamlead.setCreatedDate(commonUtil.currentDate());
		 * teamlead.setUpdatedDate(commonUtil.currentDate());
		 * teamMemberDao.saveOrUpdateTeamMembers(teamlead);
		 */
		teamMembersService.saveTeamMembersByJsonData(teamDTO, team.getId());
		return team;
	}
	
	@Override
	public Team getTeamByTeamId(Long teamId) throws Exception
	{
		logger.debug("In TeamServiceImpl class getTeamByTeamId method...");
		return teamDao.getTeamByTeamId(teamId);
	}
	
	@Override
	public Team deleteTeamById(Long teamId) throws Exception
	{
		Team team = teamDao.getTeamByTeamId(teamId);
		team.setStatus(Constants.TEAM_IN_ACTIVE);
		return teamDao.storeTeam(team);
	}
	
	@Override
	public Integer getTeamCountByTeamOwner(Long userId, Long orgLocationId) throws Exception
	{
		return teamDao.getTeamCountByTeamOwner(userId, orgLocationId);
	}
	
	@Override
	public List<Team> getTeamListByTeamOwner(Long userId, Long orgLocationId) throws Exception
	{
		return teamDao.getTeamListByTeamOwner(userId, orgLocationId);
	}
	
	@Override
	public Team saveTeam(Team team) throws Exception
	{
		return teamDao.storeTeam(team);
	}
	
	@Override
	public List<Team> getUserAssignedTeamListByOrgLocationId(Long userId, Long orgLocationId) throws Exception
	{
		return teamDao.getUserAssignedTeamListByOrgLocationId(userId, orgLocationId);
	}
	
	@Override
	public List<Long> getTeamIdsListByOrgLocationID(Long orgLocationId) throws Exception
	{
		return teamDao.getTeamIdsListByOrgLocationID(orgLocationId);
	}
	
	@Override
	public List<Long> getUserAssignedTeamIdsListByOrgLocationId(Long userId, Long orgLocationId) throws Exception
	{
		return teamDao.getUserAssignedTeamIdsListByOrgLocationId(userId, orgLocationId);
	}
	
	@Override
	public Team findByTeamName(String name, Long orgLocationId) throws Exception
	{
		return teamDao.findByTeamName(name, orgLocationId);
	}

	@Override
	public List<Team> getTeamListByTeamOwnerAndStatus(Long userId, Long orgLocationId) throws Exception
	{
		return teamDao.getTeamListByTeamOwnerAndStatus(userId,orgLocationId);
	}

	@Override
	public List<Team> getTeamListByMultipleIds(String[] teamsids, Long locationId) throws Exception {
		return teamDao.getTeamListByMultipleIds(teamsids,locationId);
	}
}

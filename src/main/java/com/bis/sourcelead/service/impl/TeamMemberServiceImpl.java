package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TeamMemberDao;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
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
public class TeamMemberServiceImpl implements TeamMembersService {
	
	@Autowired
	TeamMemberDao teamMemberDao;
	
	@Autowired
	CommonUtil commonUtil;
    
	@Autowired
	UserService userService;
	
	@Autowired
	TeamService teamService;
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
	public List<TeamMembers> getTeamMembersListByTeamID(Long teamId,Long orgLocationId) throws Exception {
		
		return teamMemberDao.getTeamMembersListByTeamID(teamId,orgLocationId);
		
	}

	@Override
	public TeamMembers getTeamMemberByIdAndLocationId(Long teamMmeberId, Long orgLocationId) throws Exception {
		return teamMemberDao.getTeamMemberByIdAndLocationId(teamMmeberId, orgLocationId);
	}

	@Override
	public TeamMembers saveOrUpdateTeamMembers(TeamMembers teamMembers) throws Exception {
		
		teamMembers.setCreatedDate(commonUtil.currentDate());
		teamMembers.setUpdatedDate(commonUtil.currentDate());
		
		return teamMemberDao.saveOrUpdateTeamMembers(teamMembers);
		
	}

	@Override
	public void saveTeamMembersByJsonData(JSONObject jSONObject,Long teamId) throws Exception {
		
		
		String  userList = null;
		if(jSONObject.has("teamMemberUserId")){
				try{
		  		  JSONArray jsonArray = jSONObject.getJSONArray("teamMemberUserId");
		  		  userList = jsonArray.toString();
		  	     }
		  	     catch(JSONException e){
		  		     userList= jSONObject.getString("teamMemberUserId");
		  	     }
		}
		if(jSONObject.has("memberUserId")){
			 try{
		  		  JSONArray jsonArray = jSONObject.getJSONArray("memberUserId");
		  		  userList = jsonArray.toString();
		  	     }
		  	     catch(JSONException e){
		  		     userList= jSONObject.getString("memberUserId");
		  	     }
		}
		
		userList = userList.replaceAll("\\[", "").replaceAll("\\]","");
		userList = userList.replaceAll("\"", "");
		String[] usersIds = userList.split(",");
		
		
		
		for(int i=0; i < usersIds.length  ;i++){
			User user = new User();
			
			TeamMembers teamMembers = new TeamMembers();
			
			
			user.setId(Long.parseLong(usersIds[i]));
			teamMembers.setMemberUserId(user);
			
			Team team = new Team();
			team.setId(teamId);
			teamMembers.setTeamId(team);
			
			teamMembers.setStatus(Constants.MEMBER_ACTIVE);
			teamMembers.setCreatedDate(commonUtil.currentDate());
			teamMembers.setUpdatedDate(commonUtil.currentDate());
	        teamMemberDao.saveOrUpdateTeamMembers(teamMembers);
		}
	   
	
}

	@Override
	public TeamMembers deleteTeamMemberById(Long teamMemberId) throws Exception {
		
		TeamMembers teamMembers = teamMemberDao.getTeamMemberByTeamMemberId(teamMemberId);
		teamMembers.setStatus(Constants.TEAM_IN_ACTIVE);
		return teamMemberDao.saveOrUpdateTeamMembers(teamMembers);
		
	}

	@Override
	public List<TeamMembers> getTeamMembersListByLocationId(Long orgLocationId) throws Exception {
		
		
		List<TeamMembers> teamMembersList = teamMemberDao.getTeamMembersListByLocationId(orgLocationId);
		for(TeamMembers teamMembers :teamMembersList){
			
			teamMembers.setTeamName(teamMembers.getTeamId().getName());
			teamMembers.setOwnerName(teamMembers.getTeamId().getOwnerUserId().getFirstName() +""+teamMembers.getTeamId().getOwnerUserId().getLastName() );
			teamMembers.setMemberName(teamMembers.getMemberUserId().getFirstName()+""+teamMembers.getMemberUserId().getLastName());
			teamMembers.setEmail(teamMembers.getEmail());
		}
		return teamMembersList;
	}
	 
}


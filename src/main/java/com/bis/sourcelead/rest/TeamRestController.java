package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Requirement service.
 * 
 * @author Prasad K
 * @date 13th Sept 2016
 *
 */

@RestController
public class TeamRestController {

	private static final Logger logger = LoggerFactory.getLogger(TeamRestController.class);

	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
		
	
	
	 @Autowired
	 CommonUtil commonUtil;
	 

	 @Autowired
	 TeamService teamService;
	 
	 @Autowired
	 TeamMembersService teamMembersService;
	 
	 @Autowired
	 RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 UserService userService;
	 
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
	 
	  @RequestMapping(value = "/getTeamListByOrgLocationID")
	  public String getTeamListByOrgLocationID(HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Team> teamList = teamService.getTeamListByOrgLocationID(loginOrgLocation.getId());
		String teamMembers="";
		for(Team team : teamList){
			
			team.setTeamOwnerName(team.getOwnerUserId().getFirstName()+""+team.getOwnerUserId().getLastName());
			Collection<TeamMembers>	teamMembersList  = teamMembersService.getTeamMembersListByTeamID(team.getId());
			team.setTeamMembersCount(teamMembersList.size());
			
			 
		}
		resultJson = commonUtil.toJSON(teamList);
		return resultJson;
		
	 }
	  
	  
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
	 
	  @RequestMapping(value = "/getTeamMembersListByLocationId")
	  public String getTeamMembersListByLocationId(HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByLocationId(loginOrgLocation.getId());
		
		resultJson = commonUtil.toJSON(teamMembersList);
		return resultJson;
		
	 }
	  
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
	 
	  @RequestMapping(value = "/getTeamMembersListByTeamID")
	  public String getTeamMembersListByTeamID(@RequestParam(value="teamId", required=false) String  teamId,HttpServletRequest request) throws Exception {

		String resultJson;
		List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByTeamID(Long.parseLong(teamId));
		for(TeamMembers  teamMembers : teamMembersList){
			
			
 			 teamMembers.setTeamName(teamMembers.getTeamId().getName());
			 teamMembers.setOwnerName(teamMembers.getTeamId().getOwnerUserId().getFirstName() +""+teamMembers.getTeamId().getOwnerUserId().getLastName() );
			 teamMembers.setMemberName(teamMembers.getMemberUserId().getFirstName()+""+teamMembers.getMemberUserId().getLastName());
			 teamMembers.setEmail(teamMembers.getTeamId().getOwnerUserId().getEmail());
			 teamMembers.setUserType(teamMembers.getTeamId().getOwnerUserId().getUserType());
			 
			 teamMembers.setTeamMemberUserId(teamMembers.getMemberUserId().getId());
			
			 Integer teamMemberRequirementAssignedCount =  requirementAssignToService.getAssignedTeamMembersCountByTeamMemberUserId(teamMembers.getMemberUserId().getId(), "all");
			 teamMembers.setTeamMemberRequirementAssignedCount(teamMemberRequirementAssignedCount);
			
			 Integer teamMemberRequirementAssignedOpenCount =  requirementAssignToService.getAssignedTeamMembersCountByTeamMemberUserId(teamMembers.getMemberUserId().getId(), "open");
			 teamMembers.setTeamMemberRequirementAssignedOpenCount(teamMemberRequirementAssignedOpenCount);
		}
		
		resultJson = commonUtil.toJSON(teamMembersList);
		return resultJson;
	 } 
	  
	  /**
	   * @author prasad k
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
	 
	  @RequestMapping(value = "/getTeamMembersListExceptExistedTeamMembersList")
	  public String getTeamMembersListExceptExistedTeamMembersList(@RequestParam(value="existedTeamMembersIds", required=false) String  existedTeamMembersIds,@RequestParam(value="teamId", required=false) Long  teamId,HttpServletRequest request) throws Exception {
		  
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
		Team  team = teamService.getTeamByTeamId(teamId);	
		String resultJson;
		List<Long> ids = new ArrayList<Long>();
		if(team.getOwnerUserId() !=null){
			ids.add(team.getOwnerUserId().getId());
		}
		String[] existedTeamMemberUserIds = existedTeamMembersIds.split(",");
		for(String id  : existedTeamMemberUserIds){
			ids.add(Long.parseLong(id));
		}
		
		List<User> userList = userService.getUserListExceptExistedTeamMembersList(ids, loginOrgLocation.getId());
		resultJson = commonUtil.toJSON(userList);
		return resultJson;
	 } 
	  
	  /**
	  *  This Method is for update Candidate details
	  * @author David Raju
	  * @param json   This is the first parameter to updateCandidate method
	  * @param  request    This is the second parameter to updateCandidate method
	  * @return String
	  * @throws NullPointerException
	  */ 
	   @RequestMapping(value = "/deleteTeamMemberById")
	   public @ResponseBody  String deleteTeamMemberById(@RequestParam(value="teamMemberId", required=false) String  teamMemberId,@RequestParam(value="reason", required=false) String  reason, HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			TeamMembers teamMembers = teamMembersService.getTeamMemberByIdAndLocationId(Long.valueOf(teamMemberId),loginOrgLocation.getId());
			if(teamMembers != null){
				
					teamMembers.setStatus(Constants.TEAM_IN_ACTIVE);
					if(StringUtils.isNotEmpty(reason)){
						/*teamMembers.setTerminateReason(reason);*/
					}
				
					teamMembers.setUpdatedBy(user.getUserCode());
					teamMembers.setUpdatedDate(commonUtil.currentDate());
					teamMembersService.saveOrUpdateTeamMembers(teamMembers);
			}
			return "success";
	 }
	  
	   @RequestMapping(value = "/storeTeamMembers", method = RequestMethod.POST)
		public @ResponseBody String storeTeamMembers(@RequestParam(value = "json", required = false) String json,Long teamId,HttpServletRequest request) throws Exception {
			
			String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			teamMembersService.saveTeamMembersByJsonData(jsonObj,teamId);
			return "success";
		}
	   
	   /**
		 * @author prasad k
		 * @since  sep 19 2016
		 * @version 0.0.1
		 * 
		 * This method is used to store/save team in team table.
		 * 1.internalley store/save in teamMember in teamMember table .
		 * 
		 * @param jsonObj This is the first paramter to storeTeam method
		 * @param orgLocations This is the second paramter to storeTeam method
		 * 
		 * @return Team 
		 * 
		 * @throws NullPointer
		*/
	   
	   @RequestMapping(value = "/storeTeam", method = RequestMethod.POST)
		public @ResponseBody String storeTeam(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String json1 = "{" + json + "}";
			
			JSONObject jsonObj = new JSONObject(json1);
			teamService.storeTeam(jsonObj,orgLocations);
			
			return "success";
		}
	   
	   /**
		  * This method for delete team by id
		  * @param id
		  * @return
		  * @throws Exception
		  */
		 @RequestMapping(value = "/deleteTeamById", method = RequestMethod.POST,headers="Accept=application/json")
		 public String deleteTeamById(@RequestParam( value = "teamId", required = false) Long teamId) throws Exception { 
			 Team team = teamService.deleteTeamById(teamId);
			 return "success";

		  }
		 
		 
		 /**
		  * This method for delete team by id
		  * @param teamMemberId
		  * @return
		  * @throws Exception
		  */
		 @RequestMapping(value = "/deleteTeamMemberByCandidateId", method = RequestMethod.POST,headers="Accept=application/json")
		 public String deleteTeamMemberByCandidateId(@RequestParam( value = "teamMemberId", required = false) Long teamMemberId) throws Exception { 
			
			 TeamMembers  teamMembers = teamMembersService.deleteTeamMemberById(teamMemberId);
			 return "success";
		  }
		 
		 
		 /**
		   * @author David Raju
		   * @since  sep 14 2016
		   * @version 0.0.1
		   * 
		   * This method is used get All Users By OrgLocationCode ExceptTeamOwner 
		   * 
		   * @param teamOwnerUserId This is the first paramter to getAllUsersByOrgLocationCodeExceptTeamOwner
		   
		   * @return string 
		   * 
		   * @throws NullPointer
		   */
		 
		  @RequestMapping(value = "/getAllUsersByOrgLocationCodeExceptTeamOwner")
		  public String getAllUsersByOrgLocationCodeExceptTeamOwner(@RequestParam(value="teamOwnerUserId", required=false) Long  teamOwnerUserId,HttpServletRequest request) throws Exception {

			String resultJson;
			Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			List<User> freshUserList = userService.getAllUsersByOrgLocationCodeExceptTeamOwner(teamOwnerUserId, loginOrgLocation.getId());
			resultJson = commonUtil.toJSON(freshUserList);
			return resultJson;
		 } 

	 
}

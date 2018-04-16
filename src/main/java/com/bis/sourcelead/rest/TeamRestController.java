package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.TeamDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
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
@RequestMapping("/app")
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
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	CandidateService candidateService;
	
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
	@PreAuthorize("hasAnyRole('ROLE_VIEW_TEAM','ROLE_VIEW_EDIT_TEAM','ROLE_VIEW_DELETE_TEAM')")
	@RequestMapping(value = "/getTeamListByOrgLocationID")
	public String getTeamListByOrgLocationID(HttpServletRequest request) throws Exception
	{
		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Team> teamList = teamService.getTeamListByOrgLocationID(loginOrgLocation.getId());
		for (Team team : teamList) {
			StringBuilder ownerNameSB = new StringBuilder();
			if(team.getOwnerUserId().getLastName() != null && StringUtils.isNotBlank(team.getOwnerUserId().getLastName())) {
				team.setTeamOwnerName(ownerNameSB.append(team.getOwnerUserId().getFirstName()).append(" ").append(team.getOwnerUserId().getLastName()).toString());	
			}else {
				team.setTeamOwnerName(ownerNameSB.append(team.getOwnerUserId().getFirstName()).toString());
			}
			Collection<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByTeamID(team.getId(), loginOrgLocation.getId());
			team.setTeamMembersCount(teamMembersList.size());
			Integer favouriteCount = candidateService.getHotlistCandidatesCountByTeamIdAndOrgLocationId(team.getId(), loginOrgLocation.getId());
			team.setCandidateHotlistCount(favouriteCount);
		}
		resultJson = commonUtil.toJSON(teamList);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getOrgLocationTeamList")
	public List<Team> getOrgLocationTeamList(@RequestParam(value = "orgLocationId", required = true) Long orgLocationId,HttpServletRequest request) throws Exception
	{
		List<Team> teamList = teamService.getTeamListByOrgLocationID(orgLocationId);
		return teamList;
	}
	
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
	@RequestMapping(value = "/getTeamMembersListByLocationId")
	public String getTeamMembersListByLocationId(HttpServletRequest request) throws Exception
	{
		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByLocationId(loginOrgLocation.getId());
		resultJson = commonUtil.toJSON(teamMembersList);
		return resultJson;
	}
	
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
	@RequestMapping(value = "/getTeamMembersListByTeamID")
	public String getTeamMembersListByTeamID(@RequestParam(value = "teamId", required = false) String teamId, HttpServletRequest request) throws Exception
	{
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByTeamID(Long.parseLong(teamId), orgLocation.getId());
		for (TeamMembers teamMembers : teamMembersList) {
			StringBuilder ownerNameSB = new StringBuilder();
			StringBuilder memberNameSB = new StringBuilder();
			teamMembers.setTeamName(teamMembers.getTeamId().getName());
			if(teamMembers.getTeamId().getOwnerUserId().getLastName() != null && StringUtils.isNotEmpty(teamMembers.getTeamId().getOwnerUserId().getLastName())) {
				teamMembers.setOwnerName(ownerNameSB.append(teamMembers.getTeamId().getOwnerUserId().getFirstName()).append(" ").append(teamMembers.getTeamId().getOwnerUserId().getLastName()).toString());
			}else {
				teamMembers.setOwnerName(ownerNameSB.append(teamMembers.getTeamId().getOwnerUserId().getFirstName()).toString());
			}
			if(teamMembers.getMemberUserId().getLastName() != null && StringUtils.isNotEmpty(teamMembers.getMemberUserId().getLastName())) {
				teamMembers.setMemberName(memberNameSB.append(teamMembers.getMemberUserId().getFirstName()).append(" ").append(teamMembers.getMemberUserId().getLastName()).toString());
			}else {
				teamMembers.setMemberName(memberNameSB.append(teamMembers.getMemberUserId().getFirstName()).toString());
			}
			teamMembers.setEmail(teamMembers.getMemberUserId().getEmail());
			UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(teamMembers.getMemberUserId().getId(), orgLocation.getId());
			teamMembers.setUserType(userRole.getUserType());
			teamMembers.setTeamMemberUserId(teamMembers.getMemberUserId().getId());
			Integer teamMemberRequirementAssignedCount = requirementAssignToService.getAssignedTeamMembersCountByTeamMemberUserId(teamMembers.getMemberUserId().getId(), "all");
			teamMembers.setTeamMemberRequirementAssignedCount(teamMemberRequirementAssignedCount);
			Integer teamMemberRequirementAssignedOpenCount = requirementAssignToService.getAssignedTeamMembersCountByTeamMemberUserId(teamMembers.getMemberUserId().getId(), "open");
			teamMembers.setTeamMemberRequirementAssignedOpenCount(teamMemberRequirementAssignedOpenCount);
		}
		resultJson = commonUtil.toJSON(teamMembersList);
		return resultJson;
	}
	
	/**
	 * @author prasad k
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
	@RequestMapping(value = "/getTeamMembersListExceptExistedTeamMembersList")
	public String getTeamMembersListExceptExistedTeamMembersList(@RequestParam(value = "existedTeamMembersIds", required = false) String existedTeamMembersIds, @RequestParam(value = "teamId", required = false) Long teamId, HttpServletRequest request) throws Exception
	{
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Team team = teamService.getTeamByTeamId(teamId);
		String resultJson;
		List<Long> ids = new ArrayList<Long>();
		if (team.getOwnerUserId() != null) {
			ids.add(team.getOwnerUserId().getId());
		}
		String[] existedTeamMemberUserIds = existedTeamMembersIds.split(",");
		if (null != existedTeamMemberUserIds && existedTeamMemberUserIds.length > 0
				&& !existedTeamMembersIds.isEmpty()) {
			for (String id : existedTeamMemberUserIds) {
				ids.add(Long.parseLong(id));
			}
		}
		 List<User> newUserList = new ArrayList<>();
		List<User> userList = userService.getUserListExceptExistedTeamMembersList(ids, loginOrgLocation.getId());
		/**
		 * commented by Pavan Jampana this piece of code is commented to allow
		 * employees as team members.
		 */
		
		/* for(User user :userList){ System.out.println(user.getId()); UserRole
		  userRole =
		 userRoleService.getUserRoleByUserIdAndLocationId(user.getId(),
		 loginOrgLocation.getId());
		  if(!Constants.EMPLOYEE.equals(userRole.getUserType())){
		 newUserList.add(user); } }*/
		 
		resultJson = commonUtil.toJSON(userList);
		return resultJson;
	}
	
	/**
	 * This Method is for update Candidate details
	 * 
	 * @author David Raju
	 * @param json
	 *            This is the first parameter to updateCandidate method
	 * @param request
	 *            This is the second parameter to updateCandidate method
	 * @return String
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/deleteTeamMemberById")
	public @ResponseBody String deleteTeamMemberById(@RequestParam(value = "teamMemberId", required = false) String teamMemberId, @RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		TeamMembers teamMembers = teamMembersService.getTeamMemberByIdAndLocationId(Long.valueOf(teamMemberId), loginOrgLocation.getId());
		if (teamMembers != null) {
			teamMembers.setStatus(Constants.TEAM_IN_ACTIVE);
			if (StringUtils.isNotEmpty(reason)) {
				/* teamMembers.setTerminateReason(reason); */
			}
			teamMembers.setUpdatedBy(user.getUserCode());
			teamMembers.setUpdatedDate(commonUtil.currentDate());
			teamMembersService.saveOrUpdateTeamMembers(teamMembers);
		}
		return "success";
	}
	
	@RequestMapping(value = "/storeTeamMembers", method = RequestMethod.POST)
	public @ResponseBody String storeTeamMembers(TeamDTO teamDTO, Long teamId, HttpServletRequest request) throws Exception
	{
		teamMembersService.saveTeamMembersByJsonData(teamDTO, teamId);
		return "success";
	}
	
	/**
	 * @author prasad k
	 * @since sep 19 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save team in team table.
	 *          1.internalley store/save in teamMember in teamMember table .
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeTeam method
	 * @param orgLocations
	 *            This is the second paramter to storeTeam method
	 * 
	 * @return Team
	 * 
	 * @throws NullPointer
	 */
	@PreAuthorize("hasRole('ROLE_ADD_TEAM')")
	@RequestMapping(value = "/storeTeam", method = RequestMethod.POST)
	@ResponseBody
	public  Response<String> storeTeam(TeamDTO teamDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response=new Response<>();
		try{
			if(user != null){
				if(Validator.validate(teamDTO)){
					teamService.storeTeam(teamDTO, orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Team Created Successfully");
					
				}
				else{
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
			}
			
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return response;
	}
	
	/**
	 * This method for delete team by id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_VIEW_DELETE_TEAM')")
	@RequestMapping(value = "/deleteTeamById", method = RequestMethod.POST, headers = "Accept=application/json")
	public String deleteTeamById(@RequestParam(value = "teamId", required = false) Long teamId, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Candidate> hotListCandidatesList = candidateService.getHotCandidateListByTeamIdAndOrgLocationId(teamId, orgLocations.getId());
		if (null != hotListCandidatesList) {
			for (Candidate hotList : hotListCandidatesList) {
				hotList.setTeamId(null);
				candidateService.saveOrUpdateCandidate(hotList);
				candidateService.saveOrUpdateCandidateElkDocument(hotList);
			}
		}
		Team team = teamService.deleteTeamById(teamId);
		return "success";
	}
	
	/**
	 * This method for delete team by id
	 * 
	 * @param teamMemberId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTeamMemberByCandidateId", method = RequestMethod.POST, headers = "Accept=application/json")
	public String deleteTeamMemberByCandidateId(@RequestParam(value = "teamMemberId", required = false) Long teamMemberId) throws Exception
	{
		TeamMembers teamMembers = teamMembersService.deleteTeamMemberById(teamMemberId);
		return "success";
	}
	
	/**
	 * This method for delete team by id
	 * 
	 * @param teamMemberId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTeamByTeamID", method = RequestMethod.POST, headers = "Accept=application/json")
	public String getTeamByTeamID(@RequestParam(value = "teamId", required = false) Long teamId) throws Exception
	{
		String resultJson = null;
		Team team = teamService.getTeamByTeamId(teamId);
		StringBuilder ownerNameSB = new StringBuilder();
		team.setTeamOwnerName(ownerNameSB.append(team.getOwnerUserId().getFirstName()).append(" ").append(team.getOwnerUserId().getLastName()).toString());
		resultJson = commonUtil.toJSON(team);
		return resultJson;
	}
	
	/**
	 * @author David Raju
	 * @since sep 14 2016
	 * @version 0.0.1
	 * 
	 *          This method is used get All Users By OrgLocationCode
	 *          ExceptTeamOwner
	 * 
	 * @param teamOwnerUserId
	 *            This is the first paramter to
	 *            getAllUsersByOrgLocationCodeExceptTeamOwner
	 * 
	 * @return string
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/getAllUsersByOrgLocationCodeExceptTeamOwner")
	public String getAllUsersByOrgLocationCodeExceptTeamOwner(@RequestParam(value = "teamOwnerUserId", required = false) Long teamOwnerUserId, HttpServletRequest request) throws Exception
	{
		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> freshUserList = userService.getAllUsersByOrgLocationCodeExceptTeamOwner(teamOwnerUserId, loginOrgLocation.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
		resultJson = commonUtil.toJSON(freshUserList);
		return resultJson;
	}
	
	@RequestMapping(value = "/storeEditedTeam")
	public Response<String> storeEditedTeam(TeamDTO teamDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try{
			
			if(user !=null){
				if(Validator.validate(teamDTO)){
					teamService.storeTeam(teamDTO, orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}else{
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
				
			}else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/getHotCandidateListForTeamByTeamId")
	public @ResponseBody String getHotCandidateListForTeamByTeamId(@RequestParam(value = "teamId", required = false) Long teamId, HttpServletRequest request) throws Exception
	{
		String resultJson;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Candidate> candidaeList = candidateService.getHotListCandidatesByTeamAndOrgLocationId(teamId, orgLocations.getId());
		List<Candidate> candidateHotList = new ArrayList<Candidate>();
		if (null != candidaeList) {
			for (Candidate hotCandidate : candidaeList) {
				if (hotCandidate.getFirstName() != null) {
					hotCandidate.setCandidateName(hotCandidate.getFirstName());
					if (StringUtils.isNotEmpty(hotCandidate.getLastName())) {
						StringBuilder canNameSB = new StringBuilder();
						hotCandidate.setCandidateName(canNameSB.append(hotCandidate.getCandidateName()).append(" ").append(hotCandidate.getLastName()).toString());
					}
				}
				if (StringUtils.isNotEmpty(hotCandidate.getCountry())) {
					hotCandidate.setCandidateLocation(hotCandidate.getCountry());
					if (StringUtils.isNotEmpty(hotCandidate.getState())) {
						StringBuilder canStateSB = new StringBuilder();
						hotCandidate.setCandidateLocation(canStateSB.append(hotCandidate.getCandidateLocation()).append(", ").append(hotCandidate.getState()).toString());
						if (StringUtils.isNotEmpty(hotCandidate.getCity())) {
							StringBuilder canCitySB = new StringBuilder();
							hotCandidate.setCandidateLocation(canCitySB.append(hotCandidate.getCandidateLocation()).append(", ").append(hotCandidate.getCity()).toString());
						}
					}
				}
				candidateHotList.add(hotCandidate);
			}
		}
		resultJson = commonUtil.toJSON(candidateHotList);
		return resultJson;
	}
	
	@RequestMapping(value = "/removeFromTeam")
	public @ResponseBody String removeFromTeam(@RequestParam(value = "id", required = false) Long id, HttpServletRequest request) throws Exception
	{
		Candidate candidate = candidateService.getCandidateByCandidateId(id);
		if (null != candidate) {
			candidate.setTeamId(null);
			candidateService.saveOrUpdateCandidate(candidate);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
		}
		return "success";
	}
	
	/**
	 * To validate Team Name
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/validateTeamName", method = RequestMethod.POST)
	public @ResponseBody String validateTeamName(@RequestParam(value = "name", required = false) String name, HttpServletRequest request) throws Exception
	{
		String result = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Team team = teamService.findByTeamName(name, orgLocation.getId());
		if (null != team) {
			result = "true";
		}
		else {
			result = "false";
		}
		return result;
	}
}

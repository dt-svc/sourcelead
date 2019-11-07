package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.reportsDTO.TimeToFillDTO;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class ReportsController {

private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${atsUrl}")
	private String atsUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	ClientReportService clientReportService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	private WageTaxSummaryService wageTaxSummaryService;
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@RequestMapping(value = "/applicantsBackoutReport")
	public ModelAndView applicantBackout(HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
	  	 List<RequirementDTO> requirementsList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
	  	 
		 ModelAndView model = new ModelAndView();
		 model.addObject("requirementsList",requirementsList);
		 model.setViewName("reports/applicantsBackoutReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	@RequestMapping(value = "/applicantBySource")
	public ModelAndView applicantBySource(HttpServletRequest request) throws Exception
	{
		 
		 
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
	  	 ModelAndView model = new ModelAndView();
	  	 List<RequirementDTO> requirementsDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementsList", requirementsDTOList);
		 model.setViewName("reports/applicantBySource");
		 return model;
	}
	
	
	@RequestMapping(value = "/timeToFill")
	public ModelAndView timeToFill(HttpServletRequest request) throws Exception
	{
		 
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 List<TimeToFillDTO> yearList = new ArrayList<>();
		 
		 int year = Calendar.getInstance().get(Calendar.YEAR);
		 for(int i=0; i<=5; i++) {
			 TimeToFillDTO timeToFillDTO = new TimeToFillDTO();
			 timeToFillDTO.setYear(year-i);
			 yearList.add(timeToFillDTO);
		 }
		
	  	 ModelAndView model = new ModelAndView();
	  	 List<RequirementDTO> requirementsDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementsList", requirementsDTOList);
		 model.addObject("currentYear", year);
		 model.addObject("yearList", yearList);
		 model.setViewName("reports/timeToFill");
		 return model;
	}

	
	@RequestMapping(value = "/applicantBackoutReportsList")
	public ModelAndView applicantBackoutReports(HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
	  	 List<RequirementDTO> requirementsList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
	  	 
		 ModelAndView model = new ModelAndView();
		 model.addObject("requirementsList",requirementsList);
		 model.setViewName("reports/applicantsBackoutReportList");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}

	
	@RequestMapping(value = "/recruitersReports")
	public ModelAndView recruitersReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
	 String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	 boolean requirementTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");

	 
		Collection<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);

	 ModelAndView model = new ModelAndView();
	 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());	
	 List<Team> teamsList = null;
	 if(!loginUserRole.equalsIgnoreCase("ROLE_ADMIN")) {
		teamsList = teamService.getTeamListByTeamOwnerAndStatus(user.getId(),orgLocations.getId()); 
	 }
	 else{
		 teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId()); 
	 }
	 model.addObject("rolesList", rolesList);
     model.addObject("teamsList", teamsList);
	 model.addObject("userList", userList);
	 model.addObject("requirementTabAccess", requirementTabAccess);
	 model.addObject("loginUserRole", loginUserRole);
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.setViewName("reports/recruitersReports");
	 return model;
		
    }
	
	@RequestMapping(value = "/myPerformanceReports")
	public ModelAndView myPerformanceReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
	 String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

	 boolean requirementTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
	
	 
	 
	 ModelAndView model = new ModelAndView();
	 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
	 List<Team> teamsList =null;
	 if(loginUserRole.equalsIgnoreCase("ROLE_LEAD_RECRUITER")) {
			teamsList = teamService.getTeamListByTeamOwnerAndStatus(user.getId(),orgLocations.getId()); 
		 }
		 else{
			 teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId()); 
		 }
     model.addObject("teamsList", teamsList);
	 model.addObject("userList", userList);
	 model.addObject("requirementTabAccess", requirementTabAccess);
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.setViewName("reports/myPerformanceReports");
	 return model;
		
    }
	
	@RequestMapping(value = "/recruiterJobsReport")
	public ModelAndView recruiterJobsReport(@RequestParam(value="userCode", required=false)String recruiterUserCode, 
			@RequestParam(value="searchValue", required=false)String searchValue,
			@RequestParam(value="timePeriod", required=false)String timePeriod,
			@RequestParam(value="customStartDate", required=false)String customStartDate,
			@RequestParam(value="customEndDate", required=false)String customEndDate,
			@RequestParam(value="orgTeams", required=false)String orgTeams,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
		 User recruiterUser = userService.getUserByUserCode(recruiterUserCode);
		 
		 
		 boolean requirementTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
		 ModelAndView model = new ModelAndView();
		 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		 List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(recruiterUser.getId(), orgLocations.getId());
		 model.addObject("teamsList", teamsList);
		 model.addObject("userList", userList);
		 model.addObject("recruiterUser", recruiterUser);
		 
		 model.addObject("searchValue", searchValue);
		 model.addObject("timePeriod", timePeriod);
		 model.addObject("customStartDate", customStartDate);
		 model.addObject("customEndDate", customEndDate);
		 model.addObject("orgTeams", orgTeams);
		 
		 model.addObject("requirementTabAccess", requirementTabAccess);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/recruiterJobsReport");
		 return model;
    }
	
	@RequestMapping(value = "/recruiterCandidatesReportList")
	public ModelAndView recruiterCandidatesReportList(@RequestParam(value="userCode", required=false)String userCode, 
			@RequestParam(value="searchValue", required=false)String searchValue,
			@RequestParam(value="timePeriod", required=false)String timePeriod,
			@RequestParam(value="customStartDate", required=false)String customStartDate,
			@RequestParam(value="customEndDate", required=false)String customEndDate,
			@RequestParam(value="orgTeams", required=false)String orgTeams,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
		 
		 User associateUser = userService.getUserByUserCode(userCode);
		 
		 
		 boolean candidateTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_CANDIATE));
		 ModelAndView model = new ModelAndView();
		 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		 List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(associateUser.getId(), orgLocations.getId());
		 model.addObject("teamsList", teamsList);
		 model.addObject("userList", userList);
		 model.addObject("associateUser", associateUser);
		 model.addObject("userCode", userCode);
		 
		 model.addObject("searchValue", searchValue);
		 model.addObject("timePeriod", timePeriod);
		 model.addObject("customStartDate", customStartDate);
		 model.addObject("customEndDate", customEndDate);
		 model.addObject("orgTeams", orgTeams);
		 
		 model.addObject("candidateTabAccess", candidateTabAccess);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/recruiterCandidatesReport");
		 return model;
    }
	
	@RequestMapping(value = "/recruiterApplicantsReportList")
	public ModelAndView recruiterApplicantsReportList(@RequestParam(value="userCode", required=false)String userCode, 
			@RequestParam(value="searchValue", required=false)String searchValue,
			@RequestParam(value="timePeriod", required=false)String timePeriod,
			@RequestParam(value="customStartDate", required=false)String customStartDate,
			@RequestParam(value="customEndDate", required=false)String customEndDate,
			@RequestParam(value="toStage", required=false)String toStage,
			@RequestParam(value="orgTeams", required=false)String orgTeams,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
		 
		 User associateUser = userService.getUserByUserCode(userCode);
		 
		 
		 boolean candidateTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_CANDIATE));
		 ModelAndView model = new ModelAndView();
		 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		 List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(associateUser.getId(), orgLocations.getId());
		 model.addObject("teamsList", teamsList);
		 model.addObject("userList", userList);
		 model.addObject("associateUser", associateUser);
		 model.addObject("userCode", userCode);
		 model.addObject("toStage", toStage);
		 
		 model.addObject("searchValue", searchValue);
		 model.addObject("timePeriod", timePeriod);
		 model.addObject("customStartDate", customStartDate);
		 model.addObject("customEndDate", customEndDate);
		 model.addObject("orgTeams", orgTeams);
		 
		 model.addObject("candidateTabAccess", candidateTabAccess);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/recruiterAllstageApplicantsReport");
		 return model;
    }
	
	
	@RequestMapping(value = "/reportsDashboard")
	public ModelAndView reportsDashboard(HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 model.setViewName("reports/reportsDashboard");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
		
	
	/*@RequestMapping(value = "/reportsDashboard")
	public ModelAndView userReports(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
		 boolean requirementTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
		 boolean vendorTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_VENDOR));
		 boolean billingTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_BILLING));
		  
		
		ModelAndView model = new ModelAndView();
		 model.addObject("requirementTabAccess", requirementTabAccess);
		 model.addObject("vendorTabAccess", vendorTabAccess);
		 model.addObject("billingTabAccess", billingTabAccess);
		 model.setViewName("reports/userReports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}*/
	
	@RequestMapping(value = "/billingReports")
	public ModelAndView billingReports(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 boolean billingTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_BILLING));

		 
		ModelAndView model = new ModelAndView();
		List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		 model.addObject("userList", userList);
		 model.addObject("billingTabAccess", billingTabAccess);
		 model.setViewName("reports/billingReports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	 @RequestMapping(value = "/applicantsListForReports")
		public ModelAndView applicantsListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReport");
		 return model;
			
	 }	
	 
	 @RequestMapping(value = "/applicantsReceivedListForReports")
		public ModelAndView applicantsReceivedListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsReceivedReport");
		 return model;
	 }
	 
	 @RequestMapping(value = "/applicantsSubmittedListForReports")
		public ModelAndView applicantsSubmittedListForReports(HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 Collection<OrgRelations> orgClientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
			if(null != orgClientList && orgClientList.size() > 0){
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
			    model.addObject("add_clientList", orgClientList);
			}
		 model.addObject("loginOrgLocId", orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsSubmittedReport");
		 return model;
	 }	 
	 
	 @RequestMapping(value = "/requirementStagesListForReports")
		public ModelAndView requirementStagesListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/requirementStageReports");
		  return model;		
	 }	
	 
	 
	 @RequestMapping(value = "/financialReportsList")
		public ModelAndView financialReportsList() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/financialReportsList");
		  return model;		
	 }	
	 
	 @RequestMapping(value = "/performanceReportList")
		public ModelAndView performanceReportList() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/performanceReportList");
		  return model;		
	 }	
	 
	 @RequestMapping(value = "/hrReportsList")
		public ModelAndView hrReportsList() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/hrReportsList");
		  return model;		
	 }	
	 
	 @RequestMapping(value = "/associateReportsList")
		public ModelAndView associateReportsList() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/associateReportsList");
		  return model;		
	 }	
	 @RequestMapping(value = "/immigrationReportsList")
		public ModelAndView immigrationReportsList() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/immigrationReportsList");
		  return model;		
	 }	
	 /**
	  * returns requirement assign to reports jsp page
	  * @return ModelAndView
	  * @throws Exception
	  */
	 @RequestMapping(value = "/requirementAssignToReports")
		public ModelAndView requirementAssignToReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 List<User> userList = userService.getUsersByUserRolesAndOrgLocationId(orgLocation.getId());
		 model.addObject("userList", userList);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/requirementAssignToReports");
		 return model;	
	 }	
	 
	 /**
	  * @author Pavan Jampana
	  * returns vendors report page 
	  */
	 @RequestMapping(value = "/vendorsReports")
		public ModelAndView vendorsReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
		 boolean vendorTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_VENDOR));
		 
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("vendorTabAccess", vendorTabAccess);
		 model.setViewName("reports/vendorsReport");
		 return model;
	 }	
	 
	 @RequestMapping(value = "/perfomanceClientReports")
		public ModelAndView clientReports(HttpServletRequest request) throws Exception
		{
			OrgLocations location = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			ModelAndView model = new ModelAndView();
			Map<String, Integer> activeAndInactiveCount = clientReportService.getAllClientsCountByOrgLocationIdAndUserId(location.getId(), user.getId());
			int activeClientsCount = activeAndInactiveCount.get("activeClient");
			int inActiveClientsCount = activeAndInactiveCount.get("inActiveClient");
			int totalClientsCount = activeClientsCount + inActiveClientsCount;
			model.addObject("activeClientsCount", activeClientsCount);
			model.addObject("inActiveClientsCount", inActiveClientsCount);
			model.addObject("totalClientsCount", totalClientsCount);
			Map<String, Integer> totalSpocCount = clientReportService.getAllClientsSpocCountByOrgLocationIdAndUserId(location.getId(), user.getId());
			model.addObject("activeSpocCount", totalSpocCount.get("totalactiveCount"));
			model.addObject("inActiveSpocCount", totalSpocCount.get("totalInactiveCount"));
			model.addObject("totalSpocCount", totalSpocCount.get("totalSpocCount"));
			model.setViewName("reports/perfomanceClientReports");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
	 
	 @RequestMapping(value = "/applicantDeclineReports")
		public ModelAndView applicantDeclineReports(HttpServletRequest request) throws Exception
		{
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			
		  	 List<RequirementDTO> requirementsList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
			
			 ModelAndView model = new ModelAndView();
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.addObject("requirementsList", requirementsList);
			 model.setViewName("reports/applicantDecline");
			 
			 return model;
		}
		
		@RequestMapping(value = "/applicantDeclineList")
		public ModelAndView applicantDeclineList(HttpServletRequest request) throws Exception
		{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");	
			 ModelAndView model = new ModelAndView();
			 List<RequirementDTO> requirementsList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.addObject("requirementsList", requirementsList);
			 model.setViewName("reports/applicantDeclineList");
			 
			 return model;
		}
	 
	 @RequestMapping(value = "/userUsageReport")
		public ModelAndView userUsageReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
//		 model.addObject("vendorTabAccess", vendorTabAccess);
		 model.setViewName("reports/usersUsageReportList");
		 return model;
	 }	
	 
	 @RequestMapping(value = "/userUsageReportsGraph")
		public ModelAndView userUsageReportsGraph(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
//		 model.addObject("vendorTabAccess", vendorTabAccess);
		 model.setViewName("reports/userUsageReportsGraph");
		 return model;
	 }
	 
	 @RequestMapping(value = "/placementReportListForReports")
		public ModelAndView placementsReportListForReports() throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/placementReport");
		  return model;		
	 }
	 
	 @RequestMapping(value = "/jobsPerformanceReport")
		public ModelAndView jobsPerformanceReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 ModelAndView model = new ModelAndView();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
			if(null != orgClientList && orgClientList.size() > 0){
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
			    model.addObject("orgClientList", orgClientList);
			}
			model.addObject("loginOrgLocId", orgLocations.getId());
		 model.setViewName("reports/jobsPerformanceReport");
		  return model;		
	 }
	 
	 @RequestMapping(value = "/jobPerformanceCandidatesByStatus")
		public ModelAndView jobPerformanceCandidatesByStatus(@RequestParam(value="tab", required=true) String tab,
				@RequestParam(value="orgLocationId", required=true) String orgLocationId,
				@RequestParam(value="requirementId", required=true) String requirementId,
				@RequestParam(value="statusType", required=true) String statusType,
				HttpServletRequest request) throws Exception {
			
			ModelAndView model = new ModelAndView();
		
			model.addObject("tab",tab);
			model.addObject("reqList","interList");
			model.addObject("requirementId",requirementId);
			model.addObject("statusType",statusType);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("reports/jobsPerformanceCandidateList");
			return model;
		}
	 
	 @RequestMapping(value = "/applicantsjoinedlist")
		public ModelAndView applicantsjoinedlist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 boolean requirementTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
		
		 
		 
		 ModelAndView model = new ModelAndView();
		 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());	
		 List<Team> teamsList = null;
		 if(loginUserRole.equalsIgnoreCase("ROLE_LEAD_RECRUITER")) {
			teamsList = teamService.getTeamListByTeamOwnerAndStatus(user.getId(),orgLocations.getId()); 
		 }
		 else{
			 teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId()); 
		 }
			Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
			for(OrgRelations ocl :orgClientList){
				ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
				ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
			 }
		    model.addObject("add_clientList", orgClientList);
	     model.addObject("teamsList", teamsList);
		 model.addObject("userList", userList);
		 model.addObject("requirementTabAccess", requirementTabAccess);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/applicantsjoinedlist");
		 return model;
			
	    }


	 @RequestMapping(value = "/employeeTasksReport")
		public ModelAndView employeeTasksReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/employeeTasksReportList");
		 return model;
	 }
	 
	 @RequestMapping(value = "/projectReportList")
		public ModelAndView projectReportList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/projectReportList");
		 return model;
	 }
	 
	 @RequestMapping(value = "/overdueTaskReport")
		public ModelAndView overdueTaskReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 ModelAndView model = new ModelAndView();
		 
		 List<Projects> projectList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		 model.addObject("projectList", projectList);
		 
		 List<LookUpData> taskTypeList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKTYPE,orgLocations.getId());
		 model.addObject("taskTypeList", taskTypeList);
		 
		 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKPRIORITY,orgLocations.getId());
		 model.addObject("taskPriorityList", taskPriorityList);
		 
		 
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/overdueTaskReport");
		 return model;
	 }
	 
	 
	 @RequestMapping(value = "/employeeTasksReportSummary")
		public ModelAndView employeeTasksReportSummary(ProjectassignTasksDTO projectassignTasksDTO,HttpServletRequest request) throws Exception {
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 ModelAndView model = new ModelAndView();
		 
		 List<Projects> projectList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		 model.addObject("projectList", projectList);
		 
		 List<LookUpData> taskTypeList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKTYPE,orgLocations.getId());
		 model.addObject("taskTypeList", taskTypeList);
		 
		 List<LookUpData> taskSeverityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSEVERITY,orgLocations.getId());
		 model.addObject("taskSeverityList", taskSeverityList);
		 
		 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKPRIORITY,orgLocations.getId());
		 model.addObject("taskPriorityList", taskPriorityList);
		 
		 List<LookUpData> taskStatusList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSTATUS,orgLocations.getId());
		 model.addObject("taskStatusList", taskStatusList);
		 
		 model.addObject("candidateId", projectassignTasksDTO.getCandidateId());
		 model.addObject("taskStatus", projectassignTasksDTO.getTaskType());
		 
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("reports/employeeTasksReportSummary");
		 return model;
	 }
		@RequestMapping(value = "/marginSheetReports")
		public ModelAndView marginSheetReports(HttpServletRequest request) throws Exception
		{
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
			 boolean billingTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_BILLING));
			Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
			 
			ModelAndView model = new ModelAndView();
			List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
			model.addObject("add_clientList", orgClientList);
			 model.addObject("userList", userList);
			 model.addObject("billingTabAccess", billingTabAccess);
			 model.setViewName("reports/marginSheetReports");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
		 		 
		 @RequestMapping(value = "/payrollReport")
			public ModelAndView payrollReport(HttpServletRequest request) throws Exception
			{
				SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
				ModelAndView model = new ModelAndView();
				if (sessionData == null) {
					sessionData = new SessionMaintainanceData();
				}
				// sessionData.setNav(Constants.PAYROLL);
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<WageTaxSummary> wageTaxSummaryList = wageTaxSummaryService.getUniqueWagesAndTaxListByOrgLocationId(orgLocation);
				if (wageTaxSummaryList != null) {
					model.addObject("wageTaxSummaryList", wageTaxSummaryList);
				}
				request.getSession().setAttribute("sessionData", sessionData);
				model.setViewName("reports/payrollReport");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
			}
			
			/**
			 * Returns all payroll balance reports for all employess jsp
			 * 
			 * @param request
			 * @return ModelAndView
			 * @throws Exception
			 */
			@RequestMapping(value = "/payrollAllBalanceReport")
			public ModelAndView allPayrollReportsJsp(HttpServletRequest request) throws Exception
			{
				SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
				ModelAndView model = new ModelAndView();
				if (sessionData == null) {
					sessionData = new SessionMaintainanceData();
				}
				// sessionData.setNav(Constants.PAYROLL);
				request.getSession().setAttribute("sessionData", sessionData);
				model.setViewName("reports/allPayrollReports");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
			}
			
			@RequestMapping(value = "/campaignStats")
			public ModelAndView campaignStats(HttpServletRequest request,HttpServletResponse response) {
				   ModelAndView model = new ModelAndView();
				   model.addObject("sourceleadUrl", sourceleadUrl);
				   model.setViewName("reports/campaignStats");
			       return model;
			}
			
			@RequestMapping(value = "/associateJoineReports")
			public ModelAndView associateJoinedReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
			 
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
			 String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			 boolean requirementTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
			 List<TimeToFillDTO> yearList = new ArrayList<>();
			 Calendar now = Calendar.getInstance();
			 now.add(Calendar.YEAR, 1);
			 int currentYearyear = Calendar.getInstance().get(Calendar.YEAR);
			 int year = now.get(Calendar.YEAR);
			 for(int i=0; i<=5; i++) {
				 TimeToFillDTO timeToFillDTO = new TimeToFillDTO();
				 timeToFillDTO.setYear(year-i);
				 yearList.add(timeToFillDTO);
			 }
			 
			 ModelAndView model = new ModelAndView();
			 List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());	
			 List<Team> teamsList = null;
			 if(loginUserRole.equalsIgnoreCase("ROLE_LEAD_RECRUITER")) {
				teamsList = teamService.getTeamListByTeamOwnerAndStatus(user.getId(),orgLocations.getId()); 
			 }
			 else{
				 teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId()); 
			 }
				Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
			    model.addObject("add_clientList", orgClientList);
		     model.addObject("teamsList", teamsList);
			 model.addObject("userList", userList);
			 model.addObject("requirementTabAccess", requirementTabAccess);
			 model.addObject("currentYear", currentYearyear);
			 model.addObject("yearList", yearList);
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 model.setViewName("reports/associateJoineReport");
			 return model;
				
		    }
			
	@RequestMapping(value = "/offerAcceptanceRate")
	public ModelAndView offerAcceptanceRateReport(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			if (user != null) {
				model.addObject("loginOrgLocId", orgLocations.getId());
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("reports/offerAcceptanceRate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	


	@RequestMapping(value = "/availableAssociatesReport")
	public ModelAndView availableAassociatesReport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		List<Candidate> empList = projectMembersService.getAllAvailableAssociates(orgLocations.getId());
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("empList", empList);
		 model.setViewName("reports/availableAssociates");

		return model;
		
	}

	@RequestMapping(value = "/pendingForExpenseApprovalReport")
	public ModelAndView pendingForExpenseApprovalReport(HttpServletRequest request) throws Exception
	{
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 List<Projects> projectList = projectsService.getAllProjectByOrgLocationsId(orgLocation.getId());
		 model.addObject("projectList", projectList);
		 model.setViewName("reports/pendingForExpenseApprovalReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	
	@RequestMapping(value = "/releasedJobsListByOrgRelationId")
	public ModelAndView releasedJobsListByOrgRelationId(@RequestParam(value="orgRelationId", required=true)Long orgRelationId,
			@RequestParam(value="spocId", required=true)Long spocId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	 ModelAndView model = new ModelAndView();
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.addObject("orgRelationId", orgRelationId);
	 model.addObject("spocId", spocId);
	 model.setViewName("reports/releasedToVendorJobsList");
	 return model;
 }	
	
	@RequestMapping(value = "/releasedJobsApplicantsDeclineListByOrgRelationId")
	public ModelAndView releasedJobsApplicantsDeclineListByOrgRelationId(@RequestParam(value="orgRelationId", required=true)Long orgRelationId,
			@RequestParam(value="spocId", required=true)Long spocId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	 ModelAndView model = new ModelAndView();
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.addObject("orgRelationId", orgRelationId);
	 model.addObject("spocId", spocId);
	 model.setViewName("reports/vendorJobsApplicantsDeclineList");
	 return model;
 }	
	
	@RequestMapping(value = "/releasedJobsApplicantsBackOutListByOrgRelationId")
	public ModelAndView releasedJobsApplicantsBackOutListByOrgRelationId(@RequestParam(value="orgRelationId", required=true)Long orgRelationId,
			@RequestParam(value="spocId", required=true)Long spocId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	 ModelAndView model = new ModelAndView();
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.addObject("orgRelationId", orgRelationId);
	 model.addObject("spocId", spocId);
	 model.setViewName("reports/vendorJobsApplicantsBackOutList");
	 return model;
 }
	
	
	@RequestMapping(value = "/releasedJobsApplicantsHiredListByOrgRelationId")
	public ModelAndView releasedJobsApplicantsHiredListByOrgRelationId(
	@RequestParam(value = "orgRelationId", required = true) Long orgRelationId,
	@RequestParam(value = "spocId", required = true) Long spocId, HttpServletRequest request,
	HttpServletResponse response) throws Exception {

	ModelAndView model = new ModelAndView();
	model.addObject("sourceleadUrl", sourceleadUrl);
	model.addObject("orgRelationId", orgRelationId);
	model.addObject("spocId", spocId);
	model.setViewName("reports/vendorJobsApplicantHiredList");
	return model;
	}
	
	@RequestMapping(value = "/loansAndAdvancesReport")
	public ModelAndView loansAndAdvancesReport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
	//	List<Candidate>empList = projectMembersService.getAllAvailableAssociates(orgLocations.getId());
	//	model.addObject("sourceleadUrl", sourceleadUrl);
	//	model.addObject("empList", empList);
		model.setViewName("reports/loansAndAdvancesReport");
	
		return model;

	}

}

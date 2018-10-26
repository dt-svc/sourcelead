package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class ReportsController {

private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value = "/recruitersReports")
	public ModelAndView recruitersReports(HttpServletRequest request,HttpServletResponse response) throws Exception {
	 
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
     model.addObject("teamsList", teamsList);
	 model.addObject("userList", userList);
	 model.addObject("requirementTabAccess", requirementTabAccess);
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
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
		 boolean vendorTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_VENDOR));
		 
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("vendorTabAccess", vendorTabAccess);
		 model.setViewName("reports/vendorsReport");
		 return model;
	 }	
	 
	 @RequestMapping(value = "/userUsageReport")
		public ModelAndView userUsageReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
//		 boolean vendorTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_VENDOR));
		 
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
//		 model.addObject("vendorTabAccess", vendorTabAccess);
		 model.setViewName("reports/usersUsageReportList");
		 return model;
	 }	
	 
	 @RequestMapping(value = "/userUsageReportsGraph")
		public ModelAndView userUsageReportsGraph(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		 
//		 boolean vendorTabAccess = tabsService.getTabAccessByRoleIdAndTabId(loginUserRoleId, orgLocations.getId(), Long.valueOf(Constants.TAB_VENDOR));
		 
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
	 
	 @RequestMapping(value = "/jobPerformanceAllApplicantsList")
		public ModelAndView jobPerformanceAllApplicantsList(@RequestParam(value="tab", required=true) String tab,
				@RequestParam(value="orgLocationId", required=true) String orgLocationId,
				@RequestParam(value="requirementId", required=true) String requirementId,
				HttpServletRequest request) throws Exception {
			
			ModelAndView model = new ModelAndView();
		
			String stage ="SUBMISSION";
		 
			model.addObject("tab",tab);
			model.addObject("reqList","interList");
			model.addObject("requirementId",requirementId);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("reports/jobPerformanceApplicantsList");
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
}

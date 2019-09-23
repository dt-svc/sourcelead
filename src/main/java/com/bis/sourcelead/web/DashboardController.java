package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@Controller
@RequestMapping("/app")
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 RequirementOffersService requirementOffersService;
	 
	 @Autowired
	 RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 OrgSpocService orgSpocService;
	 
	 @Autowired
	 TeamService teamService;

	@RequestMapping(value = "/dashboard")
	public ModelAndView projects() {
		logger.debug("display the dash board page");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("dashboard");
		return model;
	}
	
	@RequestMapping(value = "/professionalDashboard")
	public ModelAndView professionalDashboard(HttpServletRequest request) {
		logger.debug("display the dash board page");
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("professional/professionalDashboard");
		return model;
	}
	
	
	
	@RequestMapping(value = "/recruiterDashboard")
	public ModelAndView recruiterDashboard(HttpServletRequest request) {
		logger.debug("display the dash board page");
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("recruiter/recruiterDashboard");
		return model;
	}
	
	@RequestMapping(value = "/requirementApplicantsList")
	public ModelAndView requirementApplicantsList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		String stage ="SUBMISSION" ;
		
//		List<RequirementSubmissionHistory> toDayReqApplicantscount = getReqSubmissionListByStage(request,stage,tab);
		
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		Long loginUserId=user.getId();
		model.addObject("tab",tab);
		model.addObject("reqList","appList");
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		
		
//		model.addObject("toDayReqApplicantscount", toDayReqApplicantscount);
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementApplicantsList");
		return model;
	}
	
	
	@RequestMapping(value = "/requirementSubmissionsList")
	public ModelAndView requirementSubmissionsList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		String stage ="SUBMIT_TO_CLIENT" ;
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(orgLocationId));
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		/*List<RequirementSubmissionHistory> toDayReqSubmissionsList = getReqSubmissionListByStage(request,stage,tab);
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			for (RequirementSubmissionHistory requirementSubmissionHistory2 : toDayReqSubmissionsList) {
				 List<RequirementSubmissionHistory> requirementSubmissionHistory = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocations.getId(), "SUBMISSION", requirementSubmissionHistory2.getSubmissionId().getId());
					for (RequirementSubmissionHistory rsh : requirementSubmissionHistory) {
					      requirementSubmissionHistory2.getSubmissionId().setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getCreatedDate()));
				    }
				if("SUBMIT_TO_CLIENT".equalsIgnoreCase(requirementSubmissionHistory2.getToStage())){
					requirementSubmissionHistory2.getSubmissionId().setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),requirementSubmissionHistory2.getCreatedDate()));
					requirementSubmissionHistory2.getSubmissionId().setSubmittedBy(requirementSubmissionHistory2.getCreatedUser());
				}
			}*/
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		Long loginUserId=user.getId();
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.addObject("tab",tab);
		model.addObject("reqList","subList");
//		model.addObject("toDayReqSubmissionsList", toDayReqSubmissionsList);
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementSubmissionsList");
		return model;
	}
	
	@RequestMapping(value = "/requirementInterviewsList")
	public ModelAndView requirementInterviewsList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		String stage ="INTERVIEW" ;
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		Long loginUserId=user.getId();
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.addObject("tab",tab);
		model.addObject("reqList","interList");
//		model.addObject("toDayReqInterviewscount", toDayReqInterviewscount);
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementInterviewsList");
		return model;
	}
	@RequestMapping(value = "/requirementOffersList")
	public ModelAndView requirementOffersList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		String stage ="OFFERMADE" ;
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		
		Long loginUserId=user.getId();
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.addObject("tab",tab);
		model.addObject("reqList","offerlist");
//		model.addObject("toDayReqOfferscount", toDayReqOfferscount);
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementOffersList");
		return model;
	}
	
	

	public List<RequirementSubmissionHistory> getReqSubmissionListByStage(HttpServletRequest request, String stage, String tab) throws Exception
	{
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		Timezone timezone = null;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (loginOrgLocations.getTimezone() != null) {
			timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		}
		String userType = null;
		Date startUTCDate = null;
		Date endUTCDate = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59");
		if ("today".equals(tab)) {

			DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			String customStartTimeStr = outputFormatter.print(zoned);
			startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
			String endDate = outputFormatter1.print(zoned);
			Date endDateString = TZ_DF_TS.parse(endDate);
			endUTCDate = endDateString;

			/*
			 * DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 * DateTimeFormatter outputFormatter =
			 * DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00"); DateTimeFormatter
			 * outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59"); String
			 * startDate = outputFormatter.print(zoned); String startDateString =
			 * CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE,
			 * startDate); startUTCDate = TZ_DF_TS.parse(startDateString); String endDate =
			 * outputFormatter1.print(zoned); String endDateString =
			 * CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE,
			 * endDate); endUTCDate = TZ_DF_TS.parse(endDateString);
			 */
		} else {

			DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			String customStartTimeStr = outputFormatter.print(zoned);
			startUTCDate = TZ_DF_TS.parse(customStartTimeStr);

			/*
			 * DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 * DateTimeFormatter outputFormatter =
			 * DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00"); String startDate =
			 * outputFormatter.print(zoned); String startDateString =
			 * CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE,
			 * startDate); startUTCDate = TZ_DF_TS.parse(startDateString);
			 */
		}

		if (Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)) {
			userType = Constants.ADMIN;
		}

		return requirementSubmissionService.getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(
				loginOrgLocations.getId(), stage, tab, userType, user, startUTCDate, endUTCDate);
	}

	
	
	@RequestMapping(value = "/requirementRejectedList")
	public ModelAndView requirementRejectedList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		
		Long loginUserId=user.getId();
		model.addObject("tab",tab);
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.addObject("reqList","rejectedlist");
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementRejectedList");
		return model;
	}
	
	@RequestMapping(value = "/requirementJoinedList")
	public ModelAndView requirementJoinedList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		
		Long loginUserId=user.getId();
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.addObject("tab",tab);
		model.addObject("reqList","joinedlist");
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementJoinedList");
		return model;
	}
	
	@RequestMapping(value = "/requirementBackoutList")
	public ModelAndView requirementBackoutList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		
		Long loginUserId=user.getId();
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.addObject("tab",tab);
		model.addObject("reqList","backoutlist");
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementBackoutList");
		return model;
	}
	
	@RequestMapping(value = "/requirementShortList")
	public ModelAndView requirementShortList(@RequestParam(value="tab", required=true) String tab,
			@RequestParam(value="orgLocationId", required=true) String orgLocationId,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId));
		List<OrgRelationDTO> orgClientList1=new ArrayList<>();
		for(OrgRelationDTO orgspoc:orgClientList){
			OrgSpoc orgSpoc=orgSpocService.getSpocByVendorUserIdAndOrgRelationId(orgspoc.getOrgRelId().longValue(),user.getId());
			if(orgSpoc != null){
				if(orgSpoc.getVendorUser().getId().equals(user.getId())){
					orgClientList1.add(orgspoc);	
				}
				
			}
		}
		List<Team> teamsList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), Long.parseLong(orgLocationId));
		for(Team team:teamsList){
			team.setOwnerId(team.getOwnerUserId().getId());
		}
		Long loginUserId=user.getId();
		model.addObject("tab",tab);
		model.addObject("reqList","shortlist");
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("allClients", orgRelationsService.getAllClientsListOrgLocationId(Long.parseLong(orgLocationId)));
		model.addObject("myClients", orgClientList1);
		model.addObject("teamsList", teamsList);
		model.addObject("loginUserId", loginUserId);
		model.setViewName("requirement/requirementShortList");
		return model;
	}
	
	
}

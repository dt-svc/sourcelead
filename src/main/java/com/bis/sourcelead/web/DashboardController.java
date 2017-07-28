package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.bis.sourcelead.entity.DashboardCounts;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
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

	@RequestMapping(value = "/dashboard")
	public ModelAndView projects() {
		logger.debug("display the dash board page");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/dashboard");
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
		
		List<RequirementSubmissionHistory> toDayReqApplicantscount = getReqSubmissionListByStage(request,stage,tab);
		
		model.addObject("tab",tab);
		model.addObject("toDayReqApplicantscount", toDayReqApplicantscount);
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
		
		List<RequirementSubmissionHistory> toDayReqSubmissionsList = getReqSubmissionListByStage(request,stage,tab);
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			for (RequirementSubmissionHistory requirementSubmissionHistory2 : toDayReqSubmissionsList) {
				 List<RequirementSubmissionHistory> requirementSubmissionHistory = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocations.getId(), "SUBMISSION", requirementSubmissionHistory2.getSubmissionId().getId());
					for (RequirementSubmissionHistory rsh : requirementSubmissionHistory) {
					      requirementSubmissionHistory2.getSubmissionId().setCreatedDateStr(Constants.DMY.format(rsh.getSubmissionId().getCreatedDate()));
				    }
				if("SUBMIT_TO_CLIENT".equalsIgnoreCase(requirementSubmissionHistory2.getToStage())){
					requirementSubmissionHistory2.getSubmissionId().setSubmittedOn(Constants.DMY.format(requirementSubmissionHistory2.getCreatedDate()));
					requirementSubmissionHistory2.getSubmissionId().setSubmittedBy(requirementSubmissionHistory2.getCreatedUser());
				}
			}
		
		model.addObject("tab",tab);
		model.addObject("toDayReqSubmissionsList", toDayReqSubmissionsList);
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
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		String stage ="INTERVIEW" ;
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<RequirementSubmissionHistory> toDayReqInterviewscount = getReqSubmissionListByStage(request,stage,tab);
	   
		for (RequirementSubmissionHistory requirementSubmissionHistory2 : toDayReqInterviewscount) {
			int interviewedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocations.getId(), requirementSubmissionHistory2.getSubmissionId().getRequirement().getId(), "INTERVIEWED");
			requirementSubmissionHistory2.getSubmissionId().setNumOfInterview(interviewedCount);
			 List<RequirementSubmissionHistory> requirementSubmissionHistory = requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndStage(loginOrgLocations.getId(), "SUBMISSION", requirementSubmissionHistory2.getSubmissionId().getId());
			for (RequirementSubmissionHistory rsh : requirementSubmissionHistory) {
			      requirementSubmissionHistory2.getSubmissionId().setCreatedDateStr(Constants.DMY.format(rsh.getSubmissionId().getCreatedDate()));
		    }
		  }
		model.addObject("tab",tab);
		model.addObject("toDayReqInterviewscount", toDayReqInterviewscount);
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
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		String stage ="OFFERMADE" ;
		
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<RequirementSubmissionHistory> toDayReqOfferscount = getReqSubmissionListByStage(request,stage,tab);
		for (RequirementSubmissionHistory requirementSubmissionHistory : toDayReqOfferscount) {
			List<RequirementOffers> offersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(requirementSubmissionHistory.getSubmissionId().getId(), loginOrgLocations.getId());
			for (RequirementOffers requirementOffers : offersList) {
				requirementSubmissionHistory.getSubmissionId().setOfferId(requirementOffers.getId());
				requirementSubmissionHistory.getSubmissionId().setOfferRate(requirementOffers.getAmount());
				requirementSubmissionHistory.getSubmissionId().setRateType(requirementOffers.getPaymentType());
				requirementSubmissionHistory.getSubmissionId().setRatePer(requirementOffers.getPaymentMode());
				requirementSubmissionHistory.getSubmissionId().setOfferJoinDate(Constants.DMY.format(requirementOffers.getDateOfJoining()));
				if(null != requirementOffers.getPositionType()){
					requirementSubmissionHistory.getSubmissionId().setPositionType(requirementOffers.getPositionType());
				}
				if(null != requirementOffers.getFileName()){
					requirementSubmissionHistory.getSubmissionId().setAttachment(requirementOffers.getFileName());
				}
			}
				if("OFFERMADE".equalsIgnoreCase(requirementSubmissionHistory.getToStage())){
					requirementSubmissionHistory.getSubmissionId().setCreatedDateStr(Constants.DMY.format(requirementSubmissionHistory.getCreatedDate()));
				}
		}
		
		
		model.addObject("tab",tab);
		model.addObject("toDayReqOfferscount", toDayReqOfferscount);
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirementOffersList");
		return model;
	}
	
	
	private List<RequirementSubmissionHistory> getReqSubmissionListByStage(HttpServletRequest request, String stage, String tab) throws Exception
	{
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Timezone timezone = null;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginOrgLocations.getTimezone() != null){
			timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		}
		String userType = null;
		Date startUTCDate = null;
		Date endUTCDate = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if("today".equals(tab)){
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			 String startDate = outputFormatter.print(zoned);
			 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
			 startUTCDate = TZ_DF_TS.parse(startDateString);
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endUTCDate = TZ_DF_TS.parse(endDateString);
		}else{
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 String startDate = outputFormatter.print(zoned);
			 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
			 startUTCDate = TZ_DF_TS.parse(startDateString);
		}
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
		} 
		
		return requirementSubmissionService.getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),stage,tab,userType,user,startUTCDate,endUTCDate);
	}

	
}

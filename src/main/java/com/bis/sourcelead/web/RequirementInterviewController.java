package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.ProposeTimeService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Requirement Interview.
 * 
 * @author Prasanna Vadla
 *
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class RequirementInterviewController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService; 

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	ProposeTimeService proposeTimeService;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	
	@Autowired
	CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/acceptOrRejectInterview")
	 @ResponseBody  ModelAndView acceptOrRejectInterview(@RequestParam(value="interviewStatus", required=false) String interviewStatus,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		     ModelAndView model = new ModelAndView();
		     //OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    // SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		     RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		     if("Maybe".equalsIgnoreCase(interviewStatus)){
		    	 requirementInterview.setInterviewStatus(null);	 
		     }else{
		    	 requirementInterview.setInterviewStatus(interviewStatus);
		     }
			 
			 requirementInterviewService.saveRequirementInterview(requirementInterview);
			     
			 //bellow dates are convert to sql dates to current date
			     Date date = new Date();
		    	 java.sql.Timestamp currentDate = new java.sql.Timestamp(date.getTime());
			     if(requirementInterview.getStartTime() != null){
			    	 if(currentDate.equals(requirementInterview.getStartTime()) || requirementInterview.getStartTime().after(currentDate)){
			    		 requirementInterview.setStartTimeStr("interviewStartTime");
			      }
			     }
			     if(requirementInterview.getEndTime() != null){
			    	 if(currentDate.equals(requirementInterview.getEndTime()) || currentDate.before(requirementInterview.getEndTime())){
			    		 requirementInterview.setEndTimeStr("interviewEndTime");
			    	 }
	    	     }
			     
			     //stroe  requirementSubmissionHistoryService 
			     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			     RequirementSubmission  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementInterview.getSubmissionId().getId());
			     RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				 requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.INTERVIEW.toString());
				 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.ATTENDING.toString())){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_ACCEPT);
					 requirementInterviewService.applicantInterviewAcceptmailToInterviewInitiator(requirementInterviewId);
				 }
				 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.NOT_ATTENDING.toString())){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_REJECT);
					 requirementInterviewService.applicantInterviewAcceptmailToInterviewInitiator(requirementInterviewId);
				 }
				 /*if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.MAYBE.toString())){
					 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_TENTATIVE);
					 requirementInterviewService.applicantInterviewAcceptmailToInterviewInitiator(requirementInterviewId);
				 }*/
				 requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
				
				List<ProposeTime> proposeTime=proposeTimeService.getProposeTimeByInterViewId(Long.parseLong(requirementInterviewId));
				for(ProposeTime pst:proposeTime){
					if(null != pst.getProposeDate()){
						pst.setProposeDateStr(commonUtil.dateStrFromOrgDateFormatter(requirementInterview.getSubmissionId().getOrgLocations().getDateFormat(),pst.getProposeDate()));	
					}
				}
				model.addObject("proposeTime",proposeTime);
				Timezone timezone = timeZoneService.getTimezone(requirementInterview.getSubmissionId().getOrgLocations().getTimezone().getId());
				if(requirementInterview.getStartTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					model.addObject("interviewStartTimeStr",convertedDateStr);
					 
				}
				
				if(requirementInterview.getEndTime() != null){
					DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					model.addObject("interviewEndTimeStr",convertedDateStr);
				}
				
				model.setViewName("requirement/interviewAcceptOrRejectOrTentative");
				model.addObject("interviewStatus",interviewStatus);
				model.addObject("requirementInterviewId",requirementInterviewId);
				model.addObject("requirementInterview",requirementInterview);
				model.addObject("requirementInterviewDate",commonUtil.dateStrFromOrgDateFormatter(requirementInterview.getSubmissionId().getOrgLocations().getDateFormat(),requirementInterview.getDateOfInterview()));
				model.addObject("interviewEndTime",requirementInterview.getEndTimeStr());
				model.addObject("interviewStartTime",requirementInterview.getStartTimeStr());
				model.addObject("sourceleadUrl", sourceleadUrl);
				
			 return model;
	 }	
	
	
	
	@RequestMapping(value = "/proposeNewTimePublicpage")
	 @ResponseBody  ModelAndView proposeNewTimePublicpage(@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		     ModelAndView model = new ModelAndView();
		     //OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  //   SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		     RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		     String dateOfInterviewStr ="";
				List<ProposeTime> proposeTime=proposeTimeService.getProposeTimeByInterViewId(Long.parseLong(requirementInterviewId));
				for(ProposeTime pst:proposeTime){
					if(null != pst.getProposeDate()){
						pst.setProposeDateStr(commonUtil.dateStrFromOrgDateFormatter(requirementInterview.getSubmissionId().getOrgLocations().getDateFormat(),pst.getProposeDate()));	
					}
				}
				model.addObject("proposeTime",proposeTime);
				 Date date = new Date();
		    	 java.sql.Timestamp currentDate = new java.sql.Timestamp(date.getTime());
			     if(requirementInterview.getStartTime() != null){
			    	 if(currentDate.equals(requirementInterview.getStartTime()) || requirementInterview.getStartTime().after(currentDate)){
			    		 requirementInterview.setStartTimeStr("interviewStartTime");
			      }
			     }
			     if(requirementInterview.getEndTime() != null){
			    	 if(currentDate.equals(requirementInterview.getEndTime()) || currentDate.before(requirementInterview.getEndTime())){
			    		 requirementInterview.setEndTimeStr("interviewEndTime");
			    	 }
	    	     }
				Timezone timezone = timeZoneService.getTimezone(requirementInterview.getSubmissionId().getOrgLocations().getTimezone().getId());
				if(requirementInterview.getStartTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					model.addObject("interviewStartTimeStr",convertedDateStr);
					 
				}
				
				if(requirementInterview.getEndTime() != null){
					DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					model.addObject("interviewEndTimeStr",convertedDateStr);
				}
				if(requirementInterview.getDateOfInterview() != null){

					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,requirementInterview.getTimezone().getTimeZoneId(), currentTimeStr);
					Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
					interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(requirementInterview.getSubmissionId().getOrgLocations().getDateFormat(),interviewConverteDate);
					dateOfInterviewStr = interviewDateInTimezone;
			    }
				model.setViewName("requirement/proposeTime");
				model.addObject("interviewStatus","Maybe");
				model.addObject("requirementInterviewId",requirementInterviewId);
				model.addObject("requirementInterview",requirementInterview);
				model.addObject("requirementInterviewDate",dateOfInterviewStr);
				model.addObject("interviewEndTime",requirementInterview.getEndTimeStr());
				model.addObject("interviewStartTime",requirementInterview.getStartTimeStr());
				model.addObject("sourceleadUrl", sourceleadUrl);
				
			 return model;
	 }	
}
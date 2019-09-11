package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.apache.pdfbox.util.StringUtil;
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
	
	
	@RequestMapping(value = "/ws/acceptOrRejectInterview")
	 @ResponseBody  ModelAndView acceptOrRejectInterview(@RequestParam(value="interviewStatus", required=false) String interviewStatus,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		     ModelAndView model = new ModelAndView();
		     //OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    // SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		     RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		     if((requirementInterview.getInterviewStatus() !=null) && (requirementInterview.getInterviewStatus().equalsIgnoreCase("Attending") || requirementInterview.getInterviewStatus().equalsIgnoreCase("Not_Attending"))) {
		    	 model.addObject("interviewStatus","Action_Done");
		    	 model.setViewName("requirement/interviewAcceptOrRejectOrTentative");
		    	 model.addObject("sourceleadUrl", sourceleadUrl);
		     }else {
		    	 
		    	 if("Maybe".equalsIgnoreCase(interviewStatus)){
			    	 requirementInterview.setInterviewStatus(null);	 
			     }else{
			    	 requirementInterview.setInterviewStatus(interviewStatus);
			     } 
				  
				 requirementInterviewService.saveRequirementInterview(requirementInterview);
				 DateFormat DATE_TIME = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 //bellow dates are convert to sql dates to current date
				     Date date = new Date();
			    	 java.sql.Date currentDate = new java.sql.Date(date.getTime());
			    	 String stringDateFormatCurrentDate =  Constants.DMY.format(currentDate);
						Date dateDateFormatCurrentDate =  Constants.DMY.parse(stringDateFormatCurrentDate);
			    	 
			    	 java.sql.Timestamp currentDateFormatTime = new java.sql.Timestamp(date.getTime());
			    	 String stringDateFormatCurTime =  DATE_TIME.format(currentDateFormatTime);
						Date dateDateFormatCurTime =  DATE_TIME.parse(stringDateFormatCurTime);
			    	 
			    	 
			    	 
			    	 Date convertedDateStart = null;
			    	 Date convertedDateEnd = null;
			    	 Date dateDateFormatEnd = null;
			    	 Date dateDateFormatTimeEnd = null;
			    	 Timezone timezone = timeZoneService.getTimezone(requirementInterview.getTimezone().getId());
						if(requirementInterview.getStartTime() != null){
					    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
							String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
							String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
							DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
							 convertedDateStart = TZ_DF_TS.parse(startTimeUTC);
							String convertedDateStr = DF_DMY.format(convertedDateStart);
							model.addObject("interviewStartTimeStr",convertedDateStr);
							 
						}
						
						if(requirementInterview.getEndTime() != null){
							DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
							String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
							String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
							DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
							 convertedDateEnd = TZ_DF_TS.parse(startTimeUTC);
							String convertedDateStr = DF_DMY.format(convertedDateEnd);
							model.addObject("interviewEndTimeStr",convertedDateStr);
						}
						
			    	 
			    	 
			    	 
					java.sql.Date convertedDateStartSqlFormat = new java.sql.Date(convertedDateStart.getTime());
					String stringDateFormat =  Constants.DMY.format(convertedDateStartSqlFormat);
					Date dateDateFormat =  Constants.DMY.parse(stringDateFormat);
					
					java.sql.Timestamp convertedDateStartSqlFormatTime = new java.sql.Timestamp(convertedDateStart.getTime());
					String stringDateFormatTimeStart =  DATE_TIME.format(convertedDateStartSqlFormatTime);
					Date dateDateFormatTimeStart =  DATE_TIME.parse(stringDateFormatTimeStart);
					
					
					if(convertedDateEnd != null) {
					java.sql.Date convertedDateEndSQlFormat = new java.sql.Date(convertedDateEnd.getTime());
					String stringDateFormatEnd =  Constants.DMY.format(convertedDateEndSQlFormat);
					dateDateFormatEnd =  Constants.DMY.parse(stringDateFormatEnd);
					
					
					java.sql.Timestamp convertedDateEndSqlFormatTime = new java.sql.Timestamp(convertedDateEnd.getTime());
					String stringDateFormatTimeEnd =  DATE_TIME.format(convertedDateEndSqlFormatTime);
					dateDateFormatTimeEnd =  DATE_TIME.parse(stringDateFormatTimeEnd);
					}
					
					if(requirementInterview.getStartTime() != null && requirementInterview.getEndTime() == null){
						  if(dateDateFormat != null){
						    if(dateDateFormatCurrentDate.equals(dateDateFormat) || dateDateFormatCurrentDate.before(dateDateFormat)){
						    		 requirementInterview.setStartTimeStr("interviewStartTime");
						      }
						  }
					}
					 if(requirementInterview.getEndTime() != null){
					     if(dateDateFormat != null){
					    	 if((dateDateFormatCurrentDate.equals(dateDateFormat) && dateDateFormatTimeStart.after(dateDateFormatCurTime))|| dateDateFormatTimeStart.after(dateDateFormatCurTime)){
					    		 requirementInterview.setStartTimeStr("interviewStartTime");
					      }
					     }
					     if(dateDateFormatEnd != null){
					    	 if(dateDateFormatCurrentDate.equals(dateDateFormatEnd) &&  dateDateFormatCurTime.before(dateDateFormatTimeEnd)|| dateDateFormatTimeEnd.after(dateDateFormatCurTime)){
					    		 requirementInterview.setEndTimeStr("interviewEndTime");
					    	 }
			    	     }
					 }
				     //stroe  requirementSubmissionHistoryService 
				     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				     RequirementSubmission  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementInterview.getSubmissionId().getId());
				     RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
					 requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.INTERVIEW.toString());
					 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.ATTENDING.toString()) && ((dateDateFormatTimeEnd != null && dateDateFormatTimeEnd.after(dateDateFormatCurTime))||(dateDateFormatTimeStart != null && dateDateFormatTimeStart.after(dateDateFormatCurTime)))){
						 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_ACCEPT.toString());
						 requirementInterviewService.applicantInterviewAcceptmailToInterviewInitiator(requirementInterviewId);
					 }
					 if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.NOT_ATTENDING.toString()) && ((dateDateFormatTimeEnd != null && dateDateFormatTimeEnd.after(dateDateFormatCurTime))||(dateDateFormatTimeStart != null && dateDateFormatTimeStart.after(dateDateFormatCurTime)))){
						 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_REJECT.toString());
						 requirementInterviewService.applicantInterviewAcceptmailToInterviewInitiator(requirementInterviewId);
					 }
					 /*if(interviewStatus.equalsIgnoreCase(ApplicantSubmissionStatus.MAYBE.toString())){
						 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_TENTATIVE);
						 requirementInterviewService.applicantInterviewAcceptmailToInterviewInitiator(requirementInterviewId);
					 }*/
					 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					 requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
					
					List<ProposeTime> proposeTime=proposeTimeService.getProposeTimeByInterViewId(Long.parseLong(requirementInterviewId));
					for(ProposeTime pst:proposeTime){
						if(null != pst.getProposeDate()){
							pst.setProposeDateStr(commonUtil.dateStrFromOrgDateFormatter(requirementInterview.getSubmissionId().getOrgLocations().getDateFormat(),pst.getProposeDate()));	
						}
					}
					model.addObject("proposeTime",proposeTime);
					
					model.setViewName("requirement/interviewAcceptOrRejectOrTentative");
					model.addObject("interviewStatus",interviewStatus);
					model.addObject("requirementInterviewId",requirementInterviewId);
					model.addObject("requirementInterview",requirementInterview);
					model.addObject("requirementInterviewDate",commonUtil.dateStrFromOrgDateFormatter(requirementInterview.getSubmissionId().getOrgLocations().getDateFormat(),requirementInterview.getDateOfInterview()));
					model.addObject("interviewEndTime",requirementInterview.getEndTimeStr());
					model.addObject("interviewStartTime",requirementInterview.getStartTimeStr());
					model.addObject("sourceleadUrl", sourceleadUrl);
		     }
			 return model;
	 }	
	
	
	
	@RequestMapping(value = "/ws/proposeNewTimePublicpage")
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
				Date convertedDateStart = null;
				Date convertedDateEnd = null;
				
				DateFormat DATE_TIME = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				
				model.addObject("proposeTime",proposeTime);
				 Date date = new Date();
				 
				 /*date format*/
				java.sql.Date currentDatesqlformat = new java.sql.Date(date.getTime());
		    	String stringDateFormatCurrentDate =  Constants.DMY.format(currentDatesqlformat);
				Date dateFormatCurrentDate =  Constants.DMY.parse(stringDateFormatCurrentDate);
				 
				/*date-time format*/
		    	java.sql.Timestamp currentDateSql = new java.sql.Timestamp(date.getTime());
		    	String currentDateFormatTimeStart =  DATE_TIME.format(currentDateSql);
				Date dateTimeCurrentDate =  DATE_TIME.parse(currentDateFormatTimeStart);
			     
			     
				Timezone timezone = timeZoneService.getTimezone(requirementInterview.getTimezone().getId());
				if(requirementInterview.getStartTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getStartTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					convertedDateStart = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDateStart);
					model.addObject("interviewStartTimeStr",convertedDateStr);
				}
				
				if(requirementInterview.getEndTime() != null){
					DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(requirementInterview.getEndTime());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					convertedDateEnd = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDateEnd);
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
				
				
				
				/*date format*/
				java.sql.Date startDatesqlformat = new java.sql.Date(convertedDateStart.getTime());
		    	String stringDateFormatstartDate =  Constants.DMY.format(startDatesqlformat);
				Date dateFormatstartDate =  Constants.DMY.parse(stringDateFormatstartDate);
				 
				/*date-time format*/
		    	java.sql.Timestamp startDateSql = new java.sql.Timestamp(convertedDateStart.getTime());
		    	String startDateFormatTimeStart =  DATE_TIME.format(startDateSql);
				Date dateTimeFormatstartDate =  DATE_TIME.parse(startDateFormatTimeStart);
				
				if(requirementInterview.getStartTime() != null && requirementInterview.getEndTime() == null) {
					if(convertedDateStart != null){
				    	 if(dateFormatCurrentDate.equals(dateFormatstartDate) || dateFormatCurrentDate.before(dateFormatstartDate)){
				    		 requirementInterview.setStartTimeStr("interviewStartTime");
				      }
				     }
				}
				
				if(requirementInterview.getEndTime() != null) {
					if(convertedDateStart != null){
				    	 if((dateFormatCurrentDate.equals(dateFormatstartDate) && dateTimeFormatstartDate.after(dateTimeCurrentDate)) || dateTimeFormatstartDate.after(dateTimeCurrentDate)){
				    		 requirementInterview.setStartTimeStr("interviewStartTime");
				      }
				     }
				     if(convertedDateEnd != null){
							java.sql.Date endDatesqlformat = new java.sql.Date(convertedDateEnd.getTime());
					    	String stringDateFormatendDate =  Constants.DMY.format(endDatesqlformat);
							Date dateFormatendDate =  Constants.DMY.parse(stringDateFormatendDate);
							 
							/*date-time format*/
					    	java.sql.Timestamp endDateSql = new java.sql.Timestamp(convertedDateEnd.getTime());
					    	String endDateFormatTimeStart =  DATE_TIME.format(endDateSql);
							Date dateTimeFormatendDate =  DATE_TIME.parse(endDateFormatTimeStart);
							
				    	 if(dateFormatCurrentDate.equals(dateFormatendDate) && dateTimeCurrentDate.before(dateTimeFormatendDate) || dateTimeCurrentDate.before(dateTimeFormatendDate)){
				    		 requirementInterview.setEndTimeStr("interviewEndTime");
				    	 }
		    	     }
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
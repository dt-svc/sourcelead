package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.beans.ApplicantReport;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * 
 * @author root
 * @since 28-02-2017
 * Handles reports for Requirement
 */
@RestController
public class RequirementReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementReportRestController.class);
	   
	 @Autowired
	 RequirementService requirementService;
	
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 RequirementReportService requirementReportService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	
	 @RequestMapping(value = "/getRequirementReports")
	 public String getRequirementListByOrgLocationID(@RequestParam(value="reportType", required=false) String reportType,
			 @RequestParam(value="startDate", required=false) String customStartDate,
			 @RequestParam(value="endDate", required=false) String customEndDate,
			 HttpServletRequest request,HttpServletResponse response) throws Exception{

		 String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 String filterDate = null;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("yesterday".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(1);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					
				}else if("week".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(7);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twoweeks".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(14);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("month".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(30);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twomonths".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(60);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else{
					 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
						DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
						Date startTime =  commonUtil.getDateFormat(customStartDate);
						Date endTime =  commonUtil.getDateFormat(customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}
			
			List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName,orgLocation,user,filterDate,startDateUTC,endDateUTC);
			for(Requirement requirement : requirementList){
				//String postedDate = new SimpleDateFormat("yyyy-MM-dd").format(requirement.getCreatedDate());
				//requirement.setPostedDateStr(postedDate);
				 DateFormat standardFormat = new SimpleDateFormat ("yyyy-MM-dd");
				String currentTimeStr = Constants.TZ_DF.format(requirement.getCreatedDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = standardFormat.format(converteDate);
				requirement.setPostedDateStr(currentDateInTimezone);
			}
		    String reportsList = commonUtil.toJSON(requirementList);
			return reportsList;
	 }
	 
	 
	 @RequestMapping(value = "/getApplicantsReport")
	 public String getApplicantsReport(@RequestParam(value="reportType", required=false) String reportType,
			 @RequestParam(value="startDate", required=false) String customStartDate,
			 @RequestParam(value="endDate", required=false) String customEndDate,
			 HttpServletRequest request,HttpServletResponse response) throws Exception{

		 String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 String reportsList = null;

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 String filterDate = null;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("yesterday".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(1);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					
				}else if("week".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(7);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twoweeks".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(14);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("month".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(30);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twomonths".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(60);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else{
					 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
						DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
						Date startTime =  commonUtil.getDateFormat(customStartDate);
						Date endTime =  commonUtil.getDateFormat(customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}
			List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName,orgLocation,user,filterDate,startDateUTC,endDateUTC);
			List<ApplicantReport> applicantReportList = new ArrayList<>();
			if(null != requirementList){
				for(Requirement requirement:requirementList){
					ApplicantReport applicantReport = new ApplicantReport();
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;

					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsPerReq(requirement.getId());
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							if("submission".equals(rsh.getToStage())){
								applicantsCount++;
							}else if("shortList".equals(rsh.getToStage())){
								shortlistedCount++;
							}else if("interview".equals(rsh.getToStage())){
								interviewsCount++;
							}else if("offer_made".equals(rsh.getToStage())){
								offeredCount++;
							}else if("request_sent".equals(rsh.getToStage())){
								requestSentCount++;
							}else if("submit_to_client".equals(rsh.getToStage())){
								submitToClientCount++;
							}else{
								rejectedCount++;
							}
						}
						applicantReport.setRequirementTitle(requirement.getJobTitle());
						applicantReport.setPostedOn(getDateStr(requirement.getCreatedDate(),timezone));
						applicantReport.setApplicantsCount(applicantsCount);
						applicantReport.setShortlisted(shortlistedCount);
						applicantReport.setInterviewCount(interviewsCount);
						applicantReport.setSubmitToClientCount(submitToClientCount);
						applicantReport.setOfferedCount(offeredCount);
						applicantReport.setRejectedCount(rejectedCount);
						applicantReport.setRequestSentCount(requestSentCount);
						applicantReportList.add(applicantReport);
					}
				}	
				reportsList = commonUtil.toJSON(applicantReportList);
			}else{
				reportsList = commonUtil.toJSON("");
			}
			return reportsList;
	 }

		
	 
	/**
	 * returns applicants counts based on stage status
	 * 
	 * @param applicantStatus
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@Async
	@RequestMapping(value = "/getApplicantsCountForReports", method = RequestMethod.POST)
	@ResponseBody
	String getApplicantsCountForReports(
			@RequestParam(value = "applicantStatus", required = false) String applicantStatus,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Integer count = requirementReportService.getApplicantsCountsForReports(orgLocation.getId(), applicantStatus);
		return commonUtil.toJSON(count);

	}
			
		 
	
	/**
	 * returns applicants counts based on stage status
	 * 
	 * @param applicantStatus
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@Async
	@RequestMapping(value = "/getReceivedApplicantsCountForReports", method = RequestMethod.POST)
	@ResponseBody
	String getReceivedApplicantsCountForReports(HttpServletRequest request) throws Exception {
		
		ApplicantReport applicantReport = new ApplicantReport();
		int applicantsCount = 0;
		int requestSentCount = 0;
		int shortlistedCount = 0;
		int submitToClientCount = 0;
		int interviewsCount = 0;
		int offeredCount = 0;
		int rejectedCount = 0;
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<RequirementSubmissionHistory> rshList = requirementReportService.getReceivedApplicantsCountForReports(orgLocation.getId());
		
		for(RequirementSubmissionHistory rsh : rshList){
			
			if("submission".equals(rsh.getToStage())){
				applicantsCount++;
			}else if("shortList".equals(rsh.getToStage())){
				shortlistedCount++;
			}else if("interview".equals(rsh.getToStage())){
				interviewsCount++;
			}else if("offer_made".equals(rsh.getToStage())){
				offeredCount++;
			}else if("request_sent".equals(rsh.getToStage())){
				requestSentCount++;
			}else if("submit_to_client".equals(rsh.getToStage())){
				submitToClientCount++;
			}else{
				rejectedCount++;
			}
		}
		applicantReport.setApplicantsCount(applicantsCount);
		applicantReport.setShortlisted(shortlistedCount);
		applicantReport.setInterviewCount(interviewsCount);
		applicantReport.setSubmitToClientCount(submitToClientCount);
		applicantReport.setOfferedCount(offeredCount);
		applicantReport.setRejectedCount(rejectedCount);
		applicantReport.setRequestSentCount(requestSentCount);
		return commonUtil.toJSON(applicantReport);

	}
	
	@RequestMapping(value = "/getApplicantReceivedReports")
	 public String getApplicantReceivedReports(@RequestParam(value="reportType", required=false) String reportType,
			 @RequestParam(value="startDate", required=false) String customStartDate,
			 @RequestParam(value="endDate", required=false) String customEndDate,
			 HttpServletRequest request,HttpServletResponse response) throws Exception{

		 String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 String reportsList = null;

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 String filterDate = null;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("yesterday".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(1);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					
				}else if("week".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(7);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twoweeks".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(14);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("month".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(30);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twomonths".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(60);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else{
					 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
						DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
						Date startTime =  commonUtil.getDateFormat(customStartDate);
						Date endTime =  commonUtil.getDateFormat(customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}
			List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName,orgLocation,user,filterDate,startDateUTC,endDateUTC);
			List<ApplicantReport> applicantReportList = new ArrayList<>();
			if(null != requirementList){
				for(Requirement requirement:requirementList){
					
					List<RequirementSubmission> requirementSubmissionList = requirementReportService.getReceivedApplicantsCountsPerReq(requirement.getId());
					if(null != requirementSubmissionList && requirementSubmissionList.size() >0){
						for(RequirementSubmission rs : requirementSubmissionList){
								ApplicantReport applicantReport = new ApplicantReport();
								applicantReport.setRequirementTitle(requirement.getJobTitle());
								applicantReport.setReceivedOn(getDateStr(rs.getUpdatedDate(),timezone));
								applicantReport.setSource(rs.getCreatedUser()+'('+rs.getVendorLocationId().getOrg().getOrgName()+')');
								applicantReport.setApplicantName(rs.getCandidate().getFirstName());
								applicantReport.setStage(rs.getSubmissionStatus().toString());
								applicantReportList.add(applicantReport);
					}
				}	
				}
				reportsList = commonUtil.toJSON(applicantReportList);
			}
			else{
				reportsList = commonUtil.toJSON("");
			}
			return reportsList;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 /**
		 * Returns date format as string
		 * @param date
		 * @param timezone
		 * @return String
		 * @throws ParseException
		 */
	private String getDateStr(Date date, Timezone timezone) throws ParseException {
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 DateFormat standardFormat = new SimpleDateFormat ("dd-MM-yyyy");
		String currentTimeStr = Constants.TZ_DF.format(date);  
		String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
		Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
		return currentDateInTimezone = standardFormat.format(converteDate);
		
	}
}

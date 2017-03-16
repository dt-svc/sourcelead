package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.beans.ApplicantReport;
import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
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
	 private RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
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
		   
		 String reportsList = null;

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&& !"".equalsIgnoreCase(reportType) ){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
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
			List<RequirementSubmission> reqSubmissionList = requirementReportService.getTotalApplicants(orgLocation,startDateUTC,endDateUTC);
			List<ApplicantReport> applicantReportList = new ArrayList<>();
			if(null != reqSubmissionList){
				for(RequirementSubmission reqSub : reqSubmissionList){
					ApplicantReport applicantReport = new ApplicantReport();
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
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
							}else if("rejected".equals(rsh.getToStage())){
								rejectedCount++;
							}
						}
						applicantReport.setRequirementTitle(reqSub.getRequirement().getJobTitle());
						applicantReport.setPostedOn(getDateStr(reqSub.getRequirement().getCreatedDate(),timezone));
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
	String getReceivedApplicantsCountForReports(@RequestParam(value="reportType", required=false) String reportType,
			 @RequestParam(value="startDate", required=false) String customStartDate,
			 @RequestParam(value="endDate", required=false) String customEndDate,
			 HttpServletRequest request,HttpServletResponse response) throws Exception {
		

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
		    int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
		 ApplicantReport applicantReport = new ApplicantReport();
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
			List<RequirementSubmission> requirementSubmissionList = requirementReportService.getReceivedApplicantsCountsPerOrgLocId(orgLocation.getId(),startDateUTC,endDateUTC);
			
			for(RequirementSubmission reqSub : requirementSubmissionList){
				int applicantsCount = 0;
				int requestSentCount = 0;
				int shortlistedCount = 0;
				int submitToClientCount = 0;
				int interviewsCount = 0;
				int offeredCount = 0;
				int rejectedCount = 0;
				List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
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
						}else if("rejected".equals(rsh.getToStage())){
							rejectedCount++;
						}
					}
					 applicantsSum = applicantsSum+applicantsCount;
					 requestSentSum = requestSentSum+requestSentCount;
					 shortlistedSum = shortlistedSum+shortlistedCount;
					 submitToClientSum = submitToClientSum+submitToClientCount;
					 interviewsSum = interviewsSum+interviewsCount;
					 offeredSum = offeredSum+offeredCount;
					 rejectedSum = rejectedSum+rejectedCount;
				}
			}
			applicantReport.setApplicantsSum(applicantsSum);
			applicantReport.setShortlistedSum(shortlistedSum);
			applicantReport.setSubmitToClientSum(submitToClientSum);
			applicantReport.setInterviewsSum(interviewsSum);
			applicantReport.setOfferedSum(offeredSum);
			applicantReport.setRejectedSum(rejectedSum);
			applicantReport.setRequestSentSum(requestSentSum);
			
			reportsList = commonUtil.toJSON(applicantReport);
			
			/*List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName,orgLocation,user,filterDate,startDateUTC,endDateUTC);
			List<ApplicantReport> applicantReportList = new ArrayList<>();
			if(null != requirementList){
				
				for(Requirement requirement:requirementList){
				int applicantsCount = 0;
				int requestSentCount = 0;
				int shortlistedCount = 0;
				int submitToClientCount = 0;
				int interviewsCount = 0;
				int offeredCount = 0;
				int rejectedCount = 0;
				
				List<RequirementSubmissionHistory> rshList = requirementReportService.getReceivedApplicantsCountForReports(orgLocation.getId(),requirement.getId());
				
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
					}else if("rejected".equals(rsh.getToStage())){
						rejectedCount++;
					}
				}
				 applicantsSum = applicantsSum+applicantsCount;
				 requestSentSum = requestSentSum+requestSentCount;
				 shortlistedSum = shortlistedSum+shortlistedCount;
				 submitToClientSum = submitToClientSum+submitToClientCount;
				 interviewsSum = interviewsSum+interviewsCount;
				 offeredSum = offeredSum+offeredCount;
				 rejectedSum = rejectedSum+rejectedCount;
				}
				applicantReport.setApplicantsSum(applicantsSum);
				applicantReport.setShortlistedSum(shortlistedSum);
				applicantReport.setSubmitToClientSum(submitToClientSum);
				applicantReport.setInterviewsSum(interviewsSum);
				applicantReport.setOfferedSum(offeredSum);
				applicantReport.setRejectedSum(rejectedSum);
				reportsList = commonUtil.toJSON(applicantReport);
			}
			else{
				reportsList = commonUtil.toJSON("");
			}*/
			return reportsList;
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
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&& !"".equalsIgnoreCase(reportType)){
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
			/*List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName,orgLocation,user,filterDate,startDateUTC,endDateUTC);
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
			}*/
			List<ApplicantReport> applicantReportList = new ArrayList<>();
			List<RequirementSubmission> requirementSubmissionList = requirementReportService.getReceivedApplicantsCountsPerOrgLocId(orgLocation.getId(),startDateUTC,endDateUTC);
			if(null != requirementSubmissionList && requirementSubmissionList.size() >0){
				for(RequirementSubmission rs : requirementSubmissionList){
						ApplicantReport applicantReport = new ApplicantReport();
						applicantReport.setRequirementTitle(rs.getRequirement().getJobTitle());
						applicantReport.setReceivedOn(getDateStr(rs.getUpdatedDate(),timezone));
						applicantReport.setSource(rs.getCreatedUser()+'('+rs.getVendorLocationId().getOrg().getOrgName()+')');
						applicantReport.setApplicantName(rs.getCandidate().getFirstName());
						applicantReport.setStage(rs.getSubmissionStatus().toString());
						applicantReportList.add(applicantReport);
					}
			}	
			return commonUtil.toJSON(applicantReportList);
	 }
	
	
	/**
	 * To Get Total counts for applicants,shortlist,interview ect. based on dates
	 * @param reportType
	 * @param customStartDate
	 * @param customEndDate
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getApplicantsTotalCounts")
	 public String getApplicantsTotalCounts(@RequestParam(value="reportType", required=false) String reportType,
			 @RequestParam(value="startDate", required=false) String customStartDate,
			 @RequestParam(value="endDate", required=false) String customEndDate,
			 HttpServletRequest request,HttpServletResponse response) throws Exception{
		   
		    int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
		 ApplicantReport applicantReport = new ApplicantReport();
		 String reportsList = null;

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&&!"".equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
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
			
			List<RequirementSubmission> reqSubmissionList = requirementReportService.getTotalApplicants(orgLocation,startDateUTC,endDateUTC);
			if(null != reqSubmissionList){
				for(RequirementSubmission reqSub : reqSubmissionList){
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
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
							}else if("rejected".equals(rsh.getToStage())){
								rejectedCount++;
							}
						}
						 applicantsSum = applicantsSum+applicantsCount;
						 requestSentSum = requestSentSum+requestSentCount;
						 shortlistedSum = shortlistedSum+shortlistedCount;
						 submitToClientSum = submitToClientSum+submitToClientCount;
						 interviewsSum = interviewsSum+interviewsCount;
						 offeredSum = offeredSum+offeredCount;
						 rejectedSum = rejectedSum+rejectedCount;
					}
				}
				applicantReport.setApplicantsSum(reqSubmissionList.size());
				applicantReport.setShortlistedSum(shortlistedSum);
				applicantReport.setSubmitToClientSum(submitToClientSum);
				applicantReport.setInterviewsSum(interviewsSum);
				applicantReport.setOfferedSum(offeredSum);
				applicantReport.setRejectedSum(rejectedSum);
				applicantReport.setRequestSentSum(requestSentSum);
				
				reportsList = commonUtil.toJSON(applicantReport);
			}else{
				reportsList = commonUtil.toJSON("");
			}
			return reportsList;
	 }


	@RequestMapping(value = "/getDayWiseAppRecReports")
	public String getDayWiseAppRecReports(@RequestParam(value="reportType", required=false) String reportType,
			 @RequestParam(value="startDate", required=false) String customStartDate,
			 @RequestParam(value="endDate", required=false) String customEndDate,
			 HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		 String reportsList = null;
	
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&& !"".equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
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
	
			List<RequirementSubmission> requirementSubmissionVendors = requirementSubmissionService.getAllVendorsIdbyorgLocationId(orgLocation,startDateUTC,endDateUTC);
			List<ApplicantReport> applicantReportList = new ArrayList<>();
			ApplicantReport applicantReport = new ApplicantReport();
			for (RequirementSubmission rs : requirementSubmissionVendors) {
				List<RequirementSubmission> requirementSubmissionList = requirementReportService.getReceivedApplicantsCountsByVendorLocationId(orgLocation.getId(),rs.getVendorLocationId().getId(),startDateUTC,endDateUTC);
				List<RequirementSubmissionHistory> rshList = requirementReportService.getReceivedApplicantsCountForReportsByVendorLocationId(orgLocation.getId(),rs.getVendorLocationId().getId(),startDateUTC,endDateUTC);
				int applicantsCount = 0;
				int requestSentCount = 0;
				int shortlistedCount = 0;
				int submitToClientCount = 0;
				int interviewsCount = 0;
				int offeredCount = 0;
				int rejectedCount = 0;	
				
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
						}else if("rejected".equals(rsh.getToStage())){
							rejectedCount++;
						}
						
					}
					applicantReport.setApplicantsCount(requirementSubmissionList.size());
					applicantReport.setShortlisted(shortlistedCount);
					applicantReport.setInterviewCount(interviewsCount);
					applicantReport.setSubmitToClientCount(submitToClientCount);
					applicantReport.setOfferedCount(offeredCount);
					applicantReport.setRejectedCount(rejectedCount);
					applicantReport.setRequestSentCount(requestSentCount);
					applicantReport.setOrgName(rs.getVendorLocationId().getOrg().getOrgName());
					applicantReportList.add(applicantReport);
					}
			
				reportsList = commonUtil.toJSON(applicantReportList);
			
			return reportsList;
			 }
	 
	 /**
	  * get all assigend requirement to a memeber or user
	  * @param request
	  * @param response
	  * @return json
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getAllAssignedRequirementsReports")
	 public String getAllAssignedRequirementsReports(HttpServletRequest request,HttpServletResponse response) throws Exception{

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 List<RequirementAssignTo> reqAssignToList = requirementAssignToService.getAllAssignedRequirementByOrglocationId(orgLocation.getId());
		
		List<AssignedRequirementReports> astoList = new ArrayList<>();
		String reportsList;
		if(null != reqAssignToList){
			for(RequirementAssignTo rato :reqAssignToList){
				StringBuilder sb = new StringBuilder();
				AssignedRequirementReports assignedRequirementReports = new AssignedRequirementReports();
				assignedRequirementReports.setJobTitle(rato.getRequirementId().getJobTitle());
				assignedRequirementReports.setPostedOn(getDateStr(rato.getRequirementId().getUpdatedDate(),timezone));
				if(rato.getRequirementId().getManagerUserId() != null){
					if(rato.getRequirementId().getManagerUserId().getFirstName() !=null && rato.getRequirementId().getManagerUserId().getLastName() !=null){
						sb.append(rato.getRequirementId().getManagerUserId().getFirstName()).append(" ").append(rato.getRequirementId().getManagerUserId().getLastName());
					}else{
						sb.append(rato.getRequirementId().getManagerUserId().getFirstName());
					}
				}
				assignedRequirementReports.setOwner(sb.toString());
				
				if(rato.getRequirementId().getClientId() != null){
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Client")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getResponderLocationId().getOrg().getOrgName());
					}
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getLocationId().getOrg().getOrgName());
					}
				}
				StringBuilder assignUserName = new StringBuilder();
				if(null != rato.getAssignUserId()){
					if(null != rato.getAssignUserId().getFirstName() && null != rato.getAssignUserId().getLastName()){
						assignUserName.append(rato.getAssignUserId().getFirstName()).append(" ").append(rato.getAssignUserId().getLastName());
					}else{
						assignUserName.append(rato.getAssignUserId().getFirstName());
					}
				}
				assignedRequirementReports.setAssignedUser(assignUserName.toString());
				assignedRequirementReports.setAssignedOn(getDateStr(rato.getUpdatedDate(), timezone));
				assignedRequirementReports.setOffered(0);
				if(rato.getTeamId() !=null){
					assignedRequirementReports.setTeamName(rato.getTeamId().getName());
				}
				astoList.add(assignedRequirementReports);
			}	
			 reportsList = commonUtil.toJSON(astoList);
		}else{
			 reportsList = commonUtil.toJSON("");
		}
		
		
		return reportsList;
	 }
	 
	 
	 /**
	  * Get All Requiremets based on assigned user
	  * @param json
	  * @param request
	  * @param saveType
	  * @return json
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/getDayWiseAssignedReqReports", method = RequestMethod.GET)
		public @ResponseBody String getDayWiseAssignedReqReports(@RequestParam(value = "json", required = false) String json,HttpServletRequest request,HttpServletResponse response)throws Exception {

			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			String reportType = "";
			String customStartDate = "";
			String customEndDate = "";
			ArrayList<String> usersList = null;
			
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 if(null != jsonObj.getString("reportType") && !"".equals(jsonObj.getString("reportType"))){
				reportType = jsonObj.getString("reportType");
			}
			if(null != jsonObj.getString("StartDate") && !"".equals(jsonObj.getString("StartDate"))){
				customStartDate = jsonObj.getString("StartDate");
			}
			if(null != jsonObj.getString("EndDate") && !"".equals(jsonObj.getString("EndDate"))){
				customEndDate = jsonObj.getString("EndDate");
			}
		    if(jsonObj.has("user")){
		    	if(null != jsonObj.getString("user") && !"".equals(jsonObj.getString("user"))){
					String userIds = jsonObj.getString("user");
					String str = userIds.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
					usersList = new ArrayList<String>(Arrays.asList(str.split(",")));
				}	
		    }
			
			 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			 Date endDateUTC =null ;
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
				List<RequirementAssignTo> totalList = new ArrayList<>();
				if(null != usersList){
				   for(String userId : usersList){
					   List<RequirementAssignTo> reqAssignToList1 = requirementAssignToService.getAllAssignedRequirementByDateRange(orgLocation.getId(),userId,startDateUTC,endDateUTC);   
					   if(null != reqAssignToList1 && reqAssignToList1.size() >0){
						   totalList.addAll(reqAssignToList1);  
					   }
				   }
				   
			    }else{
			    	List<RequirementAssignTo> reqAssignToList = requirementAssignToService.getAllAssignedRequirementByDateRange(orgLocation.getId(),null,startDateUTC,endDateUTC);
			    	if(null != reqAssignToList && reqAssignToList.size() >0){
			    		totalList.addAll(reqAssignToList);	
			    	}
			    }
				String reportsList;
				if(null != totalList && totalList.size() >0){
					List<AssignedRequirementReports> astoReqList = getAssignedRequirementList(totalList,timezone);
					reportsList = commonUtil.toJSON(astoReqList);	
				}else{
					reportsList = commonUtil.toJSON("");	
				}
				
			return reportsList;
	 }
	 
	 private List<AssignedRequirementReports> getAssignedRequirementList(List<RequirementAssignTo> totalList, Timezone timezone) throws ParseException {
		
		 List<AssignedRequirementReports> astoList = new ArrayList<>();
		 for(RequirementAssignTo rato :totalList){
				StringBuilder sb = new StringBuilder();
				AssignedRequirementReports assignedRequirementReports = new AssignedRequirementReports();
				assignedRequirementReports.setJobTitle(rato.getRequirementId().getJobTitle());
				assignedRequirementReports.setPostedOn(getDateStr(rato.getRequirementId().getUpdatedDate(),timezone));
				if(rato.getRequirementId().getManagerUserId() != null){
					if(rato.getRequirementId().getManagerUserId().getFirstName() !=null && rato.getRequirementId().getManagerUserId().getLastName() !=null){
						sb.append(rato.getRequirementId().getManagerUserId().getFirstName()).append(" ").append(rato.getRequirementId().getManagerUserId().getLastName());
					}else{
						sb.append(rato.getRequirementId().getManagerUserId().getFirstName());
					}
				}
				assignedRequirementReports.setOwner(sb.toString());
				
				if(rato.getRequirementId().getClientId() != null){
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Client")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getResponderLocationId().getOrg().getOrgName());
					}
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getLocationId().getOrg().getOrgName());
					}
				}
				StringBuilder assignUserName = new StringBuilder();
				if(null != rato.getAssignUserId()){
					if(null != rato.getAssignUserId().getFirstName() && null != rato.getAssignUserId().getLastName()){
						assignUserName.append(rato.getAssignUserId().getFirstName()).append(" ").append(rato.getAssignUserId().getLastName());
					}else{
						assignUserName.append(rato.getAssignUserId().getFirstName());
					}
				}
				assignedRequirementReports.setAssignedUser(assignUserName.toString());
				assignedRequirementReports.setAssignedOn(getDateStr(rato.getUpdatedDate(), timezone));
				assignedRequirementReports.setOffered(0);
				if(rato.getTeamId() !=null){
					assignedRequirementReports.setTeamName(rato.getTeamId().getName());
				}
				astoList.add(assignedRequirementReports);
			}
		 
		 return astoList;
	}

	 
	 /**
	  * Get All Requiremets Assign to counts based on date range
	  * @param json
	  * @param request
	  * @param saveType
	  * @return json
	  * @throws Exception
	  */
	 
	 @SuppressWarnings("unused")
	@RequestMapping(value = "/getAllRequirementAssignToCounts", method = RequestMethod.GET)
		public @ResponseBody String getAllRequirementAssignToCounts(@RequestParam(value = "json", required = false) String json,HttpServletRequest request,HttpServletResponse response)throws Exception {
		 
		    String reportType = "";
			String customStartDate = "";
			String customEndDate = "";
			ArrayList<String> usersList = null;
			
			int applicantsCount = 0;
			int requestSentCount = 0;
			int shortlistedCount = 0;
			int submitToClientCount = 0;
			int interviewsCount = 0;
			int offeredCount = 0;
			int rejectedCount = 0;	
			 
			AssignedRequirementReports assignedRequirementReports = new AssignedRequirementReports();
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
		 if(!"undefined".equals(json)){
			 String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			 if(null != jsonObj.getString("reportType") && !"".equals(jsonObj.getString("reportType"))){
					reportType = jsonObj.getString("reportType");
				}
				if(null != jsonObj.getString("StartDate") && !"".equals(jsonObj.getString("StartDate"))){
					customStartDate = jsonObj.getString("StartDate");
				}
				if(null != jsonObj.getString("EndDate") && !"".equals(jsonObj.getString("EndDate"))){
					customEndDate = jsonObj.getString("EndDate");
				}
			    if(jsonObj.has("user")){
			    	if(null != jsonObj.getString("user") && !"".equals(jsonObj.getString("user"))){
						String userIds = jsonObj.getString("user");
						String str = userIds.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
						usersList = new ArrayList<String>(Arrays.asList(str.split(",")));
					}	
			    }
				
		 }
			
			 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			 Date endDateUTC =null ;
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
				List<RequirementAssignTo> totalList = new ArrayList<>();
				if(null != usersList){
				   for(String userId : usersList){
					   List<RequirementAssignTo> reqAssignToList1 = requirementAssignToService.getAllAssignedRequirementByDateRange(orgLocation.getId(),userId,startDateUTC,endDateUTC);   
					   if(null != reqAssignToList1 && reqAssignToList1.size() >0){
						   totalList.addAll(reqAssignToList1);  
					   }
				   }
				   
			    }else{
			    	List<RequirementAssignTo> reqAssignToList = requirementAssignToService.getAllAssignedRequirementByDateRange(orgLocation.getId(),null,startDateUTC,endDateUTC);
			    	if(null != reqAssignToList && reqAssignToList.size() >0){
			    		totalList.addAll(reqAssignToList);	
			    	}
			    }
				
				String reportsList;
				if(null != totalList && totalList.size()>0){
					Set<String> userCodesList = new HashSet<>();
					for(RequirementAssignTo rsto :totalList){
						userCodesList.add(rsto.getAssignUserId().getUserCode());
					}
					List<RequirementSubmission> reqSubList = requirementReportService.getAllSubmissionsByAssignTeamMember(orgLocation.getId(),userCodesList,startDateUTC,endDateUTC);
					
					if(null != reqSubList && reqSubList.size()>0){
						List<Long> submissionIds = new ArrayList<>();
						for(RequirementSubmission rs :reqSubList){
							submissionIds.add(rs.getId());
						}
						List<RequirementSubmissionHistory> rshlist = requirementSubmissionHistoryService.getAllRequirementSubmissionHistoryBySubmissionId(submissionIds);
						
						for(RequirementSubmissionHistory rsh : rshlist){
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
							}else if("rejected".equals(rsh.getToStage())){
								rejectedCount++;
							}
						}
					}
					
				
					
					assignedRequirementReports.setApplicantsCount(applicantsCount);
					assignedRequirementReports.setShortlistedCount(shortlistedCount);
					assignedRequirementReports.setInterviewCount(interviewsCount);
					assignedRequirementReports.setSubmitToClientCount(submitToClientCount);
					assignedRequirementReports.setOfferedCount(offeredCount);
					assignedRequirementReports.setRejectedCount(rejectedCount);
					assignedRequirementReports.setRequestSentCount(requestSentCount);
					
					reportsList = commonUtil.toJSON(assignedRequirementReports);
				}else{
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

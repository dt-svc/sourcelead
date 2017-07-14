package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.opengis.referencing.operation.ConcatenatedOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.beans.ApplicantReport;
import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementReportDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class RequirementReportServiceImpl implements RequirementReportService {
	
	@Autowired
	RequirementReportDao requirementReportDao;
	@Autowired
	private TimeZoneService timeZoneService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementAssignToService requirementAssignToService;
	
	@Autowired
	private RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	private RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	
	@Override
	public List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user, String reportType, String customStartDate, String customEndDate) throws Exception
	{
		
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateUTC = null;
		String filterDate = null;
		Date startDateUTC = null;
		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)) {
			String endDate = outputFormatter1.print(zoned);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		if (null != reportType && !"".equals(reportType)) {
			if (Constants.TO_DAY.equals(reportType)) {
				String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			}
			else if (Constants.YESTER_DAY.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
			}
			else if (Constants.ONE_WEEK.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
			}
			else if (Constants.TWO_WEEKS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
			}
			else if (Constants.ONE_MONTH.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
			}
			else if (Constants.TWO_MONTHS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
			}
			else {
				if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
					DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
					Date startTime = commonUtil.getDateFormat(customStartDate);
					Date endTime = commonUtil.getDateFormat(customEndDate);
					String customStartTimeStr = TZ_DF_CSD.format(startTime);
					String customEndTimeStr = TZ_DF_CED.format(endTime);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					endDateUTC = TZ_DF_TS.parse(endDateString);
				}
			}
		}
		List<Requirement> requirementList = requirementReportDao.getRequirementReports(userRoleName, orgLocation, user, filterDate, startDateUTC, endDateUTC);
		for (Requirement requirement : requirementList) {
			String postedDate = getDateStr(requirement.getCreatedDate(), timezone);
			requirement.setPostedDateStr(postedDate);
			if(requirement.getClientId() != null){
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
				}
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
				}
			}
			if(requirement.getManagerUserId() != null){
				String managerFirstName = "";
				if(StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())){
					managerFirstName = requirement.getManagerUserId().getFirstName();
				}
				String managerLastName = "";
				if(StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())){
					managerLastName = requirement.getManagerUserId().getLastName();
				}
				 StringBuilder managerSB = new StringBuilder();
				requirement.setManager(managerSB.append(managerFirstName).append(" ").append(managerLastName).toString());
			}
		}
		return requirementList;
	}
	
	@Override
	public List<RequirementSubmissionHistory> getApplicantsCountsPerReq(Long requirementId)
	{
		return requirementReportDao.getRequirementReports(requirementId);
	}
	
	@Override
	public Integer getApplicantsCountsForReports(Long orgLocationID, String applicantStatus) throws Exception
	{
		return requirementReportDao.getApplicantsCountsForReports(orgLocationID, applicantStatus);
	}
	
	@Override
	public List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId)
	{
		return requirementReportDao.getReceivedApplicantsCountsPerReq(requirementId);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId, Long requirementId)
	{
		return requirementReportDao.getReceivedApplicantsCountForReports(orgLocationId, requirementId);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId)
	{
		return requirementReportDao.getApplicantsCountForChart(orgLocationId);
	}
	
	@Override
	public Integer getRequirementStagesCountsForReports(Long orgLocId, String requirementStatus) throws Exception
	{
		return requirementReportDao.getRequirementStagesCountsForReports(orgLocId, requirementStatus);
	}
	
	@Override
	public List<Requirement> getRequirementStageReports(OrgLocations orgLocation, String Status) throws Exception{
		
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		List<Requirement> reqList = requirementReportDao.getRequirementStageReports(orgLocation, Status);
		for (Requirement requirement : reqList) {
			String postedDate = getDateStr(requirement.getCreatedDate(), timezone);
			requirement.setPostedDateStr(postedDate);
			if(requirement.getClientId() != null){
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
				}
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
				}
			}
			if(requirement.getManagerUserId() != null){
				String managerFirstName = "";
				if(StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())){
					managerFirstName = requirement.getManagerUserId().getFirstName();
				}
				String managerLastName = "";
				if(StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())){
					managerLastName = requirement.getManagerUserId().getLastName();
				}
				 StringBuilder managerSB = new StringBuilder();
				requirement.setManager(managerSB.append(managerFirstName).append(" ").append(managerLastName).toString());
			}
		}
		
		return reqList;
	}
	
	@Override
	public List<RequirementSubmissionHistory> getReceivedApplicantsCountForReportsByVendorLocationId(Long orgLocationId, Long vendorLocationId, Date startDateUTC, Date endDateUTC)
	{
		return requirementReportDao.getReceivedApplicantsCountForReportsByVendorLocationId(orgLocationId, vendorLocationId, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<RequirementSubmission> getReceivedApplicantsCountsByVendorLocationId(Long orgLocationId, Long vendorLocationId, Date startDateUTC, Date endDateUTC)
	{
		return requirementReportDao.getReceivedApplicantsCountsByVendorLocationId(orgLocationId, vendorLocationId, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<ApplicantReport> getTotalApplicants(OrgLocations orgLocation, String customStartDate,String customEndDate,String reportType) throws Exception
	{
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
				if(Constants.TO_DAY.equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if("month".equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if("twomonths".equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
			List<RequirementSubmission> reqSubmissionList = requirementReportDao.getTotalApplicants(orgLocation, startDateUTC, endDateUTC);
			List<ApplicantReport> applicantReportList = null;
			if(null != reqSubmissionList){
				applicantReportList = new ArrayList<>();
				for(RequirementSubmission reqSub : reqSubmissionList){
					ApplicantReport applicantReport = new ApplicantReport();
					String submissionDate="";
					String shortlistDate="";
					String interviewDate="";
					String offerDate="";
					String requestSentDate="";
					String submitToClientDate="";
					String rejectedDate="";
					String backedOutDate="";
					
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportDao.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
				
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							if(ApplicantSubmissionStatus.SUBMISSION.toString().equals(rsh.getToStage())){
								submissionDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.SHORTLIST.toString().equals(rsh.getToStage())){
								shortlistDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.INTERVIEW.toString().equals(rsh.getToStage())){
								interviewDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.OFFERMADE.toString().equals(rsh.getToStage())){
								offerDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.REQUEST_SENT.toString().equals(rsh.getToStage())){
								requestSentDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT.toString().equals(rsh.getToStage())){
								submitToClientDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.REJECTED.toString().equals(rsh.getToStage())){
								rejectedDate = getDateStr(rsh.getCreatedDate(),timezone);
							}else if(ApplicantSubmissionStatus.BACKOUT.toString().equals(rsh.getToStage())){
								backedOutDate = getDateStr(rsh.getCreatedDate(),timezone);
							}
						}
						applicantReport.setRequirementTitle(reqSub.getRequirement().getJobTitle());
						if(reqSub.getCandidate().getLastName() != null && !"".equalsIgnoreCase(reqSub.getCandidate().getLastName())){
							StringBuilder sb = new StringBuilder();
							applicantReport.setApplicantName(sb.append(reqSub.getCandidate().getFirstName()).append(" ").append(reqSub.getCandidate().getLastName()).toString());
						}else{
							applicantReport.setApplicantName(reqSub.getCandidate().getFirstName());
						}
						
						applicantReport.setSubmissionDate(submissionDate);
						applicantReport.setShortlistDate(shortlistDate);
						applicantReport.setInterviewDate(interviewDate);
						applicantReport.setOfferDate(offerDate);
						applicantReport.setSubmitToClientDate(submitToClientDate);
						applicantReport.setRequestSentDate(requestSentDate);
						applicantReport.setRejectedDate(rejectedDate);
						applicantReport.setBackedOutDate(backedOutDate);
						if(null != reqSub.getSubmissionStatus()){
							applicantReport.setStage(reqSub.getSubmissionStatus().toString());
						}
						applicantReportList.add(applicantReport);
					}
				
				}
				return applicantReportList;
			}
			return applicantReportList;
		
	}
	
	@Override
	public ApplicantReport getApplicantsCountsByDateRange(OrgLocations orgLocation,String reportType,String customStartDate,String customEndDate) throws Exception
	{
		int applicantsSum = 0;
		int requestSentSum = 0;
		int shortlistedSum = 0;
		int submitToClientSum = 0;
		int interviewsSum = 0;
		int offeredSum = 0;
		int rejectedSum = 0;
		int backoutSum = 0;
	     ApplicantReport applicantReport = null;
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
			
			List<RequirementSubmission> reqSubmissionList = requirementReportDao.getTotalApplicants(orgLocation,startDateUTC,endDateUTC);
			if(null != reqSubmissionList){
				applicantReport = new ApplicantReport();
				for(RequirementSubmission reqSub : reqSubmissionList){
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;
					int backoutCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportDao.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							if(ApplicantSubmissionStatus.SUBMISSION.toString().equalsIgnoreCase(rsh.getToStage())){
								applicantsCount++;
							}else if(ApplicantSubmissionStatus.SHORTLIST.toString().equalsIgnoreCase(rsh.getToStage())){
								shortlistedCount++;
							}else if(ApplicantSubmissionStatus.INTERVIEW.toString().equalsIgnoreCase(rsh.getToStage())){
								interviewsCount++;
							}else if(ApplicantSubmissionStatus.OFFERMADE.toString().equalsIgnoreCase(rsh.getToStage())){
								offeredCount++;
							}else if(ApplicantSubmissionStatus.REQUEST_SENT.toString().equalsIgnoreCase(rsh.getToStage())){
								requestSentCount++;
							}else if(ApplicantSubmissionStatus.SUBMIT_TO_CLIENT.toString().equalsIgnoreCase(rsh.getToStage())){
								submitToClientCount++;
							}else if(ApplicantSubmissionStatus.REJECTED.toString().equalsIgnoreCase(rsh.getToStage())){
								rejectedCount++;
							}else if(ApplicantSubmissionStatus.BACKOUT.toString().equalsIgnoreCase(rsh.getToStage())){
								backoutCount++;
							}
						}
						 applicantsSum = applicantsSum+applicantsCount;
						 requestSentSum = requestSentSum+requestSentCount;
						 shortlistedSum = shortlistedSum+shortlistedCount;
						 submitToClientSum = submitToClientSum+submitToClientCount;
						 interviewsSum = interviewsSum+interviewsCount;
						 offeredSum = offeredSum+offeredCount;
						 rejectedSum = rejectedSum+rejectedCount;
						 backoutSum = backoutSum+backoutCount;
					}
				}
				applicantReport.setApplicantsSum(reqSubmissionList.size());
				applicantReport.setShortlistedSum(shortlistedSum);
				applicantReport.setSubmitToClientSum(submitToClientSum);
				applicantReport.setInterviewsSum(interviewsSum);
				applicantReport.setOfferedSum(offeredSum);
				applicantReport.setRejectedSum(rejectedSum);
				applicantReport.setRequestSentSum(requestSentSum);
				applicantReport.setBackoutSum(backoutSum);
				
				return applicantReport;
			}
			return applicantReport;
	}
	
	@Override
	public List<ApplicantReport> getReceivedApplicantsCountsPerOrgLocId(OrgLocations orgLocation,String reportType,String customStartDate,String customEndDate) throws Exception
	{
		
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
			List<ApplicantReport> applicantReportList = null;               
			List<RequirementSubmission> requirementSubmissionList = requirementReportDao.getReceivedApplicantsCountsPerOrgLocId(orgLocation.getId(),startDateUTC,endDateUTC);
			if(null != requirementSubmissionList && requirementSubmissionList.size() >0){
				applicantReportList = new ArrayList<>(); 
				for(RequirementSubmission rs : requirementSubmissionList){
						ApplicantReport applicantReport = new ApplicantReport();
						applicantReport.setRequirementTitle(rs.getRequirement().getJobTitle());
						applicantReport.setReceivedOn(getDateStr(rs.getUpdatedDate(),timezone));
						applicantReport.setSource(rs.getCreatedUser()+'('+rs.getVendorLocationId().getOrgLocationName()+')');
						applicantReport.setApplicantName(rs.getCandidate().getFirstName());
						applicantReport.setStage(rs.getSubmissionStatus().toString());
						applicantReportList.add(applicantReport);
					}
			}	
			return applicantReportList;
	}
	
	@Override
	public List<RequirementSubmission> getAllSubmissionsByAssignTeamMember(Long orgLocationId, Set<String> userCodesList, Date startDateUTC, Date endDateUTC)
	{
		return requirementReportDao.getAllSubmissionsByAssignTeamMember(orgLocationId, userCodesList, startDateUTC, endDateUTC);
	}
	
	@Override
	public ApplicantReport getApplicantsReceivedCountsByDateRange(OrgLocations orgLocation, String reportType, String customStartDate, String customEndDate) throws Exception
	{
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		    int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
		 ApplicantReport applicantReport = null;
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
			List<RequirementSubmission> requirementSubmissionList = requirementReportDao.getReceivedApplicantsCountsPerOrgLocId(orgLocation.getId(),startDateUTC,endDateUTC);
			if(null != requirementSubmissionList){
				applicantReport = new ApplicantReport();
				for(RequirementSubmission reqSub : requirementSubmissionList){
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportDao.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
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
				
				return applicantReport;
			}else{
				return applicantReport;
			}
			
	}
	
	@Override
	public List<ApplicantReport> getAllDayWiseApplicantsRecievedRports(String reportType, String customStartDate, String customEndDate,OrgLocations orgLocation) throws Exception
	{
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
			List<ApplicantReport> applicantReportList = null;
			ApplicantReport applicantReport = new ApplicantReport();
			for (RequirementSubmission rs : requirementSubmissionVendors) {
				List<RequirementSubmission> requirementSubmissionList = requirementReportDao.getReceivedApplicantsCountsByVendorLocationId(orgLocation.getId(),rs.getVendorLocationId().getId(),startDateUTC,endDateUTC);
				List<RequirementSubmissionHistory> rshList = requirementReportDao.getReceivedApplicantsCountForReportsByVendorLocationId(orgLocation.getId(),rs.getVendorLocationId().getId(),startDateUTC,endDateUTC);
				int applicantsCount = 0;
				int requestSentCount = 0;
				int shortlistedCount = 0;
				int submitToClientCount = 0;
				int interviewsCount = 0;
				int offeredCount = 0;
				int rejectedCount = 0;	
				if(null != rshList){
					applicantReportList = new ArrayList<>();
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
					applicantReport.setOrgName(rs.getVendorLocationId().getOrgLocationName());
					applicantReportList.add(applicantReport);
					}
				}
		return applicantReportList;
	}
	
	
	@Override
	public List<AssignedRequirementReports> getAllAssignedRequirementList(JSONObject jsonObj, OrgLocations orgLocation) throws Exception
	{
		String reportType = "";
		String customStartDate = "";
		String customEndDate = "";
		ArrayList<String> usersList = null;
		
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
			if(null != totalList && totalList.size() >0){
				List<AssignedRequirementReports> astoReqList = getAssignedRequirementList(totalList,timezone);
				return astoReqList;
			}
			return null;
	}

	private List<AssignedRequirementReports> getAssignedRequirementList(List<RequirementAssignTo> totalList, Timezone timezone) throws ParseException
	{
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
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getResponderLocationId().getOrgLocationName());
					}
					if(rato.getRequirementId().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						assignedRequirementReports.setClient(rato.getRequirementId().getClientId().getLocationId().getOrgLocationName());
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

	
	
	@Override
	public AssignedRequirementReports getAllRequirementAssignToCounts(OrgLocations orgLocation, String json) throws Exception
	{
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
					}else if(Constants.YESTER_DAY.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
					}else if(Constants.ONE_WEEK.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
					}else if(Constants.TWO_WEEKS.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
					}else if(Constants.ONE_MONTH.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
					}else if(Constants.TWO_MONTHS.equals(reportType)){
						startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
				AssignedRequirementReports assignedRequirementReports = null;
				if(null != totalList && totalList.size()>0){
					assignedRequirementReports = new AssignedRequirementReports();
					Set<String> userCodesList = new HashSet<>();
					for(RequirementAssignTo rsto :totalList){
						userCodesList.add(rsto.getAssignUserId().getUserCode());
					}
					List<RequirementSubmission> reqSubList = requirementReportDao.getAllSubmissionsByAssignTeamMember(orgLocation.getId(),userCodesList,startDateUTC,endDateUTC);
					
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
					
				}
			return assignedRequirementReports;
	}

	@Override
	public List<RequirementSubmission> allSubmittedApplicantsForReports(String jsonData, String customStartDate, String customEndDate, String reportType,OrgLocations orgLocation) throws Exception
	{
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
		
		String client = null;
		if(jsonData != null){
			String clientids = "{" + jsonData + "}";
			JSONObject jsonObj = new JSONObject(clientids);
			if(jsonObj.has("client")){
				client = jsonObj.getString("client");
			}
		}
		
		List<RequirementSubmission> shortlistedList = requirementSubmissionService.getRecruiterSubmittedCandidatesListForReportsByLocationIdAndStatus(orgLocation.getId(), client,filterDate,startDateUTC,endDateUTC);
			
			ArrayList<RequirementSubmission> submissionList = new ArrayList<RequirementSubmission>();

			for (RequirementSubmission requirementSubmission : shortlistedList) {
				RequirementSubmission req = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(requirementSubmission.getId(), orgLocation.getId());
				RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
				if (null != requirementSubmissionTemplateFields.getLastName()) {
					StringBuilder canNameSB = new StringBuilder();
					requirementSubmission.setCandidateName(canNameSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
				} else {
					requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName());
				}

				requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
				RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
				if(pastRequirementRateSubmissionNegotiation != null){
					requirementSubmission.setExpectedCTC(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				}else{
					requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
				}
				
				requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
				requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
				requirementSubmission.setRequirementName(requirementSubmission.getRequirement().getJobTitle());
				
				RequirementSubmissionHistory reqSubHistory = requirementSubmissionHistoryService.getRequirementSubmissionHistoryBySubmissionId(requirementSubmission.getId());
				if(reqSubHistory != null){
				requirementSubmission.setSubmittedBy(reqSubHistory.getCreatedUser());
				}
				
				if(null != req){
					if(null !=  req.getVendorLocationId()){
						requirementSubmission.setOrgName(req.getOrgLocations().getOrgLocationName());
					}
				}
				
				
				if (requirementSubmission.getCreatedDate() != null) {
					requirementSubmission
							.setCreatedDateStr(Constants.DF_DMY.format(requirementSubmission.getCreatedDate()));
				}
				submissionList.add(requirementSubmission);
			}
			//resultJson = commonUtil.toJSON(candidatesList);
			return submissionList;
	}

	@Override
	public ApplicantReport getSubmittedApplicantsCountForReports(String jsonData, String customStartDate, String customEndDate, String reportType,OrgLocations orgLocation, User user) throws Exception
	{
		 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 String filterDate = null;
		 Date startDateUTC = null;
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
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
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
		
				int applicantsCount = 0;
				int shortlistedCount = 0;
				int interviewsCount = 0;
				int offeredCount = 0;
				int rejectedCount = 0;
				
				String client = null;
				String clientids = "{" + jsonData + "}";
				JSONObject jsonObj = new JSONObject(clientids);
				if(jsonObj.has("client")){
					client = jsonObj.getString("client");
				}
				List<RequirementSubmission> requirementSubmissionList = requirementSubmissionService.getRecruiterSubmittedCandidatesListForReportsByLocationIdAndStatus(orgLocation.getId(), client,filterDate,startDateUTC,endDateUTC);
				for(RequirementSubmission rs : requirementSubmissionList){
					RequirementSubmission req = requirementSubmissionService.getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(rs.getId(), orgLocation.getId());
					if(null !=req && null != req.getSubmissionStatus()){
						if("SUBMISSION".equalsIgnoreCase(req.getSubmissionStatus().toString())){
							applicantsCount++;
						}else if("SHORTLIST".equalsIgnoreCase(req.getSubmissionStatus().toString())){
							shortlistedCount++;
						}else if("INTERVIEW".equalsIgnoreCase(req.getSubmissionStatus().toString())){
							interviewsCount++;
						}else if("OFFERMADE".equalsIgnoreCase(req.getSubmissionStatus().toString())){
							offeredCount++;
						}else if("REJECTED".equalsIgnoreCase(req.getSubmissionStatus().toString())){
							rejectedCount++;
						}
					}
				} 
				applicantReport.setApplicantsSum(applicantsCount);
				applicantReport.setShortlistedSum(shortlistedCount);
				applicantReport.setSubmitToClientSum(requirementSubmissionList.size());
				applicantReport.setInterviewsSum(interviewsCount);
				applicantReport.setOfferedSum(offeredCount);
				applicantReport.setRejectedSum(rejectedCount);
			return applicantReport;
	}

	/**
	 * used to get yesterday,week ago etc.dates
	 * 
	 * @param DateFormat
	 *            TZ_DF_TS
	 * @param DateTimeFormatter
	 *            outputFormatter
	 * @param DateTime
	 *            zoned
	 * @param numberofDays
	 * @param Timezone
	 *            timezone
	 * @return Date
	 * @throws ParseException
	 */
	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}
	
	/**
	 * Returns date format as string
	 * 
	 * @param date
	 * @param timezone
	 * @return String
	 * @throws ParseException
	 */
	private String getDateStr(Date date, Timezone timezone) throws ParseException
	{
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat standardFormat = new SimpleDateFormat("dd-MM-yyyy");
		String currentTimeStr = Constants.TZ_DF.format(date);
		String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), currentTimeStr);
		Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
		return currentDateInTimezone = standardFormat.format(converteDate);
	}

	@Override
	public List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long submissionId, Date startDateUTC,
			Date endDateUTC) throws Exception {
		return requirementReportDao.getApplicantsCountsByDateRange(submissionId, startDateUTC, endDateUTC);
	}

	
	
	
}

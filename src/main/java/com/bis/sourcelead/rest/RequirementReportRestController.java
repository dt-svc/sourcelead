package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.bis.sourcelead.beans.ApplicantReport;
import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.beans.RequirementStagesReport;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TimeZoneService;
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
	 RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	 
	 @Autowired
	 RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	 
	 @Autowired
	 RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	
	 /**
	  * Requirement reports based on date ranges
	  * @param reportType
	  * @param customStartDate
	  * @param customEndDate
	  * @param request
	  * @param response
	  * @return json
	  * @throws Exception
	  */
	@RequestMapping(value = "/getRequirementReports")
	public String getRequirementReports(@RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName, orgLocation, user, reportType, customStartDate, customEndDate);
		String reportsList;
		if (null != requirementList) {
			reportsList = commonUtil.toJSON(requirementList);
		}
		else {
			reportsList = commonUtil.toJSON("");
		}
		return reportsList;
	}
	 
	 
	/**
	 * Used to generate applicants reports based on date range
	 * 
	 * @param reportType
	 * @param customStartDate
	 * @param customEndDate
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getApplicantsReport")
	public String getApplicantsReport(@RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ApplicantReport> applicantReportsList = requirementReportService.getTotalApplicants(orgLocation, customStartDate, customEndDate, reportType);
		if (null != applicantReportsList) {
			reportsList = commonUtil.toJSON(applicantReportsList);
		}
		else {
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
		

		 String reportsList = null;

		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 // started
		 ApplicantReport applicantReceived = requirementReportService.getApplicantsReceivedCountsByDateRange(orgLocation,reportType,customStartDate,customEndDate);
		
		 if(null != applicantReceived){
			 reportsList = commonUtil.toJSON(applicantReceived); 
		 }else{
			 reportsList = commonUtil.toJSON(""); 
		 }
			return reportsList;
	}
	
	/**
	 * Used to get all applicants recieved from the client
	 * 
	 * @param reportType
	 * @param customStartDate
	 * @param customEndDate
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getApplicantReceivedReports")
	public String getApplicantReceivedReports(@RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ApplicantReport> applicantReportList = requirementReportService.getReceivedApplicantsCountsPerOrgLocId(orgLocation, reportType, customStartDate, customEndDate);
		if (null != applicantReportList) {
			reportsList = commonUtil.toJSON(applicantReportList);
		}
		else {
			reportsList = commonUtil.toJSON("");
		}
		return reportsList;
	 }
	
	
	/**
	 * To Get Total counts for applicants,shortlist,interview ect. based on
	 * dates
	 * 
	 * @param reportType
	 * @param customStartDate
	 * @param customEndDate
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getApplicantsTotalCounts")
	public String getApplicantsTotalCounts(@RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String reportsList = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ApplicantReport applicantReportCount = requirementReportService.getApplicantsCountsByDateRange(orgLocation, reportType, customStartDate, customEndDate);
		if (null != applicantReportCount) {
			reportsList = commonUtil.toJSON(applicantReportCount);
		}
		else {
			reportsList = commonUtil.toJSON("");
		}
		return reportsList;
	}
		
	/**
	 * get Requirement count based on status
	 * 
	 * @param status
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementCountBasedOnStatus")
	public String getRequirementCountBasedOnStatus(@RequestParam(value = "status", required = false) String status, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		RequirementStagesReport requirementStagesReport = new RequirementStagesReport();
		String reqStageReport = "";
		if (null == status) {
			int newCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "NEW");
			int draftCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "DRAFT");
			int submittedCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "SUBMITTED");
			int approvedCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "APPROVED");
			int rejectedCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "REJECTED");
			int openCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "OPEN");
			int onholdCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "ONHOLD");
			int filledclosedCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "FILLEDCLOSED");
			int cancelCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "CANCEL");
			int unfilledclosedCount = requirementReportService.getRequirementStagesCountsForReports(orgLocation.getId(), "UNFILLEDCLOSED");
			requirementStagesReport.setCancelRequirementsCount(cancelCount);
			requirementStagesReport.setDraftRequirementsCount(draftCount);
			requirementStagesReport.setFilledclosedRequirementsCount(filledclosedCount);
			requirementStagesReport.setNewRequirementsCount(newCount);
			requirementStagesReport.setOnholdRequirementsCount(onholdCount);
			requirementStagesReport.setOpenRequirementsCount(openCount);
			requirementStagesReport.setSubmittedRequirementsCount(submittedCount);
			requirementStagesReport.setUnfilledclosedRequirementsCount(unfilledclosedCount);
			requirementStagesReport.setApprovedRequirementsCount(approvedCount);
			requirementStagesReport.setRejectedRequirementsCount(rejectedCount);
			reqStageReport = commonUtil.toJSON(requirementStagesReport);
		}
		else {
			List<Requirement> reqList = requirementReportService.getRequirementStageReports(orgLocation, status);
			reqStageReport = commonUtil.toJSON(reqList);
		}
		return reqStageReport;
		 
	 }
	 
	 @RequestMapping(value = "/getRequirementListBasedOnStatus")
	 public String getRequirementListBasedOnStatus(@RequestParam(value="status", required=false) String status, HttpServletRequest request,HttpServletResponse response) throws Exception{
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 
		 List<Requirement> reqList = requirementReportService.getRequirementStageReports(orgLocation,status);
		 return commonUtil.toJSON(reqList);
	 }

	/**
	 * get applicants reports based on day wise
	 * 
	 * @param reportType
	 * @param customStartDate
	 * @param customEndDate
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDayWiseAppRecReports")
	public String getDayWiseAppRecReports(@RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ApplicantReport> applicantReportList = requirementReportService.getAllDayWiseApplicantsRecievedRports(reportType, customStartDate, customEndDate, orgLocation);
		if (null != applicantReportList) {
			reportsList = commonUtil.toJSON(applicantReportList);
		}
		else {
			reportsList = commonUtil.toJSON("");
		}
		return reportsList;
		
			 }
	 
	/**
	 * get all assigend requirement to a memeber or user
	 * 
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllAssignedRequirementsReports")
	public String getAllAssignedRequirementsReports(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		List<AssignedRequirementReports> astoList = requirementAssignToService.getAllAssignedRequirementByOrglocationId(orgLocation.getId(), timezone);
		if (null != astoList) {
			reportsList = commonUtil.toJSON(astoList);
		}
		else {
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

		    String reportsList;	
		    String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			List<AssignedRequirementReports> astoReqList = requirementReportService.getAllAssignedRequirementList(jsonObj,orgLocation);
			
			if (null != astoReqList) {
				reportsList = commonUtil.toJSON(astoReqList);
			}
			else {
				reportsList = commonUtil.toJSON("");
			}
			return reportsList;
			
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
		 
		   
		String reportsList;
		 AssignedRequirementReports assignedRequirementReports = new AssignedRequirementReports();
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 AssignedRequirementReports assignedRequirementRep = requirementReportService.getAllRequirementAssignToCounts(orgLocation,json);
		 
		 if (null != assignedRequirementRep) {
				reportsList = commonUtil.toJSON(assignedRequirementRep);
			}
			else {
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
	@RequestMapping(value = "/getAllSubmittedApplicantsForReports", method = RequestMethod.POST)
	@ResponseBody
	String getAllSubmittedApplicantsForReports(@RequestParam(value = "jsonData", required = false) String jsonData, @RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<RequirementSubmission> reqSubmissionList = requirementReportService.allSubmittedApplicantsForReports(jsonData, customStartDate, customEndDate, reportType, orgLocation);
		if (null != reqSubmissionList) {
			resultJson = commonUtil.toJSON(reqSubmissionList);
		}
		else {
			resultJson = commonUtil.toJSON("");
		}
		return resultJson;
			
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
	@RequestMapping(value = "/getSubmittedApplicantsCountForReports", method = RequestMethod.POST)
	@ResponseBody
	String getSubmittedApplicantsCountForReports(@RequestParam(value = "jsonData", required = false) String jsonData, @RequestParam(value = "reportType", required = false) String reportType, @RequestParam(value = "startDate", required = false) String customStartDate, @RequestParam(value = "endDate", required = false) String customEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ApplicantReport applicantReport = requirementReportService.getSubmittedApplicantsCountForReports(jsonData, customStartDate, customEndDate, reportType, orgLocation, user);
		if (null != applicantReport) {
			reportsList = commonUtil.toJSON(applicantReport);
		}
		else {
			reportsList = commonUtil.toJSON("");
		}
		return reportsList;
			 
		}
	
}

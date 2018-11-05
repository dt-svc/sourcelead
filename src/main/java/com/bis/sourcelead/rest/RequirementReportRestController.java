package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.beans.ApplicantReport;
import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsPdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
/**
 * 
 * @author root
 * @since 28-02-2017
 * Handles reports for Requirement
 */
@RestController
@RequestMapping("/app")
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
	 
	 @Autowired
	private RequirementLocationsService requirementLocationsService;
	 
	 @Autowired
	 private ReportsUtil reportsUtil;
	 
	 @Autowired
	 private ReportsPdfUtil reportsPdfUtil;
	 
	 @Autowired
	 private RequirementSubmissionDao requirementSubmissionDao;
	 
	 @Autowired
	 private RequirementOffersService requirementOffersService; 
	 
	
	
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
	public String getRequirementReports(@RequestParam(value = "reportType", required = false) String reportType, 
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName, orgLocation, user, reportType,customStartDate,customEndDate,status,searchValue);
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
	
	@RequestMapping(value = "/getJobsForPipelineReoport",method = RequestMethod.POST)
	public String getJobsForPipelineReoport(JobFilterDTO jobFilterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<RequirementDTO> reqList =  reportsUtil.getAllRequirementsByOrgLocationIdForJobsPipeLine(jobFilterDTO,orgLocation,user,userRole,request);
		reportsList = toJSON(reqList);
			return reportsList;
	 }
	
	public String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }

	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		// String startDateString =
		// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),
		// Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(yesterDayDate);
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
	public String getApplicantReceivedReports(@RequestParam(value = "reportType", required = false) String reportType, 
			@RequestParam(value = "startDate", required = false) String customStartDate, 
			@RequestParam(value = "endDate", required = false) String customEndDate, 
			@RequestParam(value = "searchValue", required = false) String searchValue, 
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String reportsList = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ApplicantReport> applicantReportList = requirementReportService.getReceivedApplicantsCountsPerOrgLocId(orgLocation, reportType, customStartDate, customEndDate,searchValue);
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
		ApplicantReport  applicantReceived= requirementReportService.getAllDayWiseApplicantsRecievedRports(reportType,customStartDate, customEndDate,orgLocation);
		if (null != applicantReceived) {
			reportsList = commonUtil.toJSON(applicantReceived);
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
	
	@RequestMapping(value = "/getJobsPerformancetListByOrgLocationIDAndUserId",method = RequestMethod.POST)
	 public ResponseList<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(JobFilterDTO jobFilterDTO,HttpServletRequest request) throws Exception {
		ResponseList<Requirement>   requirementList = null;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				
				String userCode = user.getUserCode();
				String userType = null;
				
				String[] tableColumns = { "jobId", "jobTitle", "client", "location", "priority", "applicants",
						"shortlisted", "interview", "offered", "submitToClient", "backOut", "joined", "rejected" };// should be
				// entity names
				int sortColumnIndex = 1; // by default sorting using invoice date
				String sortColumnOrder = "asc"; // by default descending order
				if (jobFilterDTO.getOrder() != null && !jobFilterDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(jobFilterDTO.getOrder().get(0).get("column"));
					sortColumnOrder = jobFilterDTO.getOrder().get(0).get("dir");
				}
				jobFilterDTO.setSortColumn(tableColumns[sortColumnIndex]);
				jobFilterDTO.setSortOrder(sortColumnOrder);
				
				Timezone timezone = timeZoneService.getTimezone(loginOrgLocation.getTimezone().getId());
				DateTime zoned = new DateTime();
				DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd");
				Date endDateUTC = null;
				String filterDate = null;
				Date startDateUTC = null;
				if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(jobFilterDTO.getJobCreatedDateRange())	&& !"".equals(jobFilterDTO.getJobCreatedDateRange())) {
					String endDate = outputFormatter.print(zoned);
					endDateUTC = TZ_DF_TS.parse(endDate);
					endDateUTC.setHours(23);
					endDateUTC.setMinutes(59);
					endDateUTC.setSeconds(59);
				}
				if (null != jobFilterDTO.getJobCreatedDateRange() && !"".equals(jobFilterDTO.getJobCreatedDateRange())) {
					if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(jobFilterDTO.getJobCreatedDateRange())) {
						String startDate = outputFormatter.print(zoned);
						filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
						startDateUTC = TZ_DF_TS.parse(startDate);
						if (Constants.ONE_MONTH.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
						}
						else if (Constants.TWO_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
						}
						else if (Constants.THREE_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
						}
						else if (Constants.SIX_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
						}
						else if (Constants.ONE_YEAR.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
						}
					}
					else {
						if (!"".equalsIgnoreCase(jobFilterDTO.getStartDateStr())
								&& !"".equalsIgnoreCase(jobFilterDTO.getEndDateStr())
								&& !"undefined".equalsIgnoreCase(jobFilterDTO.getStartDateStr())
								&& !"undefined".equalsIgnoreCase(jobFilterDTO.getEndDateStr())) {
							DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd");
							DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd");
							Date startTime = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), jobFilterDTO.getStartDateStr());
							Date endTime = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), jobFilterDTO.getEndDateStr());
							String customStartTimeStr = TZ_DF_CSD.format(startTime);
							String customEndTimeStr = TZ_DF_CED.format(endTime);
							startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
							endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
							endDateUTC.setHours(23);
							endDateUTC.setMinutes(59);
							endDateUTC.setSeconds(59);
						}
						else {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
						}
					}
					
				}
			
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
					requirementList = requirementService.getAssignedJobsPerformanceList(loginOrgLocation.getId(), userType, user.getId(), userCode,startDateUTC,endDateUTC,jobFilterDTO);
				}else{
					requirementList = requirementService.getAssignedJobsPerformanceList(loginOrgLocation.getId(), userType, user.getId(), userCode,startDateUTC,endDateUTC,jobFilterDTO);
				}
				
				requirementList.setResponse(Result.SUCCESS.name());
			}catch (Exception e) {
				requirementList.setResponse(Result.AWKWARD.name());
				requirementList.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			requirementList.setResponse(Result.SESSION_TIMEDOUT.name());
			requirementList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return requirementList;
	 }
	
	@RequestMapping(value = "/applicantListForJob")
	public @ResponseBody ResponseList<RequirementSubmissionHistoryDTO> applicantListForJob(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO, HttpServletRequest request)throws Exception
   {
		ResponseList<RequirementSubmissionHistoryDTO> responseList = new ResponseList<>();
		try {
				OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
				
				String userType = null;
				
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
				} 
				
			   ResponseList<RequirementSubmission> toDayReqApplicantscount =requirementSubmissionDao.getRequirementSubmissionsListWithJobIdByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, Long.parseLong(requirementSubmissionHistoryDTO.getRequirementId()),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,loginOrgLocations);
				
			   List<RequirementSubmissionHistoryDTO> rshDTOList = new ArrayList<>();
			   if(toDayReqApplicantscount.getData() != null)
				for(RequirementSubmission rsh: toDayReqApplicantscount.getData()){
					RequirementSubmissionHistoryDTO rshDTO = new RequirementSubmissionHistoryDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(rsh.getCandidate().getLastName())){
						rshDTO.setCandidateName(sb.append(rsh.getCandidate().getFirstName()).append(" ").append(rsh.getCandidate().getLastName()).toString());
					}else{
						rshDTO.setCandidateName(rsh.getCandidate().getFirstName());
					}
					
					rshDTO.setRequirementUUID(rsh.getRequirement().getRequirementUUID());
					rshDTO.setJobTitle(rsh.getRequirement().getJobTitle());
					if(rsh.getRequirement().getGeneratedId() != null) {
						rshDTO.setJobGeneratedId(rsh.getRequirement().getGeneratedId());
					}
					if(rsh.getRequirement().getPayOutPeriod() != null){
						rshDTO.setPayOutPeriod(rsh.getRequirement().getPayOutPeriod());
					}
					
					if(rsh.getRequirement().getPayOutPeriod() != null &&  rsh.getJoinedDate()  != null){
						int result = Integer.parseInt(rsh.getRequirement().getPayOutPeriod());
						Date generatedInvoiceDate=CalendarUtil.addDaysForGenerateInvoice(rsh.getJoinedDate(), result);
						DateTime dtOrg = new DateTime(generatedInvoiceDate);
//						DateTime generatedInvoiceDatePlusOne = dtOrg.plusDays(1);
						rshDTO.setGenerateInvoice(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),dtOrg.toDate()));
					}
					
					if(rsh.getRequirement().getParentRequirement() != null){
						rshDTO.setOrgLocationName(rsh.getRequirement().getParentRequirement().getOrgLocationId().getOrgLocationName());
					}
					
					if(rsh.getRequirement().getClientId() != null) {
						if (rsh.getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
							if (rsh.getRequirement().getClientId().getResponderLocationId() != null) {
								rshDTO.setOrgLocationName(rsh.getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
							}
						}
						if (rsh.getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
							if (rsh.getRequirement().getClientId().getLocationId() != null) {
							rshDTO.setOrgLocationName(rsh.getRequirement().getClientId().getLocationId().getOrgLocationName());
							}
						}
					}
					
					List<String> requirementLocationsList = new ArrayList<>();
					List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(rsh.getRequirement().getId());
					if(locationsList != null && !locationsList.isEmpty()) {
						for(RequirementLocations location : locationsList) {
							requirementLocationsList.add(location.getLocation());
						}
					}
					rshDTO.setLocationsList(requirementLocationsList);
					
					
					rshDTO.setEmail(rsh.getCandidate().getEmail());
		
					if(rsh.getBackOutCategoryType() != null) {
						rshDTO.setBackoutType(rsh.getBackOutCategoryType());
						rshDTO.setBackoutReason(rsh.getBackOutReason());
					}
					if(rsh.getRejectedCategoryType() != null) {
						rshDTO.setRejectedType(rsh.getRejectedCategoryType());
						rshDTO.setRejectedReason(rsh.getRejectedReason());
					}
					if(rsh.getRequirement().getJobRate() != null) {
						rshDTO.setJobRating(rsh.getRequirement().getJobRate());
					}
					if(rsh.getRequirement().getIncentivized() != null) {
						rshDTO.setIncentivize(rsh.getRequirement().getIncentivized());
					}
					if(rsh.getCandidate().getMobilePhone() != null){
						rshDTO.setMobilePhone(rsh.getCandidate().getMobilePhone());
					}
					
					if(rsh.getJoinedDate()  != null){
						rshDTO.setJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getJoinedDate()));
					}
					
					if(rsh.getCandidateResumes() != null){
						rshDTO.setResumeUUID(rsh.getCandidateResumes().getResumeUUID());
						rshDTO.setFileUrl(rsh.getCandidateResumes().getFileURL());
						rshDTO.setFileName(rsh.getCandidateResumes().getFileName());
						rshDTO.setContentType(rsh.getCandidateResumes().getFileContentType());
					}
					rshDTO.setPrimarySkill(rsh.getCandidate().getPrimarySkill());
					rshDTO.setCreatedUser(rsh.getCreatedUser());
					rshDTO.setCandidateScore(rsh.getCandidateScore());
					rshDTO.setSubmissionStatus(rsh.getSubmissionStatus().toString());
					rshDTO.setCandidateUUID(rsh.getCandidate().getCandidateUUID());
					rshDTO.setSubmissionId(rsh.getId());
					rshDTO.setExperienceYears(rsh.getCandidate().getExperienceYears());
					rshDTO.setExperienceMonths(rsh.getCandidate().getExperienceMonths());
					rshDTO.setFinalizedRate(rsh.getFinalizedRate());
					rshDTO.setSubmittedBy(rsh.getSubmittedBy());
					rshDTO.setFinalsSubmissionStatus(rsh.getSubmissionStatus());
					rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
					
					//rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getCreatedDate()));
					rshDTO.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
					rshDTO.setSubmittedBy(rsh.getCreatedUser());
		//					}
						
					
					
					int interviewedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocations.getId(), rsh.getRequirement().getId(), "INTERVIEWED");
					rshDTO.setNumOfInterview(interviewedCount);
					
						List<RequirementOffers> offersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(rsh.getId(), loginOrgLocations.getId());
						for (RequirementOffers requirementOffers : offersList) {
							rshDTO.setOfferId(requirementOffers.getId());
							rshDTO.setOfferRate(requirementOffers.getAmount());
							rshDTO.setPaymentType(requirementOffers.getPaymentType());
							rshDTO.setPaymentMode(requirementOffers.getPaymentMode());
							rshDTO.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),requirementOffers.getDateOfJoining()));
							if(null != requirementOffers.getPositionType()){
								rshDTO.setPositionType(requirementOffers.getPositionType());
							}
							if(null != requirementOffers.getFileName()){
								rshDTO.setAttachment(requirementOffers.getFileName());
							}
							if("OFFERMADE".equals(rsh.getSubmissionStatus())){
								rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
							}
						}
							
					
					
					rshDTOList.add(rshDTO);
				}
				responseList.setRecordsFiltered(toDayReqApplicantscount.getRecordsFiltered());
				responseList.setRecordsTotal(toDayReqApplicantscount.getRecordsTotal());
				responseList.setData(rshDTOList);
		
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
		
    }
	
	@RequestMapping(value = "/getJobsPerformancetListExportByOrgLocationIDAndUserId",method = RequestMethod.GET)
	 public void getJobsPerformancetListExportByOrgLocationIDAndUserId(JobFilterDTO jobFilterDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResponseList<Requirement>   requirementList = null;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
				
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				
				String userCode = user.getUserCode();
				String userType = null;
				
				Timezone timezone = timeZoneService.getTimezone(loginOrgLocation.getTimezone().getId());
				DateTime zoned = new DateTime();
				DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd");
				Date endDateUTC = null;
				String filterDate = null;
				Date startDateUTC = null;
				if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(jobFilterDTO.getJobCreatedDateRange())	&& !"".equals(jobFilterDTO.getJobCreatedDateRange())) {
					String endDate = outputFormatter.print(zoned);
					endDateUTC = TZ_DF_TS.parse(endDate);
					endDateUTC.setHours(23);
					endDateUTC.setMinutes(59);
					endDateUTC.setSeconds(59);
				}
				if (null != jobFilterDTO.getJobCreatedDateRange() && !"".equals(jobFilterDTO.getJobCreatedDateRange())) {
					if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(jobFilterDTO.getJobCreatedDateRange())) {
						String startDate = outputFormatter.print(zoned);
						filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
						startDateUTC = TZ_DF_TS.parse(startDate);
						if (Constants.ONE_MONTH.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
						}
						else if (Constants.TWO_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
						}
						else if (Constants.THREE_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 90);
						}
						else if (Constants.SIX_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 180);
						}
						else if (Constants.ONE_YEAR.equals(jobFilterDTO.getJobCreatedDateRange())) {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 365);
						}
					}
					else {
						if (!"".equalsIgnoreCase(jobFilterDTO.getStartDateStr())
								&& !"".equalsIgnoreCase(jobFilterDTO.getEndDateStr())
								&& !"undefined".equalsIgnoreCase(jobFilterDTO.getStartDateStr())
								&& !"undefined".equalsIgnoreCase(jobFilterDTO.getEndDateStr())) {
							DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd");
							DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd");
							Date startTime = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), jobFilterDTO.getStartDateStr());
							Date endTime = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), jobFilterDTO.getEndDateStr());
							String customStartTimeStr = TZ_DF_CSD.format(startTime);
							String customEndTimeStr = TZ_DF_CED.format(endTime);
							startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
							endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
							endDateUTC.setHours(23);
							endDateUTC.setMinutes(59);
							endDateUTC.setSeconds(59);
						}
						else {
							startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
						}
					}
					
				}
			
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
					requirementList = requirementService.getAssignedJobsPerformanceList(loginOrgLocation.getId(), userType, user.getId(), userCode,startDateUTC,endDateUTC,jobFilterDTO);
				}else{
					requirementList = requirementService.getAssignedJobsPerformanceList(loginOrgLocation.getId(), userType, user.getId(), userCode,startDateUTC,endDateUTC,jobFilterDTO);
				}
				
				File file = null;
			    if(jobFilterDTO.getExportType().equalsIgnoreCase("pdf")) {
			    	 file=  reportsPdfUtil.jobsPerformancePdfExport(requirementList,loginOrgLocation);
			    }else {
			    	 file=  reportsPdfUtil.jobsPerformanceExcelExport(requirementList,loginOrgLocation);
			    }
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
				if(file!= null){
					file.delete();
				}
			}
	
}

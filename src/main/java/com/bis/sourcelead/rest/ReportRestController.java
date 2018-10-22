package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@RequestMapping("/app")
public class ReportRestController {

	private static final Logger logger = LoggerFactory.getLogger(ReportRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	UserService userService;

	@Autowired
	ProjectsService projectsService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	RequirementService requirementService;

	@Autowired
	TeamService teamService;

	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
    
	@Autowired
	CandidateService candidateService;

	@Autowired
	RolesService rolesService;

	@Autowired
	TabsService tabsService;
	
	@Autowired
	OrgRelationsService orgRelationService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementReportService requirementReportService;
	
	@Autowired
	TimeZoneService timeZoneService;

	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	ClientVendorContactsIndexHelper clientVendorContactsIndexHelper;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	@Autowired
	private LetterHeadService letterHeadService;
	
	@Autowired
	private EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	@Autowired 
	OrgLocationService orgLocationService;
	
	
	/**
	 * To get All the Users by filtering
	 * 
	 * @param request
	 * @param userRole
	 * @param searchByName
	 * @param userState
	 * @param loginUserRole
	 * @return
	 */

	@RequestMapping(value = "/getAllUsersByOrgLocationsId")
	public @ResponseBody ResponseList<UserDTO> getAllUsersByOrgLocationsId(UserRoleDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
			UserDTO userDTO,HttpServletRequest request) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	    
			
			Date[] searchDates = null;
			
				if(!"custom".equalsIgnoreCase(timePeriod)){
					searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
				}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
		    	    
		    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					
					Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
					Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
					String customStartTimeStr = TZ_DF_CSD.format(startTime);  
					String customEndTimeStr = TZ_DF_CED.format(endTime);  
					Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
					Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
		    	   
		    	    searchDates = new Date[2];
		    	    searchDates[0] = startDateUTC;
		    	    searchDates[1] = endDateUTC;
				}
				List<Long> teamsList = new ArrayList<>();
				if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
					String[] orgTeams = userRoleDto.getOrgTeams().split(",");
					for(int i=0; i < orgTeams.length; i++) {
						teamsList.add(Long.parseLong(orgTeams[i]));
					}
				}
				String type=null;
			if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
				type=userRoleDto.getType();
			}
			String[] tableColumns = { "firstName", "jobs", "candidates", "applicants", "shortlisted", "submitted", "Interviewed", "offered", "rejected","joined", "backout" };
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
				sortColumnOrder = userDTO.getOrder().get(0).get("dir");
			}
			userDTO.setSortColumn(tableColumns[sortColumnIndex]);
			userDTO.setSortOrder(sortColumnOrder);
			ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type);
			return userDTOList;
		}
	
	
	@RequestMapping(value = "/getAllJobsByUserIdAndOrgLocationId")
	public @ResponseBody ResponseList<RequirementDTO> getAllJobsByUserIdAndOrgLocationId(RequirementDTO requirementDTO, @RequestParam(value="recruiterUserId", required=false) Long recruiterUserId, @RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	      //Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	      //User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	      //String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	      //String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	      
	      User recruiterUser = userService.getUserById(recruiterUserId);
			
			Date[] searchDates = null;
			
				if(!"custom".equalsIgnoreCase(timePeriod)){
					searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					if(timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
						Date newDate = DateUtils.addHours(searchDates[0], 24);
						searchDates[1] = newDate;
					}
				}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
		    	    
		    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					
					Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
					Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
					String customStartTimeStr = TZ_DF_CSD.format(startTime);  
					String customEndTimeStr = TZ_DF_CED.format(endTime);  
					Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
					Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
		    	   
		    	    searchDates = new Date[2];
		    	    searchDates[0] = startDateUTC;
		    	    searchDates[1] = endDateUTC;
				}
				List<Long> teamsList = new ArrayList<>();
				if(requirementDTO.getOrgTeams() != null && !requirementDTO.getOrgTeams().isEmpty()) {
					String[] orgTeams = requirementDTO.getOrgTeams().split(",");
					for(int i=0; i < orgTeams.length; i++) {
						teamsList.add(Long.parseLong(orgTeams[i]));
					}
				}
			
			String[] tableColumns = { "generatedId", "jobTitle", "client", "location", "applicants", "shortlist", "submitToClient", "interview", "offer", "rejected","joined", "backout" };
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (requirementDTO.getOrder() != null && !requirementDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(requirementDTO.getOrder().get(0).get("column"));
				sortColumnOrder = requirementDTO.getOrder().get(0).get("dir");
			}
			requirementDTO.setSortColumn(tableColumns[sortColumnIndex]);
			requirementDTO.setSortOrder(sortColumnOrder);
			requirementDTO.setOrgLocationId(orgLocations.getId());
			ResponseList<RequirementDTO> requirementDTOList = requirementService.getJobListOfRecruiter(requirementDTO, recruiterUserId,searchDates,teamsList);
			for(RequirementDTO reqDTO : requirementDTOList.getData()) {
				RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(reqDTO.getId(), recruiterUser, orgLocations, "ALL");
				reqDTO.setNoOfApplicants(String.valueOf(rsCount.getApplicantsCount()));
				reqDTO.setNoOfShortlistApplicants(String.valueOf(rsCount.getShortlistCount()));
				reqDTO.setNoOfSubmittedApplicants(String.valueOf(rsCount.getSubmitToClientCount()));
				reqDTO.setNoOfInterviewedApplicants(String.valueOf(rsCount.getInterviewCount()));
				reqDTO.setNoOfOfferedApplicants(String.valueOf(rsCount.getOffersCount()));
				reqDTO.setNoOfJoinedApplicants(String.valueOf(rsCount.getJoinedCount()));
				reqDTO.setNoOfRejectedApplicants(String.valueOf(rsCount.getRejectedCount()));
				reqDTO.setNoOfBackoutApplicants(String.valueOf(rsCount.getBackoutCount()));
			}
			
			return requirementDTOList;
		}

	

	@RequestMapping(value = "/getVendorRequirementReports")
	public @ResponseBody String getVendorRequirementReports(@RequestParam(value="reportType", required=false) String reportType,
			@RequestParam(value="customStartDate", required=false) String customStartDate, @RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {
		
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    
	    
	    Date[] searchDates = null;
	    Date startDateUTC = null;
	    Date endDateUTC = null;
		
		if(!"custom".equalsIgnoreCase(reportType)){
			searchDates = reportsUtil.getJobFilterDate(reportType,orgLocations);
			startDateUTC =searchDates[0];
			endDateUTC = searchDates[1];
		}else if("custom".equalsIgnoreCase(reportType) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
    	    
    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
			DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			
			Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
			Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
			String customStartTimeStr = TZ_DF_CSD.format(startTime);  
			String customEndTimeStr = TZ_DF_CED.format(endTime);  
			 startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
			 endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			 startDateUTC = new java.sql.Date(startDateUTC.getTime());
			 endDateUTC = new java.sql.Date(endDateUTC.getTime());
		}
	    
	    List<OrgRelations> vendorslist = orgRelationService.getAllVendorsListByOrgLocationId(orgLocations.getId());
	    
	    for(OrgRelations orgrel : vendorslist){
	    	int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
			long vendorLocationId = 0L;
	    	List<Requirement> reqList = requirementService.getAllReleasedJobsForVendorBasedOnStartAndEndDates(orgLocations.getId(), orgrel.getId(),startDateUTC,endDateUTC);
	    	if(orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
	    		vendorLocationId = orgrel.getLocationId().getId();
			}if(orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				vendorLocationId = orgrel.getResponderLocationId().getId();
			}
	    	List<RequirementSubmission> reqSubmissionList = requirementSubmissionService.getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(orgLocations.getId(),vendorLocationId,startDateUTC,endDateUTC);
	    	orgrel.setSubmittedCount(reqSubmissionList.size());
	    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
		    	for(RequirementSubmission reqSub : reqSubmissionList){
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;
					int backoutCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							if("submission".equalsIgnoreCase(rsh.getToStage())){
								applicantsCount++;
							}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
								shortlistedCount++;
							}else if("interview".equalsIgnoreCase(rsh.getToStage())){
								interviewsCount++;
							}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
								offeredCount++;
							}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
								requestSentCount++;
							}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
								submitToClientCount++;
							}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
								rejectedCount++;
							}else if("backout".equalsIgnoreCase(rsh.getToStage())){
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
						 backoutSum = backoutSum + backoutCount;
					}
				}
	    	}
	    	
	    	orgrel.setReqReleasedCount(reqList.size());
	    	if(orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
	    		orgrel.setVendorName(orgrel.getLocationId().getOrgLocationName());
			}if(orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				orgrel.setVendorName(orgrel.getResponderLocationId().getOrgLocationName());
			}
	    	orgrel.setShortlistedCount(shortlistedSum);
	    	orgrel.setInterviewedCount(interviewsSum);
	    	orgrel.setOfferedCount(offeredSum);
	    	orgrel.setRejectedCount(rejectedSum);
	    	orgrel.setBackoutCount(backoutSum);
	    }
	    
		return commonUtil.toJSON(vendorslist);
	}
	
	
	@RequestMapping(value = "/getVendorSpocsReports")
	public @ResponseBody String getVendorSpocsReports(@RequestParam(value="reportType", required=false) String reportType,@RequestParam(value="relationId", required=false) Long relationId,
			@RequestParam(value="customStartDate", required=false) String customStartDate, @RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {
		
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    
	    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();

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
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}
	    
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(relationId, loginUser.getId(),orgLocations.getId(),"Vendor");
		
//	    List<OrgSpoc> vendorSpocsList = orgSpocService.getSpoclistByDomain(relationId,"");
	    for(OrgSpoc spoc : orgSpocsList){
	    	int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
	    	List<Requirement> reqList = requirementService.getTotalRequirementsReleasedToSpoc(relationId,spoc.getId(),startDateUTC,endDateUTC);
	    	spoc.setReqReleasedCount(reqList.size());
	    	   List<RequirementSubmission> reqSubmissionList = requirementSubmissionService.getVendorSpocSubmissions(spoc.getVendorUser().getUserCode(),orgLocations.getId(),startDateUTC,endDateUTC);
	    	   spoc.setSubmittedCount(reqSubmissionList.size());
		    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
			    	for(RequirementSubmission reqSub : reqSubmissionList){
						int applicantsCount = 0;
						int requestSentCount = 0;
						int shortlistedCount = 0;
						int submitToClientCount = 0;
						int interviewsCount = 0;
						int offeredCount = 0;
						int rejectedCount = 0;
						int backoutCount = 0;
						List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
						if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
							for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
								if("submission".equalsIgnoreCase(rsh.getToStage())){
									applicantsCount++;
								}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
									shortlistedCount++;
								}else if("interview".equalsIgnoreCase(rsh.getToStage())){
									interviewsCount++;
								}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
									offeredCount++;
								}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
									requestSentCount++;
								}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
									submitToClientCount++;
								}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
									rejectedCount++;
								}else if("backout".equalsIgnoreCase(rsh.getToStage())){
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
							 backoutSum = backoutSum + backoutCount;
						}
					}
		    	}
		    	if(null != spoc.getVendorUser().getLastName()){
		    		StringBuilder sb = new StringBuilder();
		    		spoc.setSpocName(sb.append(spoc.getVendorUser().getFirstName()).append(" ").append(spoc.getVendorUser().getLastName()).toString());
		    	}else{
		    		spoc.setSpocName(spoc.getVendorUser().getFirstName());
		    	}
		    	spoc.setShortlistedCount(shortlistedSum);
		    	spoc.setInterviewedCount(interviewsSum);
		    	spoc.setOfferedCount(offeredSum);
		    	spoc.setRejectedCount(rejectedSum);
		    	spoc.setBackoutCount(backoutSum);
	    }
	    
		return commonUtil.toJSON(orgSpocsList);
	}
	
	
	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}
	
	
	private String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@RequestMapping(value ="/userUsageReportList")
	public @ResponseBody ResponseList<UsersUsageReportDTO> userUsageReportList(UsersUsageReportDTO usersUsageReportDTO,@RequestParam (value = "orgLocationId", required = false)Long orgLocationId,@RequestParam (value = "customDates", required = false)String customDates, HttpServletRequest request)throws Exception{
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		usersUsageReportDTO.setId(loginUser.getId());
	ResponseList<UsersUsageReportDTO> userCount = userRoleService.getUserRoleListByLocationIdAndUserType(usersUsageReportDTO,orgLocations);
	
	return userCount;
	
	}
	
	@RequestMapping(value ="/getCountList")
	public @ResponseBody UserDTO getCountList(@RequestParam (value = "orgLocationId", required = false)Long orgLocationId,@RequestParam (value = "orgId", required = false)List<Long> orgId, HttpServletRequest request)throws Exception{
		Map<String, Integer> count = userRoleService.getUserCountListByOrgLocationId(orgLocationId, orgId);
		
		UserDTO usDTO = new UserDTO();
		
		usDTO.setActiveCount(count.get("ACTIVE"));
		usDTO.setInactiveCount(count.get("INACTIVE"));
		usDTO.setNotLoggedinCount(count.get("NOT_LOGGEDIN"));
		usDTO.setMultipleIps(count.get("MULTIPLE_IPS"));
		
		
		return usDTO;
		
	}
	
	@RequestMapping (value ="/getAllOrgs")
	public @ResponseBody List<Org> getAllOrgs(HttpServletRequest request)throws Exception{
		List<Org> orgList = orgService.getOrgs();
		
		return orgList;
		
	}
	
	@RequestMapping (value ="/blockActiveUser")
	public @ResponseBody String blockActiveUser(@RequestParam (value = "orgLocationId", required = false)Long orgLocationId,@RequestParam (value = "userRoleId", required = false)Long userRoleId,HttpServletRequest request)throws Exception{
		
		UserRole userRole =  userRoleService.getUserRoleById(userRoleId);
		userRole.setUserState(Status.INACTIVE);
		userRoleService.addUserRole(userRole);
		return "success";
		
	}
	
	@RequestMapping (value ="/multipleIpsList")
	public @ResponseBody ResponseList<UsersUsageReportDTO> multipleIpsList(UsersUsageReportDTO usersUsageReportDTO, HttpServletRequest request)throws Exception{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		ResponseList<UsersUsageReportDTO> multipleIps = userLoginLogoutSessionService.getMultipleIps(usersUsageReportDTO,orgLocations);
		return multipleIps;
		
	}
	
	@RequestMapping (value = "/getUserUsageReportExportList")
	public void getUserUsageReportExportList(UsersUsageReportDTO usersUsageReportDTO,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//		usersUsageReportDTO.setId(loginUser.getId());
		ResponseList<UsersUsageReportDTO>  usageList = userRoleService.getUserRoleListByLocationIdAndUserType(usersUsageReportDTO,orgLocations);
	
	File file = getExportUsageReport(usageList,orgLocations,usersUsageReportDTO);

	String mimeType = URLConnection.guessContentTypeFromName(file.getName());
	if (mimeType == null) {
	mimeType = "application/octet-stream";
	}
	response.setContentType(mimeType);
	StringBuilder fileName = new StringBuilder();
	response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
	response.setContentLength((int) file.length());
	InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	FileCopyUtils.copy(inputStream, response.getOutputStream());
	inputStream.close();
	response.getOutputStream().close();
	response.getOutputStream().flush();	
	if(file != null){
	file.delete();
	}
}
	
	@RequestMapping (value = "/viewCountsList")
	public @ResponseBody UserDTO viewCountsList (UsersUsageReportDTO usersUsageReportDTO,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		UserDTO userViewCount = actionHistoryService.getAllActionHistoryByUserId(usersUsageReportDTO.getUserId());
		UserDTO userDTO = new UserDTO();
		
//		UserDTO actionHistory = actionHistoryService.getAllActionHistoryByUserId(user.getId());
		userDTO.setNoOfActivities(userViewCount.getNoOfActivities());
		userDTO.setLastActivity(userViewCount.getLastActivity());
		
//		if(StringUtils.isNotBlank(userViewCount.getLastActivity())){
//			userDTO.setLastActivity(userViewCount.getLastActivity());
//		}
		
		return userViewCount;
	}
	
	@RequestMapping (value = "/ipCountDetails")
	public @ResponseBody UserDTO ipCountDetails (UsersUsageReportDTO usersUsageReportDTO,HttpServletRequest request,HttpServletResponse response)throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 UserDTO udTO = userLoginLogoutSessionService.getUsersListByIpAddress(usersUsageReportDTO.getIpAddress(),usersUsageReportDTO.getOrgId(),orgLocations.getId());
//		 UserDTO activitiesCount = actionHistoryService.getAllActionHistoryByUserId(usersUsageReportDTO.getIpAddress());
		 
		 udTO.setNoOfActivities(String.valueOf(udTO.getNoOfActivities()));
		 udTO.setTotalNoOfUsers(udTO.getTotalNoOfUsers());
		return udTO;
		
	}
	
	
	
	@RequestMapping (value = "/multipleIpsUsersList")
		public @ResponseBody ResponseList<UsersUsageReportDTO>multipleIpsUsersList (UsersUsageReportDTO usersUsageReportDTO,@RequestParam (value = "ipAddress", required = false)String ipAddress,HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		ResponseList<UsersUsageReportDTO> userLoginLogout = userLoginLogoutSessionService.getUsersByipAddress(usersUsageReportDTO);
		return userLoginLogout;
	}
	
	@RequestMapping (value = "/userUsageGraph")
	public @ResponseBody List<UsersUsageReportDTO> userUsageGraph(UsersUsageReportDTO usersUsageReportDTO,HttpServletRequest request)throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<UsersUsageReportDTO> count = userRoleService.getUsageReportUserCountListByOrgLocationId(usersUsageReportDTO.getLocationId(), usersUsageReportDTO,orgLocations);
	
//	UsersUsageReportDTO useDTO = new UsersUsageReportDTO();
//	
//	useDTO.setActiveCount(count.get("ACTIVE"));
//	useDTO.setInactiveCount(count.get("INACTIVE"));
//	useDTO.setNotLoggedinCount(count.get("NOT_LOGGEDIN"));
//	useDTO.setMultipleIps(count.get("MULTIPLE_IPS"));
	
	
	return count;
	}


	private File getExportUsageReport(ResponseList<UsersUsageReportDTO> usageList, OrgLocations orgLocations, UsersUsageReportDTO usersUsageReportDTO) throws Exception
	{
		File file = null;
		if (!"pdf".equalsIgnoreCase(usersUsageReportDTO.getExportType())) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			
			Sheet createExcelsheet = createExcelworkbook.createSheet(orgLocations.getOrgLocationName() + "_"
					+ commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, usageList, orgLocations);
			int column = 0;
			for (UsersUsageReportDTO usageExcel : usageList.getData()) {
				writeRow = createExcelsheet.createRow(column + 1);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, usageExcel, usageList, orgLocations);
				column++;
			
			file = File.createTempFile(orgLocations.getOrgLocationName() + Constants.ACTIVE_USAGE_LIST + commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date()),".xls");
			 file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}else{
			file = getUsageReportPdf(usageList,orgLocations);
		}
			return file;
	}
		
		private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, UsersUsageReportDTO usageExcel, ResponseList<UsersUsageReportDTO> usageList, OrgLocations orgLocations)
	{
			
			if(null != usageExcel.getUserName() && !"".equalsIgnoreCase(usageExcel.getUserName())){
				writeRow.createCell(0).setCellValue(usageExcel.getUserName());
			}else{
				writeRow.createCell(0).setCellValue("--");
			}
			
			if( null != usageExcel.getUserEmail() && !"".equalsIgnoreCase(usageExcel.getUserEmail())){
				writeRow.createCell(1).setCellValue(usageExcel.getUserEmail());
			}else{
				writeRow.createCell(1).setCellValue("--");
			}
			
			if(null != usageExcel.getUserType() && !"".equalsIgnoreCase(usageExcel.getUserType())){
				writeRow.createCell(2).setCellValue(usageExcel.getUserType());
			}else{
				writeRow.createCell(2).setCellValue("--");
			}
			
			if(usageExcel.getEnrolledOrgs() != null){
				for (UserRole role : usageExcel.getEnrolledOrgs()) {
					writeRow.createCell(3).setCellValue(role.getOrgName());
				}
			}else{
				writeRow.createCell(3).setCellValue("--");
			}
			
			if(null != usageExcel.getNoOfActivities() && !"".equalsIgnoreCase(usageExcel.getNoOfActivities())){
				writeRow.createCell(4).setCellValue(usageExcel.getNoOfActivities());
			}else{
				writeRow.createCell(4).setCellValue("--");
			}
			
			writeRow.createCell(5).setCellValue(usageExcel.getMultipleIps());
			
			if(null != usageExcel.getCreatedOn() && !"".equalsIgnoreCase(usageExcel.getCreatedOn())){
				writeRow.createCell(6).setCellValue(usageExcel.getCreatedOn());
			}else{
				writeRow.createCell(6).setCellValue("--");
			}
				
			if(null != usageExcel.getLastActivity() && !"".equalsIgnoreCase(usageExcel.getLastActivity())){
				writeRow.createCell(7).setCellValue(usageExcel.getLastActivity());
			}else{
				writeRow.createCell(7).setCellValue("--");
			}
			
			return createExcelsheet;
	}



		private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, ResponseList<UsersUsageReportDTO> usageList, OrgLocations orgLocations)
	{

			CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

			writeRow = createExcelsheet.createRow(1);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			
			String[] headers  = Constants.USERS_USAGE_REPORT_EXPORT;
			createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
			return createExcelsheet;
	}



		private File getUsageReportPdf(ResponseList<UsersUsageReportDTO> usageList, OrgLocations orgLocations) throws Exception
	{
			String[] headers = Constants.USERS_USAGE_REPORT_EXPORT;
			Document document = new Document(PageSize.LETTER.rotate());

			StringBuilder fileSB = new StringBuilder();
			File file = File.createTempFile(fileSB.append(orgLocations.getOrgLocationName()).append(Constants.ACTIVE_USAGE_LIST).append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");

			file.setReadable(true);
			try {
				// Get an instance of PdfWriter and create a Table.pdf file as an
				// output.
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

				BaseColor myBordColor = WebColors.getRGBColor("#000000");
				boldFont.setColor(myBordColor);

				StringBuilder orgNameSB = new StringBuilder();
				Paragraph para = new Paragraph(orgNameSB.append(orgLocations.getOrgLocationName()).append(" ").append(Constants.ACTIVE_USAGE_LIST).toString(),
						boldFont);

				para.setAlignment(Element.ALIGN_CENTER);
				document.add(para);


				PdfPTable table = new PdfPTable(headers.length);
				Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
				PdfPCell cell1 = new PdfPCell(new Phrase(Constants.ACTIVE_USAGE_LIST, font));;
				cell1.setColspan(8);
				headerCellStyle(cell1);
				table.setWidthPercentage(100);
				table.setWidths(new int[] { 3, 5, 3, 5, 5, 3, 5, 3});
				table.addCell(cell1);
				for (int i = 0; i < headers.length; i++) {
					String header = headers[i];
					PdfPCell cell = new PdfPCell();
					cell.setGrayFill(0.9f);
					cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
					BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
					cell.setBackgroundColor(myColor);
					table.addCell(cell);
				}
				table.completeRow();
				java.util.List<String[]> leaves = new ArrayList<>();
				for (UsersUsageReportDTO usagePdf : usageList.getData()) {
					leaves.add(prepareBillingToArray(usagePdf,orgLocations));
				}

				for (String[] leaveArray : leaves) {
					for (int i = 0; i < leaveArray.length; i++) {
						String datum = leaveArray[i];
						PdfPCell cell = new PdfPCell();
						cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
						table.addCell(cell);
					}
					table.completeRow();
				}

				document.addTitle("Leaves List Table");
				table.setSpacingBefore(15f);
				document.add(table);
				logger.info("Pdf table created !!");
			} catch (DocumentException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} catch (FileNotFoundException e) {
				logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
			} finally {
				document.close();
			}
			return file;
	}



		private String[] prepareBillingToArray(UsersUsageReportDTO usagePdf, OrgLocations orgLocations)
		{
			String billingArray[] = new String[Constants.USERS_USAGE_REPORT_EXPORT.length];
			
			if(usagePdf.getUserName() != null && !"".equalsIgnoreCase(usagePdf.getUserName())){
				billingArray[0] = usagePdf.getUserName();
			}else{
				billingArray[0] = "--";
			}
			
			if(usagePdf.getUserEmail() != null && !"".equalsIgnoreCase(usagePdf.getUserEmail())){
				billingArray[1] = usagePdf.getUserEmail();
			}else{
				billingArray[1] = "--";
			}
			
			if(usagePdf.getUserType() != null && !"".equalsIgnoreCase(usagePdf.getUserType())){
				billingArray[2] = usagePdf.getUserType();
			}else{
				billingArray[2] = "--";
			}
//			List<UserRole> userRoleList =  userRoleDao.getUsersListByUserId(user.getId());
//			for(UserRole ur:userRoleList){
//				ur.setOrgName(ur.getOrgLocations().getOrgLocationName());
//			}
//			
			if(usagePdf.getEnrolledOrgs() != null){
				for (UserRole role : usagePdf.getEnrolledOrgs()) {
					billingArray[3] = role.getOrgName();
				}
			}else{
				billingArray[3] = "--";
			}
			
			if(usagePdf.getNoOfActivities() != null && !"".equalsIgnoreCase(usagePdf.getNoOfActivities())){
				billingArray[4] = usagePdf.getNoOfActivities();
			}else{
				billingArray[4] = "--";
			}
			
				billingArray[5] = String.valueOf(usagePdf.getMultipleIps());
			
			if(usagePdf.getCreatedOn() != null && !"".equalsIgnoreCase(usagePdf.getCreatedOn())){
				billingArray[6] = usagePdf.getCreatedOn();
			}else{
				billingArray[6] = "--";
			}
			
			if(usagePdf.getLastActivity() != null && !"".equalsIgnoreCase(usagePdf.getLastActivity())){
				billingArray[7] = usagePdf.getLastActivity();
			}else{
				billingArray[7] = "--";
			}
			
			return billingArray;
		}



		private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, UsersUsageReportDTO usageExcel)
	{
			
			return createExcelsheet;
	}



		private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook) {
			CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
			String[] headers  = Constants.TOP_TAKERS_LEAVE_HEADERS;
			createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
			return createExcelsheet;
		}
		
		private static void headerCellStyle(PdfPCell cell) {
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(0f);
			cell.setPaddingBottom(7f);
			cell.setBackgroundColor(new BaseColor(0, 121, 182));
			cell.setBorder(0);
			cell.setBorderWidthBottom(2f);
		}
		
		
		
		@RequestMapping(value = "/getAllEmployeesList", method = RequestMethod.GET)
		public @ResponseBody  ResponseList<UserDTO> getAllEmployeesList(UserDTO userDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserDTO> userDtoList =  new ArrayList<>();
			ResponseList<UserDTO> employeeResponseList =  new ResponseList<>();
			Date[] searchDates = null;
			if(StringUtils.isNotBlank(userDTO.getFromDate()) && StringUtils.isNotBlank(userDTO.getToDate())) {
				Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), userDTO.getFromDate());
	    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), userDTO.getToDate());
	    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
	    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
	    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
	    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
	    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
	    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
	    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
	    	    fromDate = TZ_DF_TS.parse(startDateString);
	    	    toDate = TZ_DF_TS.parse(endDateString);
	    	    searchDates = new Date[2];
	    	    searchDates[0] = fromDate;
	    	    searchDates[1] = toDate;
			}
			
    	    
			ResponseList<UserRole> userRoleList = userService.getUsersResponseListByOrglocationId(userDTO,orgLocation,searchDates);
			userRoleList.getData().forEach(userRole->{
				Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocation.getId());
				UserDTO userDto = new UserDTO();
				userDto.setEmployeeId(userRole.getId().toString());
				if(userRole.getUser().getLastName() != null) {
					StringBuilder sb = new StringBuilder();
					userDto.setUserName(sb.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName()).toString());
				}else {
					userDto.setUserName(userRole.getUser().getFirstName());
				}
				userDto.setEmail(userRole.getUser().getEmail());
				userDto.setMobilePhone(userRole.getUser().getMobilePhone());
				
				if(employee.getHireDate() != null)
				userDto.setDateofJoin(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),employee.getHireDate()));
				
				if(userRole.getUserState() != null) {
					if("Inactive".equalsIgnoreCase(userRole.getUserState().toString())) {
						userDto.setDateOfTermination(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),userRole.getTerminateDate()));
					}else {
						userDto.setDateOfTermination("Till Date");
					}
				}
				if(employee.getUserEmploymentType() != null)
				userDto.setEmploymentType(employee.getUserEmploymentType());
				
				if(userRole.getUser().getUserCode() != null)
				userDto.setUserCode(userRole.getUser().getUserCode());

				if(userRole.getDesignation() != null)
				userDto.setDesignation(userRole.getDesignation());
				userDtoList.add(userDto);
			});
			employeeResponseList.setData(userDtoList);
			employeeResponseList.setRecordsFiltered(userDtoList.size());
			employeeResponseList.setRecordsTotal(userDtoList.size());
			return employeeResponseList;
		}
		
		@RequestMapping(value = "/previewCompleteTemplateByTemplateUUID", method = RequestMethod.GET)
		public @ResponseBody  void previewCompleteTemplateByTemplateId(@RequestParam("templateUUID") String templateUUID,@RequestParam("toAddress") String toAddress,@RequestParam("employeeId") Long employeeId,HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			try {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				EmployeeVerificationTemplate empTemplate = employeeVerificationTemplateService.getEmploymentVerificationtemplateByTemplateUUID(templateUUID);
				LetterHead letterHead = letterHeadService.getLetterheadById(empTemplate.getLetterHead().getId());
				User user = userService.getUserById(employeeId);
				String templateContent = reportsUtil.replaceAllTemplatePlaceHolders(empTemplate.getTemplateContent(),empTemplate,toAddress,user,orgLocation);
				reportsUtil.viewCompleteletterheadData(templateContent,letterHead,response);
			    
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		
		@RequestMapping(value = "/ws/dumpAllReplaceIpAddress", method = RequestMethod.GET)
	    public boolean getRestActionHistoryList(HttpServletRequest request, HttpServletResponse response) throws Exception
	    {
	        boolean isInserted = false;
	        
	        List<UserLoginLogoutSession> ipList = userLoginLogoutSessionService.getAll();
	         for(UserLoginLogoutSession uls :ipList){
	            		String ipAddress = uls.getIpaddress();
		            	String[] ipArray = ipAddress.split(",");
		        		if(ipArray.length > 1){
		        			for(String s :ipArray){
		        				if(StringUtils.isNotEmpty(s)){
		        					ipAddress =s.replaceAll("[^0-9.]", "");
		        					break;
		        				}
		        			}
		        			
		        		}else{
		        			ipAddress = ipAddress.replaceAll("[^0-9.]", "");
		        		}
		        		uls.setIpaddress(ipAddress);
		        		userLoginLogoutSessionService.storeUserLoginLogoutSession(uls);
	        }
	        System.out.println("End Update Ip Addrss");
	        return isInserted;
	    }

		
		/*@RequestMapping(value = "/getAllApplicantJoinedListByOrgLocationsId")
		public @ResponseBody ResponseList<UserDTO> getAllApplicantJoinedListByOrgLocationsId(UserRoleDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
				UserDTO userDTO,HttpServletRequest request) throws Exception {

		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		    
				
				Date[] searchDates = null;
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					List<Long> teamsList = new ArrayList<>();
					if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
						String[] orgTeams = userRoleDto.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}
					List<Long> clientIdsList = new ArrayList<>();
					if(userRoleDto.getClients() != null && !userRoleDto.getClients().isEmpty()) {
						String[] clients = userRoleDto.getClients().split(",");
						for(int i=0; i < clients.length; i++) {
							clientIdsList.add(Long.parseLong(clients[i]));
						}
					}
					String type=null;
				if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
					type=userRoleDto.getType();
				}
				String[] tableColumns = { "recruiterName", "noOfJobs", "totalOffers", "totalJoined", "incentives"};
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userDTO.getOrder().get(0).get("dir");
				}
				userDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userDTO.setSortOrder(sortColumnOrder);
				ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,clientIdsList);
				return userDTOList;
			}*/
		
		
		
		@RequestMapping(value = "/getAllApplicantJoinedListByOrgLocationsId")
		public @ResponseBody ResponseList<ApplicantsJoinedReportDTO> getAllApplicantJoinedListByOrgLocationsId(ApplicantsJoinedReportDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
				ApplicantsJoinedReportDTO userDTO,HttpServletRequest request) throws Exception {
				ResponseList<ApplicantsJoinedReportDTO> responseList = new ResponseList<>();
		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		    
				
				Date[] searchDates = null;
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					List<Long> teamsList = new ArrayList<>();
					if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
						String[] orgTeams = userRoleDto.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}
					List<Long> clientIdsList = new ArrayList<>();
					if(userRoleDto.getClients() != null && !userRoleDto.getClients().isEmpty()) {
						String[] clients = userRoleDto.getClients().split(",");
						for(int i=0; i < clients.length; i++) {
							clientIdsList.add(Long.parseLong(clients[i]));
						}
					}
					String type=null;
				if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
					type=userRoleDto.getType();
				}
				String[] tableColumns = { "name", "requirementCount", "offerCount", "joinedCount", "incentives"};
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userDTO.getOrder().get(0).get("dir");
				}
				userDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userDTO.setSortOrder(sortColumnOrder);

				ResponseList<ApplicantsJoinedReportDTO> userDTOList = userService.getApplicantsJoinedReport(userRoleDto,orgLocations.getId(),searchDates,teamsList,type,clientIdsList);

//				ResponseList<ApplicantsJoinedReportDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,clientIdsList);
//				responseList.setData(userDTOList);
				return userDTOList;
			}
		
		
		
		@RequestMapping(value = "/exportAllApplicantJoinedList")
		public void exportAllApplicantJoinedList(ApplicantsJoinedReportDTO userRoleDto,@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
				@RequestParam(value="exportType", required=false) String exportType,
				UserDTO userDTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		      Workbook createExcelworkbook =  new XSSFWorkbook();
		      StringBuilder excelSB = new StringBuilder();
				
				Date[] searchDates = null;
				SimpleDateFormat sdf = new SimpleDateFormat(orgLocations.getDateFormat());
				
					if(!"custom".equalsIgnoreCase(timePeriod)){
						searchDates = reportsUtil.getJobFilterDate(timePeriod,orgLocations);
					}else if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
			    	    
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						
						Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customStartDate);
						Date endTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						Date startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
						Date endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
			    	   
			    	    searchDates = new Date[2];
			    	    searchDates[0] = startDateUTC;
			    	    searchDates[1] = endDateUTC;
					}
					List<Long> teamsList = new ArrayList<>();
					if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
						String[] orgTeams = userRoleDto.getOrgTeams().split(",");
						for(int i=0; i < orgTeams.length; i++) {
							teamsList.add(Long.parseLong(orgTeams[i]));
						}
					}
					List<Long> clientIdsList = new ArrayList<>();
					if(userRoleDto.getClients() != null && !userRoleDto.getClients().isEmpty()) {
						String[] clients = userRoleDto.getClients().split(",");
						for(int i=0; i < clients.length; i++) {
							clientIdsList.add(Long.parseLong(clients[i]));
						}
					}
					String type=null;
				if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
					type=userRoleDto.getType();
				}
		         Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("applicantsjoioinedList ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString());
//				ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,clientIdsList);
				ResponseList<ApplicantsJoinedReportDTO> responseList = userService.getApplicantsJoinedReport(userRoleDto,orgLocations.getId(),searchDates,teamsList,type,clientIdsList);
		
		if (responseList.getData() != null && !responseList.getData().isEmpty()) {
			for (ApplicantsJoinedReportDTO ajrDTO : responseList.getData()) {
				Set<String> datesSet = new LinkedHashSet<>();
				if (timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
					datesSet.add("Date<<>>" + sdf.format(Calendar.getInstance().getTime()));
				} else if (timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_15_DAYS)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_1_MONTH)) {
					Map<String, Long> datesMap = userService.getApplicantsJoinedCountByDates(ajrDTO.getCreatedBy(), searchDates[0], searchDates[1], orgLocations.getDateFormat());
					Calendar c = Calendar.getInstance();
					c.setTime(searchDates[0]);
					while (c.getTime().before(searchDates[1])) {
						String cDate = sdf.format(c.getTime());
						datesSet.add(cDate + "<<>>" + (datesMap.containsKey(cDate) ? datesMap.get(cDate) : "0"));
						c.add(Calendar.DATE, 1);
					}
				} else if (timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_3_MONTHS)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_YEAR)
						|| timePeriod.equalsIgnoreCase(Constants.REPORT_FILTER_YTD)
						|| "custom".equalsIgnoreCase(timePeriod)) {
					Map<String, Long> datesMap = userService.getApplicantsJoinedCountByDates(ajrDTO.getCreatedBy(), searchDates[0], searchDates[1], orgLocations.getDateFormat());
					Map<String, Long> datesMapNew = new HashMap<>();
					SimpleDateFormat monthsSDF = new SimpleDateFormat("MMMM");
					for(String date : datesMap.keySet()) {
						String date1 = monthsSDF.format(new Date(sdf.parse(date).getTime()));
						long value = 0;
						if(datesMapNew.containsKey(date1)) {
							value = datesMapNew.get(date1);
						}
						value += datesMap.get(date);
						datesMapNew.put(date1, value);
					}
//					LinkedHashSet<String> months = CalendarUtil.getMonthStringsFromStartDateAndEndDate(searchDates[0], searchDates[1]);
					Calendar fromC = Calendar.getInstance();
		            fromC.setTime(searchDates[0]);
		            Calendar toC = Calendar.getInstance();
		            toC.setTime(searchDates[1]);
		            Set<String> months = new LinkedHashSet<>();
		            while (fromC.get(Calendar.MONTH) <= toC.get(Calendar.MONTH)) {
		                System.out.println(fromC.get(Calendar.MONTH) + " " + monthsSDF.format(fromC.getTime()));
		                months.add(monthsSDF.format(fromC.getTime()));
		                fromC.roll(Calendar.MONTH, true);
		            }
					for(String month : months) {
						if(datesMapNew.containsKey(month)) {
							datesSet.add(month + "<<>>" + datesMapNew.get(month));
						} else {
							datesSet.add(month + "<<>>0");
						}
					}
				} else {
					// custom dates
				}
				ajrDTO.setDatesList(datesSet);
			}
		}
				
				
				File file = null;
	             if(!"pdf".equalsIgnoreCase(exportType)){
	                 createExcelsheet = userService.exportAllApplicantJoinedList(createExcelsheet,createExcelworkbook,responseList,orgLocations);
	                   StringBuilder templateSB = new StringBuilder();
	                    file = File.createTempFile(templateSB.append("Applicants_Joioined_List").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
	                    file.setReadable(true);
	                    
	                  FileOutputStream fos = new FileOutputStream(file);
	                  try {
	                     createExcelworkbook.write(fos);
	                     fos.close();
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }         
	             }
	             else{
	                 file = userService.exportAllApplicantJoinedListPdf(responseList,orgLocations,searchDates);
	             
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
	                }			}
}

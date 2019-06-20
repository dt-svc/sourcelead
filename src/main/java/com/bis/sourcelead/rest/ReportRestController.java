package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.AssociateJoineReportDTO;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AvailableAssociatesDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.MarginSheetReportDTO;
import com.bis.sourcelead.dto.OfferAcceptanceRateDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RecruiterJobsReportView;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.reports.service.LoanAndAdvancesReportService;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
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
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;
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
    private RequirementInterviewService requirementInterviewService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementOfferHistoryService requirementOfferHistoryService;
	
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
	private RequirementSubmissionDao requirementSubmissionDao;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	@Autowired
	private LetterHeadService letterHeadService;
	
	@Autowired
	private RequirementLocationsService requirementLocationsService;
	
	@Autowired
	private EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	@Autowired 
	OrgLocationService orgLocationService;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
	@Autowired
	DynamicReport dynamicReport;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private RequirementOffersService requirementOffersService;
	
	@Autowired
	private LoanAndAdvancesReportService loanAndAdvancesReportService;
	
	@RequestMapping(value="/updateSourceUserCodeForSubmissionHistory")
	public String updateSourceUserCodeForSubmissionHistory() {
		int count = requirementSubmissionHistoryService.updateSourceUserCodeForSubmissionHistory();
		return count + " Records updated";
	}
	
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
				//Roles List
				List<Long> rolesList = new ArrayList<>();
				if(userRoleDto.getUserRoleName() != null && !userRoleDto.getUserRoleName().isEmpty()) {
					String[] roleNames = userRoleDto.getUserRoleName().split(",");
					for(int i=0; i < roleNames.length; i++) {
						rolesList.add(Long.parseLong(roleNames[i]));
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
			ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,rolesList);
			return userDTOList;
		}
	
	
	@RequestMapping(value = "/getAllRecruiterReportsExport")
	public File getAllRecruiterReportsExport(UserRoleDTO userRoleDto,UserDTO userDTO,
			@RequestParam(value="timePeriod", required=false) String timePeriod,
			@RequestParam(value="customStartDate", required=false) String customStartDate,
			@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request,HttpServletResponse response) throws Exception {

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
				String[] orgTeams = null;
				List<Long> teamsList = new ArrayList<>();
				if(userRoleDto.getOrgTeams() != null && !userRoleDto.getOrgTeams().isEmpty()) {
				   orgTeams = userRoleDto.getOrgTeams().split(",");
					for(int i=0; i < orgTeams.length; i++) {
						teamsList.add(Long.parseLong(orgTeams[i]));
					}
				}
				//Roles List
				String[] roleNames = null;
				List<Long> rolesList = new ArrayList<>();
				if(userRoleDto.getUserRoleName() != null && !userRoleDto.getUserRoleName().isEmpty()) {
				   roleNames = userRoleDto.getUserRoleName().split(",");
					for(int i=0; i < roleNames.length; i++) {
						rolesList.add(Long.parseLong(roleNames[i]));
					}
				}
				String type=null;
			if(userRoleDto.getType() != null && !userRoleDto.getType().isEmpty()){
				type=userRoleDto.getType();
			}
			ResponseList<UserDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,rolesList);
			
			StringBuilder sbRoles = new StringBuilder();
			String roleName = null;
			if(roleNames !=null){
			List<Roles> roles = rolesService.getRolesListUsingMultipleIds(roleNames);
			for(Roles r: roles){
				sbRoles.append(r.getDisplayName()).append(",").toString();
			}
			roleName = sbRoles.deleteCharAt(sbRoles.length()-1).toString();
			}
			
			StringBuilder sbTeams = new StringBuilder();
			String teamName = null;
			if(orgTeams !=null){
			List<Team> teams = teamService.getTeamListByMultipleIds(orgTeams,orgLocations.getId());
			for(Team t: teams){
				sbTeams.append(t.getName()).append(",").toString();
			}
			teamName = sbTeams.deleteCharAt(sbTeams.length()-1).toString();
			}
			
			File file = null;
				file = pdfUtil.recruiterReprtsListPdfTable(userDTOList, orgLocations,searchDates,roleName,teamName);
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);

			StringBuilder filename = new StringBuilder();
			response.setHeader("Content-Disposition",
					filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
			if (file != null) {
				file.delete();
			}
			return file;
	}
	
	
	@RequestMapping(value = "/getAllJobsByUserIdAndOrgLocationId")
	public @ResponseBody ResponseList<RequirementDTO> getAllJobsByUserIdAndOrgLocationId(RequirementDTO requirementDTO, @RequestParam(value="recruiterUserId", required=false) Long recruiterUserId, @RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
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
				List<RecruiterJobsReportView> rjrvList = requirementSubmissionHistoryService.getSubmissionApplicantCountsForRecruiterJobreports(orgLocations.getId(), recruiterUser, searchDates, reqDTO.getId());
				
				double  applicantSubmittedApplicantCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantSubmissionCount().doubleValue()));
				double  applicantRejectedCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantRejectedCount().doubleValue()));
				double  applicantShortlistCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantShortlistedCount().doubleValue()));
				double  applicantSubmittedCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantSubmittedClientCount().doubleValue()));
				double  applicantInterviewCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantInterviewCount().doubleValue()));
				double  applicantOfferCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantOfferMadeCount().doubleValue()));
				double  applicantJoinedCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantJoinedCount().doubleValue()));
				double  applicantBackoutCount = rjrvList.stream().collect(Collectors.summingDouble(product->product.getApplicantBackOutCount().doubleValue()));

				reqDTO.setNoOfApplicants(String.valueOf(applicantSubmittedApplicantCount));
				reqDTO.setNoOfShortlistApplicants(String.valueOf(applicantShortlistCount));
				reqDTO.setNoOfSubmittedApplicants(String.valueOf(applicantSubmittedCount));
				reqDTO.setNoOfInterviewedApplicants(String.valueOf(applicantInterviewCount));
				reqDTO.setNoOfOfferedApplicants(String.valueOf(applicantOfferCount));
				reqDTO.setNoOfJoinedApplicants(String.valueOf(applicantJoinedCount));
				reqDTO.setNoOfRejectedApplicants(String.valueOf(applicantRejectedCount));
				reqDTO.setNoOfBackoutApplicants(String.valueOf(applicantBackoutCount));
				
			}
			
			return requirementDTOList;
		}
	
	
	@RequestMapping(value = "/getAllCandidateListForRecruiterReports")
	public @ResponseBody ResponseList<Candidate> getAllCandidateListForRecruiterReports(CandidateDTO candidateDTO, 
			@RequestParam(value="userCode", required=false) String userCode, 
			@RequestParam(value="timePeriod", required=false) String timePeriod,
			@RequestParam(value="customStartDate", required=false) String customStartDate,
			@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
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
				/*List<Long> teamsList = new ArrayList<>();
				if(candidateDTO.getOrgTeams() != null && !candidateDTO.getOrgTeams().isEmpty()) {
					String[] orgTeams = candidateDTO.getOrgTeams().split(",");
					for(int i=0; i < orgTeams.length; i++) {
						teamsList.add(Long.parseLong(orgTeams[i]));
					}
				}*/
			
			String[] tableColumns = { "candidateName", "email", "phoneNumber", "empType", "location", "rate", "experience", "noOfJobs","resume", "createdOn"};
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (candidateDTO.getOrder() != null && !candidateDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(candidateDTO.getOrder().get(0).get("column"));
				sortColumnOrder = candidateDTO.getOrder().get(0).get("dir");
			}
			candidateDTO.setSortColumn(tableColumns[sortColumnIndex]);
			candidateDTO.setSortOrder(sortColumnOrder);
			candidateDTO.setOrgLocationId(orgLocations.getId());
			
			ResponseList<Candidate> list = candidateService.getAllCandidatesByCreatedByUserCode(candidateDTO,orgLocations,userCode,searchDates);
			
			return list;
		}
	
	@RequestMapping(value = "/getAllCandidateListForRecruiterReportsExport")
	public File getAllCandidateListForRecruiterReportsExport(CandidateDTO candidateDTO, 
			@RequestParam(value="userCode", required=false) String userCode, 
			@RequestParam(value="timePeriod", required=false) String timePeriod,
			@RequestParam(value="customStartDate", required=false) String customStartDate,
			@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request,HttpServletResponse response) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
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
			
			ResponseList<Candidate> list = candidateService.getAllCandidatesByCreatedByUserCode(candidateDTO,orgLocations,userCode,searchDates);
			File file = null;
			file = pdfUtil.recruiterReprtsCandidateListPdfTable(list, orgLocations,searchDates);
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);

		StringBuilder filename = new StringBuilder();
		response.setHeader("Content-Disposition",
				filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
		return file;
		}
	
	@RequestMapping(value = "/getAllSubmissionApplicantsForRecruiterReports")
	public @ResponseBody ResponseList<RequirementSubmissionHistory> getAllSubmissionApplicantsForRecruiterReports(RequirementSubmissionHistoryDTO sumissionHistoryDTO, 
			@RequestParam(value="userCode", required=false) String userCode, 
			@RequestParam(value="timePeriod", required=false) String timePeriod,
			@RequestParam(value="customStartDate", required=false) String customStartDate,
			@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
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
			
			String[] tableColumns = { "candidateName", "email", "jobTitle", "orgName", "stage"};
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (sumissionHistoryDTO.getOrder() != null && !sumissionHistoryDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(sumissionHistoryDTO.getOrder().get(0).get("column"));
				sortColumnOrder = sumissionHistoryDTO.getOrder().get(0).get("dir");
			}
			sumissionHistoryDTO.setSortColumn(tableColumns[sortColumnIndex]);
			sumissionHistoryDTO.setSortOrder(sortColumnOrder);
			
			ResponseList<RequirementSubmissionHistory> list = requirementSubmissionHistoryService.getAllSubmissionHistoryApplicantsByOwner(sumissionHistoryDTO,userCode,sumissionHistoryDTO.getToStage(),searchDates,orgLocations.getId());
			
			return list;
		}
	
	@RequestMapping(value = "/getAllSubmissionApplicantsForRecruiterReportsExport")
	public File getAllSubmissionApplicantsForRecruiterReportsExport(RequirementSubmissionHistoryDTO sumissionHistoryDTO, 
			@RequestParam(value="userCode", required=false) String userCode, 
			@RequestParam(value="timePeriod", required=false) String timePeriod,
			@RequestParam(value="customStartDate", required=false) String customStartDate,
			@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request,HttpServletResponse response) throws Exception {

	      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
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
			
			ResponseList<RequirementSubmissionHistory> list = requirementSubmissionHistoryService.getAllSubmissionHistoryApplicantsByOwner(sumissionHistoryDTO,userCode,sumissionHistoryDTO.getToStage(),searchDates,orgLocations.getId());
			
			File file = null;
			file = pdfUtil.recruiterReprtsApplicantsListPdfTable(list, orgLocations,searchDates,sumissionHistoryDTO.getToStage());
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);

		StringBuilder filename = new StringBuilder();
		response.setHeader("Content-Disposition",
				filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
		return file;
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
				if(employee != null) {
				UserDTO userDto = new UserDTO();
				userDto.setEmployeeId(userRole.getUser().getId().toString());
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
				}
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
				LetterHead letterHead = null;
				if(empTemplate.getLetterHead() != null) {
				 letterHead = letterHeadService.getLetterheadById(empTemplate.getLetterHead().getId());
				 User user = userService.getUserById(employeeId);
				 String templateContent = reportsUtil.replaceAllTemplatePlaceHolders(empTemplate.getTemplateContent(),empTemplate,toAddress,user,orgLocation);
				 reportsUtil.viewLetterHeadCompleteletterheadData(templateContent,letterHead,response);
				}
				
			    
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
				ApplicantsJoinedReportDTO userDTO,HttpServletRequest request, HttpServletResponse response) throws Exception {
			
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
				String[] tableColumns = { "name", "requirementCount", "offerCount", "joinedCount", "incentives"};
				int sortColumnIndex = 1; // by default sorting using updated date
				String sortColumnOrder = "desc"; // by default descending order
				if (userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
					sortColumnOrder = userDTO.getOrder().get(0).get("dir");
				}
				userDTO.setSortColumn(tableColumns[sortColumnIndex]);
				userDTO.setSortOrder(sortColumnOrder);
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
//		                System.out.println(fromC.get(Calendar.MONTH) + " " + monthsSDF.format(fromC.getTime()));
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
	                }			
	    }
		
		
		@RequestMapping(value ="/getEmployeeTaskRepotList")
		public @ResponseBody List<ProjectassignTasksDTO> getEmployeeTaskRepotList(HttpServletRequest request)throws Exception{
		  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  List<ProjectassignTasksDTO> projectAssignTaskList = projectAssignTasksService.getProjectAssignTaskListByOrglocationId(orgLocations.getId());
		  Map<String, List<ProjectassignTasksDTO>> map = projectAssignTaskList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getTaskStatus,Collectors.toList()));
		  Map<BigInteger, List<ProjectassignTasksDTO>> canMap = projectAssignTaskList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getCandidateId,Collectors.toList()));
		  List<ProjectassignTasksDTO> mp = new ArrayList<>();
			  for (Map.Entry<BigInteger, List<ProjectassignTasksDTO>> entry : canMap.entrySet())  {
				  ProjectassignTasksDTO dty = new ProjectassignTasksDTO();
				  Map<String,String> innerMap = new HashMap<>();
				  ProjectassignTasksDTO dto = entry.getValue().get(0);
				  dty.setFirstName(dto.getFirstName());
				  dty.setLastName(dto.getLastName());
				  dty.setFileUrl(dto.getFileUrl());
				  dty.setMemberId(dto.getMemberId());
				  dty.setCandidateId(dto.getCandidateId());
				  for (Map.Entry<String, List<ProjectassignTasksDTO>> canEntry : map.entrySet())  {
				  List<ProjectassignTasksDTO> s = entry.getValue().stream().filter(pd->canEntry.getKey().equalsIgnoreCase(pd.getTaskStatus())).collect(Collectors.toList());
				  innerMap.put(canEntry.getKey(), String.valueOf(s.size()));
				  }
				  dty.setAssignMembersMap(innerMap);
				  mp.add(dty);
			  }
		  return mp;
		
		}
		
		
		 @RequestMapping(value = "/employeeTasksReportSummaryList")
		 public @ResponseBody ResponseList<ProjectassignTasksDTO> employeeTasksReportSummaryList(ProjectassignTasksDTO projectassignTasksDTO,HttpServletRequest request)throws Exception{
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 ResponseList<ProjectassignTasksDTO> responseList = new ResponseList<>();
			 try {
						
				   String[] tableColumns = {"taskName","priority","projectName","dueDate","percentage","taskStatus"};
				    int sortColumnIndex = 1; //by default sorting using updated date
					String sortColumnOrder = "asc"; //by default referContactsDTO order
					if(projectassignTasksDTO.getOrder() != null && !projectassignTasksDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(projectassignTasksDTO.getOrder().get(0).get("column"));
						sortColumnOrder = projectassignTasksDTO.getOrder().get(0).get("dir");
					}
					projectassignTasksDTO.setOrgLocationId(orgLocations.getId());
					projectassignTasksDTO.setSortColumn(tableColumns[sortColumnIndex]);
					projectassignTasksDTO.setSortOrder(sortColumnOrder);
					ResponseList<ProjectAssignTask> projectResponseList = projectAssignTasksService.getProjectAssignTaskListByCandidateIdOrglocationId(projectassignTasksDTO);
					List<ProjectassignTasksDTO> patDTOList = new LinkedList<>();
					for(ProjectAssignTask pat: projectResponseList.getData()) {
						ProjectassignTasksDTO patDTO = new ProjectassignTasksDTO();
						patDTO.setProjctName(pat.getProjectTaskId().getProjectId().getProjectName());
						 if(pat.getProjectTaskId().getPriority() != null) {
							 patDTO.setPriority(pat.getProjectTaskId().getPriority().getName());
							 if(pat.getProjectTaskId().getPriority().getColorPic() != null) {
								 patDTO.setColorPic(pat.getProjectTaskId().getPriority().getColorPic());
							 }
						 }
						 patDTO.setTaskName(pat.getProjectTaskId().getTaskName());
						 if(pat.getProjectTaskId().getDueDate()  != null) {
							 patDTO.setDueDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), pat.getProjectTaskId().getDueDate()));
						 }
						 if(pat.getProjectTaskId().getSeverity() != null) {
							 patDTO.setTaskSeverity(pat.getProjectTaskId().getSeverity().getName());
						 }
						 if(pat.getProjectTaskId().getOwnerUserId() != null) {
							 patDTO.setTaskOwnerName(pat.getProjectTaskId().getOwnerUserId().getFullName());
						 }
						 if(pat.getProjectTaskId().getEstimateTime() != null) {
							 patDTO.setEstimatedTime(pat.getProjectTaskId().getEstimateTime());
						 }
						 patDTO.setPercentage(pat.getProjectTaskId().getPercentage());
						 patDTO.setTaskStatus(pat.getProjectTaskId().getTaskStatus().getName());
						 
						 patDTOList.add(patDTO);
					}
					responseList.setData(patDTOList);
					responseList.setRecordsTotal(projectResponseList.getRecordsTotal());
					responseList.setRecordsFiltered(projectResponseList.getRecordsFiltered());
					responseList.setResponse(Result.SUCCESS.name());
					responseList.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
					responseList.setResponse(Result.AWKWARD.name());
					responseList.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			 return responseList;
		 }
		 
		 
		@RequestMapping(value = "/getEmployeeTaskExport")
		@ResponseBody
		public void getEmployeeTaskExport(ProjectassignTasksDTO projectassignTasksDTO,HttpServletResponse response,HttpServletRequest request) throws Exception {
			  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			  String[] tableColumns = {"taskName","priority","projectName","dueDate","percentage","taskStatus"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "asc"; //by default referContactsDTO order
				if(projectassignTasksDTO.getOrder() != null && !projectassignTasksDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(projectassignTasksDTO.getOrder().get(0).get("column"));
					sortColumnOrder = projectassignTasksDTO.getOrder().get(0).get("dir");
				}
				projectassignTasksDTO.setOrgLocationId(orgLocations.getId());
				projectassignTasksDTO.setSortColumn(tableColumns[sortColumnIndex]);
				projectassignTasksDTO.setSortOrder(sortColumnOrder);
			  ResponseList<ProjectAssignTask> projectResponseList = projectAssignTasksService.getProjectAssignTaskListByCandidateIdOrglocationId(projectassignTasksDTO);

			    File file = null;
			    if(projectassignTasksDTO.getExportType().equalsIgnoreCase("pdf")) {
			    	 file=  dynamicReport.employeeTaskReportPdfExport(projectResponseList,orgLocations);
			    }else {
			    	 file=  dynamicReport.employeeTaskReportExcelExport(projectResponseList,orgLocations);
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
		 
		 
		 
		 @RequestMapping(value = "/getOverDueTaskReportList")
		 public @ResponseBody ResponseList<ProjectTasksDTO> getOverDueTaskReportList(ProjectTasksDTO projectTasksDTO,HttpServletRequest request)throws Exception{
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 ResponseList<ProjectTasksDTO> responseList = new ResponseList<>();
			 try {
						
				   String[] tableColumns = {"taskName","priority","taskType","","projectName","dueDate","percentage"};
				    int sortColumnIndex = 1; //by default sorting using updated date
					String sortColumnOrder = "asc"; //by default referContactsDTO order
					if(projectTasksDTO.getOrder() != null && !projectTasksDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(projectTasksDTO.getOrder().get(0).get("column"));
						sortColumnOrder = projectTasksDTO.getOrder().get(0).get("dir");
					}
					projectTasksDTO.setOrgLocationId(orgLocations.getId());
					projectTasksDTO.setSortColumn(tableColumns[sortColumnIndex]);
					projectTasksDTO.setSortOrder(sortColumnOrder);
					
					Date[]  searchDates = reportsUtil.last1MonthDates(orgLocations);
					
					ResponseList<ProjectTasks> projectTaskReportList = projectTasksService.getOverdueTaskReportListByOrglocationId(projectTasksDTO,searchDates);
					List<ProjectTasksDTO> taskDTOList = new LinkedList<>();
					for(ProjectTasks tasks:projectTaskReportList.getData()) {
						ProjectTasksDTO taskDTO = new ProjectTasksDTO();
						
						taskDTO.setTaskName(tasks.getTaskName());
						if(tasks.getPriority() != null) {
							taskDTO.setTaskPriority(tasks.getPriority().getName());
						}
						
						taskDTO.setTaskType(tasks.getTaskType().getName());
						taskDTO.setProjectName(tasks.getProjectId().getProjectName());
						if(tasks.getDueDate() != null) {
							taskDTO.setDueDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), tasks.getDueDate()));
						}
						if(tasks.getPercentage() != null) {
							taskDTO.setCompletion(tasks.getPercentage());
						}
						
						long diff = commonUtil.currentDate().getTime() - tasks.getDueDate().getTime();
						long diffSeconds = diff / 1000 % 60;
						long diffMinutes = diff / (60 * 1000) % 60;
						long diffHours = diff / (60 * 60 * 1000) % 24;
						long diffDays = diff / (24 * 60 * 60 * 1000);
						StringBuilder timeBuilder = new StringBuilder();
						if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
							taskDTO.setActionTimeStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
						} else if (diffDays == 0 && diffHours == 0) {
							tasks.setActionTimeStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
						} else if (diffDays == 0) {
							taskDTO.setActionTimeStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes)
									.append(" minutes ago").toString());
						} else {
							taskDTO.setActionTimeStr(timeBuilder.append(diffDays).append(" days ").append(diffHours)
									.append(" hours ago").toString());
						}

						if(tasks.getProjectId() != null) {
							List<Long> assignedMembersIds = projectMembersDao.getAllProjectMembersIdsByProjectIdAndOrgLocationId(tasks.getProjectId().getId(), projectTasksDTO.getOrgLocationId());
							if(!assignedMembersIds.isEmpty()) {
								List<ProjectAssignTask> assignUser = projectAssignTasksService.getProjectAssignTaskListByProjectTaskId(tasks.getId());
								List<ProjectAssignTask> assignedTaskUsersList =  new ArrayList<ProjectAssignTask>();
								for(ProjectAssignTask pat:assignUser) {
									if(pat.getProjectMemberId() != null) {
										if(pat.getProjectMemberId().getProjectMemberCandidteId() != null) {
											if(assignedMembersIds.contains(pat.getProjectMemberId().getId().longValue())) {
												 pat.setAssignedUserName(pat.getProjectMemberId().getProjectMemberCandidteId().getFullName());
												 pat.setAssignedUserUrl(pat.getProjectMemberId().getProjectMemberCandidteId().getFileUrl());
												 assignedTaskUsersList.add(pat);
											}
										}
									}
								 }
								taskDTO.setAssignedTaskUsers(assignedTaskUsersList);
							}
						}
						
						taskDTOList.add(taskDTO);
					}
					responseList.setData(taskDTOList);
					responseList.setRecordsTotal(projectTaskReportList.getRecordsTotal());
					responseList.setRecordsFiltered(projectTaskReportList.getRecordsFiltered());
					responseList.setResponse(Result.SUCCESS.name());
					responseList.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
					responseList.setResponse(Result.AWKWARD.name());
					responseList.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			 return responseList;
		 }
		 
		 
		 
		 @RequestMapping(value = "/getOverdueTaskExport")
			@ResponseBody
			public void getOverdueTaskExport(ProjectTasksDTO projectTasksDTO,HttpServletResponse response,HttpServletRequest request) throws Exception {
				  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				  String[] tableColumns = {"taskName","priority","taskType","","projectName","dueDate","percentage"};
				    int sortColumnIndex = 1; //by default sorting using updated date
					String sortColumnOrder = "asc"; //by default referContactsDTO order
					if(projectTasksDTO.getOrder() != null && !projectTasksDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(projectTasksDTO.getOrder().get(0).get("column"));
						sortColumnOrder = projectTasksDTO.getOrder().get(0).get("dir");
					}
					projectTasksDTO.setOrgLocationId(orgLocations.getId());
					projectTasksDTO.setSortColumn(tableColumns[sortColumnIndex]);
					projectTasksDTO.setSortOrder(sortColumnOrder);
					
					Date[]  searchDates = reportsUtil.last1MonthDates(orgLocations);
					
					ResponseList<ProjectTasks> overdueTaskReportList = projectTasksService.getOverdueTaskReportListByOrglocationId(projectTasksDTO,searchDates);
					
				    File file = null;
				    if(projectTasksDTO.getExportType().equalsIgnoreCase("pdf")) {
				    	 file=  dynamicReport.overdueTaskReportPdfExport(overdueTaskReportList,orgLocations);
				    }else {
				    	 file=  dynamicReport.overdueTaskReportExcelExport(overdueTaskReportList,orgLocations);
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
		 
		 
			@RequestMapping(value = "/getMarginSheetList")
			public @ResponseBody ResponseList<MarginSheetReportDTO> getMarginSheetList(@RequestParam(value="timePeriod", required=false) String timePeriod,
					@RequestParam(value="customStartDate", required=false) String customStartDate,
					@RequestParam(value="customEndDate", required=false) String customEndDate,
					@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
					@RequestParam(value="searchValue", required=false) String searchValue,
					MarginSheetReportDTO marginSheetReportDTO,
					HttpServletRequest request) throws Exception {
					ResponseList<MarginSheetReportDTO> responseList = new ResponseList<>();
			      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//			      Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
//			      User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//			      String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
//			      String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
//			    
					
					Date[] searchDates = null;
					ResponseList<MarginSheetReportDTO> projectMembersDTOList = new ResponseList<MarginSheetReportDTO>();
					try{
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
						List<Long> clientIdsList = new ArrayList<>();
						/*if(userRoleDto.getClients() != null && !userRoleDto.getClients().isEmpty()) {
							String[] clients = userRoleDto.getClients().split(",");
							for(int i=0; i < clients.length; i++) {
								clientIdsList.add(Long.parseLong(clients[i]));
							}
						}*/
					String[] tableColumns = { "employeeId", "employeeName","associateJoinedDate","projectName","projectDuration", "clientName", "payRate", "billRate", "Margin"};
					int sortColumnIndex = 1; // by default sorting using updated date
					String sortColumnOrder = "desc"; // by default descending order
					
					if (marginSheetReportDTO.getOrder() != null && !marginSheetReportDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(marginSheetReportDTO.getOrder().get(0).get("column"));
						sortColumnOrder = marginSheetReportDTO.getOrder().get(0).get("dir");
					}
					marginSheetReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
					marginSheetReportDTO.setSortOrder(sortColumnOrder);

//					ResponseList<ApplicantsJoinedReportDTO> userDTOList = userService.getApplicantsJoinedReport(userRoleDto,orgLocations.getId(),searchDates,teamsList,type,clientIdsList);

//					ResponseList<ApplicantsJoinedReportDTO> userDTOList = userService.getRecruitersReports(user.getId(), orgLocations.getId(), org.getId(), isGlobalUser,userDTO,loginUserRole,searchDates,teamsList,type,clientIdsList);
//					responseList.setData(userDTOList);
					projectMembersDTOList = projectMembersService.getProjectMemeberByClientProjectAndOrglocation(orgLocationId,searchDates,marginSheetReportDTO);
//					responseList.setData(projectMembersDTOList);	
					responseList.setResponse(Result.SUCCESS.name());
					responseList.setResponseMessage(Result.SUCCESS.getValue());
					 
			 }catch (Exception e) {
					responseList.setResponse(Result.AWKWARD.name());
					responseList.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
			}
					return projectMembersDTOList;
				}
			

			@RequestMapping(value = "/getMarginSheetListExport")
			public void getMarginSheetListExport(@RequestParam(value="timePeriod", required=false) String timePeriod,
					@RequestParam(value="customStartDate", required=false) String customStartDate,
					@RequestParam(value="customEndDate", required=false) String customEndDate,
					@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
					@RequestParam(value="searchValue", required=false) String searchValue,
					@RequestParam(value="exportType", required=false) String exportType,
					MarginSheetReportDTO marginSheetReportDTO,
					HttpServletRequest request,HttpServletResponse response) throws Exception {
			      OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

					Date[] searchDates = null;
					ResponseList<MarginSheetReportDTO> projectMembersDTOList = new ResponseList<MarginSheetReportDTO>();
					
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
						
						String[] tableColumns = { "employeeId", "employeeName","associateJoinedDate","projectName","projectDuration", "clientName", "payRate", "billRate", "Margin"};
						int sortColumnIndex = 1; // by default sorting using updated date
						String sortColumnOrder = "desc"; // by default descending order
						if (marginSheetReportDTO.getOrder() != null && !marginSheetReportDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(marginSheetReportDTO.getOrder().get(0).get("column"));
							sortColumnOrder = marginSheetReportDTO.getOrder().get(0).get("dir");
						}
						marginSheetReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
						marginSheetReportDTO.setSortOrder(sortColumnOrder);
						
					projectMembersDTOList = projectMembersService.getProjectMemeberByClientProjectAndOrglocation(orgLocationId,searchDates,marginSheetReportDTO);
					
					File file = null;
				    if(exportType.equalsIgnoreCase("pdf")) {
				    	 file=  dynamicReport.marginSheetReportPdfExport(projectMembersDTOList,orgLocations.getDateFormat(),orgLocations.getOrgLocationName());
				    }else {
				    	file=  dynamicReport.marginSheetReportExcelExport(projectMembersDTOList,orgLocations.getDateFormat(),orgLocations.getOrgLocationName());
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
			
			@RequestMapping(value = "/associateJoinedReportExport")
			public void associateJoinedReportExport(AssociateJoineReportDTO associateJoineReportDTO,
					@RequestParam(value="exportType", required=false) String exportType,
					HttpServletRequest request,HttpServletResponse response) throws Exception {
						OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
						
						String[] tableColumns = {"employeeId","empName", "gender","empType","deptName", "userRole","rate","hireDate", "reportingManager", "shiftName", "probationStatus"};

						   int sortColumnIndex = 1; //by default sorting using updated date
						   String sortColumnOrder = "desc"; //by default descending order
						if(associateJoineReportDTO.getOrder() != null && !associateJoineReportDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(associateJoineReportDTO.getOrder().get(0).get("column"));
							sortColumnOrder = associateJoineReportDTO.getOrder().get(0).get("dir");
						}
						associateJoineReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
						associateJoineReportDTO.setSortOrder(sortColumnOrder);
						
						Date[] freqDates = new Date[2];
						Date startUTCDate = null;
						Date endUTCDate = null;
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						 DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						 DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						 if("Custom".equalsIgnoreCase(associateJoineReportDTO.getFrequency())){
							 Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),associateJoineReportDTO.getFromDate());
								Date endTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),associateJoineReportDTO.getToDate());
								String customStartTimeStr = TZ_DF_CSD.format(startTime);  
								String customEndTimeStr = TZ_DF_CED.format(endTime);  
								startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
								endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
						 }else {
							 freqDates = reportsUtil.getJobFilterDate(associateJoineReportDTO.getFrequency(),loginOrgLocations);
							 String customStartTimeStr = TZ_DF_CSD.format(freqDates[0]);  
							 String customEndTimeStr = TZ_DF_CED.format(freqDates[1]);  
							 startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
							 endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
						 }
						 
					   ResponseList<AssociateJoineReportDTO> toDayReqApplicantscount =requirementSubmissionDao.getAssociateJoineReport(associateJoineReportDTO, loginOrgLocations.getId(),startUTCDate,endUTCDate);
					   String isTerminated = Constants.NO;
					File file = null;
				    if(exportType.equalsIgnoreCase("pdf")) {
				    	 file=  dynamicReport.associateJoinedAndTerminatedReportPdfExport(toDayReqApplicantscount,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName(),isTerminated);
				    }else {
				    	file=  dynamicReport.associateJoinedAndTerminatedReportExcelExport(toDayReqApplicantscount,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName(),isTerminated);
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

			
			@RequestMapping(value = "/applicantsJoinedListExport")
			public void applicantsJoinedListExport(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO, HttpServletRequest request,HttpServletResponse response)throws Exception
		   {
				OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Timezone timezone = null;
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginOrgLocations.getTimezone() != null){
					timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
				}
				Date[] freqDates = new Date[2];
				String userType = null;
				Date startUTCDate = null;
				Date endUTCDate = null;
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
				DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
				if("Custom".equalsIgnoreCase(requirementSubmissionHistoryDTO.getFrequency())){
					Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),requirementSubmissionHistoryDTO.getFromDate());
					Date endTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),requirementSubmissionHistoryDTO.getToDate());
					String customStartTimeStr = TZ_DF_CSD.format(startTime);  
					String customEndTimeStr = TZ_DF_CED.format(endTime);  
					startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
					endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
				}else{
					freqDates = reportsUtil.getJobFilterDate(requirementSubmissionHistoryDTO.getFrequency(),loginOrgLocations);
					String customStartTimeStr = TZ_DF_CSD.format(freqDates[0]);  
					String customEndTimeStr = TZ_DF_CED.format(freqDates[1]);  
					startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
					endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
				}
				  
				
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
				} 
				
				ResponseList<RequirementSubmissionHistoryDTO> responseList = new ResponseList<>();
			   ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, loginOrgLocations.getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,startUTCDate,endUTCDate,loginOrgLocations);

			   List<RequirementSubmissionHistoryDTO> rshDTOList = new ArrayList<>();
			   if(dashboardJobsCountlist.getData() != null)
				for(RequirementSubmissionHistory rsh: dashboardJobsCountlist.getData()){
					RequirementSubmissionHistoryDTO rshDTO = new RequirementSubmissionHistoryDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(rsh.getSubmissionId().getCandidate().getLastName())){
						rshDTO.setCandidateName(sb.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString());
					}else{
						rshDTO.setCandidateName(rsh.getSubmissionId().getCandidate().getFirstName());
					}
					
					rshDTO.setRequirementUUID(rsh.getSubmissionId().getRequirement().getRequirementUUID());
					rshDTO.setJobTitle(rsh.getSubmissionId().getRequirement().getJobTitle());
					if(rsh.getSubmissionId().getRequirement().getGeneratedId() != null) {
						rshDTO.setJobGeneratedId(rsh.getSubmissionId().getRequirement().getGeneratedId());
					}
					if(rsh.getSubmissionId().getRequirement().getClientId() != null){
		                if(StringUtils.isNotBlank(rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm())){
		                String payTerm=rsh.getSubmissionId().getRequirement().getClientId().getPaymentTerm();
		                Integer result = 0;
		                try {
		                	if(StringUtils.isNotBlank(payTerm)){
		                    payTerm=payTerm.replaceAll("\\D", "");
		                    result = Integer.parseInt(payTerm);
		                	}
		                } catch (Exception e) {
		                    result = 0;
		                }
		                rshDTO.setPayTerm(result);
		                }

		            }
					if(rsh.getSubmissionId().getRequirement().getJobRate() != null){
						rshDTO.setJobRate(rsh.getSubmissionId().getRequirement().getJobRate().toString());
					}
					
					if (rshDTO.getPayTerm() > 0 && rsh.getSubmissionId().getJoinedDate() != null) {
//						Integer result = Integer.parseInt(rshDTO.getPayOutPeriod());
						if(rshDTO.getPayTerm() != 0){
							Date generatedInvoiceDate=CalendarUtil.addDaysForGenerateInvoice(rsh.getSubmissionId().getJoinedDate(), rshDTO.getPayTerm());
							DateTime dtOrg = new DateTime(generatedInvoiceDate);
							DateTime generatedInvoiceDatePlusOne = dtOrg.plusDays(1);
							rshDTO.setGenerateInvoice(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),generatedInvoiceDatePlusOne.toDate()));
							
						}
		}
					
					if(rsh.getSubmissionId().getRequirement().getParentRequirement() != null){
						rshDTO.setOrgLocationName(rsh.getSubmissionId().getRequirement().getParentRequirement().getOrgLocationId().getOrgLocationName());
					}
					
					if(rsh.getSubmissionId().getRequirement().getClientId() != null) {
						if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
							if (rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId() != null) {
								rshDTO.setOrgLocationName(rsh.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
							}
						}
						if (rsh.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
							if (rsh.getSubmissionId().getRequirement().getClientId().getLocationId() != null) {
							rshDTO.setOrgLocationName(rsh.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
							}
						}
					}
					
					List<String> requirementLocationsList = new ArrayList<>();
					List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(rsh.getSubmissionId().getRequirement().getId());
					if(locationsList != null && !locationsList.isEmpty()) {
						for(RequirementLocations location : locationsList) {
							requirementLocationsList.add(location.getLocation());
						}
					}
					rshDTO.setLocationsList(requirementLocationsList);
					
					
					rshDTO.setEmail(rsh.getSubmissionId().getCandidate().getEmail());

					if(rsh.getSubmissionId().getBackOutCategoryType() != null) {
						rshDTO.setBackoutType(rsh.getSubmissionId().getBackOutCategoryType());
						rshDTO.setBackoutReason(rsh.getSubmissionId().getBackOutReason());
					}
					if(rsh.getSubmissionId().getRejectedCategoryType() != null) {
						rshDTO.setRejectedType(rsh.getSubmissionId().getRejectedCategoryType());
						rshDTO.setRejectedReason(rsh.getSubmissionId().getRejectedReason());
					}
					if(rsh.getSubmissionId().getRequirement().getJobRate() != null) {
						rshDTO.setJobRating(rsh.getSubmissionId().getRequirement().getJobRate());
					}
					if(rsh.getSubmissionId().getRequirement().getIncentivized() != null) {
						rshDTO.setIncentivize(rsh.getSubmissionId().getRequirement().getIncentivized());
					}
					if(rsh.getSubmissionId().getCandidate().getMobilePhone() != null){
						rshDTO.setMobilePhone(rsh.getSubmissionId().getCandidate().getMobilePhone());
					}
					
					if(rsh.getSubmissionId().getJoinedDate()  != null){
						rshDTO.setJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getJoinedDate()));
					}
					
					if(rsh.getSubmissionId().getCandidateResumes() != null){
						rshDTO.setResumeUUID(rsh.getSubmissionId().getCandidateResumes().getResumeUUID());
						rshDTO.setFileUrl(rsh.getSubmissionId().getCandidateResumes().getFileURL());
						rshDTO.setFileName(rsh.getSubmissionId().getCandidateResumes().getFileName());
						rshDTO.setContentType(rsh.getSubmissionId().getCandidateResumes().getFileContentType());
					}
					rshDTO.setPrimarySkill(rsh.getSubmissionId().getCandidate().getPrimarySkill());
					rshDTO.setCreatedUser(rsh.getSubmissionId().getCreatedUser());
					rshDTO.setCandidateScore(rsh.getSubmissionId().getCandidateScore());
					rshDTO.setSubmissionStatus(rsh.getSubmissionId().getSubmissionStatus().toString());
					rshDTO.setCandidateUUID(rsh.getSubmissionId().getCandidate().getCandidateUUID());
					rshDTO.setSubmissionId(rsh.getSubmissionId().getId());
					rshDTO.setExperienceYears(rsh.getSubmissionId().getCandidate().getExperienceYears());
					rshDTO.setExperienceMonths(rsh.getSubmissionId().getCandidate().getExperienceMonths());
					rshDTO.setFinalizedRate(rsh.getSubmissionId().getFinalizedRate());
					rshDTO.setSubmittedBy(rsh.getSubmissionId().getSubmittedBy());
					rshDTO.setToStage(rsh.getToStage());
					//rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getSubmissionId().getCreatedDate()));
					rshDTO.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
					rshDTO.setSubmittedBy(rsh.getCreatedUser());
//							}
						
					List<RequirementOfferHistory> offerHistorList = requirementOfferHistoryService.getRequirementInternalAndIsClientOfferssBySubmissionIdAndOrgLocationsId(rsh.getSubmissionId().getId(), loginOrgLocations.getId());
					if(offerHistorList != null && !offerHistorList.isEmpty()) {
						float clientOfferRate = 0;
						float internalOfferRate = 0;
						for(RequirementOfferHistory roh : offerHistorList) {
//							System.out.println("OfferID:"+roh.getOfferId().getId() + " SubmissionID:"+roh.getSubmissionId().getId()+ " Amount:" + roh.getOfferId().getAmount() + " Status:" + roh.getOfferId().getOfferStatus());
							if(roh.getOfferId().getOfferStatus().equalsIgnoreCase("CLIENT_OFFERACCEPTED")) {
								clientOfferRate = conversionUtil.rateConvertToYear(roh.getOfferId().getAmount(),roh.getOfferId().getPaymentMode(),loginOrgLocations);
							} else if(roh.getOfferId().getOfferStatus().equalsIgnoreCase("OFFERACCEPTED")) {
								internalOfferRate = conversionUtil.rateConvertToYear(roh.getOfferId().getAmount(),roh.getOfferId().getPaymentMode(),loginOrgLocations);
							}
						}
						if(clientOfferRate > 0) {
							float finalRatePerYear = clientOfferRate-internalOfferRate;
							rshDTO.setMarginRateYearly(String.format("%.3f", Float.valueOf(finalRatePerYear)));
						}
					}
					
					int interviewedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocations.getId(), rsh.getSubmissionId().getRequirement().getId(), "INTERVIEWED");
					rshDTO.setNumOfInterview(interviewedCount);
					
						List<RequirementOffers> offersList = requirementOffersService.getRequirementOfferSubmissionByIdAndOrgLocationId(rsh.getSubmissionId().getId(), loginOrgLocations.getId());
						for (RequirementOffers requirementOffers : offersList) {
							rshDTO.setOfferId(requirementOffers.getId());
							float rate = conversionUtil.rateConvertToYear(requirementOffers.getAmount(),requirementOffers.getPaymentMode(),loginOrgLocations);
							rshDTO.setOfferRateforYearly(String.format("%.3f", Float.valueOf(rate)));
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
							if("OFFERMADE".equalsIgnoreCase(rsh.getToStage())){
								rshDTO.setAppliedOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),rsh.getCreatedDate()));
							}
						}
							
						RequirementInterview reqInterview=requirementInterviewService.getinterviewBySubmissionId(rsh.getSubmissionId().getId());
						rshDTO.setInterviewOn(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),reqInterview.getDateOfInterview()));
					
					rshDTOList.add(rshDTO);
				}
				responseList.setData(rshDTOList);
			   
				File file = null;
			    if(requirementSubmissionHistoryDTO.getExportType().equalsIgnoreCase("pdf")) {
			    	 file=  dynamicReport.applicantsJoinedListExportPdf(responseList,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName());
			    }else {
			    	file=  dynamicReport.applicantsJoinedListExportExcel(responseList,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName());
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
			
			@RequestMapping(value = "/associateTerminatedReportExport")
			public void associateTerminatedReportExport(AssociateJoineReportDTO associateJoineReportDTO,
					@RequestParam(value="exportType", required=false) String exportType,
					HttpServletRequest request,HttpServletResponse response) throws Exception {
						OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
						
						String[] tableColumns = {"employeeId","empName", "empType","hireDate","reportingManager", "terminateDate","terminatedReason","userRole", "gender", "shiftName", "deptName", "rate"};

						   int sortColumnIndex = 1; //by default sorting using updated date
						   String sortColumnOrder = "desc"; //by default descending order
						if(associateJoineReportDTO.getOrder() != null && !associateJoineReportDTO.getOrder().isEmpty()) {
							sortColumnIndex = Integer.parseInt(associateJoineReportDTO.getOrder().get(0).get("column"));
							sortColumnOrder = associateJoineReportDTO.getOrder().get(0).get("dir");
						}
						associateJoineReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
						associateJoineReportDTO.setSortOrder(sortColumnOrder);
						
						Date[] freqDates = new Date[2];
						Date startUTCDate = null;
						Date endUTCDate = null;
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						 DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
						 DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
						 if("Custom".equalsIgnoreCase(associateJoineReportDTO.getFrequency())){
							 Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),associateJoineReportDTO.getFromDate());
								Date endTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),associateJoineReportDTO.getToDate());
								String customStartTimeStr = TZ_DF_CSD.format(startTime);  
								String customEndTimeStr = TZ_DF_CED.format(endTime);  
								startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
								endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
						 }else {
							 freqDates = reportsUtil.getJobFilterDate(associateJoineReportDTO.getFrequency(),loginOrgLocations);
							 String customStartTimeStr = TZ_DF_CSD.format(freqDates[0]);  
							 String customEndTimeStr = TZ_DF_CED.format(freqDates[1]);  
							 startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
							 endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
						 }
						 String isTerminated = Constants.YES;
						ResponseList<AssociateJoineReportDTO> toDayReqApplicantscount =requirementSubmissionDao.getAssociateTerminateReport(associateJoineReportDTO, loginOrgLocations.getId(),startUTCDate,endUTCDate);
						
					File file = null;
				    if(exportType.equalsIgnoreCase("pdf")) {
				    	 file=  dynamicReport.associateJoinedAndTerminatedReportPdfExport(toDayReqApplicantscount,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName(),isTerminated);
				    }else {
				    	file=  dynamicReport.associateJoinedAndTerminatedReportExcelExport(toDayReqApplicantscount,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName(),isTerminated);
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
			
	@RequestMapping(value = "/offerAcceptanceRateList")
	public ResponseList<OfferAcceptanceRateDTO> getOfferAcceptanceRateList(HttpServletRequest request, 
			@RequestParam(defaultValue = "") String selectedClients,
			@RequestParam(defaultValue = "") String filteredDate,
			@RequestParam(defaultValue = "") String filterType) {
		ResponseList<OfferAcceptanceRateDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(user != null) {
				responseList =  requirementSubmissionHistoryService.getOfferAcceptanceRateList(user.getUserCode(), orgLocations.getId(), selectedClients, filterType, filteredDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
		 
			
	@RequestMapping(value = "/getProjectsList")
	@ResponseBody
	public ResponseList<Projects> getProjectList(@RequestParam(value="associatesId", required=false) String associatesId,HttpServletRequest request,HttpServletResponse response)throws Exception{
	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	//String associates = associatesId.split(",");
	ResponseList<Projects> list = new ResponseList<Projects>();
	List<Projects> projectsList = projectsService.getProjectsListByCanddateIdAndOrgId(associatesId,orgLocations.getId());
	list.setData(projectsList);
	return list;

	}
	@RequestMapping(value = "/prepareReport")
	public Response<Map<String ,List<AvailableAssociatesDTO>>> prepareReport(AvailableAssociatesDTO availableAssociatesDTO,
			HttpServletRequest request) {
		Response<Map<String ,List<AvailableAssociatesDTO>>> response = new Response<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			response.setObject(projectsService.getAvailableAssociatesReport(availableAssociatesDTO, orgLocations));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponse(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}
		
	@ResponseBody
	@RequestMapping(value = "/availableAssociatereportExport")
	public File availableAssociatereportForExport(HttpServletRequest request, HttpServletResponse response, AvailableAssociatesDTO availableAssociatesDTO) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<Map<String ,List<AvailableAssociatesDTO>>> res = new Response<>();
		File file = null;
		res.setObject(projectsService.getAvailableAssociatesReport(availableAssociatesDTO, orgLocations));
		if (res.getObject() != null && !res.getObject().isEmpty()) {

			if (availableAssociatesDTO.getType().equalsIgnoreCase("pdf")) {
				//file = pdfUtil.availableAssociatereportReportListOfPdf(res.getObject(), user, orgLocations);
			} else {
				file = projectsService.availableAssociatereportReportListOfExcel(res.getObject(), user, orgLocations);
			}
		}
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
		if (file != null) {
			file.delete();
		}
		return file;

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/offerAcceptanceRateListForVendor")
	public ResponseList<OfferAcceptanceRateDTO> getOfferAcceptanceRateListForVendor(HttpServletRequest request, 
			@RequestParam(defaultValue = "") long orgRelationId,
			@RequestParam(defaultValue = "") long spocId,
			@RequestParam(defaultValue = "") String filteredDate,
			@RequestParam(defaultValue = "") String filterType) {
		ResponseList<OfferAcceptanceRateDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<Long> jobIdList = new ArrayList<>();
			if(user != null) {
				jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
				if(orgRelationId > 0) {							
					//above jobIdList is released to vendor job id's
					//now get the parentJobId based on vendorReleasedJob and get the jobStages for that vendor resubmitted job('submit to client')
					jobIdList = requirementReportService.getParentRequirementIdListByRequirementIds(jobIdList);
				}
				responseList =  requirementSubmissionHistoryService.getOfferAcceptanceRateListForVendor(jobIdList, orgRelationId, spocId, filterType, filteredDate);
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	@RequestMapping(value = "/getLoanReport", method = RequestMethod.GET)
	public ResponseList<LoanAndAdvanceReportDTO> getLoanReport(LoanAndAdvanceReportDTO loanAndAdvanceReportDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
//		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		ResponseList<LoanAndAdvanceReportDTO> assLoanList =  loanAndAdvancesReportService.getLoansAndAdvancesByLoanNameAndOrglocationId(orgLocations.getId(), loanAndAdvanceReportDTO);
		for(LoanAndAdvanceReportDTO lard:assLoanList.getData()) {
			lard.setFromDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lard.getStartDate()));
			lard.setToDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lard.getLoanEndDate()));
		}
		return assLoanList;
	}
	
	@RequestMapping(value = "/getLoanAndAdvancesReportExport", method = RequestMethod.GET)
	public File getLoanAndAdvancesReportExport(LoanAndAdvanceReportDTO loanAndAdvanceReportDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<LoanAndAdvanceReportDTO> assLoanList =  loanAndAdvancesReportService.getLoansAndAdvancesByLoanNameAndOrglocationId(orgLocations.getId(), loanAndAdvanceReportDTO);
		
		File file = null;
	    if(loanAndAdvanceReportDTO.getExportType().equalsIgnoreCase("pdf")) {
	    	 file=  dynamicReport.loanAndAdvanceReportPDFExport(assLoanList,orgLocations);
	    }else {
	    	 file=  dynamicReport.loanAndAdvanceReportExcelExport(assLoanList,orgLocations);
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
		
		return file;
	}
	
}

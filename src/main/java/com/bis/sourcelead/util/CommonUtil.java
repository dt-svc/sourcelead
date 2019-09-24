package com.bis.sourcelead.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.dao.ShiftDao;
import com.bis.sourcelead.dto.ApplicantRejectBackOutDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicensePaymentTransactionHistory;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardTemplateDocumentsService;
import com.bis.sourcelead.service.VendorOnboardTemplateService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.DecimalFormat;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * This is the commonutil for the certain common methods used throughout the application
 * @author: Naresh Bolisetty
 * 
 */
/**
 * @author BIS104
 *
 */
@Component
public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private TimeSheetDayEntriesService timeSheetDayEntriesService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private PayrollPayheadService payrollPayheadService;
	
	@Autowired
	private UserService userService;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${pmsUrl}")
    private String pmsUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private RequirementService requirementService;
	
	
	@Autowired
    private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	VendorOnboardTemplateService vendorOnboardTemplateService;
	
	@Autowired
	VendorOnboardTemplateDocumentsService vendorOnboardTemplateDocumentsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	MasterCandidateService masterCandidateService;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;

	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private ProjectTasksDao tasksDao;
	
	@Autowired
	private EmployeeVerificationTemplateService employeeVerificationTemplateService;
	
	@Value("${paymentSuccessReturnEmail}")
	private String paymentSuccessReturnEmail;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioconService;
	
	@Autowired
	private ShiftDao shiftDao;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;

	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	ExpenseProjectApproverHierarchyService expenseProjectApproverHierarchyService;
	
	@Autowired
	RequirementOffersDao requirementOffersDao;
	
	@Autowired
	PayrollPaySlipIndService payrollPaySlipIndService;
	
	@Autowired
	ClientVendorIndexHelper clientVendorIndexHelper;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	/**
	 * This method is used to convert JSON String to HashMap<Key,Value>
	 * @author: Naresh Bolisetty
	 * 
	 */
	public static HashMap<String, String> getJsonAsMap(String json)
	{
	    try
	    {
	        ObjectMapper mapper = new ObjectMapper();
	        TypeReference<Map<String,String>> typeRef = new TypeReference<Map<String,String>>() {};
	        HashMap<String, String> result = mapper.readValue(json, typeRef);

	        return result;
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException("Couldnt parse json:" + json, e);
	    }
	}
	
	/**
	 * This method is used to clean html content
	 * @author: Naresh Bolisetty
	 * 
	 */
	public static String cleanContent(String content) {
		Whitelist allowedTags = Whitelist.simpleText(); 
		   // This whitelist allows only simple text formatting: b, em, i, strong, u. All other HTML (tags and attributes) will be removed.
		   allowedTags.addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col",
              "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
              "i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
              "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
              "ul","xml","br","font","span");
	        allowedTags.addAttributes("a", "href", "title");
	        allowedTags.addAttributes("blockquote", "cite");
	        allowedTags.addAttributes("col", "span", "width");
	        allowedTags.addAttributes("colgroup", "span", "width");
	        allowedTags.addAttributes("img", "align", "alt", "height", "src", "title", "width","style","class");
	        allowedTags.addAttributes("ol", "start", "type");
	        allowedTags.addAttributes("q", "cite");
	        allowedTags.addAttributes("table", "summary", "width");
	        allowedTags.addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width");
	        allowedTags.addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
	        allowedTags.addAttributes("ul", "type");
	       /* allowedTags.addProtocols("a", "href", "//", "#","ftp", "http", "https", "mailto");*/
	        
	        allowedTags.addProtocols("blockquote", "cite", "http", "https","style");
	        allowedTags.addProtocols("cite", "cite", "http", "https");
	        allowedTags.addProtocols("img", "src", "http", "https");
	        allowedTags.addProtocols("q", "cite", "http", "https");
	        allowedTags.addAttributes(":all","class","style","title");
 		Document dirty = Jsoup.parseBodyFragment(content, "");
 		Cleaner cleaner = new Cleaner(allowedTags);
 		Document clean = cleaner.clean(dirty);
 		clean.outputSettings().escapeMode(EscapeMode.xhtml); // Non fa l'escape dei caratteri utf-8
 		String safe = clean.body().html();
		    return safe;
	}
	
	/**
	 * This method is used to store pre-defined sourcelead tasks for the particular project
	 * @author: Naresh Bolisetty
	 * 
	 */
	
	public static Date currentDate() {
        return new Date();
    }
	
	
	
	
	/**
	 * Converting Org based date format as string
	 * @param orgDateFormat
	 * @param date
	 * @return String
	 */
	public String dateStrFromOrgDateFormatter(String orgDateFormat, Date date){
		String dateStr = null;
		if(date != null && StringUtils.isNotEmpty(orgDateFormat)){
			SimpleDateFormat df = new SimpleDateFormat(orgDateFormat);
			dateStr = df.format(date);
		}
		return dateStr;
	}
	
	public  Date stringToDate(String dateString){

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {

		date = df.parse(dateString);

		} catch (Exception e) {

		e.printStackTrace();

		}
		return date;
		}
	public  Date stringToDateByOrgLocationFormat(String dateString, String orgLocationFormat){

		DateFormat df = new SimpleDateFormat(orgLocationFormat);
		Date date = null;
		try {

		date = df.parse(dateString);

		} catch (Exception e) {

		e.printStackTrace();

		}
		return date;
		}
	/**
	 * Convert String to Capitalized String
	 * @param name
	 * @return
	 */
	public String firstLetterCapitalizedString(String name){
		 String finalString = WordUtils.capitalizeFully(name);
		return finalString;
	}
	
	/**
	 * converting Date to String
	 * 
	 * @param date
	 * @return
	 */
	public String dateStrFromDate(Date date)
	{
		String dateStr = null;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		dateStr = df.format(date);
		return dateStr;
	}
	
	/**
	 * Elk date format
	 * @param dateString
	 * @return
	 */
	public String elkDateFormatter(Date date){
		String dateStr = null;
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = df.format(date);
		}
		return dateStr;
	}
	
	/**
	 *  Converting Org based date format as Util.Date
	 * @param orgDateFormat
	 * @param dateStr
	 * @return Date
	 */
	public Date orgLocationDateFormat(String orgDateFormat, String dateStr){
		DateFormat df = new SimpleDateFormat(orgDateFormat);
		Date date = null;
		 try {
		        date = df.parse(dateStr);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			return date;
	}
	
	public Date orgLocationCommonDate(Date date, OrgLocations loginOrgLocation){
		DateTime dt = new DateTime(date);
		DateTimeZone dtZone = DateTimeZone.forID(loginOrgLocation.getTimeZoneName());
		DateTime dtus = dt.withZone(dtZone); 
		Date dateWithTimezone = dtus.toLocalDateTime().toDate();
		return dateWithTimezone;
	}
	
	
	public  Date  dateFromElk(String dateString){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date = null;
	    try {
	        date = df.parse(dateString);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return date;
	}

	public static boolean isValidDate(String inDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	  //  dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(inDate.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	/**
	 *  returns current date based on orglocation date format
	 * @param orgDateFormat
	 * @param dateStr
	 * @return Date
	 * @throws ParseException 
	 */
	public Date getOrgLocationCurrentDate(String orgDateFormat, Date date) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat(orgDateFormat);
			return dateFormat.parse(dateFormat.format(date));
	}
	
	
	
	public void storeSourceleadTasks(User user,Projects projects) throws Exception{
		Collection<ProjectTasks> tasksList = projectTasksService.getAllSourceleadTasks();
		if(tasksList != null && !tasksList.isEmpty()){
			Integer generatedIdcount = 1;
			for(ProjectTasks task :tasksList){
				task.setProjectId(projects);
				if (!Constants.HOLIDAY.equalsIgnoreCase(task.getTaskName()) && !Constants.COMPENSATORY_LEAVE.equalsIgnoreCase(task.getTaskName()) && !Constants.WORK_FROM_HOME.equalsIgnoreCase(task.getTaskName())) {
					task.setBillableType(Constants.TASK_NON_BILLABLE);
				} else {
					task.setBillableType(Constants.TASK_BILLABLE);
				}
				LookUpData taskTypeLookUpData = new LookUpData();
				taskTypeLookUpData.setId(1060L);
				task.setTaskType(taskTypeLookUpData);
				LookUpData taskStatusLookUpData = new LookUpData();
				taskStatusLookUpData.setId(1069L);
				task.setTaskStatus(taskStatusLookUpData);
				task.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				task.setCreatedBy(user.getCreatedBy());
				task.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
				task.setGeneratedId(getProjectTaskGenaratedId(projects,generatedIdcount));
				task.setCreatedDate(new Date());
				task.setUpdatedDate(new Date());
				task.setUpdatedBy(user.getCreatedBy());
				generatedIdcount++;
			}
		}
		projectTasksService.saveAll(tasksList);
	}
	
	/**
	 * This method is used to Get the Project task generatedIds
	 * @author Laxman
	 * @param projects
	 * @param generatedIdcount2 
	 * @return
	 * @throws Exception
	 */
	
	public String getProjectTaskGenaratedId(Projects projects, Integer number) throws Exception {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(projects.getProjectShortCode());
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(number);
		return sbuilder.append(convetredRequirementsId).toString();
	}
	/**
	 * This method is used to Get the Approver User of Time or Expense Reopen Requests
	 * @author: Naresh Bolisetty
	 * 
	 */
	public Boolean isSuperAdminForProjectMemberToReopenTimesheets(Long projectMemberId,User loginUser,OrgLocations loginLocation,String isGlobalAccess) throws Exception{
		ProjectMembers clientProjectMember = projectMembersService.getClientProjectMemberByProjectMemberId(projectMemberId);
	    ProjectApproverHierarchy pah =  null;
	    Boolean isSuperUser = false;
	    if(null != clientProjectMember && null != clientProjectMember.getProjects() && null != clientProjectMember.getProjectClientsId())
	    {
	    	UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(loginUser.getId(), loginLocation.getId());
	    	pah =  projectApproverHierarchyService.getApproverUsersByProjectIdAndProjectClientId(clientProjectMember.getProjects().getId(), clientProjectMember.getProjectClientsId().getId(),loginLocation.getOrg().getId(),isGlobalAccess);
		    if(     
		    		//If the Login User As Location Admin Of Client
		    		(null != clientProjectMember.getProjectClientsId() && null != clientProjectMember.getProjectClientsId().getOrgLocationsId() && null != clientProjectMember.getProjectClientsId().getOrgLocationsId().getUser()
		    		  && clientProjectMember.getProjectClientsId().getOrgLocationsId().getId().longValue() == loginLocation.getId().longValue() && userRole != null && StringUtils.isNotEmpty(userRole.getUserType()) && userRole.getUserType().equalsIgnoreCase("Admin"))
		    		||
		    		//If the Login User As Top-Level(Level2) Approver Of Client
		    		(null != pah && null != pah.getApproverUserId() && null != pah.getApproverUserId().getId()
		    		  && pah.getApproverUserId().getId().longValue() == loginUser.getId().longValue())
		    		
		      ){
		    	isSuperUser = true;
		     }
	    }
	    return isSuperUser;
	}
	
	
	/**
	 * This method is used to Get the Approver User of Time or Expense Reopen Requests
	 * @author: Naresh Bolisetty
	 * 
	 */
	public Boolean isSuperAdminForProjectMemberToReopenExpenseses(Long projectMemberId,User loginUser,OrgLocations loginLocation) throws Exception{
		ProjectMembers clientProjectMember = projectMembersService.getClientProjectMemberByProjectMemberId(projectMemberId);
	    ExpenseProjectApproverHierarchy pah =  null;
	    Boolean isSuperUser = false;
	    if(null != clientProjectMember && null != clientProjectMember.getProjects() && null != clientProjectMember.getProjectClientsId())
	    {
	    	UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(loginUser.getId(), loginLocation.getId());
	    	pah =  expenseProjectApproverHierarchyService.getApproverUsersByProjectIdAndProjectClientId(clientProjectMember.getProjects().getId(), clientProjectMember.getProjectClientsId().getId());
		    if(     
		    		//If the Login User As Location Admin Of Client
		    		(null != clientProjectMember.getProjectClientsId() && null != clientProjectMember.getProjectClientsId().getOrgLocationsId() && null != clientProjectMember.getProjectClientsId().getOrgLocationsId().getUser()
		    		  && clientProjectMember.getProjectClientsId().getOrgLocationsId().getId().longValue() == loginLocation.getId().longValue() && userRole != null && StringUtils.isNotEmpty(userRole.getUserType()) && userRole.getUserType().equalsIgnoreCase("Admin"))
		    		||
		    		//If the Login User As Top-Level(Level2) Approver Of Client
		    		(null != pah && null != pah.getApproverUserId() && null != pah.getApproverUserId().getId()
		    		  && pah.getApproverUserId().getId().longValue() == loginUser.getId().longValue())
		    		
		      ){
		    	isSuperUser = true;
		     }
	    }
	    return isSuperUser;
	}
	/**
	 * This method is used to in-activate timesheet submissions based on projectmemberId and terminationdate
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(Long memberId,Date terminationDate){
		List<TimesheetSubmissions> timesheetSubmissionsList;
		try {
			timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(memberId,terminationDate);
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				for(TimesheetSubmissions ts :timesheetSubmissionsList){
					List<TimeSheetDayEntries> tdeList = null;
					if(!CalendarUtil.isGivenDateBetweenDateRange(ts.getStartDate(), ts.getEndDate(), terminationDate)) {
						ts.setStatus(Constants.MEMBER_IN_ACTIVE);
					    tdeList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesBySubmissionId(ts.getId());
					}else {
					    tdeList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(ts.getId(), CalendarUtil.getNextDate(terminationDate), ts.getEndDate());
					}
					if(tdeList != null && !tdeList.isEmpty()) {
						for(TimeSheetDayEntries tde :tdeList) {
							tde.setStatus(Integer.parseInt(Constants.MEMBER_IN_ACTIVE));
							tde.setTotalHours("00:00");
							tde.setBillableHours("00:00");
							tde.setNonBillableHours("00:00");
							timeSheetDayEntriesService.saveTimesheetDayEntries(tde);
						}
					}
					
				}
			}
			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
	public void terminatedMailToEmployee(User loginUser, ProjectMembersHistory pmh, OrgLocations orgLocations) throws Exception {
		 
		 User user=null;
		 if(null != pmh.getProjectMemberId()){
		 user = userService.getUserByUserCode(pmh.getProjectMemberId().getCreatedBy());
		 }
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName());
		 emailMessage.setLastName(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName());
		 emailMessage.setToEmail(pmh.getProjectMemberId().getProjectMemberCandidteId().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 //SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
		 String endDate = dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmh.getProjectTerminationDate());
		 
		 String statrDate = dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmh.getProjectAssignedDate());
		 
		 StringBuilder employeeName = new StringBuilder();
		 	 if(StringUtils.isNotBlank(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName())) {
		 		 employeeName = employeeName.append(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName()).append(" ").append(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName());
		 	 }else {
		 		 employeeName = employeeName.append(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName());
		  		 }
		  		 
		 	 globalVarsMap.put("EmployeeName", employeeName.toString());
		 	 
		 
		 if(null != pmh.getProjectMemberId().getProjects()){
			 if(null != pmh.getProjectMemberId().getProjects().getProjectName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getProjects().getProjectName())){
				 globalVarsMap.put("ProjectName", pmh.getProjectMemberId().getProjects().getProjectName());
			}else{
				 globalVarsMap.put("ProjectName", "--");
			 }
		 }
		 
		 if(null != pmh.getProjectAssignedDate()){
			 globalVarsMap.put("Project StartDate",statrDate);
		 }else{
			 globalVarsMap.put("Project StartDate", "--");
		 }
		 
		 if(null != pmh.getProjectTerminationDate()){
			 globalVarsMap.put("Project EndDate",endDate);
		 }else{
			 globalVarsMap.put("ProjectEndDate", "--");
		 }
		 
		 if (null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())) {
			 StringBuilder mangerName = new StringBuilder();
			 globalVarsMap.put("Manager", mangerName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 } else {
				 globalVarsMap.put("Manager", user.getFirstName());
			}
		
		 if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			 StringBuilder loginName = new StringBuilder();
			 globalVarsMap.put("LoginUser", loginName.append(loginUser.getFirstName()).append(loginUser.getLastName()).toString());
			 } else {
				 globalVarsMap.put("LoginUser", loginUser.getFirstName());
			}
		
		 if (null != orgLocations.getOrg()) {
			 if(null != orgLocations.getOrg().getOrgName() && !"".equalsIgnoreCase(orgLocations.getOrg().getOrgName())){
				 globalVarsMap.put("LoginOrg", orgLocations.getOrg().getOrgName());
			 } else {
				 globalVarsMap.put("LoginOrg", "--");
			}
		 }
		 
		 if(pmh.getProjectMemberId().getProjects().getOrgRelationId() !=null && !equals(pmh.getProjectMemberId().getProjects().getOrgRelationId())){
			 StringBuilder clientName = new StringBuilder();
			 globalVarsMap.put("clientName",clientName.append("with").append(" ").append(pmh.getProjectMemberId().getProjects().getOrgRelationId().getResponderLocationId().getOrg().getOrgName()).toString());
		 }
		 globalVarsMap.put("reason", pmh.getProjectMemberId().getTerminateReason());


		 StringBuilder linkBuild = new StringBuilder();
	     globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	   
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_TERMINATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.EMPLOYEE_TERMINATION,new ArrayList<EmailAttachment>());
		 
		 if(pmh.getProjectMemberId().getProjectMemberCandidteId().getConsultantUser() != null) {
	 			 User toUser = pmh.getProjectMemberId().getProjectMemberCandidteId().getConsultantUser();
	 			 String actionLink="";
	 			 emailScenerioUtil.sendNotification(actionLink, loginUser,toUser, pmh.getProjectMemberId().getProjectMemberCandidteId().getOrgLocationId(), notificationScenario,new HashMap<String,String>());
			  
 		 }

		 
		 
		

			
		}
	/**
	 * This method is used to activate timesheet submissions based on projectmemberId and terminationdate
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void activateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(Long memberId,Date terminationDate){
		List<TimesheetSubmissions> timesheetSubmissionsList;
		try {
			timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(memberId,terminationDate);
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				for(TimesheetSubmissions ts :timesheetSubmissionsList){
					ts.setStatus(Constants.MEMBER_ACTIVE);
				}
			}
			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	/**
	 * This method is used to terminate project member by project memberId and way of termination
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void terminateProjectMemberByProjectMemberId(Long projectMemberId,Primary isManuallyTerminated){
		try {
			ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
			 }
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
			pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
			
			/**
			 * commented by Prasad salina
			 */
			inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, pmh.getProjectTerminationDate());
			
			ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
			pm.setStatus(Constants.MEMBER_IN_ACTIVE);
			pm.setIsManuallyTerminated(isManuallyTerminated);
			projectMembersService.updateProjectMember(pm);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to re-activate project member by project memberId and way of termination
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void reactivateProjectMemberByProjectMemberId(Long projectMemberId){
		try {
			ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
			if(pmh != null){
				pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
				pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
				
				activateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, pmh.getProjectTerminationDate());
				
				ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
				pm.setStatus(Constants.MEMBER_ACTIVE);
				pm.setIsManuallyTerminated(null);
				projectMembersService.updateProjectMember(pm);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to newly added admin as a level2 manager for all existing projectmembers
	 * @author: Naresh Bolisetty
	 * @throws Exception 
	 * 
	 */
	 public void addAdminAsSecondLevelManager(Long locationId,User adminUser,User loginUser) throws Exception{
		
		List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(locationId);
        if(projectsList != null && !projectsList.isEmpty()){
        	for(Projects projects :projectsList){
        		if(StringUtils.isNotEmpty(projects.getStatus()) && projects.getStatus().equalsIgnoreCase(Project_Status.COMPLETED.getValue()))
        			continue;
        		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		    	pah.setProjectId(projects);
		    	pah.setApproverUserId(adminUser);
		    	pah.setLevel(2);
		    	pah.setCreatedBy(loginUser.getUserCode());
		    	pah.setCreatedDate(currentDate());
		    	pah.setUpdatedBy(loginUser.getUserCode());
		    	pah.setUpdatedDate(currentDate());
		    	pah = projectApproverHierarchyService.saveProjectApproverHierarchy(pah);
		    	List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyService.getSubmissionApproverHierarchyByApproverUserId(loginUser.getId());
		    	List<SubmissionApproverHierarchy> freshSahList = new ArrayList<SubmissionApproverHierarchy>();
		    	if(sahList != null && !sahList.isEmpty()){
		    		for(SubmissionApproverHierarchy sah :sahList){
		    			sah.setId(null);
		    			sah.setHeirarchyId(pah);
		    			//Additional Fields are added for de-normalization
						sah.setProjectId(sah.getSubmissionId().getProjects());
						sah.setMasterCandidateId(sah.getSubmissionId().getMasterCandidateId().getId());
						sah.setProjectClientId(pah.getProjectClientsId());
						sah.setOrgLocationId(pah.getProjectClientsId().getOrgLocationsId().getId());
						sah.setOrgId(pah.getProjectClientsId().getOrgLocationsId().getOrg().getId());
						ProjectMembers pm = projectMembersService.getProjectMembersByMasterCandidateIdAndProjectClientId(sah.getSubmissionId().getMasterCandidateId().getId(), pah.getProjectClientsId().getId());
						if(pm != null) {
							sah.setProjectMemberId(pm);
							sah.setCandidateId(pm.getCandidateId());
							User consultantUser = candidateService.getConsultantUserByCandidateIdOrMasterCandidateId(pm.getProjectMemberCandidteId().getId(),pm.getProjectMemberCandidteId().getMasterCandidateId().getId());
							sah.setConsultantUser(consultantUser);
						}
		    			freshSahList.add(sah);
		    		}
		    		submissionApproverHierarchyService.saveAllSubmissionApproverHierarchy(freshSahList);
		    	}
        	}
        }
	}
	
	/**
	 * This method is used to terminate all projectmembers whose project is closed
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void terminateAllProjectMembersByUser(User user){
		List<ProjectMembers> projectMembers;
		try {
			projectMembers = projectMembersService.getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			if(projectMembers != null && !projectMembers.isEmpty()){
				for(ProjectMembers projectMember :projectMembers){
					terminateProjectMemberByProjectMemberId(projectMember.getId(),Primary.NO);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	/**
	 * This method is used to terminate all projectmembers whose project is closed
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void terminateAllProjectMembers(){
		List<ProjectMembers> projectMembers;
		try {
			projectMembers = projectMembersService.getAllTerminatedProjectMembersIdsByTimesheet();
			if(projectMembers != null && !projectMembers.isEmpty()){
				for(ProjectMembers projectMember :projectMembers){
					terminateProjectMemberByProjectMemberId(projectMember.getId(),Primary.NO);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	/**
	 * This method is used to terminate project whose endDate is greaterthan current date
	 * @author: Naresh Bolisetty
	 * 
	 */
	public Projects terminateProject(Projects project) throws Exception{
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(project.getId());
		if(projectMembersList != null && !projectMembersList.isEmpty()){
			 for(ProjectMembers pojectMember : projectMembersList){
				ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pojectMember.getId());
				if(pmh == null){
					pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(pojectMember.getId());
				}
				Date currentDate = new  Date();
				Date terminationDate = null;
				if(pmh.getProjectTerminationDate() != null){
					terminationDate = pmh.getProjectTerminationDate();
				}
				else{
					terminationDate = pmh.getProjectAssignedDate();
				}
				if(currentDate.after(terminationDate)){
					pmh.setProjectTerminationDate(project.getEndDate());
					pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
				}
				terminateProjectMemberByProjectMemberId(pojectMember.getId(),Primary.NO);
			}
		}
		project.setProjectStatus(Project_Status.COMPLETED);
		return project;
	}
	
	
	/**
	 * This method is used to Terminate Project Members whose assigned date is after project end date
	 * @author: Naresh Bolisetty
	 * @throws Exception 
	 * 
	 */
 	public void terminateProjectMembersWhoseAssignedDateOrTerminatedisGreaterThanProjectEndDate(Projects project,User user) throws Exception{
 		if(project.getEndDate() != null){
	 		List<ProjectMembers> pmList =  projectMembersService.getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(project.getId());
		   	   for(ProjectMembers projectMember :pmList){
		   		ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
				 if(pmh == null)  {
					  pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMember.getId());
				 }
				 Date terminatedDate = null;
		   		   if(pmh != null && pmh.getProjectAssignedDate().after(project.getEndDate()) || pmh.getProjectAssignedDate().equals(project.getEndDate())){
				   			 projectMember.setStatus(Constants.MEMBER_IN_ACTIVE);
				   			 projectMember.setIsManuallyTerminated(Primary.NO);
				   			 projectMember.setUpdatedDate(new Date());
					    		 projectMember.setUpdatedBy(user.getUserCode());
					    		 HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
							  	 
								  if(pmh != null){
									  pmh.setProjectTerminationDate(pmh.getProjectAssignedDate());
										Calendar terminateDateCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
										terminateDateCal.setLenient(false);
										terminateDateCal.setTime(pmh.getProjectTerminationDate());
										Calendar terminatedDateMonday = CalendarUtil.getPreviousWeekDayByCalendar(terminateDateCal, "MON",projectMember.getProjects().getOrgLocationsId().getDateFormat());
										terminatedDate = terminatedDateMonday.getTime();
									 
									  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
									  pmh.setUpdatedDate(new Date());
									  pmh.setUpdatedBy(user.getUserCode());
									  memberHistoryList.add(pmh);
									  projectMember.setMemberHistoryList(memberHistoryList);
								  }
								  projectMember = projectMembersService.saveProjectMember(projectMember);
				   		 }
				   		 else{
				   			 HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
								  if(pmh != null){
									  pmh.setProjectTerminationDate(project.getEndDate());
									  terminatedDate = pmh.getProjectTerminationDate();
									  pmh.setUpdatedDate(new Date());
									  pmh.setUpdatedBy(user.getUserCode());
									  memberHistoryList.add(pmh);
									  projectMember.setMemberHistoryList(memberHistoryList);
								  }
								  projectMember = projectMembersService.saveProjectMember(projectMember);
				   		 }
		   		
				   	
				   		inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMember.getId(),terminatedDate);
		   	   }
 		 }
	}
	
 	
 	public  String getCleanHTML(String html) throws Exception{
      	 URL url = new URL(sourceleadUrl);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setDoInput(true);
         conn.setRequestMethod("POST");
         byte data[] = ("html="+(URLEncoder.encode(String.valueOf(html), "utf-8"))).getBytes();
         conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         conn.setRequestProperty("Content-Length", String.valueOf(data.length));
         conn.setDoOutput(true);
         OutputStream os = conn.getOutputStream();
         os.write(data);
         os.flush();
         
         conn.getResponseCode();
         
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String line = null;
         StringBuilder sb = new StringBuilder();
         while((line = br.readLine()) != null) {
            sb.append(line);
         }
         return sb.toString();
     }
	
	/**
	 * This method is used to storeTimesheetSubmissionRecords based on projectMemberAssignDate and projectMemberEndDate(optional)
	 * @author: Naresh Bolisetty
	 * 
	 */
	/*public void storeTimesheetSubmissionRecords(User user){
		try {
			
			*//**
			 * Getting List Of all Projectmembers based on login user who is approver/employee to that particular project
			 *  who didn't have 12 weeks prior/post submission records in DB
			 * 
			 *//*
			List<ProjectMembers> projectMembers =  projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			
			if(projectMembers != null && !projectMembers.isEmpty()){
				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				for(ProjectMembers projectMember :projectMembers){
					
					ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
					
					*//**
					 * Getting startDate of coming week from the existing submission records
					 *
					 *//*
					Date nextMonday = timesheetSubmissionsService.getNextMondayByProjectMemberId(projectMember.getId());
					
					*//**
					 * Calculating startDate for the submission records of this particular projectmember
					*//*
					Date startMondayDate = null;
					if(nextMonday != null){
						startMondayDate =  nextMonday;
					}
					else{
						
						*//**
						 * If he didn't have submission records previous for this project,then ProjectAssignedDate is taken as startDate
						*//*
						startMondayDate = pmh.getProjectAssignedDate();
						
						*//**
						 * Calculating last 12th week monday date 
						*//*
						Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						currentCal.setLenient(false);
						currentCal.setTime(new Date());
						Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "MON");
						Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(currentMonday,-84);
						Date previousMondayDate = previousMonday.getTime();
						*//**
						 * Checking whether projectAssignDate lesserthan last 12th week Monday date,
						 * then last 12th week Monday date is taken as startdate
						*//*
				        if(startMondayDate.before(previousMondayDate)){
				    	   startMondayDate = previousMondayDate;
				        }
					}
								
					Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(startMondayDate);
					Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
					
					
					
					*//**
					 * Calculating endDate for the submission records of this particular projectmember
					*//*
					Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(new Date());
					
					*//**
					 * Calculating next 12th week mondayDate from the current week monday
					*//*
					Calendar endMonday = CalendarUtil.getDateIncrementByincrement(endCal,91);
					

					*//**
					 * checking whether projectMemberEnddate is lesserthan next 12th week monday date then 
					 * projectMemberEnddate is taken as enddate for the submission records
					*//*
					if(pmh.getProjectTerminationDate() != null && pmh.getProjectTerminationDate().before(endMonday.getTime())){
					    endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					    endCal.setLenient(false);
						endCal.setTime(pmh.getProjectTerminationDate());
					    endMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON");
					}
					
					int totalWeeks = 0; 
					Date tsStartDate = null;
					Date tsEndDate = null;
					
					Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					tsCal.setLenient(false);
					
					*//**
					 * Calculating totalweeks b/w startdate and enddate
					 * 
					*//*
					totalWeeks = (int)((endMonday.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
					for (int week = 0; week < totalWeeks; week++) {
						if(tsStartDate == null){
							tsCal.setTime(startMonday.getTime());
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
							tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN").getTime();
						}
						else{
							tsCal.setTime(tsStartDate);
						}
						TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissionsByMemberIdAndStartDate(projectMember.getId(),tsStartDate);
						if(timesheetSubmissions != null) {
							timesheetSubmissions.setStatus(Constants.MEMBER_ACTIVE);
							timesheetSubmissionsList.add(timesheetSubmissions);
						}
						else{
							timesheetSubmissions = new TimesheetSubmissions();
							timesheetSubmissions.setProjectMembers(projectMember);
							timesheetSubmissions.setStartDate(tsStartDate);
							timesheetSubmissions.setEndDate(tsEndDate);
							timesheetSubmissions.setStatus(Constants.MEMBER_ACTIVE);
							timesheetSubmissions.setCreatedDate(new Date());
							timesheetSubmissions.setCreatedBy(user.getUserCode());
							timesheetSubmissions.setUpdatedDate(new Date());
							timesheetSubmissions.setUpdatedBy(user.getUserCode());
							
	                        List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectId(projectMember.getProjects().getId(),projectMember.getEmployee().getUser().getId());
							HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
	                        if(hierarchyList != null && !hierarchyList.isEmpty()){
								for(ProjectApproverHierarchy hierarchy : hierarchyList){
									SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
									saHierarchy.setHeirarchyId(hierarchy);
									saHierarchy.setSubmissionId(timesheetSubmissions);
									saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
									saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
									saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
									saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
									saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
									saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
									saHierarchyList.add(saHierarchy);
								}
								timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
							}
							timesheetSubmissionsList.add(timesheetSubmissions);
						}
						tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON").getTime();
						tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN").getTime();
					}
				}
			
				*//**
				 * Storing all submissions b/w startdate and enddate
				*//*
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	public void highLightMenu(String menuName,HttpServletRequest request){
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(menuName);
		request.getSession().setAttribute("sessionData",sessionData);
	}
	
	/**
	 * Return String Date in Date format
	 * @param dateString
	 * @return
	 */
	public  Date  getDateFormat(String dateString){
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
	    Date date = null;
	    try {
	        date = df.parse(dateString);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return date;
	}
	
	
	   /*
	    * updating the userDetails in the Spock  if we are editing  the user
	    *  Sammeta David 
	    */
	   public void updateSpockDetails( User user ,OrgLocations orgLocations) throws Exception{
	    	 
		    	 try{
			    		 List<OrgSpoc>  orgSpockList = orgSpocService.getOrgSpocByUserIdAndOrgLocationCode(user.getId(),orgLocations.getId());
			    		  if(null != orgSpockList){
					    		 for(OrgSpoc org :orgSpockList){
					    			 orgSpocService.addOrgSpoc(org);
					    			 
					    		 } 
			    		  }
		    	 }
		    	 catch(Exception e){
		    		 e.printStackTrace();
		    	 }
	    	 
	     }
	   
	   /**
		 * This method is used to update USER DB and  ElasticSearch Documents for common user
		 *
		 * @author: Ajith Matta
		 * 
		 */
		public void commonUserUpdate(User user) {

			try {
				List<UserRole> userRole = userRoleService.getUsersListByUserId(user.getId());
				if (null != userRole) {
					for (UserRole role : userRole) {
						userService.saveOrUpdateUserElkDocument(role, null, user.getUserCode());
					}
				}
			} catch (Exception e) {
				logger.info("--exception--" + e);
			}
		}
	   
	
	 /**
	 * this method converts list to Json String
	 * @param obj
	 * @return
	 */
		public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
		
	public String getBackOfficeSummaryFilters(JSONObject jsonObj) throws Exception {
		String subquery1 = "";
		String subquery2 = "";
		String subquery3 = "";

		//only fromTransDate
		 if(jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate")) 
					&& jsonObj.has("toTransDate") && "".equals(jsonObj.getString("toTransDate"))){
			 subquery1 = "and bt.transDate >= :fromTransDate" ;
		 }
		 
		 //only toTransDate
		 else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) 
					&& jsonObj.has("fromTransDate") && "".equals(jsonObj.getString("fromTransDate"))){
				subquery1 = "and bt.transDate <= :toTransDate" ;
		 }
		 
		 //combination of dates
		 else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) && 
					jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate"))){
				subquery1 = "and bt.transDate BETWEEN :fromTransDate and :toTransDate" ;
		 }
		
		 //based on paymentType
		 if(jsonObj.has("paymentTypeId") && jsonObj.getString("paymentTypeId") != null && !"".equals(jsonObj.getString("paymentTypeId"))){
			 String paymentTypeId = jsonObj.getString("paymentTypeId").replaceAll("\\[", "").replaceAll("\\]","");
			 String[] paymentTypes = paymentTypeId.split(",");
			 if (paymentTypes != null && paymentTypes[0] != null && paymentTypes[0].length() > 0) {
				 for (String paymentType : paymentTypes) {
					 paymentType = paymentType.replace("\"", "");
					 subquery2 = subquery2 + " bt.paymentTypeId.id =" + paymentType+" OR ";
				 }
			 }
		 }
		
		if(!subquery2.equalsIgnoreCase("")){
			subquery2 = " (" + subquery2.substring(0, subquery2.length() - 4) + ")";
			subquery2 = " and " +subquery2;
		}
		
		//based on transType
		if(jsonObj.has("transType") && jsonObj.getString("transType") != null && !"".equals(jsonObj.getString("transType"))){
			   String transType = jsonObj.getString("transType").replaceAll("\\[", "").replaceAll("\\]","");
			   String[] transTypes = transType.split(",");
			   if (transTypes != null && transTypes[0] != null && transTypes[0].length() > 0) {
					 for (String trans : transTypes) {
						 trans = trans.replace("\"", "");
						 subquery3= subquery3 + " bt.transType = '"+trans+"' OR ";
					 }
				 }
		 }
		
		if(!subquery3.equalsIgnoreCase("")){
			subquery3 = " (" + subquery3.substring(0, subquery3.length() - 4) + ")";
			subquery3 = " and " +subquery3;
		}
		return  subquery1+subquery2+subquery3;
		
	}

		/**
		 * @author Rajashekar create headers for file
		 * @param createExcelsheet
		 * @param style
		 * @param headers
		 * @param rowCount 
		 * @return sheet
		 */
		public Sheet setExcelHeaders(Sheet createExcelsheet, CellStyle style, String[] headers, int rowCount) {
			Row excelHeader = createExcelsheet.createRow(rowCount);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = excelHeader.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(style);
			}
			return createExcelsheet;
		}

		/**
		 * @author Rajashekar writes font style to headers
		 * @param wb
		 * @return CellStyle
		 */
		public CellStyle getCellStyle(Workbook wb) {
			CellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font font = wb.createFont();
			font.setColor(HSSFColor.BLUE.index);
			style.setFont(font);
			return style;
		}
		


		/**
		 * HashRole is used for Java Code
		 * @author: Naresh Bolisetty
		 * 
		 */
		public boolean hasRole(String role) {
	        // get security context from thread local
			
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication == null)
	            return false;

	        for (GrantedAuthority auth : authentication.getAuthorities()) {
	            if (role.equals(auth.getAuthority()))
	                return true;
	        }

	        return false;
	    }
		
		/**
		 * convertTimeZones is used to convert one timezone to anothet timezone 
		 * @author: Prasanna Vadla
		 * 
		 */
		public static String convertTimeZones(final String fromTimeZoneString, 
		        final String toTimeZoneString, final String fromDateTime) {
		    final DateTimeZone fromTimeZone = DateTimeZone.forID(fromTimeZoneString);
		    final DateTimeZone toTimeZone = DateTimeZone.forID(toTimeZoneString);
		    final DateTime dateTime = new DateTime(fromDateTime, fromTimeZone);

		    final DateTimeFormatter outputFormatter 
		        = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(toTimeZone);
		    return outputFormatter.print(dateTime);
		}
		
		/**
		 * convertTimeZones is used to convert to UTC timezone 
		 * @author Laxman
		 * @param toTimeZoneString
		 * @param fromDateTime
		 * @return
		 */
		public static String convertTimeUTC(final String toTimeZoneString, final String fromDateTime) {
		    final DateTimeZone toTimeZone = DateTimeZone.forID(toTimeZoneString);
		    final DateTime dateTime = new DateTime(fromDateTime);

		    final DateTimeFormatter outputFormatter  = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(toTimeZone);
		    return outputFormatter.print(dateTime);
		}
		
		
		public static Float getCombinedHours(Float hours1,Float hours2){
			float cthHours = hours1;
			float cthHoursValue = (int) cthHours;
			float cthMinutesValue = cthHours - cthHoursValue;
			cthMinutesValue = (float) Math.round(cthMinutesValue * 100) / 100;
			float totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

			float wthHours = hours2;
			float wthHoursValue = (int) wthHours;
			float wthMinutesValue = wthHours - wthHoursValue;
			wthMinutesValue = (float) Math.round(wthMinutesValue * 100) / 100;
			float totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

			float totalMinutes = totalCthMinutes+totalWthMinutes;

			float actualHours = (int) (totalMinutes / 60); 
			float actualMinutes = (totalMinutes % 60)/100;

			String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
			
			return Float.parseFloat(actualTimeString);
		}
		

		public static double getTotalRegularOverTimeHours(double regulraHours,double overTimeHours){
			double cthHours = regulraHours;
			double cthHoursValue = (int) cthHours;
			double cthMinutesValue = cthHours - cthHoursValue;
			double totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

			double wthHours = overTimeHours;
			double wthHoursValue = (int) wthHours;
			double wthMinutesValue = wthHours - wthHoursValue;
			double totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

			double totalMinutes = totalCthMinutes+totalWthMinutes;

			double actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
			double actualMinutes = (totalMinutes % 60)/100;

			String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
			
			return Double.parseDouble(actualTimeString);
		}
		
	/*	public static String getHoursDisplayFormat(Float hours2){
			float cthHours = 0;
			float cthHoursValue = (int) cthHours;
			float cthMinutesValue = cthHours - cthHoursValue;
			float totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

			float wthHours = hours2;
			float wthHoursValue = (int) wthHours;
			float wthMinutesValue = wthHours - wthHoursValue;
			float totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

			float totalMinutes = totalCthMinutes+totalWthMinutes;
			return paymentSuccessReturnEmail;
		}*/
		public static String getHoursDisplayFormat(Float hours2,Float totalMinutes){
			if(totalMinutes == null) {
				float wthHours = hours2;
				float wthHoursValue = (int) wthHours;
				float wthMinutesValue = wthHours - wthHoursValue;
				wthMinutesValue = (float) Math.round(wthMinutesValue * 100) / 100;
			    totalMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);
			}
			float actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
			float actualMinutes = (totalMinutes % 60)/100;
			String actualTimeString = null;
			if(actualMinutes != 0){
				actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
			}
			else{
				actualTimeString = String.format("%.0f", (actualHours+actualMinutes)).concat(".0");
			}
			
			return actualTimeString;
		}
		
		public static String getTimeFormat(Float hours2,Float totalMinutes) {
			if(totalMinutes == null) {
				float wthHours = hours2;
				float wthHoursValue = (int) wthHours;
				float wthMinutesValue = wthHours - wthHoursValue;
				wthMinutesValue = (float) Math.round(wthMinutesValue * 100) / 100;
			    totalMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);
			}
			int hours = (int) (totalMinutes / 60); 
			int mins = (int)(totalMinutes % 60);
	        String finalTime = (hours < 10 ? "0" + hours : hours) + ":" + (mins < 10 ? "0" + mins : mins);
	        return finalTime;
		}

		public void updateClientVendorElk(OrgRelations orgRelation, OrgLocations orgLocations, User user, String responderType) {
			try {
				  ClientVendorIndex  clientVendorIndex = new ClientVendorIndex();
					OrgLocations responderOrgLocations = null;
					if(orgRelation.getLocationId().getId().equals(orgLocations.getId())) {
						responderOrgLocations = orgRelation.getResponderLocationId();
					}else {
						responderOrgLocations = orgRelation.getLocationId();
					}
				  
					// Elastic Search Document Saving
					clientVendorIndex = clientVendorIndexHelper.getClientVendorIndexFromCandidate(null,
							responderOrgLocations, responderType, null, orgRelation, null, user, orgLocations);
				  sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,orgRelation.getId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			  
			
		}
		
		//common method for saveElkRequirement
		
		public RequirementDTO getRequirementDTO(Requirement requirement) throws Exception
		{
			RequirementDTO requirementDTO = new RequirementDTO();
			requirementDTO.setRequirementId(requirement.getId().toString());
			requirementDTO.setOrgLocationId(requirement.getOrgLocationId().getId());
			requirementDTO.setJobTitle(requirement.getJobTitle());
			requirementDTO.setJobType(requirement.getJobType());
			requirementDTO.setOtherJobType(requirement.getOtherJobType());
			requirementDTO.setIndustryName(requirement.getIndustryName());
			requirementDTO.setTravelAllowance(requirement.getTravelAllowance());
			requirementDTO.setCoolingOff(requirement.getReqCoolingOffPeriod());
			requirementDTO.setReleaseToAllUsers(requirement.getReleaseJobToAllUsers());
			requirementDTO.setProjectDurationMonths(requirement.getProjectDurationMonths());
			requirementDTO.setProjectDurationYears(requirement.getProjectDurationYears());
			requirementDTO.setIsreceivingApplicants(requirement.getIsreceivingApplicants());
			requirementDTO.setGeneratedId(requirement.getGeneratedId());
			if(requirement.getProjectClientID() != null){
			requirementDTO.setProjectClientID(requirement.getProjectClientID().toString());
			}
			if(requirement.getMedicalClearence() !=null){
			requirementDTO.setMedicalClearence(requirement.getMedicalClearence());
		}
			if(requirement.getSecurityClearance() !=null){
			requirementDTO.setSecurityClearance(requirement.getSecurityClearance());
		}
			if(requirement.getOverTime() !=null){
			requirementDTO.setOverTime(requirement.getOverTime());
		}
			if(requirement.getBackgroundCheck() !=null){
			requirementDTO.setBackgroundCheck(requirement.getBackgroundCheck());
			}
			if(requirement.getGender() !=null){
			requirementDTO.setGender(requirement.getGender());
			}
			if(requirement.getExperienceLevel() !=null){
			requirementDTO.setExperienceLevel(requirement.getExperienceLevel().toString());
			}
			if (null != requirement.getDescription() && !"".equals(requirement.getDescription())) {
				requirementDTO.setDescription(requirement.getDescription());
			}
			if (null != requirement.getMaxRate() && !"".equals(requirement.getMaxRate())) {
				requirementDTO.setMaxRate(requirement.getMaxRate());
			}
			if (null != requirement.getRate() && !"".equals(requirement.getRate())) {
				requirementDTO.setRate(requirement.getRate());
			}
			if (null != requirement.getRateType() && !"".equals(requirement.getRateType())) {
				requirementDTO.setRateType(requirement.getRateType());
			}
			if (null != requirement.getMinExp()) {
				requirementDTO.setMinExp(requirement.getMinExp());
			}
			if (null != requirement.getMaxExp()) {
				requirementDTO.setMaxExp(requirement.getMaxExp());
			}
			requirementDTO.setRequirementStatus(requirement.getRequirementSubmissionStatus().toString());
			// requirementIndex.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionPreviousStatus().toString());
			requirementDTO.setCountry(requirement.getCountry());
			requirementDTO.setState(requirement.getState());
			requirementDTO.setCity(requirement.getCity());
			requirementDTO.setLocation(requirement.getLocation());
			requirementDTO.setZipCode(requirement.getZipCode());
			requirementDTO.setReqCreatedBy(requirement.getCreatedBy().getId());
			requirementDTO.setReqUpdatedBy(requirement.getUpdatedBy());
			if (requirement.getManagerUserId() != null) {
				requirementDTO.setManagerUserId(requirement.getManagerUserId().getId().toString());
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (null != requirement.getClosedDate()) {
				String reqclosedDate = dateFormat.format(requirement.getClosedDate());
				requirementDTO.setClosedDate(reqclosedDate);
			}
			String reqcreatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementDTO.setCreatedDate(reqcreatedDate);
			String reqUpDatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementDTO.setUpdatedDate(reqUpDatedDate);
			Long userId = requirement.getManagerUserId().getId();
			User ownerUser = userService.getUserById(userId);
			StringBuilder sbilder = new StringBuilder();
			if (ownerUser != null) {
				if (ownerUser.getFirstName() != null && ownerUser.getLastName() != null) {
					sbilder.append((ownerUser.getFirstName())).append(" ").append(ownerUser.getLastName());
				}
				else {
					sbilder.append((ownerUser.getFirstName()));
				}
				requirementDTO.setManager(sbilder.toString());
			}
			// to store approver of the req in elk
			if (requirementDTO.getParallelApprover() != null
					&& !requirementDTO.getParallelApprover().isEmpty()) {
				String[] reqApproverUserId = requirementDTO.getParallelApprover().toArray(new String[requirementDTO.getParallelApprover().size()]);
				requirementDTO.setReqLevel1ApproverUserId(reqApproverUserId);
			}
			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
				String sequentialApprovers1 = requirementDTO.getSequentialApprover1();
				String sequentialApprovers2 = requirementDTO.getSequentialApprover2();
				requirementDTO.setReqLevel1ApproverUserId(sequentialApprovers1.split(","));
				requirementDTO.setReqLevel2ApproverUserId(sequentialApprovers2.split(","));
			}
			if (StringUtils.isNotBlank(requirementDTO.getAssignedToMaxCount())) {
				if (StringUtils.isNotBlank(requirementDTO.getAssignedTeamId())) {
					String assignedTeamId = requirementDTO.getAssignedTeamId();
					String[] teamOwnerUserId = assignedTeamId.split(",");
					requirementDTO.setTeamOwnerUserId(teamOwnerUserId);
				}
				if (requirementDTO.getAssignUsers() != null) {
					String[] teamAssignUserId = requirementDTO.getAssignUsers().toArray(new String[requirementDTO.getAssignUsers().size()]);
					requirementDTO.setTeamAssignUserId(teamAssignUserId);
				}
			}
			// requirementIndex.setRequirementSubmissionStatus(requirement.getRequirementSubmissionStatus().toString());
			requirementDTO.setIsTemplate(requirement.getIsTemplate());
			// education
			if (null != requirement.getEducation()) {
				String[] education = requirement.getEducation().split(",");
				requirementDTO.setEducation(Arrays.toString(education));
			}
			if (requirement.getClientId() != null) {
				Requirement requirement1 = requirementService.getRequirementByRequirementId(requirement.getId());
				StringBuilder sb = new StringBuilder();
				if (Constants.CLIENT.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					if (requirement1.getClientId().getResponderLocationId() != null) {
						requirementDTO.setClient(requirement1.getClientId().getResponderLocationId().getOrgLocationName());
					}
				}
				if (Constants.VENDOR.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					requirementDTO.setClient(requirement1.getClientId().getLocationId().getOrgLocationName());
				}
				// Prasad salina
				if (null != requirement1.getSpocId()) {
					if (requirement1.getSpocId().getClientUser() != null
							&& requirement1.getSpocId().getClientUser().getLastName() != null
							&& !"".equals(requirement1.getSpocId().getClientUser().getLastName())) {
						sb.append(requirement1.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement1.getSpocId().getClientUser().getLastName());
					}
					else {
						sb.append(requirement1.getSpocId().getClientUser().getFirstName());
					}
					requirementDTO.setClientContactName(sb.toString());
					requirementDTO.setClientContactEmail(requirement1.getSpocId().getClientUser().getEmail());
				}
				// requirementIndex.setClientContactEmail(requirement1.getClientId().getLocationId().getUser().getEmail());
			}
			// requirementIndex.setClient(requirement.getClientName());
			// skill pending
			if (null != requirement.getSkills()) {
				String[] skills = requirement.getSkills().split(",");
				requirementDTO.setSkills(Arrays.toString(skills));
			}
			if (null != requirement.getNoticePeriod()) {
				String[] noticePeriod = requirement.getNoticePeriod().split(",");
				requirementDTO.setNoticePeriod(Arrays.asList(noticePeriod));
			}
			requirementDTO.setJobId(requirement.getJobId());
			requirementDTO.setPriority(requirement.getPriority());
			requirementDTO.setCurrentRateCurrency(requirement.getCurrencyCode());
			requirementDTO.setPositions(requirement.getPositions());
			if (null != requirement.getTotalApplicants()) {
				requirementDTO.setNumberOfApplicants(requirement.getTotalApplicants().toString());
			}
			requirementDTO.setReleasedBy(requirement.getReleasedBy());
			requirementDTO.setRequirementUUID(requirement.getRequirementUUID());
			if (null != requirement.getIsPublic()) {
				requirementDTO.setIsPublicJob(requirement.getIsPublic());
			}
			else {
				requirementDTO.setIsPublicJob("N");
			}
			if (null != requirement.getIsCrowdSourcing()) {
				requirementDTO.setIsCrowdSourcingJob(requirement.getIsCrowdSourcing());
			}
			else {
				requirementDTO.setIsCrowdSourcingJob("N");
			}
			if (null != requirement.getPostedOrgName()
					&& !requirement.getPostedOrgName().isEmpty()) {
				requirementDTO.setPostedOrg(requirement.getPostedOrgName());
			}
			if (null != requirement.getAppliedProfessionals()
					&& !"".equalsIgnoreCase(requirement.getAppliedProfessionals())) {
				String[] appliedProfessionals = requirement.getAppliedProfessionals().split(",");
				requirementDTO.setAppliedProfessionals(appliedProfessionals);
			}
			if (null != requirement.getAppliedRecruiters()
					&& !"".equalsIgnoreCase(requirement.getAppliedRecruiters())) {
				String[] appliedRecruiters = requirement.getAppliedRecruiters().split(",");
				requirementDTO.setAppliedRecruiters(appliedRecruiters);
			}
			if (null != requirement.getReqlevel1ApproverStatus()
					&& !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())) {
				requirementDTO.setReqLevel1ApproverStatus(requirement.getReqlevel1ApproverStatus());
			}
			if (null != requirement.getReqlevel2ApproverStatus()
					&& !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())) {
				requirementDTO.setReqLevel2ApproverStatus(requirement.getReqlevel2ApproverStatus());
			}
			if (StringUtils.isNotBlank(requirement.getRewardType())) {
				requirementDTO.setRewardType(requirement.getRewardType());
			}
			if (StringUtils.isNotBlank(requirement.getRewardPercentage())) {
				requirementDTO.setRewardPercentage(requirement.getRewardPercentage());
			}
			if (StringUtils.isNotBlank(requirement.getRewardFlat())) {
				requirementDTO.setRewardFlat(requirement.getRewardFlat());
			}
			if (StringUtils.isNotBlank(requirement.getRewardFlatRateType())) {
				requirementDTO.setRewardFlatRateType(requirement.getRewardFlatRateType());
			}
			if (StringUtils.isNotBlank(requirement.getRewardGuaranteePeriod())) {
				requirementDTO.setRewardGuaranteePeriod(requirement.getRewardGuaranteePeriod());
			}
			if (null != requirement.getVisaType()) {
				String[] visaTypes = requirement.getVisaType().split(",");
				requirementDTO.setVisa(Arrays.asList(visaTypes));
			}
			if (null != requirement.getGeneratedId()
					&& !"".equalsIgnoreCase(requirement.getGeneratedId())) {
				requirementDTO.setGeneratedId(requirement.getGeneratedId());
			}
			if(requirement.getLevel2ApproverUserId() != null) {
				requirementDTO.setSequentialApprover1(requirement.getLevel1ApproverUserId()[0]);
				requirementDTO.setSequentialApprover2(requirement.getLevel2ApproverUserId()[0]);
			} else if(requirement.getLevel1ApproverUserId()!=null){
				requirementDTO.setParallelApprover(Arrays.asList(requirement.getLevel1ApproverUserId()));
			}
			return requirementDTO;
		}
		
		public static byte[] readBytesFromFile(String filePath)
		{
			FileInputStream fileInputStream = null;
			byte[] bytesArray = null;
			try {
				File file = new File(filePath);
				bytesArray = new byte[(int) file.length()];
				// read file into bytes[]
				fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytesArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bytesArray;
		}
		/**
		 * Get Number of days Between two given dates including boundaries
		 * @param fromDate
		 * @param toDate
		 * @return
		 * @throws ParseException
		 */
		public int getNumberOfDaysBetweenTwoDates(Date fromDate, Date toDate) throws ParseException {
				long diff = toDate.getTime() - fromDate.getTime();
				int  nuberOfDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				return nuberOfDays+1;
			}
		
		/**
		 * Get Number of days Between two given dates excluding boundaries
		 * @param fromDate
		 * @param toDate
		 * @return int
		 * @throws ParseException
		 */
		public int diffBtwTowDates(Date fromDate, Date toDate) throws ParseException {
				long diff = toDate.getTime() - fromDate.getTime();
				int  nuberOfDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				return nuberOfDays;
			}
		
		public  Date[] getStartDateEndDateByYear(int year){
			Date[] dates = new Date[2];
		    Calendar calendarStart=Calendar.getInstance();
		    calendarStart.set(Calendar.YEAR,year);
		    calendarStart.set(Calendar.MONTH,0);
		    calendarStart.set(Calendar.DAY_OF_MONTH,1);
		    // returning the first date
		    dates[0] = calendarStart.getTime();
		    Calendar calendarEnd=Calendar.getInstance();
		    calendarEnd.set(Calendar.YEAR,year);
		    calendarEnd.set(Calendar.MONTH,11);
		    calendarEnd.set(Calendar.DAY_OF_MONTH,31);
		    // returning the last date
		    dates[1] = calendarEnd.getTime();
			return dates;
		}
		
		public  Date[] getMonthStartDateEndDateByMonth(int month) {
			Date[] dates = new Date[2];
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH));
			dates[0] = cal.getTime();
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			dates[1] = cal.getTime();
			return dates;
	}
		public  Date[] getMonthStartDateEndDateByYearAndMonth(int year,int month) {
			Calendar calendar = Calendar.getInstance();
			Date[] dates = new Date[2];
//			int yearpart = 2017;
//			int monthPart = 9;
			int dateDay = 1;
			calendar.set(year, month, dateDay);
			int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			dates[0] = calendar.getTime();
			calendar.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
			dates[1] = calendar.getTime();
			return dates;
	}
		
		
		
		public Candidate getVendorCandidateForCandidateCreation(CandidateDTO candidateDTO,OrgRelations orgRelation,OrgLocations orgLocations,User loginUser) throws Exception{
			  Candidate candidate = null;
			  OrgLocations vendorOrgLocation = null;
			  if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				  vendorOrgLocation = orgRelation.getResponderLocationId();
			  }else if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				  vendorOrgLocation = orgRelation.getLocationId();
			  }
			  
			  if(StringUtils.isNotBlank(candidateDTO.getEmail())){
				  candidate = candidateService.findByCandidateEmail(candidateDTO.getEmail().trim(),vendorOrgLocation.getId());
			  }else if(StringUtils.isNotBlank(candidateDTO.getEmailId())){
				  candidate = candidateService.findByCandidateEmail(candidateDTO.getEmailId().trim(),vendorOrgLocation.getId());
			  }
			  
			  if(candidate == null){
				  candidate = new Candidate();
				  
				  MasterCandidate masterCandidate = null;
					masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
					if(null == masterCandidate){
						masterCandidate = masterCandidateService.storeMasterCandidateByCandidateDTO(candidateDTO, loginUser);
					}
					candidate.setMasterCandidateId(masterCandidate);
				  
					candidate.setOrgLocationId(vendorOrgLocation);
					candidate.setCandidateType("Candidate");
					candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					
					if(StringUtils.isNotBlank(candidateDTO.getFirstName())){
						candidate.setFirstName(candidateDTO.getFirstName());
					}
				
					if(StringUtils.isNotBlank(candidateDTO.getLastName())){
						candidate.setLastName(candidateDTO.getLastName());
					}
					
					if(StringUtils.isNotBlank(candidateDTO.getEmail())){
						candidate.setEmail(candidateDTO.getEmail());
					}
					
					if(StringUtils.isNotBlank(candidateDTO.getCountry())){
						candidate.setCountry(candidateDTO.getCountry());
					}
				
				candidate.setIsAssociate(Constants.NO);
				candidate.setDesignationType("W-2");
				if(candidateDTO.getWorkLocation() != null) {
					OrgLocations workLocation = new OrgLocations();
					workLocation.setId(candidateDTO.getWorkLocation());
					candidate.setWorkLocation(workLocation);
				}
				candidate.setStatus(1);
	  			Candidate savedCandidate = candidateService.saveOrUpdateCandidate(candidate);
	  			candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
			  }else {
				  if(candidateDTO.getWorkLocation() != null) {
						OrgLocations workLocation = new OrgLocations();
						workLocation.setId(candidateDTO.getWorkLocation());
						candidate.setWorkLocation(workLocation);
					}
				  candidateService.saveOrUpdateCandidateElkDocument(candidate);
			  }
			  
			  return candidate;
		  }
		
public void storeVendorOnboardDefaultTemplate(User user,OrgLocations orgLocations) throws Exception{
			
			VendorOnboardingTemplate onboardTemplate = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(1L);
			if(onboardTemplate != null) {
				 VendorOnboardingTemplate vendorOnboardTemplate = new VendorOnboardingTemplate();
				 vendorOnboardTemplate.setTemplateName(orgLocations.getOrgLocationName()+" Default Vendor Onboard Template");
			 	 vendorOnboardTemplate.setOrgLocationId(orgLocations);
				 vendorOnboardTemplate.setTemplateStatus("Finished");
				 vendorOnboardTemplate.setIsDefaultTemplate(Constants.YES);
				 vendorOnboardTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
				 vendorOnboardTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			 	 vendorOnboardTemplate.setCreatedBy(user);
			 	 vendorOnboardTemplate.setCreatedDate(new Date());
			 	 vendorOnboardTemplate.setUpdatedBy(user);
			 	 vendorOnboardTemplate.setUpdatedDate(new Date());
			 	 Set<VendorOnboardingTemplateDocuments> templateDocumentsList = storeOnboardDocuments(user,orgLocations,vendorOnboardTemplate,onboardTemplate);
			 	 vendorOnboardTemplate.setOnboardTemplateDocuments(templateDocumentsList);
			 	 vendorOnboardTemplateService.saveVendorOnBoardingTemplate(vendorOnboardTemplate);
			 	 
			}
			
		}
		
		
		private Set<VendorOnboardingTemplateDocuments> storeOnboardDocuments(User user,OrgLocations orgLocations,VendorOnboardingTemplate vendorOnboardTemplate,VendorOnboardingTemplate onboardTemplate) throws  Exception {
			
			 HashSet<VendorOnboardingTemplateDocuments> docuemntsList = new HashSet<VendorOnboardingTemplateDocuments>(); 
			 
			 List<VendorOnboardingTemplateDocuments> onboardDocumentsList = vendorOnboardTemplateDocumentsService.getVendorOnBoardTemplateDocumentsByTemplateId(onboardTemplate.getId());
			 if(onboardDocumentsList != null && onboardDocumentsList.size() > 0) {
				
				 
				 for(VendorOnboardingTemplateDocuments onboardDoc : onboardDocumentsList) {
					 VendorOnboardingTemplateDocuments documents = new VendorOnboardingTemplateDocuments();
					    documents.setVendorOnboardingTemplateId(vendorOnboardTemplate);
					    documents.setDocumentType("Documents");
					    documents.setDocumentName(onboardDoc.getDocumentName());
					    documents.setIsRequired(onboardDoc.getIsRequired());
		                documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                documents.setCreatedDate(CommonUtil.currentDate());
		                documents.setCreatedBy(user);
		                documents.setUpdatedDate(CommonUtil.currentDate());
		                documents.setUpdatedBy(user);
		                docuemntsList.add(documents);
				 }
			 }
			 return docuemntsList;
		}
		
		
		public Candidate getVendorCandidate(JSONObject jsonObj,OrgRelations orgRelation,OrgLocations orgLocations,User loginUser) throws Exception{
			  Candidate candidate = null;
			  OrgLocations vendorOrgLocation = null;
			  if(orgRelation.getInitiatorType().equalsIgnoreCase("Client") && orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				  vendorOrgLocation = orgRelation.getResponderLocationId();
			  }else if(orgRelation.getInitiatorType().equalsIgnoreCase("Vendor") && orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				  vendorOrgLocation = orgRelation.getLocationId();
			  }
			  
			  if(jsonObj.has("email") && jsonObj.getString("email") != null){
				  candidate = candidateService.findByCandidateEmail(jsonObj.getString("email").trim(),vendorOrgLocation.getId());
			  }else if(jsonObj.has("EmailId") && jsonObj.getString("EmailId") != null){
				  candidate = candidateService.findByCandidateEmail(jsonObj.getString("EmailId").trim(),vendorOrgLocation.getId());
			  }
			  
			  if(candidate == null){
				  candidate = new Candidate();
				  
				  MasterCandidate masterCandidate = null;
					masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
					if(null == masterCandidate){
						masterCandidate = masterCandidateService.storeMasterCandidateByJsonObject(jsonObj, loginUser,orgLocations);
					}
					candidate.setMasterCandidateId(masterCandidate);
				  
					candidate.setOrgLocationId(vendorOrgLocation);
					candidate.setCandidateType("Candidate");
					candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					
					if(jsonObj.has("firstName") && jsonObj.getString("firstName") !=null){
						candidate.setFirstName(jsonObj.getString("firstName"));
					}else if(jsonObj.has("FirstName") && jsonObj.getString("FirstName") !=null){
						candidate.setFirstName(jsonObj.getString("FirstName"));
					}
				
					if(jsonObj.has("lastName") && jsonObj.getString("lastName") !=null){
						candidate.setLastName(jsonObj.getString("lastName"));
					}else if(jsonObj.has("LastName") && jsonObj.getString("LastName") !=null){
						candidate.setLastName(jsonObj.getString("LastName"));
					}
					
					if(jsonObj.has("email") && jsonObj.getString("email") !=null){
						candidate.setEmail(jsonObj.getString("email"));
					}else if(jsonObj.has("EmailId") && jsonObj.getString("EmailId") != null && !jsonObj.getString("EmailId").isEmpty()){
						candidate.setEmail(jsonObj.getString("EmailId"));
					}
					
					if(jsonObj.has("location") && jsonObj.getString("location") != null && !jsonObj.getString("location").isEmpty()){
			        	candidate.setLocation(jsonObj.getString("location"));
					}
						
			        if(jsonObj.has("country") && jsonObj.getString("country") != null && !jsonObj.getString("country").isEmpty()){
			        	candidate.setCountry(jsonObj.getString("country"));
					}
					 
					if(jsonObj.has("state") && jsonObj.getString("state") != null && !jsonObj.getString("state").isEmpty()){
						 candidate.setState(jsonObj.getString("state"));
					}
					 
					if(jsonObj.has("city") && jsonObj.getString("city") != null && !jsonObj.getString("city").isEmpty()){
						 candidate.setCity(jsonObj.getString("city"));
					}
					
					 if(jsonObj.has("zipcode") && jsonObj.getString("zipcode") != null && !jsonObj.getString("zipcode").isEmpty()){
				        	candidate.setZipCode(jsonObj.getString("zipcode"));
					}
					 
					 if(jsonObj.has("PhoneNumber") && jsonObj.getString("PhoneNumber") != null && !jsonObj.getString("PhoneNumber").isEmpty()){
						 candidate.setMobilePhone(jsonObj.getString("PhoneNumber"));
					}
					
					if(jsonObj.has("AlternateNumber") && jsonObj.getString("AlternateNumber") != null && !jsonObj.getString("AlternateNumber").isEmpty()){
						 candidate.setAlternateMobilePhone(jsonObj.getString("AlternateNumber"));
					}
					
					
					if(jsonObj.has("currentCTCRateType") && jsonObj.getString("currentCTCRateType") != null && !jsonObj.getString("currentCTCRateType").isEmpty()){
						candidate.setCurrentRateCurrency(jsonObj.getString("currentCTCRateType"));
					}
					
					if(jsonObj.has("CurrentCTC") && jsonObj.getString("CurrentCTC") != null && !jsonObj.getString("CurrentCTC").isEmpty()){
						candidate.setCurrentRate(jsonObj.getString("CurrentCTC"));
					}
					
					if(jsonObj.has("currentCTCRatePer") && jsonObj.getString("currentCTCRatePer") != null && !jsonObj.getString("currentCTCRatePer").isEmpty()){
						 candidate.setCurrentRateType(jsonObj.getString("currentCTCRatePer"));
					}
					
					if(jsonObj.has("expectedCTCRateType") && jsonObj.getString("expectedCTCRateType") != null && !jsonObj.getString("expectedCTCRateType").isEmpty()){
						 candidate.setExpectedRateCurrencey(jsonObj.getString("expectedCTCRateType"));
						 
					}
					 
					if(jsonObj.has("ExpectedCTC") && jsonObj.getString("ExpectedCTC") != null && !jsonObj.getString("ExpectedCTC").isEmpty()){
						 candidate.setExpectedRate(jsonObj.getString("ExpectedCTC"));
						 
					}
					
					if(jsonObj.has("expectedCTCRatePer") && jsonObj.getString("expectedCTCRatePer") != null && !jsonObj.getString("expectedCTCRatePer").isEmpty()){
						 candidate.setRateType(jsonObj.getString("expectedCTCRatePer"));
					}
					
					if(jsonObj.has("NoticePeriod") && jsonObj.getString("NoticePeriod") != null && !jsonObj.getString("NoticePeriod").isEmpty()){
						 candidate.setNoticePeriod(jsonObj.getString("NoticePeriod"));
						 
					}
					
					if(jsonObj.has("Designation") && jsonObj.getString("Designation") != null && !jsonObj.getString("Designation").isEmpty()){
						candidate.setDesignation(jsonObj.getString("Designation"));
					}
					 if(jsonObj.has("WorkExperience") && jsonObj.getString("WorkExperience") != null && !jsonObj.getString("WorkExperience").isEmpty()){
						 candidate.setExperienceYears(jsonObj.getString("WorkExperience"));
					 }
					 
					 if(jsonObj.has("monthExp") && jsonObj.getString("monthExp") != null && !jsonObj.getString("monthExp").isEmpty()){
						 candidate.setExperienceMonths(jsonObj.getString("monthExp"));
					 }
					 
					 candidate.setDesignationType("Full Time");
					
					if(jsonObj.has("primarySkill") && jsonObj.getString("primarySkill") != null && !jsonObj.getString("primarySkill").isEmpty()){
						 candidate.setPrimarySkill(jsonObj.getString("primarySkill"));
					}
					 
					 if(jsonObj.has("candidateSkills") && jsonObj.getString("candidateSkills") != null && !jsonObj.getString("candidateSkills").isEmpty()){
						  
						 String candidateSkills = null;
							try{
					    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
					    		   candidateSkills = jsonArray.toString();
					    	  }
					    	  catch(JSONException e){
					    		  candidateSkills= jsonObj.getString("candidateSkills");
					    	  }
							candidateSkills = candidateSkills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
							
						 
						 candidate.setSkillsName(candidateSkills);
						 
					
					 }
				
				candidate.setIsAssociate(Constants.NO);
				candidate.setStatus(1);
	  			candidateService.saveOrUpdateCandidate(candidate);
	  			candidateService.saveOrUpdateCandidateElkDocument(candidate);
			  }
			  
			  return candidate;
		  }
		
		

		public Candidate getC2CVendorCandidate(Candidate vendorCandidate,OrgRelations orgRelation,OrgLocations orgLocations,User loginUser) throws Exception{
			  Candidate candidate = null;
			  OrgLocations vendorOrgLocation = null;
			  if(orgRelation.getInitiatorType().equalsIgnoreCase("Client") && orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				  vendorOrgLocation = orgRelation.getResponderLocationId();
			  }else if(orgRelation.getInitiatorType().equalsIgnoreCase("Vendor") && orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				  vendorOrgLocation = orgRelation.getLocationId();
			  }
			  
			  candidate = candidateService.findByCandidateEmail(vendorCandidate.getEmail(),vendorOrgLocation.getId());
			  
			  if(candidate == null){
				  candidate = new Candidate();
				 
					candidate.setMasterCandidateId(vendorCandidate.getMasterCandidateId());
				  
					candidate.setOrgLocationId(vendorOrgLocation);
					candidate.setCandidateType("Candidate");
					candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					
					candidate.setFirstName(vendorCandidate.getFirstName());
					candidate.setLastName(vendorCandidate.getLastName());
					candidate.setEmail(vendorCandidate.getEmail());
					candidate.setLocation(vendorCandidate.getLocation());
					candidate.setCountry(vendorCandidate.getCountry());
					candidate.setState(vendorCandidate.getState());
					candidate.setCity(vendorCandidate.getCity());
					candidate.setZipCode(vendorCandidate.getZipCode());
					candidate.setMobilePhone(vendorCandidate.getMobilePhone());
					candidate.setAlternateMobilePhone(vendorCandidate.getAlternateMobilePhone());
					candidate.setDesignationType("Full Time");
					candidate.setSkillsName(vendorCandidate.getSkills());
					 
					candidate.setIsAssociate(Constants.NO);
					candidate.setStatus(1);
		  			candidateService.saveOrUpdateCandidate(candidate);
		  			candidateService.saveOrUpdateCandidateElkDocument(candidate);
			  }
			  
			  return candidate;
		  }

		/**
		 * returns all the dates between two dates as String
		 * @param startDate
		 * @param endDate
		 * @return String
		 * @throws ParseException
		 */
	public String getDaysBetweenDates(Date startDate, Date endDate,String orgFormat) throws ParseException {
		List<Date> dates = new ArrayList<Date>();
		List<String> dateStr = new ArrayList<>();
		DateFormat formatter;
		formatter = new SimpleDateFormat(orgFormat);
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
			dates.add(new Date(curTime));
			curTime += interval;
		}
		for (int i = 0; i < dates.size(); i++) {
			Date lDate = (Date) dates.get(i);
			String ds = formatter.format(lDate);
			dateStr.add(ds);
		}
		String datesStrLisr = dateStr.stream().collect(Collectors.joining(","));
		return datesStrLisr;
	}
	
	public void onboardCreationCandidateGeneratedIdUpdated(Candidate candidate, OrgLocations locations) throws Exception{
		
		if(candidate!= null && (candidate.getGeneratedId() == null || candidate.getGeneratedId().isEmpty())){
			StringBuilder generatedIDBuild = new StringBuilder();
			candidate.setGeneratedId(generatedIDBuild.append(locations.getGeneratedUserId()).append("CD1").toString());
			candidateService.saveOrUpdateCandidate(candidate);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
		}
		
	}
	
	public void saveDefaultContractTemplate(Org org, OrgLocations orgLocations, User user,String template,String templateType) throws Exception {
		 ContractsTemplate contractsTemplate = new ContractsTemplate();
		 contractsTemplate.setTemplateName(templateType);
		 contractsTemplate.setDescription(templateType);
		 contractsTemplate.setTemplateContent(DefaultContractTemplateUtil.getDefaultTemplates(templateType));
		 contractsTemplate.setOrgLocationId(orgLocations);
		 contractsTemplate.setTemplateType(templateType);
		 contractsTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		 contractsTemplate.setTemplateStatus(Constants.MEMBER_IN_ACTIVE);
		 contractsTemplate.setUpdatedBy(user.getUserCode());
		 contractsTemplate.setUpdatedDate(new Date());
		 contractsTemplate.setCreatedBy(user.getUserCode());
		 contractsTemplate.setCreatedDate(new Date());
		 contractsTemplateService.saveContractTemplate(contractsTemplate);
	}
	
	public List<UserDTO> getRoleActionUsers(String roleActionName,Long orgLocationId) throws Exception{
		
		List<UserDTO> usersDTOList = new ArrayList<>();
		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(roleActionName);
		List<User> usersList = userService.getUserListByRoleActionIdAndOrgLocationId(action.getId(), orgLocationId);
		for(User user : usersList) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setName(user.getFullName());
			UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocationId);
			userDTO.setUserRole(userRole.getRoles().getDisplayName());
			usersDTOList.add(userDTO);
		}
		return usersDTOList;
	}
	
	public boolean isProjectAccessableToUser(String projectUUID,String projectClientUUID,User loginUser,OrgLocations orgLocations,String loginUserRole,Long candidateId,String isGlobalAccess) {
		boolean flag = false;
		try {
			ProjectClients projectClients = projectClientService.getProjectClientsByUUID(projectClientUUID);
			if(projectClients != null) {
				if((projectClients.getOrgLocationsId().getId().equals(orgLocations.getId())) || (StringUtils.isNotBlank(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase(Constants.YES))) {
					if(StringUtils.isNotBlank(loginUserRole) && (!loginUserRole.equalsIgnoreCase("ROLE_ADMIN") && !loginUserRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN"))) {
						ProjectApproverHierarchy approverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(loginUser.getId(), projectClients.getId());
						ExpenseProjectApproverHierarchy expenseApproverHierarchy = expenseProjectApproverHierarchyService.getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(loginUser.getId(), projectClients.getId());
						ProjectMembers pm = projectMembersService.getProjectMembersByCandidateIdAndProjectClientId(candidateId, projectClients.getId());
						Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
						if(expenseApproverHierarchy != null || approverHierarchy != null || pm != null || projects.getCreatedBy().equalsIgnoreCase(loginUser.getUserCode()) || isGlobalAccess.equalsIgnoreCase(Constants.YES)) {
							flag = true;
						}
					}else {
						flag = true;
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public List<SourceleadSectionDTO> getSourceleadFormSectionsByActionId(HttpServletRequest request,Long actionId) throws Exception{
	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionId,orgLocations.getId());
	return sections;
	}
	
	
	
	
	public String getProjectTaskGenaratedId(Projects projects) throws Exception {
		Integer generatedIdcount = 0;
		Long number = 1L;
		String requirementId = null;
	
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(projects.getProjectShortCode());
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Integer checkNumber = tasksDao.getProjectTasksCountByProjectId(projects.getId()); 
		if (checkNumber == null) {
			requirementId = number.toString();
		} else {
			Integer reqId = checkNumber + 1;
			requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
		
		return sbuilder.append(convetredRequirementsId).toString();
	}

	public void saveDefaultEmployeeVerificationTemplate(Org org, OrgLocations orgLocations, User user, String template) {
		 EmployeeVerificationTemplate employeeVerificationTemplate = new EmployeeVerificationTemplate();
		 StringBuilder sb = new StringBuilder();
		 employeeVerificationTemplate.setTemplateName(sb.append(orgLocations.getOrgLocationName()).append(" ").append(template).toString());
		 employeeVerificationTemplate.setSignatory(org.getUser());
		 employeeVerificationTemplate.setDescription(template);
		 employeeVerificationTemplate.setTemplateContent(DefaultEmployeeVerificationTemplateUtil.getDefaultTemplates(template));
		 employeeVerificationTemplate.setOrgLocationId(orgLocations);
		 employeeVerificationTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		 employeeVerificationTemplate.setTemplateStatus(Constants.MEMBER_ACTIVE);
		 employeeVerificationTemplate.setUpdatedBy(user.getUserCode());
		 employeeVerificationTemplate.setUpdatedDate(new Date());
		 employeeVerificationTemplate.setCreatedBy(user.getUserCode());
		 employeeVerificationTemplate.setCreatedDate(new Date());
		 employeeVerificationTemplateService.saveDefaultEmployeeVerificationTemplate(employeeVerificationTemplate);
	}
	
	public void employeeTerminationMailToProjectManager(User loginUser, ProjectMembersHistory pmh,OrgLocations orgLocations) throws Exception {
		 
		 User user=null;
		 List<ProjectApproverHierarchy>  mangerList = projectApproverHierarchyService.getApproverUsersByProjectId(pmh.getProjectMemberId().getProjects().getId());
		 for (ProjectApproverHierarchy projectApproverHierarchy : mangerList) {
			 if(null != pmh.getProjectMemberId()){
				 user = userService.getUserById(projectApproverHierarchy.getApproverUserId().getId());
		}
		 //SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 String endDate = dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmh.getProjectTerminationDate());
		 
	     StringBuilder employeeName = new StringBuilder();
	 		 if(StringUtils.isNotBlank(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName())) {
	 			 employeeName = employeeName.append(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName()).append(" ").append(pmh.getProjectMemberId().getProjectMemberCandidteId().getLastName());
	 		 }else {
	 		 employeeName = employeeName.append(pmh.getProjectMemberId().getProjectMemberCandidteId().getFirstName());
	  		 }
	  		 
	 	 globalVarsMap.put("EmployeeName", employeeName.toString());
		 
		 
		 if(null != pmh.getProjectMemberId().getProjects()){
			 if(null != pmh.getProjectMemberId().getProjects().getProjectName() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getProjects().getProjectName())){
				 globalVarsMap.put("ProjectName", pmh.getProjectMemberId().getProjects().getProjectName());
			}else{
				 globalVarsMap.put("ProjectName", "--");
			 }
		 }
		 
		 
		 if(null != pmh.getProjectTerminationDate()){
			 globalVarsMap.put("ProjectEndDate",endDate);
		 }else{
			 globalVarsMap.put("ProjectEndDate", "--");
		 }
		 
		 
		 if (null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())) {
			 StringBuilder mangerName = new StringBuilder();
			 globalVarsMap.put("Manager", mangerName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 } else {
				 globalVarsMap.put("Manager", user.getFirstName());
			}
		
		 if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			 StringBuilder loginName = new StringBuilder();
			 globalVarsMap.put("LoginUser", loginName.append(loginUser.getFirstName()).append(loginUser.getLastName()).toString());
			 } else {
				 globalVarsMap.put("LoginUser", loginUser.getFirstName());
			}
		
		 if (null != pmh.getProjectMemberId().getTerminateReason() && !"".equalsIgnoreCase(pmh.getProjectMemberId().getTerminateReason())) {
			 globalVarsMap.put("reason", pmh.getProjectMemberId().getTerminateReason());
			 } else {
				 globalVarsMap.put("reason", "--");
			}
		 
		 StringBuilder linkBuild = new StringBuilder();
	     globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	   
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 //using same migration which has been already used in updatedRequirementMailToManager method
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMPLOYEE_TERMINATION_MAIL_TO_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);

		 //construct HTML template using required data
		 
		 //calling the sendEmail method of emailScenerioUtil
		 sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.EMPLOYEE_TERMINATION_MAIL_TO_MANAGER,new ArrayList<EmailAttachment>());
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(pmsUrl).append("/").append("projectSummary?projectUUID="+pmh.getProjectMemberId().getProjects().getProjectUUID()+"&projectClientUUID="+pmh.getProjectMemberId().getProjectClientUUID()+"&tabType=team").toString();

		 emailScenerioUtil.sendNotification(actionLink, loginUser,user, projectApproverHierarchy.getProjectId().getOrgLocationsId(), notificationScenario,new HashMap<String,String>());
		 }
		}
	
	public static TimesheetActivities getTimesheetActivity(SubmissionApproverHierarchy sah,String activityStatus) {
		TimesheetActivities timesheetActivity = null;
		if(sah != null) {
			timesheetActivity = new TimesheetActivities();
			timesheetActivity.setProjectId(sah.getSubmissionId().getProjects());
			timesheetActivity.setActivityStatus(activityStatus.toUpperCase());
			timesheetActivity.setMasterCandidate(sah.getSubmissionId().getMasterCandidateId());
			timesheetActivity.setFrequencyStartDate(sah.getFrequencyStartDate());
			timesheetActivity.setFrequencyEndDate(sah.getFrequencyEndDate());
			timesheetActivity.setRejectedReason(sah.getRejectedReason());
			timesheetActivity.setActionPerformedBy(sah.getActionPerformedBy());
			timesheetActivity.setActionPerformedByOrgLocationId(sah.getActionPerformedByOrgLocationId());
			timesheetActivity.setStatus(sah.getStatus());
			timesheetActivity.setReopenedUser(sah.getReopenedUser());
			timesheetActivity.setReopenReason(sah.getReopenReason());
			timesheetActivity.setReopenRejectedReason(sah.getReopenRejectedReason());
			Date createdDate = addCurrentTimeToDate(sah.getCreatedDate());
			timesheetActivity.setCreatedDate(createdDate);
			timesheetActivity.setCreatedBy(sah.getCreatedBy());
			timesheetActivity.setActionPerformedbyUserId(sah.getTimesheetActivityLoginUser());
			if(sah.getSubmissionId() != null) {
				timesheetActivity.setWeekComments(sah.getSubmissionId().getWeekcomments());
			}
			timesheetActivity.setWeekTotal(sah.getSubmissionId().getTotalHours());
			timesheetActivity.setWeekTotalBillable(sah.getSubmissionId().getBillableHours());
			timesheetActivity.setWeekTotalNonBillable(sah.getSubmissionId().getNonBillableHours());
		}
		return timesheetActivity;
	}
	
	
  private static Date addCurrentTimeToDate(Date createdDate) {
	  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(createdDate);
	  
	  Calendar calender = Calendar.getInstance();
	  calender.setTime(new Date());
	     
      String currentdate =  sdf.format(calender.getTime());
      String hh = currentdate.split(":")[0];
      String mm = currentdate.split(":")[1];
      String ss = currentdate.split(":")[2];
      
      cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hh));
      cal.add(Calendar.MINUTE, Integer.parseInt(mm));
      cal.add(Calendar.SECOND, Integer.parseInt(ss));
      
      Date newDate = cal.getTime();
      System.out.println(newDate);
      return newDate;
	}

public OrgLicenseSubscriptionHistory  getOrgLicenseSubscriptionHistory(OrgLicenseSubscription orgLicenseSubscription,User loginUser) {
		
		OrgLicenseSubscriptionHistory history = new OrgLicenseSubscriptionHistory();
		history.setOrgLicenseSubscription(orgLicenseSubscription);
		history.setSubscriptionType(orgLicenseSubscription.getSubscriptionType());
		history.setUserSubscriptionType(orgLicenseSubscription.getUserSubscriptionType());
		history.setStorageSubscriptionType(orgLicenseSubscription.getStorageSubscriptionType());
		history.setCurrency(orgLicenseSubscription.getCurrency());
		history.setInvoiceAmount(orgLicenseSubscription.getInvoiceAmount());
		history.setSubscriptionTabs(orgLicenseSubscription.getSubscriptionTabs());
		history.setFromDate(orgLicenseSubscription.getFromDate());
		history.setToDate(orgLicenseSubscription.getToDate());
		history.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		history.setTimezone(orgLicenseSubscription.getTimezone());
		history.setCreatedBy(loginUser);
		history.setUpdatedBy(loginUser);
		history.setCreatedDate(new Date());
		history.setUpdatedDate(new Date());
		return history;
		
	}
  
  
  public void paymentAcknowledgementMail(OrgLicensePaymentTransactionHistory paymentHistory,User loginUser,Org org, OrgLocations orgLocations)
			throws Exception {
	  
	    EmailMessage emailMessage = new EmailMessage();
	    emailMessage.setFirstName(paymentHistory.getTransactionUser());
		emailMessage.setToEmail(paymentHistory.getTransactionUserEmail());
		emailMessage.setFromEmail("donotreply@sourcelead.com");
		emailMessage.setFromName("donotreply@sourcelead.com");
		emailMessage.setCreatedBy(loginUser.getUserCode());
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("customerName", paymentHistory.getTransactionUser());
		
		StringBuilder paymentDetailsBuilder = paymentDetailsTable(paymentHistory);
		globalVarsMap.put("paymentDetails", paymentDetailsBuilder.toString());
		// calling the sendEmail method of emailScenerioUtil
		sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.LICENSE_PAYMENT_ACKNOWLEDGEMENT,new ArrayList<EmailAttachment>());

  }
  
  
  
  public void paymentAcknowledgementDetailsMail(OrgLicensePaymentTransactionHistory paymentHistory,User loginUser,Org org, OrgLocations orgLocations)
			throws Exception {
	  
	    EmailMessage emailMessage = new EmailMessage();
	    emailMessage.setFirstName(paymentHistory.getTransactionUser());
		emailMessage.setToEmail(paymentSuccessReturnEmail);
		emailMessage.setFromEmail(paymentHistory.getTransactionUserEmail());
		emailMessage.setFromName(paymentHistory.getTransactionUser());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("companyName", orgLocations.getOrgLocationName());
		
		StringBuilder paymentDetailsBuilder = paymentDetailsTable(paymentHistory);
		globalVarsMap.put("paymentDetails", paymentDetailsBuilder.toString());
		// calling the sendEmail method of emailScenerioUtil
		sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.LICENSE_PAYMENT_ACKNOWLEDGEMENT_DETAILS,new ArrayList<EmailAttachment>());

}
  
  
  public StringBuilder paymentDetailsTable(OrgLicensePaymentTransactionHistory paymentHistory) throws Exception {
	  StringBuilder tempHTML = new StringBuilder();
	  String transactionDate = dateStrFromOrgDateFormatter("dd-MM-yyyy",paymentHistory.getTransactionDate());
	  
	     tempHTML.append("<table style='display:table-cell;vertical-align:middle; width:650px; font-size: 12px;  margin:0px;'>");
		 tempHTML.append("<tr align='center'>");
		 tempHTML.append("<th style='padding:5px 10px' width='20%' bgcolor='#E5E8E8'><span>License Type<span></th>");
		 tempHTML.append("<th style='padding:5px 10px' width='15%' bgcolor='#E5E8E8'><span>Order Id<span></th>");
		 tempHTML.append("<th style='padding:5px 10px' width='15%' bgcolor='#E5E8E8'><span>Transaction Id<span></th>");
		 tempHTML.append("<th style='padding:5px 10px' width='20%' bgcolor='#E5E8E8'><span>Transaction Date<span></th>");
		 tempHTML.append("<th style='padding:5px 10px' width='30%' bgcolor='#E5E8E8'><span>Amount<span></th>");
		 tempHTML.append("</tr>");
		 tempHTML.append("<tbody>");
		 tempHTML.append("<tr align='center'>");
		 
		 tempHTML.append("<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+paymentHistory.getSubscriptionType()+"</td>");
		 tempHTML.append("<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+paymentHistory.getOrderId()+"</td>");
		 tempHTML.append("<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+paymentHistory.getTransactionId()+"</td>");
		 tempHTML.append("<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+transactionDate+"</td>");
		 tempHTML.append("<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+paymentHistory.getCurrency()+"&nbsp;"+paymentHistory.getInvoiceAmount()+"</td>");
		 
		 tempHTML.append("</tr>");
		 tempHTML.append("</tbody>");
		 tempHTML.append("</table>");
	  
	     return tempHTML;
  }

  
  
  public Map<String, String> sendTemplateEmail(EmailMessage emailMessage,Map<String, String> globalVarsMap,Org org,OrgLocations orgLocations,String uniqueScenerioName,List<EmailAttachment> attachmentList){
	  Map<String, String> returnMap = new HashMap<>();
	  try{
		//setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 
			//getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if(orgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(uniqueScenerioName,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(uniqueScenerioName,Constants.ENTITY_TYPE_EMAIL);
			   }
			 
			 /*String emailBody = null;
			 String path=staticPath.substring(0, staticPath.lastIndexOf("/"));
			 try {
				    BufferedReader lineReader = new BufferedReader(new FileReader(path+emailScenario.getBodyUrl().replace("\\","\\\\")));
				    emailBody = new Scanner(lineReader).useDelimiter("\\Z").next();
				   // System.out.println(emailBody);
				   
				} catch (IOException ex) {
				    System.err.println(ex);
				}*/

			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
			 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
			 
			 returnMap.put("emailBody", body);
			 returnMap.put("emailSubject", subject);
			 String signature = null;
			 if(StringUtils.isNotBlank(emailScenario.getSignature())){
				 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
			 }
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,org,orgLocations);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,attachmentList);
			
	  }catch (Exception e) {
		  e.printStackTrace();
	}
	  return returnMap;
	  
  }

/**
 * Conert Date to OrgLocation format with HH:MM:SS 
 * @param dateFormat
 * @param date
 * @return String
 */
public String getOrglocationDateFormatHHMMSS(String dateFormat, Date date)
{
	DateFormat sdf = new SimpleDateFormat(dateFormat+" HH:mm:ss");
	return sdf.format(date);
}

  
  public String getConfiguredFromEmail(Long orgLocationId,EmailScenario  emailScenario) throws Exception{
	  
	     String fromEmail = null;
		 EmailConfigurationFromMail confFromMail=null;
		 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocationId);
		 if(emailConfigMailList != null){
			 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
				 if( fromMail.getEmailModules() != null){
 					String[] jobIds = fromMail.getEmailModules().split(",");
						for(int i=0; i<jobIds.length; i++) {
							if(Long.valueOf(jobIds[i]) == emailScenario.getTabId().getId() ){
								confFromMail=fromMail;
							}
						}
				 }
			 } 
			 
			 if(confFromMail == null){
				 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocationId);
				 if(confFromMail != null){
					 fromEmail = confFromMail.getEmail();
				 }
			 }else{
				 fromEmail = confFromMail.getEmail();
			 }
		 }
		 
		 return fromEmail;
  }
  
	
  public Shift saveDefaultGenaeralShift(OrgLocations orgLocations, User user,String shiftName) throws Exception {
	     Shift shift = new Shift();
	     shift.setShiftUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	     shift.setOrgLocations(orgLocations);
	     shift.setShiftName(shiftName);
	     shift.setTimeIn("9:00 AM");
	     shift.setTimeOut("6:00 PM");
	     shift.setMinmumHours("9 hrs");
	     shift.setTotalHours("9 hrs");
	     shift.setShiftDescription("Genearal Shift");
	     
	     shift.setMondayInTime("9:00 AM");
	     shift.setMondayOutTime("6:00 PM");
	     
	     shift.setTuesdayInTime("9:00 AM");
	     shift.setTuesdayOutTime("6:00 PM");
	     
	     shift.setWednesdayInTime("9:00 AM");
	     shift.setWednesdayOutTime("6:00 PM");
	     
	     shift.setThursdayInTime("9:00 AM");
	     shift.setThursdayOutTime("6:00 PM");
	     
	     shift.setFridayInTime("9:00 AM");
	     shift.setFridayOutTime("6:00 PM");
	     
	     shift.setSaturdayInTime("9:00 AM");
	     shift.setSaturdayOutTime("6:00 PM");
	     
	     shift.setSundayInTime("9:00 AM");
	     shift.setSundayOutTime("6:00 PM");
	     shift.setDefaultStatus("Y");
	     shift.setStatus(Constants.RECORD_ACTIVE.toString());
	     if(user != null) {
			 shift.setUpdatedBy(user.getUserCode());
			 shift.setCreatedBy(user.getUserCode());
	     }
		 shift.setUpdatedDate(new Date());
		 shift.setCreatedDate(new Date());
		 shiftDao.saveShift(shift);
		 return shift;
	}
  
  
  public String saveJoinStatusInSubmission(ApplicantRejectBackOutDTO applicantRejectBackOutDTO, Long submissionId, OrgLocations orgLocations, User user) throws Exception {
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		if(!requirementSubmission.getSubmissionStatus().equals(ApplicantSubmissionStatus.JOINED)) {
			
			// update offer status with out offer accepting and update submission and
			// history
			if (requirementSubmission.getSubmissionStatus().equals(ApplicantSubmissionStatus.OFFERMADE.toString())) {
				RequirementSubmissionHistory requirementSubmissionHistory1 = new RequirementSubmissionHistory();
				requirementSubmissionHistory1.setFromStage(requirementSubmission.getSubmissionStatus().toString());
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED.toString());
				requirementSubmissionHistory1.setToStage("OFFERACCEPTED");
				requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,
						requirementSubmissionHistory1, requirementSubmission.getCreatedBy());
				List<RequirementOffers> offersList = requirementOffersDao.getRequirementOfferSubmissionByIdAndOrgLocationId(submissionId, orgLocations.getId());
				offersList.forEach(requirementOffers -> {
					if (requirementOffers != null) {
						if (requirementOffers.getOfferStatus().equalsIgnoreCase("CLIENT_OFFERMADE")) {
							requirementOffers.setOfferStatus("CLIENT_OFFERACCEPTED");
						} else {
							requirementOffers.setOfferStatus("OFFERACCEPTED");
						}

						requirementOffers.setUpdatedBy(user.getUserCode());
						requirementOffers.setUpdatedDate(this.currentDate());
						try {
							requirementOffersDao.storeRequirementOffers(requirementOffers);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				if(requirementSubmission.getSourceSubmission() != null){
		        	   RequirementSubmission parentSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
			           if(parentSubmission != null){
			        	   parentSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString());
			        	   parentSubmission.setUpdatedDate(currentDate());
			        	   requirementSubmissionService.storeRequirementSubmission(parentSubmission);
			           }
		           }
			}
			
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.JOINED.toString());
			// to update Submission status
			requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.JOINED.toString());
			requirementSubmission.setUpdatedBy(user.getUserCode());
			StringBuilder sb = new StringBuilder();
			requirementSubmission.setUpdatedUser(sb.append(user.getFullName()).toString());
			requirementSubmission.setUpdatedDate(new Date());
			
			if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getJoinDate()) && !"".equalsIgnoreCase(applicantRejectBackOutDTO.getJoinDate())) {
				try {
					Date convertedDate = orgLocationDateFormat(orgLocations.getDateFormat(), applicantRejectBackOutDTO.getJoinDate());
					requirementSubmission.setJoinedDate(convertedDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				requirementSubmission.setJoinedDate(null);
			}
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
		}
		return "success";
	}

  	public void storeDefaultPayheadsWithOrgLocation(User user, OrgLocations orgLocations) throws Exception{
	  OrgLocations sourceLeadOrgLocations = new OrgLocations();
	  sourceLeadOrgLocations.setId(1l);
		ResponseList<PayrollPayheadDTO> responsePayheadList = payrollPayheadService.getPayrollPayheadList(sourceLeadOrgLocations);
		for(PayrollPayheadDTO payrollPayheadDTO : responsePayheadList.getData()) {
			payrollPayheadDTO.setUuid(null);
			payrollPayheadService.savePayrollPayhead(payrollPayheadDTO, user, orgLocations);
		}
	}

  
	public static String toFixed2(double d) {
		String str;
		d = d * 100;
		d = Math.round(d);
		d = d / 100;
		str = ("" + d).lastIndexOf(".") == ("" + d).length() - 2 ? d + "0" : d + "";
		return str;
	}

	/**
	 * @author:Vikram Reddy 
	 * @param createExcelsheet
		 * @param style
		 * @param headers
		 * @param rowCount 
		 * @return sheet
	 */

	public Sheet setExcelHeader(Sheet createExcelsheet, CellStyle style, List<String> headers, int rowCount) {
		Row excelHeader = createExcelsheet.createRow(rowCount);
		int i=0;
		for (String head:headers) {
			Cell cell = excelHeader.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(style);
			i++;
		}
		return createExcelsheet;
	}

	public List<FileInfo> getListOfFileInfo(List<File> filesList, String type,
			@RequestParam(defaultValue = "0") long orgId, @RequestParam(defaultValue = "0") long orgLocationId)
			throws IOException {
		//System.out.println(" orgId :   "+orgId +", orgLocationId : "+orgLocationId);
		 logger.debug("***** orgId :   "+orgId +", orgLocationId : "+orgLocationId);
		List<FileInfo> fileInfoList = new ArrayList<>();
		try {
			filesList.forEach(fileObj -> {
				FileInfo fileInfo = new FileInfo();
				String fileName = fileObj.getName();
				File file = new File(staticPath, "tempResumes" + System.getProperty("file.separator") + fileName);
				FileUploadUtil fileUploadUtil = new FileUploadUtil();
				String extenstion = fileName.substring(fileName.lastIndexOf("."));
				if (extenstion.equalsIgnoreCase(".rar"))
					return;
				if (extenstion.equalsIgnoreCase(".zip")) {
					fileInfoList.addAll(fileUploadUtil.uploadZipFile(staticPath, orgId, orgLocationId, type, file));
				} else {
					String fileURL = fileUploadUtil.uploadFile(staticPath, orgId, orgLocationId, type, file);
					fileInfo.setFileName(fileName);
					fileInfo.setFileURL(fileURL);
					try {
						fileInfo.setContentType(Files.probeContentType(file.toPath()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					fileInfoList.add(fileInfo);
				}
			}); // for each close.
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(" before file return list size ");
		return fileInfoList;
	}
	
	public Date calculateBeforeDate(Date date, int before) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -before);
		return cal.getTime();
	}
	
	 public static String convertToIndianCurrency(BigDecimal num) {
	        long number = num.longValue();
	        long no = num.longValue();
	        int decimal = (int) (num.remainder(BigDecimal.ONE).doubleValue() * 100);
	        int digits_length = String.valueOf(no).length();
	        int i = 0;
	        ArrayList<String> str = new ArrayList<>();
	        HashMap<Integer, String> words = new HashMap<>();
	        words.put(0, "");
	        words.put(1, "One");
	        words.put(2, "Two");
	        words.put(3, "Three");
	        words.put(4, "Four");
	        words.put(5, "Five");
	        words.put(6, "Six");
	        words.put(7, "Seven");
	        words.put(8, "Eight");
	        words.put(9, "Nine");
	        words.put(10, "Ten");
	        words.put(11, "Eleven");
	        words.put(12, "Twelve");
	        words.put(13, "Thirteen");
	        words.put(14, "Fourteen");
	        words.put(15, "Fifteen");
	        words.put(16, "Sixteen");
	        words.put(17, "Seventeen");
	        words.put(18, "Eighteen");
	        words.put(19, "Nineteen");
	        words.put(20, "Twenty");
	        words.put(30, "Thirty");
	        words.put(40, "Forty");
	        words.put(50, "Fifty");
	        words.put(60, "Sixty");
	        words.put(70, "Seventy");
	        words.put(80, "Eighty");
	        words.put(90, "Ninety");
	        String digits[] = {"", "Hundred", "Thousand", "Lakh", "Crore"};
	        while (i < digits_length) {
	            int divider = (i == 2) ? 10 : 100;
	            number = no % divider;
	            no = no / divider;
	            i += divider == 10 ? 1 : 2;
	            if (number > 0) {
	                int counter = str.size();
	                String plural = (counter > 0 && number > 9) ? "s" : "";
	                String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural : words.get(Integer.valueOf((int) Math.floor(number / 10) * 10)) + " " + words.get(Integer.valueOf((int) (number % 10))) + " " + digits[counter] + plural;                
	                str.add(tmp);
	            } else {
	                str.add("");
	            }
	        }
	 
	        Collections.reverse(str);
	        String Rupees = String.join(" ", str).trim();
	 
	        String paise = (decimal) > 0 ? " and " + words.get(Integer.valueOf((int) (decimal - decimal % 10))) + " " + words.get(Integer.valueOf((int) (decimal % 10))) +"paise": "";
	        return Rupees + " rupees"+ paise + " only";
	    }

	public void saveDefaultPayslipTemplate(OrgLocations orgLocations, User user) throws Exception {
		 PayrollPaySlipInd	 pps = new PayrollPaySlipInd();
		 pps.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		 pps.setIsActive(Constants.YES);
		 pps.setDeletePermission(Constants.NO);
	
	     pps.setPaySlipName("Default Payslip");
	     pps.setPaySlipDescription("Default Payslip");
	
    	//Company releated code
		pps.setCompanyName(Constants.YES);
		pps.setCompanyAddress(Constants.YES);
		pps.setCompanyLogo(Constants.YES);
	
	    //Employee Reated code
		pps.setEmployeeId(Constants.YES);
		pps.setEmployeeType(Constants.YES);
		pps.setEmployeeUanNumber(Constants.YES);
		pps.setEmployeePfNumber(Constants.YES);
		pps.setEmployeeBankDetails(Constants.YES);
		pps.setEmployeeDesignation(Constants.YES);
		pps.setEmployeeName(Constants.YES);
		pps.setEmployeeContactDetails(Constants.YES);
		pps.setEmployeeJoinDate(Constants.YES);
		pps.setEmployeeLocation(Constants.YES);
		pps.setEmployeePanNumber(Constants.YES);
		
	
	    //Leave Related code
		pps.setLeaveAttended(Constants.YES);
		pps.setLeaveLeaves(Constants.YES);
		
		pps.setOrgLocationId(orgLocations);
		pps.setStatus(Constants.RECORD_ACTIVE);
		pps.setCreatedDate(new Date());
		pps.setCreatedBy(user);
		payrollPaySlipIndService.savePayrollPaySlipInd(pps);
	}
	
	
	public String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress localip = null;
			try {
				localip = java.net.InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
			}
			if(localip != null)
            ip = localip.getHostAddress();
        }
        
        String[] ipArray = ip.split(",");
		if(ipArray.length > 1){
			for(String s :ipArray){
				if(StringUtils.isNotEmpty(s)){
					ip =s.replaceAll("[^0-9.]", "");
					break;
				}
			}
			
		}else{
			ip = ip.replaceAll("[^0-9.]", "");
		}
	    return ip;  
	}
	
	
	public Map<String,String> getCandidateBulkImportHeaderMap() {
		Map<String,String> headerMap = new HashMap<>();
		headerMap.put("summary", "summary");
		headerMap.put("lastName", "Last Name");
		headerMap.put("vendorcontact", "Vendor Contact");
		headerMap.put("alternateMobilePhone", "Alternate Mobile Number");
		headerMap.put("address", "Address");
		headerMap.put("primarySkill", "Primary Skill");
		headerMap.put("expectedRate", "Expected Rate");
		headerMap.put("experienceYears", "Experience");
		headerMap.put("currentRate", "Current Rate");
		headerMap.put("candidateSkills", "Skills");
		headerMap.put("firstName", "First Name");
		headerMap.put("candidateEmployers", "Employers");
		headerMap.put("mobilePhone", "Phone");
		headerMap.put("vendor", "Vendor");
		headerMap.put("designationType", "Designation Type");
		headerMap.put("docFile", "Resume");
		headerMap.put("middleName", "middleName");
		headerMap.put("designation", "Designation");
		headerMap.put("candidateEducation", "Education");
		headerMap.put("email", "Email");
		return headerMap;
		
	}

	public static String getCaptilizedText(String text) {
		String convertedText = "";
		try {
			for(String t : text.split(" ")) {
				convertedText += (convertedText.isEmpty() ? "" : " ") + t.substring(0, 1).toUpperCase() + t.substring(1).toLowerCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedText;
	}
	
	
	
	
	public CandidateGlobal getVendorGlobalCandidate(JSONObject jsonObj,OrgRelations orgRelation,OrgLocations orgLocations,User loginUser) throws Exception{
		  CandidateGlobal candidate = null;
		  OrgLocations vendorOrgLocation = null;
		  if(orgRelation.getInitiatorType().equalsIgnoreCase("Client") && orgRelation.getLocationId().getId().equals(orgLocations.getId())){
			  vendorOrgLocation = orgRelation.getResponderLocationId();
		  }else if(orgRelation.getInitiatorType().equalsIgnoreCase("Vendor") && orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
			  vendorOrgLocation = orgRelation.getLocationId();
		  }
		  
		  if(jsonObj.has("email") && jsonObj.getString("email") != null){
			  candidate = candidateGlobalService.findByCandidateGlobalEmail(jsonObj.getString("email").trim(),vendorOrgLocation.getId());
		  }else if(jsonObj.has("EmailId") && jsonObj.getString("EmailId") != null){
			  candidate = candidateGlobalService.findByCandidateGlobalEmail(jsonObj.getString("EmailId").trim(),vendorOrgLocation.getId());
		  }
		  
		  if(candidate == null){
			  candidate = new CandidateGlobal();
			  
			  MasterCandidate masterCandidate = null;
				masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
				if(null == masterCandidate){
					masterCandidate = masterCandidateService.storeMasterCandidateByJsonObject(jsonObj, loginUser,orgLocations);
				}
				candidate.setMasterCandidateId(masterCandidate);
			  
				candidate.setOrgLocationId(vendorOrgLocation);
				candidate.setCandidateType("Candidate");
				candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				
				if(jsonObj.has("firstName") && jsonObj.getString("firstName") !=null){
					candidate.setFirstName(jsonObj.getString("firstName"));
				}else if(jsonObj.has("FirstName") && jsonObj.getString("FirstName") !=null){
					candidate.setFirstName(jsonObj.getString("FirstName"));
				}
			
				if(jsonObj.has("lastName") && jsonObj.getString("lastName") !=null){
					candidate.setLastName(jsonObj.getString("lastName"));
				}else if(jsonObj.has("LastName") && jsonObj.getString("LastName") !=null){
					candidate.setLastName(jsonObj.getString("LastName"));
				}
				
				if(jsonObj.has("email") && jsonObj.getString("email") !=null){
					candidate.setEmail(jsonObj.getString("email"));
				}else if(jsonObj.has("EmailId") && jsonObj.getString("EmailId") != null && !jsonObj.getString("EmailId").isEmpty()){
					candidate.setEmail(jsonObj.getString("EmailId"));
				}
				
				if(jsonObj.has("location") && jsonObj.getString("location") != null && !jsonObj.getString("location").isEmpty()){
		        	candidate.setLocation(jsonObj.getString("location"));
				}
					
		        if(jsonObj.has("country") && jsonObj.getString("country") != null && !jsonObj.getString("country").isEmpty()){
		        	candidate.setCountry(jsonObj.getString("country"));
				}
				 
				if(jsonObj.has("state") && jsonObj.getString("state") != null && !jsonObj.getString("state").isEmpty()){
					 candidate.setState(jsonObj.getString("state"));
				}
				 
				if(jsonObj.has("city") && jsonObj.getString("city") != null && !jsonObj.getString("city").isEmpty()){
					 candidate.setCity(jsonObj.getString("city"));
				}
				
				 if(jsonObj.has("zipcode") && jsonObj.getString("zipcode") != null && !jsonObj.getString("zipcode").isEmpty()){
			        	candidate.setZipCode(jsonObj.getString("zipcode"));
				}
				 
				 if(jsonObj.has("PhoneNumber") && jsonObj.getString("PhoneNumber") != null && !jsonObj.getString("PhoneNumber").isEmpty()){
					 candidate.setMobilePhone(jsonObj.getString("PhoneNumber"));
				}
				
				if(jsonObj.has("AlternateNumber") && jsonObj.getString("AlternateNumber") != null && !jsonObj.getString("AlternateNumber").isEmpty()){
					 candidate.setAlternateMobilePhone(jsonObj.getString("AlternateNumber"));
				}
				
				
				if(jsonObj.has("currentCTCRateType") && jsonObj.getString("currentCTCRateType") != null && !jsonObj.getString("currentCTCRateType").isEmpty()){
					candidate.setCurrentRateCurrency(jsonObj.getString("currentCTCRateType"));
				}
				
				if(jsonObj.has("CurrentCTC") && jsonObj.getString("CurrentCTC") != null && !jsonObj.getString("CurrentCTC").isEmpty()){
					candidate.setCurrentRate(jsonObj.getString("CurrentCTC"));
				}
				
				if(jsonObj.has("currentCTCRatePer") && jsonObj.getString("currentCTCRatePer") != null && !jsonObj.getString("currentCTCRatePer").isEmpty()){
					 candidate.setCurrentRateType(jsonObj.getString("currentCTCRatePer"));
				}
				
				if(jsonObj.has("expectedCTCRateType") && jsonObj.getString("expectedCTCRateType") != null && !jsonObj.getString("expectedCTCRateType").isEmpty()){
					 candidate.setExpectedRateCurrencey(jsonObj.getString("expectedCTCRateType"));
					 
				}
				 
				if(jsonObj.has("ExpectedCTC") && jsonObj.getString("ExpectedCTC") != null && !jsonObj.getString("ExpectedCTC").isEmpty()){
					 candidate.setExpectedRate(jsonObj.getString("ExpectedCTC"));
					 
				}
				
				if(jsonObj.has("expectedCTCRatePer") && jsonObj.getString("expectedCTCRatePer") != null && !jsonObj.getString("expectedCTCRatePer").isEmpty()){
					 candidate.setRateType(jsonObj.getString("expectedCTCRatePer"));
				}
				
				if(jsonObj.has("NoticePeriod") && jsonObj.getString("NoticePeriod") != null && !jsonObj.getString("NoticePeriod").isEmpty()){
					 candidate.setNoticePeriod(jsonObj.getString("NoticePeriod"));
					 
				}
				
				if(jsonObj.has("Designation") && jsonObj.getString("Designation") != null && !jsonObj.getString("Designation").isEmpty()){
					candidate.setDesignation(jsonObj.getString("Designation"));
				}
				 if(jsonObj.has("WorkExperience") && jsonObj.getString("WorkExperience") != null && !jsonObj.getString("WorkExperience").isEmpty()){
					 candidate.setExperienceYears(jsonObj.getString("WorkExperience"));
				 }
				 
				 if(jsonObj.has("monthExp") && jsonObj.getString("monthExp") != null && !jsonObj.getString("monthExp").isEmpty()){
					 candidate.setExperienceMonths(jsonObj.getString("monthExp"));
				 }
				 
				 candidate.setDesignationType("Full Time");
				
				if(jsonObj.has("primarySkill") && jsonObj.getString("primarySkill") != null && !jsonObj.getString("primarySkill").isEmpty()){
					 candidate.setPrimarySkill(jsonObj.getString("primarySkill"));
				}
				 
				 if(jsonObj.has("candidateSkills") && jsonObj.getString("candidateSkills") != null && !jsonObj.getString("candidateSkills").isEmpty()){
					  
					 String candidateSkills = null;
						try{
				    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
				    		   candidateSkills = jsonArray.toString();
				    	  }
				    	  catch(JSONException e){
				    		  candidateSkills= jsonObj.getString("candidateSkills");
				    	  }
						candidateSkills = candidateSkills.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
						
					 
					 candidate.setSkillsName(candidateSkills);
					 
				
				 }
			
			candidate.setIsAssociate(Constants.NO);
			candidate.setStatus(1);
			
			candidateGlobalService.storeCandidateGlobal(candidate);
			candidateGlobalService.saveOrUpdateCandidateGlobalElkDocument(candidate);
			
		  }
		  
		  return candidate;
	  }
	
	
}


package com.bis.sourcelead.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dao.CronTrackerDao;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.TimeZoneDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProbationDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignStats;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidatesHotListDummy;
import com.bis.sourcelead.entity.ContractOwnerHierarchy;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.CronTracker;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.EmailReminderHierarchy;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayShift;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaPlaceOfEployment;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollScheduleCalenderDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollApprover;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.payroll.service.GenerateUSPayrollService;
import com.bis.sourcelead.payroll.service.PayrollScheduleService;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailReminderService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.service.JobSchedulerTestService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.Envelope;
import com.docusign.esign.model.LoginAccount;
import com.docusign.esign.model.LoginInformation;

/**
*
* <b>Purpose:</b><br>
*
* Spring Job Scheduler class for 
* 
* <b>CopyRights:</b><br>
* Business Intelli Solutions India Pvt Ltd.- 2016<br>
* 
* @author: Srinivas Vemula
* @version <br>
*
*          <b>RevisionHistory:</b>
*
*          <pre>
*
* <b>
* Sl No   Modified Date        Author</b>
* ==============================================
* 1.        28-06-2016            Srinivas Vemula
*      -- Base Release
* 2
* </pre>
*/
public class EMailJobScheduler {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;	
	
	 @Value("${env}")
	 private String environment;
	
	@Autowired
	JobSchedulerTestService jobSchedulerTestService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	TeamMembersService teamMembersService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	HolidayService holidayService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	SourceleadResumeParserNew sourceleadResumeParser;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CandidateHotListService candidateHotListService;
	
	@Autowired
	SourceleadELKBaseIndexer sourceleadELKBaseIndexer;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;

	@Autowired
	private ContractsTemplateDao contractsTemplateDao;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
    @Autowired
    CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	TaskService taskService;
	
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	TimeZoneDao timeZoneDao;
	
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	JobAlertService jobAlertService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	private CandidateOffBoardingService candidateOffBoardingService;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private CampaignUtil campaignUtil;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	AssociateBalanceLeaveService associateBalanceLeaveService;
	
	@Autowired
	LeaveDao leaveDao;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioconService;
	
	@Autowired
	private ImmigrationCommonService immigrationCommonService;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	private ImmigrationTemplateService immigrationTemplateService;
	
	@Autowired
	private ConfigurePayrollApproverService configurePayrollApproverService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private PayrollScheduleService payrollScheduleService;
	
	@Autowired
	private PayrollUtil payrollUtil;
	
	@Autowired
	private AssociateSalaryService associateSalaryService;
	
	@Autowired
	private GenerateUSPayrollService generateUSPayrollService;
	
	@Autowired
	private USPaysheetService uSPaysheetService;
	
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private CronTrackerDao cronTrackerDao;
	
	@Autowired
	private EmailReminderService emailReminderService;
	
	@Autowired
	private RequirementOffersService requirementOffersService;
	
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	private ProjectTasksService projectTasksService;

	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
	
	private static boolean startEmailParsingProcess = true;
	
	
	//=============================================== EMPLOYEE TIME SHEETS MAILS STARTED==========================================
	
	/**
	 * @author Rajashekar & Prasad salina
	 * Fires on Every Firday at 11:45 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 45 23 ? * FRI ")
	public void runJobSchedulerByWeekly() {
		logger.info("******************  Notification Job Scheduler started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			for (Org org : orgList) {
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				for (OrgLocations ol : orgLocations) {
					HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.WEEKLY,ol.getId());
					if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
						for(ProjectMembers projectMember : ProjectMembersList){
							UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getProjectMemberCandidteId().getConsultantUser().getId(),ol.getId());
							if("Admin".equalsIgnoreCase(userRole.getUserType()))
								continue;
							    sendCronRemainderForProjectMember(projectMember,ol,userRole,Constants.WEEKLY);
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	
	/**
	 * @author Rajashekar & Prasad salina
	 * Fires on Every Firday at 11:45 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 45 23 ? * FRI ")
	public void runJobSchedulerByBi_Weekly() {
		logger.info("******************  Notification Job Scheduler Every two weeks started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			for (Org org : orgList) {
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				for (OrgLocations ol : orgLocations) {
					HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.BI_WEEKLY,ol.getId());
					if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
						for(ProjectMembers projectMember : ProjectMembersList){
							UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getProjectMemberCandidteId().getConsultantUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
							if("Admin".equalsIgnoreCase(userRole.getUserType()))
								continue;
							    sendCronRemainderForProjectMember(projectMember,ol,userRole,Constants.BI_WEEKLY);
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	
	/**
	 * @CommentedBy Mahaveer Manoj
	 * @Reason as this schedule job is blocking IPAddress in QA server, and anyhow we aren't using this functionality.
	 *  	   So, commented for now. If needed will open later
	 **/
   // 
	//@Scheduled(cron = "0/30 * * * * *")// Scheduled for every 30 seconds for testing need to comment after testing.
	@Scheduled(cron = "0 0/15 * 1/1 * ?") // Scheduled for every 15 Minutes.
	@Async
	public void runJobSchedulerEmailJobParsingAtOrgLevel() { // reads the email configured at org level and parse the
																// Jobs(attachments)in inbox folder.
		// Saves the resumes at org level specific folder and updates the org level
		// candidates table with parsed information.
		logger.info("******************  Parsing resumes is Started each org level environment value is ::: "+environment);
		logger.info("******************  startEmailParsingProcessvalue is ::: "+startEmailParsingProcess);
		if(environment != null && !environment.trim().equalsIgnoreCase("local") && startEmailParsingProcess) {
			logger.debug("****************** Parsing resumes is Started..@ time  *****  >>>> :   "+Calendar.getInstance().getTime());
			System.out.println("****************** Parsing resumes is Started..@ time  *****  >>>> :   "+Calendar.getInstance().getTime());
			System.out.println("=========== isEmailParsingProcess started ======================="+startEmailParsingProcess);
			logger.debug("=========== isEmailParsingProcess started ======================="+startEmailParsingProcess);
			startEmailParsingProcess = false;
		try {
			List<Org> orgList = orgService.getOrgs();
			User user = null;
			for (Org org : orgList) {
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());

				for (OrgLocations orgLoc : orgLocations) {
					user = orgLoc.getUser();

					OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLoc.getId());

					Long orgLocationId = loginOrgLocation.getId();
					List<EmailScenarioConfiguration> emailsList = emailScenarioconService.getConfParserMailList(orgLocationId, Constants.RESUME_PARSING, true);
						File tempfolder = null;
					for(EmailScenarioConfiguration email :  emailsList) {
						System.out.println(" **************Parsisng started for Email id is :" + email.getEmailId());
						logger.debug("***********Parsisng started  Email id is :" + email.getEmailId());
						tempfolder = new File(staticPath+ System.getProperty("file.separator") +"tempResumes" );
						if (!tempfolder.exists()) tempfolder.mkdirs(); // if not exist create the folder with path.
						 new ReadEmail().readEMailAttachments(orgLocationId, email, tempfolder);  //downloads the attachments from the configured email by user level to temp folder.
					
					
						 List<File> fileList = Arrays.asList(tempfolder.listFiles());
					
	                    if(fileList.size() > 0) { // if no email attachment found skip the parsing.
						List<FileInfo> fileInfoList = commonUtil.getListOfFileInfo(fileList, "resumes",	loginOrgLocation.getOrg().getId(), orgLocationId);
						FileUploadUtil.processConfiguredEmailShedulerForResumesParsing(fileInfoList, staticPath, sourceleadResumeParser, candidateService,
								                    						candidateResumesService, loginOrgLocation.getCountry(), loginOrgLocation,user);
	                    }
					}
				}

			}
			startEmailParsingProcess = true;
			System.out.println("****************** Parsing resumes is Completed..@ time  *****  >>>> :   "+Calendar.getInstance().getTime());
			logger.debug("****************** Parsing resumes is Completed..@ time  *****  >>>> :   "+Calendar.getInstance().getTime());
			logger.debug("=========== isEmailParsingProcessComplted ======================="+startEmailParsingProcess);
			System.out.println("=========== isEmailParsingProcessComplted ======================="+startEmailParsingProcess);
		} catch (Exception e) {
			logger.error("*****************Exception caught while Job parsing shceduler *************"+e.getMessage());
			startEmailParsingProcess = true;
			e.printStackTrace();
		}
       }
	}
	
   @Scheduled(cron = "0 0 9 1/1 * ?") // Scheduled for every day at @ 9.a.m on united states time.
   //@Scheduled(cron = "0/30 * * * * *") // used for dev testing run for every 30 seconds..
	@Async
	public void runJobSchedulerDailyUpdateLCA() { // check if LCA is expired on not on daily basis, if expired updating
													// LCA status in Database.
		logger.info("******************  updating All LCA  Deatails Started..! **********");
		//System.out.println("******************  updating All LCA  Deatails Started..! **********");
		try {
			String fromUser = "";
			User configUser=null;
			Date currentDate = new Date();
			Date daysFilter = null;
			Calendar cal = Calendar.getInstance();
			
			int expiring_days_arr [] = {180, 90, 60, 30, 15, 7};
			for(int i=0; i<expiring_days_arr.length; i++) {
				cal.add(Calendar.DATE, expiring_days_arr[i]);
				daysFilter = cal.getTime();
			// lca expired in 180 days it will send email to lca manager
			List<Lca> lcaListExpiring = immigrationCommonService.getAllLcaList(daysFilter);
			
			if(lcaListExpiring != null && lcaListExpiring.size()>0) {
				List<Lca> lcaListByManager = new ArrayList<Lca>();

				Map<User, List<Lca>> managerLcaMap = new HashMap<User, List<Lca>>();
				
				for (Lca  lcaObj : lcaListExpiring) { // here for loop preparing lca list by user base.

					if (managerLcaMap.get(lcaObj.getCreatedBy()) == null) {
						lcaListByManager = new ArrayList<Lca>();
						lcaListByManager.add(lcaObj);
						managerLcaMap.put(lcaObj.getCreatedBy(), lcaListByManager);
					} else {
						managerLcaMap.get(lcaObj.getCreatedBy()).add(lcaObj);
					}
				}
				for (Map.Entry<User, List<Lca>> entry : managerLcaMap.entrySet()) {
				//	System.out.println("Key = " + entry.getKey().getId() + ", Value = " + entry.getValue().size());
					User toUser = (User)entry.getKey();
					List<Lca> mapLcaList = entry.getValue();
					OrgLocations ol = mapLcaList.get(0).getOrgLocationId(); // ge
				    sendEmailReminderToLcaManagerExpiredInDays(toUser, fromUser, lcaListExpiring, ol, String.valueOf(expiring_days_arr[i]));
				}
			  }
			}	
			
			List<Lca> lcaList = immigrationCommonService.getAllLcaList(currentDate);
			if (lcaList != null && lcaList.size() > 0) {
				List<Lca> lcaListByManager = new ArrayList<Lca>();

				Map<User, List<Lca>> managerLcaMap = new HashMap<User, List<Lca>>();

				for (Lca lcaObj : lcaList) { // here for loop preparing lca list by user base.

					if (managerLcaMap.get(lcaObj.getCreatedBy()) == null) {
						lcaListByManager = new ArrayList<Lca>();
						lcaListByManager.add(lcaObj);
						managerLcaMap.put(lcaObj.getCreatedBy(), lcaListByManager);
					} else {
						managerLcaMap.get(lcaObj.getCreatedBy()).add(lcaObj);
					}
				}
				// below code is used for sending email to each user in map. Map contains user
				// as key and value as list of lca objects.
				for (Map.Entry<User, List<Lca>> entry : managerLcaMap.entrySet()) {
				//	System.out.println("Key = " + entry.getKey().getId() + ", Value = " + entry.getValue().size());
					User toUser = (User) entry.getKey();
					List<Lca> mapLcaList = entry.getValue();
					OrgLocations ol = mapLcaList.get(0).getOrgLocationId(); // getting org loaction id from the first
					User fromNotificationUser =userService.getUserByEmail(fromUser);														// object.
					sendEmailReminderToLcaManagerExpired(toUser, fromNotificationUser, mapLcaList, ol, ImmigrationConstants.LCA_STATUS_EXPIRED);
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
			e.printStackTrace();
			// System.out.println("ERROR in " + e.getMessage());
		}
	}
	
	private void sendEmailReminderToLcaManagerExpired(User toUserObj, User fromUser, List<Lca> lcaList, OrgLocations ol, String currLcaStatus) throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		
		 emailMessage.setFirstName(toUserObj.getFirstName());
		 emailMessage.setLastName(toUserObj.getLastName());
		 emailMessage.setToEmail(toUserObj.getEmail());
		//configured Email
			
			String configuredFromEmail = null;
			 EmailConfigurationFromMail confFromMail=null;
			 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(ol.getId());
			 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
				 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
					 if( fromMail.getEmailModules() != null){
						String[] jobIds = fromMail.getEmailModules().split(",");
							for(int i=0; i<jobIds.length; i++) {
								if(Long.valueOf(jobIds[i]) == 39l ){
									confFromMail=fromMail;
								}
							}
					 }
				 } 
				 
				 if(confFromMail != null){
					 configuredFromEmail = confFromMail.getEmail();
				 }
			 }
			 
			 if(StringUtils.isNotBlank(configuredFromEmail)){
				 emailMessage.setFromEmail(configuredFromEmail);
			 }else {
				 emailMessage.setFromEmail(fromUser.toString());
			 }
		
	//	List<String> employeeList = immigrationCommonService.getAllExpiredCandidatesFromLcaHistoryByLcaId(lcaId);
		 StringBuilder sb = new StringBuilder();
		 
		 sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center;' cellpadding='2px' cellspacing='0' border='0'>");
		        sb.append("<thead style='background-color:#375d81; color: #fff;'>");
		        sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");            
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>LCA No</th>");          
		        sb.append("<th style=.border: 1px solid #12385c; text-align: center;'>LCA Title</th>");                
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Location</th>");                  
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Employees</th>");
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Start date</th>");
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>End date</th>");
		        sb.append("</tr>");    
		        sb.append("</thead>");    
		        sb.append("<tbody>");
	        lcaList.forEach(lca->{
	        	
 	        	String strStartDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(), lca.getStartDate());
	  			String strEndDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(), lca.getEndDate());
		        sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
		        sb.append("<td style='border: 1px solid #375d81;' width='300'>"+lca.getLcaNo()+"</td>");                      
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+lca.getLcaTitle()+"</td>"); 
		        StringBuilder locSb = new StringBuilder();
		        for (LcaPlaceOfEployment source : lca.getLcaPlaceOfEployment()) {
		        	locSb.append(source.getLocation()).append(" &").toString();	
		        }
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+locSb.deleteCharAt(locSb.length() - 1)+"</td>"); 
		       
		        List<String> employeeList = immigrationCommonService.getAllExpiredCandidatesFromLcaHistoryByLcaId(lca.getId());
		       // System.out.println("sendEmailReminderToLcaManagerExpired lcaid>>>>>>"+lca.getId());
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+( employeeList.size() > 0 ? StringUtils.join(employeeList,',') : " " ) +"</td>"); 
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+strStartDate+"</td>"); 
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+strEndDate+"</td>"); 
		        sb.append("</tr>");
	        });
	  		
	  		sb.append("</tbody>");   
		    sb.append("</table>"); 

		Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("currLcaStatus", currLcaStatus);
		 
		 globalVarsMap.put("No_Of_Lcas",String.valueOf(lcaList.size()));
		 
		 if(toUserObj.getLastName() != null) {
			 globalVarsMap.put("Lca_Manager",toUserObj.getFirstName() +" "+ toUserObj.getLastName());
		 }else {
			 globalVarsMap.put("Lca_Manager",toUserObj.getFirstName());
		 }
		 
		 globalVarsMap.put("Lca_Table", sb.toString());
		// globalVarsMap.put("Expiring_Days",commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(), lcaObj.getEndDate()));
		 
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("URL_Link", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
		"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
		.toString());

		globalVarsMap.put("Org_Name",ol.getOrgLocationName());
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 EmailScenario emailScenario = null;
		 //getting the current EmailScenario by EmailScenario unique name and entity type
			 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LCA_MANAGER_EXPIRED_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
			 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
			 String signature = null;
			 if(StringUtils.isNotBlank(emailScenario.getSignature())){
				 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
			 }
			 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, ol.getOrg(), ol);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,ol.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
		// LCA manager Expired Notification 
		EmailScenario notificationScenario = null;
		notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LCA_MANAGER_EXPIRED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
		//   System.out.println("notificationScenario :"+notificationScenario.getBody());
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("")
				.append(sourceleadUrl).append("/immigration").append("/viewLCAList").toString();
		emailScenerioUtil.sendNotification(actionLink, fromUser , toUserObj, ol, notificationScenario,new HashMap<String,String>());
		}
	
	
	private void sendEmailReminderToLcaManagerExpiredInDays(User toUserObj, String fromEmail, List<Lca> lcaList, OrgLocations ol, String currLcaDaysStatus) throws Exception {
			
		      EmailMessage emailMessage = new EmailMessage();
			
			  emailMessage.setFirstName(toUserObj.getFirstName());
			  emailMessage.setLastName(toUserObj.getLastName());
			  emailMessage.setToEmail(toUserObj.getEmail());
			  emailMessage.setFromEmail(fromEmail);
			  
			 StringBuilder sb = new StringBuilder();
			 Date expiredDate = null;	
			 sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center;' cellpadding='2px' cellspacing='0' border='0'>");
		        sb.append("<thead style='background-color:#375d81; color: #fff;'>");
		        sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");            
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>LCA No</th>");          
		        sb.append("<th style=.border: 1px solid #12385c; text-align: center;'>LCA Title</th>");                
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Location</th>");                  
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Employees</th>");
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Start date</th>");
		        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>End date</th>");
		        sb.append("</tr>");    
		        sb.append("</thead>");    
		        sb.append("<tbody>");
				
		        for(Lca lca :lcaList)
		        {
	        	String strStartDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(), lca.getStartDate());
	  			String strEndDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(), lca.getEndDate());
	  			expiredDate =lca.getEndDate();
		        sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
		        sb.append("<td style='border: 1px solid #375d81;' width='300'>"+lca.getLcaNo()+"</td>");                      
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+lca.getLcaTitle()+"</td>");                  
		        StringBuilder locSb = new StringBuilder();
		        for (LcaPlaceOfEployment source : lca.getLcaPlaceOfEployment()) {
		        	locSb.append(source.getLocation()).append(" &").toString();	
		        }
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+locSb.deleteCharAt(locSb.length() - 1)+"</td>"); 
		        List<String> employeeList = immigrationCommonService.getAllExpiredCandidatesFromLcaHistoryByLcaId(lca.getId());
		       // System.out.println("sendEmailReminderToLcaManagerExpiredInDays lcaid>>>>>>>>>>>"+lca.getId());
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+(employeeList.size() > 0 ? StringUtils.join(employeeList,',') : " ") +"</td>"); 
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+strStartDate+"</td>"); 
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+strEndDate+"</td>"); 
		        sb.append("</tr>");	
		       }
	  		sb.append("</tbody>");   
		    sb.append("</table>"); 
       
			Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("currLcaStatus", currLcaDaysStatus);
			 
			 globalVarsMap.put("No_Of_Lcas",String.valueOf(lcaList.size()));
			 
			 if(toUserObj.getLastName() != null) {
				 globalVarsMap.put("Lca_Manager",toUserObj.getFirstName() +" "+ toUserObj.getLastName());
			 }else {
				 globalVarsMap.put("Lca_Manager",toUserObj.getFirstName());
			 }
			 
			 Calendar cal = Calendar.getInstance();
			 Date startDate = cal.getTime();
			 
			 SimpleDateFormat df=new SimpleDateFormat("yyyy-M-dd");
			 String expDate=df.format(expiredDate);
			 Date endDate=commonUtil.dateFromElk(expDate);		
			 long diff = endDate.getTime()-startDate.getTime();
			 
			 Long expiredInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			 
		//	 System.out.println("expiredInDays>>>>>>>>>>>>>>>>>>>>>"+expiredInDays);
			 globalVarsMap.put("Lca_Table", sb.toString());
			 globalVarsMap.put("Expiring_Days",String.valueOf(expiredInDays));
			 
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("URL_Link", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
			"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
			.toString());
	
			globalVarsMap.put("Org_Name",ol.getOrgLocationName());
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 EmailScenario emailScenario = null;
			 //getting the current EmailScenario by EmailScenario unique name and entity type
				 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LCA_MANAGER_EXPIRED_EMAIL_IN_DAYS,Constants.ENTITY_TYPE_EMAIL);
	
				//convert subject,body and signature
				 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
				 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
				 String signature = null;
				 if(StringUtils.isNotBlank(emailScenario.getSignature())){
					 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
				 }
				 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, ol.getOrg(), ol);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,ol.getId(),iAdressArray,new ArrayList<EmailAttachment>());

		}
	
	/**
	 * @author Rajashekar & Prasad salina
	 * Fires on Every 15 (semi_monthly) days at 11:45 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 45 23 1/15 * ?")
	public void runJobSchedulerBySemi_Monthly() {
		logger.info("******************  Notification Job Scheduler Every 15 days in a month started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			for (Org org : orgList) {
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				for (OrgLocations ol : orgLocations) {
					HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.SEMI_MONTHLY,ol.getId());
					if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
						for(ProjectMembers projectMember : ProjectMembersList){
							UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getProjectMemberCandidteId().getConsultantUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
							if("Admin".equalsIgnoreCase(userRole.getUserType()))
								continue;
							   sendCronRemainderForProjectMember(projectMember,ol,userRole,Constants.SEMI_MONTHLY);
							    
					}
				}
			  }
			}
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	/**
	 * @author Rajashekar
	 * Fires on Every month at 11:45 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 45 23 30 1/1 ?")
	public void runJobSchedulerByMonthly() {
		logger.info("******************  Notification Job Scheduler  Every month 30th started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			for (Org org : orgList) {
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				for (OrgLocations ol : orgLocations) {
					HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.MONTHLY,ol.getId());
					if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
						for(ProjectMembers projectMember : ProjectMembersList){
							UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getProjectMemberCandidteId().getConsultantUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
							if("Admin".equalsIgnoreCase(userRole.getUserType()))
								continue;
							sendCronRemainderForProjectMember(projectMember,ol,userRole,Constants.MONTHLY);
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	
	@Async
	private void sendCronRemainderForProjectMember(ProjectMembers projectMember,OrgLocations orgLocations,UserRole userRole,String frequency) throws Exception{
			String level ="";
			Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember,orgLocations,"employeeScheduler");
			LinkedHashMap<String,String> finalTFMap = new LinkedHashMap<>();
			if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
				 Date cronStartDate = null;
				 Date cronEndDate = null;
				for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
				    String key = entry.getKey();
				    String value =entry.getValue();
				    Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
					Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
					
					Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
					currentCal.setLenient(false);
					currentCal.setTime(CommonUtil.currentDate());
					
		             Boolean flag = true;
		             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 && frequency.equalsIgnoreCase(Constants.BI_WEEKLY)){
		            	 flag = false;
		             }
					
		             if(submissionApproverHierarchyService.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMember.getMasterCandidateId().getId(),projectMember.getProjects().getId()) && (flag)){
				    	finalTFMap.put(key, value);
			    	    if(cronStartDate == null){
							cronStartDate = frequencyStartDate;
						}
						if(cronEndDate == null || CalendarUtil.getNextDate(cronEndDate).equals(frequencyStartDate)){
							cronEndDate = frequencyEndDate;
						}else{
							String stDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),cronStartDate);
							String eDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),cronEndDate);
							finalTFMap.put(stDate,eDate);
							cronStartDate = null;
							cronEndDate = null;
						}
					}
				}
				if(cronStartDate != null && cronEndDate != null){
				    String stDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),cronStartDate);
					String eDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),cronEndDate);
					finalTFMap.put(stDate,eDate);
					cronStartDate = null;
					cronEndDate = null;
			    }
			}
			if(null != finalTFMap && !finalTFMap.isEmpty()){
				projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),orgLocations.getId(),finalTFMap,projectMember.getProjects(),level);	
				logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
			}
			
	}
	//=============================================== EMPLOYEE TIME SHEETS MAILS END==========================================
 	
	
	//=============================================== LEVEL 1 TIME SHEETS MAILS STARTED==========================================
	/**
	 * @author Prasad salina
	 * mail to level1 Managers Weekly
	 * Fires on Monday at 11:45 PM of every week of the month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 ? * MON")
//	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void runJobSchedulerByMondayForWeeklyAndBiWeekly() {
		try {
			  logger.info("******************  Notification Job Scheduler MONDAY of every week of the Month started (Weekly) to Level 1 Manager..! **********");
			  level1ORLevel2ManagersCronRemainder(Constants.WEEKLY,Constants.level1);
			  
			  logger.info("******************  Notification Job Scheduler MONDAY of every week of the Month started (Bi_Weekly) to Level 1 Manager..! **********");
			  level1ORLevel2ManagersCronRemainder(Constants.BI_WEEKLY,Constants.level1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Prasad salina
	 * mail to level1 Managers Semi-Monthly
	 * Fires every 17th of the month at 11:45 PM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 17 1/1 ?")
	public void runJobSchedulerBy17thForSemiMonthly() {
		logger.info("******************  Notification Job Scheduler every 17th of the month at 11:45 PM started (Semi_Monthly) to Level 1 Manager..! **********");
		try {
			  level1ORLevel2ManagersCronRemainder(Constants.SEMI_MONTHLY,Constants.level1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Prasad salina
	 * mail to level1 Managers Semi-Monthly and Monthly
	 * Fires every 2nd of the month at 11:45 PM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 2 1/1 ?")
	public void runJobSchedulerBy2ndForSemiMonthly() {
		try {
			  logger.info("******************  Notification Job Scheduler every 2nd of the month at 11:45 PM started (Semi_Monthly) to Level 1 Manager..! **********");
			  level1ORLevel2ManagersCronRemainder(Constants.SEMI_MONTHLY,Constants.level1);
			  
			  logger.info("******************  Notification Job Scheduler every 2nd of the month at 11:45 PM started (Monthly) to Level 1 Manager..! **********");
			  level1ORLevel2ManagersCronRemainder(Constants.MONTHLY,Constants.level1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	//=============================================== LEVEL 1 TIME SHEETS MAILS END==========================================
	

	
	
	//=============================================== LEVEL 2 TIME SHEETS MAILS STARTED==========================================
	/**
	 * Prasanna vadla
	 * this is for level 2
	 * Fires on Wednesday at 11:45 PM of every week of the month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 ? * WED")
	public void runJobSchedulerByWedDayWeeklyAndBiWeekly() {
		logger.info("******************  Notification Job Scheduler WednesDay of every week of the Month started (Weekly) to Level 2 Manager..! **********");
		try {
			level1ORLevel2ManagersCronRemainder(Constants.WEEKLY,Constants.level2);
			
			logger.info("******************  Notification Job Scheduler WednesDay of every week of the Month started (Bi_Weekly) to Level 2 Manager..! **********");
			level1ORLevel2ManagersCronRemainder(Constants.BI_WEEKLY,Constants.level2);
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Prasad salina
	 * mail to level2 Managers Semi-Monthly
	 * Fires every 19th of the month at 11:45 PM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 19 1/1 ?")
	public void runJobSchedulerBy19thForSemiMonthly() {
		logger.info("******************  Notification Job Scheduler every 19th of the month at 11:45 PM started (Semi_Monthly) to Level 2 Manager..! **********");
		try {
			  level1ORLevel2ManagersCronRemainder(Constants.SEMI_MONTHLY,Constants.level2);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Prasad salina
	 * mail to level2 Managers Semi-Monthly and Monthly
	 * Fires every 4th of the month at 11:45 PM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 4 1/1 ?")
	public void runJobSchedulerBy4thForSemiMonthly() {
		try {
			  logger.info("******************  Notification Job Scheduler every 4th of the month at 11:45 PM started (Semi_Monthly) to Level 2 Manager..! **********");
			  level1ORLevel2ManagersCronRemainder(Constants.SEMI_MONTHLY,Constants.level2);
			  
			  logger.info("******************  Notification Job Scheduler every 4th of the month at 11:45 PM started (Monthly) to Level 1 Manager..! **********");
			  level1ORLevel2ManagersCronRemainder(Constants.MONTHLY,Constants.level2);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	@Async
	private void level1ORLevel2ManagersCronRemainder(String invoiceFrequency, int managerLevel) throws Exception {
		List<Org> orgList = orgService.getOrgs();
		Map<String, String> timesheetFrequencyMap = null;
		for (Org org : orgList) {
			List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
			for (OrgLocations ol : orgLocations) {

				List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService
						.getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(ol.getId(), invoiceFrequency,
								managerLevel);

				for (ProjectApproverHierarchy pah : projectApproverHierarchyList) {

					HashSet<ProjectMembers> projectMembersList = userRoleService
							.getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(invoiceFrequency,
									pah.getApproverUserId().getId(),ol.getId());

					if (null != projectMembersList && !projectMembersList.isEmpty()) {
						StringBuilder tempHTML = new StringBuilder();
						int count = 0;
						if (count == 0) {
							tempHTML.append(
									"<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
							tempHTML.append("<tr>");
							tempHTML.append("<th> Employee Name </th>");
							tempHTML.append("<th> Frequency </th>");
							tempHTML.append("<th> Project Name </th>");
							if (managerLevel != 1) {
								tempHTML.append("<th> Manager Name </th>");
							} else {

							}
							tempHTML.append("</tr>");
							count++;
						}
						boolean pmCheck = true;
						for (ProjectMembers projectMembers : projectMembersList) {
							if(projectMembers.getProjectMemberCandidteId() == null  || projectMembers.getProjectMemberCandidteId().getConsultantUser() == null)
								continue;
							 boolean freqCheck = true;
							timesheetFrequencyMap = timesheetFrequencyGeneratorUtil
									.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,ol,
											"approverScheduler");
							if (null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()) {
								LinkedList<String> frequency = new LinkedList<>();
								 Date cronStartDate = null;
								 Date cronEndDate = null;
								for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();
									Date frequencyStartDate = commonUtil.orgLocationDateFormat(ol.getDateFormat(),
											key.replaceAll("/", "-"));
									Date frequencyEndDate = commonUtil.orgLocationDateFormat(ol.getDateFormat(),
											value.replaceAll("/", "-"));
									Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
									currentCal.setLenient(false);
									currentCal.setTime(CommonUtil.currentDate());

									Boolean flag = true;
									 if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 && invoiceFrequency.equalsIgnoreCase(Constants.BI_WEEKLY)){
						            	 flag = false;
						             }
						             
						             if(flag && submissionApproverHierarchyService.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMembers.getMasterCandidateId().getId(),projectMembers.getProjects().getId()) ){
						            	 freqCheck = false;
						            	 pmCheck = false;  
						            	 if(cronStartDate == null){
												cronStartDate = frequencyStartDate;
											}
											if(cronEndDate == null || CalendarUtil.getNextDate(cronEndDate).equals(frequencyStartDate)){
												cronEndDate = frequencyEndDate;
											}else{
												String stDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronStartDate);
												String eDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronEndDate);
												frequency.add("<b>"+stDate.replaceAll("/", "-")+" To "+eDate.replaceAll("/", "-"));
												cronStartDate = null;
												cronEndDate = null;
											}
									 }
						             

								}
								if(freqCheck)
									continue;
								tempHTML.append("<tr>");
								if (projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName() != null
										&& projectMembers.getProjectMemberCandidteId().getConsultantUser().getLastName() != null) {
									tempHTML.append("<td>" + projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName() + " "
											+ projectMembers.getProjectMemberCandidteId().getConsultantUser().getLastName() + "</td>");
								} else {
									tempHTML.append(
											"<td>" + projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName() + "</td>");
								}
								
								if(cronStartDate != null && cronEndDate != null){
								    String stDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronStartDate);
									String eDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronEndDate);
									frequency.add("<b>"+stDate.replaceAll("/", "-")+" To "+eDate.replaceAll("/", "-"));
									cronStartDate = null;
									cronEndDate = null;
							    }
								if (frequency != null && !frequency.isEmpty()) {
									tempHTML.append(
											"<td>" + frequency.stream().collect(Collectors.joining("<br/>")) + "</td>");
								}
								tempHTML.append("<td>" + projectMembers.getProjects().getProjectName() + "</td>");
								if (managerLevel != 1) {
									ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService
											.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),
													projectMembers.getProjectMemberCandidteId().getId());
									tempHTML.append("<td>")
											.append(managerProjectApproverHierarchy.getApproverUserId().getFullName())
											.append("</td>");
								}
								tempHTML.append("</tr>");
							}
						}
						if (StringUtils.isNotEmpty(tempHTML.toString())) {
							tempHTML.append("</table> ");
						}
						if(pmCheck)
							continue;
						if (null != pah.getApproverUserId() && null != timesheetFrequencyMap
								&& !timesheetFrequencyMap.isEmpty()) {
							projectsService.userEmailToAdmin(tempHTML, ol, pah.getApproverUserId());
						}
					}

				}

			}
		}

	}
	//=============================================== LEVEL 2 TIME SHEETS MAILS END==========================================
	
	
	//=============================================== ADMIN TIME SHEETS MAILS STARTED==========================================
	
	/**
	 * @author Prasad Salina
	 * this is for Admin
	 * Fires on Friday at 11:45 PM of every week of the month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 ? * FRI")
	public void runJobSchedulerByFRIDay() {
		logger.info("******************  Notification Job Scheduler WednesDay of every week of the Month started (Weekly) to Admin..! **********");
		try {
			adminCronRemainder(Constants.WEEKLY);
			
			logger.info("******************  Notification Job Scheduler WednesDay of every week of the Month started (Bi_Weekly) to Admin..! **********");
			adminCronRemainder(Constants.BI_WEEKLY);
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Prasad salina
	 * mail to Admin Semi-Monthly
	 * Fires every 21st of the month at 11:45 PM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 21 1/1 ?")
	public void runJobSchedulerBy21stForSemiMonthly() {
		logger.info("******************  Notification Job Scheduler every 21st of the month at 11:45 PM started (Semi_Monthly) to Admin..! **********");
		try {
			adminCronRemainder(Constants.SEMI_MONTHLY);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * @author Prasad salina
	 * mail to Admin Semi-Monthly and Monthly
	 * Fires every 6th of the month at 11:45 PM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 45 23 2 1/1 ?")
	public void runJobSchedulerBy6thForSemiMonthly() {
		try {
			  logger.info("******************  Notification Job Scheduler every 6th of the month at 11:45 PM started (Semi_Monthly) to Admin..! **********");
			  adminCronRemainder(Constants.SEMI_MONTHLY);
			  
			  logger.info("******************  Notification Job Scheduler every 6th of the month at 11:45 PM started (Monthly) to Admin..! **********");
			  adminCronRemainder(Constants.MONTHLY);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	@Async
	private void adminCronRemainder(String invoiceFrequency) throws Exception {
		try {
			List<Org> orgList = orgService.getOrgs();
			Map<String,String>  timesheetFrequencyMap = null;
			for (Org org : orgList) {
					StringBuilder tempHTML = new StringBuilder();
					int count=0;
					tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
					List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
					for (OrgLocations ol : orgLocations) {
						     if(count==0){	
							            tempHTML.append("<tr>");
								        tempHTML.append("<th> Employee Name </th>");
								        tempHTML.append("<th> Frequency </th>");
										tempHTML.append("<th> Project Name </th>");
										tempHTML.append("<th> Manager Name </th>");
										tempHTML.append("</tr>");
										count++;
							   }
						    	
						    	 HashSet<ProjectMembers> projectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(invoiceFrequency,ol.getId());
						    	 boolean pmCheck = true;
						    	 if(null != projectMembersList && !projectMembersList.isEmpty()){
									for (ProjectMembers projectMembers : projectMembersList) {
										 boolean freqCheck = true;
										timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,ol,"approverScheduler");
										  if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
										            LinkedList<String> frequency = new LinkedList<>();
													 Date cronStartDate = null;
													 Date cronEndDate = null;
													
													 for(Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
														    String key = entry.getKey();
														    String value =entry.getValue();
														    
														    Date frequencyStartDate = commonUtil.orgLocationDateFormat(ol.getDateFormat(),key.replaceAll("/", "-"));
															Date frequencyEndDate = commonUtil.orgLocationDateFormat(ol.getDateFormat(),value.replaceAll("/", "-"));
															Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
															currentCal.setLenient(false);
															currentCal.setTime(CommonUtil.currentDate());
												             Boolean flag = true;
												             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 && invoiceFrequency.equalsIgnoreCase(Constants.BI_WEEKLY)){
												            	 flag = false;
												             }
												             
												             if(flag && submissionApproverHierarchyService.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMembers.getMasterCandidateId().getId(),projectMembers.getProjects().getId())){
												            	    freqCheck = false;
												            	    pmCheck = false;
												            	    if(cronStartDate == null){
																		cronStartDate = frequencyStartDate;
																	}
																	if(cronEndDate == null || CalendarUtil.getNextDate(cronEndDate).equals(frequencyStartDate)){
																		cronEndDate = frequencyEndDate;
																	}else{
																		String stDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronStartDate);
																		String eDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronEndDate);
																		frequency.add("<b>"+stDate.replaceAll("/", "-")+" To "+eDate.replaceAll("/", "-"));
																		cronStartDate = null;
																		cronEndDate = null;
																	}
															 }
														    
													 }
													 if(freqCheck)
														continue;
													 tempHTML.append("<tr>");
													 if(projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName()!= null && projectMembers.getProjectMemberCandidteId().getConsultantUser().getLastName() !=null){
														 tempHTML.append("<td style='text-align: center;'>"+projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName()+" "+projectMembers.getProjectMemberCandidteId().getConsultantUser().getLastName()+"</td>");
													 }else{
														 tempHTML.append("<td style='text-align: center;'>"+projectMembers.getProjectMemberCandidteId().getConsultantUser().getFirstName()+"</td>");
													 }
													 if(cronStartDate != null && cronEndDate != null){
														    String stDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronStartDate);
															String eDate = commonUtil.dateStrFromOrgDateFormatter(ol.getDateFormat(),cronEndDate);
															frequency.add("<b>"+stDate.replaceAll("/", "-")+" To "+eDate.replaceAll("/", "-"));
															cronStartDate = null;
															cronEndDate = null;
													 }
												     if(frequency != null && !frequency.isEmpty()){
													    tempHTML.append("<td style='text-align: center;'>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
												     }
													  tempHTML.append("<td style='text-align: center;'>"+projectMembers.getProjects().getProjectName()+"</td>");
													 
													  Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectMembers.getProjects().getId(),ol.getId());
													  StringBuilder projectStringBuilder=new StringBuilder();
													  for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
														    if(StringUtils.isNoneEmpty(projectStringBuilder.toString())){
														    	projectStringBuilder.append(" , ");
														    }
													    	if(approverHierarchy.getLevel()==1){
													    		projectStringBuilder.append(approverHierarchy.getApproverUserId().getFullName()).toString();
													    	}else if(approverHierarchy.getLevel()==2){
													    		projectStringBuilder.append(approverHierarchy.getApproverUserId().getFullName()).toString();
													    	}
													    }
													    
													  tempHTML.append("<td style='text-align: center;'>").append(projectStringBuilder).append("</td>");
													  tempHTML.append("</tr>");
										          }
									       }	
									}
						    	    if(pmCheck)
						    	    	continue;
						    	    tempHTML.append("</table> ");
						    	    
									if(null != ol.getUser() && null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
									   projectsService.userEmailToAdmin(tempHTML,ol,ol.getUser());
									}
					  }
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//=============================================== ADMIN TIME SHEETS MAILS END==========================================
	
	
	
	/**
	 * @author Prasad salina
	 * for terminate projects when current date is crossed project end date
	 * Fires on Every Day at 12:05 AM emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 5 0 1/1 * ?")
	public void runJobSchedulerProjectsAndMembers() {
		try {
			  logger.info("******************  Notification Job Scheduler for terminate projects when current date is crossed project end date..! **********");
			  List<Projects> projectsList = projectsService.getAllProjectsEndDateGraterthanCurrentDate();
				for(Projects project : projectsList){
					commonUtil.terminateProject(project);
				}
				projectsService.saveAllProjects(projectsList);
				
				commonUtil.terminateAllProjectMembers();
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	
	
	/**
	 * @author prasanna
	 * Fires on every 30 min on every day emailJobScheduler.job.cron.everyminute.expression
	 * @throws Exception 
	 */
	@Scheduled(cron = "0 0/30 * 1/1 * ?")
	@Async
	public void runRemainderEmailScheduler() throws Exception {
				logger.info("##########  E-Mail Job Scheduler started..! ##########");
				logger.info("########## Mail Sent to: Applicant,Interviewer,Others  ##########");
				DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int addMinuteTime = 30;
				Date targetTime = new Date(); //now
				Date date = new Date();
				targetTime = DateUtils.addMinutes(targetTime, addMinuteTime); 
				
				String triggerTimeStr = Constants.TZ_DF.format(targetTime); 
				String currentTimeStr = Constants.TZ_DF.format(date); 
				
				String endDateString = CommonUtil.convertTimeUTC(Constants.UTC_TIMEZONE, triggerTimeStr);
				String startDateString = CommonUtil.convertTimeUTC(Constants.UTC_TIMEZONE, currentTimeStr);
				Date endDateUTC = ts.parse(endDateString);
				Date startDateUTC = ts.parse(startDateString);
				
				List<RequirementInterviewHistory> interviewList = requirementInterviewHistoryService.getAllInterviewListByCurrentDate(startDateString,endDateString);
				for (RequirementInterviewHistory interviewHistory : interviewList) {
					
					RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(interviewHistory.getInterviewId().getId().toString());
			 		
					int remainderTime = -(Integer.parseInt(requirementInterview.getRemainedBefore()));
					Date newTargetTime = DateUtils.addMinutes(requirementInterview.getStartTime(), remainderTime); 

					
					if(startDateUTC.after(newTargetTime) && newTargetTime.before(endDateUTC)){
					Org org = orgService.findOrgByID(requirementInterview.getSubmissionId().getOrgLocations().getOrg().getId());
					User user = userService.getUserByUserCode(requirementInterview.getUpdatedBy());
					 
					List<RequirementInterviewer> requirementInterviewerList = requirementInterviewerService.getInterviwersListByInterviewId(requirementInterview.getId());
					
					if (requirementInterviewerList!=null) {
						for (RequirementInterviewer requirementInterviewer : requirementInterviewerList) {
							requirementInterviewService.sendEmailToInterviewrs(requirementInterview.getSubmissionId().getCandidate(),requirementInterviewer.getInterviewerUser(),requirementInterview.getSubmissionId(), user, org, requirementInterview, requirementInterview.getSubmissionId().getOrgLocations());
						}
					}
					
					if(interviewHistory.getNotificationToApplicant() != null) {
						if(interviewHistory.getNotificationToApplicant().equalsIgnoreCase("Y")){
							requirementInterviewService.sendEmailToApplicants(requirementInterview.getSubmissionId().getCandidate(),requirementInterview.getSubmissionId().getOrgLocations(),requirementInterview.getSubmissionId(), user, org, requirementInterview);
						}
					}
					
					if(interviewHistory.getNotificationToSpoc() != null && interviewHistory.getNotificationToSpoc().equalsIgnoreCase("Y")){

						requirementInterviewService.sendEmailToVendors(requirementInterview.getSubmissionId().getCandidate(),requirementInterview.getSubmissionId(), user,org, requirementInterview,requirementInterview.getSubmissionId().getOrgLocations());

					
					if(requirementInterview.getNotificationToOthers().equalsIgnoreCase("Y")){
						String[] othersEmails = requirementInterview.getNotificationToOthersEmails().split(",");
						for (String emailId : othersEmails) {
							User otherUser = new User();
							otherUser.setEmail(emailId);
							requirementInterviewService.sendEmailToInterviewrs(requirementInterview.getSubmissionId().getCandidate(),otherUser,requirementInterview.getSubmissionId(), user, org, requirementInterview, requirementInterview.getSubmissionId().getOrgLocations());
						}
					}
					}
						

				}
		}
    }
	
	/**
	 * @author David Raju
	 * This Cron Method is to add  the Candidates which are consultants  to  HotList (i,e making  them favorite list ) When they are going to terminate  from  the Project within  the given noticePeriod 
	 * This Cron Runs Daily once at 3 AM
	 */
	@Scheduled(cron = "0 0 3 1/1 * ? ")
	public void runHotlistCandidatesDaily() throws Exception {
		logger.info("******************  Daily HotList Candidates Started **********");
			List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
			for (OrgLocations orgLocations : orgLocationsList) {
				
				List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
				 for(User user :userList){
					 List<ProjectMembers> projectMemberslist = null;
						// to bring the candidates who are going to terminate in a given time 
		  			  List<Candidate>   candidatesListToBeAddedInHotList = candidateService.getAllCandidatesForHotListingWhoAreTerminating(orgLocations.getId(),30,user.getId());
		  			
		  			    if(null != candidatesListToBeAddedInHotList && candidatesListToBeAddedInHotList.size() > 0){
		  			    	List<CandidatesHotListDummy> hotlist  =  new ArrayList<CandidatesHotListDummy>();
		  			    	 for(Candidate can :candidatesListToBeAddedInHotList){
		  			    		can.setIsFavourite(Constants.RECORD_ACTIVE);
		  			    		Candidate candi = candidateService.addCandidate(can);
		  			    		projectMemberslist  = 	projectMembersService.getAllProjectsByUserIdForCandidatesHotList(can.getConsultantUser().getId());
			  			  		if(null != projectMemberslist && projectMemberslist.size() > 0){
				  			  		
			  			  		List<CandidatesHotListDummy> hotlist1  =  new ArrayList<CandidatesHotListDummy>();
			  			  			
				  			  		for(ProjectMembers pm :projectMemberslist){
				  			  			ProjectMembersHistory pmh = 	projectMembersHistoryService.getProjectMembeHistoryForHotlistCandidatesByUsedId(pm.getId());
				  			  			CandidatesHotListDummy candidatesHotListDummy = new CandidatesHotListDummy();
				  			  			
				  			  			StringBuilder cName = new StringBuilder();
				  			  			if(null != can.getLastName()){
				  			  				cName.append("can.getFirstName()").append(" ").append(can.getLastName());
				  			  			}else{
				  			  				cName.append("can.getFirstName()");
				  			  			}
				  			  			candidatesHotListDummy.setCandidateName(cName.toString());
				  			  			candidatesHotListDummy.setCandidateEmail(can.getEmail());
				  			  			candidatesHotListDummy.setCandidatePhnNo(can.getMobilePhone());
				  			  		    candidatesHotListDummy.setProjectName(pm.getProjects().getProjectName());
				  			  		   
				  			  		    String dateOfInterviewStr ="";
					  			  		Timezone timezone = null;
					  					if(null != orgLocations.getTimezone()){
					  				    	 timezone = timeZoneDao.getTimezone(orgLocations.getTimezone().getId());
					  					 }
				  			  		    String currentTimeStr = Constants.TZ_DF.format(pmh.getProjectTerminationDate());
					  					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					  					String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
					  					Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
					  					interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),interviewConverteDate);
					  					dateOfInterviewStr = interviewDateInTimezone;
				  			  		    candidatesHotListDummy.setProjectEndDate(dateOfInterviewStr);
				  			  		   hotlist1.add(candidatesHotListDummy);
				  			  		   
				  			  		}
				  			  		
				  			  	hotlist.addAll(hotlist1);
				  			  		 
			  			  		}
				  			  	CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candi);
				  			  	sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candi.getId().toString());
		  			    	 }
				  			  sendEmailToManager(user, projectMemberslist, candidatesListToBeAddedInHotList,hotlist, orgLocations, orgLocations.getOrg());
		  			    }
				 }
			}
    }

	/*@Scheduled(cron = "0 0/30 * 1/1 * ?")
	@Async
	public void scheduleUserMail() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int addMinuteTime = 30;
		Date targetTime = new Date(); // now
		Date date = new Date();
		targetTime = DateUtils.addMinutes(targetTime, addMinuteTime);
		String currentTime = dateFormat.format(date);
		DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
		String time = dateFormat1.format(date);
		final Date dateObj = dateFormat1.parse(time);
		String taskTime = new SimpleDateFormat("KK:mm a").format(dateObj);
		List<Tasks> taskDetails = taskService.getTaskByScheduledTime(currentTime);
		for (Tasks task : taskDetails) {

			if (taskTime.equals(task.getTaskTime())) {
				if (taskTime.equalsIgnoreCase(task.getTaskTime())) {
				taskService.mailToUserTask(task);
			}
		  }
		}

	}*/
	
	
	
	@Scheduled(cron = "0 30 18 1/1 * ?")
	@Async
	public void scheduleUserMailDailyTask() throws Exception {
		List<Tasks> taskDetails = taskService.getTaskListByRepeatStatus(Constants.JOB_ALERT_DURATION_DIALY);
		for (Tasks task : taskDetails) {
			taskService.mailToUserTask(task);
		  }
		}
	
	
	@Scheduled(cron = "0 30 18 ? * MON")
	@Async
	public void scheduleUserMailWeeklyTask() throws Exception {
		List<Tasks> taskDetails = taskService.getTaskListByRepeatStatus(Constants.JOB_ALERT_DURATION_WEEKLY);
		for (Tasks task : taskDetails) {
			taskService.mailToUserTask(task);
		  }
		}
	
	
	@Scheduled(cron = "0 30 18 1 1/1 ?")
	@Async
	public void scheduleUserMailMonthlyTask() throws Exception {
		List<Tasks> taskDetails = taskService.getTaskListByRepeatStatus(Constants.MONTHLY);
		for (Tasks task : taskDetails) {
			taskService.mailToUserTask(task);
		  }
		}
	
	@Scheduled(cron = "0 30 18 1 1 ?")
	@Async
	public void scheduleUserMailYearlyTask() throws Exception {
		List<Tasks> taskDetails = taskService.getTaskListByRepeatStatus(Constants.YEARLY);
		for (Tasks task : taskDetails) {
			taskService.mailToUserTask(task);
		  }
		}
	
	
	public void sendEmailToManager(User user, List<ProjectMembers> projectMemberslist, List<Candidate> candidatesListToBeAddedInHotList, List<CandidatesHotListDummy> hotlist , OrgLocations orgLocations, Org org)	throws Exception {
		 
		EmailMessage emailMessage = new EmailMessage();
		
		
		    StringBuilder sb = new StringBuilder();
		
	        sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center;' cellpadding='2px' cellspacing='0' border='0'>");
	        sb.append("<thead style='background-color:#375d81; color: #fff;'>");
	        sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");            
	        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Name</th>");          
	        sb.append("<th style=.border: 1px solid #12385c; text-align: center;'>Email Id </th>");                
	        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Phn No  </th>");                  
	        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Project Name </th>");
	        sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Project End Date </th>");
	        sb.append("</tr>");    
	        sb.append("</thead>");    
	        sb.append("<tbody>");
			
	  		for(CandidatesHotListDummy can :hotlist){
		        sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
		        sb.append("<td style='border: 1px solid #375d81;' width='300'>"+can.getCandidateName()+"</td>");                      
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+can.getCandidateEmail()+"</td>");                  
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+can.getCandidatePhnNo()+"</td>"); 
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+can.getProjectName()+"</td>"); 
		        sb.append("<td style='border: 1px solid #375d81;' width='225'>"+can.getProjectEndDate()+"</td>"); 
		        sb.append("</tr>");
	  		}
	  		sb.append("</tbody>");   
		    sb.append("/<table>"); 
		
		if(user.getFirstName()!=null){
			 emailMessage.setFirstName(user.getFirstName());
		}
		else{
			 emailMessage.setFirstName("");
		}
		if(user.getLastName()!=null){
			 emailMessage.setLastName(user.getLastName());
		}
		else{
			 emailMessage.setLastName("");
		}
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("");
		 emailMessage.setFromName(orgLocations.getOrgName());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("managerName", emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 globalVarsMap.put("consultantsCount", Integer.toString(candidatesListToBeAddedInHotList.size()));
		 globalVarsMap.put("tableOfContent", sb.toString());
		 
		//configured Email
			
			String fromEmail = null;
			 EmailConfigurationFromMail confFromMail=null;
			 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocations.getId());
			 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
				 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
					 if( fromMail.getEmailModules() != null){
						String[] jobIds = fromMail.getEmailModules().split(",");
							for(int i=0; i<jobIds.length; i++) {
								if(Long.valueOf(jobIds[i]) == 10l ){
									confFromMail=fromMail;
								}
							}
					 }
				 } 
				 
				 if(confFromMail == null){
					 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
					 if(confFromMail != null){
						 fromEmail = confFromMail.getEmail();
					 }
				 }else{
					 fromEmail = confFromMail.getEmail();
				 }
			 }
			 
			 if(StringUtils.isNotBlank(fromEmail)){
				 emailMessage.setFromEmail(fromEmail);
			 }
			 
		 //calling the sendEmail method of emailScenerioUtil
		 
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.HOTLIST_ALERT_EMAIL,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.HOTLIST_ALERT_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION); 
		 StringBuilder actionLinkBuilder = new StringBuilder();
		 String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("/candidatesList").toString();		 
		 if(user.getFirstName()!=null){
		 String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(),globalVarsMap,noificationEmailScenario);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 User sourceLeadAdmin = userService.getUserById(Constants.SOURCELEAD_ADMIN_USERID);
		 
		 emailScenerioUtil.sendNotification(actionLink, sourceLeadAdmin, user, orgLocations, noificationEmailScenario,new HashMap<String,String>());
		 }
	}
	
	
	/**
	 * @author prasanna
	 * Fires on every 30 min on every day emailJobScheduler.job.cron.everyminute.expression
	 * @throws Exception 
	 */

	//TODO commented by Rakshith
	//	@Scheduled(cron = "0 0/30 * 1/1 * ?")
	//	@Async
	public void runContractDocumentStatus() throws Exception {
				logger.info("##########  E-Mail Job Scheduler started..! ##########");
				List<Contracts> conList =  contractsTemplateService.getListOfContractsEnvId();
				
				String UserName = "sourcelead@rediffmail.com";
				String Password = "Pwd!123";    
				String IntegratorKey = "1bf5d901-629e-4ec3-a915-8991fd08206a";
				
				/*String UserName = "rajasekhar@businessintelli.com";
				String Password = "rajashekar";    
				String IntegratorKey = "04e0361f-eebe-47ab-b3b2-d5d36c2d4dd4";*/

				// for production environment update to "www.docusign.net/restapi"
				String BaseUrl = "https://demo.docusign.net/restapi";

		        // list of user account(s)
		        List<LoginAccount> loginAccounts = null;
		        
		        // initialize the api client
		        ApiClient apiClient = new ApiClient();
		        apiClient.setBasePath(BaseUrl);
		        
		        // create JSON formatted auth header
		        String creds = "{\"Username\":\"" +  UserName + "\",\"Password\":\"" +  Password + "\",\"IntegratorKey\":\"" +  IntegratorKey + "\"}";
		        apiClient.addDefaultHeader("X-DocuSign-Authentication", creds);
		        
		        // assign api client to the Configuration object
		        Configuration.setDefaultApiClient(apiClient);
		        try
		        {
		        	// login call available off the AuthenticationApi
		        	AuthenticationApi authApi = new AuthenticationApi();
		        	
		        	// login has some optional parameters we can set
		            AuthenticationApi.LoginOptions loginOps = authApi.new LoginOptions();
		            loginOps.setApiPassword("true");
		            loginOps.setIncludeAccountIdGuid("true");
		            LoginInformation loginInfo = authApi.login(loginOps);
		         
		            // note that a given user may be a member of multiple accounts
		            loginAccounts = loginInfo.getLoginAccounts();
		            
		         //   System.out.println("LoginInformation: " + loginAccounts);
		        
		        
		            String accountId = loginAccounts.get(0).getAccountId();
		            // instantiate a new EnvelopesApi object
		            EnvelopesApi envelopesApi = new EnvelopesApi();  
		            
		            // the list status changes call requires at least a from_date
		            EnvelopesApi.ListStatusChangesOptions options = envelopesApi.new ListStatusChangesOptions();
		            
		            for (Contracts contracts : conList) {
		            		String parentFromStage = contracts.getTemplateSubmissionStatus();
			            	if(contracts.getEndDate()!=null){
			            		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			            		Date date = new Date();
			            		String currentTimeStr = TZ_DF_TS.format(date);
			    				Date curDate = TZ_DF_TS.parse(currentTimeStr);
			            		if(contracts.getEndDate().before(curDate)|| contracts.getEndDate().equals(curDate)){
			            			contracts.setId(contracts.getId());
			        				contracts.setTemplateSubmissionStatus(Constants.EXPIRED);
			        				String fromStage = contracts.getTemplateSubmissionStatus();
			            			Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
				        			if(null != childContarct && null != childContarct.getParentContractId()){
				        				fromStage = childContarct.getTemplateSubmissionStatus();
				        				childContarct.setId(childContarct.getId());
				        				childContarct.setTemplateSubmissionStatus(Constants.EXPIRED);
				        				contractsTemplateService.saveNewContract(childContarct);
				        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
					        				if(!(Constants.EXPIRED).equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
						        				ContractsHistory childContractsHistory = new ContractsHistory();
						            			childContractsHistory.setFromStage(fromStage);
						        				childContractsHistory.setContractsId(childContarct);
						        				childContractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
						        				childContractsHistory.setCreatedDate(new Date());
						        				childContractsHistory.setUpdatedDate(new Date());
						        				contractsTemplateDao.saveContractsHistory(childContractsHistory);
					        				}
				        				}
			            		}
			            	 }
			            	}
			            	
			            	try{
			            		if(null != contracts.getEnvelopId() ){
					            	if(!Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!Constants.EXPIRED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!"completed".equalsIgnoreCase(contracts.getContractStatus())){
						            	 //envelopesApi.getEnvelope(accountId, contracts.getEnvelopId());
						            	Envelope evp =  envelopesApi.getEnvelope(accountId, contracts.getEnvelopId());
						            	if(evp.getStatus().equalsIgnoreCase("completed")&&!Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
						            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
						            		if(null != childContarct.getParentContractId()){
						            		String fromStage = childContarct.getTemplateSubmissionStatus();
						        				childContarct.setId(childContarct.getId());
						        				childContarct.setTemplateSubmissionStatus(Constants.SEND_FOR_VERIFICATION);
						        				contracts.setId(contracts.getId());
						        				contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_VERIFY);
						        				contracts.setContractStatus(evp.getStatus());
						        				childContarct.setContractStatus(evp.getStatus());
						        				contractsTemplateService.saveNewContract(childContarct);
						        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
						        				if(!(Constants.SEND_FOR_VERIFICATION).equalsIgnoreCase(fromStage)){
							        				ContractsHistory childContractsHistory = new ContractsHistory();
							            			childContractsHistory.setFromStage(fromStage);
							        				childContractsHistory.setContractsId(childContarct);
							        				childContractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
							        				childContractsHistory.setCreatedDate(new Date());
							        				childContractsHistory.setUpdatedDate(new Date());
							        				contractsTemplateDao.saveContractsHistory(childContractsHistory);
						        				}
						        				}
						        				//this method is used to send the email when receiver signed the contract
						        				if (!Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())){
						        					contractSignedByReceiver(contracts);
						        				}
						        			}
						            	}
						            	if(evp.getStatus().equalsIgnoreCase("declined")&&!Constants.EXPIRED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
						            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
						        			if(null != childContarct.getParentContractId()){
						        				String fromStage = childContarct.getTemplateSubmissionStatus();
						        				childContarct.setId(childContarct.getId());
						        				childContarct.setTemplateSubmissionStatus(Constants.REJECTED);
						        				contracts.setId(contracts.getId());
						        				contracts.setTemplateSubmissionStatus(Constants.REJECTED);
						        				contracts.setContractStatus(evp.getStatus());
						        				childContarct.setContractStatus(evp.getStatus());
						        				contractsTemplateService.saveNewContract(childContarct);
						        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
							        				if(!(Constants.REJECTED).equalsIgnoreCase(fromStage)){
								        				ContractsHistory childContractsHistory = new ContractsHistory();
								            			childContractsHistory.setFromStage(fromStage);
								        				childContractsHistory.setContractsId(childContarct);
								        				childContractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
								        				childContractsHistory.setCreatedDate(new Date());
								        				childContractsHistory.setUpdatedDate(new Date());
								        				contractsTemplateDao.saveContractsHistory(childContractsHistory);
							        				}
						        				}
						        			}
						            	}
						            	if(evp.getStatus().equalsIgnoreCase("deleted")&&!Constants.EXPIRED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
						            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
						        			if(null != childContarct.getParentContractId()){
						        				String fromStage = childContarct.getTemplateSubmissionStatus();
						        				childContarct.setId(childContarct.getId());
						        				childContarct.setTemplateSubmissionStatus(Constants.DELETED);
						        				contracts.setId(contracts.getId());
						        				contracts.setTemplateSubmissionStatus(Constants.DELETED);
						        				contracts.setContractStatus(evp.getStatus());
						        				childContarct.setContractStatus(evp.getStatus());
						        				contractsTemplateService.saveNewContract(childContarct);
						        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
						        				if(!(Constants.DELETED).equalsIgnoreCase(fromStage)){
							        				ContractsHistory childContractsHistory = new ContractsHistory();
							            			childContractsHistory.setFromStage(fromStage);
							        				childContractsHistory.setContractsId(childContarct);
							        				childContractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
							        				childContractsHistory.setCreatedDate(new Date());
							        				childContractsHistory.setUpdatedDate(new Date());
							        				contractsTemplateDao.saveContractsHistory(childContractsHistory);
						        				}
						        				}
						        			}
						            	}
						            	
						            	
					            	}
					            	}
			            	}catch(Exception e){
			            		logger.info("****************** Envelop not found for the Id : "+contracts.getEnvelopId()+"******************");
			            		continue;
			            	}
			            	contractsTemplateService.saveNewContract(contracts);
			            	if(!parentFromStage.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
				            	ContractsHistory contractsHistory = new ContractsHistory();
		            			contractsHistory.setFromStage(parentFromStage);
		        				contractsHistory.setContractsId(contracts);
		        				contractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
		        				contractsHistory.setCreatedDate(new Date());
		        				contractsHistory.setUpdatedDate(new Date());
		        				contractsTemplateDao.saveContractsHistory(contractsHistory);
			            	}
			            	
						}
		            
				
		        }
		        catch (com.docusign.esign.client.ApiException ex)
		        {
		            System.out.println("Exception: " + ex);
		        }
	}
	
	/**
	 * cron to move expired contract to EXPIRED stage
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0/30 * 1/1 * ?")
	@Async
	public void runExpiredContract() throws Exception{
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLoc : orgLocationsList) {
			List<Contracts> contractList = contractsTemplateService.getContractByLocationId(orgLoc.getId());
			if(contractList != null && contractList.size() > 0) {
				for(Contracts contracts : contractList) {
				   	if(contracts.getEndDate()!=null){
			    		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			    		Date date = new Date();
			    		String currentTimeStr = TZ_DF_TS.format(date);
						Date curDate = TZ_DF_TS.parse(currentTimeStr);
			    		if(contracts.getEndDate().before(curDate)|| contracts.getEndDate().equals(curDate)){
			    			contracts.setId(contracts.getId());
							contracts.setTemplateSubmissionStatus(Constants.EXPIRED);
							String fromStage = contracts.getTemplateSubmissionStatus();
			    			Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
			    			if(null != childContarct && null != childContarct.getParentContractId()){
			    				fromStage = childContarct.getTemplateSubmissionStatus();
			    				childContarct.setId(childContarct.getId());
			    				childContarct.setTemplateSubmissionStatus(Constants.EXPIRED);
			    				contractsTemplateService.saveNewContract(childContarct);
			    				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
			        				if(!(Constants.EXPIRED).equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
				        				ContractsHistory childContractsHistory = new ContractsHistory();
				            			childContractsHistory.setFromStage(fromStage);
				        				childContractsHistory.setContractsId(childContarct);
				        				childContractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
				        				childContractsHistory.setCreatedDate(new Date());
				        				childContractsHistory.setUpdatedDate(new Date());
				        				contractsTemplateDao.saveContractsHistory(childContractsHistory);
			        				}
			    				}
			    		}
			    	 }
			    	}
				}
			}
		}
	}
	
	
	public void contractSignedByReceiver(Contracts contracts) throws Exception
	{
		for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
			User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
			User receiver = coh.getOwnerUserId();
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(owner.getFirstName());
			 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
				 emailMessage.setLastName(owner.getLastName());
			 }else{
				 emailMessage.setLastName("");
			 }
			 emailMessage.setToEmail(owner.getEmail());
			 emailMessage.setFromEmail(receiver.getEmail());
			 emailMessage.setCreatedBy("sladmin");
			 if(receiver.getLastName()!= null && !"".equalsIgnoreCase(receiver.getLastName())){
				 StringBuilder sb = new StringBuilder();
				 emailMessage.setFromName(sb.append(receiver.getFirstName()).append(" ").append(receiver.getLastName()).toString());
			 }else{
				 emailMessage.setFromName(receiver.getFirstName());
			 }
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
				 globalVarsMap.put("contractTitle", contracts.getContractTitle());
				 }else{
					 globalVarsMap.put("contractTitle","--");
				 }
			 
			 if(owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())){
				StringBuilder ownersb = new StringBuilder();
				 globalVarsMap.put("ownerUser", ownersb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
				 }else{
					 globalVarsMap.put("ownerUser",owner.getFirstName());
				 }
			 
			 if(receiver.getFirstName() != null && !"".equalsIgnoreCase(receiver.getFirstName()) && receiver.getLastName() != null && !"".equalsIgnoreCase(receiver.getLastName())){
					StringBuilder receiversb = new StringBuilder();
					 globalVarsMap.put("receiverUser", receiversb.append(receiver.getFirstName()).append(" ").append(receiver.getLastName()).toString());
					 }else{
						 globalVarsMap.put("receiverUser",receiver.getFirstName());
					 }
			
			 if(contracts.getContractOrgLocationId() != null){
				 globalVarsMap.put("receiverOrg", contracts.getContractOrgLocationId().getOrg().getOrgName());
				 }else{
					 globalVarsMap.put("receiverOrg","--");
				 }
			 
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_NOTIFICATION);
			 
			 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			 notificationScenario.setBody(notificationbody);

			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 StringBuilder notificationLink = new StringBuilder();
			 String actionLink="";
		     actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=verify").toString();

			 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 
		 emailScenerioUtil.sendNotification(actionLink,receiver,owner, contracts.getOrgLocationId(), notificationScenario,new HashMap<String,String>());
		}
	}
	
	/**
	 * @author : Pavan Jampana
	 * this cron will used to send the remainder emails to respective owners about expiring of the contract. 
	 * 
	 * this cron will be fired once a day at 3'O clock
	 * 
	 * Morning 8 Am for us/canada
	 */

	/*@Scheduled(cron = "0 0 15 1/1 * ?")
	@Async
	public void notifyToUsersAboutContractExpiry() throws Exception
	{
		DateTime zoned = new DateTime();
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// to fire remainder one week before
		DateTime week = zoned.plusDays(7);
		String startDate = outputFormatter1.print(week);
		String startDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, startDate);
		Date startDateUTC = ts.parse(startDateString);
		String endDate = outputFormatter.print(week);
		String endDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, endDate);
		Date endDateUTC = ts.parse(endDateString);
		
		List<Contracts> oneWeekContractsList = contractsTemplateService.getAllContractsBasedOnTimeFrequency(startDateUTC,endDateUTC);
		for(Contracts contracts : oneWeekContractsList){
			contractExpiryRemainderMail(contracts,7);
		}
		
		//to fire two days before
		DateTime twodays = zoned.plusDays(2);
		String twoDaysStartDate = outputFormatter1.print(twodays);
		String twoDaysStartDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, twoDaysStartDate);
		Date twoDaysStartDateUTC = ts.parse(twoDaysStartDateString);
		String twoDaysEndDate = outputFormatter.print(twodays);
		String twoDaysEndDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, twoDaysEndDate);
		Date twoDaysEndDateUTC = ts.parse(twoDaysEndDateString);
		
		List<Contracts> twoDaysContractsList = contractsTemplateService.getAllContractsBasedOnTimeFrequency(twoDaysStartDateUTC,twoDaysEndDateUTC);
		for(Contracts contracts : twoDaysContractsList){
			contractExpiryRemainderMail(contracts,2);
		}
		
		//to fire one days before
		DateTime oneDay = zoned;
		String oneDayStartDate = outputFormatter1.print(oneDay);
		String oneDayStartDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, oneDayStartDate);
		Date oneDayStartDateUTC = ts.parse(oneDayStartDateString);
		String oneDayEndDate = outputFormatter.print(oneDay);
		String oneDayEndDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, oneDayEndDate);
		Date oneDayEndDateUTC = ts.parse(oneDayEndDateString);
		
		List<Contracts> oneDayContractsList = contractsTemplateService.getAllContractsBasedOnTimeFrequency(oneDayStartDateUTC,oneDayEndDateUTC);
		for(Contracts contracts : oneDayContractsList){
			contractExpiryRemainderMail(contracts,1);
		}
		
		//this cron is related to invoices to run every day.
//		getDueDateCompletedInvoicesLists();

	}*/

	/**
	 * @author Rajashekar
	 * changing invoice payment status when due date is over
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 15 1/1 * ?")
	@Async
	public void getDueDateCompletedInvoicesLists() throws Exception {
		List<Invoice> invoiceList = invoiceService.getDueDateCompletedInvoicesList();
		invoiceList.forEach(invoice -> {
			if (invoice.getDueDate() != null && !"".equals(invoice.getDueDate())) {
				if (new Date().after(invoice.getDueDate())) {
					invoice.setPaymentStatus(Constants.OVER_DUE);
					try {
						invoiceService.saveInvocePojo(invoice);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
	}
	

	/*private void contractExpiryRemainderMail(Contracts contracts, int days) throws Exception
	{
			User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
			OrgLocations orgLocations = orgLocationService.getOrgLocations(contracts.getOrgLocationId().getId());
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(owner.getFirstName());
			 
			 
			//Url for redirection 			 
		     String redirectURL = "/contractListbyStatus-params-"+"contractType="+contracts.getContractType()+"-and-status=waiting";
			 Long redirectOrgLocationId = orgLocations.getId();

			 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
				 
				 emailMessage.setLastName(owner.getLastName());
			 }else{
				 emailMessage.setLastName("");
			 }
			 emailMessage.setToEmail(owner.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setCreatedBy("sladmin");
			 emailMessage.setFromName(orgLocations.getOrgName()); 
			 
			 
			 String fromEmail = null;
			 EmailConfigurationFromMail confFromMail=null;
			 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocations.getId());
			 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
				 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
					 if( fromMail.getEmailModules() != null){
						String[] jobIds = fromMail.getEmailModules().split(",");
							for(int j=0; j<jobIds.length; j++) {
								if(Long.valueOf(jobIds[j]) == 16L ){
									confFromMail=fromMail;
								}
							}
					 }
				 } 
				 
				 if(confFromMail == null){
					 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
					 if(confFromMail != null){
						 fromEmail = confFromMail.getEmail();
					 }
				 }else{
					 fromEmail = confFromMail.getEmail();
				 }
			 }
			 
			 if(StringUtils.isNotBlank(fromEmail)){
				 emailMessage.setFromEmail(fromEmail);
			 }
	         
			 
			 String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),contracts.getEndDate());
			 String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),contracts.getStartDate());
			 String dayCount = String.valueOf(days);
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
				 globalVarsMap.put("contractTitle", contracts.getContractTitle());
				 }else{
					 globalVarsMap.put("contractTitle","--");
				 }
			 
			 if(contracts.getContractType()!= null && !"".equalsIgnoreCase(contracts.getContractType())){
				 globalVarsMap.put("contractType", contracts.getContractType());
				 }else{
					 globalVarsMap.put("contractType","--");
				 }
			
			 if(contracts.getEndDate()!= null && !"".equalsIgnoreCase(contracts.getEndDate().toString())){
				 globalVarsMap.put("contractEndDate", endDate);
				 }else{
					 globalVarsMap.put("contractEndDate","--");
				 }
			 
			 if(contracts.getStartDate()!= null && !"".equalsIgnoreCase(contracts.getStartDate().toString())){
				 globalVarsMap.put("contractStartDate", startDate);
				 }else{
					 globalVarsMap.put("contractStartDate","--");
				 }
			 
			 if(contracts.getTemplateSubmissionStatus() != null && !"".equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
				 globalVarsMap.put("status", contracts.getTemplateSubmissionStatus());
				 }else{
					 globalVarsMap.put("status","--");
				 }
			 
			 globalVarsMap.put("expiringdays",dayCount);
			 if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 
				 StringBuilder builder=new StringBuilder();
				 
				 globalVarsMap.put("clientContact",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
				 
			 }
			 else{
			 globalVarsMap.put("clientContact",contracts.getContactUser().getFirstName());
			 }
			 
			if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
				StringBuilder creatorSb = new StringBuilder();
				globalVarsMap.put("contractOwner", creatorSb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
			}else if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName())){
				globalVarsMap.put("contractOwner",owner.getFirstName());
			}else{
				globalVarsMap.put("contractOwner","--");
			}
			
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginUrl",loginBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append
                           ("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reminder</div></a>").toString());
			
			 if(contracts.getContactUser().getFirstName() != null && contracts.getContactUser().getLastName()!=null && !"".equalsIgnoreCase(contracts.getContactUser().getFirstName()) && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("receiverParty",userBuild.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
				 }else{
					 globalVarsMap.put("receiverParty",contracts.getContactUser().getFirstName());
				}
			 
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_EXPIRY_REMAINDER,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_EXPIRY_REMAINDER,Constants.ENTITY_TYPE_NOTIFICATION);

			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			 notificationScenario.setBody(notificationbody);
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
			 if(StringUtils.isNotBlank(fromMail)){
				 emailMessage.setFromEmail(fromMail);
			 } 
			 StringBuilder notificationLink = new StringBuilder();
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 String actionLink="";
			 actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=waiting").toString();
			 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
//			 emailScenerioUtil.sendNotification(actionLink, null,owner, contracts.getOrgLocationId(), notificationScenario);
		
	}*/
	
	
	
	@Scheduled(cron = "0 45 23 1/1 * ?")
	public void runJobSchedulerByForClosedRequirementsDaily() {
		logger.info("******************  Close Jobs  Every Day ! **********");
		
		try {
			DateTime zoned = new DateTime();
	        DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
	        DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
	        DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        // to fire remainder one week before
	        String startDate = outputFormatter1.print(zoned);
	        String startDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, startDate);
	        Date startDateUTC = ts.parse(startDateString);
	        String endDate = outputFormatter.print(zoned);
	        String endDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, endDate);
	        Date endDateUTC = ts.parse(endDateString);
	        
	        List<Requirement> requirementsList = requirementService.getAllClosedRequirementsByStartDateAndEndDate(startDateUTC, endDateUTC);
			
			 if(null != requirementsList && requirementsList.size() > 0){
		        	for(Requirement requirement : requirementsList){
		        		requirement.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionStatus());
		        		requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.CLOSED);
		        		requirementService.addRequirement(requirement);
		            }
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	
	@Scheduled(cron = "0 30 18 1/1 * ?")
	public void runDialyjobAlerts() {
		logger.info("******************  Jobs Alerts  Every Day ! **********");
		try {
			List<JobAlert> jobAlertsList = jobAlertService.getConfiruredJobsAlertsList(Constants.JOB_ALERT_DURATION_DIALY);
			getJobsList(jobAlertsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	@Scheduled(cron = "0 30 18 ? * MON")
	public void runWeeklyjobAlerts() {
		logger.info("******************  Jobs Alerts  Weekly ! **********");
		try {
			List<JobAlert> jobAlertsList = jobAlertService.getConfiruredJobsAlertsList(Constants.JOB_ALERT_DURATION_WEEKLY);
			getJobsList(jobAlertsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	@Scheduled(cron = "0 30 18 1 1/1 ?")
	public void runMonthlyjobAlerts() {
		logger.info("******************  Jobs Alerts  Monthly ! **********");
		try {
			List<JobAlert> jobAlertsList = jobAlertService.getConfiruredJobsAlertsList(Constants.JOB_ALERT_DURATION_MONTHLY);
			getJobsList(jobAlertsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	
	@Scheduled(cron = "0 0 1 1/1 * ?")
	public void candidateOffboringDelayMail() {
		logger.info("******************  Jobs Alerts  Every Day ! **********");
		try {
			List<CandidateOffBoarding> candidateOffBoarding = candidateOffBoardingService.candidateOffboringDelayMail();
			mailToOffboardCandidate(candidateOffBoarding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
	
	
	private void getJobsList(List<JobAlert> jobAlertsList){
		
		try{
			if(null != jobAlertsList && jobAlertsList.size() > 0){
				for(JobAlert jobAlert : jobAlertsList){
					List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
					 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
					 
					 BoolQueryBuilder anyStatusBoolQueryBuilder = QueryBuilders.boolQuery();
				     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "NEW"));
				     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
				     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "APPROVED"));
					 boolQueryBuilder.must(anyStatusBoolQueryBuilder);
					 
					 BoolQueryBuilder anyReleaseBoolQueryBuilder = QueryBuilders.boolQuery();
					 anyReleaseBoolQueryBuilder.should(QueryBuilders.matchQuery("isPublicJob", "Y"));
					 anyReleaseBoolQueryBuilder.should(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
					 boolQueryBuilder.must(anyReleaseBoolQueryBuilder);
					 
					 
					 if(!isNullOrEmpty(jobAlert.getCity())){
						 boolQueryBuilder.must(QueryBuilders.matchQuery("city", jobAlert.getCity()));
					 }
					 
					 if(!isNullOrEmpty(jobAlert.getExperience())){
						 boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(0).lte(new Integer(jobAlert.getExperience())));
					 }
					 
					 if(!isNullOrEmpty(jobAlert.getRate())){
						 boolQueryBuilder.must(QueryBuilders.matchQuery("currentRateCurrency", jobAlert.getRateCurrency()));
						 boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", jobAlert.getRateType()));
						 boolQueryBuilder.must(QueryBuilders.rangeQuery("maxRate").gte(0).lte(new Integer(jobAlert.getRate())));
					 }
					 
					 if(!isNullOrEmpty(jobAlert.getIndustryName())){
						 boolQueryBuilder.must(QueryBuilders.matchQuery("industryName", jobAlert.getIndustryName().trim()));
					 }
					 
					 if(!isNullOrEmpty(jobAlert.getSkillsName())){
						 
						 ArrayList<String> skillsOrKeyWordList = null;
						 skillsOrKeyWordList = new ArrayList<String>(Arrays.asList(jobAlert.getSkillsName().split(",")));
						 if(skillsOrKeyWordList != null && !skillsOrKeyWordList.isEmpty()){
							 BoolQueryBuilder anySkillOrKeyWordBoolQueryBuilder = QueryBuilders.boolQuery();
							 
							 for(String keywd : skillsOrKeyWordList){
								 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
								ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
								if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
									for(QueryBuilder QueryBuilder :QueryBuilderList){
										anyBoolSubQueryBuilder.should(QueryBuilder);
									}
									anySkillOrKeyWordBoolQueryBuilder.should(anyBoolSubQueryBuilder);
								}
							}
							 boolQueryBuilder.must(anySkillOrKeyWordBoolQueryBuilder);
						 }
						 
					 }
					 
					 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0, Constants.MAX_RESULTS_TWENTY,boolQueryBuilder).getHits();

			 	     if(null != searchHits && searchHits.length >0){
			 	    	ObjectMapper mapper = new ObjectMapper();
			 	    	for (SearchHit hit : searchHits) {
			 	    		StringBuilder json = new StringBuilder(hit.getSourceAsString());
			 	    	    RequirementIndex requirementIndex = mapper.readValue(json.toString(), RequirementIndex.class);
			 	    	    requirementIndex.setJobAlertTitle(jobAlert.getAlertTitle());
			 	    	    requirementList.add(requirementIndex);
			 	    	}
			 	    	
			 	    	sendJobAlertEmail(jobAlert,requirementList);
			 	     }
					 
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private ArrayList<QueryBuilder> getQueryBuilderList(String keywd){
		   
		   ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		    QueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		    QueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
			
			QueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("jobId", keywd));
			
			QueryBuilderList.add(QueryBuilders.matchQuery("industryName", keywd));
			
			QueryBuilderList.add(QueryBuilders.matchQuery("description", keywd));
			
			
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			}

				
			return QueryBuilderList; 
	 }
	 
	 
	 private void sendJobAlertEmail(JobAlert jobAlert,List<RequirementIndex> requirementList) throws Exception{
		 
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String alertUserName = null;
		 
		 if(null != jobAlert.getUserId()){
			 if(!isNullOrEmpty(jobAlert.getUserId().getLastName())){
				 alertUserName = sb.append(jobAlert.getUserId().getFirstName()).append(" ").append(jobAlert.getUserId().getLastName()).toString();
			 }else{
				 alertUserName = sb.append(jobAlert.getUserId().getFirstName()).toString();
			 } 
		 }else{
			 alertUserName = "Candidate";
		 }
		 
		 
		 emailMessage.setToEmail(jobAlert.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("sladmin");
		 
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("alertJobsCount", String.valueOf(requirementList.size()));
		 globalVarsMap.put("alertTitle", jobAlert.getAlertTitle());
		 globalVarsMap.put("alertUserName", alertUserName);
		 
		 String jobAlertsTable = getJobAlertsTable(requirementList,jobAlert.getJobAlertUUID());
		 
		 globalVarsMap.put("alertJobs", jobAlertsTable);
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.JOB_ALERTS,Constants.ENTITY_TYPE_EMAIL);
		
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	 }
	 
	 
	 
	 private String getJobAlertsTable(List<RequirementIndex> requirementList,String jobAlertUUID) throws Exception{
		 String tempHTML = null;
		 
			 
			 tempHTML = "<table align='center' style='border-spacing:0;font-family:Arial, sans-serif;color:#333333;Margin:0 auto;width:100%; overflow-x:auto; max-width:800px;border-collapse:collapse; border:1px solid #ddd;' >";
			 tempHTML = tempHTML+"<tbody>";
			 tempHTML = tempHTML+"<tr style='border:0;border-collapse:collapse;'>";
			 tempHTML = tempHTML+"<td style='padding-top:10px;padding-bottom:0px;padding-right:10px;padding-left:10px;background-color:#ffffff;'>";
			 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#333333'>";
			 tempHTML = tempHTML+"<tbody>";
			
			 int i = 1;
			 
			 for(RequirementIndex requirementIndex : requirementList){
				 
					 if(i <= 10){
						 
						 StringBuilder jobTitle = new StringBuilder();
						 if(!isNullOrEmpty(requirementIndex.getJobId())){
							 jobTitle = jobTitle.append(requirementIndex.getJobId()).append("-").append(requirementIndex.getJobTitle());
						 }else{
							 jobTitle = jobTitle.append(requirementIndex.getJobTitle());
						 }
						 
						 StringBuilder experience = new StringBuilder();
						 
						 if(requirementIndex.getMinExperience() !=  null && requirementIndex.getMaxExperience() != null){
							 experience = experience.append("(").append(requirementIndex.getMinExperience()).append("-").append(requirementIndex.getMaxExperience()).append(" Years").append(")");
						 }
						 else if(requirementIndex.getMinExperience() !=  null && requirementIndex.getMaxExperience() == null){
							 experience = experience.append("(").append(requirementIndex.getMinExperience()).append(" Years").append(")");
						 }
						 else if(requirementIndex.getMinExperience() ==  null && requirementIndex.getMaxExperience() != null){
							 experience = experience.append("(").append(requirementIndex.getMaxExperience()).append(" Years").append(")");
						 }
						 
						 StringBuilder location = new StringBuilder();
						 if(!isNullOrEmpty(requirementIndex.getCity())){
							 location = location.append(requirementIndex.getCity());
						 }
						 
						 StringBuilder jobSkills = new StringBuilder();
						 String skills = null;
						 if(null != requirementIndex.getSkills()){
							 String[] skillsList = requirementIndex.getSkills();
							 for(String skill : skillsList){
								 jobSkills = jobSkills.append(skill).append(", ");
							 }
							 
							 jobSkills.deleteCharAt(jobSkills.length()-2) ;
							 skills = jobSkills.toString();
						 }else{
							 skills = "----";
						 }
						 
						 
						 tempHTML = tempHTML+"<tr>";
						 tempHTML = tempHTML+"<td style='width:100%;padding:0px 0px 10px 0px;text-align:left;border-bottom:1px solid #ddd; line-height: 18px;'>";
						 tempHTML = tempHTML+"<p style='Margin:0;font-size:14px;Margin-bottom:2px;color:#1976d2;padding:0;text-decoration:none;''>";
						 tempHTML = tempHTML+"<a style='text-decoration:none;' href='"+sourceleadUrl+"/"+"jobAlertRequirementSummary?requirementUUID="+requirementIndex.getRequirementUUID()+"'> "+jobTitle.toString()+"  "+experience.toString()+" </a>";
						 tempHTML = tempHTML+"</p>";
						 tempHTML = tempHTML+"<p style='Margin:0;font-size:13px;Margin-bottom:1px;padding:0;'>"+requirementIndex.getPostedOrg()+" </p>";
						 tempHTML = tempHTML+"<p style='Margin:0;font-size:13px;Margin-bottom:1px;color:#757575;padding:0;'> "+location.toString()+" </p>";
						 tempHTML = tempHTML+"<p style='Margin:0;Margin-bottom:0px;color:#757575;font-size:13px;padding:0;'>";
						 tempHTML = tempHTML+"<b>Key skills:</b> "+skills+" </p>";
						 tempHTML = tempHTML+"</td>";
						 tempHTML = tempHTML+"</tr>";
				 }
					 i++;
			 }
				
			 
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";;
			 tempHTML = tempHTML+"<tr style='background-color:#ffffff;min-height:32px;'>";
			 tempHTML = tempHTML+"<td style='padding-top:15px;padding-bottom:15px;padding-right:10px;padding-left:10px;'>";
			 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#ffffff;'>";
			 tempHTML = tempHTML+"<tbody>";
			 tempHTML = tempHTML+"<tr>";
			 tempHTML = tempHTML+"<td style='cursor:pointer;padding-bottom:10px;padding-left:0;vertical-align:top;'>";
			 
			 if(requirementList.size() > 10){
				 tempHTML = tempHTML+"<a href='"+sourceleadUrl+"/"+"viewAllJobAlerts?jobAlertUUID="+jobAlertUUID+"' style='color:#fff;text-decoration:none;font-size:15px!important;font-weight: 600; border: 1px solid #1565c0; padding: 5px 10px; background: #1e88e5;'>View all matching jobs</a> </td>";
			 }
			 
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	 }
	 
	 
	 
	 
	 public void mailToOffboardCandidate(List<CandidateOffBoarding> candidateOffBoardingList) throws Exception{
		 
		 User toUser = null;
		
		
		 Date previousDat = yesterday();
		 for(CandidateOffBoarding candidateOffBoarding: candidateOffBoardingList){
			 
			 
			 UserRole userRole=userRoleService.getUserByUserId(candidateOffBoarding.getUserId().getId());
			 
			 if(userRole.getReportingManager() != null && userRole.getReportingManager().getId() != null){
				 
				  toUser = userService.getUserById(userRole.getReportingManager().getId());
				 }else{
					  toUser = userService.getUserById(userRole.getOrgLocations().getUser().getId());
				 }
			  OrgLocations orgLocations = orgLocationService.getOrgLocations(candidateOffBoarding.getOrgLocationId().getId());
			
			 
			 
			 SimpleDateFormat dateFormat = new SimpleDateFormat(orgLocations.getDateFormat());
			 String previousDate = dateFormat.format(previousDat);
			 String candidateOffBorfingDate = dateFormat.format(candidateOffBoarding.getEndDate());
			if(previousDate.equals(candidateOffBorfingDate) && candidateOffBoarding.getOffboardSubmitted().equals("N")){
	       // SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
	        EmailMessage emailMessage = new EmailMessage();
	         emailMessage.setFirstName(toUser.getFirstName());
	         emailMessage.setLastName(toUser.getLastName());
	         emailMessage.setToEmail(toUser.getEmail());
	         emailMessage.setFromEmail(defaultFromEmailAddressForProd);
	         
	         String fromEmail = null;
			 EmailConfigurationFromMail confFromMail=null;
			 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocations.getId());
			 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
				 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
					 if( fromMail.getEmailModules() != null){
						String[] jobIds = fromMail.getEmailModules().split(",");
							for(int j=0; j<jobIds.length; j++) {
								if(Long.valueOf(jobIds[j]) == 19L ){
									confFromMail=fromMail;
								}
							}
					 }
				 } 
				 
				 if(confFromMail == null){
					 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
					 if(confFromMail != null){
						 fromEmail = confFromMail.getEmail();
					 }
				 }else{
					 fromEmail = confFromMail.getEmail();
				 }
			 }
			 
			 if(StringUtils.isNotBlank(fromEmail)){
				 emailMessage.setFromEmail(fromEmail);
			 }
	         
	         
	         emailMessage.setCreatedBy(toUser.getUserCode());
	         StringBuilder msgBuild = new StringBuilder();
	        // emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	         emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	        //setting the class filed values
	         Map<String, String> globalVarsMap = new HashMap<String, String>();
	        
	         globalVarsMap.put("loginUser", emailMessage.getFromName());

	         StringBuilder name = new StringBuilder();
	         if(candidateOffBoarding.getLastName() != null && candidateOffBoarding.getLastName() != "" && candidateOffBoarding.getLastName() != "undefined"){
	        	  globalVarsMap.put("candidateName", name.append(candidateOffBoarding.getUserId().getFirstName()).append(" ").append(candidateOffBoarding.getUserId().getLastName()).toString());
	        
	         }
	         else{
	        	 globalVarsMap.put("candidateName", candidateOffBoarding.getUserId().getFirstName());
	         }
	         
	         if(toUser.getLastName() != null && toUser.getLastName() != "" && toUser.getLastName() != "undefined"){
	        	  globalVarsMap.put("ManagerName", name.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());
	        
	         }
	         else{
	        	 globalVarsMap.put("ManagerName", toUser.getFirstName());
	         }
	         
	         globalVarsMap.put("companyName", orgLocations.getOrg().getOrgName());
	        
	         StringBuilder linkBuild = new StringBuilder();
	         globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	        
	                
	        
	        
	         //getting the current EmailScenario by EmailScenario unique name and entity type
	         EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFBOARDINGAPPLICANTDELAYPROCESS,Constants.ENTITY_TYPE_NOTIFICATION);

	        
	         //covert subject,body and signature
	        
	        /* String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
	         notificationScenario.setBody(notificationbody);
*/
	        
	        // OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();

	        
	        
	         //calling the sendEmail method of emailScenerioUtil
	         commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.OFFBOARDINGAPPLICANTDELAYPROCESS,new ArrayList<EmailAttachment>());
	         String actionLink="";
	     // emailScenerioUtil.sendNotification(actionLink, loginUser, candidateOffBoarding.getUserId(), orgLocations, notificationScenario);
			}
	      }
	    }
	
	 private Date yesterday() {
		    final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -1);
		    return cal.getTime();
		}
	 
	 /**
	  * cron runs every day at 9 AM to send notifications email  about holiday to all users
	  * @throws Exception
	  */
	@Scheduled(cron = "0 0 9 1/1 * ?")
	//@Scheduled(cron = "0 0/3 * 1/1 * ?") //testing related every 3minit  run the cron
	@Async
	public void holidayNotificationToUsers() throws Exception {
		DateTime zoned = new DateTime();	
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// to fire remainder one week before
		DateTime week = zoned.plusDays(1);
		String startDate = outputFormatter1.print(week);
		String startDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, startDate);
		Date holidayStartDate = ts.parse(startDateString);
		String endDate = outputFormatter.print(week);
		String endDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, endDate);
		Date holidayEndDate = ts.parse(endDateString);
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
		/*OrgLocations asd = orgLocationService.getOrgLocations(2754L);
		List<OrgLocations> orgLocationsList = new ArrayList<>();
		orgLocationsList.add(asd);*/
		/*
		 * OrgLocations orgLocations = orgLocationsList.get(1);..for single org
		 */
		List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByEmailScenarioName(Constants.HOLIDAY_REMAINDER);
		for (OrgLocations orgLocations : orgLocationsList) {
			if(emailReminderList.stream().filter(e -> e.getOrgLocationsId().getId() == orgLocations.getId()).collect(Collectors.toList()).size() == 0) {
			List<Holiday> holidayList = holidayService.getHolidaybyLocationIdAndFromdate(holidayStartDate, holidayEndDate, orgLocations.getId());
			String holidayNames = "";
			int i = 0;
			if (holidayList != null && !holidayList.isEmpty()) {
				/*for (Holiday holiday : holidayList) {
					if (i == holidayList.size() - 1)
						holidayNames += holiday.getHolidayName();
					else
						holidayNames += holiday.getHolidayName() + ",";
					i++;
				}*/
				Map<String,String> map = new HashMap<>();
				for (Holiday holiday : holidayList) {
					if (i == holidayList.size() - 1) {
						for(HolidayShift hshift : holiday.getHolidayShift())
							map.put(hshift.getShift().getShiftName(), holiday.getHolidayName());
					}else {
						for(HolidayShift hshift : holiday.getHolidayShift()) {
							if(map.containsKey(hshift.getShift().getShiftName())) {
								String name = map.get(hshift.getShift().getShiftName());
								StringBuilder sb = new StringBuilder();
								sb.append(name).append(",").append(holiday.getHolidayName());
								map.put(hshift.getShift().getShiftName(), sb.toString());
							}else {
								map.put(hshift.getShift().getShiftName(), holiday.getHolidayName());
							}
						}
					}
						
					i++;
				}
				
				List<UserRole> userList = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
				for (UserRole uRole : userList) {
					User user = uRole.getUser();
					if(uRole.getShift() != null && map.containsKey(uRole.getShift().getShiftName())) {
						EmailMessage emailMessage = new EmailMessage();
						emailMessage.setFirstName(user.getFirstName());
						if (StringUtils.isNotBlank(user.getLastName()))
							emailMessage.setLastName(user.getLastName());
						emailMessage.setToEmail(user.getEmail());
						emailMessage.setFromEmail("defaultFromEmailAddressForProd");
						
						String fromEmail = null;
						 EmailConfigurationFromMail confFromMail=null;
						 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocations.getId());
						 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
							 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
								 if( fromMail.getEmailModules() != null){
									String[] jobIds = fromMail.getEmailModules().split(",");
										for(int j=0; j<jobIds.length; j++) {
											if(Long.valueOf(jobIds[j]) == 31L ){
												confFromMail=fromMail;
											}
										}
								 }
							 } 
							 
							 if(confFromMail == null){
								 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
								 if(confFromMail != null){
									 fromEmail = confFromMail.getEmail();
								 }
							 }else{
								 fromEmail = confFromMail.getEmail();
							 }
						 }
						 
						 if(StringUtils.isNotBlank(fromEmail)){
							 emailMessage.setFromEmail(fromEmail);
						 }
						  	// for URL redirections from mail
						 String redirectURL = "/leaveManagementReports";
						 Long redirectOrgLocationId = orgLocations.getId(); 	 
						 
						StringBuilder msgBuild = new StringBuilder();
						emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
						// setting the class filed values
						Map<String, String> globalVarsMap = new HashMap<String, String>();
						globalVarsMap.put("loginUser", emailMessage.getFromName());
						StringBuilder name = new StringBuilder();
						if (user.getLastName() != null && user.getLastName() != "" && user.getLastName() != "undefined") {
							globalVarsMap.put("UserName",
									name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
						} else {
							globalVarsMap.put("UserName", user.getFirstName());
						}
						globalVarsMap.put("companyName", orgLocations.getOrg().getOrgName());
						globalVarsMap.put("date",
								commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), holidayEndDate));
						globalVarsMap.put("holidayname", map.get(uRole.getShift().getShiftName()));
						InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
						
						EmailScenario  emailScenario = null;
						 if(orgLocations != null)
						 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.HOLIDAY_REMAINDER,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
						  if(emailScenario == null){
								emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.HOLIDAY_REMAINDER,Constants.ENTITY_TYPE_EMAIL);
						   }
						
						//configured Email
						String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
						 if(StringUtils.isNotBlank(fromMail)){
							 emailMessage.setFromEmail(fromMail);
						 } 
						 
						StringBuilder loginBuilder = new StringBuilder();
						globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Holiday</div></a>").toString());
						
						
						commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.HOLIDAY_REMAINDER,new ArrayList<EmailAttachment>());
						EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
								Constants.HOLIDAY_REMAINDER_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
						String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
						notificationScenario.setBody(notificationBody);
						StringBuilder actionLinkBuilder = new StringBuilder();
						String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("leaveManagementReports").toString();
						emailScenerioUtil.sendNotification(actionLink, orgLocations.getOrg().getUser(), user, orgLocations,notificationScenario,new HashMap<String,String>());
					
					}
					
				}
			}
		}
	}}
	
	/**
	 * @author Pavan Jampana
	 * cron runs every day at 9:00 AM to remind the approver about pending leaves
	 * @throws Exception  
	 */
//	@Scheduled(cron = "0 * * * * *") // Scheduled for every minute
	@Scheduled(cron = "0 0 9 1/1 * ?")
//	@Scheduled(cron = "	0 0/1 * 1/1 * ?")
//	@Scheduled(cron = "0/30 * * * * *")
	@Async
	public void appliedLeaveRemainder() throws Exception
	{
		
		try {
			
			List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
			
			List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByEmailScenarioName(Constants.APPROVE_PENDING_LEAVES);
				
			for(OrgLocations orgLocation : orgLocationsList){
				
				if(emailReminderList.stream().filter(e -> e.getOrgLocationsId().getId() == orgLocation.getId()).collect(Collectors.toList()).size() == 0) {
					
				List<LeaveApproveHierarchy> lahList = leaveService.getAllLeaveApproversList(orgLocation.getId());
				
				Collection<LeaveApproveHierarchy> filteredLahList = lahList.stream().<Map<Long, LeaveApproveHierarchy>> collect(HashMap::new,(m,e)->m.put(e.getApproverUserId().getId(), e), Map::putAll).values();
				
				for (LeaveApproveHierarchy lah : filteredLahList) {
					if(Constants.LEAVE_PENDING.equalsIgnoreCase(lah.getLeaveid().getStatus())){
						DateTime zoned = new DateTime(lah.getLeaveid().getCreatedDate());
						DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
						DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
						DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						DateTime week = zoned.plusDays(3);
						String startDate = outputFormatter1.print(week);
						String startDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, startDate);
						Date leaveStartDate = ts.parse(startDateString);
						String endDate = outputFormatter.print(week);
						String endDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, Constants.UTC_TIMEZONE, endDate);
						Date leaveEndDate = ts.parse(endDateString);
						
						User user = lah.getApproverUserId();
						OrgLocations orgLocations = lah.getLeaveid().getOrgLocations();
						
						List<LeaveApproveHierarchy> noOfLeaveRequests = lahList.stream().filter(l->l.getApproverUserId().getId().longValue() == user.getId().longValue() && Constants.LEAVE_PENDING.equalsIgnoreCase(l.getLeaveid().getStatus())).collect(Collectors.toList());
						
						EmailMessage emailMessage = new EmailMessage();
						emailMessage.setFirstName(user.getFirstName());
						if (StringUtils.isNotBlank(user.getLastName()))
							emailMessage.setLastName(user.getLastName());
						emailMessage.setToEmail(user.getEmail());
						emailMessage.setFromEmail("defaultFromEmailAddressForProd");
						String fromEmail = null;
						EmailConfigurationFromMail confFromMail = null;
						List<EmailConfigurationFromMail> emailConfigMailList = emailScenarioconService.getConfFromMailList(orgLocations.getId());
						if (emailConfigMailList != null && !emailConfigMailList.isEmpty()) {
							for (EmailConfigurationFromMail fromMail : emailConfigMailList) {
								if (fromMail.getEmailModules() != null) {
									String[] jobIds = fromMail.getEmailModules().split(",");
									for (int j = 0; j < jobIds.length; j++) {
										if (Long.valueOf(jobIds[j]) == 31L) {
											confFromMail = fromMail;
										}
									}
								}
							}
							if (confFromMail == null) {
								confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
								if (confFromMail != null) {
									fromEmail = confFromMail.getEmail();
								}
							}
							else {
								fromEmail = confFromMail.getEmail();
							}
						}
						if (StringUtils.isNotBlank(fromEmail)) {
							emailMessage.setFromEmail(fromEmail);
						}
						StringBuilder msgBuild = new StringBuilder();
						emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
						// setting the class filed values
						Map<String, String> globalVarsMap = new HashMap<String, String>();
						StringBuilder name = new StringBuilder();
						if (user.getLastName() != null && user.getLastName() != ""
								&& user.getLastName() != "undefined") {
							globalVarsMap.put("approverName", name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
						}
						else {
							globalVarsMap.put("approverName", user.getFirstName());
						}
						globalVarsMap.put("orgName", orgLocations.getOrgLocationName());
						globalVarsMap.put("noOfLeaveRequests", String.valueOf(noOfLeaveRequests.size()));
						StringBuilder loginBuilder = new StringBuilder();
						globalVarsMap.put("loginUrl",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
						
						InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
						EmailScenario emailScenario = null;
						if (orgLocations != null)
							emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.APPLY_LEAVE_REMAINDER, Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
						if (emailScenario == null) {
							emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLY_LEAVE_REMAINDER, Constants.ENTITY_TYPE_EMAIL);
						}
						// configured Email
						String fromMail = commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
						if (StringUtils.isNotBlank(fromMail)) {
							emailMessage.setFromEmail(fromMail);
						}
						commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.APPLY_LEAVE_REMAINDER, new ArrayList<EmailAttachment>());
						
					}
				}
			}
			}
			
			sendProbationPeriodMail();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	
	
	@Async
	private void sendProbationPeriodMail() throws Exception {
		
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
		
		for (OrgLocations orgLocations : orgLocationsList) {
			
			List<User> resourceManagersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
			Calendar cal = Calendar.getInstance();
			if(orgLocations.getNoOfDays() != null && orgLocations.getNoOfDays() > 0) {
				cal.add(Calendar.DATE, orgLocations.getNoOfDays());
			}else {
				cal.add(Calendar.DATE, 2);
			}
			
			Date futureDate = cal.getTime();
			for (User user : resourceManagersList) {
				List<ProbationDTO> probationUsersList = userService.getAllProbationUsersListForResourceManager(user.getId(),futureDate,orgLocations.getId());
				if(probationUsersList != null && probationUsersList.size() > 0)
					mailForwardProbationaryAssociatesToResourceManager(probationUsersList,user,orgLocations);
			
			}
			
		}
		
		
	}

	private void mailForwardProbationaryAssociatesToResourceManager(List<ProbationDTO> probationUsersList, User toUser, OrgLocations orgLocations) {
		

		EmailMessage emailMessage = new EmailMessage();
		
			if(toUser.getFirstName()!=null){
				 emailMessage.setFirstName(toUser.getFirstName());
			}
			else{
				 emailMessage.setFirstName("");
			}
			if(toUser.getLastName()!=null){
				 emailMessage.setLastName(toUser.getLastName());
			}
			else{
				 emailMessage.setLastName("");
			}
			
			 emailMessage.setToEmail(toUser.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setCreatedBy("sladmin");
			 if(StringUtils.isNotBlank(toUser.getLastName())){
				 StringBuilder sb=new StringBuilder();
				 emailMessage.setFromName(sb.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());
			 }else{
				 emailMessage.setFromName(toUser.getFirstName());
			 }

			 
			 StringBuilder  associateTable  = buildProbationAssociatesTable(probationUsersList,toUser);
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("resourceManager", emailMessage.getFromName());
				 globalVarsMap.put("associateTable", associateTable.toString());
				 
			 //calling the sendEmail method of emailScenerioUtil
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.PROBATIONARY_ASSOCiATES_EMAIL,new ArrayList<EmailAttachment>());
		
	}

	private StringBuilder buildProbationAssociatesTable(List<ProbationDTO> probationUsersList, User toUser) {
		

		StringBuilder tempHTML = new StringBuilder();
        tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
        tempHTML.append("<tr>");
        tempHTML.append("<th> Associates Name </th>");
        tempHTML.append("<th> Date of Join </th>");
        tempHTML.append("<th> Role </th>");
        tempHTML.append("<th> Department </th>");
        tempHTML.append("<th> Probation Period </th>");
        tempHTML.append("</tr>");
       
    		for(ProbationDTO prob:probationUsersList){
    			 
    			tempHTML.append("<tr>");
		         
		         if(prob.getAssociateLastName() != null && !"".equalsIgnoreCase(prob.getAssociateLastName())){
	        		 tempHTML.append("<td style='text-align: center;'>").append(prob.getAssociateFirstName()).append(" ").append(prob.getAssociateLastName()).append("</td>");
	        	 }else{
	        		 tempHTML.append("<td style='text-align: center;'>").append(prob.getAssociateFirstName()).append("</td>");
	        	 }
		         
		         if(prob.getHireDate() != null){
	        		 tempHTML.append("<td style='text-align: center;'>").append(prob.getHireDate()).append("</td>");
	        	 }else{
	        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
	        	 }
		         if(prob.getAssociateRole() != null && !"".equalsIgnoreCase(prob.getAssociateRole())){
	        		 tempHTML.append("<td style='text-align: center;'>").append(prob.getAssociateRole()).append("</td>");
	        	 }else{
	        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
	        	 }
		         if(prob.getDepartmentName() != null && !"".equalsIgnoreCase(prob.getDepartmentName())){
	        		 tempHTML.append("<td style='text-align: center;'>").append(prob.getDepartmentName()).append("</td>");
	        	 }else{
	        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
	        	 }
		         
		         if(prob.getProbationPeriod() != null && !"".equalsIgnoreCase(prob.getProbationPeriod())){
	        		 tempHTML.append("<td style='text-align: center;'>").append(prob.getProbationPeriod()).append("</td>");
	        	 }else{
	        		 tempHTML.append("<td style='text-align: center;'>").append("--").append("</td>");
	        	 }
		         
			}
        tempHTML.append("</tr>");
	     tempHTML.append("</table> ");
	return tempHTML;
		
		
	}

	/**
	 * cron runs every day at 6:30 AM to get every day stats
	 * @throws Exception  
	 */
	@Scheduled(cron = "0 30 18 1/1 * ?")
	@Async
	public void getDailyCampaignStats() throws Exception
	{

		List<Campaign> campaignList = campaignService.getAllCampaigns();
		
		for(Campaign campaign : campaignList) {
			OrgLocations orgLocation = orgLocationService.getOrgLocations(campaign.getOrgLocation().getId());
			Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
			logger.info("Camapaign stats start date is : "+currentDate);
			String response = campaignUtil.readCampaignStatsByCategory(campaign,currentDate);
			if(StringUtils.isNotBlank(response)) {
				CampaignStats campaignStats  = null;
				JSONArray jsonArr = new JSONArray(response);
//				String campaignName = "";
				Date date = campaign.getCreatedDate();
				campaignStats = campaignService.getCampaignStatsByCampaignId(campaign.getCampaignCategory());
				if(campaignStats !=null) {
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject jsonObj = jsonArr.getJSONObject(i);
						JSONArray statJsonArr = jsonObj.getJSONArray("stats");
						for (int j = 0; j < statJsonArr.length(); j++) {
							JSONObject statsObj = statJsonArr.getJSONObject(j);
							JSONObject metricsObj = statsObj.getJSONObject("metrics");
//							if(campaignStats !=null) {
								campaignStats.setBlocks(String.valueOf(Integer.parseInt(campaignStats.getBlocks()) + Integer.parseInt(metricsObj.getString("blocks"))));
								campaignStats.setBounceDrops(String.valueOf( Integer.parseInt(campaignStats.getBounceDrops()) + Integer.parseInt(metricsObj.getString("bounce_drops"))));
								campaignStats.setBounces(String.valueOf(Integer.parseInt(campaignStats.getBounces()) + Integer.parseInt(metricsObj.getString("bounces"))));
								campaignStats.setClicks(String.valueOf(Integer.parseInt(campaignStats.getClicks()) + Integer.parseInt(metricsObj.getString("clicks"))));
								campaignStats.setDeferred(String.valueOf(Integer.parseInt(campaignStats.getDeferred()) + Integer.parseInt(metricsObj.getString("deferred"))));
								campaignStats.setDelivered(String.valueOf(Integer.parseInt(campaignStats.getDelivered()) + Integer.parseInt(metricsObj.getString("delivered"))));
								campaignStats.setInvalidEmails(String.valueOf( Integer.parseInt(campaignStats.getInvalidEmails()) + Integer.parseInt(metricsObj.getString("invalid_emails"))));
								campaignStats.setOpens(String.valueOf(Integer.parseInt(campaignStats.getOpens()) + Integer.parseInt(metricsObj.getString("opens"))));
								campaignStats.setProcessed(String.valueOf(Integer.parseInt(campaignStats.getProcessed()) + Integer.parseInt(metricsObj.getString("processed"))));
								campaignStats.setRequest(String.valueOf(Integer.parseInt(campaignStats.getRequest()) + Integer.parseInt(metricsObj.getString("requests"))));
								campaignStats.setSpamReportDrops(String.valueOf(Integer.parseInt(campaignStats.getSpamReportDrops()) + Integer.parseInt(metricsObj.getString("spam_report_drops"))));
								campaignStats.setSpamReports(String.valueOf(Integer.parseInt(campaignStats.getSpamReports()) + Integer.parseInt(metricsObj.getString("spam_reports"))));
								campaignStats.setUniqueClicks(String.valueOf(Integer.parseInt(campaignStats.getUniqueClicks()) + Integer.parseInt(metricsObj.getString("unique_clicks"))));
								campaignStats.setUniqueOpens(String.valueOf(Integer.parseInt(campaignStats.getUniqueOpens()) + Integer.parseInt(metricsObj.getString("unique_opens"))));
								campaignStats.setUnsubscribeDrops(String.valueOf(Integer.parseInt(campaignStats.getUnsubscribeDrops()) + Integer.parseInt(metricsObj.getString("unsubscribe_drops"))));
								campaignStats.setUnsubscribes(String.valueOf(Integer.parseInt(campaignStats.getUnsubscribes()) + Integer.parseInt(metricsObj.getString("unsubscribes"))));
						}
						
				}
				}else {
					  campaignStats = new CampaignStats();
					  for (int i = 0; i < jsonArr.length(); i++) {
							JSONObject jsonObj = jsonArr.getJSONObject(i);
							JSONArray statJsonArr = jsonObj.getJSONArray("stats");
							for (int j = 0; j < statJsonArr.length(); j++) {
								JSONObject statsObj = statJsonArr.getJSONObject(j);
								JSONObject metricsObj = statsObj.getJSONObject("metrics");
									    campaignStats = new CampaignStats();
									    campaignStats.setCampaignDate(date);
										campaignStats.setCategoryName(statsObj.getString("name"));
										campaignStats.setCampaignId(campaign.getCampaignId());
										campaignStats.setBlocks(metricsObj.getString("blocks"));
										campaignStats.setBounceDrops(metricsObj.getString("bounce_drops"));
										campaignStats.setBounces(metricsObj.getString("bounces"));
										campaignStats.setClicks(metricsObj.getString("clicks"));
										campaignStats.setDeferred(metricsObj.getString("deferred"));
										campaignStats.setDelivered(metricsObj.getString("delivered"));
										campaignStats.setInvalidEmails(metricsObj.getString("invalid_emails"));
										campaignStats.setOpens(metricsObj.getString("opens"));
										campaignStats.setProcessed(metricsObj.getString("processed"));
										campaignStats.setRequest(metricsObj.getString("requests"));
										campaignStats.setSpamReportDrops(metricsObj.getString("spam_report_drops"));
										campaignStats.setSpamReports(metricsObj.getString("spam_reports"));
										campaignStats.setUniqueClicks(metricsObj.getString("unique_clicks"));
										campaignStats.setUniqueOpens(metricsObj.getString("unique_opens"));
										campaignStats.setUnsubscribeDrops(metricsObj.getString("unsubscribe_drops"));
										campaignStats.setUnsubscribes(metricsObj.getString("unsubscribes"));
										campaignStats.setStatus(1);
										campaignStats.setCreatedDate(currentDate);
										campaignStats.setUpdatedDate(currentDate);
										campaignStats.setOrgLocation(orgLocation);
							}
							
					}
				}
			
				CampaignStats saveCampaignStats = campaignService.saveCampaignStats(campaignStats);
				if(saveCampaignStats !=null) {
					logger.info("campaig stats successfully read");
				}	
		}
		}
		
	}
	
	/**
	 * This method is used to update the leave Status from pending to approved for expired leaves 
	 * this cron will run at 04:00 AM (UTC)
	 * @throws Exception
	 */
	//@Scheduled(cron = "0 0 4 1/1 * ?")
	@Async
	public void updateLeaveStatus() throws Exception
	{
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
		for (OrgLocations orgLocation : orgLocationsList) {
			List<Leave> leaveList = leaveService.getAllLeavesByLocationIdAndStatus(orgLocation.getId(), Constants.PENDING);
			for (Leave leave : leaveList) {
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String currentTimeStr = TZ_DF_TS.format(date);
				Date curDate = TZ_DF_TS.parse(currentTimeStr);
				if (leave.getToDate().before(curDate)) {
					leave.setStatus(Constants.APPROVED);
					leaveDao.saveLeave(leave);
				}
			}
		}
	}
	
	@Scheduled(cron = "	0 0 10 1/1 * ?")
	@Async
	public void contractSignedByReceivers() throws Exception
	{
		List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocations();
		Date date = new Date(); // Or where ever you get it from
		Date daysAgo = new DateTime(date).minusDays(4).toDate();
		for (OrgLocations orglocations : orgLocations) {
			List<Contracts> contractsList = contractsTemplateService.getContractsBylocationIdForReminder(orglocations.getId(), Constants.PENDING, date);
			for (Contracts contracts : contractsList) {
				if (contracts.getParentContractId() == null) {
					for (ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()) {
						sendcontractEmail(contracts, coh, orglocations);
					}
					sendcontractEmail(contracts, null,orglocations);
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void sendcontractEmail(Contracts contracts, ContractOwnerHierarchy coh, OrgLocations orglocations) throws Exception
	{
		User owner = null;
//		User receiver = null;
		if (coh != null) {
			owner = coh.getOwnerUserId();
//			receiver = contracts.getContactUser();
		}
		else {
			owner = contracts.getContactUser();
//			receiver = contracts.getContactUser();
		}
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(owner.getFirstName());
		if (owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())) {
			emailMessage.setLastName(owner.getLastName());
		}
		else {
			emailMessage.setLastName("");
		}
		emailMessage.setToEmail(owner.getEmail());
		emailMessage.setFromEmail(defaultFromEmailAddressForProd);
         
         String fromEmail = null;
		 EmailConfigurationFromMail confFromMail=null;
		 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orglocations.getId());
		 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
			 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
				 if( fromMail.getEmailModules() != null){
					String[] jobIds = fromMail.getEmailModules().split(",");
						for(int j=0; j<jobIds.length; j++) {
							if(Long.valueOf(jobIds[j]) == 16L ){
								confFromMail=fromMail;
							}
						}
				 }
			 } 
			 
			 if(confFromMail == null){
				 confFromMail = emailScenarioconService.getConfFromMailByStatus(orglocations.getId());
				 if(confFromMail != null){
					 fromEmail = confFromMail.getEmail();
				 }
			 }else{
				 fromEmail = confFromMail.getEmail();
			 }
		 }
		 
		 if(StringUtils.isNotBlank(fromEmail)){
			 emailMessage.setFromEmail(fromEmail);
		 }
		 
		emailMessage.setCreatedBy("sladmin");
		
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (contracts.getContractTitle() != null
				&& !"".equalsIgnoreCase(contracts.getContractTitle())) {
			globalVarsMap.put("contractTitle", contracts.getContractTitle());
		}
		else {
			globalVarsMap.put("contractTitle", "--");
		}
		
		String startTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String contractStartDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orglocations.getTimezone().getTimeZoneId(), startTimeStr);
		Date contractConverteDate = TZ_DF_TS.parse(contractStartDate);
		String contractDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),contractConverteDate);
		globalVarsMap.put("contractStartDate", contractDateInTimezone);
		if (contracts.getEndDate() != null) {
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			DateFormat TZ_DF_ETS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contractEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orglocations.getTimezone().getTimeZoneId(), currentTimeStr);
			Date contractConvertedDate = TZ_DF_ETS.parse(contractEndDate);
			String contractEndDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),contractConvertedDate);
			globalVarsMap.put("contractEndDate", contractEndDateInTimezone);
		}
		else {
			globalVarsMap.put("contractEndDate", "--");
		}
		
		StringBuilder ownerSb = new StringBuilder();
		for (ContractOwnerHierarchy cohh : contracts.getOwnerHierarchyList()) {
			if (StringUtils.isNotBlank(cohh.getOwnerUserId().getFirstName())
					&& StringUtils.isNotBlank(cohh.getOwnerUserId().getLastName())) {
				ownerSb.append(cohh.getOwnerUserId().getFirstName()).append(" ").append(cohh.getOwnerUserId().getLastName()).append(", ");
			}
			else {
				ownerSb.append(cohh.getOwnerUserId().getFirstName()).append(", ");
			}
		}
		globalVarsMap.put("OwnerName", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
		
		if(contracts.getContractType()!=null && !"".equalsIgnoreCase(contracts.getContractType())){
			globalVarsMap.put("contractType",contracts.getContractType());
			}else{
				globalVarsMap.put("contractType","--");
		}
		
		if (owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName())
				&& owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())) {
			StringBuilder ownersb = new StringBuilder();
			globalVarsMap.put("contractOwner", ownersb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
		}
		else {
			globalVarsMap.put("contractOwner", owner.getFirstName());
		}
		if (coh != null) {
			globalVarsMap.put("receiverOrgName", contracts.getContractOrgLocationId().getOrgLocationName());
		}
		else {
			globalVarsMap.put("receiverOrgName", contracts.getOrgLocationId().getOrgLocationName());
		  }
		 
		 if(StringUtils.isNoneBlank(contracts.getAutoGenId())){
		     globalVarsMap.put("contractId", contracts.getAutoGenId());
		 }
		 
		     globalVarsMap.put("noOfDays", "4");
		 
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		     
		   //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
			 if(contracts.getOrgLocationId() != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.REMINDER_CONTRACT_SIGNING_EMAIL_PENDING,Constants.ENTITY_TYPE_EMAIL, orglocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REMINDER_CONTRACT_SIGNING_EMAIL_PENDING,Constants.ENTITY_TYPE_EMAIL);
			   }
			 
		     
		     String fromMail=commonUtil.getConfiguredFromEmail(orglocations.getId(), emailScenario);
			 if(StringUtils.isNotBlank(fromMail)){
				 emailMessage.setFromEmail(fromMail);
			 }      
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.REMINDER_CONTRACT_SIGNING_EMAIL_PENDING, null);
		// EmailScenario emailScenario =
		// emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_EMAIL);
		/*
		 * EmailScenario notificationScenario =
		 * emailScenarioService.getEmailScenarioByUniqueName(Constants.
		 * CONTRACT_SIGNED,Constants.ENTITY_TYPE_NOTIFICATION);
		 * 
		 * String notificationbody =
		 * emailScenerioUtil.buildBody(notificationScenario.getBody(),
		 * globalVarsMap); notificationScenario.setBody(notificationbody);
		 */
		/*
		 * //covert subject,body and signature String subject =
		 * emailScenerioUtil.bulidSubject(emailScenario.getSubject(),
		 * globalVarsMap); String body =
		 * emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap
		 * ); String signature =
		 * emailScenerioUtil.buildSignature(emailScenario.getSignature(),
		 * globalVarsMap); String url = sourceleadUrl;
		 * 
		 * 
		 * //construct HTML template using required data String emailTemplate =
		 * emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage,
		 * subject, body, signature, url,
		 * contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
		 * emailMessage.setSubject(subject);
		 * emailMessage.setMessageBody(emailTemplate);
		 */
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"
				+ "contractType=" + contracts.getContractType() + "&status=verify").toString();
		// emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new
		// ArrayList<EmailAttachment>());
		// emailScenerioUtil.sendNotification(actionLink,receiver,owner,
		// contracts.getOrgLocationId(), notificationScenario,new
		// HashMap<String,String>());
	}
	
	@Scheduled(cron = "	0 0 10 1/1 * ?")
	@Async
	public void contractReminderForWaitingToVerify() throws Exception
	{
		List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocations();
		Date date = new Date(); // Or where ever you get it from
		Date daysAgo = new DateTime(date).minusDays(4).toDate();
		for (OrgLocations orglocations : orgLocations) {
			List<Contracts> contractsList = contractsTemplateService.getContractsBylocationIdForReminder(orglocations.getId(), Constants.WAITING_TO_BE_VERIFY, date);
			for (Contracts contracts : contractsList) {
				if (contracts.getParentContractId() == null) {
					for (ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()) {
						sendcontractWTVEmail(contracts, coh, orglocations);
					}
					sendcontractWTVEmail(contracts, null,orglocations);
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void sendcontractWTVEmail(Contracts contracts, ContractOwnerHierarchy coh, OrgLocations orglocations) throws Exception
	{
		User owner = null;
		// User receiver = null;
		if (coh != null) {
			owner = coh.getOwnerUserId();
			// receiver = contracts.getContactUser();
		}
		else {
			owner = contracts.getContactUser();
			// receiver = contracts.getContactUser();
		}
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(owner.getFirstName());
		if (owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())) {
			emailMessage.setLastName(owner.getLastName());
		}
		else {
			emailMessage.setLastName("");
		}
		emailMessage.setToEmail(owner.getEmail());
		emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		
		   String fromEmail = null;
			 EmailConfigurationFromMail confFromMail=null;
			 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orglocations.getId());
			 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
				 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
					 if( fromMail.getEmailModules() != null){
						String[] jobIds = fromMail.getEmailModules().split(",");
							for(int j=0; j<jobIds.length; j++) {
								if(Long.valueOf(jobIds[j]) == 16L ){
									confFromMail=fromMail;
								}
							}
					 }
				 } 
				 
				 if(confFromMail == null){
					 confFromMail = emailScenarioconService.getConfFromMailByStatus(orglocations.getId());
					 if(confFromMail != null){
						 fromEmail = confFromMail.getEmail();
					 }
				 }else{
					 fromEmail = confFromMail.getEmail();
				 }
			 }
			 
			 if(StringUtils.isNotBlank(fromEmail)){
				 emailMessage.setFromEmail(fromEmail);
			 }
			 
		emailMessage.setCreatedBy("sladmin");
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (contracts.getContractTitle() != null
				&& !"".equalsIgnoreCase(contracts.getContractTitle())) {
			globalVarsMap.put("contractTitle", contracts.getContractTitle());
		}
		else {
			globalVarsMap.put("contractTitle", "--");
		}
		String startTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String contractStartDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orglocations.getTimezone().getTimeZoneId(), startTimeStr);
		Date contractConverteDate = TZ_DF_TS.parse(contractStartDate);
		String contractDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(), contractConverteDate);
		globalVarsMap.put("contractStartDate", contractDateInTimezone);
		if (contracts.getEndDate() != null) {
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			DateFormat TZ_DF_ETS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contractEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orglocations.getTimezone().getTimeZoneId(), currentTimeStr);
			Date contractConvertedDate = TZ_DF_ETS.parse(contractEndDate);
			String contractEndDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(), contractConvertedDate);
			globalVarsMap.put("contractEndDate", contractEndDateInTimezone);
		}
		else {
			globalVarsMap.put("contractEndDate", "--");
		}
		StringBuilder ownerSb = new StringBuilder();
		for (ContractOwnerHierarchy cohh : contracts.getOwnerHierarchyList()) {
			if (StringUtils.isNotBlank(cohh.getOwnerUserId().getFirstName())
					&& StringUtils.isNotBlank(cohh.getOwnerUserId().getLastName())) {
				ownerSb.append(cohh.getOwnerUserId().getFirstName()).append(" ").append(cohh.getOwnerUserId().getLastName()).append(", ");
			}
			else {
				ownerSb.append(cohh.getOwnerUserId().getFirstName()).append(", ");
			}
		}
		globalVarsMap.put("OwnerName", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
		if (contracts.getContractType() != null
				&& !"".equalsIgnoreCase(contracts.getContractType())) {
			globalVarsMap.put("contractType", contracts.getContractType());
		}
		else {
			globalVarsMap.put("contractType", "--");
		}
		if (owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName())
				&& owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())) {
			StringBuilder ownersb = new StringBuilder();
			globalVarsMap.put("contractOwner", ownersb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
		}
		else {
			globalVarsMap.put("contractOwner", owner.getFirstName());
		}
		if (coh != null) {
			globalVarsMap.put("receiverOrgName", contracts.getContractOrgLocationId().getOrgLocationName());
		}
		else {
			globalVarsMap.put("receiverOrgName", contracts.getOrgLocationId().getOrgLocationName());
		}
		if (StringUtils.isNoneBlank(contracts.getAutoGenId())) {
			globalVarsMap.put("contractId", contracts.getAutoGenId());
		}
		globalVarsMap.put("noOfDays", "4");
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(contracts.getOrgLocationId() != null)
		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.REMINDER_CONTRACT_SIGNING_EMAIL_WAITING_TO_BE_VERIFY,Constants.ENTITY_TYPE_EMAIL, orglocations.getId());
		  if(emailScenario == null){
				emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REMINDER_CONTRACT_SIGNING_EMAIL_WAITING_TO_BE_VERIFY,Constants.ENTITY_TYPE_EMAIL);
		   }
		 
		
		String fromMail=commonUtil.getConfiguredFromEmail(orglocations.getId(), emailScenario);
		 if(StringUtils.isNotBlank(fromMail)){
			 emailMessage.setFromEmail(fromMail);
		 } 
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.REMINDER_CONTRACT_SIGNING_EMAIL_WAITING_TO_BE_VERIFY, new ArrayList<EmailAttachment>());
		// EmailScenario emailScenario =
		// emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_EMAIL);
		/*
		 * EmailScenario notificationScenario =
		 * emailScenarioService.getEmailScenarioByUniqueName(Constants.
		 * CONTRACT_SIGNED,Constants.ENTITY_TYPE_NOTIFICATION);
		 * 
		 * String notificationbody =
		 * emailScenerioUtil.buildBody(notificationScenario.getBody(),
		 * globalVarsMap); notificationScenario.setBody(notificationbody);
		 */
		/*
		 * //covert subject,body and signature String subject =
		 * emailScenerioUtil.bulidSubject(emailScenario.getSubject(),
		 * globalVarsMap); String body =
		 * emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap
		 * ); String signature =
		 * emailScenerioUtil.buildSignature(emailScenario.getSignature(),
		 * globalVarsMap); String url = sourceleadUrl;
		 * 
		 * 
		 * //construct HTML template using required data String emailTemplate =
		 * emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage,
		 * subject, body, signature, url,
		 * contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
		 * emailMessage.setSubject(subject);
		 * emailMessage.setMessageBody(emailTemplate);
		 */
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"
				+ "contractType=" + contracts.getContractType() + "&status=verify").toString();
		// emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new
		// ArrayList<EmailAttachment>());
		// emailScenerioUtil.sendNotification(actionLink,receiver,owner,
		// contracts.getOrgLocationId(), notificationScenario,new
		// HashMap<String,String>());
	}
	
	
	/**
	  * cron runs every day at 9 AM to send notifications email  about immigration template tasks selected users
	  * @throws Exception
	  */
	@Scheduled(cron = "0 0 9 1/1 * ?")
	@Async
	public void immigrationTemplateTaskReminder() throws Exception {
		List<Org> orgList = orgService.getOrgs();
		for (Org org : orgList) {
				List<OrgLocations> orgLocations = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				for (OrgLocations ol : orgLocations) {
					List<Immigration> immigrations = immigrationCommonService.getImmigrationsByOrgId(ol.getId());
					if(!immigrations.isEmpty()) {
						for(Immigration immigration : immigrations) {
							List<ImmigrationTemplateTasks> templateTasks = immigrationTemplateService.getImmigrationTemplateTasksByTempId(immigration.getImmigrationTemplateId().getId());
							if(!templateTasks.isEmpty()) {
								for(ImmigrationTemplateTasks tasks : templateTasks) {
									if(tasks.getTaskReminder() != null) {
										String[] tasksReminders = tasks.getTaskReminder().split(",");
								        if(tasksReminders.length != 0) {
								        	Calendar cal = Calendar.getInstance();
									        cal.setTime(tasks.getDueAfterDate());
									        for(String taskReminder: tasksReminders) {
									        	if(taskReminder.equalsIgnoreCase("1-days")) {
									        		cal.add(Calendar.DATE, 1);
									        		Date dueAfterDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		cal.setTime(new Date());
									        		Date currentDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		if(dueAfterDateInTimezone.equals(currentDateInTimezone)) {
									        			if(tasks.getAssignToUsers() != null) {
									        				String[] users = tasks.getAssignToUsers().split(",");
									        				for(String user: users) {
									        					User toUserObj = userService.getUserById(Long.parseLong(user));
									        					User fromUserObj = immigration.getCreatedBy();
									        					sendEmailToTaskReminderUser(toUserObj, fromUserObj, immigration, ol);
									        					sendNotificationToTaskReminderUser(toUserObj,immigration, ol);
									        				}
									        			}else if(tasks.getAssignToCandidate().equals("Y")) {
									        				Candidate toCandidateObj = immigration.getCandidate();
									        				User fromUserObj = immigration.getCreatedBy();
								        					sendEmailToTaskReminderCandidate(toCandidateObj, fromUserObj, immigration, ol);
								        					sendNotificationToTaskReminderCandidate(toCandidateObj,immigration, ol);
									        			}
									        		}
									        		
									        	}else if(taskReminder.equalsIgnoreCase("1-Week")) {
									        		cal.add(Calendar.DATE, 7);
									        		Date dueAfterDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		cal.setTime(new Date());
									        		Date currentDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		if(dueAfterDateInTimezone.equals(currentDateInTimezone)) {
									        			if(tasks.getAssignToUsers() != null) {
									        				String[] users = tasks.getAssignToUsers().split(",");
									        				for(String user: users) {
									        					User toUserObj = userService.getUserById(Long.parseLong(user));
									        					User fromUserObj = immigration.getCreatedBy();
									        					sendEmailToTaskReminderUser(toUserObj, fromUserObj, immigration, ol);
									        					sendNotificationToTaskReminderUser(toUserObj,immigration, ol);
									        				}
									        			}else if(tasks.getAssignToCandidate().equals("Y")) {
									        				Candidate toCandidateObj = immigration.getCandidate();
									        				User fromUserObj = immigration.getCreatedBy();
								        					sendEmailToTaskReminderCandidate(toCandidateObj, fromUserObj, immigration, ol);
								        					sendNotificationToTaskReminderCandidate(toCandidateObj,immigration, ol);
									        			}
									        		}
									        	}else if(taskReminder.equalsIgnoreCase("15-days")) {
									        		cal.add(Calendar.DATE, 15);
									        		Date dueAfterDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		cal.setTime(new Date());
									        		Date currentDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		if(dueAfterDateInTimezone.equals(currentDateInTimezone)) {
									        			if(tasks.getAssignToUsers() != null) {
									        				String[] users = tasks.getAssignToUsers().split(",");
									        				for(String user: users) {
									        					User toUserObj = userService.getUserById(Long.parseLong(user));
									        					User fromUserObj = immigration.getCreatedBy();
									        					sendEmailToTaskReminderUser(toUserObj, fromUserObj, immigration, ol);
									        					sendNotificationToTaskReminderUser(toUserObj,immigration, ol);
									        				}
									        			}else if(tasks.getAssignToCandidate().equals("Y")) {
									        				Candidate toCandidateObj = immigration.getCandidate();
									        				User fromUserObj = immigration.getCreatedBy();
								        					sendEmailToTaskReminderCandidate(toCandidateObj, fromUserObj, immigration, ol);
								        					sendNotificationToTaskReminderCandidate(toCandidateObj,immigration, ol);
									        			}
									        		}
									        	}else if(taskReminder.equalsIgnoreCase("1-Month")) {
									        		cal.add(Calendar.MONTH, 1);
									        		Date dueAfterDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		cal.setTime(new Date());
									        		Date currentDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		if(dueAfterDateInTimezone.equals(currentDateInTimezone)) {
									        			if(tasks.getAssignToUsers() != null) {
									        				String[] users = tasks.getAssignToUsers().split(",");
									        				for(String user: users) {
									        					User toUserObj = userService.getUserById(Long.parseLong(user));
									        					User fromUserObj = immigration.getCreatedBy();
									        					sendEmailToTaskReminderUser(toUserObj, fromUserObj, immigration, ol);
									        					sendNotificationToTaskReminderUser(toUserObj,immigration, ol);
									        				}
									        			}else if(tasks.getAssignToCandidate().equals("Y")) {
									        				Candidate toCandidateObj = immigration.getCandidate();
									        				User fromUserObj = immigration.getCreatedBy();
								        					sendEmailToTaskReminderCandidate(toCandidateObj, fromUserObj, immigration, ol);
								        					sendNotificationToTaskReminderCandidate(toCandidateObj,immigration, ol);
									        			}
									        		}
									        	}else if(taskReminder.equalsIgnoreCase("90-days")) {
									        		cal.add(Calendar.DATE, 90);
									        		Date dueAfterDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		cal.setTime(new Date());
									        		Date currentDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		if(dueAfterDateInTimezone.equals(currentDateInTimezone)) {
									        			if(tasks.getAssignToUsers() != null) {
									        				String[] users = tasks.getAssignToUsers().split(",");
									        				for(String user: users) {
									        					User toUserObj = userService.getUserById(Long.parseLong(user));
									        					User fromUserObj = immigration.getCreatedBy();
									        					sendEmailToTaskReminderUser(toUserObj, fromUserObj, immigration, ol);
									        					sendNotificationToTaskReminderUser(toUserObj,immigration, ol);
									        				}
									        			}else if(tasks.getAssignToCandidate().equals("Y")) {
									        				Candidate toCandidateObj = immigration.getCandidate();
									        				User fromUserObj = immigration.getCreatedBy();
								        					sendEmailToTaskReminderCandidate(toCandidateObj, fromUserObj, immigration, ol);
								        					sendNotificationToTaskReminderCandidate(toCandidateObj,immigration, ol);
									        			}
									        		}
									        	}else if(taskReminder.equalsIgnoreCase("180-days")) {
									        		cal.add(Calendar.DATE, 180);
									        		Date dueAfterDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		cal.setTime(new Date());
									        		Date currentDateInTimezone = getDateByTimeZone(cal.getTime(),ol);
									        		if(dueAfterDateInTimezone.equals(currentDateInTimezone)) {
									        			if(tasks.getAssignToUsers() != null) {
									        				String[] users = tasks.getAssignToUsers().split(",");
									        				for(String user: users) {
									        					User toUserObj = userService.getUserById(Long.parseLong(user));
									        					User fromUserObj = immigration.getCreatedBy();
									        					sendEmailToTaskReminderUser(toUserObj, fromUserObj, immigration, ol);
									        					sendNotificationToTaskReminderUser(toUserObj,immigration, ol);
									        				}
									        			}else if(tasks.getAssignToCandidate().equals("Y")) {
									        				Candidate toCandidateObj = immigration.getCandidate();
									        				User fromUserObj = immigration.getCreatedBy();
								        					sendEmailToTaskReminderCandidate(toCandidateObj, fromUserObj, immigration, ol);
								        					sendNotificationToTaskReminderCandidate(toCandidateObj,immigration, ol);
									        			}
									        		}
									        	}
									        }
								        }
									}
								}
							}
						}
					}
				}
		}
		
	}

	private void sendNotificationToTaskReminderCandidate(Candidate toCandidateObj, Immigration immigration,
			OrgLocations ol) throws Exception {

		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (immigration.getGeneratedId() != null && !"".equalsIgnoreCase(immigration.getGeneratedId())) {
			globalVarsMap.put("immigrationId", immigration.getGeneratedId());
		}
		if (immigration.getPetitionType().getName() != null
				&& !"".equalsIgnoreCase(immigration.getPetitionType().getName())) {
			globalVarsMap.put("petitionType", immigration.getPetitionType().getName());
		}
		
		/*EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.IMMIGRATION_NOTIFICATION_TO_BENEFICIARY, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("immigration/viewImmigration?immigrationUUID="+immigration.getImmigrationUuid()+"&candidateUUID="+immigration.getCandidate().getCandidateUUID()+"&immiTemplateUUID="+immigration.getImmigrationTemplateId().getUuid()+"&myImmigrationPage=Y").toString();
		emailScenerioUtil.sendNotification(actionLink, ol.getOrg().getUser(), toCandidateObj.getConsultantUser(), ol,notificationScenario,new HashMap<String,String>());*/
	}

	private void sendEmailToTaskReminderCandidate(Candidate toCandidateObj, User fromUserObj, Immigration immigration,
			OrgLocations ol) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(toCandidateObj.getFirstName());
		emailMessage.setLastName(toCandidateObj.getLastName());
		emailMessage.setToEmail(toCandidateObj.getEmail());
		emailMessage.setFromEmail(fromUserObj.getEmail());
		emailMessage.setCreatedBy(fromUserObj.getUserCode());
		
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (immigration.getGeneratedId() != null && !"".equalsIgnoreCase(immigration.getGeneratedId())) {
			globalVarsMap.put("immigrationId", immigration.getGeneratedId());
		}
		if (immigration.getPetitionType().getName() != null
				&& !"".equalsIgnoreCase(immigration.getPetitionType().getName())) {
			globalVarsMap.put("petitionType", immigration.getPetitionType().getName());
		}
		if (fromUserObj.getFullName() != null && !"".equalsIgnoreCase(fromUserObj.getFullName())) {
			globalVarsMap.put("immigrationManagerName", fromUserObj.getFullName());
		}
		if (fromUserObj.getEmail() != null && !"".equalsIgnoreCase(fromUserObj.getEmail())) {
			globalVarsMap.put("immigrationManagerEmail", fromUserObj.getEmail());
		}
		if (toCandidateObj.getFullName() != null
				&& !"".equalsIgnoreCase(toCandidateObj.getFullName())) {
			globalVarsMap.put("beneficiaryName", toCandidateObj.getFullName());
		}
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("immigrationLoginUrl", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());

		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, ol.getOrg(), ol,
				Constants.EMAIL_TO_IMMIGRATION_BENEFICIARY, new ArrayList<EmailAttachment>());
		
	}

	private void sendNotificationToTaskReminderUser(User toUserObj, Immigration immigration, OrgLocations orgLocations) throws Exception {
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (immigration.getGeneratedId() != null && !"".equalsIgnoreCase(immigration.getGeneratedId())) {
			globalVarsMap.put("immigrationId", immigration.getGeneratedId());
		}
		if (immigration.getPetitionType().getName() != null
				&& !"".equalsIgnoreCase(immigration.getPetitionType().getName())) {
			globalVarsMap.put("petitionType", immigration.getPetitionType().getName());
		}
		
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.IMMIGRATION_NOTIFICATION_TO_BENEFICIARY, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("immigration/viewImmigration?immigrationUUID="+immigration.getImmigrationUuid()+"&candidateUUID="+immigration.getCandidate().getCandidateUUID()+"&immiTemplateUUID="+immigration.getImmigrationTemplateId().getUuid()+"&myImmigrationPage=N").toString();
		emailScenerioUtil.sendNotification(actionLink, orgLocations.getOrg().getUser(), toUserObj, orgLocations,notificationScenario,new HashMap<String,String>());
		
	}

	private Date getDateByTimeZone(Date time, OrgLocations ol) throws ParseException {
		String startTimeStr = Constants.TZ_DF.format(time);
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, ol.getTimezone().getTimeZoneId(), startTimeStr);
		Date convertedDate = TZ_DF_TS.parse(date);
		Date dateInTimezone = commonUtil.getOrgLocationCurrentDate(ol.getDateFormat(),convertedDate);
		return dateInTimezone;
	}

	private void sendEmailToTaskReminderUser(User toUserObj, User fromUserObj, Immigration immigration, OrgLocations orgLocations) {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(toUserObj.getFirstName());
		emailMessage.setLastName(toUserObj.getLastName());
		emailMessage.setToEmail(toUserObj.getEmail());
		emailMessage.setFromEmail(fromUserObj.getEmail());
		emailMessage.setCreatedBy(fromUserObj.getUserCode());
		
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (immigration.getGeneratedId() != null && !"".equalsIgnoreCase(immigration.getGeneratedId())) {
			globalVarsMap.put("immigrationId", immigration.getGeneratedId());
		}
		if (immigration.getPetitionType().getName() != null
				&& !"".equalsIgnoreCase(immigration.getPetitionType().getName())) {
			globalVarsMap.put("petitionType", immigration.getPetitionType().getName());
		}
		if (fromUserObj.getFullName() != null && !"".equalsIgnoreCase(fromUserObj.getFullName())) {
			globalVarsMap.put("immigrationManagerName", fromUserObj.getFullName());
		}
		if (fromUserObj.getEmail() != null && !"".equalsIgnoreCase(fromUserObj.getEmail())) {
			globalVarsMap.put("immigrationManagerEmail", fromUserObj.getEmail());
		}
		if (toUserObj.getFullName() != null
				&& !"".equalsIgnoreCase(toUserObj.getFullName())) {
			globalVarsMap.put("beneficiaryName", toUserObj.getFullName());
		}
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("immigrationLoginUrl", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());

		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations,
				Constants.EMAIL_TO_IMMIGRATION_BENEFICIARY, new ArrayList<EmailAttachment>());
	}
	
	/**
	 * Calculating Monthly Leaves
	 * @throws Exception
	 */
	@Scheduled(cron = "0 3 0 1 1/1 ?")
	@Async
	public void calculateMonthlyLeaves() throws Exception 
	{
		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.MONTHLY.equalsIgnoreCase(leaveType.getPeriod())){
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					c.set(Calendar.DATE, 1);
					Date startDate = c.getTime();
					c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
					Date endDate = c.getTime();
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,startDate,endDate);
				}
			}
		}
	}
	
	/**
	 * calculate Quarterly Leaves
	 * @throws Exception
	 */
	@Scheduled(cron = "0 3 0 1 1/3 ?")
	@Async
	public void calculateQuarterlyLeaves() throws Exception
	{
		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.QUARTERLY.equalsIgnoreCase(leaveType.getPeriod())){
					LocalDate today = LocalDate.now();
					LocalDate[] dates = conversionUtil.getThisMonthQuarterDates(today.minusDays(1));
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,dates[0].toDate(),dates[1].toDate());
				}
			}
		}
	}
	
	/**
	 * calculate Half Yearly Leaves
	 * @throws Exception
	 */
	@Scheduled(cron = "0 3 0 1 1/6 ?")
	@Async
	public void calculateHalfYearlyLeaves() throws Exception
	{
		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.HALFYEARLY.equalsIgnoreCase(leaveType.getPeriod())){
					LocalDate today = LocalDate.now();
					LocalDate[] dates = conversionUtil.getThisMonthHalfyearyDates(today.minusDays(1));
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,dates[0].toDate(),dates[1].toDate());
				}
			}
		}
	}
	
	/**
	 * calculate Yearly Leaves
	 * @throws Exception
	 */
	@Scheduled(cron = "0 3 0 1 1 ?")
//	@Scheduled(cron = "0 0/3 * 1/1 * ?")
	@Async
	public void calculateYearlyLeaves() throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(1890l);
//		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
//		for(OrgLocations orgLocation : orglocationsList){
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			for(LeaveType leaveType : leaveTypeList){
				if(Constants.YEARLY.equalsIgnoreCase(leaveType.getPeriod())){
					Date[] dates = ConversionUtil.getPreviousYearStartAndEndDate();
					int status = associateBalanceLeaveService.calculateAndUpdateAssociateBalanceLeaves(orgLocation,leaveType,dates[0],dates[1]);
				}
			}
//		}
	}
	/**
	 * 0 0 8 ? * MON-FRI  Every Day at 8 AM 
	 * //@Scheduled(cron = "0 0/1 * 1/1 * ?") Every 5 min for testing purpose
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 8 ? * MON-FRI")
	public void autoMaticPayrollRunning() throws Exception
	{
		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLocation : orglocationsList){
			ConfigurePayrollApprover configurePayroll = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
			if(configurePayroll != null && "YES".equalsIgnoreCase(configurePayroll.getAutomaticPayroll())) {
				List<UserGroup> groupList = userGroupService.getAllUserGroupsByLocationIdAndPayrollFrequency(orgLocation,configurePayroll.getAutoPayrollFrequency());
				for(UserGroup usrGroup : groupList) {
					UsPayrollSchedule uSPayrollSchedule = payrollScheduleService.findScheduleByFrequency(usrGroup.getPayFrequency(), orgLocation.getId(), "");
					if(uSPayrollSchedule != null) {
						List<PayrollScheduleCalenderDTO> scheduleList = payrollUtil.getPayScheduleWeeksByDate(uSPayrollSchedule,orgLocation);
						Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), new Date());//11-10-2018
						for(PayrollScheduleCalenderDTO scheduleDto : scheduleList) {
							Date nextPayRunDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), scheduleDto.getPayRunDate());
							if(nextPayRunDate.equals(currentDate)) {
								GenerateUSPayrollDTO generateUSPayrollDTO = new GenerateUSPayrollDTO();
								String dates[] = scheduleDto.getPayPeriod().split(",");
								Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
								Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
								generateUSPayrollDTO.setGroupId(usrGroup.getGroupUUID());
								generateUSPayrollDTO.setPayPeriod(scheduleDto.getPayPeriod());
								PaysheetFrequencyDTO existedData = generateUSPayrollService.getPayrollData(generateUSPayrollDTO,orgLocation,orgLocation.getUser());
								
								List<AssociateSalary> assSallist = associateSalaryService.getAssociateSalaryListByGroupId(orgLocation,usrGroup.getId(),"All");
								
//								UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
								
								if (assSallist != null && assSallist.size() >0) {
									PaysheetFrequency paySheetFreq = null;
									if(existedData != null) {
										 paySheetFreq = new PaysheetFrequency();
										 paySheetFreq.setId(existedData.getPaySheetFrequencyId());
									}else {
										 paySheetFreq = new PaysheetFrequency();
									}
									
									paySheetFreq.setUserGroup(usrGroup);
									paySheetFreq.setOrgLocation(orgLocation);
									String[] payPeriod = scheduleDto.getPayPeriod().split(",");
									Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[0]);
									paySheetFreq.setFrequencyStartDate(startDate);
									Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[1]);
									paySheetFreq.setFrequencyEndDate(endDate);
									paySheetFreq.setPayCheckDate(uSPayrollSchedule.getPayCheckDate());
									paySheetFreq.setCreatedDate(new Date());
									paySheetFreq.setUpdatedDate(new Date());
									paySheetFreq.setCreatedBy(orgLocation.getUser().getId());
									paySheetFreq.setUpdatedBy(orgLocation.getUser().getId());
									paySheetFreq.setStatus(1);
									paySheetFreq.setType(PayrollConstant.PAYROLL);
									paySheetFreq.setApproverStatus(PayrollConstant.DRAFT);
									paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
									Set<EmployeePayroll> payrollList = generateUSPayrollService.calculatePayroll(existedData,paySheetFreq, orgLocation.getUser(), orgLocation,assSallist,freqStartDate, freqEndDate,usrGroup);
									if(payrollList !=null && payrollList.size() > 0) {
										paySheetFreq.setEmployeepayroll(payrollList);
										uSPaysheetService.addPaysheetFrequency(paySheetFreq);
									}
								}
							}
						}
					}	
					}
					
 			}
		}
		saveCronTracker("Payroll","autoMaticPayrollRunning","0 0 8 ? * MON-FRI","Every Day at 8:00 AM");
	}
	
	
	/**
	 * @author Laxman
	 * cron runs every day at 6:30 AM to get every day stats
	 * @throws Exception  
	 */
//	@Scheduled(cron = "0 0/5 * 1/1 * ?") this crone is testing for every 5 Minits
	@Scheduled(cron = "0 30 18 1/1 * ?")
	@Async
	public void assignAssetReturnOnDateBeforeDateStatusChanging() throws Exception
	{
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
		for (OrgLocations orgLocations : orgLocationsList) {
			List<Asset> assetList = assetService.getAssetListByLocationId(orgLocations.getId());
			if(!assetList.isEmpty()) {
				for(Asset asset: assetList) {
					List<AssetAssign> assetAssignList = assetService.getAllAssignAssetListByAssetId(asset.getId());
					if(!assetAssignList.isEmpty()) {
						for(AssetAssign aa :assetAssignList) {
							if(aa.getReturnedOn() != null) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								 Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
								 if(aa.getReturnedOn().before(dateWithoutTime)) {
									 aa.setStatus(Constants.RECORD_INACTIVE);
									 assetService.saveAssetAssign(aa);
								 }
							 }
						}	
					}
				}	
			}
		
		}
	}
	
	//0 0 5 1/1 * ?  Every Day at 5 AM 
	@Scheduled(cron = "0 0 5 1/1 * ?")
	public void sendUSPayrollScheduleEmail() throws Exception
	{
		List<OrgLocations> orglocationsList = orgLocationService.getAllOrgLocations();
		for(OrgLocations orgLocation : orglocationsList){
			ConfigurePayrollApprover configurePayroll = configurePayrollApproverService.getConfigurePayrollApproverByLoc(orgLocation);
			if(configurePayroll != null && "YES".equalsIgnoreCase(configurePayroll.getAutomaticPayroll())) {
				List<UsPayrollSchedule> scheduleList = payrollScheduleService.getAllPayrollScheduleByOrgLocationId(orgLocation.getId());
				if(scheduleList != null) {
					for(UsPayrollSchedule usPayrollSchedule : scheduleList) {
						int beforeDay=0;
						try {
							beforeDay = Integer.parseInt(usPayrollSchedule.getNotifyMember());
							if(usPayrollSchedule.getNotifyMember() != null && beforeDay > 0){
								List<PayrollScheduleCalenderDTO> scheduleDates = payrollUtil.getPayScheduleWeeksByDate(usPayrollSchedule,orgLocation);
								Set<PayrollApprover> NotifyApprover= configurePayroll.getPayrollApprover();
								for (PayrollApprover NotifyApproverObj : NotifyApprover) {
									User approver = NotifyApproverObj.getUser();
									for(PayrollScheduleCalenderDTO scheduleCalDTO : scheduleDates) {
										 EmailMessage emailMessage = new EmailMessage();
										 emailMessage.setFirstName(approver.getFirstName());
										 emailMessage.setLastName(approver.getLastName());
										 emailMessage.setToEmail(approver.getEmail());
										 emailMessage.setFromEmail("donotreply@sourcelead.com");
										 emailMessage.setFromName("Sourcelead Team");
										 emailMessage.setEmailType(Constants.US_PAYROLL_SCHEDULE_EMAIL);
										 emailMessage.setCreatedBy(orgLocation.getUser().getUserCode());
										 emailMessage.setOrgLocationId(orgLocation.getUser().getId());
										//setting the class filed values
										 
										String payFrequency = "";
										String payPeriod = "";
										if("Weekly".equalsIgnoreCase(usPayrollSchedule.getPayFrequency())) {
											payFrequency = usPayrollSchedule.getPayFrequency();
											payPeriod = scheduleCalDTO.getPayFrequency();
										}
										if("Bi-Weekly".equalsIgnoreCase(usPayrollSchedule.getPayFrequency())) {
											payFrequency = usPayrollSchedule.getPayFrequency();
											payPeriod = scheduleCalDTO.getPayFrequency();
										}
										if("Monthly".equalsIgnoreCase(usPayrollSchedule.getPayFrequency())) {
											payFrequency = usPayrollSchedule.getPayFrequency();
											payPeriod = scheduleCalDTO.getPayPeriod();
										}
										 Map<String, String> globalVarsMap = new HashMap<String, String>();
										 StringBuilder builder=new StringBuilder();
										 if(approver.getLastName() != null && approver.getLastName() !=""){
											 globalVarsMap.put("PayrollApproverName",builder.append(approver.getFirstName()).append(" ").append(approver.getLastName()).toString());	 
										 }else{
											 globalVarsMap.put("PayrollApproverName", approver.getFirstName());	 
										 }
										 globalVarsMap.put("PayPeriod",payPeriod);
										 globalVarsMap.put("RunPayrollDate",scheduleCalDTO.getPayRunDate());
										 globalVarsMap.put("PayFrequency",payFrequency);
										 globalVarsMap.put("PayDate",scheduleCalDTO.getPayCheckDate());
										 
										 List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();

										    Date payDate = new SimpleDateFormat(orgLocation.getDateFormat()).parse(scheduleCalDTO.getPayCheckDate());
								 		    Date runDate = new SimpleDateFormat(orgLocation.getDateFormat()).parse(scheduleCalDTO.getPayRunDate()); 
											Date rqurdDate = commonUtil.calculateBeforeDate(runDate,Integer.parseInt(usPayrollSchedule.getNotifyMember()));
											if(rqurdDate.after(payDate) && rqurdDate.before(runDate))
											{
													 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation, Constants.US_PAYROLL_SCHEDULE_EMAIL, attachmentList);
											}	
										 }
									}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
 			}
		}
		saveCronTracker("Payroll","sendUSPayrollScheduleEmail","0 0 5 1/1 * ?","Every Day at 5 AM");
	}	
	
	/**
	 * @author rajat rana
	 * 
	 * Tracking cron method like when it called by the server
	 * 
	 * @param cronModule
	 * @param methodName
	 * @param cronExpression
	 * @param cronType
	 * @throws Exception
	 */
	
	private void saveCronTracker(String cronModule,String methodName,String cronExpression,String cronType) throws Exception {
		CronTracker ct = new CronTracker();
		ct.setModuleName(cronModule);
		ct.setMethodName(methodName);
		ct.setCronExpression(cronExpression);
		ct.setCrontype(cronType);
		ct.setCronRunDateTime(new Date());
		cronTrackerDao.saveCronTracker(ct);
		
	}
	
	
	/*@Scheduled(cron = "0 0 5 1/1 * ?")    cron method for the job reminder according to the aplicant job joining date */
	public void applicant_Join_Date_to_reminder() throws Exception{
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Scheduled(cron = "0 0 0/1 1/1 * ?") // Scheduled for every one hour
//	@Scheduled(cron = "0 * * * * *") // Scheduled for every minute
	@Async
	public void runJobSchedulerEmailReminderAtOrgLevel() {
		try {
			
			// Applicant Joining remainder
			List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByEmailScenarioName(Constants.APPLICANT_JOINING);
			for(EmailReminder emailReminder : emailReminderList) {
				if(emailReminder.getEmailReminderHierarchyList().size() > 0) {
					for(EmailReminderHierarchy emailReminderHierarchy : emailReminder.getEmailReminderHierarchyList()) {
						Date convertedOrgDate = commonUtil.orgLocationCommonDate(new Date(), emailReminder.getOrgLocationsId());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(convertedOrgDate);
						int hrs = calendar.get(Calendar.HOUR_OF_DAY);
						int ampm = calendar.get(Calendar.AM_PM);
						if( (ampm == 0 && emailReminderHierarchy.getRemindertime().contains("AM") && hrs == Integer.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0])) || (ampm == 1 && emailReminderHierarchy.getRemindertime().contains("PM") && hrs == Integer.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0])+12) ) {
							calendar.add(Calendar.DATE, emailReminderHierarchy.getReminderdays().intValue()); // Adding remainderDays days
							SimpleDateFormat dateFormat = new SimpleDateFormat(emailReminder.getOrgLocationsId().getDateFormat());
					        String updatedDateStr     = dateFormat.format(calendar.getTime());
					        Date updatedDate = dateFormat.parse(updatedDateStr);
							
					        List<RequirementOffers> reqSubOfferList = requirementOffersService.getOfferAcceptedCandidatesByOrgLocationIdAndDateOfJoin(emailReminder.getOrgLocationsId().getId(), updatedDate);
							if(reqSubOfferList != null && reqSubOfferList.size() > 0) {
								
								//filter for unique requirement
								TreeSet<RequirementOffers> list = reqSubOfferList.stream().collect(Collectors.toCollection(
								          () -> new TreeSet<RequirementOffers>((p1, p2) -> p1.getSubmissionId().getRequirement().getId().compareTo(p2.getSubmissionId().getRequirement().getId())) ));
										
									for (RequirementOffers r : list) {
										StringBuffer applicantsInfoGrid = new StringBuffer();
										applicantsInfoGrid.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
										applicantsInfoGrid.append("<tbody>");
										applicantsInfoGrid.append("<tr>");
										applicantsInfoGrid.append("<th> Applicant Name </th>");
										applicantsInfoGrid.append("<th> Job Title </th>");
										applicantsInfoGrid.append("<th> Client </th>");
										applicantsInfoGrid.append("<th> Rate </th>");
										applicantsInfoGrid.append("<th> Join Date </th>");
										applicantsInfoGrid.append("</tr>");
										
										for(RequirementOffers reqSubOffer : reqSubOfferList) {
											if(reqSubOffer.getSubmissionId().getRequirement().getId() == r.getSubmissionId().getRequirement().getId()) {
												String clientName = "--";
												String rate = "--";
												String jobId = "";
												if(reqSubOffer.getSubmissionId().getRequirement().getClientId() != null){
													if(reqSubOffer.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")){
														clientName = reqSubOffer.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName();
													}
													if(reqSubOffer.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")){
														clientName = reqSubOffer.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName();
													}
												}
												if(reqSubOffer.getAmount() != null && reqSubOffer.getPaymentMode() != null){
													rate = reqSubOffer.getPaymentType() + " " + reqSubOffer.getAmount()+"/"+reqSubOffer.getPaymentMode();
												}
												if(reqSubOffer.getSubmissionId().getRequirement().getJobId() != null){
													jobId = reqSubOffer.getSubmissionId().getRequirement().getJobId() + " - ";
												}
												applicantsInfoGrid.append("<tr>");
												applicantsInfoGrid.append("<td>"+reqSubOffer.getSubmissionId().getCandidate().getFullName()+"</td>");
												applicantsInfoGrid.append("<td>"+jobId+reqSubOffer.getSubmissionId().getRequirement().getJobTitle()+"</td>");
												applicantsInfoGrid.append("<td>"+clientName+"</td>");
												applicantsInfoGrid.append("<td>"+rate+"</td>");
												applicantsInfoGrid.append("<td>"+updatedDateStr+"</td>");
												applicantsInfoGrid.append("</tr>");
											}
										}
										applicantsInfoGrid.append("</tbody>");
										applicantsInfoGrid.append("</table>");
										
										//loop for notify to emails
										for(String email : emailReminder.getNotifyTo().split(",")) {
											User toUser = new User();
											toUser.setEmail(email.trim());
											toUser.setFirstName("");
											toUser.setLastName("");
											applicationReminderEmailPreparationAndSend(toUser, emailReminder, emailReminderHierarchy, applicantsInfoGrid, reqSubOfferList, updatedDateStr);;
										}
										
										//mail to job manager
										applicationReminderEmailPreparationAndSend(r.getSubmissionId().getRequirement().getManagerUserId(), emailReminder, emailReminderHierarchy, applicantsInfoGrid, reqSubOfferList, updatedDateStr);
									}
							}
						}
					}
				}
			}
			
			logger.debug("******************  EmailReminder on org locations Completed **********************");
		} catch (Exception e) {
			logger.error("*****************Exception caught while EmailReminder shceduler *************"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private void applicationReminderEmailPreparationAndSend(User toUser, EmailReminder emailReminder, EmailReminderHierarchy emailReminderHierarchy, StringBuffer applicantsInfoGrid, List<RequirementOffers> reqSubOfferList, String updatedDateStr) {
		EmailMessage emailMessage = new EmailMessage();
		String jobOwner = "";
		if(StringUtils.isNotBlank(toUser.getFirstName())) {
			emailMessage.setFirstName(toUser.getFirstName());
			jobOwner = toUser.getFullName();
		}
		if(StringUtils.isNotBlank(toUser.getLastName())) {
			emailMessage.setLastName(toUser.getLastName());
		}
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail("info@sourcelead.com");
		emailMessage.setFromName("Sourcelead Team");
		emailMessage.setEmailType(Constants.APPLICANT_JOINING);
		emailMessage.setCreatedBy(emailReminder.getOrgLocationsId().getUser().getUserCode());
		emailMessage.setOrgLocationId(emailReminder.getOrgLocationsId().getId());
		
		//setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("noOfApplicants", String.valueOf(reqSubOfferList.size())+" applicants");
		globalVarsMap.put("joinDate",updatedDateStr);
		globalVarsMap.put("jobOwner",jobOwner);
		globalVarsMap.put("noOfDays",emailReminderHierarchy.getReminderdays()+" days");
		globalVarsMap.put("applicantsInfo",applicantsInfoGrid.toString());
		List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, emailReminder.getOrgLocationsId().getOrg(), emailReminder.getOrgLocationsId(), Constants.APPLICANT_JOINING, attachmentList);
		
		// Calling the saveCroneTracker method
		try {
			saveCronTracker("ApplicantJoiningReminder","applicationReminderEmailPreparationAndSend","0 0 0/1 1/1 * ?","Scheduled for every one hour");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@SuppressWarnings("deprecation")
	@Scheduled(cron = "0 0 0/1 1/1 * ?") // Scheduled for every one hour
    //@Scheduled(cron = "0 * * * * *") // Scheduled for every minute
	@Async
	public void runLeaveHolidaySchedulerEmailReminderAtOrgLevel() {
		try {

			// Applicant Holiday remainder
			List<EmailReminder> emailReminderList = emailReminderService
					.getEmailReminderListByEmailScenarioName(Constants.HOLIDAY_REMAINDER);
			for (EmailReminder emailReminder : emailReminderList) {
				if (emailReminder.getEmailReminderHierarchyList().size() > 0) {
					for (EmailReminderHierarchy emailReminderHierarchy : emailReminder
							.getEmailReminderHierarchyList()) {
						Date convertedOrgDate = commonUtil.orgLocationCommonDate(new Date(),
								emailReminder.getOrgLocationsId());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(convertedOrgDate);
						int hrs = calendar.get(Calendar.HOUR_OF_DAY);
						int ampm = calendar.get(Calendar.AM_PM);
						if ((ampm == 0 && emailReminderHierarchy.getRemindertime().contains("AM")
								&& hrs == Integer.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0]))
								|| (ampm == 1 && emailReminderHierarchy.getRemindertime().contains("PM")
										&& hrs == Integer.parseInt(
												emailReminderHierarchy.getRemindertime().split(":")[0]) + (hrs==12?0:12))) {
							calendar.add(Calendar.DATE, emailReminderHierarchy.getReminderdays().intValue()); // Adding
																												// remainderDays
																												// days
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
							String updatedDateStr = dateFormat.format(calendar.getTime());
							Date updatedDate = dateFormat.parse(updatedDateStr);
 
							DateTime zoned = new DateTime(updatedDate);
							DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
							DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
							DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

							String startDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
									Constants.UTC_TIMEZONE, updatedDateStr);
							Date holidayStartDate = ts.parse(startDateString);
							String endDate = outputFormatter.print(zoned);
							String endDateString = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
									Constants.UTC_TIMEZONE, endDate);
							Date holidayEndDate = ts.parse(endDateString);
							List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
							for (OrgLocations orgLocations : orgLocationsList) {
								List<Holiday> holidayList = holidayService.getHolidaybyLocationIdAndFromdate(
										holidayStartDate, holidayEndDate, orgLocations.getId());
								String holidayNames = "";
								int i = 0;
								if (holidayList != null && !holidayList.isEmpty()) {
									Map<String, String> map = new HashMap<>();
									for (Holiday holiday : holidayList) {
										if (i == holidayList.size() - 1) {
											for (HolidayShift hshift : holiday.getHolidayShift())
												map.put(hshift.getShift().getShiftName(), holiday.getHolidayName());
										} else {
											for (HolidayShift hshift : holiday.getHolidayShift()) {
												if (map.containsKey(hshift.getShift().getShiftName())) {
													String name = map.get(hshift.getShift().getShiftName());
													StringBuilder sb = new StringBuilder();
													sb.append(name).append(",").append(holiday.getHolidayName());
													map.put(hshift.getShift().getShiftName(), sb.toString());
												} else {
													map.put(hshift.getShift().getShiftName(), holiday.getHolidayName());
												}
											}
										}

										i++;
									}

									List<UserRole> userList = userRoleService
											.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
									for (UserRole uRole : userList) {
										User user = uRole.getUser();
										if (uRole.getShift() != null
												&& map.containsKey(uRole.getShift().getShiftName())) {
											EmailMessage emailMessage = new EmailMessage();
											emailMessage.setFirstName(user.getFirstName());
											if (StringUtils.isNotBlank(user.getLastName()))
												emailMessage.setLastName(user.getLastName());
											emailMessage.setToEmail(user.getEmail());
											emailMessage.setFromEmail("defaultFromEmailAddressForProd");

											String fromEmail = null;
											EmailConfigurationFromMail confFromMail = null;
											List<EmailConfigurationFromMail> emailConfigMailList = emailScenarioconService
													.getConfFromMailList(orgLocations.getId());
											if (emailConfigMailList != null && !emailConfigMailList.isEmpty()) {
												for (EmailConfigurationFromMail fromMail : emailConfigMailList) {
													if (fromMail.getEmailModules() != null) {
														String[] jobIds = fromMail.getEmailModules().split(",");
														for (int j = 0; j < jobIds.length; j++) {
															if (Long.valueOf(jobIds[j]) == 31L) {
																confFromMail = fromMail;
															}
														}
													}
												}

												if (confFromMail == null) {
													confFromMail = emailScenarioconService
															.getConfFromMailByStatus(orgLocations.getId());
													if (confFromMail != null) {
														fromEmail = confFromMail.getEmail();
													}
												} else {
													fromEmail = confFromMail.getEmail();
												}
											}

											if (StringUtils.isNotBlank(fromEmail)) {
												emailMessage.setFromEmail(fromEmail);
											}
											// for URL redirections from mail
											String redirectURL = "/leaveManagementReports";
											Long redirectOrgLocationId = orgLocations.getId();

											StringBuilder msgBuild = new StringBuilder();
											emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
											// setting the class filed values
											Map<String, String> globalVarsMap = new HashMap<String, String>();
											globalVarsMap.put("loginUser", emailMessage.getFromName());
											StringBuilder name = new StringBuilder();
											if (user.getLastName() != null && user.getLastName() != ""
													&& user.getLastName() != "undefined") {
												globalVarsMap.put("UserName", name.append(user.getFirstName())
														.append(" ").append(user.getLastName()).toString());
											} else {
												globalVarsMap.put("UserName", user.getFirstName());
											}
											globalVarsMap.put("companyName", orgLocations.getOrg().getOrgName());
											globalVarsMap.put("date", commonUtil.dateStrFromOrgDateFormatter(
													orgLocations.getDateFormat(), holidayEndDate));
											globalVarsMap.put("holidayname", map.get(uRole.getShift().getShiftName()));
											InternetAddress[] iAdressArray = InternetAddress
													.parse(emailMessage.getToEmail());

											EmailScenario emailScenario = null;
											if (orgLocations != null)
												emailScenario = emailScenarioService
														.getEmailScenarioByUniqueNameAndOrgLocationId(
																Constants.HOLIDAY_REMAINDER,
																Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
											if (emailScenario == null) {
												emailScenario = emailScenarioService.getEmailScenarioByUniqueName(
														Constants.HOLIDAY_REMAINDER, Constants.ENTITY_TYPE_EMAIL);
											}

											// configured Email
											String fromMail = commonUtil.getConfiguredFromEmail(orgLocations.getId(),
													emailScenario);
											if (StringUtils.isNotBlank(fromMail)) {
												emailMessage.setFromEmail(fromMail);
											}

											StringBuilder loginBuilder = new StringBuilder();
											globalVarsMap.put("loginURL", loginBuilder.append("<a href='")
													.append(sourceleadLoginUrl + "?redirectURL=" + redirectURL
															+ "&redirectOrgLocationId=" + redirectOrgLocationId)
													.append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Holiday</div></a>")
													.toString());

											commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,
													orgLocations.getOrg(), orgLocations, Constants.HOLIDAY_REMAINDER,
													new ArrayList<EmailAttachment>());
											EmailScenario notificationScenario = emailScenarioService
													.getEmailScenarioByUniqueName(
															Constants.HOLIDAY_REMAINDER_NOTIFICATION,
															Constants.ENTITY_TYPE_NOTIFICATION);
											String notificationBody = emailScenerioUtil.buildTemplateBody(
													notificationScenario.getBody(), globalVarsMap,
													notificationScenario);
											notificationScenario.setBody(notificationBody);
											StringBuilder actionLinkBuilder = new StringBuilder();
											String actionLink = actionLinkBuilder.append("").append(sourceleadUrl)
													.append("/").append("leaveManagementReports").toString();
											emailScenerioUtil.sendNotification(actionLink,
													orgLocations.getOrg().getUser(), user, orgLocations,
													notificationScenario, new HashMap<String, String>()); 
											// calling the saveCroneTracker method
											saveCronTracker("ApplicantHolidayRemainder","runLeaveHolidaySchedulerEmailReminderAtOrgLevel","0 0 0/1 1/1 * ?","Scheduled for every one hour");
										}

									}
								}
							}

						}

					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"*****************Exception caught while EmailReminder shceduler *************" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Scheduled(cron = "0 0 0/1 1/1 * ?") // Scheduled for every one hour
	//@Scheduled(cron = "0 * * * * *") // Scheduled for every minute
	@Async
	public void runApprovedAndPendingLeaveSchedulerEmailReminderAtOrgLevel() {
		try {
			
				runLeaveSchedulerEmailReminderAtOrgLevel(Constants.APPROVE_PENDING_LEAVES);
				runLeaveSchedulerEmailReminderAtOrgLevel(Constants.EMPLOYEE_ON_LEAVE);
			
			
			}
		catch(Exception e) {
		    logger.error(
                    "*****************Exception caught while EmailReminder shceduler *************" + e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	
	public void runLeaveSchedulerEmailReminderAtOrgLevel(String leaveStatus) {
		try {

			// Approved Leave remainder
			List<EmailReminder> emailReminderList = null;
			if(leaveStatus.equalsIgnoreCase(Constants.APPROVE_PENDING_LEAVES)) {
				emailReminderList = emailReminderService
						.getEmailReminderListByEmailScenarioName(Constants.APPROVE_PENDING_LEAVES);
			}else if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
				emailReminderList = emailReminderService
						.getEmailReminderListByEmailScenarioName(Constants.EMPLOYEE_ON_LEAVE);
			}
			
			for (EmailReminder emailReminder : emailReminderList) {
				if (emailReminder.getEmailReminderHierarchyList().size() > 0) {
					for (EmailReminderHierarchy emailReminderHierarchy : emailReminder
							.getEmailReminderHierarchyList()) {
						Date convertedOrgDate = commonUtil.orgLocationCommonDate(new Date(),
								emailReminder.getOrgLocationsId());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(convertedOrgDate);
						int hrs = calendar.get(Calendar.HOUR_OF_DAY);
						int ampm = calendar.get(Calendar.AM_PM);
						if ((ampm == 0 && emailReminderHierarchy.getRemindertime().contains("AM")
								&& hrs == Integer.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0]))
								|| (ampm == 1 && emailReminderHierarchy.getRemindertime().contains("PM")
										&& hrs == Integer
												.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0])
												+ (hrs == 12 ? 0 : 12))) {
							if(leaveStatus.equalsIgnoreCase(Constants.APPROVE_PENDING_LEAVES)) {
								calendar.add(Calendar.DATE, -emailReminderHierarchy.getReminderdays().intValue()); // remainderDays days
							}else if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
								calendar.add(Calendar.DATE, emailReminderHierarchy.getReminderdays().intValue()); // remainderDays days
							}
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String updatedDateStr = dateFormat.format(calendar.getTime());
							Date updatedDate = dateFormat.parse(updatedDateStr);


							List<Leave> userLeaveList = null;               // for getting the userleave list 
							List<ProjectTasks> projectTaskList = null;     //  for getting the projectTaskList
								
								if(leaveStatus.equalsIgnoreCase(Constants.APPROVE_PENDING_LEAVES)) {
									userLeaveList = leaveService
											.getAllLeavesByLocationIdAndStatusAndAppliedDate(
													emailReminder.getOrgLocationsId().getId(), "PENDING", updatedDate);
								}else if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
									userLeaveList = leaveService
											.getAllLeavesByLocationIdAndStatusAndLeaveStartDate(
													emailReminder.getOrgLocationsId().getId(), "APPROVED", updatedDate);
								}
								
								
								if (userLeaveList != null && userLeaveList.size() > 0) {
									for (Leave pendingLeave : userLeaveList) {
										
										projectTaskList = projectTasksService.getTasksListByLeaveType(pendingLeave.getLeaveType().getId());

										StringBuffer approvePendingInfoGrid = new StringBuffer();
										approvePendingInfoGrid.append(
												"<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
										approvePendingInfoGrid.append("<tbody>");
										approvePendingInfoGrid.append("<tr>");
										approvePendingInfoGrid.append("<th> Associate Name </th>");
										approvePendingInfoGrid.append("<th> Leave type </th>");
										approvePendingInfoGrid.append("<th> Duration </th>");
										approvePendingInfoGrid.append("<th> Applied On </th>");
										
										if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
											approvePendingInfoGrid.append("<th> No of days </th>");
											approvePendingInfoGrid.append("<th> Status</th>");
										}
										
										approvePendingInfoGrid.append("</tr>");

										String AssociateName = "";
										String LeaveType = "";
										String LeaveDuration = "";
										String leaveApplyDate = "";
										
										if (pendingLeave.getUser().getUserName() != null) {
											AssociateName = " " + pendingLeave.getUser().getFullName();
										}
										if (pendingLeave.getLeaveType() != null
												&& pendingLeave.getLeaveType().getName() != null) {
											LeaveType = pendingLeave.getLeaveType().getName();
										}
										if (" " + pendingLeave.getNumberOfDays() != null) {
											java.sql.Date sqlLeaveStartDate = new java.sql.Date(pendingLeave.getFromDate().getTime());
											java.sql.Date sqlLeaveEndDate = new java.sql.Date(pendingLeave.getToDate().getTime());
											LeaveDuration = " " + sqlLeaveStartDate + " to " + sqlLeaveEndDate;
										}
										if (" " + pendingLeave.getCreatedDate() != null) {
											java.sql.Date sqlappliedDate = new java.sql.Date(pendingLeave.getCreatedDate().getTime());
											leaveApplyDate = " " + sqlappliedDate;
										}

										approvePendingInfoGrid.append("<tr>");
										approvePendingInfoGrid.append("<td>" + AssociateName + "</td>");
										approvePendingInfoGrid.append("<td>" + LeaveType + "</td>");
										approvePendingInfoGrid.append("<td>" + LeaveDuration + "</td>");
										approvePendingInfoGrid.append("<td>" + leaveApplyDate + "</td>");
										
										if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
											String noOfDays = "";
											String lStatus = "";
											
											if (" " + pendingLeave.getNumberOfDays() != null) {
												noOfDays = " " + pendingLeave.getNumberOfDays();
											}
											if (" " + pendingLeave.getStatus() != null) {
												lStatus = " " + pendingLeave.getStatus();
											}
											
											
											approvePendingInfoGrid.append("<td>" + noOfDays + "</td>");
											approvePendingInfoGrid.append("<td>" + lStatus + "</td>");
										}
										approvePendingInfoGrid.append("</tr>");
										approvePendingInfoGrid.append("</tbody>");
										approvePendingInfoGrid.append("</table>");

									
									  // loop for notify to emails 
										
										if(leaveStatus.equalsIgnoreCase(Constants.APPROVE_PENDING_LEAVES)) {
											for(String email : emailReminder.getNotifyTo().split(",")) {
												User toUser = new User();
												toUser.setEmail(email.trim());
												toUser.setFirstName("");
												toUser.setLastName("");
												sendConfiguredMailTo(toUser, emailReminder, emailReminderHierarchy, approvePendingInfoGrid, pendingLeave, leaveApplyDate, userLeaveList.size(), leaveStatus); 
											}
											//project Manager
											 List<ProjectMembers> projectMemberList =  projectMembersService.getAllActiveProjectsByUserIdAndOrgLocationId(pendingLeave.getUser().getId(),pendingLeave.getOrgLocations().getId());
											for(ProjectMembers members :projectMemberList) {
												List<ProjectApproverHierarchy> projectApproverHierarchy = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(members.getProjects().getId(),members.getProjects().getOrgLocationsId().getId());
												for(ProjectApproverHierarchy pah:projectApproverHierarchy) {
													sendConfiguredMailTo(pah.getApproverUserId(), emailReminder, emailReminderHierarchy, approvePendingInfoGrid, pendingLeave, leaveApplyDate, userLeaveList.size(), leaveStatus);
												}
											}
										}
 
										else if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
											String[] roleIds = emailReminder.getRoles().split(",");
											
											for(String roleType :roleIds) {
												if(roleType.equalsIgnoreCase("Reporting Manager")) {
													//sending the mail to reporting manger
													for(LeaveApproveHierarchy lah : pendingLeave.getApproverHierarchyList()) {
													sendConfiguredMailTo(lah.getApproverUserId(), emailReminder, emailReminderHierarchy, approvePendingInfoGrid, pendingLeave, leaveApplyDate, userLeaveList.size(), leaveStatus);
													}
												} 
												else if(roleType.equalsIgnoreCase("Team Leader")) {
													//Team lead
													 List<TeamMembers>  teamMemberList = teamMembersService.getTeamMembersListByUserIdAndLocationId(pendingLeave.getUser().getId(),pendingLeave.getOrgLocations().getId());
													for(TeamMembers team:teamMemberList) {
														sendConfiguredMailTo(team.getTeamId().getOwnerUserId(), emailReminder, emailReminderHierarchy, approvePendingInfoGrid, pendingLeave, leaveApplyDate, userLeaveList.size(), leaveStatus);
													}
	
												}
												else if(roleType.equalsIgnoreCase("Project Manager")){
													//project Manager
													 List<ProjectMembers> projectMemberList =  projectMembersService.getAllActiveProjectsByUserIdAndOrgLocationId(pendingLeave.getUser().getId(),pendingLeave.getOrgLocations().getId());
													for(ProjectMembers members :projectMemberList) {
														List<ProjectApproverHierarchy> projectApproverHierarchy = projectApproverHierarchyService.getProjectsHierarchyListByProjectIdAndOrgLocationId(members.getProjects().getId(),members.getProjects().getOrgLocationsId().getId());
														for(ProjectApproverHierarchy pah:projectApproverHierarchy) {
															sendConfiguredMailTo(pah.getApproverUserId(), emailReminder, emailReminderHierarchy, approvePendingInfoGrid, pendingLeave, leaveApplyDate, userLeaveList.size(), leaveStatus);
														}
													}
												}
											}
										}
								}
						}
					}
				}
			}
			}
		} catch (Exception e) {
			logger.error(
					"*****************Exception caught while EmailReminder shceduler *************" + e.getMessage());
			e.printStackTrace();

		}

	}
	
	
	void sendConfiguredMailTo(User toUser, EmailReminder emailReminder, EmailReminderHierarchy emailReminderHierarchy, StringBuffer approvePendingInfoGrid, Leave pendingLeave, String leaveApplyDate, int userLeaveListSize, String leaveStatus){
		EmailMessage emailMessage = new EmailMessage();
		String Manager = "";
		if (StringUtils.isNotBlank(toUser.getFirstName())) {
			emailMessage.setFirstName(toUser.getFirstName());
			Manager = toUser.getFullName();
		}
		if (StringUtils.isNotBlank(toUser.getLastName())) {
			emailMessage.setLastName(toUser.getLastName());
		}
		
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail("info@sourcelead.com");
		emailMessage.setFromName("Sourcelead Team");
		emailMessage.setEmailType(Constants.APPROVE_PENDING_LEAVES);
		emailMessage.setCreatedBy(
				emailReminder.getOrgLocationsId().getUser().getUserCode());
		emailMessage.setOrgLocationId(emailReminder.getOrgLocationsId().getId());

		// setting the class filed values
		java.sql.Date sqlDateOfLeave = new java.sql.Date(pendingLeave.getFromDate().getTime());
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("Manager", Manager);
		globalVarsMap.put("ApplyLeaveDate", leaveApplyDate);
		globalVarsMap.put("NoOfApprovePendingLeaves",
				String.valueOf(userLeaveListSize));
		globalVarsMap.put("noOfDays",
				emailReminderHierarchy.getReminderdays() + " days");
		globalVarsMap.put("applicantsInfo", approvePendingInfoGrid.toString());
		globalVarsMap.put("DateOfLeave", sqlDateOfLeave.toString());

		// Setting the approveLeave
	  StringBuilder linkBuild = new StringBuilder();
	  globalVarsMap.put("approveLeaveLink", linkBuild.append("<a href='")
	  .append(sourceleadUrl).append("/").append("employeeRequestedLeaves")
	  .append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Approve Leave</div></a>"
	  ) .toString());
	 

		List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		if(leaveStatus.equalsIgnoreCase(Constants.APPROVE_PENDING_LEAVES)) {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,
					emailReminder.getOrgLocationsId().getOrg(),
					emailReminder.getOrgLocationsId(), Constants.APPROVE_PENDING_LEAVES,
					attachmentList);
		}
		
		if(leaveStatus.equalsIgnoreCase(Constants.EMPLOYEE_ON_LEAVE)) {
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, emailReminder.getOrgLocationsId().getOrg(), emailReminder.getOrgLocationsId(), Constants.EMPLOYEE_ON_LEAVE, attachmentList);
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@Scheduled(cron = "0 0 0/1 1/1 * ?") // Scheduled for every one hour
	//@Scheduled(cron = "0 * * * * *") // Scheduled for every minute
	@Async
	public void runContractExpireEmailReminderAtOrgLevel() {
		try {
			List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByEmailScenarioName(Constants.CONTRACT_EXPIRY_REMAINDER);
			for(EmailReminder emailReminder : emailReminderList) {
				if(emailReminder.getEmailReminderHierarchyList().size() > 0) {
					for(EmailReminderHierarchy emailReminderHierarchy : emailReminder.getEmailReminderHierarchyList()) {
						Date convertedOrgDate = commonUtil.orgLocationCommonDate(new Date(), emailReminder.getOrgLocationsId());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(convertedOrgDate);
						int hrs = calendar.get(Calendar.HOUR_OF_DAY);
						int ampm = calendar.get(Calendar.AM_PM);
						if( (ampm == 0 && emailReminderHierarchy.getRemindertime().contains("AM") && hrs == Integer.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0])) || (ampm == 1 && emailReminderHierarchy.getRemindertime().contains("PM") && hrs == Integer.parseInt(emailReminderHierarchy.getRemindertime().split(":")[0])+12) ) {
							calendar.add(Calendar.DATE, emailReminderHierarchy.getReminderdays().intValue()); // Adding remainderDays days
							SimpleDateFormat dateFormat = new SimpleDateFormat(emailReminder.getOrgLocationsId().getDateFormat());
					        String updatedDateStr = dateFormat.format(calendar.getTime());
					        Date updatedDate = dateFormat.parse(updatedDateStr);
					         
					        //method for the contractexpired cron 
					        
							List<Contracts> expiredContractsList = contractsTemplateService.getAllContractsBasedOnConfigureDate(updatedDate);
							if(expiredContractsList != null) {
								for(Contracts contracts : expiredContractsList){
								
									contractExpiryRemainderMail(contracts,updatedDateStr, emailReminder);
								}
							}
						}
					}
				}
			}
			
		}catch (Exception e) {
			logger.error(
					"*****************Exception caught while EmailReminder shceduler *************" + e.getMessage());
			e.printStackTrace();

		}
	}
	
	@SuppressWarnings("unused")
	private void contractExpiryRemainderMail(Contracts contracts, String days, EmailReminder emailReminder) throws Exception
	{
			User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
			OrgLocations orgLocations = orgLocationService.getOrgLocations(contracts.getOrgLocationId().getId());
			
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setFirstName(owner.getFirstName());
			 
			//Url for redirection 			 
		     String redirectURL = "/contractListbyStatus-params-"+"contractType="+contracts.getContractType()+"-and-status=waiting";
			 Long redirectOrgLocationId = orgLocations.getId();

			 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
				 
				 emailMessage.setLastName(owner.getLastName());
			 }else{
				 emailMessage.setLastName("");
			 }
			 emailMessage.setToEmail(owner.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setCreatedBy("sladmin");
			 emailMessage.setFromName(orgLocations.getOrgName()); 
			 			 
			 String fromEmail = null;
			 EmailConfigurationFromMail confFromMail=null;
			 List<EmailConfigurationFromMail> emailConfigMailList=emailScenarioconService.getConfFromMailList(orgLocations.getId());
			 if(emailConfigMailList != null && !emailConfigMailList.isEmpty()){
				 for(EmailConfigurationFromMail fromMail:emailConfigMailList){
					 if( fromMail.getEmailModules() != null){
						String[] jobIds = fromMail.getEmailModules().split(",");
							for(int j=0; j<jobIds.length; j++) {
								if(Long.valueOf(jobIds[j]) == 16L ){
									confFromMail=fromMail;
								}
							}
					 }
				 } 
				 
				 if(confFromMail == null){
					 confFromMail = emailScenarioconService.getConfFromMailByStatus(orgLocations.getId());
					 if(confFromMail != null){
						 fromEmail = confFromMail.getEmail();
					 }
				 }else{
					 fromEmail = confFromMail.getEmail();
				 }
			 }
			 
			 if(StringUtils.isNotBlank(fromEmail)){
				 emailMessage.setFromEmail(fromEmail);
			 }
	         			 
			 String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),contracts.getEndDate());
			 String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),contracts.getStartDate());
			 String dayCount = String.valueOf(days);
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 
			 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
				 globalVarsMap.put("contractTitle", contracts.getContractTitle());
				 }else{
					 globalVarsMap.put("contractTitle","--");
				 }
			 
			 if(contracts.getContractType()!= null && !"".equalsIgnoreCase(contracts.getContractType())){
				 globalVarsMap.put("contractType", contracts.getContractType());
				 }else{
					 globalVarsMap.put("contractType","--");
				 }
			
			 if(contracts.getEndDate()!= null && !"".equalsIgnoreCase(contracts.getEndDate().toString())){
				 globalVarsMap.put("contractEndDate", endDate);
				 }else{
					 globalVarsMap.put("contractEndDate","--");
				 }
			 
			 if(contracts.getStartDate()!= null && !"".equalsIgnoreCase(contracts.getStartDate().toString())){
				 globalVarsMap.put("contractStartDate", startDate);
				 }else{
					 globalVarsMap.put("contractStartDate","--");
				 }
			 
			 if(contracts.getTemplateSubmissionStatus() != null && !"".equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
				 globalVarsMap.put("status", contracts.getTemplateSubmissionStatus());
				 }else{
					 globalVarsMap.put("status","--");
				 }
			 
			 globalVarsMap.put("expiringdays",dayCount);
			 if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 
				 StringBuilder builder=new StringBuilder();
				 
				 globalVarsMap.put("clientContact",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
				 
			 }
			 else{
			 globalVarsMap.put("clientContact",contracts.getContactUser().getFirstName());
			 }
			 
			if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
				StringBuilder creatorSb = new StringBuilder();
				globalVarsMap.put("contractOwner", creatorSb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
			}else if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName())){
				globalVarsMap.put("contractOwner",owner.getFirstName());
			}else{
				globalVarsMap.put("contractOwner","--");
			}
			
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginUrl",loginBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append
                           ("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reminder</div></a>").toString());
			
			 if(contracts.getContactUser().getFirstName() != null && contracts.getContactUser().getLastName()!=null && !"".equalsIgnoreCase(contracts.getContactUser().getFirstName()) && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("receiverParty",userBuild.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
				 }else{
					 globalVarsMap.put("receiverParty",contracts.getContactUser().getFirstName());
				}
			 			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_EXPIRY_REMAINDER,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_EXPIRY_REMAINDER,Constants.ENTITY_TYPE_NOTIFICATION);

			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			 notificationScenario.setBody(notificationbody);
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
			 if(StringUtils.isNotBlank(fromMail)){
				 emailMessage.setFromEmail(fromMail);
			 } 
			 StringBuilder notificationLink = new StringBuilder();
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 String actionLink="";
			 actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=waiting").toString();
			 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 
			 for (String email : emailReminder.getNotifyTo().split(",")) {
					User toUser = new User();
					toUser.setEmail(email.trim());
					toUser.setFirstName("");
					toUser.setLastName("");
					String notify = "";
					if (StringUtils.isNotBlank(toUser.getFirstName())) {
						emailMessage.setFirstName(toUser.getFirstName());
						notify = toUser.getFullName();
					}
					if (StringUtils.isNotBlank(toUser.getLastName())) {
						emailMessage.setLastName(toUser.getLastName());
					}
					emailMessage.setToEmail(toUser.getEmail());
					emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				} 
//			 emailScenerioUtil.sendNotification(actionLink, null,owner, contracts.getOrgLocationId(), notificationScenario);
			 
			 // Calling the save crone tracker method
			 saveCronTracker("ContractReminderEmail","contractExpiryRemainderMail","0 0 0/1 1/1 * ?","Scheduled for every one hour");	
	}
	
}

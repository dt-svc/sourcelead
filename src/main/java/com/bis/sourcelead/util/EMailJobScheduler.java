package com.bis.sourcelead.util;

import java.text.DateFormat;
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
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dao.TimeZoneDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidatesHotListDummy;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.service.JobSchedulerTestService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
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
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;	
	
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
	OrgService orgService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
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
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
	
	
	
	//=============================================== EMPLOYEE TIME SHEETS MAILS STARTED==========================================
	
	/**
	 * @author Rajashekar & Prasad salina
	 * Fires on Every Firday at 11:45 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 45 23 ? * FRI ")
	public void runJobSchedulerByWeekly() {
		logger.info("******************  Notification Job Scheduler started..! **********");
		try {
			HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.WEEKLY);
			if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
				for(ProjectMembers projectMember : ProjectMembersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					if("Admin".equalsIgnoreCase(userRole.getUserType()))
						continue;
					    sendCronRemainderForProjectMember(projectMember,userRole,Constants.WEEKLY);
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
			HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.BI_WEEKLY);
			if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
				for(ProjectMembers projectMember : ProjectMembersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					if("Admin".equalsIgnoreCase(userRole.getUserType()))
						continue;
					    sendCronRemainderForProjectMember(projectMember,userRole,Constants.BI_WEEKLY);
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }

	/**
	 * @author Rajashekar & Prasad salina
	 * Fires on Every 15 (semi_monthly) days at 11:45 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 45 23 1/15 * ?")
	public void runJobSchedulerBySemi_Monthly() {
		logger.info("******************  Notification Job Scheduler Every 15 days in a month started..! **********");
		try {
				HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.SEMI_MONTHLY);
				if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
					for(ProjectMembers projectMember : ProjectMembersList){
						UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
						if("Admin".equalsIgnoreCase(userRole.getUserType()))
							continue;
						   sendCronRemainderForProjectMember(projectMember,userRole,Constants.SEMI_MONTHLY);
						    
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
			HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.MONTHLY);
			if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
				for(ProjectMembers projectMember : ProjectMembersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					if("Admin".equalsIgnoreCase(userRole.getUserType()))
						continue;
					sendCronRemainderForProjectMember(projectMember,userRole,Constants.MONTHLY);
				}
			}
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	
	@Async
	private void sendCronRemainderForProjectMember(ProjectMembers projectMember,UserRole userRole,String frequency) throws Exception{
			String level ="";
			OrgLocations orgLocations = orgLocationService.getOrgLocations(userRole.getOrgLocations().getId());
			Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember,"employeeScheduler");
			LinkedHashMap<String,String> finalTFMap = new LinkedHashMap<>();
			if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
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
					
		             if(submissionApproverHierarchyService.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMember.getId()) && (flag)){
				    	finalTFMap.put(key, value);
					}
				}
			}
			if(null != finalTFMap && !finalTFMap.isEmpty()){
				projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),finalTFMap,projectMember.getProjects(),level);	
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
									pah.getApproverUserId().getId());

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
						for (ProjectMembers projectMembers : projectMembersList) {
							timesheetFrequencyMap = timesheetFrequencyGeneratorUtil
									.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,
											"approverScheduler");
							if (null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()) {
								LinkedList<String> frequency = new LinkedList<>();
								tempHTML.append("<tr>");
								if (projectMembers.getEmployee().getUser().getFirstName() != null
										&& projectMembers.getEmployee().getUser().getLastName() != null) {
									tempHTML.append("<td>" + projectMembers.getEmployee().getUser().getFirstName() + " "
											+ projectMembers.getEmployee().getUser().getLastName() + "</td>");
								} else {
									tempHTML.append(
											"<td>" + projectMembers.getEmployee().getUser().getFirstName() + "</td>");
								}

								for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
									String key = entry.getKey();
									String value = entry.getValue();

									Date frequencyEndDate = commonUtil.orgLocationDateFormat(ol.getDateFormat(),
											value.replaceAll("/", "-"));
									Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
									currentCal.setLenient(false);
									currentCal.setTime(CommonUtil.currentDate());

									Boolean flag = true;
									if (CalendarUtil.getNumberOfDays(frequencyEndDate, currentCal.getTime()) < -2
											&& invoiceFrequency.equalsIgnoreCase(Constants.BI_WEEKLY)) {
										flag = false;
									}
									if (flag) {
										frequency.add(
												"<b>" + key.replaceAll("/", "-") + " To " + value.replaceAll("/", "-"));
									}

								}
								if (frequency != null && !frequency.isEmpty()) {
									tempHTML.append(
											"<td>" + frequency.stream().collect(Collectors.joining("<br/>")) + "</td>");
								}
								tempHTML.append("<td>" + projectMembers.getProjects().getProjectName() + "</td>");
								if (managerLevel != 1) {
									ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService
											.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),
													projectMembers.getEmployee().getUser().getId());
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
					    	
					    	 HashSet<ProjectMembers> projectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(invoiceFrequency);
							
					    	 if(null != projectMembersList && !projectMembersList.isEmpty()){
								for (ProjectMembers projectMembers : projectMembersList) {
									timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,"approverScheduler");
									  if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
									            LinkedList<String> frequency = new LinkedList<>();
												 tempHTML.append("<tr>");
												 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
													 tempHTML.append("<td style='text-align: center;'>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
												 }else{
													 tempHTML.append("<td style='text-align: center;'>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
												 }
												 
												 for(Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
													    String key = entry.getKey();
													    String value =entry.getValue();
													    
														Date frequencyEndDate = commonUtil.orgLocationDateFormat(ol.getDateFormat(),value.replaceAll("/", "-"));
														Calendar currentCal = Calendar.getInstance(TimeZone.getDefault());
														currentCal.setLenient(false);
														currentCal.setTime(CommonUtil.currentDate());
														
											             Boolean flag = true;
											             if(CalendarUtil.getNumberOfDays(frequencyEndDate,currentCal.getTime()) <-2 && invoiceFrequency.equalsIgnoreCase(Constants.BI_WEEKLY)){
											            	 flag = false;
											             }
											             if(flag){
											            	 frequency.add("<b>"+key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-"));
											             }
													    
												 }
											     if(frequency != null && !frequency.isEmpty()){
												    tempHTML.append("<td style='text-align: center;'>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
											     }
												  tempHTML.append("<td style='text-align: center;'>"+projectMembers.getProjects().getProjectName()+"</td>");
												 
												  Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectMembers.getProjects().getId());
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
					    	 tempHTML.append("</table> ");
								if(null != ol.getUser() && null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
								   projectsService.userEmailToAdmin(tempHTML,ol,ol.getUser());
								}
				  }
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
				
				DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				
				int addMinuteTime = 30;
				Date targetTime = new Date(); //now
				Date date = new Date();
				targetTime = DateUtils.addMinutes(targetTime, addMinuteTime); 
				
				String triggerTimeStr = dateFormat.format(targetTime); 
				String currentTimeStr = dateFormat.format(date); 
				
				
				List<RequirementInterviewHistory> interviewList = requirementInterviewHistoryService.getAllInterviewListByCurrentDate(currentTimeStr,triggerTimeStr);
				for (RequirementInterviewHistory interviewHistory : interviewList) {
					
					RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(interviewHistory.getInterviewId().getId().toString());
			 		
					int remainderTime = -(Integer.parseInt(requirementInterview.getRemainedBefore()));
					Date newTargetTime = DateUtils.addMinutes(requirementInterview.getStartTime(), remainderTime); 

					
					if(newTargetTime.after(date) && newTargetTime.before(targetTime)){
					Org org = orgService.findOrgByID(requirementInterview.getSubmissionId().getOrgLocations().getOrg().getId());
					User user = userService.getUserByUserCode(requirementInterview.getUpdatedBy());
					 
					List<RequirementInterviewer> requirementInterviewerList = requirementInterviewerService.getInterviwersListByInterviewId(requirementInterview.getId());
					
					if (requirementInterviewerList!=null) {
						for (RequirementInterviewer requirementInterviewer : requirementInterviewerList) {
							requirementInterviewService.sendEmailToInterviewrs(requirementInterview.getSubmissionId().getCandidate(),requirementInterviewer.getInterviewerUser(),requirementInterview.getSubmissionId(), user, org, requirementInterview, requirementInterview.getSubmissionId().getOrgLocations());
						}
					}
					
					if(interviewHistory.getNotificationToApplicant().equalsIgnoreCase("Y")){
								requirementInterviewService.sendEmailToApplicants(requirementInterview.getSubmissionId().getCandidate(),requirementInterview.getSubmissionId().getOrgLocations(),requirementInterview.getSubmissionId(), user, org, requirementInterview);
					}
					if(interviewHistory.getNotificationToSpoc().equalsIgnoreCase("Y")){

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
		 StringBuilder interviewStringBuilder=new StringBuilder();
		 emailMessage.setToEmail(user.getEmail());
		// emailMessage.setFromEmail("info@sourcelead.com");
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("");
		 emailMessage.setFromName("Sourcelead Team");
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("managerName", emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 globalVarsMap.put("consultantsCount", Integer.toString(candidatesListToBeAddedInHotList.size()));
		 globalVarsMap.put("tableOfContent", sb.toString());
			 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.HOTLIST_ALERT_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, org,orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());

		 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.HOTLIST_ALERT_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
		 
		 if(user.getFirstName()!=null){
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 User sourceLeadAdmin = userService.getUserById(Constants.SOURCELEAD_ADMIN_USERID);
		 
		 emailScenerioUtil.sendNotification(actionLink, sourceLeadAdmin, user, orgLocations, noificationEmailScenario);
		 }
	}
	
	
	/**
	 * @author prasanna
	 * Fires on every 30 min on every day emailJobScheduler.job.cron.everyminute.expression
	 * @throws Exception 
	 */
	@Scheduled(cron = "0 0/30 * 1/1 * ?")
	@Async
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
						        				contractSignedByReceiver(contracts);
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
	
	
	private void contractSignedByReceiver(Contracts contracts) throws Exception
	{
			User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
			User receiver = userService.getUserByUserCode(contracts.getContactUser().getUserCode());
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
			 
			 String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
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
			 String actionLink="";
			 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 
		 emailScenerioUtil.sendNotification(actionLink,receiver,owner, contracts.getOrgLocationId(), notificationScenario);
		
	}
	
	/**
	 * @author : Pavan Jampana
	 * this cron will used to send the remainder emails to respective owners about expiring of the contract. 
	 * 
	 * this cron will be fired once a day at 3'O clock
	 * 
	 * Morning 8 Am for us/canada
	 */

	@Scheduled(cron = "0 0 15 1/1 * ?")
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
		getDueDateCompletedInvoicesLists();

	}

	/**
	 * @author Rajashekar
	 * changing invoice payment status when due date is over
	 * @throws Exception
	 */
	private void getDueDateCompletedInvoicesLists() throws Exception {
		List<Invoice> invoiceList = invoiceService.getDueDateCompletedInvoicesList();
		invoiceList.forEach(invoice -> {
			if (invoice.getDueDate() != null && !"".equals(invoice.getDueDate())) {
				if (invoice.getDueDate().after(new Date())) {
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
	

	private void contractExpiryRemainderMail(Contracts contracts, int days) throws Exception
	{
			User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
			OrgLocations orgLocations = orgLocationService.getOrgLocations(contracts.getOrgLocationId().getId());
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(owner.getFirstName());
			 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
				 
				 emailMessage.setLastName(owner.getLastName());
			 }else{
				 emailMessage.setLastName("");
			 }
			 emailMessage.setToEmail(owner.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setCreatedBy("sladmin");
			 emailMessage.setFromName("SourceLead Admin"); 
			 
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
			globalVarsMap.put("loginUrl",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			
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
			 
			String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			 notificationScenario.setBody(notificationbody);
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 String actionLink="";
			 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
//			 emailScenerioUtil.sendNotification(actionLink, null,owner, contracts.getOrgLocationId(), notificationScenario);
		
	}
	
	
	
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
								ArrayList<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderList(keywd);
								if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
									for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
										anyBoolSubQueryBuilder.should(matchQueryBuilder);
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
	
	 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderList(String keywd){
		   
		   ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobId", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchQuery("industryName", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchQuery("description", keywd));
			
			
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			}

				
			return matchQueryBuilderList; 
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
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
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
						 
						 if(!isNullOrEmpty(requirementIndex.getMinExperience()) && !isNullOrEmpty(requirementIndex.getMaxExperience())){
							 experience = experience.append("(").append(requirementIndex.getMinExperience()).append("-").append(requirementIndex.getMaxExperience()).append(" Years").append(")");
						 }
						 else if(!isNullOrEmpty(requirementIndex.getMinExperience()) && isNullOrEmpty(requirementIndex.getMaxExperience())){
							 experience = experience.append("(").append(requirementIndex.getMinExperience()).append(" Years").append(")");
						 }
						 else if(isNullOrEmpty(requirementIndex.getMinExperience()) && !isNullOrEmpty(requirementIndex.getMaxExperience())){
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
	         emailMessage.setFromEmail("donotreply@sourcelead.com");
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
	         globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	        
	                
	        
	         InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
	        
	         //getting the current EmailScenario by EmailScenario unique name and entity type
	         EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFBOARDINGAPPLICANTDELAYPROCESS,Constants.ENTITY_TYPE_EMAIL);
	         EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFBOARDINGAPPLICANTDELAYPROCESS,Constants.ENTITY_TYPE_NOTIFICATION);

	        
	         //covert subject,body and signature
	         String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
	         String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
	         String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
	         String url = sourceleadUrl;
	        
	        /* String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
	         notificationScenario.setBody(notificationbody);
*/
	        
	        // OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();

	        
	         //construct HTML template using required data
	         String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
	         emailMessage.setSubject(subject);
	         emailMessage.setMessageBody(emailTemplate);
	        
	         //calling the sendEmail method of emailScenerioUtil
	         emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
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
}
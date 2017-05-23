package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidatesHotListDummy;
import com.bis.sourcelead.entity.ConsolidatedReport;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
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
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
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
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.docusign.esign.api.AuthenticationApi;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.api.AuthenticationApi.LoginOptions;
import com.docusign.esign.api.EnvelopesApi.ListStatusChangesOptions;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.model.Envelope;
import com.docusign.esign.model.EnvelopesInformation;
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
			DateFormat format = Constants.DF_DMY;
			Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember,"employeeScheduler");
			LinkedHashMap<String,String> finalTFMap = new LinkedHashMap<>();
			if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
				for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
				    String key = entry.getKey();
				    String value =entry.getValue();
				    Date frequencyStartDate = format.parse(key.replaceAll("/", "-"));
					Date frequencyEndDate = format.parse(value.replaceAll("/", "-"));
					
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
		Map<String,String>  timesheetFrequencyMap = null;
		DateFormat format = Constants.DF_DMY;
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
									if(managerLevel != 1){
										tempHTML.append("<th> Manager Name </th>");
									}
									tempHTML.append("</tr>");
									count++;
						  }
					     
					     List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(ol.getId(), invoiceFrequency, managerLevel);
					   
					     for (ProjectApproverHierarchy pah : projectApproverHierarchyList) {
					    	
					    	 HashSet<ProjectMembers> projectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(invoiceFrequency,pah.getApproverUserId().getId());
							
					    	 if(null != projectMembersList && !projectMembersList.isEmpty()){
								for (ProjectMembers projectMembers : projectMembersList) {
									timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMembers,"approverScheduler");
									  if(null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
									            LinkedList<String> frequency = new LinkedList<>();
												 tempHTML.append("<tr>");
												 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
												 }else{
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
												 }
												 
												 for(Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
													    String key = entry.getKey();
													    String value =entry.getValue();
													    
														Date frequencyEndDate = format.parse(value.replaceAll("/", "-"));
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
												    tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
											     }
												  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
												  if(managerLevel != 1){
													  ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
													  tempHTML.append("<td>").append(managerProjectApproverHierarchy.getApproverUserId().getFullName()).append("</td>");
												  }
												  tempHTML.append("</tr>");
									          }
								       }	
								}
					    	 tempHTML.append("</table> ");
								if(null != pah.getApproverUserId() && null != timesheetFrequencyMap && !timesheetFrequencyMap.isEmpty()){
								   projectsService.userEmailToAdmin(tempHTML,ol,pah.getApproverUserId());
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
		DateFormat format = Constants.DF_DMY;
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
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
												 }else{
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
												 }
												 
												 for(Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
													    String key = entry.getKey();
													    String value =entry.getValue();
													    
														Date frequencyEndDate = format.parse(value.replaceAll("/", "-"));
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
												    tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
											     }
												  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
												 
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
												    
												  tempHTML.append("<td>").append(projectStringBuilder).append("</td>");
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
			List<Org> orgList = orgService.getOrgs();
			for (Org org : orgList) {
				OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
				
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
					  					interviewDateInTimezone = Constants.DMY.format(interviewConverteDate);
					  					dateOfInterviewStr = interviewDateInTimezone;
				  			  		    candidatesHotListDummy.setProjectEndDate(dateOfInterviewStr);
				  			  		   hotlist1.add(candidatesHotListDummy);
				  			  		   
				  			  		}
				  			  		
				  			  	hotlist.addAll(hotlist1);
				  			  		 
			  			  		}
				  			  	CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candi);
				  			  	sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candi.getId().toString());
		  			    	 }
				  			  sendEmailToManager(user, projectMemberslist, candidatesListToBeAddedInHotList,hotlist, orgLocations, org);
		  			    }
				 }
			}
    }

	@Scheduled(cron = "0 0/30 * 1/1 * ?")
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
		 emailMessage.setFromEmail("info@sourcelead.com");
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
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, org);
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
	
	
	/*
	 * This method is for sending mail for Reporting manager 
	 *  while creating user in an organization we assign a reporting manager for every individual user ,this is optional 
	 
	@Scheduled(cron = "0 0 12 1/1 * ? ")
	@Async
	public void consolidatedReportsofReportingManagerForHisUsers() throws Exception {
		

        // To get the entire Org List from SourceLead
		List<Org> orgList = orgService.getOrgs();
		for (Org org : orgList) {
			OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
			ArrayList<Long> userIdList = new ArrayList<Long>();
			ArrayList<String> userCodeList = new ArrayList<String>();
			
	        // To get the entire UserList based on OrgLocationId from SourceLead having requirement Tab Access
			List<User> userRoleList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),
					Long.valueOf(Constants.TAB_REQUIREMENT));

			if (userRoleList != null && userRoleList.size() > 0) {

				for (User user : userRoleList) {
					
			        // To get the entire UserList  based on reporting Manager userId by  OrgLocationId from SourceLead having requirement Tab Access
					LinkedHashSet<User> userListUnderReportingManager = userService.getUsersListAsReoprtingManagersByOrgLocationIdHavingTabAccess(orgLocations.getId(),user,Long.valueOf(Constants.TAB_REQUIREMENT));
					
						
						if(userListUnderReportingManager.size() >0 && !userListUnderReportingManager.isEmpty()){
							
							List<ConsolidatedReport> consolidatedList = new ArrayList<ConsolidatedReport>();

							for(User usr :userListUnderReportingManager){
								
								
								ConsolidatedReport  conreport  =  new  ConsolidatedReport();
								conreport.setUserName(usr.getFirstName());
								
								Integer  todayReleasedRequirementsCount  = 0;
								Integer  activeRequirmenttsCount  = 0;
								Integer  applicantsCount  = 0;
								Integer  shortlistedCount  = 0;
								Integer  interviewedCount  = 0;
								Integer  submittedToClientCount  = 0;
								Integer  offeredCount  = 0;
								Integer  rejectedCount  = 0;
								Integer  tasksCompleted  = 0;
								Integer  candidatesCount  = 0;
								Integer  submissionsRecievedCount  = 0;
								Integer  requestSentCount  = 0;
								
								
								
								Long tasksCount = taskService.getTasksCompletedTodayByLocationCodeandUserId(orgLocations, usr);
								conreport.setNumberOfTasksCompletedCount(tasksCount.intValue());
								
								Integer candidatesCountCreatedToday = candidateService.getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(orgLocations, usr);
								conreport.setCreatedCandidatesCount(candidatesCountCreatedToday);
								
								// To get released  requirement Counts
								String[] modules = org.getModulesId().split(",");
								for (int i = 0; i < modules.length; i++) {
										if (modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)) {
											  todayReleasedRequirementsCount = requirementService.getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(orgLocations.getId(), usr);
											  conreport.setReleasedRequirementCount(todayReleasedRequirementsCount);
											  break;
										}
										
								}
								// to get active requirement based on userId
								List<Requirement> requirementList = requirementService.getAllRequirementsByOrglocationIdAndUserId(orgLocations.getId(), usr);
								activeRequirmenttsCount = requirementList.size();
								conreport.setActiveRequirementsCount(activeRequirmenttsCount);
								
								if(requirementList.size() >0 && !requirementList.isEmpty()){
                                	   
                                	   for (Requirement requirement : requirementList) {


               							//conreport.setRequirementName(requirement.getJobTitle());
               							for (int i = 0; i < modules.length; i++) {

               								
               								 * This is for checking the login Organization is
               								 * ATS or not And Get ALl received jobs Count
               								 * 
               								 
               								if (modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)) {

               									List<RequirementSubmission> submissionsRecievedList = requirementSubmissionService.getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(usr, orgLocations,
               													"CLIENT_SUBMISSIONS", requirement);
               									if (submissionsRecievedList.size() > 0 && submissionsRecievedList != null) {
               										submissionsRecievedCount = submissionsRecievedList.size();
               										   conreport.setRecievedSubmussionsCount(submissionsRecievedCount);
               										   break;
               									}
               								}
               								
               							}

               							Integer applicantsCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"APPLICANTS", requirement);
               							applicantsCount = applicantsCount+ applicantsCountForEachRequirement ;
               							conreport.setApplicantsCount(applicantsCount);
               							
               							Integer shortlistedCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"SHORTLISTED", requirement);
               							shortlistedCount = shortlistedCount + shortlistedCountForEachRequirement;
               							conreport.setShortlistedCount(shortlistedCount);

               							
               							
               							Integer submittedToClientCountForEachRequirement= requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"SUBMIT_TO_CLIENT", requirement);
               							submittedToClientCount = submittedToClientCount + submittedToClientCountForEachRequirement;
               							conreport.setSubmitToClientCount(submittedToClientCount);
               							
               							Integer interviewedCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"INTERVIEWED", requirement);
               							interviewedCount = interviewedCount + interviewedCountForEachRequirement;
               							conreport.setInterviewsCount(interviewedCount);
               							

               							Integer rejectedCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"REJECTED", requirement);
               							rejectedCount = rejectedCount + rejectedCountForEachRequirement;
               							conreport.setRejectedCount(rejectedCount);

               							Integer  requestSentCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"REQUESTSENT", requirement);
               							requestSentCount = requestSentCount +requestSentCountForEachRequirement ;
               							conreport.setRequestSentCount(requestSentCount);

               							Integer offeredCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(usr, orgLocations,"OFFERED", requirement);
               							offeredCount = offeredCount + offeredCountForEachRequirement ;
               							conreport.setOffersCount(offeredCount);

               						}
                                   }
								consolidatedList.add(conreport);
							}
							sendEmailconsolidatedReportsofReportingManagerForHisUsers(consolidatedList, user);
						}
						
				}

			}
		}

	}
	
	public void sendEmailconsolidatedReportsofReportingManagerForHisUsers(List<ConsolidatedReport> list,User user) throws Exception {
		
		 StringBuilder  sb  = new StringBuilder();
		
		 sb.append("<div>");
		 sb.append("<span style='width:260px;display:inline-block;margin-bottom:5px'>Team Name:</span>");
		 sb.append("<strong style='padding-left:10px'>");
		 sb.append(teamName);
		 sb.append("</strong><div>");
		 
		 sb.append("<div>");
		 sb.append("<span style='width:260px;display:inline-block;margin-bottom:5px'>Number of Team Numbers:</span>");
		 sb.append("<strong style='padding-left:10px'>");
		 sb.append(teamMembersCount);
		 sb.append("</strong><div>");
		 sb.append("<div>");
		
	
   	sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center;' cellpadding='2px' cellspacing='0' border='0'>");
   	sb.append("<thead style='background-color:#375d81; color: #fff;'>");
   	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");		    
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>User's</th>");	      
   	sb.append("<th style=.border: 1px solid #12385c; text-align: center;'>Active Requirements </th>");		        
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Applicants  </th>");		          
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Received </th>");
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Shortlisted </th>");
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Interviewed  </th>");
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Submit to Client </th>");
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Offered </th>");
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Tasks completed </th>");
   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Candidates created </th>");
   	
   	sb.append("</tr>");	
   	sb.append("</thead>");	
   	sb.append("<tbody>");
   	for(ConsolidatedReport conreport :list){
   		
   	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
   	sb.append("<td style='border: 1px solid #375d81;' width='300'>"+conreport.getUserName()+"</td>");		              
   	sb.append("<td style='border: 1px solid #375d81;' width='225'>"+conreport.getActiveRequirementsCount()+"</td>");	              
   	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getApplicantsCount()+"</td>");
   	if(null != conreport.getRecievedSubmussionsCount()){
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getRecievedSubmussionsCount()+"</td>");		            
   	}else{
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+0+"</td>");		            

   	}
   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getShortlistedCount()+"</td>");
   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getInterviewsCount()+"</td>");	
   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getSubmitToClientCount()+"</td>");	
   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getOffersCount()+"</td>");	
   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getNumberOfTasksCompletedCount()+"</td>");
   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getCreatedCandidatesCount()+"</td>");
   	sb.append("</tr>");		            
   	   
       }
   	sb.append("</tbody>");	              
   	sb.append("</table>");   
		
		 StringBuilder nameBuilder = new StringBuilder();
		 StringBuilder expBuilder = new StringBuilder();
		 StringBuilder locationBuilder = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 String experience = null;
		 String location = null;
		 
		 
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setCreatedBy("sladmin");
		 emailMessage.setFromName("info@sourcelead.com");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("experience", experience);
		 globalVarsMap.put("location", location);
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, sb.toString(), signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	
	
	
	
	 * This method is for sending mail for Every individual user  regarding requirement reports
	 * 
	 
	
	@Scheduled(cron = "0 0 12 1/1 * ? ")
	@Async
	public void scheduleUserMailForRequirementConsolidatedReports() throws Exception {
		
		
		 // To get the entire Org List from SourceLead
		List<Org> orgList = orgService.getOrgs();
		for (Org org : orgList) {
			OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
			ArrayList<Long> userIdList = new ArrayList<Long>();
			ArrayList<String> userCodeList = new ArrayList<String>();

			
			  // To get the entire UserList based on OrgLocationId from SourceLead having requirement Tab Access
			List<User> userRoleList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),
					Long.valueOf(Constants.TAB_REQUIREMENT));

			if (userRoleList != null && userRoleList.size() > 0) {

				for (User user : userRoleList) {
							Integer  todayReleasedRequirementsCount  = 0;
							
							List<ConsolidatedReport> list = new ArrayList<ConsolidatedReport>();
		
							String[] modules = org.getModulesId().split(",");
							for (int i = 0; i < modules.length; i++) {
									if (modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)) {
										
										  todayReleasedRequirementsCount = requirementService.getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(orgLocations.getId(), user);
										  break;
									}
									
							}
							// Requirements list assigned to requirement
							List<Requirement> requirementList = requirementService.getAllRequirementsByOrglocationIdAndUserId(orgLocations.getId(), user);
							Integer releasedRequirementCount = 0;
							Integer numberOfActiveRequirementCount = 0;
							Integer numberOfCreatedCandidatesCount = 0;
		
		
							for (Requirement requirement : requirementList) {
		
										ConsolidatedReport conreport = new ConsolidatedReport();
				
										conreport.setRequirementName(requirement.getJobTitle());
										for (int i = 0; i < modules.length; i++) {
				
											
											
											 * This is for checking the login Organization is
											 * ATS or not And Get ALl Vendor Submitted Jobs
											 * 
											 
											 
											if (modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)) {
				
												List<RequirementSubmission> submissionsFromClient = requirementSubmissionService.getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(user, orgLocations,
																"CLIENT_SUBMISSIONS", requirement);
												if (submissionsFromClient.size() > 0 && submissionsFromClient != null) {
													   conreport.setSubmissionCount(submissionsFromClient.size());
													   break;
												}
											}
											
										}
				
										Integer applicantsCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,"APPLICANTS", requirement);
										conreport.setApplicantsCount(applicantsCount);
				
										Integer shortlistedCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"SHORTLISTED", requirement);
										conreport.setShortlistedCount(shortlistedCount);
				
										Integer submittedToClientCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"SUBMIT_TO_CLIENT", requirement);
										conreport.setSubmitToClientCount(submittedToClientCount);
				
										Integer interviewedCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"INTERVIEWED", requirement);
										conreport.setInterviewsCount(interviewedCount);
				
										Integer rejectedCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"REJECTED", requirement);
										conreport.setRejectedCount(rejectedCount);
				
										Integer requestSentCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"REQUESTSENT", requirement);
										conreport.setRequestSentCount(requestSentCount);
				
										Integer offeredCount = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(user, orgLocations,
														"OFFERED", requirement);
										conreport.setOffersCount(offeredCount);
				
				  
										list.add(conreport);
				
									}
									numberOfCreatedCandidatesCount =	 candidateService.getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(orgLocations,user);
									sendEmailToscheduleUserMailForRequirementConsolidatedReports(list,user,todayReleasedRequirementsCount,requirementList.size(),numberOfCreatedCandidatesCount);
							
						}

			     }
		     }
		}


	
	 * This method is for sending mail for Every Team Lead  regarding team members requirement reports 
	 * 
	 
	
	@Scheduled(cron = "0 0 12 1/1 * ? ")
	@Async
	public void scheduleTeamLeadMailForRequirementConsolidatedReports() throws Exception {
	
		 // To get the entire Org List from SourceLead
		List<Org> orgList = orgService.getOrgs();
		for (Org org : orgList) {
			OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
			ArrayList<Long> userIdList = new ArrayList<Long>();
			ArrayList<String> userCodeList = new ArrayList<String>();
			
			//userList having Requirement Tab Access  and orglocationId
			List<User> userRoleList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),
					Long.valueOf(Constants.TAB_REQUIREMENT));

			if (userRoleList != null && userRoleList.size() > 0) {

				for (User user : userRoleList) {
					
					// Getting Team List based on userID and Location Id
					List<Team> teamList = teamService.getTeamListByTeamOwner(user.getId(),orgLocations.getId());
					if(teamList.size() >0 && !teamList.isEmpty()){
						for(Team team :teamList){
							
							String teamName = team.getName();
							
							// Getting TeamMembers  List based on TeamId and Location Id
							 List<TeamMembers>  teamMembersList =   teamMembersService.getTeamMembersListByTeamID(team.getId(), orgLocations.getId());
							
							Integer  teamMembersCount =  teamMembersList.size();
							List<ConsolidatedReport> consolidatedList = new ArrayList<ConsolidatedReport>();
							
							if(teamMembersList.size() >0 && !teamMembersList.isEmpty()){
								
								
								for(TeamMembers teamMember :teamMembersList){
								
									ConsolidatedReport  conreport  =  new  ConsolidatedReport();
									
									Integer  todayReleasedRequirementsCount  = 0;
									Integer  activeRequirmenttsCount  = 0;
									Integer  applicantsCount  = 0;
									Integer  shortlistedCount  = 0;
									Integer  interviewedCount  = 0;
									Integer  submittedToClientCount  = 0;
									Integer  offeredCount  = 0;
									Integer  rejectedCount  = 0;
									Integer  tasksCompleted  = 0;
									Integer  candidatesCount  = 0;
									Integer  submissionsRecievedCount  = 0;
									Integer  requestSentCount  = 0;
									
									
									
									User assignUser =  userService.getUserById(teamMember.getMemberUserId().getId());
									conreport.setTeamMemberName(assignUser.getFirstName());
									
									Long tasksCount = taskService.getTasksCompletedTodayByLocationCodeandUserId(orgLocations, assignUser);
									conreport.setNumberOfTasksCompletedCount(tasksCount.intValue());
									
									Integer candidatesCountCreatedToday = candidateService.getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(orgLocations, assignUser);
									conreport.setCreatedCandidatesCount(candidatesCountCreatedToday);
									
									// To get released  requirement Counts
									String[] modules = org.getModulesId().split(",");
									for (int i = 0; i < modules.length; i++) {
											if (modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)) {
												  todayReleasedRequirementsCount = requirementService.getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(orgLocations.getId(), assignUser);
												  conreport.setReleasedRequirementCount(todayReleasedRequirementsCount);
												  break;
											}
											
									}
									// to get active requirmentCounts
									List<Requirement> requirementList = requirementService.getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(orgLocations.getId(),teamMember);
									activeRequirmenttsCount = requirementList.size();
									conreport.setActiveRequirementsCount(activeRequirmenttsCount);
									
									if(requirementList.size() >0 && !requirementList.isEmpty()){
                                    	   
                                    	   for (Requirement requirement : requirementList) {


                   							conreport.setRequirementName(requirement.getJobTitle());
                   							for (int i = 0; i < modules.length; i++) {

                   								
                   								 * This is for checking the login Organization is
                   								 * ATS or not And Get ALl received jobs Count
                   								 * 
                   								 
                   								if (modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)) {

                   									List<RequirementSubmission> submissionsRecievedList = requirementSubmissionService.getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(assignUser, orgLocations,
                   													"CLIENT_SUBMISSIONS", requirement);
                   									if (submissionsRecievedList.size() > 0 && submissionsRecievedList != null) {
                   										submissionsRecievedCount = submissionsRecievedList.size();
                   										   conreport.setRecievedSubmussionsCount(submissionsRecievedCount);
                   										   break;
                   									}
                   								}
                   								
                   							}

                   							Integer applicantsCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"APPLICANTS", requirement);
                   							applicantsCount = applicantsCount+ applicantsCountForEachRequirement ;
                   							conreport.setApplicantsCount(applicantsCount);
                   							
                   							Integer shortlistedCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"SHORTLISTED", requirement);
                   							shortlistedCount = shortlistedCount + shortlistedCountForEachRequirement;
                   							conreport.setShortlistedCount(shortlistedCount);

                   							
                   							
                   							Integer submittedToClientCountForEachRequirement= requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"SUBMIT_TO_CLIENT", requirement);
                   							submittedToClientCount = submittedToClientCount + submittedToClientCountForEachRequirement;
                   							conreport.setSubmitToClientCount(submittedToClientCount);
                   							
                   							Integer interviewedCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"INTERVIEWED", requirement);
                   							interviewedCount = interviewedCount + interviewedCountForEachRequirement;
                   							conreport.setInterviewsCount(interviewedCount);
                   							

                   							Integer rejectedCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"REJECTED", requirement);
                   							rejectedCount = rejectedCount + rejectedCountForEachRequirement;
                   							conreport.setRejectedCount(rejectedCount);

                   							Integer  requestSentCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"REQUESTSENT", requirement);
                   							requestSentCount = requestSentCount +requestSentCountForEachRequirement ;
                   							conreport.setRequestSentCount(requestSentCount);

                   							Integer offeredCountForEachRequirement = requirementSubmissionService.getConsolidatedReportsForMailingForIndividualUserForToday(assignUser, orgLocations,"OFFERED", requirement);
                   							offeredCount = offeredCount + offeredCountForEachRequirement ;
                   							conreport.setOffersCount(offeredCount);

                   						}
                                       }
									consolidatedList.add(conreport);
								}
								
							}
							sendEmailToTeamLeadEveryDayConsolidatedReports(consolidatedList, user,team.getName(),teamMembersCount);
						}
					}
					
				}

			}
		}
		
		}


	public void sendEmailToTeamLeadEveryDayConsolidatedReports(List<ConsolidatedReport> list,User user,String teamName,Integer teamMembersCount) throws Exception {
		
		 StringBuilder  sb  = new StringBuilder();
		
		 sb.append("<div>");
		 sb.append("<span style='width:260px;display:inline-block;margin-bottom:5px'>Team Name:</span>");
		 sb.append("<strong style='padding-left:10px'>");
		 sb.append(teamName);
		 sb.append("</strong><div>");
		 
		 sb.append("<div>");
		 sb.append("<span style='width:260px;display:inline-block;margin-bottom:5px'>Number of Team Numbers:</span>");
		 sb.append("<strong style='padding-left:10px'>");
		 sb.append(teamMembersCount);
		 sb.append("</strong><div>");
		 sb.append("<div>");
		
			
		   	sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center;' cellpadding='2px' cellspacing='0' border='0'>");
		   	sb.append("<thead style='background-color:#375d81; color: #fff;'>");
		   	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");		    
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Team Members</th>");	      
		   	sb.append("<th style=.border: 1px solid #12385c; text-align: center;'>Active Requirements </th>");		        
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Applicants  </th>");		          
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Received </th>");
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Shortlisted </th>");
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Interviewed  </th>");
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Submit to Client </th>");
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Offered </th>");
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Tasks completed </th>");
		   	sb.append("<th style='border: 1px solid #12385c; text-align: center;'>Candidates created </th>");
		   	
		   	sb.append("</tr>");	
		   	sb.append("</thead>");	
		   	sb.append("<tbody>");
		   	for(ConsolidatedReport conreport :list){
		   		
		   	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
		   	sb.append("<td style='border: 1px solid #375d81;' width='300'>"+conreport.getTeamMemberName()+"</td>");		              
		   	sb.append("<td style='border: 1px solid #375d81;' width='225'>"+conreport.getActiveRequirementsCount()+"</td>");	              
		   	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getApplicantsCount()+"</td>");
		   	if(null != conreport.getRecievedSubmussionsCount()){
			    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getRecievedSubmussionsCount()+"</td>");		            
		   	}else{
			    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+0+"</td>");		            
		
		   	}
		   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getShortlistedCount()+"</td>");
		   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getInterviewsCount()+"</td>");	
		   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getSubmitToClientCount()+"</td>");	
		   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getOffersCount()+"</td>");	
		   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getNumberOfTasksCompletedCount()+"</td>");
		   	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getCreatedCandidatesCount()+"</td>");
		   	sb.append("</tr>");		            
		   	   
		       }
		   	sb.append("</tbody>");	              
		   	sb.append("</table>");   
				
		 StringBuilder nameBuilder = new StringBuilder();
		 StringBuilder expBuilder = new StringBuilder();
		 StringBuilder locationBuilder = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 String experience = null;
		 String location = null;
		 
		 
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setCreatedBy("sladmin");
		 emailMessage.setFromName("info@sourcelead.com");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("experience", experience);
		 globalVarsMap.put("location", location);
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, sb.toString(), signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	
	private void sendEmailToscheduleUserMailForRequirementConsolidatedReports(List<ConsolidatedReport> list, User user,
			Integer todayReleasedRequirementsCount, int size, Integer numberOfCreatedCandidatesCount) throws Exception {
		
		 StringBuilder  sb  = new StringBuilder();
		 
			 
			 sb.append("<div>");
			 sb.append("<span style='width:260px; display: inline-block; margin-bottom: 5px;'>Released Requirement Count:</span> <strong style='padding-left:10px;'>"+todayReleasedRequirementsCount+"</strong>");
			 sb.append("<div>");
			 sb.append("<div>");
			 sb.append("<span style='width:260px; display: inline-block; margin-bottom: 5px;'>Created Candidates Count:</span> <strong style='padding-left:10px;'>"+numberOfCreatedCandidatesCount+"</strong>");
			 sb.append("<div>");
			 sb.append("<div>");
			 sb.append("<span style='width:260px; display: inline-block; margin-bottom: 5px;'>Number Of Active Requirement:</span> <strong style='padding-left:10px;'>"+size+"</strong>");
			 sb.append("<div>");
				
			
	    	sb.append("<table width='900' align='center' style='border-collapse: collapse; margin: 0 auto;border: 1px solid #375d81; text-align: center; font-size: 12px;' cellpadding='2px' cellspacing='0' border='0'>");
	    	sb.append("<thead style='background-color:#375d81; color: #fff;'>");
	    	sb.append("<tr style='min-height: 30px; vertical-align: middle;'>");		    
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Requirement Name</th>");	      
	    	sb.append("<th style=.border: 1px solid #12385c; text-align: center; padding:15px 0;'>Applicants</th>");		        
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Shortlisted </th>");		          
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Submit To Client </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Rejected </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Interviewed </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Request Sent </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Offered </th>");
	    	sb.append("<th style='border: 1px solid #12385c; text-align: center; padding:15px 0;'>Submissions from Client </th>");
	    	
	    	sb.append("</tr>");	
	    	sb.append("</thead>");	
	    	sb.append("<tbody>");
	    	
	    	for(ConsolidatedReport conreport :list){
	    		
	    	sb.append("<tr style='min-height: 30px; line-height: 30px; vertical-align: middle;'>");
	    	sb.append("<td style='display: block; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 200px;' width='300'>"+conreport.getRequirementName()+"</td>");		              
	    	sb.append("<td style='border: 1px solid #375d81;' width='225'>"+conreport.getApplicantsCount()+"</td>");	              
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getShortlistedCount()+"</td>");	              
	    	sb.append("<td style='border: 1px solid #375d81;' width='275'>"+conreport.getSubmitToClientCount()+"</td>");		            
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getRejectedCount()+"</td>");
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getInterviewsCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getRequestSentCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getOffersCount()+"</td>");	
	    	sb.append("<td style='border: 1px solid #375d81;' width='200'>"+conreport.getSubmissionCount()+"</td>");
	    	sb.append("</tr>");		            
	    	   
	        }
	    	sb.append("</tbody>");	              
	    	sb.append("</table>");   
			
			 StringBuilder nameBuilder = new StringBuilder();
			 StringBuilder expBuilder = new StringBuilder();
			 StringBuilder locationBuilder = new StringBuilder();
			 EmailMessage emailMessage = new EmailMessage();
			 String professionalName = null;
			 String experience = null;
			 String location = null;
			 
			 
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromName("Sourcelead Admin");
			 emailMessage.setCreatedBy("sladmin");
			 emailMessage.setFromName("info@sourcelead.com");
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("professionalName", professionalName);
			 globalVarsMap.put("experience", experience);
			 globalVarsMap.put("location", location);
			 
			
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPLIED_TO_REQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, sb.toString(), signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
*/
	/**
	 * @author prasanna
	 * Fires on every 1 hour on every day emailJobScheduler.job.cron.everyminute.expression
	 * @throws Exception 
	 */
	@Scheduled(cron = "0 0/1 * 1/1 * ?")
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
		            	if(null != contracts.getEnvelopId() ){
		            		String parentFromStage = contracts.getTemplateSubmissionStatus();
			            	if(contracts.getEndDate()!=null){
			            		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			            		Date date = new Date();
			            		String currentTimeStr = TZ_DF_TS.format(date);
			    				Date curDate = TZ_DF_TS.parse(currentTimeStr);
			            		if(contracts.getEndDate().before(curDate)|| contracts.getEndDate().equals(curDate)){
			            			Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
				        			if(null != childContarct.getParentContractId()){
				        				String fromStage = childContarct.getTemplateSubmissionStatus();
				        				childContarct.setId(childContarct.getId());
				        				childContarct.setTemplateSubmissionStatus("EXPIRED");
				        				contracts.setId(contracts.getId());
				        				contracts.setTemplateSubmissionStatus("EXPIRED");
				        				contractsTemplateService.saveNewContract(childContarct);
				        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
					        				if(!("EXPIRED").equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
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
			            	
			       
			            	if(!"FINALIZED".equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!"EXPIRED".equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!"completed".equalsIgnoreCase(contracts.getContractStatus())){
				            	Envelope evp =  envelopesApi.getEnvelope(accountId, contracts.getEnvelopId());
				            	if(evp.getStatus().equalsIgnoreCase("completed")&&!contracts.getTemplateSubmissionStatus().equalsIgnoreCase("FINALIZED")){
				            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
				            		if(null != childContarct.getParentContractId()){
				            		String fromStage = childContarct.getTemplateSubmissionStatus();
				        				childContarct.setId(childContarct.getId());
				        				childContarct.setTemplateSubmissionStatus("SEND_FOR_VERIFICATION");
				        				contracts.setId(contracts.getId());
				        				contracts.setTemplateSubmissionStatus("WAITING_TO_BE_VERIFY");
				        				contracts.setContractStatus(evp.getStatus());
				        				childContarct.setContractStatus(evp.getStatus());
				        				contractsTemplateService.saveNewContract(childContarct);
				        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
				        				if(!("SEND_FOR_VERIFICATION").equalsIgnoreCase(fromStage)){
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
				            	if(evp.getStatus().equalsIgnoreCase("declined")&&!"EXPIRED".equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!contracts.getTemplateSubmissionStatus().equalsIgnoreCase("FINALIZED")){
				            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
				        			if(null != childContarct.getParentContractId()){
				        				String fromStage = childContarct.getTemplateSubmissionStatus();
				        				childContarct.setId(childContarct.getId());
				        				childContarct.setTemplateSubmissionStatus("REJECTED");
				        				contracts.setId(contracts.getId());
				        				contracts.setTemplateSubmissionStatus("REJECTED");
				        				contracts.setContractStatus(evp.getStatus());
				        				childContarct.setContractStatus(evp.getStatus());
				        				contractsTemplateService.saveNewContract(childContarct);
				        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
					        				if(!("REJECTED").equalsIgnoreCase(fromStage)){
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
				            	if(evp.getStatus().equalsIgnoreCase("deleted")&&!"EXPIRED".equalsIgnoreCase(contracts.getTemplateSubmissionStatus())&&!contracts.getTemplateSubmissionStatus().equalsIgnoreCase("FINALIZED")){
				            		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
				        			if(null != childContarct.getParentContractId()){
				        				String fromStage = childContarct.getTemplateSubmissionStatus();
				        				childContarct.setId(childContarct.getId());
				        				childContarct.setTemplateSubmissionStatus("DELETED");
				        				contracts.setId(contracts.getId());
				        				contracts.setTemplateSubmissionStatus("DELETED");
				        				contracts.setContractStatus(evp.getStatus());
				        				childContarct.setContractStatus(evp.getStatus());
				        				contractsTemplateService.saveNewContract(childContarct);
				        				if(!fromStage.equalsIgnoreCase(childContarct.getTemplateSubmissionStatus())){
				        				if(!("DELETED").equalsIgnoreCase(fromStage)){
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
				
		        }
		        catch (com.docusign.esign.client.ApiException ex)
		        {
		            System.out.println("Exception: " + ex);
		        }
	}
}

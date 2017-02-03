package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.JobSchedulerTestService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;

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
	
	@Autowired
	JobSchedulerTestService jobSchedulerTestService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	UserRoleService userRoleService;
	
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
	ProjectMembersService projectMembersService;
	
	@Autowired
	TaskService taskService;
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
	
	
	/**
	 * @author Rajashekar
	 * Fires on Every Firday at 6:30 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 30 18 ? * FRI")
	public void runJobSchedulerByWeekly() {
		logger.info("******************  Notification Job Scheduler started..! **********");
		try {
			//HashSet<User> usrList = projectsService.getAllUserTimeSheetSubmissionStatusCurrentWeek();
			HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.WEEKLY);
			if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
				for(ProjectMembers projectMember : ProjectMembersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					if("Admin".equalsIgnoreCase(userRole.getUserType()))
						continue;
					HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(projectMember.getId(),userRole.getOrgLocations().getId(),userRole.getUser().getId());
					String level ="";
					if(timesheetSubmissions !=null){
						projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),timesheetSubmissions,level);
						logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
					}
						
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
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
	 * @author Rajashekar
	 * Fires on Every 14 (Bi_Weekly) days at 7:30 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 30 19 1/14 * ?")
	public void runJobSchedulerByBi_Weekly() {
		logger.info("******************  Notification Job Scheduler Every two weeks started..! **********");
		try {
			
			//HashSet<User> usrList = projectsService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(invoiceFrequency);
			HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.BI_WEEKLY);
			if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
				for(ProjectMembers projectMember : ProjectMembersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					if("Admin".equalsIgnoreCase(userRole.getUserType()))
						continue;
					HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(projectMember.getId(),userRole.getOrgLocations().getId(),userRole.getUser().getId());
					String level="";
					if(timesheetSubmissions !=null){
						projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),timesheetSubmissions,level);	
						logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
					}
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }

	/**
	 * @author Rajashekar
	 * Fires on Every 15 (semi_monthly) days at 7:30 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 30 19 1/15 * ?")
	public void runJobSchedulerBySemi_Monthly() {
		logger.info("******************  Notification Job Scheduler Every 15 days in a month started..! **********");
		try {
				HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.SEMI_MONTHLY);
				if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
					for(ProjectMembers projectMember : ProjectMembersList){
						UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
						if("Admin".equalsIgnoreCase(userRole.getUserType()))
							continue;
						HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(projectMember.getId(),userRole.getOrgLocations().getId(),userRole.getUser().getId());
						String level ="";
							if(timesheetSubmissions != null){
								projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),timesheetSubmissions,level);	
								logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
							}
						    
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	/**
	 * @author Rajashekar
	 * Fires on Every month at 7:30 PM and send mails to employees those who are not submited their timesheets
	 */
	@Scheduled(cron = "0 30 19 30 1/1 ?")
	public void runJobSchedulerByMonthly() {
		logger.info("******************  Notification Job Scheduler  Every month 30th started..! **********");
		try {
			//HashSet<User> usrList = projectsService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.MONTHLY);
			HashSet<ProjectMembers> ProjectMembersList = userRoleService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(Constants.MONTHLY);
			if(ProjectMembersList !=null && ProjectMembersList.size() > 0){
				for(ProjectMembers projectMember : ProjectMembersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
					if("Admin".equalsIgnoreCase(userRole.getUserType()))
						continue;
					HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(projectMember.getId(),userRole.getOrgLocations().getId(),userRole.getUser().getId());
					String level="";
					if(timesheetSubmissions !=null){
						projectsService.userEmailNotificationForTimeSheetSubmission(userRole.getUser(),userRole.getOrgLocations().getId(),timesheetSubmissions,level);	
						logger.info("########## Mail Sent to: "+userRole.getUser().getEmail()+" ##########");
					}
				}
			}
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
    }
	//======================================================End
 	
 			
 			
	/**
	 * Prasanna vadla 
	 * mail to level1 Managers
	 * 
	 * Fires on Monday of every week of the month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 0 12 ? * MON")
	public void runJobSchedulerByMonday() {


		logger.info("******************  Notification Job Scheduler MONDAY of every week of the Month started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			HashSet<TimesheetSubmissions> timesheetSubmissions = null;
			for (Org org : orgList) {
				ProjectApproverHierarchy projectApproverHierarchy = null;
				User user = null;
				StringBuilder tempHTML = new StringBuilder();
				int count=0;
				tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
				
				OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
				
				List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
				if(count==0){	
					 tempHTML.append("<tr>");
						        tempHTML.append("<th> Employee Name </th>");
						        tempHTML.append("<th> Frequency </th>");
								tempHTML.append("<th> Project Name </th>");
								tempHTML.append("<th> Manager Name </th>");
								tempHTML.append("</tr>");
								count++;
					}
				
				for (Projects projects : projectsList) {
					Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projects.getId());
					for (ProjectMembers projectMembers : projectMembersList) {
						 timesheetSubmissions = timesheetSubmissionsService.getWeekFrequencyByProjMemId(projectMembers.getId());
						          if(timesheetSubmissions !=null){
							          TimesheetSubmissions ts = timesheetSubmissions.iterator().next();
							          List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectIdForManager(ts.getProjectMembers().getProjects().getId(), projectMembers.getEmployee().getUser().getId(),1);
							           if(projectApproverHierarchyList.size() > 0){
							        	   projectApproverHierarchy = projectApproverHierarchyList.get(0);
							        	   if(projectApproverHierarchy.getLevel() == 1){
							        		   user = projectApproverHierarchy.getApproverUserId();
							        	   }
							           }
							          LinkedList<String> frequency = new LinkedList<>();
										if(timesheetSubmissions != null && !timesheetSubmissions.isEmpty()){
											if(projectApproverHierarchyList.size() == 0){
												 tempHTML.append("<tr>");
												 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
												 }else{
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
												 }
												 
												 for(TimesheetSubmissions ts1 :timesheetSubmissions){
													 frequency.add("<b>"+Constants.DMY.format(ts1.getStartDate())+" To "+Constants.DMY.format(ts1.getEndDate()));
												   }
											    if(frequency != null && !frequency.isEmpty()){
												 tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
											     }
												  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
												  ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
												 if(managerProjectApproverHierarchy !=null){
													 tempHTML.append("<td>"+managerProjectApproverHierarchy.getApproverUserId().getFirstName()+"</td>"); 
												 }else{
													 tempHTML.append("<td>--</td>");
												 }
												  
												 tempHTML.append("</tr>");
											
											}
										}
						          }
									
							}
					}
				tempHTML.append("</table> ");
				if(user !=null && timesheetSubmissions!=null){
				projectsService.userEmailToAdmin(tempHTML,orgLocations,user);
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	
	/**
	 * Prasanna vadla
	 * this is for level for level 2
	 * Fires on Wednesday of every week of the month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 1 0 ? * WED")
	public void runJobSchedulerByWedDay() {
		logger.info("******************  Notification Job Scheduler WednesDay of every week of the month started..! **********");
		try {
			
			List<Org> orgList = orgService.getOrgs();
			HashSet<TimesheetSubmissions> timesheetSubmissions = null;
			for (Org org : orgList) {
				ProjectApproverHierarchy projectApproverHierarchy = null;
				User user = null;
				StringBuilder tempHTML = new StringBuilder();
				int count=0;
				tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
				
				OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
				
				List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
				if(count==0){	
					 tempHTML.append("<tr>");
						        tempHTML.append("<th> Employee Name </th>");
						        tempHTML.append("<th> Frequency </th>");
								tempHTML.append("<th> Project Name </th>");
								tempHTML.append("<th> Manager Name </th>");
								tempHTML.append("</tr>");
								count++;
					}
				
				for (Projects projects : projectsList) {
					
					Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projects.getId());
					for (ProjectMembers projectMembers : projectMembersList) {
						timesheetSubmissions = timesheetSubmissionsService.getWeekFrequencyByProjMemId(projectMembers.getId());        
						  if(timesheetSubmissions !=null){
					          TimesheetSubmissions ts = timesheetSubmissions.iterator().next();
					          List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectIdForManager(ts.getProjectMembers().getProjects().getId(), projectMembers.getEmployee().getUser().getId(),2);
					           if(projectApproverHierarchyList.size() > 0){
					        	   projectApproverHierarchy = projectApproverHierarchyList.get(0);
					        	   if(projectApproverHierarchy.getLevel() == 2){
					        		   user = projectApproverHierarchy.getApproverUserId();
					        	   }
					           }
					          LinkedList<String> frequency = new LinkedList<>();
								if(timesheetSubmissions != null && !timesheetSubmissions.isEmpty()){
									if(projectApproverHierarchyList.size() == 0){
										 tempHTML.append("<tr>");
										 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
											 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
										 }else{
											 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
										 }
										 
										 for(TimesheetSubmissions ts1 :timesheetSubmissions){
											 frequency.add("<b>"+Constants.DMY.format(ts1.getStartDate())+" To "+Constants.DMY.format(ts1.getEndDate()));
										   }
									    if(frequency != null && !frequency.isEmpty()){
										 tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
									     }
										  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
										  ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
                                            if(managerProjectApproverHierarchy !=null){
                                            	 tempHTML.append("<td>"+managerProjectApproverHierarchy.getApproverUserId().getFirstName()+"</td>");
										         }else{
										        	 tempHTML.append("<td>--</td>"); 
										         }
										 tempHTML.append("</tr>");
									
									}
								}
						}
						
						        
							}
					}
				tempHTML.append("</table> ");
				if(user !=null && timesheetSubmissions!=null){
					projectsService.userEmailToAdmin(tempHTML,orgLocations,user);
				}
				}
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
	}
	
	/**
	 * Prasanna vadla
	 * this is for Admin mail
	 * Fires on Friday of every week of the month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 1 0 ? * FRI")
	public void runJobSchedulerByFRIDay() {
		logger.info("******************  Notification Job Scheduler FRIDAY of every week of the month started..! **********");
		try {
			List<Org> orgList = orgService.getOrgs();
			HashSet<TimesheetSubmissions> timesheetSubmissions = null;
			for (Org org : orgList) {
				ProjectApproverHierarchy projectApproverHierarchy = null;
				User user = null;
				StringBuilder tempHTML = new StringBuilder();
				int count=0;
				tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
				
				OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(org.getId());
				
				List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
				if(count==0){	
					 tempHTML.append("<tr>");
						        tempHTML.append("<th> Employee Name </th>");
						        tempHTML.append("<th> Frequency </th>");
								tempHTML.append("<th> Project Name </th>");
								tempHTML.append("<th> Manager Name </th>");
								tempHTML.append("</tr>");
								count++;
					}
				
				for (Projects projects : projectsList) {
					Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projects.getId());
					for (ProjectMembers projectMembers : projectMembersList) {
						 timesheetSubmissions = timesheetSubmissionsService.getWeekFrequencyByProjMemId(projectMembers.getId());
						          if(timesheetSubmissions !=null){
							          TimesheetSubmissions ts = timesheetSubmissions.iterator().next();
							          List<ProjectApproverHierarchy> projectApproverHierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectIdForManager(ts.getProjectMembers().getProjects().getId(), projectMembers.getEmployee().getUser().getId(),1);
							           if(projectApproverHierarchyList.size() > 0){
							        	   projectApproverHierarchy = projectApproverHierarchyList.get(0);
							        	   if(projectApproverHierarchy.getLevel() == 1){
							        		   user = projectApproverHierarchy.getApproverUserId();
							        	   }
							           }
							          LinkedList<String> frequency = new LinkedList<>();
										if(timesheetSubmissions != null && !timesheetSubmissions.isEmpty()){
											if(projectApproverHierarchyList.size() == 0){
												 tempHTML.append("<tr>");
												 if(projectMembers.getEmployee().getUser().getFirstName()!= null && projectMembers.getEmployee().getUser().getLastName() !=null){
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+" "+projectMembers.getEmployee().getUser().getLastName()+"</td>");
												 }else{
													 tempHTML.append("<td>"+projectMembers.getEmployee().getUser().getFirstName()+"</td>");
												 }
												 
												 for(TimesheetSubmissions ts1 :timesheetSubmissions){
													 frequency.add("<b>"+Constants.DMY.format(ts1.getStartDate())+" To "+Constants.DMY.format(ts1.getEndDate()));
												   }
											    if(frequency != null && !frequency.isEmpty()){
												 tempHTML.append("<td>"+frequency.stream().collect(Collectors.joining("<br/>"))+"</td>");
											     }
												  tempHTML.append("<td>"+projectMembers.getProjects().getProjectName()+"</td>");
												  ProjectApproverHierarchy managerProjectApproverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectIdLevel1(projectMembers.getProjects().getId(),projectMembers.getEmployee().getUser().getId());
												 if(managerProjectApproverHierarchy !=null){
													 tempHTML.append("<td>"+managerProjectApproverHierarchy.getApproverUserId().getFirstName()+"</td>");
												 }else{
													 tempHTML.append("<td>--</td>");
												 }
												  tempHTML.append("</tr>");
											
											}
										}
						          }
									
							}
					}
				tempHTML.append("</table> ");
				if(user !=null && timesheetSubmissions!=null){
				projectsService.userEmailToAdmin(tempHTML,orgLocations,user);
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR in " + e.getMessage());
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
				
				// to bring the candidates who are going to terminate in a given time 
				
  			    List<Candidate>   candidatesListToBeAddedInHotList = candidateService.getAllCandidatesForHotListingWhoAreTerminating(orgLocations.getId(),30);
                
  			    if(null != candidatesListToBeAddedInHotList){
  			    	 for(Candidate can :candidatesListToBeAddedInHotList){
  			    		can.setIsFavourite(Constants.RECORD_ACTIVE);
  			    		Candidate candi = candidateService.addCandidate(can);
	  			  		
		  			  	CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candi);
		  			  	sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candi.getId().toString());
  				
  				
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
	
	
}

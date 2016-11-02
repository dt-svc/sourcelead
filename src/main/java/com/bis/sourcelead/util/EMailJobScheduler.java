package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.JobSchedulerTestService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
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
	UserService userservice;
	
	@Autowired
	OrgService orgservice;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService;
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
/**
 * Fires at 7:00 PM Every Day	
 */
//	@Scheduled(cron = "${emailJobScheduler.job.cron.expression}")
	@Scheduled(cron = "0 30 4 * * ?")
	public void runEMailJobScheduler() {
		logger.info("##########  E-Mail Job Scheduler started..! ##########");
		try {
			List<User> usrList = projectsService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus();
			for(User user : usrList){
				if("Admin".equalsIgnoreCase(user.getUserType()))
					continue;
				HashSet<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(user.getId());
				projectsService.userEmailNotificationForTimeSheetSubmission(user,timesheetSubmissions);
				logger.info("########## Mail Sent to: "+user.getEmail()+" ##########");
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
        
    }
	
	/**
	 * Fires on first Friday on every month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 30 18 ? * FRI")
	public void runNotificationJobScheduler() {
		String notificationJobSchedulerStatus = "";
		logger.info("******************  Notification Job Scheduler started..! **********");
		Date date = new Date();
		try {
			notificationJobSchedulerStatus = jobSchedulerTestService.notiFicationSchedularTest(date.toLocaleString());
			logger.info("*********** COMPLETED the Notification Job Scheduler with status as : "+notificationJobSchedulerStatus+" ***********");
			HashSet<User> usrList = projectsService.getAllUserTimeSheetSubmissionStatusCurrentWeek();
			if(usrList != null && !usrList.isEmpty()){
				for(User user : usrList){
					if("Admin".equalsIgnoreCase(user.getUserType()))
						continue;
					HashSet<TimesheetSubmissions>  timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(user.getId());
					projectsService.userEmailNotificationForTimeSheetSubmission(user,timesheetSubmissions);
					logger.info("########## Mail Sent to: "+user.getEmail()+" ##########");
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
      
    }
	
	/**
	 * Fires on 30 min on every day emailJobScheduler.job.cron.everyminute.expression
	 * @throws Exception 
	 */
	@Scheduled(cron = "0 0/30 * 1/1 * ?")
	@Async
	public void runRemainderEmailScheduler() throws Exception {
				logger.info("##########  E-Mail Job Scheduler started..! ##########");
				logger.info("########## Mail Sent to: Applicant,Interviewer,Others  ##########");
				
				DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				
				int addMinuteTime = 30;
				Date targetTime = new Date(); //now
				targetTime = DateUtils.addMinutes(targetTime, addMinuteTime); 
				String triggerTime = dateFormat.format(targetTime); 
				
				String currentTime = dateFormat.format(date); 
				
				
				List<RequirementInterview> interviewList = requirementInterviewService.getAllInterviewListByCurrentDate(currentTime,triggerTime);
				for (RequirementInterview requirementInterview : interviewList) {
					Org org = orgservice.findOrgByID(requirementInterview.getSubmissionId().getOrgLocations().getOrg().getId());
					User user = userservice.getUserByUserCode(requirementInterview.getUpdatedBy());
					
					List<RequirementInterviewer> requirementInterviewerList = requirementInterviewerService.getInterviwersListByInterviewId(requirementInterview.getId());
					
					if (requirementInterviewerList!=null) {
						for (RequirementInterviewer requirementInterviewer : requirementInterviewerList) {
							requirementInterviewService.sendEmailToInterviewrs(requirementInterview.getSubmissionId().getCandidate(),requirementInterviewer.getInterviewerUser(),requirementInterview.getSubmissionId(), user, org, requirementInterview, requirementInterview.getSubmissionId().getOrgLocations());
						}
					}
					
					if(requirementInterview.getNotificationToApplicant().equalsIgnoreCase("Y")){
								requirementInterviewService.sendEmailToApplicants(requirementInterview.getSubmissionId().getCandidate(),requirementInterview.getSubmissionId(), user, org, requirementInterview);
					}
					if(requirementInterview.getNotificationToSpoc().equalsIgnoreCase("Y")){
						requirementInterviewService.sendEmailToVendors(requirementInterview.getSubmissionId().getCandidate(),requirementInterview.getSubmissionId(), user,org, requirementInterview);
					}
					
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

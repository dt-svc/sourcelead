package com.bis.sourcelead.util;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.JobSchedulerTestService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

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
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
/**
 * Fires at 7:00 PM Every Day	
 */
//	@Scheduled(cron = "${emailJobScheduler.job.cron.expression}")
	@Scheduled(cron = "0 30 19 ? * *")
	public void runEMailJobScheduler() {
		String emailJobSchedulerStatus = "";
		logger.info("##########  E-Mail Job Scheduler started..! ##########");
		Date date = new Date();
		try {
			List<User> usrList = projectsService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus();
			for(User user : usrList){
				if("Admin".equalsIgnoreCase(user.getUserType()))
					continue;
				List<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(user.getId());
				projectsService.userEmailNotificationForTimeSheetSubmission(user,timesheetSubmissions);
			}
			emailJobSchedulerStatus = jobSchedulerTestService.schedulerTest(date.toLocaleString());
			logger.info("########## COMPLETED the E-Mail Job Scheduler with status as : "+emailJobSchedulerStatus+" ##########");
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
        
    }
	
	/**
	 * Fires on first Friday on every month emailJobScheduler.job.cron.everyfriday.expression
	 */
	@Scheduled(cron = "0 30 18 ? * FRI")
//	@Scheduled(cron = "${emailJobScheduler.job.cron.everyfriday.expression}")
	public void runNotificationJobScheduler() {
		String notificationJobSchedulerStatus = "";
		logger.info("******************  Notification Job Scheduler started..! **********");
		Date date = new Date();
		try {
			notificationJobSchedulerStatus = jobSchedulerTestService.notiFicationSchedularTest(date.toLocaleString());
			logger.info("*********** COMPLETED the Notification Job Scheduler with status as : "+notificationJobSchedulerStatus+" ***********");
			HashSet<User> usrList = projectsService.getAllUserTimeSheetSubmissionStatusCurrentWeek();
			for(User user : usrList){
				if("Admin".equalsIgnoreCase(user.getUserType()))
					continue;
				List<TimesheetSubmissions>  timesheetSubmissions = timesheetSubmissionsService.getWeekFrequency(user.getId());
				projectsService.userEmailNotificationForTimeSheetSubmission(user,timesheetSubmissions);
			}
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
      
    }
}

package com.bis.sourcelead.util;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.JobSchedulerTestService;
import com.bis.sourcelead.service.ProjectsService;

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
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
/**
 * Fires at 7:30 PM Every Day	
 */
	@Scheduled(cron = "${emailJobScheduler.job.cron.expression}")
	public void runEMailJobScheduler() {
		String emailJobSchedulerStatus = "";
		logger.info("##########  E-Mail Job Scheduler started..! ##########");
		Date date = new Date();
		try {
			List<User> usrList = projectsService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus();
			for(User user : usrList){
				projectsService.userEmailNotificationForTimeShhetSubmission(user);
			}
			emailJobSchedulerStatus = jobSchedulerTestService.schedulerTest(date.toLocaleString());
			logger.info("########## COMPLETED the E-Mail Job Scheduler with status as : "+emailJobSchedulerStatus+" ##########");
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
        
    }
	
	/**
	 * Fires on first Friday on every month
	 */
	@Scheduled(cron = "0 30 18 ? * FRI")
	public void runNotificationJobScheduler() {
		String notificationJobSchedulerStatus = "";
		logger.info("******************  Notification Job Scheduler started..! **********");
		Date date = new Date();
		try {
			notificationJobSchedulerStatus = jobSchedulerTestService.notiFicationSchedularTest(date.toLocaleString());
			logger.info("*********** COMPLETED the Notification Job Scheduler with status as : "+notificationJobSchedulerStatus+" ***********");
			List<User> usrList = projectsService.getAllUserTimeSheetSubmissionStatus();
			for(User user : usrList){
				projectsService.userEmailNotificationForTimeShhetSubmission(user);
			}
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
      
    }
}

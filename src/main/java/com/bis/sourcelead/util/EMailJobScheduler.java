package com.bis.sourcelead.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.bis.sourcelead.service.JobSchedulerTestService;

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
	
	private static final Logger logger = LoggerFactory.getLogger(EMailJobScheduler.class);
	
	@Scheduled(cron = "${emailJobScheduler.job.cron.expression}")
	public void runEMailJobScheduler() {
		String emailJobSchedulerStatus = "";
		logger.info("##########  E-Mail Job Scheduler started..! ##########");
		Date date = new Date();
		try {
			emailJobSchedulerStatus = jobSchedulerTestService.schedulerTest(date.toLocaleString());
			logger.info("########## COMPLETED the E-Mail Job Scheduler with status as : "+emailJobSchedulerStatus+" ##########");
			
		} catch (Exception e) {
			logger.error("ERROR in " + e.getMessage());
		}
        
    }

}

package com.bis.sourcelead;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bis.sourcelead.util.EMailJobScheduler;

/**
*
* <b>Purpose:</b><br>
*
* Spring Job Scheduler configuration class. This class enables the job scheduling.
* 
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


@Configuration
@EnableScheduling
@EnableAsync
public class JobSchedulerConfig {
	
	@Bean
    public EMailJobScheduler bean() {
        return new EMailJobScheduler();
    }

}

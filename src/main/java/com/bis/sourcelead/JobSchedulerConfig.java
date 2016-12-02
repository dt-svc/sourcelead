package com.bis.sourcelead;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bis.sourcelead.exception.SourceLeadCustomAsyncExceptionHandler;
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
* Sl No   Modified Date   Author</b>
* ==============================================
* 1.    28-Jun-2016      Srinivas Vemula
*      -- Base Release
* 2.	01-Dec-2016      Srinivas Vemula
*      -- Converted the email sending logic to async call. For this, we need to over ride
*         the AsyncCinfigurer with custom exception class.
*         Changed the method name as well. 
* </pre>
*/


@Configuration
@EnableScheduling
@EnableAsync
public class JobSchedulerConfig implements AsyncConfigurer {
	
	@Bean
    public EMailJobScheduler createEMailJobScheduler() {
        return new EMailJobScheduler();
    }
	
	@Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SourceLeadCustomAsyncExceptionHandler();
    }

	@Override
	public Executor getAsyncExecutor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("SourceLeadAsyncSendMail-");
        executor.initialize();
        return executor;
	}

}

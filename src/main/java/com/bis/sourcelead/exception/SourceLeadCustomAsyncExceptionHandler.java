package com.bis.sourcelead.exception;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
*
* <b>Purpose:</b><br>
*
* SourceLead custom asynchronous  exception class, which will be called
* if any exception occurred during the async calls 
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
* 1.    01-Dec-2016      Srinivas Vemula
*      -- Base Release
* </pre>
*/
public class SourceLeadCustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(SourceLeadCustomAsyncExceptionHandler.class);
	
	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
		
		logger.error("Method name : " + method.getName());
		logger.error("Exception : " + throwable);
		
		for (Object param : obj) {
			logger.error("Parameter value : " + param);
		}
	}

}

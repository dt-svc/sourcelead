package com.bis.sourcelead;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.CouchbaseClusterService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.impl.CouchbaseClusterServiceImpl;
import com.bis.sourcelead.service.impl.UserLoginLogoutSessionServiceImpl;
import com.couchbase.client.java.Bucket;

public class SourceleadSessionListener implements HttpSessionListener {

	@Configuration
	static class UserLoginLogoutServiceContextConfiguration {
		@Bean
		public UserLoginLogoutSessionService userLoginLogoutSessionService() {
			return new UserLoginLogoutSessionServiceImpl();
		}
		
		@Bean
		public CouchbaseClusterService couchbaseClusterService() {
			return new CouchbaseClusterServiceImpl();
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(SourceleadSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		 System.out.println("@@@@@ Session is created @@@@@");
		logger.debug("@@@@@ Session is created @@@@@ ");
		event.getSession().setMaxInactiveInterval(30 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		HttpSession session = event.getSession();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		UserLoginLogoutSessionService userLoginLogoutSessionService = (UserLoginLogoutSessionService) ctx
				.getBean("userLoginLogoutSessionService");

		UserLoginLogoutSession userLoginSession = (UserLoginLogoutSession) event.getSession()
				.getAttribute("userLoginSession");

		if (userLoginSession != null) {
			userLoginSession.setLogoutTime(new Date());
			userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
		}
		
	/*     if(event.getSession().getAttribute("sessionId") != null) {
	    	 CouchbaseClusterService couchbaseClusterService = (CouchbaseClusterService) ctx
	 				.getBean("couchbaseClusterService");
	 	     CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
	 	     Bucket bucket = couchbaseClusterService.openBucket(couchConfig.getUserLoginSessionStoreBucket(), couchConfig.getPassword());
	 	     if(bucket != null) {
	 	    	 bucket.remove(event.getSession().getAttribute("sessionId")+"_SESSION");
	 	     }
	    	
	     }*/
	    
	     System.out.println("@@@@@ Session is destroyed @@@@@");
	     logger.debug("@@@@@ Session is Destroyed @@@@@ ");
	}
}
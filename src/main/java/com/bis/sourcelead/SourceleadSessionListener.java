package com.bis.sourcelead;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.UserLoginLogoutSessionServiceImpl;
import com.bis.sourcelead.service.impl.UserServiceImpl;
public class SourceleadSessionListener implements HttpSessionListener {
	
		@Configuration
		static class UserServiceContextConfiguration {
			@Bean
			public UserService userService() {
				return new UserServiceImpl();
			}
		}
		@Configuration
		static class UserLoginLogoutServiceContextConfiguration {
			@Bean
			public UserLoginLogoutSessionService userLoginLogoutSessionService() {
				return new UserLoginLogoutSessionServiceImpl();
			}
		}
	
	  private static final Logger logger = LoggerFactory.getLogger(SourceleadSessionListener.class);
	 
	  
	   @Override
		public void sessionCreated(HttpSessionEvent event) {
	 		logger.debug("@@@@@ Session is created @@@@@ ");
			event.getSession().setMaxInactiveInterval(30*60);
		}
		
		@Override
		public void sessionDestroyed(HttpSessionEvent event) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			HttpSession session = event.getSession();
	        ApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	        UserService userService = (UserService) ctx.getBean("userService");
	        UserLoginLogoutSessionService userLoginLogoutSessionService = (UserLoginLogoutSessionService) ctx.getBean("userLoginLogoutSessionService");
	          
			User loginUser = userService.findByUserName(auth.getName());
			
				UserLoginLogoutSession userLoginSession =(UserLoginLogoutSession)event.getSession().getAttribute("userLoginSession");
				if(userLoginSession == null){
					if(loginUser != null){
						userLoginSession = userLoginLogoutSessionService.getLastLoginRecord(loginUser.getId());
					}
				}
				if(userLoginSession != null){
					userLoginSession.setLogoutTime(new Date());
					userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
				}
				
			   logger.debug("@@@@@ Session is Destroyed @@@@@ ");
		}
}
package com.bis.sourcelead;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.impl.UserLoginLogoutSessionServiceImpl;

public class SourceleadSessionListener implements HttpSessionListener {

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

		logger.debug("@@@@@ Session is Destroyed @@@@@ ");
	}
}
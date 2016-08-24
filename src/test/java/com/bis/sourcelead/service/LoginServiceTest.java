package com.bis.sourcelead.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.service.impl.LoginServiceImpl;

/**
 * This is a JUnit Test class for testing LoginService methods
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class LoginServiceTest {

	
	@Configuration
	static class LoginServiceTestContextConfiguration {
		@Bean
		public LoginService loginService() {
			return new LoginServiceImpl();
		}
				

	}

	@Autowired
	LoginService loginService;

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceTest.class);

	@Test
	public void checkAuthentication() throws Exception {

		Boolean check = loginService.checkAuthentication("admin", "admin");
		logger.info("checkAuthentication "+check.FALSE);
		logger.info("checkAuthentication "+check);
	}

}

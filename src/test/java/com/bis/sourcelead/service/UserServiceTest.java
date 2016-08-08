package com.bis.sourcelead.service;

import org.junit.Assert;
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
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.UserServiceImpl;

/**
 * This is a JUnit Test class for testing UserService methods
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class UserServiceTest {

	@Configuration
	static class UserServiceTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
				

	}

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	//@Test
	public void findByUserName() {
		String username = "SourceLeadAdmin";
		logger.debug("In UserServiceTest class...");
		User user = userService.findByUserName(username);
		Assert.assertEquals("SourceLeadAdmin",user.getUserName());
		
	}
	@Test
	public void getOrgCodeandLocationCodeTimeSheetNotification() throws Exception{
		userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(7l);
	}
}

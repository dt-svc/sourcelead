package com.bis.sourcelead.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginAttempts;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class UserLoginAttemptsServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginAttemptsServiceTest.class);
	
	@Autowired
	UserLoginAttemptsService userLoginAttemptsService;
	
	@Test
	public void addUserLoginAttempts() throws Exception{
		UserLoginAttempts userLoginAttempts = new UserLoginAttempts();
		userLoginAttempts.setAttemptsCount(0);
		userLoginAttempts.setCreatedBy("shiva");
		userLoginAttempts.setCreatedDate(new Date());
		userLoginAttempts.setStatus("ACTIVE");
		userLoginAttempts.setUpdatedBy("yogi");
		userLoginAttempts.setUpdatedDate(new Date());
		User user = new User();
		user.setId(3l);
		userLoginAttempts.setUser(user);
		assertEquals(userLoginAttempts,userLoginAttemptsService.addUserLoginAttempts(userLoginAttempts));
		
	}
	@Test
	public void getUserLoginAttemptsByUserId() throws Exception{
		UserLoginAttempts UserLoginAttemptsByUserId = userLoginAttemptsService.getUserLoginAttemptsByUserId(3l);
		assertEquals(new Long(3),UserLoginAttemptsByUserId.getId());
	}
	@Test
	public void deleteUserLoginAttempts() throws Exception{
		UserLoginAttempts userLoginAttempts = new UserLoginAttempts();
		userLoginAttempts.setId(1l);
		User user = new User();
		user.setId(3l);
		userLoginAttempts.setUser(user);
		UserLoginAttempts deleteUserLogin = userLoginAttemptsService.deleteUserLoginAttempts(userLoginAttempts);
		assertEquals(new Long(1),deleteUserLogin.getId());
	}
}

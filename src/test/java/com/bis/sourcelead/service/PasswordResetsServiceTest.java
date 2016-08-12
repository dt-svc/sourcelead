package com.bis.sourcelead.service;

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
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class PasswordResetsServiceTest {

	@Autowired
	PasswordResetsSevice passwordResetsSevice;

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationService.class);

	//@Test
	public void savePasswordResets() throws Exception {
		PasswordResets passwordResets = new PasswordResets();
		User user = new User();
		user.setId(3L);
		passwordResets.setUser(user);
		passwordResets.setEmail("info@hangonn.com");
		passwordResets.setVerificationCode("VERF");
		passwordResets.setStatus("Active");
		passwordResets.setCreatedDate(new Date());
		passwordResets.setCreatedBy("SourceLead");
		passwordResets.setUpdatedDate(new Date());
		passwordResets.setUpdatedBy("Sourcelead");
		passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
		logger.info("Saved Successfully..."+passwordResets.getEmail());
	}
	
	//@Test
	public void getPasswordResetsByUserId() throws Exception {
		PasswordResets passwordResets = new PasswordResets();
		User user = new User();
		user.setId(3L);
		passwordResets.setUser(user);
		passwordResets = passwordResetsSevice.getPasswordResetsByUserId(passwordResets.getUser().getId());
		logger.info("Retrieved Successfully..."+passwordResets.getEmail());
	}
	
	//@Test
	public void getPasswordResetsByVerificationCode() throws Exception {
		PasswordResets passwordResets = new PasswordResets();
		passwordResets.setVerificationCode("VERF");
		passwordResets = passwordResetsSevice.getPasswordResetsByVerificationCode(passwordResets.getVerificationCode());
		logger.info("Retrieved Successfully..."+passwordResets.getEmail());
	}
	
	@Test
	public void deletePasswordResets() throws Exception {
		PasswordResets passwordResets = new PasswordResets();
		passwordResets.setId(1L);
		User user = new User();
		user.setId(3L);
		passwordResets.setUser(user);
		passwordResets.setEmail("info@hangonn.com");
		passwordResets.setVerificationCode("VERF");
		passwordResets.setStatus("Active");
		passwordResets.setCreatedDate(new Date());
		passwordResets.setCreatedBy("SourceLead");
		passwordResets.setUpdatedDate(new Date());
		passwordResets.setUpdatedBy("Sourcelead");
		passwordResets = passwordResetsSevice.deletePasswordResets(passwordResets);
		logger.info("Deleted Successfully..."+passwordResets.getEmail());
	}
}

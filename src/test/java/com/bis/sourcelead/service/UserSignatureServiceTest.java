package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserSignature;

/**
 * This is a JUnit Test class for testing UserSignatureService methods
 * 
 * @author: Pavan jampana
 * @date: 21-Dec-2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class UserSignatureServiceTest {
	
	
	@Autowired
	UserSignatureService userSignatureService;
	
	@Autowired
	UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserSignatureServiceTest.class);
	
	//@Test
	public void saveUserSignature() throws Exception{
		
		UserSignature userSignature = new UserSignature();
		User user = new User();
		user.setId(7l);
		OrgLocations orgLoc = new OrgLocations();
		orgLoc.setId(3l);
		userSignature.setUserId(user);
		userSignature.setOrgLocationId(orgLoc);
		userSignature.setSignature("Thank You");
		userSignature.setSignatureName("Signature");
		userSignature.setStatus(1);
		userSignature.setCreatedBy("Aazam");
		userSignature.setCreatedDate(new Date());
		userSignature.setUpdatedBy("Majnu");
		userSignature.setUpdatedDate(new Date());
		userSignatureService.saveUserSignature(userSignature);
		logger.info("Saved Successfully...!!");
	}
	
	//@Test
	public void getUserSignatureListByUserIdAndLocationId() throws Exception{
		User user = new User();
		user.setId(7l);
		OrgLocations orgLoc = new OrgLocations();
		orgLoc.setId(3l);
		List<UserSignature> list = userSignatureService.getUserSignatureListByUserIdAndLocationId(user.getId(), orgLoc.getId());
		logger.info("Retrieved Successfully...!!"+list.size());
	}
	
	//@Test
	public void getUserSignatureById() throws Exception{
		Long userSignatureId = 1l;
		UserSignature userSignature = userSignatureService.getUserSignatureById(userSignatureId);
		logger.info("Retrieved Successfully...!!"+userSignature.getSignatureName());
	}
	
	@Test
	public void setUserSignatureDetails() throws Exception{
		JSONObject jsonObj = new JSONObject();
		 jsonObj.put("signatureName", "Best Regards");
		 jsonObj.put("signatureText", "this is used to regard something....");
		 User user = userService.getUserById(7l);
		 OrgLocations orgLoc = new OrgLocations();
		 orgLoc.setId(3l);
		 userSignatureService.setUserSignatureDetails(jsonObj, user, orgLoc);
		 logger.info("Saved Successfully...!!");
	}
}

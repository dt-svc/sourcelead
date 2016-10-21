package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.impl.UploadFileServiceImpl;
import com.bis.sourcelead.service.impl.UserServiceImpl;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles Junit Test Cases for Bulk imports
 * @author Rajashekar
 * @date 11th Aug 2016
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class UploadFileServiceTest {
//	uploadFile(MultipartFile multipartFile, OrgLocations orgLocations, User user, String isMailChecked) throws Exception;
	private static final Logger logger = LoggerFactory.getLogger(UploadFileServiceTest.class);
	
	@Configuration
	static class uploadFileServiceTestContextConfiguration {
		@Bean
		public UploadFileService uploadFileService() {
			return new UploadFileServiceImpl();
		}
	}
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	HttpServletRequest request;

	@Test
	public void getOrgCodeandLocationCodeTimeSheetNotification() throws Exception{
		
//		User user = new User();
//		user.setId(3L);
//		user.setFirstName("Rajashekar");
//		user.setLastName("Chelpuri");
//		user.setEmail("rajasekhar@businessintelli.com");
//		user.setUserState(Status.ACTIVE);
//		user.setUserType("Admin");
//		user.setUserName("rajasekhar@businessintelli.com");
//		user.setPassword("Pwd123");
//		user.setUserCode("6eHaBeO6");
//		user.setIsMailSent(Status.Y);
//		user.setCreatedBy("6eHaBeO6");
//		/*<--------------------User Entity End-------------------->*/
//		UserRole userRole = new UserRole();
//		Roles roles = new Roles();
//		roles.setId(1L);
//		roles.setRoleName("ROLE_ADMIN");
//		roles.setDisplayName("Admin");
//		OrgLocations orgLocation = new OrgLocations();
//		
//		Org org = new Org();
//		org.setId(2L);
//		org.setOrgName("Business Intelli solutions");
//		org.setUser(user);
//		org.setWebSite("businessintelli.com");
//		org.setOrgType("Company");
//		org.setOrgCode("tb8y");
//		org.setStatus(Status.ACTIVE);
//        orgLocation.setId(3L);
//        orgLocation.setOrg(org);
//        orgLocation.setUser(user);
//        orgLocation.setCountry("India");
//        orgLocation.setLocationCode("tb8yIN");
//        orgLocation.setStatus(Status.ACTIVE);
//        orgLocation.setCreatedBy("6eHaBeO6");
//        
//		userRole.setId(4L);
//		userRole.setRoles(roles);
//		userRole.setOrgLocations(orgLocation);
//		userRole.setIsPrimary(1);
//		userRole.setCreatedBy("6eHaBeO6");
//		Set<UserRole> userRoleSet = new HashSet<UserRole>();
//		user.setUserRole(userRoleSet);
		
		File file = new File("//root/Desktop/DF/Create users.xls");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String isMailChecked = null;
	  	uploadFileService.uploadFile(multipartFile, orgLocations, user, isMailChecked);
	}
	
}

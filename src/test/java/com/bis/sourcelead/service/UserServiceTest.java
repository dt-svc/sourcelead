package com.bis.sourcelead.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.UserCreation;
import com.bis.sourcelead.form.UserProfile;
import com.bis.sourcelead.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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
	
//	@Test
	public void addUser() throws Exception{
		User user = new User();
        user.setId(25L);
		user.setCreatedBy("ajith");
		user.setCreatedDate(new Date());
		user.setDocFile(new byte[4096]);
		user.setEmail("ajith@gmail.com");
		user.setFileContentType("open");
		user.setFirstName("matta");
		user.setIsMailSent(Status.Y);
		user.setJobTitle("developer");
		user.setLastName("reddy");
		user.setLocation("hyderabad");
		user.setMobilePhone("890876590");
		user.setPassword("bis@123");
		user.setStatus("active");
		user.setTerminateReason("fail");
		user.setUpdatedBy("srinivas");
		user.setUpdatedDate(new Date());
		user.setUserCode("132425");
		user.setUserName("shiva");
		user.setUserState(Status.ACTIVE);
		user.setUserType("employee");
		user.setWorkPhone("1233467890");
		
		userService.addUser(user);
		logger.info("add user details success fully inserted "+ user.getEmail());
	}
	
	@Test
	public void createUser() throws Exception{
		
		UserCreation userCreation = new UserCreation();
		//user.setId(1011l);
		userCreation.setEmail("prasad_test123@gmail.com");
		userCreation.setFirstName("Test");
		userCreation.setLastName("User");
		userCreation.setIsMailSent(Status.Y);
		userCreation.setPhoneNumber("890876590");
		userCreation.setPassword("bis@123");
		userCreation.setUserCode("132425");
		userCreation.setUserName("pavan");
		userCreation.setUserState(Status.ACTIVE);
		userCreation.setRoleType("Admin");
		
		userCreation.setEmployeeId("1001");
		userCreation.setRate("12345");
		userCreation.setRatePer("Hourly");
		userCreation.setDayHours("8");
		userCreation.setInvoiceFrequency("Monthly");
		userCreation.setDateofJoin("2016-08-17");
		userCreation.setProjectId("");
	
		 JSONObject jsonObj = new JSONObject(userCreation);
	        System.out.println( jsonObj );
		
		Long orgLocationsId = 5l;
		
		User user = new User();
		
		userService.createUser(jsonObj,user,orgLocationsId);
		logger.info(" success fully inserted");

		
	}
	
	//@Test
	public void sendEmail() throws Exception{
		User user = new User();
//		user.setId(10l);
		user.setCreatedBy("srinivas");
		user.setCreatedDate(new Date());
		user.setDocFile(new byte[4096]);
		user.setEmail("srinivas@gmail.com");
		user.setFileContentType("open");
		user.setFirstName("pradeeop");
		user.setIsMailSent(Status.Y);
		user.setJobTitle("tester");
		user.setLastName("reddy");
		user.setLocation("rangareddy");
		user.setMobilePhone("990876590");
		user.setPassword("bis@123");
		user.setStatus("active");
		user.setTerminateReason("invalid user");
		user.setUpdatedBy("rakesh");
		user.setUpdatedDate(new Date());
		user.setUserCode("132425");
		user.setUserName("anusha");
		user.setUserState(Status.ACTIVE);
		user.setUserType("admin");
		user.setWorkPhone("334678903");
		
		User loginUser =new User();
		//loginUser.setId(12l);
		String password="bis12334";
		
		userService.sendEmail(user,loginUser,password);
		logger.info(" success fully send email "+user.getStatus());
	}

	//@Test
	public void validateEmailandCandidate() throws Exception{
		String website = "www.bis.com";
		String email = "info@businessintelli.com"; 
		String sourceleadBLockedwebsites = "www.facebook.com";
		
		userService.validateEmailandCandidate(website,email,sourceleadBLockedwebsites);
		logger.info(" success fully validate email "+email);
	}
	
	//@Test
	public void  getUserById() throws Exception{
		
		Long Id=3l;
		User user = userService.getUserById(Id);
		user = userService.getUserById(Id);
		logger.info(" success fully getUserById " + user.getEmail());
		
		
	}
	//@Test
	public void findByEmail() throws Exception{
		
		User user = new User();
		String email = "ramu@tcs.com";
		
		user = userService.findByEmail(email);
		logger.info(" success fully findByEmail "+user.getFirstName());
	}
	
	//@Test
	public void getUsers() throws Exception{
		
		//User user = new User();
		Collection<User> user  = userService.getUsers();
		logger.info(" getUsers successfully updated "+user.size());
		
		
	}
	
	//@Test
	
	public void saveUserProfile() throws Exception{
		
		User user = new User();
//		user.setId(10l);
		user.setCreatedBy("srinivas");
		user.setCreatedDate(new Date());
		user.setDocFile(new byte[4096]);
		user.setEmail("srinivas@gmail.com");
		user.setFileContentType("open");
		user.setFirstName("pradeeop");
		user.setIsMailSent(Status.Y);
		user.setJobTitle("tester");
		user.setLastName("reddy");
		user.setLocation("rangareddy");
		user.setMobilePhone("990876590");
		user.setPassword("bis@123");
		user.setStatus("active");
		user.setTerminateReason("invalid user");
		user.setUpdatedBy("rakesh");
		user.setUpdatedDate(new Date());
		user.setUserCode("132425");
		user.setUserName("anusha");
		user.setUserState(Status.ACTIVE);
		user.setUserType("admin");
		user.setWorkPhone("334678903");
		
		UserProfile userProfile = new UserProfile();
		
		userProfile.setCurrentPassword("bis@123");
				MultipartFile multipartFile = getMultiPartFile();
		userProfile.setFile(multipartFile);
		userProfile.setFirstName("sourcelead");
	//	userProfile.setId(2);
		userProfile.setJobTitle("designer");
		userProfile.setLastName("timesheet");
		userProfile.setLocation("kolkatta");
		userProfile.setMobilePhone("54789562123");
		userProfile.setPassword("Bis@123");
		
	//	userService.saveUserProfile(userProfile,request,user);
		logger.info("saveUserProfile"+multipartFile.getOriginalFilename());
		
			
	}

	private MultipartFile getMultiPartFile() throws IOException {
		File file = new File("src/test/resources/input.txt");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
		return multipartFile;
	}
	
	/*//@Test
	public void getUsersByUserRoles() throws Exception{
		
		Collection<User> user = userService.getUsersByUserRoles();
		logger.info("getUsersByUserRoles " + user.size());
	}*/
	//@Test
	public void getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser() throws Exception{
		
		List<User> user = new ArrayList<User>();
		user = userService.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(1l,2l);
		logger.info("getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser " + user.size());
	}
	
	//@Test
	public void getUsersByUserRolesAndOrgLocationId() throws Exception{
		
		List<User> user = new ArrayList<User>();
		user = userService.getUsersByUserRolesAndOrgLocationId(1l);
		logger.info("getUsersByUserRolesAndOrgLocationId " + user.size());
	}
	
	//@Test
	public void getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser() throws Exception{
		
		List<User> user = new ArrayList<User>();
		user = userService.getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(1l,2l,1l);
		logger.info("getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser " + user.size());
	}
	
	//@Test
	public void getAllUserOrgCodeAndLocationCodeTimeSheetNotification() throws Exception{
		
		List<Employees> employee = new ArrayList<Employees>();
		employee = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(1l);
		logger.info("getAllUserOrgCodeAndLocationCodeTimeSheetNotification " + employee.size());
	}
}

package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.UserProfile;
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

	
	
	@Test
	public void addUser() throws Exception{
		User user = new User();

        user.setId(1L);

		user.setCreatedBy("ajith");
		user.setCreatedDate(new Date());
		user.setDocFile(new byte[4096]);
		user.setEmail("ajith@gmail.com");
		user.setFileContentType("open");
		user.setFirstName("matta");
		user.setIsMailSent(Status.N);
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
	
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("firstName", "Demo_final13");
		 jsonObj.put("lastName", "User");
		 jsonObj.put("email", "demomail123@gmail.com");
		 jsonObj.put("phoneNumber", "890876590");
		 jsonObj.put("roleType", "Admin");
		 
		 jsonObj.put("employeeId", "1");
		 jsonObj.put("rate", "12345");
		 jsonObj.put("ratePer", "Hourly");
		 jsonObj.put("dayHours", "8");
		 jsonObj.put("invoiceFrequency", "Monthly");
		 jsonObj.put("dateofJoin", "2016-08-17");
		 jsonObj.put("projectId", "");
		
		 Long orgLocationsId = 3l;
		 User user = new User();
		
		userService.createUser(jsonObj,user,orgLocationsId);
		logger.info(" success fully inserted");

		
	}
	
	@Test
	public void findByUserName() {	

		String username = "demomail123@gmail.com";
		
		User user = userService.findByUserName(username);
		logger.debug("In UserServiceTest class... " +user.getEmail() );

		
	}
	
	@Test
	public void sendEmail() throws Exception{
		User user = new User();
		user.setId(1l);
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

	@Test
	public void validateEmailandCandidate() throws Exception{
		String website = "www.bis.com";
		String email = "info@businessintelli.com"; 
		String sourceleadBLockedwebsites = "www.facebook.com";
		
		userService.validateEmailandCandidate(website,email,sourceleadBLockedwebsites);
		logger.info(" success fully validate email "+email);
	}
	
	@Test
	public void  getUserById() throws Exception{
		
		Long Id=3l;
		User user = userService.getUserById(Id);
		user = userService.getUserById(Id);
		logger.info(" success fully getUserById " + user.getEmail());
		
		
	}
	
	@Test
   public void findByEmail() throws Exception{
		User user = new User();
		String email = "demomail123@gmail.com";
		
		user = userService.findByEmail(email);
		logger.info(" success fully findByEmail "+user.getFirstName());
	}
	
	@Test
	public void getUsers() throws Exception{
		
		//User user = new User();
		Collection<User> user  = userService.getUsers();
		logger.info(" getUsers successfully updated "+user.size());
	}
	
	@Test
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
		logger.info("saveUserProfile"+multipartFile.getSize());
	}

	private MultipartFile getMultiPartFile() throws IOException {
		File file = new File("src/test/resources/input.txt");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
		return multipartFile;
	}
	
	@Test
	public void getUsersByUserRoles() throws Exception{
		Collection<User> user = userService.getUsersByUserRoles(2l);
		logger.info("getUsersByUserRoles " + user.size());
	}
	
	@Test
	public void getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser() throws Exception{
		List<User> user = new ArrayList<User>();
		user = userService.getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser(1l,2l);
		logger.info("getUsersByUserRolesAndOrgLocationIdAndNoCurrentUser " + user.size());
	}
	
	@Test
	public void getUsersByUserRolesAndOrgLocationId() throws Exception{
		List<User> user = new ArrayList<User>();
		user = userService.getUsersByUserRolesAndOrgLocationId(1l);
		logger.info("getUsersByUserRolesAndOrgLocationId " + user.size());
	}
	
	@Test
	public void getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser() throws Exception{
		List<User> user = new ArrayList<User>();
		user = userService.getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser(1l,2l,1l);
		logger.info("getUsersRoleCountByRoleIdAndLocationIdAndNotLoggedInUser " + user.size());
	}
	
	@Test
	public void getAllUserOrgCodeAndLocationCodeTimeSheetNotification() throws Exception{
		List<Employees> employee = new ArrayList<Employees>();
		employee = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(1l);
		logger.info("getAllUserOrgCodeAndLocationCodeTimeSheetNotification " + employee.size());
	}
	
	@Test
	public void sendEmailToClientOrVendor() throws Exception{
		Org loginOrg = new Org();
		loginOrg.setOrgName("Source Lead");
		User loginUser = new User();
		loginUser.setFirstName("Sourcelead");
		User user = new User();
		user.setFirstName("Raj");
		user.setLastName("Krishna");
		user.setEmail("pavan.jampana@businessintelli.com");
		userService.sendEmailToClientOrVendor(user, loginUser, "Pwd123", "Client", loginOrg);
		logger.info(" success fully sent email to client/Vendor");
	}
	
	@Test
	public void getUserByIdAndOrgLocationsId() throws Exception{
		User user = new User();
		user.setId(3L);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(3L);
		userService.getUserByIdAndOrgLocationsId(user.getId(), orgLocations.getId());
		logger.info("Retrieved user details are.."+user.getFirstName());
	}
	
	@Test
	public void storeUser() throws Exception{
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("firstName", "Demo_final13");
		 jsonObj.put("lastName", "User");
		 jsonObj.put("email", "demomail123@gmail.com");
		 jsonObj.put("phoneNumber", "890876590");
		 jsonObj.put("roleType", "Admin");
		 jsonObj.put("isMailSent", "Y");
		 jsonObj.put("employeeId", "123");
		 jsonObj.put("rate", "15000");
		 jsonObj.put("invoiceFrequency", "Monthly");
		 jsonObj.put("dateofJoin", "2016-08-17");
		 jsonObj.put("dayHours", "8");
		 jsonObj.put("projectId", "1");
		 
		 User users = new User();
		 users.setUserCode("sdf21d");
		 users.setFirstName("Raj");
		 users.setLastName("Tarun");
		 
		 OrgLocations orgLocations = new OrgLocations();
		 orgLocations.setId(3L);
		 
		 String sourceType = "CREATEUSER";
		 String responderType = "Client";
		 
		 Org loginOrg = new Org();
		 loginOrg.setOrgName("SourceLead");
		 
		userService.storeUser(jsonObj, users, orgLocations, sourceType, responderType, loginOrg);
		logger.info("User details Stored Successfully....");
	}
	
	@Test
	public void getOwnersByOrgLocationId() throws Exception{
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(3L);
		List<User> list = userService.getOwnersByOrgLocationId(orgLocations.getId());
		logger.info("Owner details retrieved Successfully...."+list.size());
	}
	
	@Test
	public void getManagersByUserRolesAndOrgLocationId() throws Exception{
		String roleType = "2";
		Long orgLocationsId = 3l;
		List<User> list = userService.getManagersByUserRolesAndOrgLocationId(roleType, orgLocationsId);
		logger.info("Manager details retrieved Successfully...."+list.size());
	}
	
	@Test
	public void getManagersByUserIdAndUserRolesAndOrgLocationId() throws Exception{
		Long userId = 3l;
		String roleType = "2";
		Long orgLocationsId = 3l;
		List<User> list = userService.getManagersByUserIdAndUserRolesAndOrgLocationId(userId,roleType,orgLocationsId);
		logger.info("Manager details retrieved Successfully...."+list.size());
	}
	
	@Test
	public void getUserDetailsByUserCode() throws Exception{
		User user = new User();
		user.setUserCode("0mkBMphb");
		user =userService.getUserDetailsByUserCode(user.getUserCode());
		logger.info("User details retrieved Successfully...."+user.getFirstName());
	}
}

package com.bis.sourcelead.service;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.Registration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class RegistrationServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceTest.class);
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	PasswordResetsSevice passwordResetsSevice;
	
	/*//@Test
	public void addRegistration() throws Exception{
		Registration registration = new Registration();
		registration.setCompanyName("tesla");
		registration.setCountry("INDIA");
		registration.setEmail("shiva@gmail.com");
		registration.setFirstName("atom");
//		registration.setId(1);
		registration.setLastName("shiva");
		registration.setPassword("PWD123");
		registration.setVerificationCode("12345");
		registration.setWebsite("www.tesla.com");
		User user = new User();
		user.setId(3L);
		registrationService.addRegistration(registration, user, " ", " ");
		logger.info("registration added successfully");
		
	}*/
	//@Test
	public void sendEmail() throws Exception{
		Registration registration = new Registration();
		registration.setEmail("shiva@gmail.com");
		registrationService.sendEmail(registration);
		logger.info("email added successfully");
	}
	//@Test
	public void sendResetPasswordEmail() throws Exception{
		User user = new User();
		user.setId(10l);
		user.setUserCode("pAw1ghoN");
		user.setEmail("shivayog@gmail.com");
		registrationService.sendResetPasswordEmail(user, "bis123");
		logger.info("sendResetPasswordEmail added successfully");
	}
	
	//@Test
	public void sendUpdatePasswordEmail() throws Exception
	{
		User user=new User();
		user.setId(123l);
		user.setCreatedBy("shiva");
		user.setCreatedDate(new Date());
		user.setEmail("shiva@gmail.com");
		user.setFileContentType("string");
		user.setFirstName("ajith");
		user.setIsMailSent(Status.ACTIVE);
		user.setJobTitle("developer");
		user.setLastName("kumar");
		user.setLocation("hyderabad");
		user.setMobilePhone("6546457646");
		user.setPassword("PWD123");
        File file = new File("C:\\testing.txt");
        byte[] bFile = new byte[(int) file.length()];
		user.setDocFile(bFile);
		Set<PasswordResets> passwordResets = new HashSet<PasswordResets>();
		PasswordResets ps = new PasswordResets();
		ps.setUser(user);
		passwordResets.add(ps);
		user.setPasswordResets(passwordResets);
		user.setStatus("true");
		user.setTerminateReason("qtyuoiuytre");
		user.setUpdatedBy("shiva");
		user.setUpdatedDate(new Date());
		user.setUserCode("heell");
		user.setUserName("yogi");
		Set<UserRole> userRole = new HashSet<UserRole>() ;
		UserRole ur= new UserRole();
		ur.setUser(user);
		userRole.add(ur);
		user.setUserRole(userRole);
		user.setUserState(Status.ACTIVE);
		user.setUserType("per");
		user.setWorkPhone("12345");
		user.getIsMailSent();
		registrationService.sendUpdatePasswordEmail(user);
		logger.info("sendUpdatePasswordEmail added successfully");
	}
	
		//@Test
		public void addPasswordResets() throws Exception{
			User user = new User();
			user.setId((long)1);
			String PasswordReset = registrationService.addPasswordResets(user);
			logger.info("addPasswordResets added successfully"+PasswordReset.toString());
		}
		
		//@Test
		public void resetPassword() throws Exception{
			//PasswordResets passwordResets,String password
			User user = new User();
			user.setUserCode("pAw1ghoN");
			user.setUserName("shivayog@gmail.com");
			PasswordResets passwordResets = new PasswordResets();
			passwordResets.setUser(user);
			registrationService.resetPassword(passwordResets,"bisbis");
			logger.info("resetPassword added successfully");
			
		}
		
		@Test
		public void deletePasswordResets() throws Exception{
			//PasswordResets passwordResets = new PasswordResets();
			//passwordResets.setId((long)1l);
			PasswordResets passwordResets = passwordResetsSevice.getPasswordResetsByUserId(3l);
			if(passwordResets != null){
				registrationService.deletePasswordResets(passwordResets);
				logger.info("deletePasswordResets added successfully"+passwordResets.getStatus());
			}
			
}
}
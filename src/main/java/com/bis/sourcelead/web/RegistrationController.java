package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Controller
@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class RegistrationController {
	
	 @Autowired
	 RegistrationService registrationService;
	
	 @Autowired
	 SourceleadAuthenticationProvider surceleadAuthenticationProvider;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 PasswordResetsSevice passwordResetsSevice;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	 
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	 
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.GET)
	   public String addCompanyRegistration(Registration registration) {
			
			try {
				registrationService.addRegistration(registration);
				
				//For User Authentication
				Authentication authentication = new UsernamePasswordAuthenticationToken(registration.getEmail(), registration.getPassword());
				authentication = surceleadAuthenticationProvider.authenticate(authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				//email to user
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(registration.getFirstName());
				 emailMessage.setLastName(registration.getLastName());
				 emailMessage.setToEmail(registration.getEmail());
				 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
				 emailMessage.setFromName("SourceLead");
				 
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("firstName", emailMessage.getFirstName());
				 globalVarsMap.put("lastName",emailMessage.getLastName());
				 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"'>Login</a>");
				
				 //setting the Recipient information
				 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
				 EmailRecipient emailRecipient = new EmailRecipient();
				 emailRecipient.setEmail(emailMessage.getToEmail());
				 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
				 emailRecipientList.add(emailRecipient);
				
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ORG_REGISTRATION,Constants.ENTITY_TYPE_EMAIL);
				
				 //covert subjet,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = null;
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				 
				 //calling the sendEmail method of emailScenerioUtil
				 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "home";
	     
	   }
	
	
	
	
	@RequestMapping(value = "/resetPasswordWithUserName" , method = RequestMethod.GET)
	   public String resetPasswordWithUserName(Registration registration) {
		
		User user = userService.findByUserName(registration.getEmail());
		if(null != user){
			try {
				PasswordResets passwordResets  = passwordResetsSevice.getPasswordResetsByUserId(user.getId());
				if(null == passwordResets){
					String verificationCode = registrationService.addPasswordResets(user);
					sendResetPasswordEmail(user,verificationCode);
				}else{
					passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
					passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
					sendResetPasswordEmail(user,passwordResets.getVerificationCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "valid";
		}else{
			return "Invalid";
		}
		
	}
	
	
	private void sendResetPasswordEmail(User user, String verificationCode) throws Exception {
		//email to user
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("verificationCode",verificationCode);
		 globalVarsMap.put("resetPasswordURL","<a href='"+sourceleadUrl+"/resetPassword"+"'>Reset Password</a>");
			
		//setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.FORGOT_PASSWORD,Constants.ENTITY_TYPE_EMAIL);
		
		//covert subjet,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = null;
		
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		
	}




	@RequestMapping(value = "/verifyResetPassword" , method = RequestMethod.GET)
	   public String verifyResetPassword(Registration registration) {
		
		//FetchType for User in PasswordResets is declared with Eager. So, User also loaded along with PasswordResets
		PasswordResets passwordResets  = passwordResetsSevice.getPasswordResetsByVerificationCode(registration.getVerificationCode());
		
		if(null != passwordResets){
			try {
				registrationService.resetPassword(passwordResets, registration.getPassword());
				registrationService.deletePasswordResets(passwordResets);
				
				//email to user
				User user = userService.findByUserName(passwordResets.getEmail());
				
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(user.getFirstName());
				 emailMessage.setLastName(user.getLastName());
				 emailMessage.setToEmail(user.getEmail());
				 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
				 emailMessage.setFromName("SourceLead");
				 
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("firstName", emailMessage.getFirstName());
				 globalVarsMap.put("lastName",emailMessage.getLastName());
					
				//setting the Recipient information
				 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
				 EmailRecipient emailRecipient = new EmailRecipient();
				 emailRecipient.setEmail(emailMessage.getToEmail());
				 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
				 emailRecipientList.add(emailRecipient);
				
				//getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATE_PASSWORD,Constants.ENTITY_TYPE_EMAIL);
			
				 //covert subjet,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = null;
				 
				//construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				 
				//calling the sendEmail method of emailScenerioUtil
				 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "login";
		}else{
			return "Invalid";
		}
		
	}
	
	
	
	
	}
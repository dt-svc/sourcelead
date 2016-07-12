package com.bis.sourcelead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.UserService;
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
	 
	 
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.POST)
	   public String addCompanyRegistration(Registration registration) {
			
			try {
				registrationService.addRegistration(registration);
				
				//For User Authentication
				Authentication authentication = new UsernamePasswordAuthenticationToken(registration.getEmail(), registration.getPassword());
				authentication = surceleadAuthenticationProvider.authenticate(authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				//email to user
				registrationService.sendEmail(registration);
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
					
					//email to user
					registrationService.sendResetPasswordEmail(user,verificationCode);
				}else{
					passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
					passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
					
					//email to user
					registrationService.sendResetPasswordEmail(user,passwordResets.getVerificationCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "valid";
		}else{
			return "Invalid";
		}
		
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
				registrationService.sendUpdatePasswordEmail(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "login";
		}else{
			return "Invalid";
		}
		
	}
	
	
	
	
	}
package com.bis.sourcelead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.RegistrationService;

@Controller
@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;
	
	 @Autowired
	 SourceleadAuthenticationProvider surceleadAuthenticationProvider;
	 
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.GET)
	   public String addCompanyRegistration(Registration registration) {
			
			try {
				registrationService.addRegistration(registration);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(registration.getEmail(), registration.getPassword());
				authentication = surceleadAuthenticationProvider.authenticate(authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "home";
	     
	   }
	}
package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.UserService;

@Controller
@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;
	
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 SourceleadAuthenticationProvider surceleadAuthenticationProvider;
	 
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.GET)
	   public ModelAndView addCompanyRegistration(Registration registration) {
			
			try {
				registrationService.addRegistration(registration);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(registration.getEmail(), registration.getPassword());
				authentication = surceleadAuthenticationProvider.authenticate(authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return new ModelAndView("redirect:/home");
	     
	   }
	
	
}

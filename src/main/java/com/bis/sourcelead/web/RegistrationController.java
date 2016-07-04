package com.bis.sourcelead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.RegistrationService;

@Controller
@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;
	
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.GET)
	   public @ResponseBody  void addCompanyRegistration(Registration registration) {
			
			try {
				registrationService.addRegistration(registration);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     
	   }
	

}

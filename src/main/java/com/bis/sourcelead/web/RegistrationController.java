package com.bis.sourcelead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.OrgService;

@Controller
@RestController
public class RegistrationController {
	
	@Autowired
	private OrgService orgService;
	
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.GET)
	   public @ResponseBody  void addCompanyRegistration(Registration registration) {
	     
	      System.out.println(registration.getFirstName());
	   }

}

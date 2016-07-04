package com.bis.sourcelead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;

@Controller
@RestController
public class RegistrationController {
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.GET)
	   public @ResponseBody  void addCompanyRegistration(Registration registration) {
		
			User user = new User();
			user.setFirstName(registration.getFirstName());
			user.setLastName(registration.getLastName());
			user.setEmail(registration.getEmail());
			user.setUserName(registration.getEmail());
			user.setPassword(sourceleadPasswordEncoder.encode(registration.getPassword()));
		
	     
	   }

}

package com.bis.sourcelead.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	
	@RequestMapping(value = "/validateOrgWebsite", method = RequestMethod.GET)
	    public @ResponseBody String validateOrgWebsite(
	    		@RequestParam(value="website", required=false) String website) {
		
			String result = "";
			
			if(!website.startsWith("http") && !website.startsWith("https")){
				 website = "http://" + website;
		    }        
		    URL netUrl = null;
			try {
				netUrl = new URL(website);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList websiteList = new ArrayList();
		    String host = netUrl.getHost();
		    if(host.startsWith("www")){
		        host = host.substring("www".length()+1);
		    }
			   String orgInfo = orgService.websiteValidation(host);
			
			if (orgInfo != null) {
				websiteList.add("true");
			}
			else{
				websiteList.add("false");
			}
			String[] temp = (String[]) websiteList.toArray(new String[] {});
			JSONArray mJSONArray = new JSONArray(Arrays.asList(temp));
			result = mJSONArray.toString();
			return result;
			
	    }
		
			
		
	     

}

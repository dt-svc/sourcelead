package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dao.CustomerDao;
import com.bis.sourcelead.entity.Customer;
import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.TestEhCacheService;

/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public LoginService loginService;
	
	@Autowired
	TestEhCacheService testEhCacheService;
	
	@Autowired
	CustomerDao customerDao;

   @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

			ModelAndView model = new ModelAndView();
			model.setViewName("login");
			return model;

	}
   
   
   @RequestMapping(value = { "/home"}, method = RequestMethod.GET)
  	public ModelAndView homePage() {

  			ModelAndView model = new ModelAndView();
  			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  			model.setViewName("home");
  			return model;

  	}
   
   

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;

	}
	
	
	 @RequestMapping(value = { "/ehCacheTest"}, method = RequestMethod.GET)
	  	public ModelAndView ehCachePage() {
	  			ModelAndView model = new ModelAndView();
	  			model.setViewName("ehCacheTest");
	  			return model;

	  	}
	 
	 
	 @RequestMapping(value = "/click1", method = RequestMethod.GET)
	    public ModelAndView handleRequest(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 testEhCacheService.testSecondLevelCache2();
	     return null;     
	    }
	 
	 
	 @RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
	    public ModelAndView handleRequest2(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 Customer customer = customerDao.getCustomer(1);
		 ModelAndView model = new ModelAndView("customerForm");
		 model.addObject("customer", customer);  
		 return model;
		 
		 
	    }
	 
	 
	 @RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	 public ModelAndView saveCustomer(@ModelAttribute Customer customer) {
	     customerDao.saveOrUpdateCustomer(customer);
	     ModelAndView model = new ModelAndView();
		 model.setViewName("ehCacheTest");
		 return model;
	 }
	

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			model.addObject("username", auth.getPrincipal());

		}

		model.setViewName("403");
		return model;

	}
}

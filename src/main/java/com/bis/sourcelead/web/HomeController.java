package com.bis.sourcelead.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.UserService;



/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Controller
@RestController
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public LoginService loginService;
	

	@Autowired
	private OrgService orgService;
	
	@Autowired
	private UserService userService;
	
	@Value("${jdbc.username}")
	private String user;
	
	@Value("${sourceLeadBlockedSocialDomains}")
	private String sourceLeadBlockedSocialDomains;
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;
	
	
	@PostConstruct
	public void init() {
		System.out.println(" *** HomeController init with: " + applicationContext);
	}

   @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

			ModelAndView model = new ModelAndView();
			model.setViewName("login");
			return model;

	}
   
   
   
   @RequestMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView model = new ModelAndView();
		model.setViewName("registration");

		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("sourceLeadBlockedSocialDomains", sourceLeadBlockedSocialDomains);
		return model;

	}
   
   
   
   @RequestMapping(value = "/forgotPassword")
 	public ModelAndView forgotPassword() {
 			ModelAndView model = new ModelAndView();
 			model.setViewName("forgotPassword");
 			return model;
 	}
   
   
   @RequestMapping(value = "/resetPassword")
  	public ModelAndView resetPassword() {
  			ModelAndView model = new ModelAndView();
  			model.setViewName("resetPassword");
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
	
	@RequestMapping(value = "/rest/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name) {
		return name;
	}

	//fileUpload
	
	 @RequestMapping(value = "/uploadForm")
		public ModelAndView fileUploadForm() {
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
}

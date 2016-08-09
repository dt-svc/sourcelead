package com.bis.sourcelead.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;


/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Controller
@PropertySource("${propertyLocation:app.properties}")
@ControllerAdvice
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
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@PostConstruct
	public void init() {
		System.out.println(" *** HomeController init with: " + applicationContext);
	}

	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public String defaultPage() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 if(auth != null && auth.getPrincipal() != null){
				return "redirect:/dashboard";
		 }
		 else{
				return "redirect:/login";
		 }
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String customExceptionPage(HttpRequestMethodNotSupportedException e){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 if(auth != null && auth.getPrincipal() != null){
				return "redirect:/dashboard";
		 }
		 else{
				return "redirect:/login";
		 }
	}
   
   
   /*@RequestMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView model = new ModelAndView();
		model.setViewName("registration");

		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("sourceLeadBlockedSocialDomains", sourceLeadBlockedSocialDomains);
		return model;

	}*/
   
   
   
   @RequestMapping(value = "/forgotPassword")
 	public ModelAndView forgotPassword() {
 			ModelAndView model = new ModelAndView();
 			model.setViewName("forgotPassword");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
   
   
   @RequestMapping(value = "/resetPassword")
  	public ModelAndView resetPassword(HttpServletRequest request) {
  			ModelAndView model = new ModelAndView();
  			model.setViewName("resetPassword");
  			model.addObject("sourceleadUrl", sourceleadUrl);
  			
  			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPasswordMsg");
  			if(!isNullOrEmpty(msg)){
  				model.addObject("msg", msg);
  				SourceLeadSessionManager.removeSessionAttribute(request, "resetPasswordMsg");
  			}
  			
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

	@RequestMapping(value = "/login",  method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginPage(@RequestParam(value = "logout", required = false) String logout,HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.getPrincipal() != null && !(auth instanceof AnonymousAuthenticationToken) && !StringUtils.isNotEmpty(logout)){
			return new ModelAndView("redirect:/dashboard");
	    }
		 else{
			 if (logout != null) {
					model.addObject("msg", "You've been logged out successfully.");
			 }
			 
			 String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPasswordSucessfulMsg");
	  			if(!isNullOrEmpty(msg)){
	  				model.addObject("msg", msg);
	  				SourceLeadSessionManager.removeSessionAttribute(request, "resetPasswordSucessfulMsg");
	  			}
			 model.setViewName("login");
		    	 
		 }
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

	/**
	 * Returns File upload jsp
	 * @param request
	 * @return ModelAndView
	 */
	
	 @RequestMapping(value = "/uploadForm")
		public ModelAndView fileUploadForm(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Users);
			
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
}

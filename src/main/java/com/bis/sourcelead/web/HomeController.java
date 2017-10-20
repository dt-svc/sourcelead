package com.bis.sourcelead.web;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
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
	 private NotificationsService notificationsService;
	 
	 @Autowired
	 OrgLocationService orgLocationService;
	 
	 @Autowired
	 OrgSpocService orgSpocService;
	 
	@Value("${jdbc.username}")
	private String user;
	
	@Value("${sourceLeadBlockedSocialDomains}")
	private String sourceLeadBlockedSocialDomains;
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/*@Autowired
	private ApplicationContext applicationContext;*/
	
	@Autowired
	private DataSource dataSource;
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
	@PostConstruct
	public void init() {
		logger.debug(" *** HomeController init with: " );
	}

/*	@RequestMapping(value = { "/**"},  method = { RequestMethod.GET, RequestMethod.POST })
	public String defaultPage(HttpServletRequest request) {
		try {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 		 if(auth != null && auth.getPrincipal() != null){
				return "redirect:/dashboard";
		 }
		 else{
				return "redirect:/login";
		 } 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/login"; 
	}
	*/
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String customExceptionPage(HttpRequestMethodNotSupportedException exception){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(exception));
		if(auth != null && auth.getPrincipal() != null){
				return "redirect:/dashboard";
		 }
		 else{
				return "redirect:/login";
		 }
	}
	
	
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
	public ModelAndView loginPage(@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
			@RequestParam(value = "referUUID", required = false) String referUUID,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.getPrincipal() != null && !(auth instanceof AnonymousAuthenticationToken) && !StringUtils.isNotEmpty(logout)){
			if(isNullOrEmpty(requirementUUID)){
				return new ModelAndView("redirect:/dashboard");
			}else{
				StringBuilder redirectUrl = new StringBuilder();
				try {
					response.sendRedirect(redirectUrl.append("/sourcelead/professionalRequirements?requirementUUID=").append(requirementUUID).toString());
				} catch (IOException e) {
					
				}
			}
			
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
	  			
	  			if(!isNullOrEmpty(requirementUUID)){
	  				 model.addObject("requirementUUID", requirementUUID);
	  			}
	  			
	  			if(!isNullOrEmpty(referUUID)){
	  				 model.addObject("referUUID", referUUID);
	  			}
  			
			 model.setViewName("login");
		    	 
		 }
		return model;
	}
	
	@RequestMapping(value = "/Inlogin",  method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginPageClient(@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "spocUUID", required = false) String spocUUID,
			@RequestParam(value = "UUID", required = false) String UUID
			,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		 model.addObject("spocUUID",spocUUID);
		 model.addObject("UUID",UUID);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null && auth.getPrincipal() != null && !(auth instanceof AnonymousAuthenticationToken) && !StringUtils.isNotEmpty(logout)){
			 if(null != UUID && !"".equalsIgnoreCase(UUID) && null != spocUUID && !"".equalsIgnoreCase(spocUUID)){
				 
				 OrgSpoc orgSpoc = orgSpocService.getSpocBySpocUUID(spocUUID);
				 
				 if(null != orgSpoc && !auth.getPrincipal().equals(orgSpoc.getOwnerUser().getEmail()) && (auth.getPrincipal().equals(orgSpoc.getClientUser().getEmail()) || auth.getPrincipal().equals(orgSpoc.getVendorUser().getEmail()))){
					 orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
					 orgSpocService.addOrgSpoc(orgSpoc);
					 if(null != orgSpoc.getOwnerUser() && null != orgSpoc.getClientUser() &&  orgSpoc.getOwnerUser().getId().longValue() == orgSpoc.getClientUser().getId().longValue()){
						  OrgLocations vendorLocation = orgLocationService.getOrgLocations(orgSpoc.getVendorLocationId().getId());
						  OrgLocations clientLocation = orgLocationService.getOrgLocations(orgSpoc.getClientLocationId().getId());
						  StringBuilder stringBuilder = new StringBuilder();
						  stringBuilder.append("You accepted ").append(orgSpoc.getOwnerUser().getFirstName()).append(" (").append(clientLocation.getOrgLocationName()).append(") ").append("invitation successfully").append(" in ").append(vendorLocation.getOrgLocationName());
						  model.addObject("msg", stringBuilder);
					 }else if(null != orgSpoc.getOwnerUser() && null != orgSpoc.getVendorUser() &&  orgSpoc.getOwnerUser().getId().longValue() == orgSpoc.getVendorUser().getId().longValue()){
						  OrgLocations clinetLocation = orgLocationService.getOrgLocations(orgSpoc.getClientLocationId().getId());
						  OrgLocations vendorLocation = orgLocationService.getOrgLocations(orgSpoc.getVendorLocationId().getId());
						  StringBuilder stringBuilder = new StringBuilder();
						  stringBuilder.append("You accepted ").append(orgSpoc.getOwnerUser().getFirstName()).append(" (").append(vendorLocation.getOrgLocationName()).append(") ").append("invitation successfully").append(" in ").append(clinetLocation.getOrgLocationName());
						  model.addObject("msg", stringBuilder);
					 }
					 
					 Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
					 if(null != notifications){
						 notifications.setUserNotificationStatus("Accepted");
						 notificationsService.saveNotification(notifications);
					 }
				 }
			 }
				model.setViewName("/dashboard");
				model.addObject("sourceleadUrl", sourceleadUrl);
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
	
	

	/**
	 * Returns File upload jsp
	 * @param request
	 * @return ModelAndView
	 */
	@PreAuthorize("hasRole('ROLE_USER_BULK_IMPORTS')")
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
	 
	    @RequestMapping(value = "/errorPage",method = RequestMethod.GET)
		public ModelAndView displayErrorPage() {
			ModelAndView model = new ModelAndView();
			model.setViewName("errorPage");
			return model;

		}
}

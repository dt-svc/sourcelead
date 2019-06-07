package com.bis.sourcelead.web;

import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

import edu.emory.mathcs.backport.java.util.LinkedList;

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
	RequirementService requirementService;

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	UserService userService;

	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	public InviteCompanyService inviteCompanyService;

	@Value("${jdbc.username}")
	private String user;

	@Value("${sourceLeadBlockedSocialDomains}")
	private String sourceLeadBlockedSocialDomains;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	/*
	 * @Autowired private ApplicationContext applicationContext;
	 */

	@Autowired
	private DataSource dataSource;

	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	@PostConstruct
	public void init() {
		logger.debug(" *** HomeController init with: ");
	}

	@RequestMapping(value = {"/app", "/app/**"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String defaultPage(HttpServletRequest request) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.getPrincipal() != null) {
				return "redirect:/app/dashboard";
			} else {
				return "redirect:/login";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/login";
	}
	 

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String customExceptionPage(HttpRequestMethodNotSupportedException exception) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(exception));
		if (auth != null && auth.getPrincipal() != null) {
			return "redirect:/app/dashboard";
		} else {
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

	@RequestMapping(value = {"/resetPassword", "/app/resetPassword"})
	public ModelAndView resetPassword(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("resetPassword");
		model.addObject("sourceleadUrl", sourceleadUrl);

		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPasswordMsg");
		if (!isNullOrEmpty(msg)) {
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
	
	
	
	@RequestMapping(value = {"/demoRequest"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView sourceleadDemoRequest() {

		ModelAndView model = new ModelAndView();
		model.addObject("demoRequest", "demoRequest");
		model.setViewName("static-web/index");

		return model;

	}

	@RequestMapping(value = {"/login"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginPage(@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "referUUID", required = false) String referUUID,
			@RequestParam(value = "allowed", required = false) String allowed, 
			@RequestParam(value = "redirectURL", required = false) String redirectURL,
			@RequestParam(value = "redirectOrgLocationId", required = false) Long redirectOrgLocationId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();

		if(redirectURL != null && !redirectURL.isEmpty()) {
			request.getSession().setAttribute("redirectURL", redirectURL);
			request.getSession().setAttribute("redirectOrgLocationId", redirectOrgLocationId);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null && !(auth instanceof AnonymousAuthenticationToken)
				&& !StringUtils.isNotEmpty(logout)) {
			if("false".equalsIgnoreCase(allowed)) {
				return new ModelAndView("redirect:/logout?allowed=false");
			}
			if (isNullOrEmpty(requirementUUID)) {
				return new ModelAndView("redirect:/app/dashboard");
			} else {
				StringBuilder redirectUrl = new StringBuilder();
				try {
					response.sendRedirect(redirectUrl.append("/app/professionalRequirements?requirementUUID=")
							.append(requirementUUID).toString());
				} catch (IOException e) {

				}
			}

		} else {
			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}

			if (token != null) {
				User user = userService.getUserByVerficationUUID(token);
				model.addObject("username", user.getUserName());
			}

			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPasswordSucessfulMsg");
			if (!isNullOrEmpty(msg)) {
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "resetPasswordSucessfulMsg");
			}

			if (!isNullOrEmpty(requirementUUID)) {
				model.addObject("requirementUUID", requirementUUID);
			}

			if (!isNullOrEmpty(referUUID)) {
				model.addObject("referUUID", referUUID);
			}
			if("false".equalsIgnoreCase(allowed))
				model.addObject("msg", "You are not allowed to access from current location");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = "/Inlogin", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginPageClient(@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "spocUUID", required = false) String spocUUID,
			@RequestParam(value = "UUID", required = false) String UUID, HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("spocUUID", spocUUID);
		model.addObject("UUID", UUID);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null && !(auth instanceof AnonymousAuthenticationToken)
				&& !StringUtils.isNotEmpty(logout)) {
			if (null != UUID && !"".equalsIgnoreCase(UUID) && null != spocUUID && !"".equalsIgnoreCase(spocUUID)) {

				OrgSpoc orgSpoc = orgSpocService.getSpocBySpocUUID(spocUUID);

				if (null != orgSpoc && !auth.getPrincipal().equals(orgSpoc.getOwnerUser().getEmail())
						&& (auth.getPrincipal().equals(orgSpoc.getClientUser().getEmail())
								|| auth.getPrincipal().equals(orgSpoc.getVendorUser().getEmail()))) {
					orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
					orgSpocService.addOrgSpoc(orgSpoc);
					if (null != orgSpoc.getOwnerUser() && null != orgSpoc.getClientUser() && orgSpoc.getOwnerUser()
							.getId().longValue() == orgSpoc.getClientUser().getId().longValue()) {
						OrgLocations vendorLocation = orgLocationService
								.getOrgLocations(orgSpoc.getVendorLocationId().getId());
						OrgLocations clientLocation = orgLocationService
								.getOrgLocations(orgSpoc.getClientLocationId().getId());
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("You accepted ").append(orgSpoc.getOwnerUser().getFirstName()).append(" (")
								.append(clientLocation.getOrgLocationName()).append(") ")
								.append("invitation successfully").append(" in ")
								.append(vendorLocation.getOrgLocationName());
						model.addObject("msg", stringBuilder);
					} else if (null != orgSpoc.getOwnerUser() && null != orgSpoc.getVendorUser() && orgSpoc
							.getOwnerUser().getId().longValue() == orgSpoc.getVendorUser().getId().longValue()) {
						OrgLocations clinetLocation = orgLocationService
								.getOrgLocations(orgSpoc.getClientLocationId().getId());
						OrgLocations vendorLocation = orgLocationService
								.getOrgLocations(orgSpoc.getVendorLocationId().getId());
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("You accepted ").append(orgSpoc.getOwnerUser().getFirstName()).append(" (")
								.append(vendorLocation.getOrgLocationName()).append(") ")
								.append("invitation successfully").append(" in ")
								.append(clinetLocation.getOrgLocationName());
						model.addObject("msg", stringBuilder);
					}

					Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
					if (null != notifications) {
						notifications.setUserNotificationStatus("Accepted");
						notificationsService.saveNotification(notifications);
					}
				}
			}
			model.setViewName("/dashboard");
			model.addObject("sourceleadUrl", sourceleadUrl);
		} else {
			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}

			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPasswordSucessfulMsg");
			if (!isNullOrEmpty(msg)) {
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "resetPasswordSucessfulMsg");
			}
			model.setViewName("login");

		}
		return model;
	}

	
	/*@RequestMapping(value = { "/ehCacheTest" ,  "/app/ehCacheTest"}, method = RequestMethod.GET)
	public ModelAndView ehCachePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("ehCacheTest");
		return model;

	}*/

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
	 * 
	 * @param request
	 * @return ModelAndView
	 */

	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public ModelAndView displayErrorPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errorPage");
		return model;

	}

	/**
	 * Returns File upload jsp
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/sURL/{shortURL}")
	public String redirectShortUrl(@PathVariable String shortURL, HttpServletRequest request) throws Exception {
		Requirement r = requirementService.getRequirementByShortURL(shortURL);
		//Candidate c = candidateService.getCandidateByShortURL(shortURL);
		if (r != null)
			return "redirect:/requirementsSummary.html?requirementsUUID="+shortURL;
		return "redirect:/";

	}
	
	@RequestMapping(value = "/getScheduleStatus")
	public @ResponseBody ScheduleMaintenanceDTO getScheduleStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	ScheduleMaintenanceDTO dto = inviteCompanyService.getScheduleStatus();
    	if(dto != null) {
    		String dateString = getConvertedStartEndDateString(dto.getStartDate(), dto.getEndDate());
    		String maintenanceText = "Please note that we will be experiencing server downtime "+dateString+" due to "+dto.getScheduleMessage();
    		dto.setMainMessage(maintenanceText);
    	}
    	return dto;
	}
    
    
    public String getConvertedStartEndDateString(String startDate, String endDate) throws NumberFormatException, Exception {
    	String startTime = getTime(startDate);
    	String endTime = getTime(endDate);
    	/*DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		String parsedDate = dateFormat.format(dateFormat.parse(scheduleDate));
		String dateTime = parsedDate+" "+scheduleStartTime;*/
		DateFormat dateFormat1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		TimeZone timezone = now.getTimeZone();
		Date startDateWithTime = dateFormat1.parse(startDate);
		Date endDateWithTime = dateFormat1.parse(endDate);
		DateFormat df1 = new SimpleDateFormat("dd MMM yyyy");
		DateFormat df3 = new SimpleDateFormat("z");
        df1.setTimeZone(TimeZone.getTimeZone(timezone.getID()));
        String startFormatedDate = df1.format(startDateWithTime);
        String endFormatedDate = df1.format(endDateWithTime);
        df3.setTimeZone(TimeZone.getTimeZone(timezone.getID()));
        String IST3 = df3.format(startDateWithTime);
        String convertedDateString = null;
        if(startFormatedDate.equals(endFormatedDate)) {
        	convertedDateString = "on the "+startFormatedDate+" from "+startTime+" to "+endTime+" "+IST3;
        }else {
        	convertedDateString = "from "+startFormatedDate+" "+startTime+" to "+endFormatedDate+" "+endTime+" "+IST3;
        }
    	return convertedDateString;
    }
    
    public String getTime(String scheduleDate) throws NumberFormatException, Exception {
    	//DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		//String parsedDate = dateFormat.format(dateFormat.parse(scheduleDate));
		//String dateTime = parsedDate+" "+scheduleTime;
		DateFormat dateFormat1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		TimeZone timezone = now.getTimeZone();
		Date dateWithTime = dateFormat1.parse(scheduleDate);
		DateFormat df = new SimpleDateFormat("hh:mm a");
        df.setTimeZone(TimeZone.getTimeZone(timezone.getID()));
        String time = df.format(dateWithTime);
    	return time;
    }
    
	@RequestMapping(value="/getAvailableFonts")
	@ResponseBody
	public List<String> getAvailableSystemFonts(HttpServletRequest request) {
		List<String> fontsList = new LinkedList();
		try {
			GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
			for(String font : g.getAvailableFontFamilyNames()) {
				fontsList.add(font);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fontsList;
	}

}

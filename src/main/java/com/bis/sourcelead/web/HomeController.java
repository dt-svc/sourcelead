package com.bis.sourcelead.web;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.service.LoginService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;

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
	@Value("${sourceleadUrl}")
	private String sourceleadLoginUrl;
	
	protected boolean isNullOrEmpty(String str)
	{
		return str == null || "".equals(str.trim());
	}
	
	@PostConstruct
	public void init()
	{
		logger.debug(" *** HomeController init with: ");
	}
	
	@RequestMapping(value = { "/", "/**" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String defaultPage(HttpServletRequest request)
	{
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.getPrincipal() != null) {
				return "redirect:" + sourceleadUrl + "/dashboard";
			}
			else {
				return "redirect:" + sourceleadLoginUrl;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:" + sourceleadLoginUrl;
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String customExceptionPage(HttpRequestMethodNotSupportedException exception)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(exception));
		if (auth != null && auth.getPrincipal() != null) {
			return "redirect:" + sourceleadUrl + "/dashboard";
		}
		else {
			return "redirect:" + sourceleadLoginUrl;
		}
	}
	
	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied()
	{
		ModelAndView model = new ModelAndView();
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			model.addObject("username", auth.getPrincipal());
		}
		model.setViewName("403");
		return model;
	}
	
	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public ModelAndView displayErrorPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("errorPage");
		return model;
	}
	
	@RequestMapping(value = {"/login"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String loginPage(HttpServletRequest request,HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			return "redirect:" + sourceleadUrl + "/dashboard";
		}
		else {
			return "redirect:" + sourceleadLoginUrl;
		}	
	}
}
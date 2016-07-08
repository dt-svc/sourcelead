package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class LoginSessionController {
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/home" , method = RequestMethod.GET)
	   public ModelAndView loginSession(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) {
		
		
		User user = userService.findByUserName(authentication.getName());
		setSessionUserAttributes(user,request);
		
		try {
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			OrgLocations orgLocations = userRole.getOrgLocations();
			setSessionOrgLocationAttributes(orgLocations,request);
			Org org = orgLocations.getOrg();
			setSessionOrgAttributes(org,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView model = new ModelAndView();
		model.setViewName("home");
	    return model;
		
	}
	
	
	private void setSessionUserAttributes(User user, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "userCode", user.getUserCode());
		SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());
	}
	
	private void setSessionOrgLocationAttributes(OrgLocations orgLocations, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
		SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
	}
	
	private void setSessionOrgAttributes(Org org, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		SourceLeadSessionManager.setSessionAttribute(request, "orgCode", org.getOrgCode());
	}
	
	
}

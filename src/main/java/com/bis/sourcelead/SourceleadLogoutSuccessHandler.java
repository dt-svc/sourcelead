package com.bis.sourcelead;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dao.ActionHistoryDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;

/**
 * Custom AuthenticationSuccessHandler
 * 
 * @author: Naresh Bolisetty
 * @date: 07-Nov-2017
 */
@Component
public class SourceleadLogoutSuccessHandler implements LogoutSuccessHandler{
	
	@Autowired
	ActionHistoryDao actionHistoryDao;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		String userSessionId = request.getParameter("userSessionId");
		String orgLocationId = request.getParameter("orgLocationId");
		String allowed = request.getParameter("allowed");
		
		if(StringUtils.isNotEmpty(userSessionId)){
			String actionUUID = null;
			if(StringUtils.isNotEmpty(orgLocationId)){
				UserLoginLogoutSession userLoginLogoutSession = userLoginLogoutSessionService.getUserLoginLogoutSessionById(Long.parseLong(userSessionId));
				actionUUID = userLoginLogoutSession.getActionUUID();
				OrgLocations orgLocations;
				try {
					orgLocations = orgLocationService.getOrgLocations(Long.parseLong(orgLocationId));
					actionHistoryDao.saveActionHistory("getActionHistoryList",userLoginLogoutSession,orgLocations,"");
				}  catch (Exception e) {}
			}
			response.setStatus(HttpServletResponse.SC_OK);
			response.sendRedirect(request.getContextPath()+"/app/getActionHistoryList/"+actionUUID);
		}
		else{
			response.setStatus(HttpServletResponse.SC_OK);
			response.sendRedirect(request.getContextPath()+"/login"+(StringUtils.isNotEmpty(allowed) ? "?allowed=false" : ""));
		}
		
		
		
	}
	
}
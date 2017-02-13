package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ProfessionalProfileController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	
	 @PreAuthorize("hasRole('ROLE_CROWDSOURCING_PROFILE')")
	 @RequestMapping(value = "/professionalProfile")
		public ModelAndView professionalProfile(HttpServletRequest request) throws Exception {
		    
		        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				ModelAndView model = new ModelAndView();
				
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Profile);
				
				request.getSession().setAttribute("sessionData",sessionData);
				
				model.setViewName("professional/profile");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
}

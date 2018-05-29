package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
//@RequestMapping("/app")
public class EmailNotificationConfigureController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	EmailScenarioConfigurationService emailconService;

	@RequestMapping(value = "/app/emailNontificationConfigure")
	public ModelAndView emailNontificationConfigure(HttpServletRequest request) throws Exception
	{
		
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		 EmailScenarioConfiguration emailSceconf= emailconService.getEmailConfigOrgLocId(orgLocation.getId());
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("emailSceconf", emailSceconf);
		 model.setViewName("/emailNontificationConfigure");
		 
		 return model;
	}
	
}

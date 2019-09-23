package com.bis.sourcelead.web.reportsController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")

public class ApplicantsBackoutReportController {
	@Autowired
	ReportsUtil reportsUtil; 
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value = "/applicantsBackoutReport")
	public ModelAndView applicantBackout(HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
	  	 List<RequirementDTO> requirementsList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
	  	 
		 ModelAndView model = new ModelAndView();
		 model.addObject("requirementsList",requirementsList);
		 model.setViewName("reports/applicantsBackoutReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	@RequestMapping(value = "/applicantBackoutReportsList")
	public ModelAndView applicantBackoutReports(HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
	  	 List<RequirementDTO> requirementsList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
	  	 
		 ModelAndView model = new ModelAndView();
		 model.addObject("requirementsList",requirementsList);
		 model.setViewName("reports/applicantsBackoutReportList");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	

}

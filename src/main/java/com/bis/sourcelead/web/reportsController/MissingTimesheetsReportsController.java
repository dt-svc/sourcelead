package com.bis.sourcelead.web.reportsController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class MissingTimesheetsReportsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	ProjectsService projectsService;
	
	@RequestMapping(value = "/missingTimesheetsReport")
	public ModelAndView missingTimesheetsReport(HttpServletRequest request) throws Exception
	{
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 List<Projects> projectList = projectsService.getAllProjectByOrgLocationsId(orgLocation.getId());
		 model.addObject("projectList", projectList);
		 model.setViewName("reports/missingTimesheetsReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	@RequestMapping(value = "/pendingForApprovalReport")
	public ModelAndView pendingForApprovalReport(HttpServletRequest request) throws Exception
	{
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 List<Projects> projectList = projectsService.getAllProjectByOrgLocationsId(orgLocation.getId());
		 model.addObject("projectList", projectList);
		 model.setViewName("reports/pendingForApprovalTimesheetReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
	
	 
	
	
}

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
public class JobPipelineReportsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	ReportsUtil reportsUtil;
	
	@RequestMapping(value = "/jobPipelineReports")
	public ModelAndView jobPipelineReports(HttpServletRequest request) throws Exception
	{
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 ModelAndView model = new ModelAndView();
		 
		 List<RequirementDTO> requirementsDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
		 model.addObject("requirementsList", requirementsDTOList);
		 
		 model.setViewName("reports/jobPipelineReports");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
}

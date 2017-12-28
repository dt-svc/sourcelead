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
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 ModelAndView model = new ModelAndView();
		 List<RequirementDTO> requirementDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocations.getId(), user, userRole, request);
		 model.addObject("requirementDTOList",requirementDTOList);
		 model.setViewName("reports/applicantsBackoutReport");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}

}

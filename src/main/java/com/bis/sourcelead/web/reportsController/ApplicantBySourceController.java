package com.bis.sourcelead.web.reportsController;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.bis.sourcelead.reportsDTO.TimeToFillDTO;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class ApplicantBySourceController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ReportsUtil reportsUtil;

	@RequestMapping(value = "/applicantBySource")
	public ModelAndView applicantBySource(HttpServletRequest request) throws Exception
	{
		 
		 
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
	  	 ModelAndView model = new ModelAndView();
	  	 List<RequirementDTO> requirementsDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementsList", requirementsDTOList);
		 model.setViewName("reports/applicantBySource");
		 return model;
	}
	
	
	@RequestMapping(value = "/timeToFill")
	public ModelAndView timeToFill(HttpServletRequest request) throws Exception
	{
		 
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 List<TimeToFillDTO> yearList = new ArrayList<>();
		 
		 int year = Calendar.getInstance().get(Calendar.YEAR);
		 for(int i=0; i<=5; i++) {
			 TimeToFillDTO timeToFillDTO = new TimeToFillDTO();
			 timeToFillDTO.setYear(year-i);
			 yearList.add(timeToFillDTO);
		 }
		
	  	 ModelAndView model = new ModelAndView();
	  	 List<RequirementDTO> requirementsDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementsList", requirementsDTOList);
		 model.addObject("currentYear", year);
		 model.addObject("yearList", yearList);
		 model.setViewName("reports/timeToFill");
		 return model;
	}
}

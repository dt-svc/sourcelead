package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class RequirementController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	RequirementService requirementService;
	
	@RequestMapping(value = "/requirement")
	public ModelAndView newRequirement(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirement");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
        
        List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsList", educationsList);
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		
		List<Requirement> jobsTemplateList = requirementService.getJobTitleByJobTemplateStatusAndOrgLocationId(orgLocations.getId());
		model.addObject("jobsTemplateList", jobsTemplateList);		
		return model;

	}

}

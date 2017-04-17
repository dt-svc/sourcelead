package com.bis.sourcelead.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RequirementService;

@Controller
public class OpenJobsController {

	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value = "/openRequirements")
		 public ModelAndView openJobs() throws Exception{
		 ModelAndView model = new ModelAndView();
		 List<Countries> countriesList = countriesService.getAllCountries();
		 model.addObject("countryList",countriesList);
		 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		 model.addObject("skillsList",skillsList);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("openJobs/openjobs");
		 return model;
	}
	
	
	 /*@RequestMapping(value = "/openRequirementsSummary")
		public ModelAndView openJobsforApplicants(@RequestParam(value = "requirementUUID", required = false) String requirementUUID) throws Exception{
		 ModelAndView model = new ModelAndView();
		 //OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Requirement requirementDetails =  requirementService.getRequirementByRequirementUUID(requirementUUID);
		 model.addObject("requirementDetails", requirementDetails);
		 String requirementCreatedDate = Constants.DMY.format(requirementDetails.getCreatedDate());
		 model.addObject("requirementCreatedDate", requirementCreatedDate);
		 model.setViewName("openJobs/openjobsSummary");
		 //Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		 //model.addObject("loginCountryName",countries);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementUUID", requirementUUID);
		 return model;
	}*/
}

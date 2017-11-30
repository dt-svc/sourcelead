package com.bis.sourcelead.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;

@Controller
@RequestMapping("/path")
public class OpenJobsController {

	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private OrgLocationService orgLocationService;
	
	@RequestMapping(value = "/ws/openRequirements")
		 public ModelAndView openJobs() throws Exception{
		 ModelAndView model = new ModelAndView();
		 List<Countries> countriesList = countriesService.getAllCountries();
		 model.addObject("countryList",countriesList);
		 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		 model.addObject("skillsList",skillsList);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("openJobs/souceleadJobPage");
		 return model;
	}
	
	
	@RequestMapping(value = "/ws/jobs/{title}/{location}/{experience}")
	 public String openRequirementsRedirect(@PathVariable String title,@PathVariable String location,@PathVariable String experience,HttpServletRequest request) throws Exception{
	 ModelAndView model = new ModelAndView();
	 List<Countries> countriesList = countriesService.getAllCountries();
	 model.addObject("countryList",countriesList);
	 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
	 model.addObject("skillsList",skillsList);
	 
	 List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
	 model.addObject("industryList", industryListWithoutId);
	 
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.addObject("openJobsTitle", title);
	 model.addObject("openJobsLocation", location);
	 model.addObject("experience", experience);
//	 model.setViewName("openJobs/openjobs");
	 return "openJobs/openjobs";
}
	
	
	
	@RequestMapping(value = "/ws/openJobsPublicPage")
		public ModelAndView openJobsforApplicants(@RequestParam(value = "requirementUUID", required = false) String requirementUUID) throws Exception{
		 ModelAndView model = new ModelAndView();
		 //OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Requirement requirementDetails =  requirementService.getRequirementByRequirementUUID(requirementUUID);
		 OrgLocations orglo = orgLocationService.getOrgLocations(requirementDetails.getOrgLocationId().getId());
		 
		 model.addObject("requirementReleaseStatus", requirementDetails.getIsRequirementRelesedToPublic());
		 model.addObject("requirementDetails", requirementDetails);
		 String requirementCreatedDate = commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),requirementDetails.getCreatedDate());
		 model.addObject("requirementCreatedDate", requirementCreatedDate);
		 model.setViewName("openJobs/openjobs");
		 //Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		 //model.addObject("loginCountryName",countries);
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementUUID", requirementUUID);
		 return model;
	}
}

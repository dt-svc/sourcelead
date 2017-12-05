package com.bis.sourcelead.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	@RequestMapping(value = "/openJobs")
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
	
	
	@RequestMapping(value = "/jobs/{title}/{location}/{experience}")
	 public String openRequirementsRedirect(ModelMap modelMap,@PathVariable String title,@PathVariable String location,@PathVariable String experience,
			 @RequestParam(value = "postedOn",defaultValue ="", required = false) String postedOn,@RequestParam(value = "locationType",defaultValue ="", required = false) String locationType,
			 @RequestParam(value = "employementType",defaultValue ="", required = false) String employementType,@RequestParam(value = "companies",defaultValue ="", required = false) String companies,
			 HttpServletRequest request) throws Exception{
	 List<Countries> countriesList = countriesService.getAllCountries();
	 modelMap.addAttribute("countryList",countriesList);
	 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
	 modelMap.addAttribute("skillsList",skillsList);
	 
	 List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
	 modelMap.addAttribute("industryList", industryListWithoutId);
	 
	 modelMap.addAttribute("sourceleadUrl", sourceleadUrl);
	 modelMap.addAttribute("openJobsTitle", title);
	 modelMap.addAttribute("openJobsLocation", location);
	 modelMap.addAttribute("openJobsExperience", experience);
	 modelMap.addAttribute("postedOn", postedOn);
	 modelMap.addAttribute("locationType", locationType);
	 modelMap.addAttribute("employementType", employementType);
	 modelMap.addAttribute("companies", companies);
	 return "openJobs/openjobs";
}
	
	@RequestMapping(value = "/jobs")
	 public String openRequirementsRedirect(ModelMap modelMap,
			 @RequestParam(value = "postedOn",defaultValue ="", required = false) String postedOn,@RequestParam(value = "locationType",defaultValue ="", required = false) String locationType,
			 @RequestParam(value = "employementType",defaultValue ="", required = false) String employementType,@RequestParam(value = "companies",defaultValue ="", required = false) String companies,
			 HttpServletRequest request) throws Exception{
		 return "forward:/jobs/null/null/null";
	}
	
	@RequestMapping(value = "/jobs/{title}")
	 public String openRequirementsRedirect(ModelMap modelMap,@PathVariable String title,
			 @RequestParam(value = "postedOn",defaultValue ="", required = false) String postedOn,@RequestParam(value = "locationType",defaultValue ="", required = false) String locationType,
			 @RequestParam(value = "employementType",defaultValue ="", required = false) String employementType,@RequestParam(value = "companies",defaultValue ="", required = false) String companies,
			 HttpServletRequest request) throws Exception{
		return "forward:/jobs/title/null/null";
	}
	
	@RequestMapping(value = "/jobs/{title}/{location}")
	 public String openRequirementsRedirect(ModelMap modelMap,@PathVariable String title,@PathVariable String location,
			 @RequestParam(value = "postedOn",defaultValue ="", required = false) String postedOn,@RequestParam(value = "locationType",defaultValue ="", required = false) String locationType,
			 @RequestParam(value = "employementType",defaultValue ="", required = false) String employementType,@RequestParam(value = "companies",defaultValue ="", required = false) String companies,
			 HttpServletRequest request) throws Exception{
		return "forward:/jobs/title/location/null";
	}
	
	
	@RequestMapping(value = "/openJobsPublicPage")
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

package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.List;

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
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
	
	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@RequestMapping(value = "/openJobs")
	public ModelAndView openJobs() throws Exception
	{
		ModelAndView model = new ModelAndView();
		List<Countries> countriesList = countriesService.getAllCountries();
		model.addObject("countryList", countriesList);
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("openJobs/search-jobs");
		return model;
	}
	
	@RequestMapping(value = "/guestApplyToJob")
	public ModelAndView guestApplyToJob(@RequestParam(value = "requirementUUID", required = false) String requirementUUID) throws Exception
	{
		ModelAndView model = new ModelAndView();
		Requirement req = requirementService.getRequirementByRequirementUUID(requirementUUID);
		model.addObject("reqId", req.getId());
		model.addObject("orgId", req.getOrgLocationId().getOrg().getId());
		model.addObject("locationId", req.getOrgLocationId().getId());
		model.addObject("skills", req.getSkills());
		model.addObject("reqName", req.getJobTitle());
		model.addObject("jobType", req.getJobType());
		model.addObject("minExperience", req.getMinExp());
		model.addObject("maxExperience", req.getMaxExp());
		model.addObject("postedOrg", req.getPostedOrgName());
		List<String> requirementLocationsList = new ArrayList<>();
		List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(req.getId());
		if(locationsList != null && !locationsList.isEmpty()) {
			for(RequirementLocations location : locationsList) {
				requirementLocationsList.add(location.getLocation());
			}
		}
		req.setLocationsList(requirementLocationsList);
		model.addObject("location", req.getLocationsList());
		model.addObject("minRate", req.getRate());
		model.addObject("maxRate", req.getMaxRate());
		model.addObject("rateType",req.getRateType());
		model.addObject("currencyCode", req.getCurrencyCode());
		model.addObject("noticePeriod", req.getNoticePeriod());
		model.addObject("visaTypes", req.getVisaType());
		model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("openJobs/guestApplyToJob");
		return model;
	}
	
	@RequestMapping(value = { "/jobs/{title}/{location}/{experience}",
			"/app/jobs/{title}/{location}/{experience}" })
	public String openRequirementsRedirectMain(ModelMap modelMap, @PathVariable String title, @PathVariable String location, @PathVariable String experience, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, @RequestParam(value = "postedOrgName", defaultValue = "", required = false) String postedOrgName,@RequestParam(value = "offset", defaultValue = "", required = false) String offset, HttpServletRequest request) throws Exception
	{
		List<Countries> countriesList = countriesService.getAllCountries();
		modelMap.addAttribute("countryList", countriesList);
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		modelMap.addAttribute("skillsList", skillsList);
		List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
		modelMap.addAttribute("industryList", industryListWithoutId);
		modelMap.addAttribute("sourceleadUrl", sourceleadUrl);
		title = (title != null && !title.isEmpty() && !title.equalsIgnoreCase("null")) ? title : "all";
		location = (location != null && !location.isEmpty() && !location.equalsIgnoreCase("null")) ? location : "all";
		experience = (experience != null && !experience.isEmpty() && !experience.equalsIgnoreCase("null")) ? experience : "all";
		modelMap.addAttribute("openJobsTitle", title);
		modelMap.addAttribute("openJobsLocation", location);
		modelMap.addAttribute("openJobsExperience", experience);
		modelMap.addAttribute("postedOn", postedOn);
		modelMap.addAttribute("locationType", locationType);
		modelMap.addAttribute("employementType", employementType);
		modelMap.addAttribute("companies", companies);
		modelMap.addAttribute("offset", offset);
		modelMap.addAttribute("postedOrgName", postedOrgName);
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "guestuserCreateMsg");
		if(!isNullOrEmpty(msg)){
			modelMap.addAttribute("guestusermsg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "guestuserCreateMsg");
		}
		return "openJobs/openjobs";
	}
	
	
	private boolean isNullOrEmpty(String msg)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@RequestMapping(value = { "/app/ws/{postedOrgName}-{companies}"})
	public String openRequirementsRedirect(ModelMap modelMap, @PathVariable String companies,@PathVariable String postedOrgName,HttpServletRequest request) throws Exception
	{
		modelMap.addAttribute("sourceleadUrl", sourceleadUrl);
		modelMap.addAttribute("companies", companies);
		modelMap.addAttribute("postedOrgName", postedOrgName);
		modelMap.addAttribute("specificCompany", "specificCompany");
		return "openJobs/openjobs";
	}

	
	@RequestMapping(value = "/job/{url}-{id}")
	public ModelAndView openRequirementsSummary(@PathVariable long id, @PathVariable String url) throws Exception
	{
		/*
		 * url = title != null ?
		 * title.replaceAll("-mod-","%").replaceAll("-and-","&").replaceAll(
		 * "-or-","/").replaceAll("-", " ") : title;
		 */
		ModelAndView modelAndView = new ModelAndView();
		Requirement requirementDeatilsSummary = requirementService.getRequirementByRequirementIdAndJobTitle(id, url);
		if (requirementDeatilsSummary != null
				&& requirementDeatilsSummary.getRequirementUUID() != null) {
			String uuid = requirementDeatilsSummary.getRequirementUUID();
			modelAndView.addObject("requirementUUID", uuid);
			if (requirementDeatilsSummary.getJobType() != null
					&& requirementDeatilsSummary.getJobType() != "") {
				modelAndView.addObject("employementType", requirementDeatilsSummary.getJobType().toLowerCase().replaceAll(" ", "-").replaceAll(" & ", "-and-"));
			}
		}
		else {
			modelAndView.addObject("requirementUUID", "not found");
		}
		if (requirementDeatilsSummary != null
				&& requirementDeatilsSummary.getIsRequirementRelesedToPublic() != null
				&& requirementDeatilsSummary.getIsRequirementRelesedToPublic() != "") {
			modelAndView.addObject("requirementReleaseStatus", requirementDeatilsSummary.getIsRequirementRelesedToPublic());
		}
		modelAndView.addObject("sourceleadUrl", sourceleadUrl);
		modelAndView.setViewName("openJobs/openjobs");
		return modelAndView;
	}

	@RequestMapping(value = "/jobs")
	public String openRequirementsRedirect(ModelMap modelMap,@RequestParam(value = "offset", defaultValue = "", required = false) String offset, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
		/*return "redirect:/jobs/null/null/null";*/
		return openRequirementsRedirectMain(modelMap, null, null, null, postedOn, locationType, employementType, companies, "", offset, request);
	}
		
	@RequestMapping(value = "/jobs/{title}")
	public String openRequirementsRedirect(ModelMap modelMap,@RequestParam(value = "offset", defaultValue = "", required = false) String offset, @PathVariable String title, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
//		return "forward:/jobs/" + title + "/null/null";
		return openRequirementsRedirectMain(modelMap, title, null, null, postedOn, locationType, employementType, companies, "", offset, request);

	}
	
	@RequestMapping(value = "/jobs/{title}/{location}")
	public String openRequirementsRedirect(ModelMap modelMap,@RequestParam(value = "offset", defaultValue = "", required = false) String offset, @PathVariable String title, @PathVariable String location, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
//		return "forward:/jobs/" + title + "/" + location + "/null";
		return openRequirementsRedirectMain(modelMap, title, location, null, postedOn, locationType, employementType, companies, "", offset, request);

	}
	
	@RequestMapping(value = "/requirementsSummary.html")
	public String requirementsSummary(@RequestParam(value = "requirementsUUID", required = false) String requirementsUUID) throws Exception
	{
		Requirement requirementDeatils = requirementService.getRequirementByRequirementUUID(requirementsUUID);
		Requirement requirementDeatilsSummary = requirementService.getRequirementByRequirementId(requirementDeatils.getId());
		requirementDeatilsSummary.setRequirementOrgName(requirementDeatilsSummary.getOrgLocationId().getOrgLocationName());
		requirementDeatilsSummary.setRequirementCreatedDateToString(commonUtil.dateStrFromOrgDateFormatter(requirementDeatils.getOrgLocationId().getDateFormat(), requirementDeatilsSummary.getCreatedDate()));
		return "static-web/about-us";
	}
	
	@RequestMapping(value = { "/app/openJobsPublicPage", "/openJobsPublicPage" })
	public ModelAndView openJobsforApplicants(@RequestParam(value = "requirementUUID", required = false) String requirementUUID) throws Exception
	{
		ModelAndView model = new ModelAndView();
		// OrgLocations orgLocations = (OrgLocations)
		// SourceLeadSessionManager.getSessionAttribute(request,
		// "orgLocations");
		Requirement requirementDetails = requirementService.getRequirementByRequirementUUID(requirementUUID);
		OrgLocations orglo = orgLocationService.getOrgLocations(requirementDetails.getOrgLocationId().getId());
		model.addObject("requirementReleaseStatus", requirementDetails.getIsRequirementRelesedToPublic());
		model.addObject("requirementDetails", requirementDetails);
		String requirementCreatedDate = commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(), requirementDetails.getCreatedDate());
		model.addObject("requirementCreatedDate", requirementCreatedDate);
		model.setViewName("openJobs/openjobs");
		// Countries countries =
		// countriesService.getCountriesByCountryName(orgLocations.getCountry());
		// model.addObject("loginCountryName",countries);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("requirementUUID", requirementUUID);
		return model;
	}
}

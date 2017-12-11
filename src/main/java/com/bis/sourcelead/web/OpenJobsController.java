package com.bis.sourcelead.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.core.internal.resources.MoveDeleteHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public ModelAndView openJobs() throws Exception
	{
		ModelAndView model = new ModelAndView();
		List<Countries> countriesList = countriesService.getAllCountries();
		model.addObject("countryList", countriesList);
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("openJobs/sourceleadJobPage");
		return model;
	}
	
	@RequestMapping(value = { "/jobs/{title}/{location}/{experience}",
			"/app/jobs/{title}/{location}/{experience}" })
	public String openRequirementsRedirect(ModelMap modelMap, @PathVariable String title, @PathVariable String location, @PathVariable String experience, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
		List<Countries> countriesList = countriesService.getAllCountries();
		modelMap.addAttribute("countryList", countriesList);
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		modelMap.addAttribute("skillsList", skillsList);
		List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
		modelMap.addAttribute("industryList", industryListWithoutId);
		modelMap.addAttribute("sourceleadUrl", sourceleadUrl);
		title = title == null ? "test" : title;
		modelMap.addAttribute("openJobsTitle", title != null && !title.equalsIgnoreCase("null")
				? title : "");
		modelMap.addAttribute("openJobsLocation", location != null
				&& !location.equalsIgnoreCase("null") ? location : "");
		modelMap.addAttribute("openJobsExperience", experience != null
				&& !experience.equalsIgnoreCase("null") ? experience : "");
		modelMap.addAttribute("postedOn", postedOn);
		modelMap.addAttribute("locationType", locationType);
		modelMap.addAttribute("employementType", employementType);
		modelMap.addAttribute("companies", companies);
		return "openJobs/openjobs";
	}
	
	@RequestMapping(value = "/{title}-{id}")
	public ModelAndView openRequirementsSummary(@PathVariable long id, @PathVariable String title) throws Exception
	{
		title = title != null
				? title.replaceAll("-", " ").replaceAll("-and-", "&").replaceAll("-or-", "||")
				: title;
		ModelAndView modelAndView = new ModelAndView();
		Requirement requirementDeatilsSummary = requirementService.getRequirementByRequirementIdAndJobTitle(id, title);
		if (requirementDeatilsSummary != null
				&& requirementDeatilsSummary.getRequirementUUID() != null) {
			String uuid = requirementDeatilsSummary.getRequirementUUID();
			modelAndView.addObject("requirementUUID", uuid);
			if (requirementDeatilsSummary.getJobType() != null
					&& requirementDeatilsSummary.getJobType() != "") {
				modelAndView.addObject("employementType", requirementDeatilsSummary.getJobType().toLowerCase().replaceAll(" ", "-").replaceAll(" & ", "-and-"));
			}
		}
		if (requirementDeatilsSummary.getIsRequirementRelesedToPublic() != null
				&& requirementDeatilsSummary.getIsRequirementRelesedToPublic() != "") {
		modelAndView.addObject("requirementReleaseStatus", requirementDeatilsSummary.getIsRequirementRelesedToPublic());
		}
		modelAndView.setViewName("openJobs/openjobs");
		return modelAndView;
	}
	
	@RequestMapping(value = "/jobs")
	public String openRequirementsRedirect(ModelMap modelMap, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
		return "forward:/jobs/null/null/null";
	}
	
	@RequestMapping(value = "/jobs/{title}")
	public String openRequirementsRedirect(ModelMap modelMap, @PathVariable String title, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
		return "forward:/jobs/" + title + "/null/null";
	}
	
	@RequestMapping(value = "/jobs/{title}/{location}")
	public String openRequirementsRedirect(ModelMap modelMap, @PathVariable String title, @PathVariable String location, @RequestParam(value = "postedOn", defaultValue = "", required = false) String postedOn, @RequestParam(value = "locationType", defaultValue = "", required = false) String locationType, @RequestParam(value = "employementType", defaultValue = "", required = false) String employementType, @RequestParam(value = "companies", defaultValue = "", required = false) String companies, HttpServletRequest request) throws Exception
	{
		return "forward:/jobs/" + title + "/" + location + "/null";
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
	
	@RequestMapping(value = { "/openJobsPublicPage", "/app/openJobsPublicPage" })
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

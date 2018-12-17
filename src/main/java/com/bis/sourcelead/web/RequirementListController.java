package com.bis.sourcelead.web;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.JobBoardConfigurationService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class RequirementListController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	private ProjectClientService projectClientService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
        
    @Autowired
    private JobBoardConfigurationService jobBoardConfigurationService;
	
	@RequestMapping(value = "/jobs")
	public ModelAndView requirementList(HttpServletRequest request,@RequestParam(value="searchResult", required=false)String  searchResult
			,@RequestParam(value="searchValue", required=false)String searchValue,@RequestParam(defaultValue = "") String viewTab) throws Exception {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		if(searchResult != null && !searchResult.equalsIgnoreCase("undefined") && searchResult.equalsIgnoreCase("universalRequirementSearchList")){
		    model.addObject("universalRequirementList", "universalRequirementList");
		    model.addObject("searchValue",searchValue);
		    model.addObject("searchType","Requirement");
		}
		
		/*List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);*/
		
		List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
		model.addObject("skillsList", skillsList);
		
		List<LookUpData> skillList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillList", skillList);

		List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		
		model.addObject("educationsList", educationsList);
		
		List<RequirementLocations> requirementLocations=requirementLocationsService.getAllRequirementByOrgLocationId(orgLocations.getId());
		
		Map<String, List<RequirementLocations>> cityList = requirementLocations.stream().collect(Collectors.groupingBy(rl -> rl.getCity() == null ? "" : rl.getCity()));
		
		Set<String> uniquecityList=cityList.keySet();
		TreeSet<String> seen = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		uniquecityList.removeIf(s -> !seen.add(s));
		model.addObject("cityList", uniquecityList);
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		if(null != orgClientList && orgClientList.size() > 0){
			for(OrgRelations ocl :orgClientList){
				ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
				ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
			 }
		    model.addObject("add_clientList", orgClientList);
		}
	    model.addObject("loginOrgLocId", orgLocations.getId());
	    
	    List<ProjectClients> projectList = projectClientService.getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(orgLocations.getId());
		for(ProjectClients projectClients : projectList){
			if(projectClients.getOrgRelationId() != null){
			if(projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				projectClients.setClientName(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
			}else if(projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
				projectClients.setClientName(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
			}
		}
		}
		model.addObject("projectList", projectList);
	    
	    /*List<Projects> projectList=projectsService.getProjectByOrgLocationsId(orgLocations.getId());
	    
	    model.addObject("projectList", projectList);*/
	    
	    List<String> visaList = lookUpDataService.getAllVisaByCategory();
	    model.addObject("visaList", visaList);
	    
	    List<Countries> countryList = countriesService.getAllCountries();
		model.addObject("countryList", countryList);
		
		if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
		
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
			   model.addObject("loginCountryName", countries.getName());
			}
			if(countries!= null && countries.getName()!= null) {
	        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
	        model.addObject("statesList", statesList);
		}
	}
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("viewTab",viewTab);
		model.setViewName("requirement/requirementList");
		
	    	/*
		    *  This code is commented as  the client  based requirement   is not in use 
		    */
		
		   /* List<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
	         for(OrgRelations orgRelations:orgClientList){
	         	orgRelations.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
	         }
	        model.addObject("add_client", orgClientList);
	        model.addObject(orgClientList);*/
	        
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Requirement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		/*String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "reqCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "reqCreateMsg");
		}*/
		
		return model;

	}
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@RequestMapping(value = "/releaseRequirementToClientOrVendors")
	public ModelAndView releaseRequirementToClientOrVendors(String requirementUUID,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
                User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		
		Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		if(null != requirement && requirement.getOrgLocationId().getId().equals(orgLoc.getId())){
			String sourceleadShareUrl = StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
			model.addObject("sourceleadShareUrl", sourceleadShareUrl);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("requirement", requirement);
			model.addObject("jobBoardList", jobBoardConfigurationService.getOrgJobBoardList(orgLoc.getOrg().getId(), true));
			model.addObject("jobBoardAccountList", jobBoardConfigurationService.getOrgJobBoardAccountList(orgLoc.getOrg().getId(), 0, null, user.getId(), orgLoc.getId()));
			model.setViewName("requirement/releaseRequirement");
			getReleaseManagementDetails(model, requirement, requirementUUID, orgLoc, request);
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		return model;
	}
	
	
	@RequestMapping(value = "/jobReleaseManagement")
	public ModelAndView jobReleaseManagement(String requirementUUID,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
	
		Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		if(null != requirement && requirement.getOrgLocationId().getId().equals(orgLoc.getId())){
			model.addObject("jobRecommendations", "jobRecommendations");
			String sourceleadShareUrl = StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
			model.addObject("sourceleadShareUrl", sourceleadShareUrl);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("requirement", requirement);
                        model.addObject("jobBoardList", jobBoardConfigurationService.getOrgJobBoardList(orgLoc.getOrg().getId(), true));
			model.addObject("jobBoardAccountList", jobBoardConfigurationService.getOrgJobBoardAccountList(orgLoc.getOrg().getId(), 0, null, user.getId(), orgLoc.getId()));
			model.setViewName("requirement/requirementRelease");
                        getReleaseManagementDetails(model, requirement, requirementUUID, orgLoc, request);
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		return model;
	}
	
	
	@RequestMapping(value = "/jobRecommendations")
	public ModelAndView jobRecommendations(String requirementUUID,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		ModelAndView model = new ModelAndView();
		
		Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		List<Countries> countryList = countriesService.getAllCountries();
		model.addObject("countryList", countryList);
		if(null != requirement && requirement.getOrgLocationId().getId().equals(orgLoc.getId())){
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("requirementUUID", requirementUUID);
			model.addObject("requirementId", requirement.getId());
			model.addObject("requirementSubmissionStatus", requirement.getRequirementSubmissionStatus());
			model.setViewName("requirement/jobRecommendations");
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		return model;
	}
	
	
	public ModelAndView getReleaseManagementDetails(ModelAndView model,Requirement requirement,String requirementUUID,OrgLocations orgLoc,HttpServletRequest request) throws Exception {
		
		String experience = "";
	    if(StringUtils.isNotEmpty(requirement.getMinExp()) && StringUtils.isEmpty(requirement.getMaxExp())){
			experience = requirement.getMinExp()+" Years";
		}else if(StringUtils.isEmpty(requirement.getMinExp()) && StringUtils.isNotEmpty(requirement.getMaxExp())){
			experience = requirement.getMaxExp()+" Years";
		}else if(StringUtils.isNotEmpty(requirement.getMinExp()) && StringUtils.isNotEmpty(requirement.getMaxExp())){
			experience = requirement.getMinExp()+" to "+requirement.getMaxExp()+" Years";
		}
		
	    String location ="";
		if(StringUtils.isNotEmpty(requirement.getState()) && StringUtils.isNotEmpty(requirement.getCountry())){
				location = requirement.getState()+", "+requirement.getCountry();
		}else{
			location = requirement.getCountry();
		}
		
		StringBuilder  description = new StringBuilder("");
		description = description.append("Job Title : ").append(requirement.getJobTitle()).append("; ")
		            .append("Posted By : ").append(requirement.getOrgLocationId().getOrgLocationName()).append("; ")
		            .append("Skills : ").append(requirement.getSkills()).append("; ").append("Job Type : ").append(requirement.getJobType()).append("; ");
		if(StringUtils.isNotEmpty(experience)){
			description =   description.append("Experience : ").append(experience).append("; ");
		}
		if(StringUtils.isNotEmpty(location)){
			description =   description.append("Job Location : ").append(location).append("; ");	
		}
		if(StringUtils.isNotEmpty(requirement.getNoticePeriod())){
			description =   description.append("Notice Period : ").append(requirement.getNoticePeriod()).append("; ");
		}
		
		description = description.append("Posted Date : ").append(requirement.getCreatedDate()).append("; ").append("");
		
		model.addObject("socialpost_title", "");
		model.addObject("socialpost_link", new StringBuilder("").append(sourceleadUrl).append("/openJobsPublicPage?requirementUUID=").append(requirementUUID));
		model.addObject("socialpost_description", description);
		model.addObject("socialpost_picture", "");
		model.addObject("socialpost_comment", "");
		

	SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
	if(sessionData == null)
	{
		sessionData = new SessionMaintainanceData();
	}
	sessionData.setNav(Constants.Requirement);
	
	//List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLoc.getId());
	List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLoc.getId(), "", "");
	
	for(OrgRelations orgRelation :vendorsList){
		if(orgRelation.getLocationId().getId().equals(orgLoc.getId())){
			orgRelation.setVendorName(orgRelation.getResponderLocationId().getOrgLocationName());
		}
		if(orgRelation.getResponderLocationId().getId().equals(orgLoc.getId())){
			orgRelation.setVendorName(orgRelation.getLocationId().getOrgLocationName());
		}
	}
	model.addObject("vendor_list", vendorsList);
    model.addObject(vendorsList);
    model.addObject("requirementId",requirement.getId());

    model.addObject("requirementUUID",requirementUUID);
   
    if(!isNullOrEmpty(requirement.getJobType())){
    	model.addObject("requirementType",requirement.getJobType());
    }
   
    if(null != requirement.getRate()){
    	 model.addObject("jobRate",requirement.getCurrencyCode()+" "+requirement.getRate()+" / "+requirement.getRateType());	
    }
    
    if(null != requirement.getRewardType()){
    	model.addObject("rewardType", requirement.getRewardType());
    	model.addObject("rewardPercentage", requirement.getRewardPercentage());
    	model.addObject("rewardFlat", requirement.getRewardFlat());
    	model.addObject("rewardFlatRateType", requirement.getRewardFlatRateType());
    	model.addObject("rewardGauranteePeriod", requirement.getRewardGuaranteePeriod());
    }
   

    model.addObject("jobTitle",requirement.getJobTitle());
    
	String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "reqReleaseMessage");
	if(!isNullOrEmpty(msg)){
		model.addObject("msg", msg);
		SourceLeadSessionManager.removeSessionAttribute(request, "reqReleaseMessage");
	}
	request.getSession().setAttribute("sessionData",sessionData);
		return model;
		
	}
	
	
	
	
	 @RequestMapping(value = "/terminateRequirementTemplate", method = RequestMethod.POST)
		public @ResponseBody String terminateRequirementTemplate(String requirementId, HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			Requirement rt =  requirementService.getRequirementTemplateByRequirementId(Long.parseLong(requirementId),orgLocation.getId());
			if(rt != null){
				
				rt.setStatus(Constants.MEMBER_IN_ACTIVE);
				
				rt.setUpdatedBy(user.getUserCode());
				rt.setUpdatedDate(commonUtil.currentDate());
				requirementService.updateRequirementTemplate(rt);
			}
			return "success";
		}
	
	 @RequestMapping(value = "/getOpenNetworkStatus", method = RequestMethod.GET)
		public @ResponseBody String getOpenNetworkStatus(Long requirementId, HttpServletRequest request) throws Exception {
		 String resultJson = null;
		 Requirement requirement= requirementService.getRequirementByRequirementId(requirementId);
			if(null != requirement){
				requirement.setIsCrowdSourcing(requirement.getIsCrowdSourcing());
				requirement.setIsPublic(requirement.getIsPublic());
			}
			resultJson = commonUtil.toJSON(requirement);
			return resultJson;
		}
	 /*
	  * author Prasanna vadla
	  * redirect to requirementReports page
	  */
	 @RequestMapping(value = "/requirementListForReports")
		public ModelAndView requirementListForReports(@RequestParam(value="serachValue", required=false)String serachValue) throws Exception {
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("requirement/requirementReports");
		 return model;
			
	 }
	 
}

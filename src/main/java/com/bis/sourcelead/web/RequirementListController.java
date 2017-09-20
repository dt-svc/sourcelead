package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class RequirementListController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
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
	
	@RequestMapping(value = "/requirementList")
	public ModelAndView requirementList(HttpServletRequest request,@RequestParam(value="searchResult", required=false)String  searchResult
			,@RequestParam(value="searchValue", required=false)String searchValue) throws Exception {
		
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
		
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		if(null != orgClientList && orgClientList.size() > 0){
			for(OrgRelations ocl :orgClientList){
				ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
				ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
			 }
		    model.addObject("add_clientList", orgClientList);
		}
		
	    model.addObject("loginOrgLocId", orgLocations.getId());
	    
	    List<Countries> countryList = countriesService.getAllCountries();
		model.addObject("countryList", countryList);
		
		if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
		
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
			   model.addObject("loginCountryName", countries.getName());
			}
	        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
	        model.addObject("statesList", statesList);
		}
	    
		model.addObject("sourceleadUrl", sourceleadUrl);
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
		
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "reqCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "reqCreateMsg");
		}
		
		return model;

	}
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@RequestMapping(value = "/releaseRequirementToClientOrVendors")
	public ModelAndView releaseRequirementToClientOrVendors(String requirementUUID,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		ModelAndView model = new ModelAndView();
		
		Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		if(null != requirement && requirement.getOrgLocationId().getId().equals(orgLoc.getId())){
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("requirement/releaseRequirement");
			

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
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		
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

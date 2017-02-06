package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class RequirementListController {
	
	
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
			,@RequestParam(value="serachValue", required=false)String serachValue) throws Exception {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(searchResult != null && !searchResult.equalsIgnoreCase("undefined") && searchResult.equalsIgnoreCase("universalRequirementList")){
		    model.addObject("universalRequirementList", "universalRequirementList");
		    model.addObject("serachValue",serachValue);
		}
		
		/*List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);*/
		
		List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
		model.addObject("skillsList", skillsList);

		
		List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsList", educationsList);
		
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getResponderLocationId().getOrg().getOrgName());
			ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
		 }
	    model.addObject("add_clientList", orgClientList);
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
	         	orgRelations.setClientName(orgRelations.getResponderLocationId().getOrg().getOrgName());
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
	public ModelAndView releaseRequirementToClientOrVendors(String requirementId,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/releaseRequirement");
		
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Requirement);
		
		//List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLoc.getId());
		List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLoc.getId(), "");
		
		for(OrgRelations orgRelation :vendorsList){
			if(orgRelation.getLocationId().getId() == orgLoc.getId()){
				orgRelation.setClientName(orgRelation.getResponderLocationId().getOrg().getOrgName());
			}
			if(orgRelation.getResponderLocationId().getId() == orgLoc.getId()){
				orgRelation.setClientName(orgRelation.getLocationId().getOrg().getOrgName());
			}
		}
		model.addObject("vendor_list", vendorsList);
        model.addObject(vendorsList);
		
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
	
	 
}

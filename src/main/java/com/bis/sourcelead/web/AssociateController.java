package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class AssociateController {
	private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	StatesService statesService;
	
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	
	@Autowired
	private RolesService roleService;
	
	@Autowired
	 TimeZoneService timeZoneService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
/**
 * @author Laxman 
 * @param searchResult
 * @param searchValue
 * @param request
 * @param associateKeyword
 * @param associateIdSearchId
 * @return model
 * @throws Exception
 */
	
	@RequestMapping(value = "/associatesList")
	public ModelAndView usersList(@RequestParam(value="candidateStatus", required=false) String  candidateStatus,
			@RequestParam(value="searchResult", required=false)String  searchResult ,
			@RequestParam(value="searchValue", required=false)String searchValue, HttpServletRequest request,
			@RequestParam(value="candidateKeyword", required=false)String candidateKeyword,
			@RequestParam(value="candidateIdSearchId", required=false)String candidateIdSearchId) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/associatesList");
		
		if(candidateKeyword != null && !candidateKeyword.equalsIgnoreCase("undefined") && candidateKeyword.equalsIgnoreCase("candidateSearch")){
		    model.addObject("candidateKeyword", candidateKeyword);
		    model.addObject("candidateIdSearchId",candidateIdSearchId);
		}
		
		if(searchResult != null && !searchResult.equalsIgnoreCase("undefined") && searchResult.equalsIgnoreCase("universalCanddateList")){
		    model.addObject("universalCanddateList", "universalCanddateList");
		    model.addObject("serachValue",searchValue);
		    model.addObject("searchType", "Associate");
		}
		
		List<Countries> countryList = countriesService.getAllCountries();
		model.addObject("countryList", countryList);
		
		if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
		
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
			   model.addObject("loginCountryName", countries.getName());
			   model.addObject("currency", countries.getCurrency());
			   
			}
	        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
	        model.addObject("statesList", statesList);
		}
		
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		
		List<LookUpData> visaList = lookUpDataService.getLookUpDataByCategory(Constants.VISA);
		model.addObject("visaList", visaList);
		
		List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsList", educationsList);
		
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		model.addObject("documentsList", documentsList);
		
		model.addObject("candidateType", "Associates");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Associates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		//this block is for success messages --@Yashoda
				String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "candidateCreateMsg");
				if(null != msg && !"".equals(msg)){
					model.addObject("msg", msg);
					SourceLeadSessionManager.removeSessionAttribute(request, "candidateCreateMsg");
				}
				else{
					msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "consultantCreateMsg");
					model.addObject("msg", msg);
					SourceLeadSessionManager.removeSessionAttribute(request, "consultantCreateMsg");
				}		
		
		return model;

	}
	
	 
	
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_CANDIDATE')")
	@RequestMapping(value = "/associate")
	public ModelAndView addAssociate(@RequestParam(value="type", required=false) String type ,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		
		List<Roles> rolesList = roleService.getOrgEnrolledRolesByOrgLocationId(org, orgLocations.getId());
		model.addObject("rolesList", rolesList);
		
		if("addAssociate".equalsIgnoreCase(type)){
			model.setViewName("candidates/addAssociate");
		}
		if("importAssociate".equalsIgnoreCase(type)){
			model.setViewName("candidates/importAssociate");
		}
		if("associateFromCandidate".equalsIgnoreCase(type)){
			model.setViewName("candidates/associateFromCandidate");
		}
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("type", type);
	
		/* Based on Country Currency code will be set into Rate field */
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Associates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		/* educationsList,skillsList are used for autocompletes for education and skills in add consultant creation */
		
		 List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		 model.addObject("educationsList", educationsList);
		
		 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		
		 model.addObject("skillsList", skillsList);
		 
		 List<String> visaList = lookUpDataService.getAllVisaByCategory();
		 model.addObject("visaList", visaList);
		 
	return model;
	}
	
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}

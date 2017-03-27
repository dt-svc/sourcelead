package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CandidateController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);
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
	
	/**
	 * Handles to display candidate jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	//Note this method is ResumeParsing Candidate method
	
	 @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
	 @RequestMapping(value = "/candidate")
		public ModelAndView candidate(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candiade page");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
            
            
            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			
			model.addObject("skillsList", skillsList);
			
			return model;
	    }
	 
	   //Note this method is ResumeParsing Candidate method
		
		 @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
		 @RequestMapping(value = "/addCandidate")
			public ModelAndView addCandidate(HttpServletRequest request) throws Exception {
			 	logger.debug("Display the candiade page");
			 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				ModelAndView model = new ModelAndView();
				model.setViewName("candidates/addCandidate");
				model.addObject("sourceleadUrl", sourceleadUrl);
				
				Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if(null != countries){
	            model.addObject("loginCountryName", countries.getName());
	            model.addObject("currencyCode", countries.getCurrency());
				}
	           
	            
	            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
				model.addObject("educationsList", educationsList);
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				
				model.addObject("skillsList", skillsList);
				
				return model;
		    }
		 
		 
	
	
	@RequestMapping(value = "/candidatesList")
	public ModelAndView usersList(@RequestParam(value="candidateStatus", required=false) String  candidateStatus,
			@RequestParam(value="searchResult", required=false)String  searchResult
			,@RequestParam(value="searchValue", required=false)String searchValue,
			HttpServletRequest request,
			@RequestParam(value="candidateKeyword", required=false)String candidateKeyword,
			@RequestParam(value="candidateIdSearchId", required=false)String candidateIdSearchId) throws Exception {
		logger.debug("--------candidatesList------");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/candidatesList");
		
		if(candidateKeyword != null && !candidateKeyword.equalsIgnoreCase("undefined") && candidateKeyword.equalsIgnoreCase("candidateSearch")){
		    model.addObject("candidateKeyword", candidateKeyword);
		    model.addObject("candidateIdSearchId",candidateIdSearchId);
		}
		
		if(searchResult != null && !searchResult.equalsIgnoreCase("undefined") && searchResult.equalsIgnoreCase("universalCanddateList")){
		    model.addObject("universalCanddateList", "universalCanddateList");
		    model.addObject("serachValue",searchValue);
		    model.addObject("searchType", "Candidate");
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
		
	/*	List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgLocationID(orgLocations.getId(),org.getId(),Constants.CANDIDATE_ACTIVE,"");
		model.addObject("candidatesList", candidatesList);*/
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		
		List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsList", educationsList);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Candidates);
		
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
	
	
	
	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/assignHotListToTeam")
	public ModelAndView assignHotListToTeam(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/assignHotListToTeam");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Candidates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		
		return model;

	}
	
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_CANDIDATE')")
	@RequestMapping(value = "/addConsultant")
	public ModelAndView addConsultant(@RequestParam(value="type", required=false) String  type,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		ModelAndView model = new ModelAndView();
		
		List<Roles> rolesList = roleService.getOrgEnrolledRolesByOrgLocationId(org, orgLocations.getId());
		model.addObject("rolesList", rolesList);
		
		model.setViewName("candidates/addConsultant");
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
		sessionData.setNav(Constants.Candidates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		/* educationsList,skillsList are used for autocompletes for education and skills in add consultant creation */
		
		 List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		 model.addObject("educationsList", educationsList);
		
		 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		
		 model.addObject("skillsList", skillsList);
		
		return model;
		
	}
	
	 //uploadCandidateForm
	 @RequestMapping(value = "/uploadCandidateForm")
		public ModelAndView uploadCandidateForm(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadCandidateForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			sessionData.setNav(Constants.Candidates);
			return model;

		}
	 
	 /*
	  * author PrasadK
	  * redirect to candidateReports page
	  */
	 @RequestMapping(value = "/candidateListForReports")
		public ModelAndView candidateListForReports(@RequestParam(value="serachValue", required=false)String serachValue,HttpServletRequest request) throws Exception {
		 
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("candidates/candidatesReports");
		 
		 Long candidateCount = candidateService.getCandidatesCountByCandidateTypeOrgLocationID(orgLocations.getId(), "Candidate");
		 Long consultantCount = candidateService.getCandidatesCountByCandidateTypeOrgLocationID(orgLocations.getId(), "Consultant");

		 model.addObject("consultantCount", consultantCount);
		 model.addObject("candidateCount", candidateCount);
		 
		 return model;
		
	 }	 
	 
	
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}

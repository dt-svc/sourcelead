package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ProfessionalProfileController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	/* @PreAuthorize("hasRole('ROLE_CROWDSOURCING_PROFILE')")*/
	 @RequestMapping(value = "/professionalProfile")
		public ModelAndView professionalProfile(HttpServletRequest request) throws Exception {
		        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				ModelAndView model = new ModelAndView();
				Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Profile);
				
				request.getSession().setAttribute("sessionData",sessionData);
				
				if(null != loginUser && StringUtils.isNotEmpty(loginUser.getCountry())){
					
					Countries countries = countriesService.getCountriesByCountryName(loginUser.getCountry());
					if(null != countries){
					   model.addObject("loginCountryName", countries.getName());
					   model.addObject("currencyCode", countries.getCurrency());
					}
			        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
			        model.addObject("statesList", statesList);
				}
				List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
				model.addObject("educationsList", educationsList);
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				model.addObject("skillsList", skillsList);
				
				List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
				model.addObject("documentsList", documentsList);
				
				
				List<String> visaList = lookUpDataService.getAllVisaByCategory();
				model.addObject("visaList", visaList);
				
				Integer candidateResumeCount =  candidateResumesService.getCandidateResumeCountByResumeId(candidateId);
				if(candidateResumeCount > 0 ){
					model.setViewName("professional/profile");
				}else{
					model.setViewName("professional/updateProfessionalProfile");
				}
				
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("candidateId", candidateId);
				return model;
	}
	 
	 @RequestMapping(value = "/getProfessionalInfo" , method = RequestMethod.GET)
		public String getProfessionalInfo(@RequestParam(value="candidateStatus", required=false) String  candidateStatus,HttpServletRequest request) throws Exception {
		        String resultJson;
		        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		        Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		        //Candidate candidate =	candidateService.getProfessionalInfoByLoginUserIdAndCandidateType(loginUser.getId(), candidateStatus);
		        if(null != candidate.getConsultantUser()){
		        	candidate.setFirstName(candidate.getConsultantUser().getFirstName());
		        	if(null != candidate.getConsultantUser().getLastName()){
		        		candidate.setLastName(candidate.getConsultantUser().getLastName());	
		        	}
			        if(null != candidate.getConsultantUser().getFacebook()){
			        	candidate.setFacebook(candidate.getConsultantUser().getFacebook());	
			        }
			        if(null != candidate.getConsultantUser().getTwitter()){
			        	candidate.setTwitter(candidate.getConsultantUser().getTwitter());
			        }
			        if(null != candidate.getConsultantUser().getGooglePlus()){
			        	candidate.setGooglePlus(candidate.getConsultantUser().getGooglePlus());	
			        }
			        if(null != candidate.getConsultantUser().getLinkedIn()){
			        	candidate.setLinkedIn(candidate.getConsultantUser().getLinkedIn());
			        }
			        	
		        }
		        
		        resultJson = commonUtil.toJSON(candidate);
				return resultJson;
	}
	 
	 
	 @RequestMapping(value = "/professionalPraseCandidate")
		public ModelAndView addCandidate(HttpServletRequest request) throws Exception {
		 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			model.setViewName("professional/professionalPraseCandidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			Countries countries = null;
			if(isNullOrEmpty(loginUser.getProfessionalRegisterCountry())){
				countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			}else{
				countries = countriesService.getCountriesByCountryName(loginUser.getProfessionalRegisterCountry());
			}
			
			if(null != countries){
	         model.addObject("loginCountryName", countries.getName());
	         model.addObject("currencyCode", countries.getCurrency());
			}
			
			List<String> visaList = lookUpDataService.getAllVisaByCategory();
			model.addObject("visaList", visaList);
			
            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Profile);
			
			request.getSession().setAttribute("sessionData",sessionData);
			
			model.addObject("skillsList", skillsList);
			model.addObject("candidateId", candidateId);
			
			return model;
	    }
	 
	 @RequestMapping(value = "/professionalAddProfile")
		public ModelAndView professionalAddProfile(HttpServletRequest request) throws Exception {
		 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			model.setViewName("professional/professionalAddProfile");
			model.addObject("sourceleadUrl", sourceleadUrl);
			Countries countries = null;
			
			if(isNullOrEmpty(loginUser.getProfessionalRegisterCountry())){
				countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			}else{
				countries = countriesService.getCountriesByCountryName(loginUser.getProfessionalRegisterCountry());
			}
			
			if(null != countries){
	         model.addObject("loginCountryName", countries.getName());
	         model.addObject("currencyCode", countries.getCurrency());
			}
			
			List<String> visaList = lookUpDataService.getAllVisaByCategory();
			model.addObject("visaList", visaList);
			
            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			
			model.addObject("skillsList", skillsList);
			model.addObject("candidateId", candidateId);
			
			return model;
	    }
	 
}

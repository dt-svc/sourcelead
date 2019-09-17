package com.bis.sourcelead.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CandidateGlobalUtil;
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
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	CandidateCommentsService candidateCommentsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SourceleadTagsService sourceleadTagsService;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	
	@Autowired
	CandidateGlobalUtil candidateGlobalUtil;
 
	
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
				/*if(candidateResumeCount > 0 ){
					model.setViewName("professional/profile");
				}else{
					model.setViewName("professional/updateProfessionalProfile");
				}*/
				model.setViewName("professional/profile");
				model.addObject("candidateResumeCount", candidateResumeCount);
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("candidateId", candidateId);
				return model;
	}
	 
	 @RequestMapping(value = {"/getProfessionalInfo", "/rest/getProfessionalInfo"}, method = RequestMethod.GET)
		public Candidate getProfessionalInfo(@RequestParam(value="candidateId", required=false) Long  candidateId,@RequestParam(value="locationCode", required=false) String  locationCode,HttpServletRequest request) throws Exception {
		        Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		        OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				
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
			        
			        UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(candidate.getConsultantUser().getId(), orgLocations.getId());
			        if(userRole != null && StringUtils.isNotBlank(userRole.getDesignation())) {
			        	candidate.setDesignation(userRole.getDesignation());
			        }
			        	
		        }
		        Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		        candidate.setTagsList(tagsList);
				Set<String> candidateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.CANDIDATES.getValue(), candidate.getId());
				if(candidateTagsList != null && !candidateTagsList.isEmpty()) {
					StringBuilder candidateTagsStr = new StringBuilder();
					for(String tags : candidateTagsList) {
						candidateTagsStr = candidateTagsStr.append(tags).append(",");
					}
					candidateTagsStr.toString();
					candidate.setCandidateTags(candidateTagsStr.substring(0,candidateTagsStr.length()-1));
				}
		        
		        if (candidate.getOrgRelationId() != null) {
					if (candidate.getOrgRelationId().getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
						candidate.setVendorName(candidate.getOrgRelationId().getLocationId().getOrgLocationName());
					}
					if (candidate.getOrgRelationId().getLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
						candidate.setVendorName(candidate.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					}

				}
		        
		        
		        if(candidate.getSpocId() != null){
		        candidate.setVendorContactName(candidate.getSpocId().getVendorUser().getFullName());
		        }
		        int percentage = candidateService.getCandidatePercentage(candidate,orgLocations);
		        candidate.setProfilePercentage(percentage);
		       /* if(candidate.getConsultantUser() != null) {
		        	 candidate.getConsultantUser().setPassword(null);
		        }*/
		        List<CandidateComments> candidateCommentsList = candidateCommentsService.getAllCommentsOnCandidateByCandidateIdAndOrgLocation(candidate.getId(), orgLocations);
		        candidate.setCommentsCount(String.valueOf(candidateCommentsList.size()));
		        candidate.setDateofBirthString(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), candidate.getDateOfBirth()));
				return candidate;
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
	 
	 
	 @RequestMapping(value = {"/getGlobalCandidateInfo", "/rest/getGlobalCandidateInfo"}, method = RequestMethod.GET)
		public CandidateGlobal getGlobalCandidateInfo(@RequestParam(value="candidateId", required=false) Long  candidateId,@RequestParam(value="locationCode", required=false) String  locationCode,HttpServletRequest request) throws Exception {
		        CandidateGlobal candidate = candidateGlobalService.getCandidateGlobalByCandidateId(candidateId);
		        OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				
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
			        
			        UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(candidate.getConsultantUser().getId(), orgLocations.getId());
			        if(userRole != null && StringUtils.isNotBlank(userRole.getDesignation())) {
			        	candidate.setDesignation(userRole.getDesignation());
			        }
			        	
		        }
		        Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		        candidate.setTagsList(tagsList);
				Set<String> candidateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.CANDIDATES.getValue(), candidate.getId());
				if(candidateTagsList != null && !candidateTagsList.isEmpty()) {
					StringBuilder candidateTagsStr = new StringBuilder();
					for(String tags : candidateTagsList) {
						candidateTagsStr = candidateTagsStr.append(tags).append(",");
					}
					candidateTagsStr.toString();
					candidate.setCandidateTags(candidateTagsStr.substring(0,candidateTagsStr.length()-1));
				}
		        
		        if (candidate.getOrgRelationId() != null) {
					if (candidate.getOrgRelationId().getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
						candidate.setVendorName(candidate.getOrgRelationId().getLocationId().getOrgLocationName());
					}
					if (candidate.getOrgRelationId().getLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
						candidate.setVendorName(candidate.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					}

				}
		        
		        
		        if(candidate.getSpocId() != null){
		        candidate.setVendorContactName(candidate.getSpocId().getVendorUser().getFullName());
		        }
		        int percentage = candidateGlobalUtil.getCandidatePercentage(candidate,orgLocations);
		        candidate.setProfilePercentage(percentage);
		        List<CandidateComments> candidateCommentsList = candidateCommentsService.getAllCommentsOnCandidateGlobalByCandidateIdAndOrgLocation(candidate.getId(), orgLocations.getId());
		        candidate.setCommentsCount(String.valueOf(candidateCommentsList.size()));
		        candidate.setDateofBirthString(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), candidate.getDateOfBirth()));
				return candidate;
	}
	 
}

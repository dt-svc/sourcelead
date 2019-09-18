package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.ContactsGroupService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.CrowdSourcingUserBankAccountsService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ProfessionalRequirementsController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;
	
	@Value("${atsUrl}")
	private String atsUrl;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	RequirementReferService requirementReferService;
	
	@Autowired
	ReferContactsService referContactsService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CrowdSourcingUserBankAccountsService crowdSourcingUserBankAccountsService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	private ContactsGroupService contactsGroupService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	 
	    @RequestMapping(value = "/bankAccountDetails")
	 	public ModelAndView bankAccountDetails(HttpServletRequest request) throws Exception {
	    	
	    	    User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
	    	
	 			ModelAndView model = new ModelAndView();
	 			model.setViewName("professional/bankAccountDetails");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			
	 			CrowdSourcingUserBankAccounts bankAccounts = crowdSourcingUserBankAccountsService.getUserBankAccountByUserId(user.getId());
	 			if(null != bankAccounts){
	 				model.addObject("bankAccounts", bankAccounts);
	 			}
	 			
	 			return model;
	 	}
	
	
	 @RequestMapping(value = "/professionalRequirements")
		public ModelAndView professionalProfile(HttpServletRequest request,@RequestParam(value="requirementUUID", required=false) String  requirementUUID,
				@RequestParam(value="referUUID", required=false) String  referUUID) throws Exception {
		    
		        Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		        User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		        Countries countries = null;
		        
		        ModelAndView model = new ModelAndView();
				
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Requirement);
				
				request.getSession().setAttribute("sessionData",sessionData);
				
				
				
			    List<Countries> countryList = countriesService.getAllCountries();
				model.addObject("countryList", countryList);
				
				if(StringUtils.isNotEmpty(user.getProfessionalRegisterCountry())){
					countries = countriesService.getCountriesByCountryName(user.getProfessionalRegisterCountry());
				}else{
					countries = countriesService.getCountriesByCountryName(user.getCountry());
				}
				
				
				if(null != countries){
					   model.addObject("loginCountryName", countries.getName());
					   List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
				       model.addObject("statesList", statesList);
				}
			       
				
				if(null != candidateId){
					Integer resumesCount = candidateResumesService.getCandidateResumeCountByCandidateId(candidateId);
					model.addObject("resumesCount", resumesCount);
					model.addObject("candidateId", candidateId);
				}
				
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				model.addObject("skillsList", skillsList);
				
				List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
				model.addObject("industryList", industryListWithoutId);
				
			    if(!isNullOrEmpty(requirementUUID)){
			    	Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
				    	if(null != requirement){
				    		
				    		RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionByCandidateIdAndRequirementId(candidateId, requirement.getId());
				    		if(null != submission){
				    			model.addObject("publicSubmissionId", submission.getId());
				    		}
				    		model.addObject("publicRequirementId", requirement.getId());
				    		model.addObject("publicRequirementTitle", requirement.getJobTitle());
				    	}
			    }
			    
			    if(!isNullOrEmpty(referUUID)){
			    	RequirementRefer refer = requirementReferService.getRequirementReferByReferUUID(referUUID);
			    	if(null != refer){
			    		model.addObject("publicReferId", refer.getId());
			    	}
			    }
				
				model.setViewName("professional/professionalRequirements");
				model.addObject("sourceleadUrl", sourceleadUrl);
				
				return model;
	}
	 
	 @RequestMapping(value = "/professionalRequirementProfile/{requirementId}", method = RequestMethod.GET)
		public ModelAndView professionalRequirementProfile(HttpServletRequest request) throws Exception {
		        Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		        ModelAndView model = new ModelAndView();
				SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
				if(sessionData == null)
				{
					sessionData = new SessionMaintainanceData();
				}
				sessionData.setNav(Constants.Requirement);
				request.getSession().setAttribute("sessionData",sessionData);
				model.setViewName("professional/professionalRequirementProfile");
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("candidateId", candidateId);
				
				return model;
	}
	 
	 @RequestMapping(value = "/requirementProfile/{requirementUUID}", method = RequestMethod.GET)
		public @ResponseBody void userRequirementSummary(@PathVariable String requirementUUID, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			StringBuilder redirectUrl = new StringBuilder();
			response.sendRedirect(redirectUrl.append("/ats/requirementProfileDetails?requirementUUID=").append(requirementUUID).toString());

	 }
	 
	 @RequestMapping(value = "/requirementProfile/{requirementUUID}/{referUUID}", method = RequestMethod.GET)
		public @ResponseBody void userSummary(@PathVariable String requirementUUID,@PathVariable String referUUID, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			StringBuilder redirectUrl = new StringBuilder();
			response.sendRedirect(redirectUrl.append("/ats/requirementProfileDetails?requirementUUID=").append(requirementUUID).append("&referUUID=").append(referUUID).toString());

	 }
	 
	 @RequestMapping(value = "/requirementProfileDetails")
		public ModelAndView userSummaryDetails(HttpServletRequest request, @RequestParam(value = "requirementUUID", required = false) String requirementUUID,
				@RequestParam(value = "referUUID", required = false) String referUUID) throws Exception {
		 Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");	 
		 ModelAndView model = new ModelAndView();
			  SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			 if(sessionData == null)
			 {
			 	sessionData = new SessionMaintainanceData();
			 }
			 
			 if(null != candidateId){
				 Integer resumesCount = candidateResumesService.getCandidateResumeCountByCandidateId(candidateId);
				 model.addObject("resumesCount", resumesCount);
			 }
			 
			 
			sessionData.setNav(Constants.Requirement);
			request.getSession().setAttribute("sessionData",sessionData);
			model.setViewName("professional/professionalRequirementProfile");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("requirementUUID", requirementUUID);
			model.addObject("referUUID", referUUID);
			
			model.addObject("candidateId", candidateId);
			return model;

		}
	 
	    @RequestMapping(value = "/getRequirementProfessionalProfileDetails" , method = RequestMethod.GET)
		public  @ResponseBody String getRequirementProfessionalProfileDetails(@RequestParam(value="requirementUUID", required=false) String  requirementUUID,
				@RequestParam(value="referUUID", required=false) String  referUUID,HttpServletRequest request) throws Exception {
		        String resultJson;
		        User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		        Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		        Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		        if(null != requirement.getOrgLocationId()){
		        	if(null != requirement.getOrgLocationId().getOrg()){
		        		requirement.setOrgImageUrl(requirement.getOrgLocationId().getOrg().getFileUrl());
		        		if(null != requirement.getOrgLocationId().getOrg().getOrgCode()){
		        			requirement.setOrgcode(requirement.getOrgLocationId().getOrg().getOrgCode());	
		        		}
		        	}
		        }
		        
		        if(null !=  requirement.getAppliedProfessionals() && null != user && null != candidateId){
	    			  List<Long> userId = new ArrayList<Long>();
	    			  String[] appliedProfessionals = requirement.getAppliedProfessionals().split(",");
	 					for (int i=0; i < appliedProfessionals.length; i++){
	 						userId.add(Long.parseLong(appliedProfessionals[i]));
	 					 }
	 					boolean flag = userId.contains(user.getId());
	 					if(flag == true){
	 						requirement.setIsJobApplied(Constants.YES);
	 						RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirement.getId(), candidateId);
	 		 	    		   if(null != submission){
	 		 	    			 requirement.setSubmissionId(submission.getId());
	 		 	    		   }
	 					}else{
	 						requirement.setIsJobApplied(Constants.NO);
	 					} 
	    		     }else{
	    		    	 requirement.setIsJobApplied(Constants.NO);
	    		      }
		        
		        if(!isNullOrEmpty(referUUID)){
			    	RequirementRefer refer = requirementReferService.getRequirementReferByReferUUID(referUUID);
			    	if(null != refer){
			    		requirement.setReferId(refer.getId());
			    	}
			    }
				
		        //requirement location list
		        List<String> requirementLocationsList = new ArrayList<>();
		        List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations location : locationsList) {
						requirementLocationsList.add(location.getLocation());
					}
				}
				requirement.setLocationsList(requirementLocationsList);
				
		        resultJson = commonUtil.toJSON(requirement);
				return resultJson;
	}
	 
	 
	 
	 @RequestMapping(value = "/referRequirementToProfessional")
		public ModelAndView referRequirementToProfessional(@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
				@RequestParam(value = "contactUUID", required = false) String contactUUID,
				@RequestParam(value = "referUUID", required = false) String referUUID) {
			
		 ModelAndView model = new ModelAndView();
		 model.setViewName("professional/requirementDetailsSummary");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("SourceLeadWeb_atsUrl", atsUrl);
		 model.addObject("sourceleadLoginUrl", sourceleadLoginUrl);
		 model.addObject("contactUUID", contactUUID);
		 model.addObject("requirementUUID", requirementUUID);
		 model.addObject("referUUID", referUUID);
		 return model;
			
		}
	 
		

	 @RequestMapping(value = "/referRequirementList")
	 public ModelAndView referRequirementList(HttpServletRequest request) throws Exception {

	 		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("professional/referRequirementList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		 
		 User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		 List<ContactsGroup> contactsGroupList = contactsGroupService.getContactsGroupListByUserId(user.getId());
		 model.addObject("contactsGroupList", contactsGroupList);
		 model.addObject("contactsGroupListSize", contactsGroupList.size());
		
		return model;
		}
			 
	 @RequestMapping(value = "/validateReferedEmail",  method = RequestMethod.GET)
	   public @ResponseBody String validateReferedEmail(@RequestParam(value="email", required=false) String email,
	   		@RequestParam(value="requirementId", required=false) Long requirementId,
	   		HttpServletRequest request) throws Exception {
		  
		  User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		  
		  if(!user.getEmail().equalsIgnoreCase(email)){
			  boolean isRefer = requirementReferService.isReferToRequirement(email, requirementId);
			  if(isRefer == true){
				  return "Invalid"; 
			  }else{
				  boolean flag = requirementReferService.isProfessionalReferToRequirement(email, requirementId, user.getId());
				  if(flag == true){
					  return "Invalid";
				  }else{
					  return "Success";
				  }
			  } 
		  }else{
			  return "Invalid Email";
		  }
		  
	   }
	 
	 
	 

	 @RequestMapping(value = "/validateContactEmail",  method = RequestMethod.GET)
	  public @ResponseBody String validateContactEmail(@RequestParam(value="contactEmail", required=false) String contactEmail,
	   		HttpServletRequest request) throws Exception {
		  
		  User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		  
		  ReferContacts referContacts = referContactsService.getReferContactsByEmails(user.getId(), contactEmail);
		  if(null != referContacts){
			  return "Invalid";
		  }else{
			  return "Success";
		  }
		  
	   }
	 
	@RequestMapping(value = "/getProfessionalResumesCount",  method = RequestMethod.GET)
	   public @ResponseBody Integer getProfessionalResumesCount(HttpServletRequest request) throws Exception {
		  Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		  Integer resumesCount = candidateResumesService.getCandidateResumeCountByCandidateId(candidateId);
		  return resumesCount;
	   }
 
 
	 @RequestMapping(value = "/referredToMePortletList")
	 @ResponseBody
		public ModelAndView referredToMePortletList(HttpServletRequest request,@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "requirementStatus", required = false) String requirementStatus) throws Exception {
		 ModelAndView model = new ModelAndView();
			  SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			 if(sessionData == null)
			 {
			 	sessionData = new SessionMaintainanceData();
			 }
			sessionData.setNav(Constants.Dashboard);
			request.getSession().setAttribute("sessionData",sessionData);
			model.setViewName("professional/referredToMePortletList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("startDate", startDate);
			model.addObject("endDate", endDate);
			model.addObject("requirementStatus", requirementStatus);
			return model;

		}
	 
	 @RequestMapping(value = "/referredByMePortletList")
	 @ResponseBody
		public ModelAndView referredByMePortletList(HttpServletRequest request,@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "requirementStatus", required = false) String requirementStatus) throws Exception {
		 ModelAndView model = new ModelAndView();
			  SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			 if(sessionData == null)
			 {
			 	sessionData = new SessionMaintainanceData();
			 }
			sessionData.setNav(Constants.Dashboard);
			request.getSession().setAttribute("sessionData",sessionData);
			model.setViewName("professional/referredByMePortletList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("startDate", startDate);
			model.addObject("endDate", endDate);
			model.addObject("requirementStatus", requirementStatus);
			return model;

		}
	 
	 @RequestMapping(value = "/recruiterSubmttedJobList")
	 @ResponseBody
		public ModelAndView recruiterSubmttedJobList(HttpServletRequest request,@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "requirementStatus", required = false) String requirementStatus) throws Exception {
		 ModelAndView model = new ModelAndView();
			  SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			 if(sessionData == null)
			 {
			 	sessionData = new SessionMaintainanceData();
			 }
			sessionData.setNav(Constants.Dashboard);
			request.getSession().setAttribute("sessionData",sessionData);
			model.setViewName("professional/recruiterSubmttedJobList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("startDate", startDate);
			model.addObject("endDate", endDate);
			model.addObject("requirementStatus", requirementStatus);
			return model;

		}
	 
	 
	 
	 @RequestMapping(value = "/jobAlertRequirementSummary")
		public ModelAndView jobAlertRequirementSummary(@RequestParam(value = "requirementUUID", required = false) String requirementUUID) {
			
		 ModelAndView model = new ModelAndView();
		 model.setViewName("jobAlert/jobAlertRequirementSummary");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("requirementUUID", requirementUUID);
		 return model;
			
		}
	 
	 
	 @RequestMapping(value = "/downloadContactCSVTemplate")
		public void downloadContactCSVTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
			File file = null;
			String fn;	
		    		 fn = "/templates/contact_csv_template.csv";
		    		 URL url = getClass().getResource(fn);
				     file = new File(url.toURI());

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					StringBuilder fileNameSB = new StringBuilder();
					response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
					response.setContentLength((int) file.length());
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
					inputStream.close();
					response.getOutputStream().close();
					response.getOutputStream().flush();
			
		}
	 
	 /* @PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_PROFILE')")
	  @RequestMapping(value = "/getProfessionalInterviewsEvent")
	  public  String getProfessionalInterviewsEvent(String dateRange, HttpServletRequest request) throws Exception {
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			 OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 return crowdSourcingRequirementsRestController.getProfessionalInterviewsEventDashBoard(dateRange, candidateId,loginOrgLocations);
	  }*/
}

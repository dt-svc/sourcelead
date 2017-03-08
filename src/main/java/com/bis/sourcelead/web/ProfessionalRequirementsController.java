package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.CrowdSourcingUserBankAccountsService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ProfessionalRequirementsController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
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
	
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
	 @RequestMapping(value = "/register")
	 	public ModelAndView register(@RequestParam(value = "contactUUID", required = false) String contactUUID) throws Exception {
	 			ModelAndView model = new ModelAndView();
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			if(!isNullOrEmpty(contactUUID)){
	 				ReferContacts referContacts = referContactsService.getReferContactsByUUID(contactUUID);
	 				if(null == referContacts.getContactUser()){
	 					model.addObject("firstName",referContacts.getFirstName());
		 				model.addObject("lastName",referContacts.getLastName());
		 				model.addObject("email",referContacts.getContactUserEmail());
		 				model.addObject("contactUUID", contactUUID);
		 				model.setViewName("registration");
	 				}else{
	 					User user = userService.getUserById(referContacts.getContactUser().getId());
	 					if(null == user.getPassword()){
	 						model.addObject("userName", user.getUserName());
			 				model.setViewName("resetloginCredentials");
	 					}else{
	 						model.setViewName("login");
	 					}
	 				}
	 				
	 			}else{
	 				model.setViewName("registration");
	 			}
	 			
	 			return model;
	 	}
	 
	 @RequestMapping(value = "/resetloginCredentials")
	 	public ModelAndView resetloginCredentials(HttpServletRequest request) {
	 			ModelAndView model = new ModelAndView();
	 			model.setViewName("resetloginCredentials");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			
	 			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg");
	  			if(!isNullOrEmpty(msg)){
	  				model.addObject("msg", msg);
	  				SourceLeadSessionManager.removeSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg");
	  			}
	 			return model;
	 	}
	 
	    @PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
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
	
	
	 
	 
	
	 @PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
	 @RequestMapping(value = "/professionalRequirements")
		public ModelAndView professionalProfile(HttpServletRequest request) throws Exception {
		    
		        Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		        User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		        
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
				
				if(StringUtils.isNotEmpty(user.getCountry())){
				
					Countries countries = countriesService.getCountriesByCountryName(user.getCountry());
					if(null != countries){
					   model.addObject("loginCountryName", countries.getName());
					}
			        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
			        model.addObject("statesList", statesList);
				}
				
				/*List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
				model.addObject("skillsList", skillsList);*/
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				model.addObject("skillsList", skillsList);
				
			    
				
				model.setViewName("professional/professionalRequirements");
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("candidateId", candidateId);
				
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
		public @ResponseBody void userSummary(@PathVariable String requirementUUID, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			StringBuilder redirectUrl = new StringBuilder();
			response.sendRedirect(redirectUrl.append("/sourcelead/requirementProfileDetails?requirementUUID=").append(requirementUUID).toString());

		}
	 
	 @RequestMapping(value = "/requirementProfileDetails")
		public ModelAndView userSummaryDetails(HttpServletRequest request, @RequestParam(value = "requirementUUID", required = false) String requirementUUID) throws Exception {
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
			model.addObject("requirementUUID", requirementUUID);
			model.addObject("candidateId", candidateId);
			return model;

		}
	 
	    @RequestMapping(value = "/getRequirementProfessionalProfileDetails" , method = RequestMethod.GET)
		public  @ResponseBody String getRequirementProfessionalProfileDetails(@RequestParam(value="requirementUUID", required=false) String  requirementUUID,HttpServletRequest request) throws Exception {
		        String resultJson;
		        User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		        Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		        if(null != requirement.getOrgLocationId()){
		        	if(null != requirement.getOrgLocationId().getOrg()){
		        		if(null != requirement.getOrgLocationId().getOrg().getOrgCode()){
		        			requirement.setOrgcode(requirement.getOrgLocationId().getOrg().getOrgCode());	
		        		}
		        	}
		        }
		        
		        if(null !=  requirement.getAppliedProfessionals() && null != user){
	    			  List<Long> userId = new ArrayList<Long>();
	    			  String[] appliedProfessionals = requirement.getAppliedProfessionals().split(",");
	 					for (int i=0; i < appliedProfessionals.length; i++){
	 						userId.add(Long.parseLong(appliedProfessionals[i]));
	 					 }
	 					boolean flag = userId.contains(user.getId());
	 					if(flag == true){
	 						requirement.setIsJobApplied(Constants.YES);
	 					}else{
	 						requirement.setIsJobApplied(Constants.NO);
	 					} 
	    		     }else{
	    		    	 requirement.setIsJobApplied(Constants.NO);
	    		      }
		        
		        resultJson = commonUtil.toJSON(requirement);
				return resultJson;
	}
	 
	 
	 
	 @RequestMapping(value = "/referRequirementToProfessional")
		public ModelAndView referRequirementToProfessional(@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
				@RequestParam(value = "contactUUID", required = false) String contactUUID) {
			
		 ModelAndView model = new ModelAndView();
		 model.setViewName("professional/requirementDetailsSummary");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("contactUUID", contactUUID);
		 model.addObject("requirementUUID", requirementUUID);
		 return model;
			
		}
	 
		
	 @RequestMapping(value = "/referRequirementList")
	 public ModelAndView referRequirementList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("professional/referRequiremnetList");
		model.addObject("sourceleadUrl", sourceleadUrl);
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
	 
}

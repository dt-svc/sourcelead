package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationRestController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);
	@Autowired
	 RegistrationService registrationService;
	
	 @Autowired
	 SourceleadAuthenticationProvider surceleadAuthenticationProvider;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 PasswordResetsSevice passwordResetsSevice;
	 
	 @Autowired
	 OrgService orgService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 UserRoleService userRoleService;
	 
	 @RequestMapping(value = "/getOrgsList")
		public @ResponseBody String getUsersList(HttpServletRequest request,
				@RequestParam(value="searchByName", required=false) String searchByName,
			@RequestParam(value = "userState", required = false) String userState) {

		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<Org> orgsList = new ArrayList<>();

		ArrayList<Org> regOrgsList = new ArrayList<Org>();

		try {
			orgsList = orgService.getEnrolledOrgs(org.getId(), userState, searchByName);
			for(Org regOrg : orgsList){
				regOrg.setEmail(regOrg.getUser().getEmail());
				regOrg.setIsMailSent(regOrg.getUser().getIsMailSent());
				regOrg.setUserState(regOrg.getUser().getUserState());
				regOrg.setMobilePhone(regOrg.getUser().getMobilePhone());
				UserRole userRole = userRoleService.getUserByUserId(regOrg.getUser().getId());
				regOrg.setUserState(userRole.getUserState());
				regOrgsList.add(regOrg);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		resultJson = commonUtil.toJSON(regOrgsList);
		return resultJson;

	}
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.POST)
	   public String addCompanyRegistration(
				@RequestParam(value="json", required=false) String json ,
				@RequestParam(value="modulesList", required=false) String modulesList ,
				@RequestParam(value="submodulesList", required=false) String submodulesList ,
	   			HttpServletRequest request) {
		try {

				String json1 = "{"+json+"}";
				Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				Registration registration =  gson.fromJson(json1,Registration.class);
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    String[] registredModules =  modulesList.split(",");
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			    for(int i=0; i<registredModules.length; i++){
			    	if(Long.parseLong(registredModules[i]) == 1l){
			    		if(isNullOrEmpty(submodulesList)){
			    			return "Invalid Sub Modules";
			    		}
			    		
			    	}
			    }
			   
				registrationService.addRegistration(registration,user,modulesList,submodulesList, orgLocations.getId() );
				SourceLeadSessionManager.setSessionAttribute(request, "orgCreateMsg", registration.getCompanyName()+" Org has created sucessfully.");
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
			return "orgList";
	     
	   }
	
	@RequestMapping(value = "/resetPasswordWithUserName" , method = RequestMethod.GET)
	public String resetPasswordWithUserName(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {
		String json1 = "{"+json+"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Registration registration = gson.fromJson(json1, Registration.class);
		User user = userService.findByUserName(registration.getEmail());
		if (null != user) {
			try {
				PasswordResets passwordResets = passwordResetsSevice.getPasswordResetsByUserId(user.getId());
				if (null == passwordResets) {
					String verificationCode = registrationService.addPasswordResets(user);
					registrationService.sendResetPasswordEmail(user, verificationCode);
				} else {
					passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
					passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
					// email to user
					registrationService.sendResetPasswordEmail(user, passwordResets.getVerificationCode());
				}

				SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordMsg",
						"Verification Code sent to your mail.");
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return "valid";
		} else {
			return "Invalid";
		}

	}
	

	@RequestMapping(value = "/verifyResetPassword" , method = RequestMethod.GET)
	   public String verifyResetPassword(@RequestParam(value="json", required=false) String json ,HttpServletRequest request) throws Exception {
		
		String json1 = "{"+json+"}";
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Registration registration =  gson.fromJson(json1,Registration.class);
		
		//FetchType for User in PasswordResets is declared with Eager. So, User also loaded along with PasswordResets
		PasswordResets passwordResets  = passwordResetsSevice.getPasswordResetsByVerificationCode(registration.getVerificationCode());
		
		if(null != passwordResets){
			try {
				registrationService.resetPassword(passwordResets, registration.getPassword());
				registrationService.deletePasswordResets(passwordResets);
				
				//email to user
				User user = userService.findByUserName(passwordResets.getEmail());
				registrationService.sendUpdatePasswordEmail(user);
				 SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordSucessfulMsg", "Your password has reset sucessfully.");
				
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return "login";
		}else{
			return "Invalid";
		}
		
	}
	
	
	   @RequestMapping(value = "/ws/validateOrgWebsite", method = RequestMethod.GET)
	   public @ResponseBody String validateOrgWebsite(
	   		@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites) {
			String status;
			if(!isNullOrEmpty(website)){
			    String orgInfo = orgService.websiteValidation(website,sourceleadBLockedwebsites);
				if ("Website Duplicated".equalsIgnoreCase(orgInfo)) {
					status = "Website Duplicated";
				}else if("Invalid Website".equalsIgnoreCase(orgInfo)){
					status = "Invalid Website";
				}
				else{
					status = "SUCCESS";
				}
			}else{
				status = "Invalid Website";
			}
			
	    return status;
	   }
	   
	   
	   @RequestMapping(value = "/validateCompanyName", method = RequestMethod.GET)
	   public @ResponseBody String validateCompanyName(
	   		@RequestParam(value="companyName", required=false) String companyName) {
			String status = null;
			if(!isNullOrEmpty(companyName)){
			    boolean flag;
				try {
					flag = orgService.isOrgNameExisted(companyName);
					if (flag == true) {
						status = "Company Duplicated";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	       return status;
	   }
	   
	   
	   @RequestMapping(value = "/getUserDetails",  method = RequestMethod.POST)
	    public @ResponseBody String getUserDetails(@RequestParam(value="email", required=false) String email,
	    		@RequestParam(value="json", required=false) String json) {
			
		   ArrayList<String> userList = new ArrayList<String>();
			
		   User user = null;
		   
			try {
				user = userService.findByEmail(email);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(null != user)
			{
				userList.add(user.getFirstName());
				userList.add(user.getLastName());
				userList.add(user.getMobilePhone());
			}
			
			return commonUtil.toJSON(userList);
			
		}
	   
	   
	   
	   @RequestMapping(value = "/ws/emailAndWebsiteRegistrationValidation",  method = RequestMethod.GET)
	   public @ResponseBody String emailAndWebsiteRegistrationValidation(@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites,
	   		@RequestParam(value="email", required=false) String email) throws Exception {
		   return userService.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
	   }
	
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	}
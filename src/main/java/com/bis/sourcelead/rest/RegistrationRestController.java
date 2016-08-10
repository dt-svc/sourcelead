package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationRestController {
	
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
	 
	 
	 @RequestMapping(value = "/getOrgsList")
		@ResponseBody
		public String getUsersList(HttpServletRequest request,
				@RequestParam(value="searchByName", required=false) String searchByName,
				@RequestParam(value="userState", required=false) String userState) {

		 	String resultJson = null;
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			List<Org> orgsList = new ArrayList<Org>();
			
			try {
				orgsList = orgService.getEnrolledOrgs(org.getId(), userState, searchByName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			 resultJson = toJSON(orgsList);
			 return resultJson;

		}
	 
	 
	
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.POST)
	   public String addCompanyRegistration(
				@RequestParam(value="json", required=false) String json ,
				@RequestParam(value="modules", required=false) String modules ,
				@RequestParam(value="subModules", required=false) String subModules ,
	   			HttpServletRequest request) {

		try {
				json = "{"+json+"}";
				JSONObject jsonObj = new JSONObject(json);
				Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				Registration registration =  gson.fromJson(json,Registration.class);
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				
			    String[] registredModules =  modules.split(",");
			    if(registredModules[0].equalsIgnoreCase("Recruiting")){
			    	if(null == subModules || "".equalsIgnoreCase(subModules)){
			    		return "Invalid Sub Modules";
			    	}
			    }
				
				registrationService.addRegistration(registration,user,modules,subModules);
				SourceLeadSessionManager.setSessionAttribute(request, "orgCreateMsg", registration.getCompanyName()+" Org has created sucessfully.");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "orgList";
	     
	   }
	
	
	@RequestMapping(value = "/resetPasswordWithUserName" , method = RequestMethod.GET)
	   public String resetPasswordWithUserName(@RequestParam(value="json", required=false) String json ,HttpServletRequest request) throws Exception {
		
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Registration registration =  gson.fromJson(json,Registration.class);
		
		User user = userService.findByUserName(registration.getEmail());
		if(null != user){
			try {
				PasswordResets passwordResets  = passwordResetsSevice.getPasswordResetsByUserId(user.getId());
				if(null == passwordResets){
					String verificationCode = registrationService.addPasswordResets(user);
					
					//email to user
					registrationService.sendResetPasswordEmail(user,verificationCode);
				}else{
					passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
					passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
					
					//email to user
					registrationService.sendResetPasswordEmail(user,passwordResets.getVerificationCode());
				}
				
				 SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordMsg", "Verification Code sent to your mail.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "valid";
		}else{
			return "Invalid";
		}
		
	}
	

	@RequestMapping(value = "/verifyResetPassword" , method = RequestMethod.GET)
	   public String verifyResetPassword(@RequestParam(value="json", required=false) String json ,HttpServletRequest request) throws Exception {
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Registration registration =  gson.fromJson(json,Registration.class);
		
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
				e.printStackTrace();
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
		
			String status = null;
			if(!isNullOrEmpty(website)){
			    String orgInfo = orgService.websiteValidation(website,sourceleadBLockedwebsites);
				if (orgInfo.equalsIgnoreCase("Website Duplicated")) {
					status = "Website Duplicated";
				}else if(orgInfo.equalsIgnoreCase("Invalid Website")){
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
	   
	   
	   @RequestMapping(value = "/ws/emailAndWebsiteRegistrationValidation",  method = RequestMethod.GET)
	   public @ResponseBody String emailAndWebsiteRegistrationValidation(@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites,
	   		@RequestParam(value="email", required=false) String email) throws Exception {
	   	
		  String status  = userService.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
		  return status;
	   }
	   
	   
	   private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
		
	
	   protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	
	
	}
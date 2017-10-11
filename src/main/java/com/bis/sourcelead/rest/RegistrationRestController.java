package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CrowdSourcingUserRegistrationDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.RegistrationOptionalService;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.form.RestResponsetUtil;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.RegistrationOptionalServicesService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
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
	 
	 @Autowired
	 SourceleadActionsService sourceleadActionsService;
	 
	 @Autowired
	 RolesService rolesService;
	 
	 @Autowired
	 OrgLocationService orgLocationService;
	 
	 @Autowired
		private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 @Autowired
	 private RegistrationOptionalServicesService registrationOptionalServicesService;
	 
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
				OrgLocations orglocations = orgLocationService.getPrimaryOrgLocationByOrgId(regOrg.getId());
				if(null != regOrg.getUser()){
					regOrg.setEmail(regOrg.getUser().getEmail());
					regOrg.setIsMailSent(regOrg.getUser().getIsMailSent());
					regOrg.setUserState(regOrg.getUser().getUserState());
					regOrg.setMobilePhone(regOrg.getUser().getMobilePhone());
					regOrg.setRegisteredUserId(regOrg.getUser().getId());
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(regOrg.getUser().getId(),orglocations.getId());
					if(null != userRole){
						regOrg.setUserState(userRole.getUserState());
					}
				}
				
				if(null != orglocations){
					regOrg.setLocation(orglocations.getCountry());
					regOrg.setPrimaryOrgLocationId(orglocations.getId());
				}
				regOrgsList.add(regOrg);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		resultJson = commonUtil.toJSON(regOrgsList);
		return resultJson;

	}
	 
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/companyRegistration" , method = RequestMethod.POST)
	   public String addCompanyRegistration(
				@RequestParam(value="json", required=false) String json ,
				@RequestParam(value="modulesList", required=false) String modulesList ,
				@RequestParam(value="submodulesList", required=false) String submodulesList ,
	   			HttpServletRequest request) {
		try {

				String json1 = "{"+json+"}";
				JSONObject jsonObj = new JSONObject(json1);
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
			   
				registrationService.addRegistration(registration,user,modulesList,submodulesList, orgLocations.getId(),jsonObj);
				 StringBuilder orgCreateMsg = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "orgCreateMsg", orgCreateMsg.append(registration.getCompanyName()).append(" Org has created sucessfully.").toString());
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
			return "orgList";
	     
	   }
	
	
	
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/createUesrBySourceleadAdmin" , method = RequestMethod.POST)
	 public  String createUser(AddUserDTO addUserDTO, CandidateDTO candidateDTO,HttpServletRequest request) throws Exception {
		 	
		 /*json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);*/
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 registrationService.addUserBySourcelead(user, addUserDTO,candidateDTO);
	 	
		 return "success";
	   }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/changePrimaryAdmin" , method = RequestMethod.POST)
	 public  String changePrimaryAdmin(@RequestParam(value="userId", required=false) Long userId,
			 @RequestParam(value="orgLocationId", required=false) Long orgLocationId,HttpServletRequest request) throws Exception {
		 	
		 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		 User user = new User();
		 user.setId(userId);
		 
		 orgLocations.setUser(user);
		 
		 Org org = orgLocations.getOrg();
		 org.setUser(user);
		 
		 orgLocationService.updateOrgLocations(orgLocations);
		 orgService.addOrganization(org);
		 	
		 return "success";
	   }
	 
	 
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	 @RequestMapping(value = "/editOrgPrimaryUser" , method = RequestMethod.POST)
	 public  String editOrgPrimaryUser(@RequestParam(value="json", required=false) String json, 
			 @RequestParam(value="userId", required=false) Long userId ,
			 @RequestParam(value="orgLocationId", required=false) Long orgLocationId ,HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
	 	 
	 	 User primaryUser = userService.getUserById(userId);
	 	 if(null != primaryUser){
	 		 if(!isNullOrEmpty(jsonObj.getString("primaryUserFirstName"))){
	 			primaryUser.setFirstName(jsonObj.getString("primaryUserFirstName"));
	 		 }
	 		 
	 		 if(jsonObj.has("primaryUserLastName") && !isNullOrEmpty(jsonObj.getString("primaryUserLastName"))){
		 			primaryUser.setLastName(jsonObj.getString("primaryUserLastName"));
	 		 }else{
	 			primaryUser.setLastName(null);
	 		 }
	 		 
	 		 if(!isNullOrEmpty(jsonObj.getString("primaryUserEmail"))){
		 			primaryUser.setEmail(jsonObj.getString("primaryUserEmail"));
		 			primaryUser.setUserName(jsonObj.getString("primaryUserEmail"));
	 		 }
	 		 
	 		 if(jsonObj.has("mobilePhone") && !isNullOrEmpty(jsonObj.getString("mobilePhone"))){
	 			primaryUser.setMobilePhone(jsonObj.getString("mobilePhone"));
	 		 }else{
	 			primaryUser.setMobilePhone(null);
	 		 }
	 		 
	 		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
	 		primaryUser.setPassword(sourceleadPasswordEncoder.encode(password));
	 		userService.addUser(primaryUser);
	 		
	 		userService.sendEmail(primaryUser, user, password,orgLocations.getId());
	 		
	 	 }
	 	
		 return "success";
	   }
	
	
	 @RequestMapping(value = "/registerAsProfessional" , method = RequestMethod.POST)
	 public  String registerAsProfessional(CrowdSourcingUserRegistrationDTO csuRegistrationDTOO,
			 @RequestParam(value="contactUUID", required=false) String contactUUID,
			 HttpServletRequest request) throws Exception {
		
		 String result = registrationService.saveProfessional(csuRegistrationDTOO,contactUUID);
		 SourceLeadSessionManager.setSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg",
					"Username sent to your mail.");
	 	
		 return result;
	   }
	 
	 @RequestMapping(value = "/registerAsIndividualRecruiter" , method = RequestMethod.POST)
	 public  String registerAsIndividualRecruiter(CrowdSourcingUserRegistrationDTO csUserRegistrationDTO, HttpServletRequest request) throws Exception {
		
		 String result = registrationService.saveIndividualRecruiter(csUserRegistrationDTO);
		 SourceLeadSessionManager.setSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg",
					"Username sent to your mail.");
	 	
		 return result;
	   }
	 
	 
	 
	 
	 @RequestMapping(value = "/registerAsProfessionalFromSocialInviter" , method = RequestMethod.POST)
	 public  String registerAsProfessionalFromSocialInviter(@RequestParam(value="json", required=false) String json,
			 HttpServletRequest request) throws Exception {
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
		 registrationService.saveProfessionalFromSocialInviter(jsonObj);
	 	
		 return "success";
	   }
	 
	 @RequestMapping(value = "/registerAsIndividualRecruiterFromSocialInviter" , method = RequestMethod.POST)
	 public  String registerAsIndividualRecruiterFromSocialInviter(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
		 registrationService.saveIndividualRecruiterFromSocialInviter(jsonObj);
		 return "success";
	   }
	 
	 
	 
	 @RequestMapping(value = "/resetCredentials" , method = RequestMethod.POST)
	 public  String resetCredentials(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 User user = userService.getUserByUserName(jsonObj.getString("userName"));
		 if(null != user){
			 user.setPassword(sourceleadPasswordEncoder.encode(jsonObj.getString("password")));
			 prepareRegisterOptionalServicesToAllForms(jsonObj,user);
			 userService.addUser(user);
		 }
		 return "success";
	   }
	 
	 
	 public void prepareRegisterOptionalServicesToAllForms(JSONObject jsonObj, User user) throws JSONException{
			if(jsonObj.has("Wouldliketo") && !isNullOrEmpty(jsonObj.getString("Wouldliketo"))){
					String WouldliketoString  = jsonObj.getString("Wouldliketo");
				    String Wouldlike = replacejsonToString(WouldliketoString);
					user.setRegisterOptionalServiceId(Wouldlike);
			   }
		}
	 
	 public String replacejsonToString(String replaceString){
			String newReplacedString = null;
			newReplacedString = replaceString.replaceAll("\\[", "").replaceAll("\\]","");
			newReplacedString = newReplacedString.replaceAll("\"", "");
			return newReplacedString;
		}
	 
	 
	 @RequestMapping(value = "/getOptionalValueList" , method = RequestMethod.POST)
	 public  String getOptionalValueList(HttpServletRequest request) throws Exception {
	 			String registerTypeTwo = "Would like to";
				List<RegistrationOptionalService> WouldLikeToList = registrationOptionalServicesService.getRegistraionOptionalServiceListByRegisterType(registerTypeTwo);
				return commonUtil.toJSON(WouldLikeToList);
	        }
	 
	 
	
	   @PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	   @RequestMapping(value = "/updateOrgModules" , method = RequestMethod.POST)
	   public String updateOrgModules(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
				@RequestParam(value="modulesList", required=false) String modulesList ,
				@RequestParam(value="submodulesList", required=false) String submodulesList ,
			    @RequestParam(value="actionIds", required=false) String actionIds,
			    HttpServletRequest request) throws Exception {
		
		     OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			 String orgModules = "";
			 StringBuilder strbul = null; 
			 StringBuilder strbul2 = null; 
			 StringBuilder strbul3 = null; 
			 
			 if(!isNullOrEmpty(submodulesList)){
					String[] registeredSubModules =  submodulesList.split(",");
					for(int i=0; i<registeredSubModules.length; i++){
							orgModules = orgModules+registeredSubModules[i]+",";
							
							 Long moduleId = null;
							 Long tabId = null;
							 
							if(registeredSubModules[i].equals("3")){
								moduleId = (long) Constants.MODULE_VMS;
								tabId = (long) Constants.TAB_VENDOR;
							}
							else if(registeredSubModules[i].equals("2")){
								moduleId = (long) Constants.MODULE_ATS;
								tabId = (long) Constants.TAB_CLIENT;
							}
								List<Long> actionsList = sourceleadActionsService.getSourceLeadActionsByModuleId(moduleId);
								
								Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
								 List<Long> adminRoleActonList = new ArrayList<Long>();
								 for (String s : adminrole.getActionId().split(",")){
									 adminRoleActonList.add(Long.parseLong(s));
								 }
								 
								 adminRoleActonList.retainAll(actionsList);
								 actionsList.removeAll(adminRoleActonList);
								 
								 strbul  = new StringBuilder();
							     Iterator<Long> iter = actionsList.iterator();
							     while(iter.hasNext())
							     {
							         strbul.append(iter.next());
							        if(iter.hasNext()){
							         strbul.append(",");
							        }
							     }
							     strbul.toString();
								
								String adminids = adminrole.getActionId()+","+strbul;
								adminrole.setActionId(adminids);
								rolesService.storeRoles(adminrole);
								
								Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
								
								String managerids = managerrole.getActionId()+","+strbul;
								managerrole.setActionId(managerids);
								rolesService.storeRoles(managerrole);
							
					}
				}
				
				if(!isNullOrEmpty(modulesList)){
					List<Long> actionsList = null;
					String employeeIds = null;
					String[] registeredModules =  modulesList.split(",");
					for(int i=0; i<registeredModules.length; i++){
						if(Long.parseLong(registeredModules[i]) != 1L){
							orgModules = orgModules+registeredModules[i]+",";
						}
						
						
						if(registeredModules[i].equals("4")){
							
							Long moduleId = (long) Constants.MODULE_TIMESHEET;
							Long myTimeSheetTabId = (long) Constants.TAB_MY_TIMESHEET;
							Long timeSheetApprovalTabId = (long) Constants.TAB_TIMESHEET_APPROVAL;
							Long tabId = (long) Constants.TAB_CLIENT;
							Long projectsTabId = (long) Constants.TAB_PROJECTS;
							
							actionsList = sourceleadActionsService.getSourceLeadTimeSheetActionsByModuleIdAndTabId(moduleId, myTimeSheetTabId, timeSheetApprovalTabId);
							
							 strbul  = new StringBuilder();
						     Iterator<Long> iter = actionsList.iterator();
						     while(iter.hasNext())
						     {
						         strbul.append(iter.next());
						        if(iter.hasNext()){
						         strbul.append(",");
						        }
						     }
						     
						     strbul.toString();
						     
						     strbul2 = new StringBuilder();
						     List<Long> clientActionsList = sourceleadActionsService.getSourceLeadActionsByTabId(tabId);
						     Iterator<Long> iter2 = clientActionsList.iterator();
						     while(iter2.hasNext())
						     {
						    	 strbul2.append(iter2.next());
						        if(iter2.hasNext()){
						        	strbul2.append(",");
						        }
						     }
						        strbul2.toString();
						        
						        strbul3 = new StringBuilder();
							     List<Long> projectsActionsList = sourceleadActionsService.getSourceLeadActionsByTabId(projectsTabId);
							     Iterator<Long> iter3 = projectsActionsList.iterator();
							     while(iter3.hasNext())
							     {
							    	 strbul3.append(iter3.next());
							        if(iter3.hasNext()){
							        	strbul3.append(",");
							        }
							     }
							        strbul3.toString();
						        
						        Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
						        String adminids = null;
						        List<Long> adminRoleActonList = new ArrayList<Long>();
								 for (String s : adminrole.getActionId().split(",")){
									 adminRoleActonList.add(Long.parseLong(s));
								 }
								 
								 adminRoleActonList.retainAll(clientActionsList);
								
								 if(adminRoleActonList.size() == 0){
									 adminids = adminrole.getActionId()+","+strbul+","+strbul3+","+strbul2;
								 }else{
									 adminids = adminrole.getActionId()+","+strbul+","+strbul3;
								 }
						        
								adminrole.setActionId(adminids);
								rolesService.storeRoles(adminrole);
								
								Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
								String managerids = null;
								
								List<Long> managerRoleActonList = new ArrayList<Long>();
								 for (String s : managerrole.getActionId().split(",")){
									 managerRoleActonList.add(Long.parseLong(s));
								 }
								 
								 managerRoleActonList.retainAll(clientActionsList);
								
								 if(managerRoleActonList.size() == 0){
									 managerids = managerrole.getActionId()+","+strbul+","+strbul3+","+strbul2;
								 }else{
									 managerids = managerrole.getActionId()+","+strbul+","+strbul3;
								 }
								
								managerrole.setActionId(managerids);
								rolesService.storeRoles(managerrole);
								
								
								actionsList = sourceleadActionsService.getSourceLeadActionsByModuleIdAndTabId(moduleId, myTimeSheetTabId);
								
								 strbul  = new StringBuilder();
							     Iterator<Long> iter1 = actionsList.iterator();
							     while(iter1.hasNext())
							     {
							         strbul.append(iter1.next());
							        if(iter1.hasNext()){
							         strbul.append(",");
							        }
							     }
							     strbul.toString();
							     
						        Roles employeeRole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_USER, orgLocations.getId());
								if(!isNullOrEmpty(employeeRole.getActionId())){
									employeeIds = employeeRole.getActionId()+","+strbul;
								}else{
									employeeIds = strbul.toString();
								}
								employeeRole.setActionId(employeeIds);
								rolesService.storeRoles(employeeRole);
							
						}
					}
				}
				
				if("" != orgModules){
					orgModules = orgModules.substring(0, orgModules.length()-1);
					StringBuilder orgModulesstr = new StringBuilder();
					orgModules = orgModulesstr.append(orgLocations.getModulesId()).append(",").append(orgModules).toString();
					orgLocations.setModulesId(orgModules);
				}
			    orgLocationService.updateOrgLocations(orgLocations);
		   
		return "success";
	   }
	
	
	
	
	
	
	
	@RequestMapping(value = "/resetPasswordWithUserName" , method = RequestMethod.POST)
	public RestResponsetUtil resetPasswordWithUserName(HttpServletRequest request) throws Exception {
		 RestResponsetUtil resResponsetUtil = new RestResponsetUtil();
		String json1 = IOUtils.toString(request.getInputStream());
		if(StringUtils.isNotEmpty(json1)){
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Registration registration = gson.fromJson(json1, Registration.class);
			User user = userService.findByUserName(registration.getEmail());
			if (null != user) {
				UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
				if (null != userRole) {
					try {
						PasswordResets passwordResets = passwordResetsSevice.getPasswordResetsByUserId(user.getId());
						if (null == passwordResets) {
							String verificationCode = registrationService.addPasswordResets(user);
							registrationService.sendResetPasswordEmail(user,userRole.getOrgLocations().getId(), verificationCode);
						} else {
							passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
							passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
							// email to user
							registrationService.sendResetPasswordEmail(user,userRole.getOrgLocations().getId(), passwordResets.getVerificationCode());
						}
		
						SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordMsg",
								"Verification Code sent to your mail.");
					} catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}
				resResponsetUtil.setStatus("valid");
				return resResponsetUtil;
			} else {
				resResponsetUtil.setStatus("Invalid");
				return resResponsetUtil;
			}
		}
		resResponsetUtil.setStatus("Invalid");
		return resResponsetUtil;
	}
	

	@RequestMapping(value = "/verifyResetPassword" , method = RequestMethod.POST)
	   public RestResponsetUtil verifyResetPassword(HttpServletRequest request) throws Exception {
		RestResponsetUtil resResponsetUtil = new RestResponsetUtil();
		String json1 = IOUtils.toString(request.getInputStream());
		if(StringUtils.isNotEmpty(json1)){
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
					if(null != user){
						UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
						if (null != userRole) {
							registrationService.sendUpdatePasswordEmail(user,userRole.getOrgLocations().getId());
						}
					}
					 SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordSucessfulMsg", "Your password has reset sucessfully.");
					
				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
				resResponsetUtil.setResetResponse("login");
				return resResponsetUtil;
			}else{
				resResponsetUtil.setResetResponse("Invalid");
				return resResponsetUtil;
			}
		}
		resResponsetUtil.setResetResponse("Invalid");
		return resResponsetUtil;
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
	   		@RequestParam(value="companyName", required=false) String companyName,HttpServletRequest request) {
			String status = null;
			boolean flag = false;
				try {
					List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
					if(null != orgLocationsList){
						for(OrgLocations orgLocations : orgLocationsList){
							if(orgLocations.getOrgLocationName().equalsIgnoreCase(companyName)){
								flag = true;
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		   
		   if(flag == true){
			   status = "Company Duplicated";
		   }else{
			   status = "success";
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
	   
	   @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")	   
	   @RequestMapping(value = "/orgSummary/{orgId}" , method = RequestMethod.GET)
		 public @ResponseBody void orgSummary(@PathVariable Long orgId,HttpServletRequest request,HttpServletResponse response) throws Exception {
				StringBuilder redirectUrl = new StringBuilder();
				response.sendRedirect(redirectUrl.append("/sourcelead/enrolledOrgSummary?orgId=").append(orgId).toString());
				
		   }
	   
	   
	   
	   @RequestMapping(value = "/ws/emailAndWebsiteRegistrationValidation",  method = RequestMethod.GET)
	   public @ResponseBody String emailAndWebsiteRegistrationValidation(@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites,
	   		@RequestParam(value="email", required=false) String email) throws Exception {
		   return userService.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
	   }
	   
	   
	    @RequestMapping(value = "/getRegisteredOrgsLocationsList")
		public @ResponseBody String getRegisteredOrgsLocationsList(HttpServletRequest request) {

		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgLocations> orgLocationsList = new ArrayList<>();

		ArrayList<OrgLocations> regOrgsLocationsList = new ArrayList<OrgLocations>();

		try {
			orgLocationsList = orgLocationService.getAllRegisteredOrgLocationsByOrgId(org.getId(),loginOrgLocation.getId());
			if(null != orgLocationsList && orgLocationsList.size() > 0){
			for(OrgLocations orgLocations : orgLocationsList){
				if(null != orgLocations.getUser()){
					
					StringBuilder userName = new StringBuilder();
					if(!isNullOrEmpty(orgLocations.getUser().getLastName())){
						userName = userName.append(orgLocations.getUser().getFirstName()).append(" ").append(orgLocations.getUser().getLastName());
					}else{
						userName = userName.append(orgLocations.getUser().getFirstName());
					}
					orgLocations.setOrglocationUserName(userName.toString());
					orgLocations.setOrglocationUserEmail(orgLocations.getUser().getEmail());
					orgLocations.setOrglocationUserPhone(orgLocations.getUser().getMobilePhone());
					orgLocations.setOrglocationUserId(orgLocations.getUser().getId());
					orgLocations.setOrgCode(org.getOrgCode());
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orgLocations.getUser().getId(),orgLocations.getId());
					if(null != userRole){
						orgLocations.setOrglocationUserStatus(userRole.getUserState().toString());
					}
				}
				
				regOrgsLocationsList.add(orgLocations);
			}
		  }
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		resultJson = commonUtil.toJSON(regOrgsLocationsList);
		return resultJson;

	}
	   
	   
	
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	}
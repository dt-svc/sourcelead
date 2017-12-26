package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CrowdSourcingUserRegistrationDTO;
import com.bis.sourcelead.dto.WhiteListIPAddressDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.RegistrationOptionalService;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.WhiteListIPAddress;
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
import com.bis.sourcelead.service.WhiteListIpAddressService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.UserLoginHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationRestController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);
	 
	@Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	
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
	 UserLoginHelper userLoginHelper;
	 
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
	 
	 
	 @Autowired
	 WhiteListIpAddressService whiteListIpAddressService;
	 
	 @RequestMapping(value = "/app/getOrgsList")
	 		public @ResponseBody String getUsersList(HttpServletRequest request,
				@RequestParam(value="searchByName", required=false) String searchByName,
				@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "userState", required = false) String userState) {

		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<Org> orgsList = new ArrayList<>();

		ArrayList<Org> regOrgsList = new ArrayList<Org>();

		try {
			orgsList = orgService.getEnrolledOrgs(org.getId(), userState, searchByName,offset);
			if(orgsList != null && orgsList.size() > 0) {
			for(Org regOrg : orgsList){
					OrgLocations orglocations = orgLocationService.getPrimaryOrgLocationByOrgId(regOrg.getId());
					if(null != regOrg.getUser()){
						UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(regOrg.getUser().getId(),orglocations.getId());
						regOrg.setEmail(regOrg.getUser().getEmail());
						regOrg.setIsMailSent(userRole.getIsMailSent());
						regOrg.setUserState(regOrg.getUser().getUserState());
						regOrg.setMobilePhone(regOrg.getUser().getMobilePhone());
						regOrg.setRegisteredUserId(regOrg.getUser().getId());
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
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		resultJson = commonUtil.toJSON(regOrgsList);
		return resultJson;

	}
	 
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/companyRegistration" , method = RequestMethod.POST)
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
	 @RequestMapping(value = "/app/createUesrBySourceleadAdmin" , method = RequestMethod.POST)
	 public  String createUser(AddUserDTO addUserDTO, CandidateDTO candidateDTO,HttpServletRequest request) throws Exception {
		 	
		 /*json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);*/
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 registrationService.addUserBySourcelead(user, addUserDTO,candidateDTO);
	 	
		 return "success";
	   }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/changePrimaryAdmin" , method = RequestMethod.POST)
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
	 @RequestMapping(value = "/app/editOrgPrimaryUser" , method = RequestMethod.POST)
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
		 Long profUserId = null;
		if (result.contains("success")) {
			String s2[] = result.split("success");
			profUserId = Long.parseLong(s2[s2.length - 1]);
			result = "success";
		}
		SourceLeadSessionManager.setSessionAttribute(request, "profUserId", profUserId);
		 SourceLeadSessionManager.setSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg",
					"Username sent to your mail.");
	 	
		 return result;
	   }
	 
	 @RequestMapping(value = "/registerAsIndividualRecruiter" , method = RequestMethod.POST)
	 public  String registerAsIndividualRecruiter(CrowdSourcingUserRegistrationDTO csUserRegistrationDTO, HttpServletRequest request) throws Exception {
		
		 String result = registrationService.saveIndividualRecruiter(csUserRegistrationDTO);
		 Long recUserId = null;
			if (result.contains("success")) {
				String s2[] = result.split("success");
				recUserId = Long.parseLong(s2[s2.length - 1]);
				result = "success";
			}
			SourceLeadSessionManager.setSessionAttribute(request, "profUserId", recUserId);
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
	   @RequestMapping(value = "/app/updateOrgModules" , method = RequestMethod.POST)
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
	

	@RequestMapping(value = "/app/verifyResetPassword" , method = RequestMethod.POST)
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
	
	   @RequestMapping(value = "/app/validateOrgWebsite", method = RequestMethod.GET)
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
	   
	   
	   @RequestMapping(value = "/app/validateCompanyName", method = RequestMethod.GET)
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
	   
	   
	   
	   @RequestMapping(value = "/app/validateRegiseredCompanyName", method = RequestMethod.GET)
	   public @ResponseBody String validateRegiseredCompanyName(
	   		@RequestParam(value="companyName", required=false) String companyName,HttpServletRequest request) {
			String status = null;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			if(!loginOrgLocation.getOrgLocationName().equalsIgnoreCase(companyName)) {
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
		}else {
			 status = "success";
		}
		   
       return status;
	   }
	   
	   
	   @RequestMapping(value = "/app/getUserDetails",  method = RequestMethod.POST)
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
	   @RequestMapping(value = "/app/orgSummary/{orgId}" , method = RequestMethod.GET)
		 public @ResponseBody void orgSummary(@PathVariable Long orgId,HttpServletRequest request,HttpServletResponse response) throws Exception {
				StringBuilder redirectUrl = new StringBuilder();
				response.sendRedirect(redirectUrl.append("/app/enrolledOrgSummary?orgId=").append(orgId).toString());
				
		   }
	   
	   
	   
	   @RequestMapping(value = "/app/emailAndWebsiteRegistrationValidation",  method = RequestMethod.GET)
	   public @ResponseBody String emailAndWebsiteRegistrationValidation(@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites,
	   		@RequestParam(value="email", required=false) String email) throws Exception {
		   return userService.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
	   }
	   
	   
	    @RequestMapping(value = "/app/getRegisteredOrgsLocationsList")
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
	    
	    
	@RequestMapping(value = "/app/ws/activateEmail", method = RequestMethod.GET)
	public @ResponseBody ModelAndView activateEmail(@RequestParam(value="token", required=false) String token, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String status = "";
		User user = userService.getUserByVerficationUUID(token);
		if(user != null){
			Date uuidSentTime = user.getUrlSentTime();
			Calendar cal = Calendar.getInstance(); // creates calendar
			Date current = cal.getTime();
		    cal.setTime(uuidSentTime); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		    Date addedDtime = cal.getTime();
			if((current.after(uuidSentTime) && current.before(addedDtime)) || current.equals(addedDtime)){
				String isVerified = user.getIsVerified();
				if(Constants.NO.equalsIgnoreCase(isVerified)){
					user.setIsVerified(Constants.YES);
					user.setUpdatedDate(new Date());
					userService.addUser(user);
					if(Constants.INDIVIDUAL_RECRUITER.equalsIgnoreCase(user.getUserType()))
						registrationService.sendEmailToIndividualRecruiter(user);
					else
						registrationService.sendEmailToProfessional(user);
					status = "success";
					// verified click to login
				}else{
					status = "already Verified";
				}
			}else{
				//url expiry
				status = "expired";
			}
			
			model.addObject("userId",user.getId());
			model.addObject("token",token);
			model.addObject("sourceleadUrl",sourceleadUrl);
		}else{
			//home page
			status = "home";
		}
		model.addObject("status", status);
		model.setViewName("verifyAccount");
		return model;
	}
	
	
	@RequestMapping(value = "/app/resendVerificationEmail", method = RequestMethod.GET)
	public @ResponseBody String resendVerificationEmail(@RequestParam(value = "userId", required = false) Long userId,@RequestParam(value = "userName", required = false) String userName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = null;
		if(userId != null)
			user = userService.getUserById(userId);
		else
			user = userService.findByUserName(userName);
		if (Constants.NO.equalsIgnoreCase(user.getIsVerified())) {
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
			user.setUrlSentTime(new Date());
			user.setIsVerified(Constants.NO);
			userService.addUser(user);
			registrationService.sendActivationEmail(user);
			return "success";
		}
		else {
			return "failure";
		}
	}
	   
	@RequestMapping(value = "/app/verificationDone", method = RequestMethod.POST)
	public String verificationDone(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "userRoleId", required = false) long userRoleId,@RequestParam(value = "verfDone", required = false) String verfDone,HttpServletRequest request) throws Exception
	{
		UserRole userRole = userRoleService.getUserRoleById(userRoleId);
		if("".equalsIgnoreCase(userName) || userName == null){
			UserLoginLogoutSession userLoginSession = (UserLoginLogoutSession) request.getSession().getAttribute("userLoginSession");
			if(userLoginSession != null){
				if(Constants.YES.equalsIgnoreCase(verfDone))
					userLoginSession.setAuthenticated(Constants.YES);
				request.getSession().setAttribute("userLoginSession", userLoginSession);
			}
		}
		SecurityContextHolder.clearContext();
		SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(userRole, userName, password,verfDone));
		return "true";
	}
	
	@RequestMapping(value = "/app/getUserDetailsByRoleId", method = RequestMethod.GET)
	public ModelAndView getUserDetailsByRoleId(@RequestParam(value = "id", required = false) Long roleId,@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password) throws Exception
	{
		ModelAndView model = new ModelAndView();
		UserRole userRole = null;
		if(roleId != null)
			userRole = userRoleService.getUserRoleById(roleId);
		else{
			User user = userService.findByUserName(userName);
			userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		}
		if(userRole.getUser() != null){
			model.addObject("userId",userRole.getUser().getId());
			model.addObject("userLoginSecurity",userRole.getUser().getLoginSecurity());
			model.addObject("userRoleId",userRole.getId());
			model.addObject("emailId",userRole.getUser().getEmail());
			if(StringUtils.isNotBlank(userRole.getUser().getMobilePhone()))
				model.addObject("mobilePhone",userRole.getUser().getMobilePhone());
		}
		if(userName != null)
			model.addObject("username",userName);
		if(password != null)
			model.addObject("password",password);
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("otpVerification");
		return model;
	}
	
	@RequestMapping(value = "/app/sendOtpToUser", method = RequestMethod.GET)
	public ModelAndView sendOtpToUser(@RequestParam(value = "id", required = false) Long userId,@RequestParam(value = "type", required = false) String type,HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		User user = userService.getUserById(userId);
		String otp = registrationService.sendOtpToUser(user,type);
		if(user != null){
			model.addObject("emailId",user.getEmail());
			if(StringUtils.isNotBlank(user.getMobilePhone()))
				model.addObject("mobilePhone",user.getMobilePhone());
		}
		model.addObject("authToken",otp);
		model.addObject("status","success");
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("otpVerification");
		return model;
	}
	
	@RequestMapping(value = "/app/validateWhitelistIp", method = RequestMethod.GET)
	public boolean validateWhitelistIp(@RequestParam(value = "userRoleId", required = false) long userRoleId,HttpServletRequest request) throws Exception
	{
		UserRole userRole = userRoleService.getUserRoleById(userRoleId);
		List<WhiteListIPAddressDTO> ipList = whiteListIpAddressService.getWhiteListIpsByLocationId(userRole.getOrgLocations().getId());
		String ipAddress = userLoginHelper.getClientIpAddr(request);
		String[] currentIp = ipAddress.split("\\.");
		boolean matched = false;
		if(ipList.size() > 0){
			for(WhiteListIPAddressDTO wlipDto : ipList){
				String fromip = wlipDto.getFromIpAddress();
				String toip = wlipDto.getToIpAddress();
				
				String[] fromipaddr = fromip.split("\\.");
				String[] toipaddr = toip.split("\\.");
				
				if((Integer.parseInt(currentIp[3]) >= Integer.parseInt(fromipaddr[3])) && (Integer.parseInt(currentIp[3]) <= Integer.parseInt(toipaddr[3]))){
					if(Integer.parseInt(currentIp[2]) >= Integer.parseInt(fromipaddr[2]) && (Integer.parseInt(currentIp[2]) <= Integer.parseInt(toipaddr[2]))){
						if(Integer.parseInt(currentIp[1]) >= Integer.parseInt(fromipaddr[1]) && (Integer.parseInt(currentIp[1]) <= Integer.parseInt(toipaddr[1]))){
							if(Integer.parseInt(currentIp[0]) >= Integer.parseInt(fromipaddr[0]) && (Integer.parseInt(currentIp[0]) <= Integer.parseInt(toipaddr[0]))){
								matched = true;
							}else{
								matched = false;
							}
						}else{
							matched = false;
						}
					}else{
						matched = false;
					}
				}else{
					matched = false;
				}
			}
		}else{
			matched = true;
		}
		
		return matched;
	}
	
	
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	}
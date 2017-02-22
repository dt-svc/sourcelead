package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ActionRolesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationController {
	
	 
	 @Value("${sourceLeadBlockedSocialDomains}")
	 private String sourceLeadBlockedSocialDomains;
	 
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private ModulesService modulesService;
	 
	 @Autowired
	 private ActionRolesService actionRolesService;
	 
	 @Autowired
	 private NotificationsService notificationsService;
	 
	 @Autowired
	 private ModulesService moduleService;
	 
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 SourceleadActionsService sourceleadActionsService;
	 
	 @Autowired
	 RolesService rolesService;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 UserRoleService userRoleService;
	 
	 @Autowired
	 RolesService roleService;
	 
	 @Autowired
	 OrgSpocService orgSpocService;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/enrolledOrgsList")
		public ModelAndView enrolledOrgsList(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
			ModelAndView model = new ModelAndView();
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCreateMsg");
			if(!isNullOrEmpty(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "orgCreateMsg");
			}
			model.setViewName("registration/enrolledOrgsList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			
			return model;

	 }
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/enrollOrg")
		public ModelAndView registration() {
			ModelAndView model = new ModelAndView();
			List<Modules> modulesList = null;
			List<Modules> requirementSubModulesList = null;
			try {
				 modulesList = modulesService.getSourceLeadModules();
				 model.addObject("modulesList", modulesList);
				 
				 List<Timezone> timezoneList= timeZoneService.getAllTimeZones();
				 model.addObject("timezoneList", timezoneList);
				 
				 requirementSubModulesList = moduleService.getRequirementSubModules();
				 model.addObject("requirementSubModulesList", requirementSubModulesList);
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.setViewName("registration/enrollOrg");

			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("sourceLeadBlockedSocialDomains", sourceLeadBlockedSocialDomains);
			
			return model;

		}
	 
	 
	 /**
	  * @author Venkat I
	  * This method calls when client or vendor accept the invitation or accept the notification.
	  * @param respondingOrgType
	  * @param actionLink
	  * @param respondingPartyOrgLocationId
	  * @return
	  */
	 
	 @RequestMapping(value = "/acceptClientOrVendorInvitation")
		public String acceptClientOrVendorInvitation(@RequestParam(value = "respondingOrgType", required = false) String respondingOrgType,
				@RequestParam(value = "actionLink", required = false) String actionLink,
				@RequestParam(value = "UUID", required = false) String UUID,
				@RequestParam(value = "respondingPartyOrgLocationId", required = false) Long respondingPartyOrgLocationId,
				@RequestParam(value = "initiatingPartyOrgLocationId", required = false) Long initiatingPartyOrgLocationId) {
			
		    Org org = null;
		    OrgLocations orgLocations = null;
		    boolean flag;
			
		    try {
		    	orgLocations = orgLocationService.getOrgLocations(respondingPartyOrgLocationId);
		    	org = orgLocations.getOrg();
		    	String modules = org.getModulesId();
		    	Long tabId = null;
		    	Long moduleId = null;
		    	if(null != org){
			    	org.setOrgType("Company");
			    	
			    	List<Integer> moduleIds = new ArrayList<Integer>();
					 for (String s : org.getModulesId().split(",")){
						 moduleIds.add(Integer.parseInt(s));
					 }
					 
			    	if("ATS".equalsIgnoreCase(respondingOrgType)){
			    		flag = moduleIds.contains(Constants.MODULE_ATS);
			    		tabId = (long) Constants.TAB_CLIENT;
			    		moduleId = (long) Constants.MODULE_ATS;
			    		if(flag == false){
			    			modules = modules+","+Constants.MODULE_ATS;
			    			saveUserRoleActions(moduleId,tabId,orgLocations);
			    		}
			    	}else if("VMS".equalsIgnoreCase(respondingOrgType)){
			    		flag = moduleIds.contains(Constants.MODULE_VMS);
			    		tabId = (long) Constants.TAB_VENDOR;
			    		moduleId = (long) Constants.MODULE_VMS;
			    		if(flag == false){
			    			modules = modules+","+Constants.MODULE_VMS;
			    			saveUserRoleActions(moduleId,tabId,orgLocations);
			    		}
			    	}
			    	
					
			    	org.setModulesId(modules);
			    	org.setStatus(Status.ACTIVE);
					orgService.updateOrg(org);
					
					 Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
					 if(null != notifications){
						 notifications.setUserNotificationStatus("Accepted");
						 notificationsService.saveNotification(notifications);
					 }
					 
					 /*OrgSpoc orgSpoc = orgSpocService.getSpocListByResponderLocationId(initiatingPartyOrgLocationId,respondingPartyOrgLocationId,respondingOrgType);
					 if(null != orgSpoc ){
						 orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
						 orgSpocService.addOrgSpoc(orgSpoc);
					 }*/
					 
		    	}
				 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    
			if(null == actionLink){
				return "redirect:/login";
			}else{
				 StringBuilder redirectUrl = new StringBuilder();
				//return "redirect:/dashboard/"+respondingPartyOrgLocationId+"";
				 return redirectUrl.append("redirect:/dashboard/").append(respondingPartyOrgLocationId).toString();
			}
			
		}
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/enrolledOrgSummary")
		public ModelAndView enrolledOrgSummary(HttpServletRequest request,
				@RequestParam(value = "orgId", required = false) Long orgId) throws Exception {
			    ModelAndView model = new ModelAndView();
			
				OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(orgId);

				List<Modules> modulesList = null;
				List<Modules> requirementSubModulesList = null;
				
				if(null != orgLocations.getOrg().getModulesId()){
					ArrayList<Modules> registeredModulesList = new ArrayList<Modules>();
					ArrayList<Modules> registeredSubModulesList = new ArrayList<Modules>();
					modulesList = modulesService.getSourceLeadModules();
					requirementSubModulesList = moduleService.getRequirementSubModules();
					
					List<Long> orgModulesList = new ArrayList<Long>();
					 for (String s : orgLocations.getOrg().getModulesId().split(",")){
						 orgModulesList.add(Long.parseLong(s));
					 }
					 
					 for(Modules modules : modulesList){
						 if((orgModulesList.contains(modules.getId()))){
							 modules.setIsModuleConfigured(Constants.YES);
						 }else{
							 modules.setIsModuleConfigured(Constants.NO);
						 }
						 registeredModulesList.add(modules);
					 }
					 
					 for(Modules modules : requirementSubModulesList){
						 if(orgModulesList.contains(modules.getId())){
							 modules.setIsModuleConfigured(Constants.YES);
						 }else{
							 modules.setIsModuleConfigured(Constants.NO);
						 }
						 registeredSubModulesList.add(modules);
					 }
					 
					 model.addObject("registeredModulesList", registeredModulesList);
					 model.addObject("registeredSubModulesList", registeredSubModulesList);
				}
				
				model.addObject("orgloc",orgLocations);
				model.addObject("registeredOrgLocationId",orgLocations.getId());
				model.addObject("orgId",orgId);
				model.setViewName("registration/enrolledOrgSummary");
				
				if(null != orgLocations.getUser()){
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orgLocations.getUser().getId(), orgLocations.getId());
					model.addObject("userState",userRole.getUserState());
				}
				
				model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

		}
	 
	 
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/getOrgRegisteredUsers")
		public @ResponseBody String getOrgRegisteredUsers(@RequestParam(value = "orgId", required = false) Long orgId,
				HttpServletRequest request) throws Exception {

		    String resultJson;
		    OrgLocations orgLocations = orgLocationService.getOrgLocationByOrgId(orgId);

			List<User> usersList = userService.getUsersListByOrglocationId(orgLocations.getId());
			List<User> orgUsersList = new ArrayList<User>(); 
			if(null != usersList){
				for(User user : usersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
					user.setUserState(userRole.getUserState());
					user.setRole(userRole.getRoles().getDisplayName());
					orgUsersList.add(user);
				}
			}
		    resultJson = commonUtil.toJSON(orgUsersList);
			return resultJson;
		}
		
		
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/getRegisteredOrgRoles", method = RequestMethod.POST)
		public @ResponseBody String getReportingManager(
				@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception {
			String resultJson = null;
			List<Roles> rolesList = roleService.getRoles(orgLocationId);
			resultJson = commonUtil.toJSON(rolesList);
			return resultJson;
		}
		
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/validateRegisteredUserEmail" , method = RequestMethod.POST)
		 public @ResponseBody  String validateUserEmail(@RequestParam(value="emailId", required=false) String emailId,
				 @RequestParam(value="orgLocationId", required=false) Long orgLocationId,HttpServletRequest request) throws Exception {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	String result = null;
			 	User user = userService.findByEmailAndOrgLocationsId(emailId,orgLocationId);
			 	
			 	if (user != null) {
					result = "true";
				}else{
					result = "false";
				}
				return result;
		   }
		
		
		 @RequestMapping(value = "/validateOrgPrimaryUserEmail",  method = RequestMethod.GET)
		    public @ResponseBody String validateOrgPrimaryUserEmail(@RequestParam(value="email", required=false) String email,
		    		@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
		    		@RequestParam(value="json", required=false) String json) throws Exception {
				
			    String result = null;
			    User user = null;
			 	user = userService.findByEmailAndOrgLocationsId(email,orgLocationId);
			 	if (null != user) {
					result = "success";
				}else{
					user = userService.findByEmail(email);
					if(null == user){
						result = "success";
					}else{
						result = "Existed Email";
					}
				}
				return result;
			}
		   
		
	
	 public void saveUserRoleActions(Long moduleId,Long tabId,OrgLocations orgLocations) throws Exception{
		 
			List<Long> actionsList = sourceleadActionsService.getSourceLeadActionsByModuleId(moduleId);
			
			Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
			 List<Long> adminRoleActonList = new ArrayList<Long>();
			 for (String s : adminrole.getActionId().split(",")){
				 adminRoleActonList.add(Long.parseLong(s));
			 }
			 
			 adminRoleActonList.retainAll(actionsList);
			 actionsList.removeAll(adminRoleActonList);
			
			
	    	StringBuilder strbul  = new StringBuilder();
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
	 
	 
	 
	  @RequestMapping(value = "/validateCrowdSourcingEmail",  method = RequestMethod.GET)
	   public @ResponseBody String validateCrowdSourcingEmail(@RequestParam(value="email", required=false) String email,
	   		@RequestParam(value="type", required=false) String type) throws Exception {
		  
		  User user = userService.getUserByEmailAndUserType(email, type);
		  if(null == user){
			  return "Success";
		  }else{
			  return "Invalid";
		  }
	   }
	  
	  
	  @RequestMapping(value = "/validateCrowdSourcingUserName",  method = RequestMethod.GET)
	   public @ResponseBody String validateCrowdSourcingUserName(@RequestParam(value="userName", required=false) String userName) throws Exception {
		  
		  User user = userService.getUserByUserName(userName);
		  if(null != user){
			  return "Success";
		  }else{
			  return "Invalid";
		  }
	   }
	  
	  @RequestMapping(value = "/validateCrowdSourcingResetUserName",  method = RequestMethod.GET)
	   public @ResponseBody String validateCrowdSourcingResetUserName(@RequestParam(value="userName", required=false) String userName,
			   @RequestParam(value="resetUserName", required=false) String resetUserName) throws Exception {
		  
		  User user = null;
		  user = userService.getUserByUserName(userName);
		  if(null != user){
			  if(user.getEmail().equalsIgnoreCase(resetUserName)){
				  return "Invalid";
			  }else{
				  user = userService.findByUserName(resetUserName); 
				  if(null == user){
					  return "Success";
				  }else{
					  return "Invalid";
				  }
			  }
			  
		  }else{
			  return "Invalid";
		  }
	   }
	  
	 
	 /**	 
	  * checks if string is null or empty
	  * @param str
	  * @return boolean
	  */
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	
	
	}
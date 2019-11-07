package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dto.OrgLocationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.EmailDomains;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.RegistrationOptionalService;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ActionRolesService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmailDomainsService;
import com.bis.sourcelead.service.EmployeeVerificationTemplateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RegistrationOptionalServicesService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
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
	 
	 @Autowired
	 private ClientService clientService;
	 
	 @Autowired
	 EmailDomainsService emailDomainsService;
	 
	 @Autowired
	 private EmployeeVerificationTemplateService employeeVerificationTemplateService;
	 
	 @Autowired
	 ReferContactsDao referContactsDao;
	 
	 @Autowired
	 LookUpDataService lookUpDataService;
	 
	 @Autowired
	 OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	 
	 @Autowired
	 SourceleadRolesService sourceleadRolesService;
	 
	 @Autowired
	 TabsService tabService;
	 
	 @Autowired
	 OrgLicenseSubscriptionTypesService orgLicenseSubscriptionTypesService;
	 
	 @Autowired
	 private RegistrationOptionalServicesService registrationOptionalServicesService;
	 
	 @Autowired
	 ReferContactsService referContactsService;
	 
	 @Value("${GoogleMaps_Location_API_KEY}")
	 private String googleMapsLocationApiKey;


	 @Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
		
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	

	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/enrolledOrgsList")
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
	 @RequestMapping(value = "/app/requestedOrgs")
		public ModelAndView requestedOrgs(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("registration/requestedOrgs");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/demoRequests")
		public ModelAndView demoRequests(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("registration/demoRequests");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/enrollOrg")
		public ModelAndView enrollOrg(HttpServletRequest request,
				@RequestParam(value = "locationCode", required = false) String locationCode) {
		 
		 	commonUtil.highLightMenu(Constants.OrgSettings, request);
		 
			ModelAndView model = new ModelAndView();
			try {
				List<OrgLicenseSubscriptionTypes> subscriptionTypeList = orgLicenseSubscriptionTypesService.getLicenseSubscriptionTypesByCategory(Constants.SUBSCRIPTION_TYPE);
				model.addObject("subscriptionTypeList", subscriptionTypeList);
				 
				List<OrgLicenseSubscriptionTypes> userSubscriptionTypeList = orgLicenseSubscriptionTypesService.getLicenseSubscriptionTypesByCategory(Constants.USER_SUBSCRIPTION);
				model.addObject("userSubscriptionTypeList", userSubscriptionTypeList);
				
				List<OrgLicenseSubscriptionTypes> dataStorageTypeList = orgLicenseSubscriptionTypesService.getLicenseSubscriptionTypesByCategory(Constants.DATA_STORAGE);
				model.addObject("dataStorageTypeList", dataStorageTypeList);
				
				if(StringUtils.isNotBlank(locationCode)) {
					OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
					model.addObject("orgLocationId", orgLocations.getId());
					model.addObject("locationCode", locationCode);
					model.addObject("registeredOrgId", orgLocations.getOrg().getId());
					OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(orgLocations.getOrg().getId(), orgLocations.getCountry());
					model.addObject("orgLicenseSubscriptionId", orgLicenseSubscription.getId());
					
				}
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
			
			model.setViewName("registration/enrollOrg");
		
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("sourceLeadBlockedSocialDomains", sourceLeadBlockedSocialDomains);
			
			return model;

		}
	 
	 
	 /**
	  * @author Venkat I and Prasad salina(Modified)
	  * This method calls when client or vendor accept the invitation or accept the notification.
	  * @param respondingOrgType
	  * @param actionLink
	  * @param respondingPartyOrgLocationId
	  * @return
	 * @throws Exception 
	  */
	 
	 @RequestMapping(value = "/app/ws/acceptClientOrVendorInvitation")
		public void acceptClientOrVendorInvitation(@RequestParam(value = "respondingOrgType", required = false) String respondingOrgType,
				@RequestParam(value = "actionLink", required = false) String actionLink,
				@RequestParam(value = "UUID", required = false) String UUID,
				@RequestParam(value = "respondingPartyOrgLocationId", required = false) Long respondingPartyOrgLocationId,
				@RequestParam(value = "initiatingPartyOrgLocationId", required = false) Long initiatingPartyOrgLocationId,
				@RequestParam(value = "spocUUID", required = false) String spocUUID,HttpServletResponse response) throws Exception {
			
		    Org org = null;
		    OrgLocations orgLocations = null;
		    boolean flag;
		    try {
		    	orgLocations = orgLocationService.getOrgLocations(respondingPartyOrgLocationId);
		    	org = orgLocations.getOrg();
		    	String modules = orgLocations.getModulesId();
		    	Long moduleId = null;
		    	Long tsModuleId = (long) Constants.MODULE_TIMESHEET;
		    	if(null != org){
		    		String currentOrgType = org.getOrgType();
			    	org.setOrgType("Company");
			    	
			    	List<Integer> moduleIds = new ArrayList<Integer>();
					 for (String s : orgLocations.getModulesId().split(",")){
						 moduleIds.add(Integer.parseInt(s));
					 }
					 
			    	
					 
					 if("VMS".equalsIgnoreCase(respondingOrgType)){
			    		flag = moduleIds.contains(Constants.MODULE_VMS);
			    		moduleId = (long) Constants.MODULE_VMS;
			    		if(flag == false){
			    			modules = modules+","+Constants.MODULE_VMS;
			    		}
			    		
			    		boolean tsflag = moduleIds.contains(Constants.MODULE_TIMESHEET);
			    		if(tsflag == false) {
		    				modules = modules+","+Constants.MODULE_TIMESHEET;
		    			}
			    	}
			    	
					 saveUserRoleActions(orgLocations);
					 
					
					//orgLocations.setModulesId(modules);
					if(!isNullOrEmpty(spocUUID)){
						OrgSpoc orgSpoc = orgSpocService.getSpocBySpocUUID(spocUUID);
						if(null != orgSpoc){
							orgLocations.setUser(orgSpoc.getClientUser());
							org.setUser(orgSpoc.getClientUser());
						}
					}
					
					org.setStatus(Status.ACTIVE);
					orgService.updateOrg(org);
					orgLocations.setStatus(Status.ACTIVE);
					orgLocationService.updateOrgLocations(orgLocations);
					
					
					if(orgLocations != null) {
						EmployeeVerificationTemplate empTemp = employeeVerificationTemplateService.getEmploymentVerificationtemplateByLocationId(orgLocations.getId());
						if(empTemp != null) {
							empTemp.setSignatory(orgLocations.getUser());
							employeeVerificationTemplateService.updateEmployMentTemplate(empTemp);
						}
					}
					
					if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
						userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(orgLocations);
						
						if(StringUtils.isNotBlank(currentOrgType) && currentOrgType.equalsIgnoreCase("Guest")) {
							List<UserRole> userRolesList = userRoleService.getAllUserRolesByOrgLocationId(orgLocations.getId());
							if(userRolesList != null && !userRolesList.isEmpty()) {
								
								for(UserRole userRole : userRolesList) {
									userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
								}
							}
						}
					}
					
		    	}
				 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    
			if(null == actionLink){
				StringBuilder redirectUrl = new StringBuilder();
				response.sendRedirect(redirectUrl.append("/Inlogin?spocUUID=").append(spocUUID).append("&UUID=").append(UUID).toString());
			}else{
				 StringBuilder redirectUrl = new StringBuilder();
				 if("acceptNotification".equalsIgnoreCase(actionLink)){
					 Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
					 if(null != notifications){
						 notifications.setUserNotificationStatus("Accepted");
						 notificationsService.saveNotification(notifications);
					 }
					 
					 OrgSpoc orgSpoc = orgSpocService.getSpocBySpocUUID(spocUUID);
					 if(null != orgSpoc ){
						 orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
						 orgSpocService.addOrgSpoc(orgSpoc);
					 }
					 
					 ReferContacts referContact=referContactsDao.getReferContactsByEmails(orgSpoc.getVendorUser().getId(), orgSpoc.getVendorUser().getEmail());
					 if(referContact ==null){
						 ReferContacts referContacts=new ReferContacts();
						 referContacts.setReferFromUser(orgSpoc.getClientUser());
						 referContacts.setFirstName(orgSpoc.getVendorUser().getFirstName());
						 referContacts.setLastName(orgSpoc.getVendorUser().getLastName());
						 referContacts.setContactUserEmail(orgSpoc.getVendorUser().getEmail());
						 referContacts.setContactUserPhone(orgSpoc.getVendorUser().getMobilePhone());
						 referContacts.setContactUser(orgSpoc.getVendorUser());
						 referContacts.setContactUserUUID(SourceLeadRandomCodeHelper.generateUUID());

						 
						 if(orgSpoc.getOrgRelations().getInitiatorType().equalsIgnoreCase("Vendor")){
							 String compName=orgSpoc.getOrgRelations().getLocationId().getOrgLocationName(); 
								//OrgLocations OrgLocations =orgLocationsDao.getOrgLocations(id);
								referContacts.setCompanyName(compName);
						 }
						 if(orgSpoc.getOrgRelations().getResponderType().equalsIgnoreCase("Vendor")){
							 String compName=orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName(); 
								referContacts.setCompanyName(compName);
							 }
						 referContacts.setVendorType(1);
						 referContacts.setCreatedDate(new Date());
						 referContacts.setCreatedBy(orgSpoc.getClientUser().getUserCode());
						 referContactsDao.addReferContacts(referContacts); 
					 }
					 
					 
					 clientService.sendAcceptEmailToClientOrVendor(orgSpoc,orgSpoc.getClientUser(),orgLocations);
				 }else if("rejectNotification".equalsIgnoreCase(actionLink)){
					 Notifications notifications = notificationsService.getNotificationsByUUID(UUID);
					 if(null != notifications){
						 notifications.setUserNotificationStatus("Rejected");
						 notificationsService.saveNotification(notifications);
					 }
					 
					 OrgSpoc orgSpoc = orgSpocService.getSpocBySpocUUID(spocUUID);
					 if(null != orgSpoc ){
						 orgSpoc.setInvitationStatus(Constants.MAIL_REJECTED);
						 orgSpoc.setSpocStatus("In-Active");
						 orgSpocService.addOrgSpoc(orgSpoc);
						 clientService.sendRejectEmailToClientOrVendor(orgSpoc,orgSpoc.getClientUser(),orgLocations);
					 }
				 }
				 
				 response.sendRedirect(redirectUrl.append(sourceleadUrl).append("/notificationsList").toString());
			}
			
		}
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/enrolledOrgSummary")
		public ModelAndView enrolledOrgSummary(HttpServletRequest request,
				@RequestParam(value = "orgCode", required = false) String orgCode) throws Exception {
			    ModelAndView model = new ModelAndView();
			    commonUtil.highLightMenu(Constants.OrgSettings, request);
			    Org org = orgService.getOrgByOrgCode(orgCode);
				OrgLocations orgLocations = orgLocationService.getPrimaryOrgLocationByOrgId(org.getId());
				
				model.addObject("orgloc",orgLocations);
				model.addObject("registeredOrgLocationId",orgLocations.getId());
				model.addObject("registeredCountry",orgLocations.getCountry());
				model.addObject("orgId",org.getId());
				model.addObject("orgCode",orgCode);
				model.setViewName("registration/enrolledOrgSummary");
				
				if(null != orgLocations.getUser()){
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orgLocations.getUser().getId(), orgLocations.getId());
					if(userRole != null)
					model.addObject("userState",userRole.getUserState());
				}
				
				List<OrgLicenseSubscriptionTypes> subscriptionTypeList = orgLicenseSubscriptionTypesService.getLicenseSubscriptionTypesByCategory(Constants.SUBSCRIPTION_TYPE);
				model.addObject("subscriptionTypeList", subscriptionTypeList);
				 
				List<OrgLicenseSubscriptionTypes> userSubscriptionTypeList = orgLicenseSubscriptionTypesService.getLicenseSubscriptionTypesByCategory(Constants.USER_SUBSCRIPTION);
				model.addObject("userSubscriptionTypeList", userSubscriptionTypeList);
				
				List<OrgLicenseSubscriptionTypes> dataStorageTypeList = orgLicenseSubscriptionTypesService.getLicenseSubscriptionTypesByCategory(Constants.DATA_STORAGE);
				model.addObject("dataStorageTypeList", dataStorageTypeList);
				
				List<String> orgSubscribedCountriesList = orgLicenseSubscriptionService.getOrgLicenseSubscribedAndRegisteredCountriesByOrgId(org.getId());
				model.addObject("orgSubscribedCountriesList", orgSubscribedCountriesList);
				
				model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

		}
	 
	 
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/app/getOrgRegisteredUsers")
		public @ResponseBody ResponseList<UserDTO> getOrgRegisteredUsers(UserDTO userDTO,@RequestParam(value = "registeredOrgLocationId", required = false) Long registeredOrgLocationId,
				HttpServletRequest request) throws Exception {

		    String resultJson;
		    OrgLocations orgLocations = orgLocationService.getOrgLocations(registeredOrgLocationId);
		    
		    String[] tableColumns = {"firstName", "email", "mobilePhone", "displayName", "status", "primaryAdmin", "superAdmin"};
		    int sortColumnIndex = 1; //by default sorting using updated date
			String sortColumnOrder = "desc"; //by default descending order
			if(userDTO.getOrder() != null && !userDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(userDTO.getOrder().get(0).get("column"));
				sortColumnOrder = userDTO.getOrder().get(0).get("dir");
			}
			userDTO.setSortColumn(tableColumns[sortColumnIndex]);
			userDTO.setSortOrder(sortColumnOrder);
		    

			ResponseList<UserDTO> usersDTOList = userService.getUsersDTOListByOrglocationId(orgLocations.getId(),userDTO);
			return usersDTOList;
		}
		
		
		
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/app/getOrgSubscribedCountries", method = RequestMethod.GET)
		public @ResponseBody List<String> getOrgSubscribedCountries(
				@RequestParam(value = "orgId", required = true) Long orgId,HttpServletRequest request) throws Exception {
			List<String> countriesList = orgLicenseSubscriptionService.getOrgLicenseSubscribedCountriesByOrgId(orgId);
			return countriesList;
		}
		
		
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/app/getLocationEnrolledCompanies", method = RequestMethod.GET)
		public @ResponseBody List<OrgLocationDTO> getLocationEnrolledCompanies(
				@RequestParam(value = "orgId", required = true) Long orgId,@RequestParam(value = "country", required = true) String country,HttpServletRequest request) throws Exception {
			List<OrgLocations> orgLocationsList = orgLocationService.getAllCurrentRegisteredOrgLocationsByOrgIdAndCountry(orgId, country);
			List<OrgLocationDTO> orgLocationsDTOList = new ArrayList<>();
			if(orgLocationsList != null && !orgLocationsList.isEmpty()) {
				for(OrgLocations orgLocations : orgLocationsList) {
					OrgLocationDTO orgLocationDTO = new OrgLocationDTO();
					orgLocationDTO.setId(orgLocations.getId());
					orgLocationDTO.setOrgName(orgLocations.getOrgLocationName());
					orgLocationsDTOList.add(orgLocationDTO);
				}
			
			}
			return orgLocationsDTOList;
		}
		
		
		
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/app/getRegisteredOrgRoles", method = RequestMethod.GET)
		public @ResponseBody List<RoleDTO> getRegisteredOrgRoles(
				@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception {
			List<Roles> rolesList = roleService.getRoles(orgLocationId);
			List<RoleDTO> rolesDTOList = new ArrayList<>();
			if(rolesList != null && !rolesList.isEmpty()) {
				for(Roles roles : rolesList) {
					RoleDTO rolesDTO = new RoleDTO();
					rolesDTO.setId(roles.getId());
					rolesDTO.setDisplayName(roles.getDisplayName());
					rolesDTOList.add(rolesDTO);
				}
			}
			return rolesDTOList;
		}
		
		@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
		@RequestMapping(value = "/app/validateRegisteredUserEmail" , method = RequestMethod.POST)
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
		
		
		 @RequestMapping(value = "/app/validateOrgPrimaryUserEmail",  method = RequestMethod.GET)
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
		   
		
	
	 public void saveUserRoleActions(OrgLocations orgLocations) throws Exception{
		 
		   SourceleadRoles soruceLeadRoles = sourceleadRolesService.getSourceleadRolesByRoleName(Constants.ROLE_GUEST_CLIENT);
			List<Long> soureleadActionIdList = new ArrayList<>();
			String[] actionsIds = null;
			actionsIds = soruceLeadRoles.getActionId().split(",");
			 for(int i=0 ; i < actionsIds.length; i++) {
				 soureleadActionIdList.add(Long.parseLong(actionsIds[i]));
			 }
			
			Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
			 List<Long> adminRoleActonList = new ArrayList<Long>();
			 for (String s : adminrole.getActionId().split(",")){
				 adminRoleActonList.add(Long.parseLong(s));
			 }
			 
			 adminRoleActonList.retainAll(soureleadActionIdList);
			 soureleadActionIdList.removeAll(adminRoleActonList);
			
			
	    	StringBuilder strbul  = new StringBuilder();
		     Iterator<Long> iter = soureleadActionIdList.iterator();
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
			
			
			updateOrgSubscriptionRegsiteredTabs(orgLocations,adminids);
			
			
	 }
	 
	 
	 
	 public void saveVendorUserRoleActions(OrgLocations orgLocations) throws Exception{
		 
		   SourceleadRoles soruceLeadRoles = sourceleadRolesService.getSourceleadRolesByRoleName(Constants.ROLE_GUEST_VENDOR);
			List<Long> soureleadActionIdList = new ArrayList<>();
			String[] actionsIds = null;
			actionsIds = soruceLeadRoles.getActionId().split(",");
			 for(int i=0 ; i < actionsIds.length; i++) {
				 soureleadActionIdList.add(Long.parseLong(actionsIds[i]));
			 }
			
			Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
			 List<Long> adminRoleActonList = new ArrayList<Long>();
			 for (String s : adminrole.getActionId().split(",")){
				 adminRoleActonList.add(Long.parseLong(s));
			 }
			 
			 adminRoleActonList.retainAll(soureleadActionIdList);
			 soureleadActionIdList.removeAll(adminRoleActonList);
			
			
	    	StringBuilder strbul  = new StringBuilder();
		     Iterator<Long> iter = soureleadActionIdList.iterator();
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
			
			updateOrgSubscriptionRegsiteredTabs(orgLocations,adminids);
	 }
	 
	 
	 public void updateOrgSubscriptionRegsiteredTabs(OrgLocations orglocations,String actionIds) throws Exception {
		 
		 String[] sourceleadActionIds = actionIds.split(",");
		 List<Long> actionIdsList = new ArrayList<>();
		 for(int i=0; i<sourceleadActionIds.length;i++) {
			 actionIdsList.add(Long.parseLong(sourceleadActionIds[i]));
		 }
		 
		 List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActionsByIdsList(actionIdsList);
		 Set<Long> tabIds = new HashSet<>();
		 if(actionsList != null && !actionsList.isEmpty()) {
			 for(SourceleadActions action : actionsList) {
				 Tabs tabs = tabService.getTabListByTabId(action.getTabs().getId());
				 if(tabs.getTabs() != null){
					 if(tabs.getId() == 14L) {
						 tabIds.add(tabs.getId());
					 }else {
						 tabIds.add(tabs.getTabs().getId());
					 }
				 }else {
					 tabIds.add(tabs.getId());
				 }
			 }
		 }
		 
		 StringBuilder tabsBuilder = new StringBuilder();
		 for (Long tabId : tabIds) {
			 tabsBuilder.append(tabId).append(",");
		 }
		 String tabsBuilderString = tabsBuilder.substring(0, tabsBuilder.length()-1);
		 
		 OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(orglocations.getOrg().getId(), orglocations.getCountry());
		 orgLicenseSubscription.setSubscriptionTabs(tabsBuilderString);
		 orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
	 }
	 
	 
	 
	  @RequestMapping(value = "/app/ws/validateCrowdSourcingEmail",  method = RequestMethod.GET)
	   public @ResponseBody String validateCrowdSourcingEmail(@RequestParam(value="email", required=false) String email,
	   		@RequestParam(value="type", required=false) String type) throws Exception {
		  
		  UserRole userRole = userRoleService.getIsProfessionalOrRecruiterExistsWithEmailAndUserType(email, type);
		  if(null == userRole){
			  return "Success";
		  }else{
			  return "Invalid";
		  } 
		  
	   }
	  
	  
	  @RequestMapping(value = "/app/ws/validateCrowdSourcingRegistrationEmail",  method = RequestMethod.GET)
	   public @ResponseBody String validateCrowdSourcingRegistrationEmail(@RequestParam(value="email", required=false) String email,
	   		@RequestParam(value="type", required=false) String type) throws Exception {
		  
		  String[] registeredEmail = email.split("[@]"); 
		  EmailDomains emailDomains = emailDomainsService.getEmailDomainsByDomainName(registeredEmail[1]);
		  
		  String status = "";
		  if(null != emailDomains){
			  User user = userService.findByEmail(email);
			  if(user != null){
				  status = "User found";
				  UserRole userRole = userRoleService.getIsProfessionalOrRecruiterExistsWithEmailAndUserType(email, type);
				  if(null == userRole){
					  status = "Success";
				  }else{
					  status = "Invalid";
				  }
			  }else{
				  status = "Not found";
			  }
		  }else{
			  status = "Invalid Domain";
		  }
		  return status;
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
	  
	  @RequestMapping(value = "/app/ws/verifyAccount",  method = RequestMethod.GET)
	   public ModelAndView verifyAccount(@RequestParam(value="requirementUUID", required=false) String requirementUUID,@RequestParam(value="referUUID", required=false) String referUUID) throws Exception {
		  
		  ModelAndView model = new ModelAndView();
		  if(referUUID != null)
			  model.addObject("referUUID", referUUID);
		  if(requirementUUID != null)
			  model.addObject("requirementUUID", requirementUUID);
		  model.addObject("sourceleadUrl", sourceleadUrl);
		  model.setViewName("verifyAccount");
		  return model;
	   }
	  
	  
	  @RequestMapping(value = "/register")
	 	public ModelAndView register(@RequestParam(value = "contactUUID", required = false) String contactUUID,
	 			@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
	 			@RequestParam(value = "referUUID", required = false) String referUUID,
	 			@RequestParam(value = "type", required = false) String type) throws Exception {
	 			ModelAndView model = new ModelAndView();
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			
	 			String registerType = "Wanted to be";
	 			String registerTypeTwo = "Would like to";
	 			
	 			
				List<RegistrationOptionalService> WantedToBeList = registrationOptionalServicesService.getRegistraionOptionalServiceListByRegisterType(registerType);
				model.addObject("WantedToBeList",WantedToBeList);
				
				List<RegistrationOptionalService> WouldLikeToList = registrationOptionalServicesService.getRegistraionOptionalServiceListByRegisterType(registerTypeTwo);
				model.addObject("WouldLikeToList",WouldLikeToList);
				
				if(!isNullOrEmpty(requirementUUID)){
					model.addObject("requirementUUID", requirementUUID);
				}
				
				if(!isNullOrEmpty(referUUID)){
					model.addObject("referUUID", referUUID);
				}
	 			
				List<LookUpData> requestOrgList = lookUpDataService.getLookUpDataByCategory(Constants.REQUEST_ORG);
				 model.addObject("requestOrgList", requestOrgList);
				 
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
	 				model.addObject("type", type);
	 				model.setViewName("registration");
	 			}
	 			model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
	 			return model;
	 	}
	  
	  
	  @RequestMapping(value = "/resetloginCredentials")
	 	public ModelAndView resetloginCredentials(HttpServletRequest request,
	 			@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
	 			@RequestParam(value = "referUUID", required = false) String referUUID) {
	 			ModelAndView model = new ModelAndView();
	 			model.setViewName("resetloginCredentials");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			
	 			if(!isNullOrEmpty(requirementUUID)){
	 				model.addObject("requirementUUID", requirementUUID);
	 			}
	 			
	 			if(!isNullOrEmpty(referUUID)){
	 				model.addObject("referUUID", referUUID);
	 			}
	 			
	 			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg");
	  			if(!isNullOrEmpty(msg)){
	  				model.addObject("msg", msg);
	  				SourceLeadSessionManager.removeSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg");
	  			}
	 			return model;
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
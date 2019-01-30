package com.bis.sourcelead.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.WhiteListIpAddressService;

@Component
public class UserLoginHelper {
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRoleService userRoleService;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Autowired
	WhiteListIpAddressService whiteListIpAddressService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	ModulesService modulesService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	TabsService tabsService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;
	
	@Value("${pmsUrl}")
	private String pmsUrl;
	
	@Value("${atsUrl}")
	private String atsUrl;
	
	@Value("${vmsUrl}")
	private String vmsUrl;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	   

	public  UsernamePasswordAuthenticationToken redirectToDashboard(HttpServletRequest req,String username, String password,String verfDone,String userRoleUUID) 
	{
		
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			
		 String[] usernameWithRole = username.split("##");
		 String uname = usernameWithRole[0];
		 
		 JSONObject userRole = userStatsCacheHelper.getLogInUserRoleStats(uname+"_AUTH_"+userRoleUUID);
		 JSONObject orgLocations = userStatsCacheHelper.getOrgLocationCacheDetails("OrgLocation_"+userRole.get("orgLocationCode").toString());
		 
		 
		 /**
		  * Need to remove this entre try block when couch base is completely established
		  */
		 try {
			UserRole userRole1 = userRoleService.getUserRoleById(Long.valueOf(userRole.get("userRoleId").toString()));
			OrgLocations orgLocations1 = userRole1.getOrgLocations();
			SourceLeadSessionManager.setSessionAttribute(req, "userRole", userRole1);
			SourceLeadSessionManager.setSessionAttribute(req, "orgLocations", orgLocations1);
			User user = userRole1.getUser();
			SourceLeadSessionManager.setSessionAttribute(req, "user", user);
			Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations1.getId());
			SourceLeadSessionManager.setSessionAttribute(req, "candidate", candidate);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 
		 
		  try {
			  if(userRole != null && userRole.get("userState").toString() != null && userRole.get("userState").toString().equals(Status.NOT_LOGGEDIN)){
	  			   UserRole uRole = userRoleService.getUserRoleById(Long.valueOf(userRole.get("userRoleId").toString()));
	  					 uRole.setUserState(Status.ACTIVE);
	  			         uRole.setNewlyLoginUser(Constants.YES);
	  			         userRoleService.addUserRole(uRole);
	                Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(uRole.getUser().getId(), Long.valueOf(orgLocations.get("orgLocationId").toString()));
	                if(null != employee){
	                    userService.saveOrUpdateUserElkDocument(uRole,employee,uRole.getCreatedBy());
	                }
  		  }
		   } catch (Exception e) {
			  e.printStackTrace();
		   }
		  
		  
		  RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
		  if (RequestContextHolder.getRequestAttributes() != null) {
		      HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
		      String ipAddress = getClientIpAddr(request);
		      
		      if(userRole != null){
			    	UserLoginLogoutSession userLoginSession = new UserLoginLogoutSession();
			    	userLoginSession.setIpaddress(ipAddress);
			    	User loginUser = new User();
			    	loginUser.setId(Long.valueOf(userRole.get("userId").toString()));
				    userLoginSession.setUserId(loginUser);
				    if(StringUtils.isNotEmpty(verfDone) && verfDone.equalsIgnoreCase("Y")){
				    	userLoginSession.setAuthenticated("Y");
				    }else{
				    	userLoginSession.setAuthenticated("N");
				    }
				    userLoginSession.setLoginTime(new Date());
				    userLoginSession.setActionUUID(RandomStringUtils.randomAlphanumeric(16));
				    userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
				    request.getSession().setAttribute("userLoginSession", userLoginSession);
			    }
		  }
		  
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "userId", userRole.get("userId"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRole", userRole.get("roleName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userRoleId", userRole.get("userRoleId"));
		  SourceLeadSessionManager.setSessionAttribute(req, "roleHierarchyId", userRole.get("roleHierarchyId"));
		  SourceLeadSessionManager.setSessionAttribute(req, "candidateId", userRole.get("candidateId"));
		  SourceLeadSessionManager.setSessionAttribute(req, "candidateUUID", userRole.get("candidateUUID"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isGlobalAccess", userRole.get("isGlobalUser"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isSuperUser", userRole.get("isSuperUser"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isSuperAdmin", userRole.get("isSuperAdmin"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRoleId", userRole.get("roleId"));	
		  SourceLeadSessionManager.setSessionAttribute(req, "userType", userRole.get("userType"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userCode", userRole.get("userCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userName", userRole.get("userName"));
	      SourceLeadSessionManager.setSessionAttribute(req, "loginUserFirstName", userRole.get("firstName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserLastName", userRole.get("lastName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserEmail", userRole.get("email"));
		  SourceLeadSessionManager.setSessionAttribute(req, "displayName", userRole.get("roleDisplayName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "designation", userRole.get("designation"));
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "fileUrl", orgLocations.get("imgUrl"));
		  SourceLeadSessionManager.setSessionAttribute(req, "averageWorkingHours", orgLocations.get("workingHours"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgName", orgLocations.get("orgName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationId", orgLocations.get("orgLocationId"));
		  SourceLeadSessionManager.setSessionAttribute(req, "locationCode", orgLocations.get("orgLocationCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgShortCode", orgLocations.get("orgShortCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "timeZoneId", orgLocations.get("timeZoneId"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isCompanyOnboarded", orgLocations.get("isCompanyOnboarded"));
		  SourceLeadSessionManager.setSessionAttribute(req, "dateFormat", orgLocations.get("dateFormat"));  
		  SourceLeadSessionManager.setSessionAttribute(req, "country", orgLocations.get("country"));
		  SourceLeadSessionManager.setSessionAttribute(req, "weekOffDays", orgLocations.get("weekOffDays"));
		  List<String> weekOffDaysList = new ArrayList<>();
		  String[] weekOffDays = orgLocations.get("weekOffDays").toString().split(",");
			for (int i = 0; i < weekOffDays.length; i++) {
				weekOffDaysList.add(weekOffDays[i]);
			}
		  SourceLeadSessionManager.setSessionAttribute(req, "weekOffDaysList", weekOffDaysList); 
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "sourceleadUrl", sourceleadUrl);
		  SourceLeadSessionManager.setSessionAttribute(req, "pmsUrl", pmsUrl);
		  SourceLeadSessionManager.setSessionAttribute(req, "atsUrl", atsUrl);
		  SourceLeadSessionManager.setSessionAttribute(req, "vmsUrl", vmsUrl);
		  SourceLeadSessionManager.setSessionAttribute(req, "googleMapsLocationApiKey", googleMapsLocationApiKey);
	
		  
		  if (orgLocations.get("dateFormat") == null || orgLocations.get("orgShortCode") == null) {
				SourceLeadSessionManager.setSessionAttribute(req, "settingsConfigured", "N");
			} else {
				SourceLeadSessionManager.setSessionAttribute(req, "settingsConfigured", "Y");
			}
		  
		  
        List<String> tabsList = null;
	    List<String> actionsList = null;
		Integer associatesTabsCount = 0; 
		Integer billingTabsCount = 0;
		Integer expensesTabCount = 0;
		Integer resourcesTabCount = 0;
		Integer paryrollTabCount = 0;
		
		boolean isJobsTabAccessable = false;
		SourceLeadSessionManager.setSessionAttribute(req, "isJobsTabAccessable", "N");
		boolean isReportTabAccessable = false;
		SourceLeadSessionManager.setSessionAttribute(req, "reportsTabConfigured", "N");
		
		if(!userRole.get("roleName").toString().equalsIgnoreCase(Constants.ROLE_NAME_PROFESSIONAL) && !userRole.get("roleName").toString().equalsIgnoreCase(Constants.ROLE_NAME_INDEPENDENT_RECRUITER)){
			try {
				tabsList =  userStatsCacheHelper.getLogInUserRoleMenusList(uname+"_AUTH_"+userRoleUUID);
				actionsList = userStatsCacheHelper.getLogInUserRoleActionsList(uname+"_AUTH_"+userRoleUUID);
				
			} catch (Exception e) {}
			for(String tab : tabsList){
					if(tab.equalsIgnoreCase("Receivable") || tab.equalsIgnoreCase("Payables")){
						billingTabsCount = billingTabsCount+1;
					}
					
					if(tab.equalsIgnoreCase("My Expenses") || tab.equalsIgnoreCase("Expenses Approval")){
						expensesTabCount = expensesTabCount+1;
					}
					
					if(tab.equalsIgnoreCase("Blog") || tab.equalsIgnoreCase("User Manuals")){
						resourcesTabCount = resourcesTabCount+1;
					}
					
					if(tab.equalsIgnoreCase("Reports")){
						SourceLeadSessionManager.setSessionAttribute(req, "reportsTabConfigured", "Y");
					}
					
					if(tab.equalsIgnoreCase("Run Payroll") || tab.equalsIgnoreCase("My Payroll")){
						paryrollTabCount = paryrollTabCount+1;
					}
					
					if (isReportTabAccessable == false) {
						if(tab.equalsIgnoreCase("Reports")) {
							isReportTabAccessable = true;
							SourceLeadSessionManager.setSessionAttribute(req, "reportsTabConfigured", "Y");
						}
					}
					
					if (isJobsTabAccessable == false) {
						if(tab.equalsIgnoreCase("Jobs")) {
							isJobsTabAccessable = true;
							SourceLeadSessionManager.setSessionAttribute(req, "isJobsTabAccessable", "Y");
						}
					}
					
				}
			
			
			SourceLeadSessionManager.setSessionAttribute(req, "tabsList", tabsList);
			SourceLeadSessionManager.setSessionAttribute(req, "associatesTabsCount", associatesTabsCount);
			SourceLeadSessionManager.setSessionAttribute(req, "billingTabsCount", billingTabsCount);
			SourceLeadSessionManager.setSessionAttribute(req, "expensesTabCount", expensesTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "resourcesTabCount", resourcesTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "paryrollTabCount", paryrollTabCount);
			
			
		  } 
		  return  new UsernamePasswordAuthenticationToken(username, password,  buildUserAuthority(actionsList));
		}else {
			
			 String[] usernameWithRole = username.split("##");
			 String uname = usernameWithRole[0];
			 Long userRoleId = 0l;
			 if(usernameWithRole.length > 1 && StringUtils.isNotEmpty(usernameWithRole[1]) && !usernameWithRole[1].equalsIgnoreCase("0") && NumberUtils.isNumber(usernameWithRole[1])) {
					userRoleId = new Long(usernameWithRole[1]);
			 }
			
			 User loginUser = userService.findByUserName(uname);
			 UserRole userRole = null;
	 	     if(userRoleId != 0 ){
	 	    	try {
					userRole = userRoleService.getUserRoleById(userRoleId);
				} catch (Exception e) {}
				
	 	     }else if(loginUser != null) {
	 	    	 try {
					userRole = userRoleService.getLoginUserPrimaryRole(loginUser.getId());
				} catch (Exception e) {}
	 	     }
			  OrgLocations orgLocations = userRole.getOrgLocations();
			  try {
				  if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
		  			   userRole.setUserState(Status.ACTIVE);
		  			   userRole.setNewlyLoginUser(Constants.YES);
		  			   userRoleService.addUserRole(userRole);
		                Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
		                if(null != employee){
		                    userService.saveOrUpdateUserElkDocument(userRole,employee,userRole.getCreatedBy());
		                }
	  		  }
			   } catch (Exception e) {
				  e.printStackTrace();
			   }
			  
			  
			  RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
			  if (RequestContextHolder.getRequestAttributes() != null) {
			      HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
			      String ipAddress = getClientIpAddr(request);
			      
			      if(loginUser != null){
				    	UserLoginLogoutSession userLoginSession = new UserLoginLogoutSession();
				    	userLoginSession.setIpaddress(ipAddress);
					    userLoginSession.setUserId(loginUser);
					    if(StringUtils.isNotEmpty(verfDone) && verfDone.equalsIgnoreCase("Y")){
					    	userLoginSession.setAuthenticated("Y");
					    }else{
					    	userLoginSession.setAuthenticated("N");
					    }
					    userLoginSession.setLoginTime(new Date());
					    userLoginSession.setActionUUID(RandomStringUtils.randomAlphanumeric(16));
					    userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
					    request.getSession().setAttribute("userLoginSession", userLoginSession);
				    }
			  }
			  
			  
			  req.getSession().setAttribute("userRoleId", userRole.getId());
			  SourceLeadSessionManager.setSessionAttribute(req, "user", loginUser);
			  SourceLeadSessionManager.setSessionAttribute(req, "userId", loginUser.getId().toString());
			  SourceLeadSessionManager.setSessionAttribute(req, "org", orgLocations.getOrg());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocations", orgLocations);
			  SourceLeadSessionManager.setSessionAttribute(req, "loginOrgLocation", orgLocations);
			  SourceLeadSessionManager.setSessionAttribute(req, "averageWorkingHours", orgLocations.getAverageWorkingHours());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationId", orgLocations.getId().toString());
			  SourceLeadSessionManager.setSessionAttribute(req, "locationCode", orgLocations.getLocationCode());
			  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRole", userRole.getRoles().getRoleName());
			  SourceLeadSessionManager.setSessionAttribute(req, "sourceleadUrl", sourceleadUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "pmsUrl", pmsUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "atsUrl", atsUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "vmsUrl", vmsUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "googleMapsLocationApiKey", googleMapsLocationApiKey);
			  SourceLeadSessionManager.setSessionAttribute(req, "userRoleId", userRoleId);
			  SourceLeadSessionManager.setSessionAttribute(req, "roleHierarchyId",
						userRole.getRoles().getRoleHierarchy().getId());
			  SourceLeadSessionManager.setSessionAttribute(req, "dateFormat", orgLocations.getDateFormat());  
			  SourceLeadSessionManager.setSessionAttribute(req, "country", orgLocations.getCountry());
			  SourceLeadSessionManager.setSessionAttribute(req, "userType", userRole.getUserType());
			  SourceLeadSessionManager.setSessionAttribute(req, "displayName", userRole.getRoles().getDisplayName());
			  SourceLeadSessionManager.setSessionAttribute(req, "designation", userRole.getDesignation());
			  SourceLeadSessionManager.setSessionAttribute(req, "isCompanyOnboarded", orgLocations.getIsCompanyOnboarded());
			  SourceLeadSessionManager.setSessionAttribute(req, "timeZoneId", orgLocations.getTimezone().getId().toString());
				 
				 List<String> weekOffDaysList = new ArrayList<>();
					if (orgLocations.getWeekOffDays() != null && !orgLocations.getWeekOffDays().isEmpty()) {
						String[] weekOffDays = orgLocations.getWeekOffDays().split(",");
						for (int i = 0; i < weekOffDays.length; i++) {
							weekOffDaysList.add(weekOffDays[i]);
						}
					} else {
						weekOffDaysList.add("Sat");
						weekOffDaysList.add("Sun");
					}
					SourceLeadSessionManager.setSessionAttribute(req, "weekOffDaysList", weekOffDaysList);
			   
			Candidate candidate;
			try {
				candidate = candidateService.getCandidateByConsultantUserId(userRole.getUser().getId(), orgLocations.getId());
				SourceLeadSessionManager.setSessionAttribute(req, "candidate", candidate);
				SourceLeadSessionManager.setSessionAttribute(req, "candidateId", candidate.getId());
			} catch (Exception e) {}
			SourceLeadSessionManager.setSessionAttribute(req, "isGlobalAccess", userRole.getIsGlobal());
			
			boolean isSuperUser;
			boolean isSuperAdmin;
			try {
				isSuperUser = orgLocationService.isLoginUserIsSuperUser(userRole.getUser().getId(),
						orgLocations.getId());
				SourceLeadSessionManager.setSessionAttribute(req, "isSuperUser", isSuperUser);
				
				isSuperAdmin = orgService.isLoginUserIsSuperAdmin(userRole.getUser().getId(), orgLocations.getOrg().getId());
				if (isSuperAdmin == true) {
					SourceLeadSessionManager.setSessionAttribute(req, "isSuperAdmin", Constants.YES);
				} else {
					SourceLeadSessionManager.setSessionAttribute(req, "isSuperAdmin", Constants.NO);
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			SourceLeadSessionManager.setSessionAttribute(req, "loginUserRoleId", userRole.getRoles().getId());	
			SourceLeadSessionManager.setSessionAttribute(req, "userType", loginUser.getUserType());
			SourceLeadSessionManager.setSessionAttribute(req, "userCode", loginUser.getUserCode());

			StringBuilder userNameSB = new StringBuilder();
			if (null != loginUser.getLastName()) {
				SourceLeadSessionManager.setSessionAttribute(req, "userName",
						userNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			} else {
				SourceLeadSessionManager.setSessionAttribute(req, "userName",
						userNameSB.append(loginUser.getFirstName()).toString());
			}


			
			
	       List<SourceleadActions> actionsList = null;
	       Collection<Tabs> tabsList = null;
	       List<String> tabNamesList = new ArrayList<>();
			Integer associatesTabsCount = 0; 
			Integer billingTabsCount = 0;
			Integer expensesTabCount = 0;
			Integer resourcesTabCount = 0;
			Integer paryrollTabCount = 0;
			
			
			boolean isJobsTabAccessable = false;
			SourceLeadSessionManager.setSessionAttribute(req, "isJobsTabAccessable", "N");
			boolean isReportTabAccessable = false;
			SourceLeadSessionManager.setSessionAttribute(req, "reportsTabConfigured", "N");
			
			if(!userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_PROFESSIONAL) && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_INDEPENDENT_RECRUITER)){
				orgLocations = userRole.getOrgLocations();
				List<Long> modulesId;
				try {
					modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(orgLocations);
					actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
					tabsList = tabsService.getTabsListByOrgLocationId(userRole.getRoles().getId(), orgLocations.getId());
				} catch (Exception e) {}
				for(Tabs tabs : tabsList){
						if(tabs.getTabName().equalsIgnoreCase("Receivable") || tabs.getTabName().equalsIgnoreCase("Payables")){
							billingTabsCount = billingTabsCount+1;
						}
						
						if(tabs.getTabName().equalsIgnoreCase("My Expenses") || tabs.getTabName().equalsIgnoreCase("Expenses Approval")){
							expensesTabCount = expensesTabCount+1;
						}
						
						if(tabs.getTabName().equalsIgnoreCase("Blog") || tabs.getTabName().equalsIgnoreCase("User Manuals")){
							resourcesTabCount = resourcesTabCount+1;
						}
						
						if(tabs.getTabName().equalsIgnoreCase("Reports")){
							SourceLeadSessionManager.setSessionAttribute(req, "reportsTabConfigured", "Y");
						}
						
						if(tabs.getTabName().equalsIgnoreCase("Run Payroll") || tabs.getTabName().equalsIgnoreCase("My Payroll")){
							paryrollTabCount = paryrollTabCount+1;
						}
						
						if (isReportTabAccessable == false) {
							if (tabs.getTabName().equalsIgnoreCase("Reports")) {
								isReportTabAccessable = true;
								SourceLeadSessionManager.setSessionAttribute(req, "reportsTabConfigured", "Y");
							}
						}

						if (isJobsTabAccessable == false) {
							if (tabs.getTabName().equalsIgnoreCase("Jobs")) {
								isJobsTabAccessable = true;
								SourceLeadSessionManager.setSessionAttribute(req, "isJobsTabAccessable", "Y");
							}
						}
						tabNamesList.add(tabs.getTabName());
					}
				
				
				SourceLeadSessionManager.setSessionAttribute(req, "tabsList", tabNamesList);
				SourceLeadSessionManager.setSessionAttribute(req, "associatesTabsCount", associatesTabsCount);
				SourceLeadSessionManager.setSessionAttribute(req, "billingTabsCount", billingTabsCount);
				SourceLeadSessionManager.setSessionAttribute(req, "expensesTabCount", expensesTabCount);
				SourceLeadSessionManager.setSessionAttribute(req, "resourcesTabCount", resourcesTabCount);
				SourceLeadSessionManager.setSessionAttribute(req, "paryrollTabCount", paryrollTabCount);
				
					
			  } 
			  return  new UsernamePasswordAuthenticationToken(username, password,  buildUserAuthorityDummy(actionsList));
		   
		}
   }
	
		private List<GrantedAuthority> buildUserAuthority(List<String> actionsList) {
    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
    	if(actionsList != null && !actionsList.isEmpty()){
    		// Build user's authorities
    		for (String sourceleadAction : actionsList) {
    			setAuths.add(new SimpleGrantedAuthority(sourceleadAction));
    		}
    	}
    	List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
    	return Result;
    }
		
		
	private List<GrantedAuthority> buildUserAuthorityDummy(List<SourceleadActions> actionsList) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		if (actionsList != null && !actionsList.isEmpty()) {
			// Build user's authorities
			for (SourceleadActions sourceleadActions : actionsList) {
				setAuths.add(new SimpleGrantedAuthority(sourceleadActions.getActionRole()));
			}
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
		
	public String getIpAddress(){
		 String ipAddress = null;
		 RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
		  if (attribs != null) {
		      HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
		      ipAddress = getClientIpAddr(request);
		  }
		  return ipAddress;
	}
	public String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress localip = null;
			try {
				localip = java.net.InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
			}
			if(localip != null)
            ip = localip.getHostAddress();
        }
        String[] ipArray = ip.split(",");
		if(ipArray.length > 1){
			for(String s :ipArray){
				if(StringUtils.isNotEmpty(s)){
					ip =s.replaceAll("[^0-9.]", "");
					break;
				}
			}
			
		}else{
			ip = ip.replaceAll("[^0-9.]", "");
		}
        
	    return ip;  
	}
}

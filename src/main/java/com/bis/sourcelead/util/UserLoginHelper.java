package com.bis.sourcelead.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.UserAccountsDTO;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
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
	
	@Value("${sourceleadDomainUrl}")
	private String sourceleadDomainUrl;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	
	@Value("${sourceleadLogoutUrl}")
	private String sourceleadLogoutUrl;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	   

	public  UsernamePasswordAuthenticationToken redirectToDashboard(HttpServletRequest req,String username, String password,String verfDone) 
	{
		
		if("true".equalsIgnoreCase(isCouchbaseEnabled)) {
			
		 String[] usernameWithRole = username.split("##");
		 String uname = usernameWithRole[0];
		 String generatedToken = usernameWithRole[2];
		 
		 JSONObject userSession =  userStatsCacheHelper.getLogInUserSessionJson(generatedToken+"_SESSION");
		 while(userSession.isEmpty()) {
			 userSession =  userStatsCacheHelper.getLogInUserSessionJson(generatedToken+"_SESSION");
		 }
		 JSONObject userRole = userStatsCacheHelper.getLogInUserRoleStats(uname.toLowerCase()+"_AUTH_"+userSession.get("userRoleUUID").toString());
		 JSONObject orgLocations = userStatsCacheHelper.getOrgLocationCacheDetails(userSession.get("orgLocationCode").toString());
		 SourceLeadSessionManager.setSessionAttribute(req, "actionUUID", userSession.get("actionUUID").toString());
		 SourceLeadSessionManager.setSessionAttribute(req, "actionId", Long.parseLong(userSession.get("actionId").toString()));
		 SourceLeadSessionManager.setSessionAttribute(req, "sessionId", userSession.get("sessionId").toString());
		 
		 try {
			
			OrgLocations loginOrgLocations = getLoginOrgLocationDetails(orgLocations);
			SourceLeadSessionManager.setSessionAttribute(req, "orgLocations", loginOrgLocations);
			
			Org logInOrg = getLoginOrgDetails(orgLocations);
			SourceLeadSessionManager.setSessionAttribute(req, "org", logInOrg);
			
			User loginUser = getLoginUserDetails(userRole,userSession);
			SourceLeadSessionManager.setSessionAttribute(req, "user", loginUser);
			
			UserRole loginUserRole = getLoginUserRoleDetails(userRole);
			SourceLeadSessionManager.setSessionAttribute(req, "userRole", loginUserRole);
			
			Candidate candidate = getCandidateDetails(userRole);
			SourceLeadSessionManager.setSessionAttribute(req, "candidate", candidate);
			
			
			
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 
		 
		  try {
			  if(userRole != null && userRole.get("userState").toString() != null && userRole.get("userState").toString().equals("NOT_LOGGEDIN")){
	  			   UserRole uRole = userRoleService.getUserRoleById(Long.valueOf(userRole.get("userRoleId").toString()));
	  					 uRole.setUserState(Status.ACTIVE);
	  			         uRole.setNewlyLoginUser(Constants.NO);
	  			         userRoleService.addUserRole(uRole);
	                Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(uRole.getUser().getId(), Long.valueOf(orgLocations.get("orgLocationId").toString()));
	                if(null != employee){
	                    userService.saveOrUpdateUserElkDocument(uRole,employee,uRole.getCreatedBy());
	                }
	                userStatsCacheHelper.saveUserProfileDetailsToCouchbase(uRole);
  		  }
		   } catch (Exception e) {
			  e.printStackTrace();
		   }
		  
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "sourceleadLogoutUrl", sourceleadLogoutUrl);
		  
		  if(userRole.get("userId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "userId", Long.parseLong(userRole.get("userId").toString()));
		  }
		  
		  if(userRole.get("userRoleId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "userRoleId", Long.parseLong(userRole.get("userRoleId").toString()));
		  }
		  
		  if(userRole.get("roleHierarchyId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "roleHierarchyId", Long.parseLong(userRole.get("roleHierarchyId").toString()));
		  }
		  
		  if(userRole.get("candidateId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "candidateId", Long.parseLong(userRole.get("candidateId").toString()));
		  }
		  
		  if(userRole.get("masterCandidateId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "masterCandidateId", Long.parseLong(userRole.get("masterCandidateId").toString()));
		  }
		  
		  if(userRole.get("shiftId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "shiftId", Long.parseLong(userRole.get("shiftId").toString()));
		  }
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRole", userRole.get("roleName"));
		  
		  if(userRole.get("roleName").toString().equalsIgnoreCase("ROLE_ADMIN")) {
			  SourceLeadSessionManager.setSessionAttribute(req, "isAdmin", true);
		  }else {
			  SourceLeadSessionManager.setSessionAttribute(req, "isAdmin", false);
		  }
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "candidateUUID", userRole.get("candidateUUID"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isGlobalAccess", userRole.get("isGlobalUser"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isSuperUser", userRole.get("isSuperUser"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isSuperAdmin", userRole.get("isSuperAdmin"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRoleId", Long.parseLong(userRole.get("roleId").toString()));	
		  SourceLeadSessionManager.setSessionAttribute(req, "userType", userRole.get("userType"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userCode", userRole.get("userCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserFullName", userRole.get("loginUserFullName"));
	      SourceLeadSessionManager.setSessionAttribute(req, "loginUserFirstName", userRole.get("firstName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserLastName", userRole.get("lastName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserMiddleName", userRole.get("middleName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userImgUrl", userRole.get("imgUrl"));
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserEmail", userRole.get("email"));
		  SourceLeadSessionManager.setSessionAttribute(req, "displayName", userRole.get("roleDisplayName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "designation", userRole.get("designation"));
		  SourceLeadSessionManager.setSessionAttribute(req, "mobilePhone", userRole.get("mobilePhone"));
		  SourceLeadSessionManager.setSessionAttribute(req, "location", userRole.get("location"));
		  SourceLeadSessionManager.setSessionAttribute(req, "linkedIn", userRole.get("linkedIn"));
		  SourceLeadSessionManager.setSessionAttribute(req, "facebook", userRole.get("facebook"));
		  SourceLeadSessionManager.setSessionAttribute(req, "twitter", userRole.get("twitter"));
		  SourceLeadSessionManager.setSessionAttribute(req, "googlePlus", userRole.get("googlePlus"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userLoginSecurity", userRole.get("userLoginSecurity"));
		  SourceLeadSessionManager.setSessionAttribute(req, "newlyLoginUser", userRole.get("newlyLoginUser"));
		  SourceLeadSessionManager.setSessionAttribute(req, "userCode", userRole.get("userCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isPayRollApprover", userRole.get("isPayRollApprover"));
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "fileUrl", orgLocations.get("imgUrl"));
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "averageWorkingHours", orgLocations.get("averageWorkingHours"));
		  SourceLeadSessionManager.setSessionAttribute(req, "workingDaysPerWeek", orgLocations.get("workingDaysPerWeek"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgName", orgLocations.get("orgName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgCode", orgLocations.get("orgCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationName", orgLocations.get("orgLocationName"));
		  SourceLeadSessionManager.setSessionAttribute(req, "dba", orgLocations.get("dba"));
		  
		  if(orgLocations.get("orgLocationId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationId", Long.parseLong(orgLocations.get("orgLocationId").toString()));
		  }
		  
		  if(orgLocations.get("orgId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "orgId", Long.parseLong(orgLocations.get("orgId").toString()));
		  }
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "locationCode", orgLocations.get("orgLocationCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgShortCode", orgLocations.get("orgShortCode"));
		  
		  if(orgLocations.get("timeZoneId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "timeZoneId", Long.parseLong(orgLocations.get("timeZoneId").toString()));
		  }
		  
		  SourceLeadSessionManager.setSessionAttribute(req, "timeZone",orgLocations.get("timeZone"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isCompanyOnboarded", orgLocations.get("isCompanyOnboarded"));
		  SourceLeadSessionManager.setSessionAttribute(req, "dateFormat", orgLocations.get("dateFormat")); 
		  SourceLeadSessionManager.setSessionAttribute(req, "state", orgLocations.get("state"));
		  SourceLeadSessionManager.setSessionAttribute(req, "city", orgLocations.get("city"));
		  SourceLeadSessionManager.setSessionAttribute(req, "zipcode", orgLocations.get("zipcode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "address", orgLocations.get("address"));
		  SourceLeadSessionManager.setSessionAttribute(req, "phone", orgLocations.get("phone"));
		  SourceLeadSessionManager.setSessionAttribute(req, "fax", orgLocations.get("fax"));
		  SourceLeadSessionManager.setSessionAttribute(req, "weekOffDays", orgLocations.get("weekOffDays"));
		  SourceLeadSessionManager.setSessionAttribute(req, "website", orgLocations.get("website"));
		  SourceLeadSessionManager.setSessionAttribute(req, "currencyCode", orgLocations.get("currencyCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "isTimesheetAvgHoursRequired", orgLocations.get("isTimesheetAvgHoursRequired"));
		  if(orgLocations.get("businessTypeId") != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "businessTypeId", Long.parseLong(orgLocations.get("businessTypeId").toString()));
		  }
		  SourceLeadSessionManager.setSessionAttribute(req, "otherBusinessType", orgLocations.get("otherBusinessType"));
		  SourceLeadSessionManager.setSessionAttribute(req, "poEmail", orgLocations.get("emailForPO"));
		  SourceLeadSessionManager.setSessionAttribute(req, "remittanceEmail", orgLocations.get("emailforRemittance"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgDescription", orgLocations.get("orgDescription"));
		  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationLoginSecurity", orgLocations.get("orgLocationLoginSecurity"));
		  SourceLeadSessionManager.setSessionAttribute(req, "countryCode", orgLocations.get("countryCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "currencyCode", orgLocations.get("currencyCode"));
		  SourceLeadSessionManager.setSessionAttribute(req, "createdDate", orgLocations.get("createdDate"));
		  
		  String redirectURL = (String) req.getSession().getAttribute("redirectURL");
			 if(redirectURL != null) {
				 long redirectOrgLocationId = (Long) req.getSession().getAttribute("redirectOrgLocationId");
				 SourceLeadSessionManager.setSessionAttribute(req, "redirectURL", redirectURL);
				 SourceLeadSessionManager.setSessionAttribute(req, "redirectOrgLocationId", redirectOrgLocationId);
			 }
			 
			 
		  UserLoginLogoutSession userLoginSession = (UserLoginLogoutSession) req.getSession().getAttribute("userLoginSession");
		  if(userLoginSession != null) {
			  SourceLeadSessionManager.setSessionAttribute(req, "userLoginSession", userLoginSession);
		  }
		  
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
		  SourceLeadSessionManager.setSessionAttribute(req, "sourceleadDomainUrl", sourceleadDomainUrl);
		  SourceLeadSessionManager.setSessionAttribute(req, "googleMapsLocationApiKey", googleMapsLocationApiKey);
	
		  
		  if (orgLocations.get("dateFormat") == null || orgLocations.get("orgShortCode") == null) {
				SourceLeadSessionManager.setSessionAttribute(req, "settingsConfigured", "N");
			} else {
				SourceLeadSessionManager.setSessionAttribute(req, "settingsConfigured", "Y");
			}
		  
		  if(userRole.get("roleName").toString().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER") || 
				  userRole.get("roleName").toString().equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")) {
			  SourceLeadSessionManager.setSessionAttribute(req, "country", userRole.get("country"));
		  }else {
			  SourceLeadSessionManager.setSessionAttribute(req, "country", orgLocations.get("country"));
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
		
		boolean isVendorsTabAccessable = false;
		SourceLeadSessionManager.setSessionAttribute(req, "isVendorsTabAccessable", "N");
		
		try {
				tabsList =  userStatsCacheHelper.getLogInUserRoleMenusList(uname.toLowerCase()+"_AUTH_"+userSession.get("userRoleUUID").toString());
				actionsList = userStatsCacheHelper.getLogInUserRoleActionsList(uname.toLowerCase()+"_AUTH_"+userSession.get("userRoleUUID").toString());
				
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
					
					if (isVendorsTabAccessable == false) {
						if (tab.equalsIgnoreCase("Vendors")) {
							isVendorsTabAccessable = true;
							SourceLeadSessionManager.setSessionAttribute(req, "isVendorsTabAccessable", "Y");
						}
					}
					
				}
			
			
			SourceLeadSessionManager.setSessionAttribute(req, "tabsList", tabsList);
			SourceLeadSessionManager.setSessionAttribute(req, "associatesTabsCount", associatesTabsCount);
			SourceLeadSessionManager.setSessionAttribute(req, "billingTabsCount", billingTabsCount);
			SourceLeadSessionManager.setSessionAttribute(req, "expensesTabCount", expensesTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "resourcesTabCount", resourcesTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "paryrollTabCount", paryrollTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "actionsList", actionsList);
			
			/*List<Long> jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(req, "requirementIdsList");
			if(jobIdList != null) {
				SourceLeadSessionManager.setSessionAttribute(req, "requirementIdsList", jobIdList);
				System.out.println("jobIdList>>>>>"+jobIdList.size());
			}*/
			
			
			if(userSession.get("loginUserAccounts") != null) {
				List<UserAccountsDTO> accountsList = new ArrayList<>();
				String[] userAccountsList = userSession.get("loginUserAccounts").toString().split(",");
				for(int i=0; i<userAccountsList.length; i++) {
					String[] userAccount = userAccountsList[i].toString().split("\\^");
					JSONObject accountsJson = userStatsCacheHelper.getLogInUserRoleStats(userAccount[0].toLowerCase()+"_AUTH_"+userAccount[1]);
					UserAccountsDTO accountsDTO = new UserAccountsDTO();
					accountsDTO.setRoleName(accountsJson.get("roleName").toString());
					accountsDTO.setDisplayName(accountsJson.get("roleDisplayName").toString());
					accountsDTO.setOrgLocationId(accountsJson.get("orgLocationId").toString());
					accountsDTO.setUserRoleId(accountsJson.get("userRoleId").toString());
					
					if(accountsJson.get("userLoginSecurity") != null) {
						accountsDTO.setLoginSecurity(accountsJson.get("userLoginSecurity").toString());
					}
					
					accountsDTO.setUserId(accountsJson.get("userId").toString());
					
					if(accountsJson.get("designation") != null) {
						accountsDTO.setDesignation(accountsJson.get("designation").toString());
					}
					
					if(accountsJson.get("userType") != null) {
						accountsDTO.setUserType(accountsJson.get("userType").toString());
					}
				
					JSONObject accountOrgLocations = userStatsCacheHelper.getOrgLocationCacheDetails(accountsJson.get("orgLocationCode").toString());
					
					if(accountOrgLocations.get("imgUrl") != null) {
						accountsDTO.setOrgImgUrl(accountOrgLocations.get("imgUrl").toString());
					}
					
					accountsDTO.setOrgLocationLoginSecurity(accountOrgLocations.get("orgLocationLoginSecurity").toString());
					accountsDTO.setOrgLocationName(accountOrgLocations.get("orgLocationName").toString());
					accountsDTO.setOrgName(accountOrgLocations.get("orgName").toString());
					
					if(accountOrgLocations.get("country") != null) {
						accountsDTO.setCountry(accountOrgLocations.get("country").toString());
					}
					
					if(accountOrgLocations.get("state") != null) {
						accountsDTO.setState(accountOrgLocations.get("state").toString());
					}
					
					if(accountOrgLocations.get("city") != null) {
						accountsDTO.setCity(accountOrgLocations.get("city").toString());
					}
					
					accountsList.add(accountsDTO);
				}
				
				SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesList", accountsList);
				SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesListCount", accountsList.size());
				
			}else {
				SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesList", null);
				SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesListCount", 0);
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
			  orgLocations.setTimeZoneId(orgLocations.getTimezone().getTimeZoneId());
			  orgLocations.setOrgCode(orgLocations.getOrg().getOrgCode());
			  try {
				  if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
		  			   userRole.setUserState(Status.ACTIVE);
		  			   userRole.setNewlyLoginUser(Constants.NO);
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
			  SourceLeadSessionManager.setSessionAttribute(req, "sourceleadLogoutUrl", sourceleadLogoutUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "user", loginUser);
			  SourceLeadSessionManager.setSessionAttribute(req, "userId", loginUser.getId());
			  SourceLeadSessionManager.setSessionAttribute(req, "org", orgLocations.getOrg());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocations", orgLocations);
			  SourceLeadSessionManager.setSessionAttribute(req, "orgCode", orgLocations.getOrg().getOrgCode());
			  SourceLeadSessionManager.setSessionAttribute(req, "loginOrgLocation", orgLocations);
			  SourceLeadSessionManager.setSessionAttribute(req, "averageWorkingHours", orgLocations.getAverageWorkingHours());
			  SourceLeadSessionManager.setSessionAttribute(req, "workingDaysPerWeek", orgLocations.getWorkingDaysPerWeek());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationId", orgLocations.getId());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgId", orgLocations.getOrg().getId());
			  SourceLeadSessionManager.setSessionAttribute(req, "locationCode", orgLocations.getLocationCode());
			  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRole", userRole.getRoles().getRoleName());
			  
			  if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
				  SourceLeadSessionManager.setSessionAttribute(req, "isAdmin", true);
			  }else {
				  SourceLeadSessionManager.setSessionAttribute(req, "isAdmin", false);
			  }
			  
		      SourceLeadSessionManager.setSessionAttribute(req, "loginUserFirstName", loginUser.getFirstName());
			  SourceLeadSessionManager.setSessionAttribute(req, "loginUserLastName", loginUser.getLastName());
			  SourceLeadSessionManager.setSessionAttribute(req, "loginUserEmail", loginUser.getEmail());
			  SourceLeadSessionManager.setSessionAttribute(req, "mobilePhone", loginUser.getMobilePhone());
			  
			  SourceLeadSessionManager.setSessionAttribute(req, "userImgUrl", loginUser.getFileUrl());
			  SourceLeadSessionManager.setSessionAttribute(req, "loginUserFullName", loginUser.getFullName());
				  
			  SourceLeadSessionManager.setSessionAttribute(req, "sourceleadUrl", sourceleadUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "pmsUrl", pmsUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "atsUrl", atsUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "vmsUrl", vmsUrl);
			  SourceLeadSessionManager.setSessionAttribute(req, "googleMapsLocationApiKey", googleMapsLocationApiKey);
			  SourceLeadSessionManager.setSessionAttribute(req, "userRoleId", userRoleId);
			  if(userRole.getRoles().getRoleHierarchy() != null) {
				  SourceLeadSessionManager.setSessionAttribute(req, "roleHierarchyId",
							userRole.getRoles().getRoleHierarchy().getId());
			  }
			 
			  SourceLeadSessionManager.setSessionAttribute(req, "dateFormat", orgLocations.getDateFormat());  
			  SourceLeadSessionManager.setSessionAttribute(req, "state", orgLocations.getState());
			  SourceLeadSessionManager.setSessionAttribute(req, "city", orgLocations.getCity());
			  SourceLeadSessionManager.setSessionAttribute(req, "zipcode", orgLocations.getZipCode());
			  SourceLeadSessionManager.setSessionAttribute(req, "address", orgLocations.getOrgLocationAddress());
			  SourceLeadSessionManager.setSessionAttribute(req, "userType", userRole.getUserType());
			  SourceLeadSessionManager.setSessionAttribute(req, "displayName", userRole.getRoles().getDisplayName());
			  SourceLeadSessionManager.setSessionAttribute(req, "designation", userRole.getDesignation());
			  SourceLeadSessionManager.setSessionAttribute(req, "isCompanyOnboarded", orgLocations.getIsCompanyOnboarded());
			  SourceLeadSessionManager.setSessionAttribute(req, "timeZoneId", orgLocations.getTimezone().getId());
			  SourceLeadSessionManager.setSessionAttribute(req, "timeZone", orgLocations.getTimezone().getTimeZoneId());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgName", orgLocations.getOrg().getOrgName());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationName", orgLocations.getOrgLocationName());
			  SourceLeadSessionManager.setSessionAttribute(req, "website", orgLocations.getWebsite());
			  SourceLeadSessionManager.setSessionAttribute(req, "dba", orgLocations.getDba());
			  SourceLeadSessionManager.setSessionAttribute(req, "phone", orgLocations.getPhone());
			  SourceLeadSessionManager.setSessionAttribute(req, "fax", orgLocations.getFax());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgShortCode", orgLocations.getGeneratedUserId());
			  SourceLeadSessionManager.setSessionAttribute(req, "isTimesheetAvgHoursRequired", orgLocations.getIsTimesheetAvgHoursRequired());
			  if(orgLocations.getBusinessType() != null) {
				  SourceLeadSessionManager.setSessionAttribute(req, "businessTypeId", orgLocations.getBusinessType().getId());
			  }
			  SourceLeadSessionManager.setSessionAttribute(req, "otherBusinessType", orgLocations.getOtherBusinessType());
			  SourceLeadSessionManager.setSessionAttribute(req, "poEmail", orgLocations.getPoEmail());
			  SourceLeadSessionManager.setSessionAttribute(req, "remittanceEmail", orgLocations.getRemittanceEmail());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgDescription", orgLocations.getOrgDescription());
			  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationLoginSecurity", orgLocations.getLoginSecurity());
			  
			  SourceLeadSessionManager.setSessionAttribute(req, "location", loginUser.getLocation());
			  SourceLeadSessionManager.setSessionAttribute(req, "linkedIn", loginUser.getLinkedIn());
			  SourceLeadSessionManager.setSessionAttribute(req, "facebook", loginUser.getFacebook());
			  SourceLeadSessionManager.setSessionAttribute(req, "twitter", loginUser.getTwitter());
			  SourceLeadSessionManager.setSessionAttribute(req, "googlePlus", loginUser.getGooglePlus());
			  SourceLeadSessionManager.setSessionAttribute(req, "userLoginSecurity", loginUser.getLoginSecurity());
			  SourceLeadSessionManager.setSessionAttribute(req, "newlyLoginUser", userRole.getNewlyLoginUser());
			  
			  
			  String redirectURL = (String) req.getSession().getAttribute("redirectURL");
				 if(redirectURL != null) {
					 long redirectOrgLocationId = (Long) req.getSession().getAttribute("redirectOrgLocationId");
					 SourceLeadSessionManager.setSessionAttribute(req, "redirectURL", redirectURL);
					 SourceLeadSessionManager.setSessionAttribute(req, "redirectOrgLocationId", redirectOrgLocationId);
				 }
				 
			 UserLoginLogoutSession userLoginSession = (UserLoginLogoutSession) req.getSession().getAttribute("userLoginSession");
			  if(userLoginSession != null) {
				  SourceLeadSessionManager.setSessionAttribute(req, "userLoginSession", userLoginSession);
			  }
			  
			  if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL") || 
					  userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")) {
				  
				  
				  if (userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER") && 
						  StringUtils.isNotBlank(loginUser.getRecruiterRegisterCountry())) {
						SourceLeadSessionManager.setSessionAttribute(req, "country", loginUser.getRecruiterRegisterCountry());
					} else if (userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL") && 
							  StringUtils.isNotBlank(loginUser.getProfessionalRegisterCountry())) {
							SourceLeadSessionManager.setSessionAttribute(req, "country", loginUser.getProfessionalRegisterCountry());
					} else {
						SourceLeadSessionManager.setSessionAttribute(req, "country",
								loginUser.getCountry());
				   }
			  }else {
				  SourceLeadSessionManager.setSessionAttribute(req, "country", orgLocations.getCountry());
			  }
			
			  
			  try {
				  Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if (countries != null) {
					SourceLeadSessionManager.setSessionAttribute(req, "currencyCode", countries.getCurrency());
					SourceLeadSessionManager.setSessionAttribute(req, "loginCountryName", countries.getName());
				}
			 } catch (Exception e2) {
				e2.printStackTrace();
			 }
				
				 
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
				candidate.setMasterCandId(candidate.getMasterCandidateId().getId());
				SourceLeadSessionManager.setSessionAttribute(req, "candidate", candidate);
				SourceLeadSessionManager.setSessionAttribute(req, "candidateId", candidate.getId());
			    SourceLeadSessionManager.setSessionAttribute(req, "masterCandidateId", candidate.getMasterCandidateId().getId());
			} catch (Exception e) {}
			SourceLeadSessionManager.setSessionAttribute(req, "isGlobalAccess", userRole.getIsGlobal());
			
			boolean isSuperUser;
			boolean isSuperAdmin;
			try {
				isSuperUser = orgLocationService.isLoginUserIsSuperUser(userRole.getUser().getId(),
						orgLocations.getId());
				
				if(isSuperUser == true) {
					SourceLeadSessionManager.setSessionAttribute(req, "isSuperUser", Constants.YES);
				}else {
					SourceLeadSessionManager.setSessionAttribute(req, "isSuperUser", Constants.NO);
				}
				
				
				isSuperAdmin = orgService.isLoginUserIsSuperAdmin(userRole.getUser().getId(), orgLocations.getOrg().getId());
				if (isSuperAdmin == true) {
					SourceLeadSessionManager.setSessionAttribute(req, "isSuperAdmin", Constants.YES);
				} else {
					SourceLeadSessionManager.setSessionAttribute(req, "isSuperAdmin", Constants.NO);
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			boolean isPayRollApprover = configurePayrollApproverService.isLoginUserIsPayRollApprover(userRole.getUser().getId(), orgLocations.getId());
			if (isPayRollApprover == true) {
			SourceLeadSessionManager.setSessionAttribute(req, "isPayRollApprover", Constants.YES);
			} else {
			SourceLeadSessionManager.setSessionAttribute(req, "isPayRollApprover", Constants.NO);
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
			boolean isVendorsTabAccessable = false;
			SourceLeadSessionManager.setSessionAttribute(req, "isVendorsTabAccessable", "N");
			
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
						
						if (isVendorsTabAccessable == false) {
							if (tabs.getTabName().equalsIgnoreCase("Vendors")) {
								isVendorsTabAccessable = true;
								SourceLeadSessionManager.setSessionAttribute(req, "isVendorsTabAccessable", "Y");
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
				
					
			  } else {
				  
			   try {
					tabsList = tabsService.getCrowdSourcingTabsListByRoleId(userRole.getRoles().getId());
					actionsList = sourceleadActionsService.getLoginCrowdSourcingUserRoleActionsByOrgLocationId(userRole.getRoles().getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				  for(Tabs tabs : tabsList){
					  tabNamesList.add(tabs.getTabName());
				  }
				SourceLeadSessionManager.setSessionAttribute(req, "tabsList", tabNamesList);
			  }
			
			
			try {
				List<UserRole> userRolesList = userRoleService.getLoginUserAccountsList(loginUser.getId());
				
				if(userRolesList != null && userRolesList.size() > 1) {
					List<UserAccountsDTO> accountsList = new ArrayList<>();
					for(UserRole ur : userRolesList) {
						
						UserAccountsDTO accountsDTO = new UserAccountsDTO();
						accountsDTO.setRoleName(ur.getRoles().getRoleName());
						accountsDTO.setDisplayName(ur.getRoles().getDisplayName());
						accountsDTO.setOrgLocationId(String.valueOf(ur.getRoles().getOrgLocations().getId()));
						accountsDTO.setUserRoleId(String.valueOf(ur.getRoles().getId()));
						
						if(ur.getUser().getLoginSecurity() != null) {
							accountsDTO.setLoginSecurity(ur.getUser().getLoginSecurity());
						}
						
						if(ur.getOrgLocations().getLoginSecurity() != null) {
							accountsDTO.setOrgLocationLoginSecurity(ur.getOrgLocations().getLoginSecurity());
						}
						
						accountsDTO.setUserId(String.valueOf(ur.getUser().getId()));
						
						if(ur.getDesignation() != null) {
							accountsDTO.setDesignation(ur.getDesignation());
						}
						
						if(ur.getUserType() != null) {
							accountsDTO.setUserType(ur.getUserType());
						}
					
						
						if(ur.getOrgLocations().getOrg().getFileUrl() != null) {
							accountsDTO.setOrgImgUrl(ur.getOrgLocations().getOrg().getFileUrl());
						}
						
						accountsDTO.setOrgLocationName(ur.getOrgLocations().getOrgLocationName());
						accountsDTO.setOrgName(ur.getOrgLocations().getOrg().getOrgName());
						
						if(ur.getOrgLocations().getCountry() != null) {
							accountsDTO.setCountry(ur.getOrgLocations().getCountry());
						}
						
						if(ur.getOrgLocations().getState() != null) {
							accountsDTO.setState(ur.getOrgLocations().getState());
						}
						
						if(ur.getOrgLocations().getCity() != null) {
							accountsDTO.setCity(ur.getOrgLocations().getCity());
						}
						
						accountsList.add(accountsDTO);
					}
					
					SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesList", accountsList);
					SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesListCount",accountsList.size());
				}else {
					SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesList", null);
					SourceLeadSessionManager.setSessionAttribute(req, "userSpocRolesListCount", 0);
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		
	
	public String getLoginUserAuthenticationTocken() {
		   Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
	       String authToken[] = auth.getName().split("##");
	       return authToken[2];
	}
	
	
	public UserRole getLoginUserRoleDetails(JSONObject userRoleJson) {
		
		UserRole userRole = new UserRole();
		userRole.setId(Long.valueOf(userRoleJson.get("userRoleId").toString()));
		userRole.setUuid(userRoleJson.get("uuid").toString());
		userRole.setUserRoleName(userRoleJson.get("roleName").toString());
		return userRole;
		
	}
	
	public OrgLocations getLoginOrgLocationDetails(JSONObject orgLocationJson) throws Exception {
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(Long.valueOf(orgLocationJson.get("orgLocationId").toString()));
		orgLocations.setDateFormat(orgLocationJson.get("dateFormat").toString());
		orgLocations.setTimeZoneId(orgLocationJson.get("timeZoneId").toString());
		orgLocations.setTimeZoneName(orgLocationJson.get("timeZone").toString());
		orgLocations.setLocationCode(orgLocationJson.get("orgLocationCode").toString());
		orgLocations.setOrgLocationName(orgLocationJson.get("orgLocationName").toString());
		if(orgLocationJson.get("orgName").toString() != null) {
			orgLocations.setOrgName(orgLocationJson.get("orgName").toString());
		}
		orgLocations.setOrgCode(orgLocationJson.get("orgCode").toString());
		orgLocations.setCurrencyCode(orgLocationJson.get("currencyCode").toString());
		orgLocations.setCountryCode(orgLocationJson.get("countryCode").toString());
		orgLocations.setCountry(orgLocationJson.get("country").toString());
		orgLocations.setOrgId(Long.valueOf(orgLocationJson.get("orgId").toString()));
		orgLocations.setOrgLocationName(orgLocationJson.get("orgLocationName").toString());
		if(orgLocationJson.get("createdDate") != null && orgLocationJson.get("createdDate").toString() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			 Date date = formatter.parse(orgLocationJson.get("createdDate").toString());
			orgLocations.setCreatedDate(date);
		}
	 
		if(orgLocationJson.get("orgShortCode") != null) {
			orgLocations.setGeneratedUserId(orgLocationJson.get("orgShortCode").toString());
		}
		
		if(orgLocationJson.get("averageWorkingHours") != null) {
			orgLocations.setAverageWorkingHours(Integer.valueOf(orgLocationJson.get("averageWorkingHours").toString()));
		}
		
		if(orgLocationJson.get("workingDaysPerWeek") != null) {
			orgLocations.setWorkingDaysPerWeek(Integer.valueOf(orgLocationJson.get("workingDaysPerWeek").toString()));
		}
		
		if(orgLocationJson.get("primaryUserId") != null) {
			orgLocations.setPrimaryUserId(Long.valueOf(orgLocationJson.get("primaryUserId").toString()));
		}
		
		if(orgLocationJson.get("isCompanyOnboarded") != null) {
			orgLocations.setIsCompanyOnboarded(orgLocationJson.get("isCompanyOnboarded").toString());
		}
		
		
		return orgLocations;
		
	}
	
	
	public User getLoginUserDetails(JSONObject userRoleJson, JSONObject userSession) {
		
		User user = new User();
		user.setId(Long.valueOf(userRoleJson.get("userId").toString()));
		user.setUserCode(userRoleJson.get("userCode").toString());
		user.setFullName(userRoleJson.get("loginUserFullName").toString());
		user.setEmail(userRoleJson.get("email").toString());
		user.setUserName(userSession.get("loginUserName").toString());
		
		if(userRoleJson.get("firstName") != null) {
			user.setFirstName(userRoleJson.get("firstName").toString());
		}
		
		if(userRoleJson.get("lastName") != null) {
			user.setLastName(userRoleJson.get("lastName").toString());
		}
		
		if(userRoleJson.get("middleName") != null) {
			user.setMiddelName(userRoleJson.get("middleName").toString());
		}
		
		return user;
	}
	
	
	public Org getLoginOrgDetails(JSONObject orgLocationJson) {
		Org org = new Org();
		org.setId(Long.valueOf(orgLocationJson.get("orgId").toString()));
		org.setOrgCode(orgLocationJson.get("orgCode").toString());
		
		if(orgLocationJson.get("superUserId") != null) {
			org.setSuperUserId(Long.valueOf(orgLocationJson.get("superUserId").toString()));
		}
		return org;
	}
	
	public Candidate getCandidateDetails(JSONObject userRoleJson) {
		
		Candidate candidate = new Candidate();
		candidate.setId(Long.valueOf(userRoleJson.get("candidateId").toString()));
		candidate.setCandidateUUID(userRoleJson.get("candidateUUID").toString());
		if(userRoleJson.get("masterCandidateId") != null) {
			candidate.setMasterCandId(Long.valueOf(userRoleJson.get("masterCandidateId").toString()));
		}
		candidate.setFirstName(userRoleJson.get("firstName").toString());
		if(userRoleJson.get("lastName") != null) {
			candidate.setLastName(userRoleJson.get("lastName").toString());
		}
		candidate.setEmail(userRoleJson.get("email").toString());
		if(userRoleJson.get("mobilePhone") != null) {
			candidate.setMobilePhone(userRoleJson.get("mobilePhone").toString());
		}
			  
		return candidate;
	}
	
	
	public List<UserAccountsDTO> getUserAccountsList(){
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
	      String authToken[] = auth.getName().split("##");
	      JSONObject userSession =  userStatsCacheHelper.getLogInUserSessionJson(authToken[2]+"_SESSION");
		
	      List<UserAccountsDTO> accountsList = new ArrayList<>();
	      
	      String[] userAccountsList = userSession.get("loginUserAccounts").toString().split(",");
			for(int i=0; i<userAccountsList.length; i++) {
				String[] userAccount = userAccountsList[i].toString().split("\\^");
				JSONObject accountsJson = userStatsCacheHelper.getLogInUserRoleStats(userAccount[0].toLowerCase()+"_AUTH_"+userAccount[1]);
				UserAccountsDTO accountsDTO = new UserAccountsDTO();
				accountsDTO.setRoleName(accountsJson.get("roleName").toString());
				accountsDTO.setDisplayName(accountsJson.get("roleDisplayName").toString());
				accountsDTO.setOrgLocationId(accountsJson.get("orgLocationId").toString());
				accountsDTO.setUserRoleId(accountsJson.get("userRoleId").toString());
				
				if(accountsJson.get("userLoginSecurity") != null) {
					accountsDTO.setLoginSecurity(accountsJson.get("userLoginSecurity").toString());
				}
				
				accountsDTO.setUserId(accountsJson.get("userId").toString());
				
				if(accountsJson.get("designation") != null) {
					accountsDTO.setDesignation(accountsJson.get("designation").toString());
				}
				
				if(userSession.get("userType") != null) {
					accountsDTO.setUserType(accountsJson.get("userType").toString());
				}
			
				JSONObject accountOrgLocations = userStatsCacheHelper.getOrgLocationCacheDetails(accountsJson.get("orgLocationCode").toString());
				
				if(accountOrgLocations.get("imgUrl") != null) {
					accountsDTO.setOrgImgUrl(accountOrgLocations.get("imgUrl").toString());
				}
				
				accountsDTO.setOrgLocationLoginSecurity(accountOrgLocations.get("orgLocationLoginSecurity").toString());
				accountsDTO.setOrgLocationName(accountOrgLocations.get("orgLocationName").toString());
				accountsDTO.setOrgName(accountOrgLocations.get("orgName").toString());
				
				if(accountOrgLocations.get("country") != null) {
					accountsDTO.setCountry(accountOrgLocations.get("country").toString());
				}
				
				if(accountOrgLocations.get("state") != null) {
					accountsDTO.setState(accountOrgLocations.get("state").toString());
				}
				
				if(accountOrgLocations.get("city") != null) {
					accountsDTO.setCity(accountOrgLocations.get("city").toString());
				}
				
				accountsList.add(accountsDTO);
			}
		
		return accountsList;
	}
	
	
}

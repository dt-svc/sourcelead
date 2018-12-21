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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
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
	
	   

	public  UsernamePasswordAuthenticationToken redirectToDashboard(HttpServletRequest req,String username, String password,String verfDone)
	{
		
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
		  SourceLeadSessionManager.setSessionAttribute(req, "org", orgLocations.getOrg());
		  SourceLeadSessionManager.setSessionAttribute(req, "orgLocations", orgLocations);
		  SourceLeadSessionManager.setSessionAttribute(req, "loginOrgLocation", orgLocations);
		  SourceLeadSessionManager.setSessionAttribute(req, "averageWorkingHours", orgLocations.getAverageWorkingHours());
		  SourceLeadSessionManager.setSessionAttribute(req, "orgLocationId", orgLocations.getId());
		  SourceLeadSessionManager.setSessionAttribute(req, "locationCode", orgLocations.getLocationCode());
		  SourceLeadSessionManager.setSessionAttribute(req, "loginUserRole", userRole.getRoles().getRoleName());
		  
		   
		Candidate candidate;
		try {
			candidate = candidateService.getCandidateByConsultantUserId(userRole.getUser().getId(), orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(req, "candidate", candidate);
			SourceLeadSessionManager.setSessionAttribute(req, "candidateId", candidate.getId());
		} catch (Exception e) {}
		SourceLeadSessionManager.setSessionAttribute(req, "isGlobalAccess", userRole.getIsGlobal());
		
		
       List<SourceleadActions> actionsList = null;
       Collection<Tabs> tabsList = null;
		Integer associatesTabsCount = 0; 
		Integer billingTabsCount = 0;
		Integer expensesTabCount = 0;
		Integer resourcesTabCount = 0;
		Integer paryrollTabCount = 0;
		if(!userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_PROFESSIONAL) && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_INDEPENDENT_RECRUITER)){
			orgLocations = userRole.getOrgLocations();
			Org org = orgLocations.getOrg();
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
				}
			
			
			SourceLeadSessionManager.setSessionAttribute(req, "tabsList", tabsList);
			SourceLeadSessionManager.setSessionAttribute(req, "associatesTabsCount", associatesTabsCount);
			SourceLeadSessionManager.setSessionAttribute(req, "billingTabsCount", billingTabsCount);
			SourceLeadSessionManager.setSessionAttribute(req, "expensesTabCount", expensesTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "resourcesTabCount", resourcesTabCount);
			SourceLeadSessionManager.setSessionAttribute(req, "paryrollTabCount", paryrollTabCount);
			
				
		  } 
		  return  new UsernamePasswordAuthenticationToken(username, password,  buildUserAuthority(actionsList));
	   }
	
		private List<GrantedAuthority> buildUserAuthority(List<SourceleadActions> actionsList) {

    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
       
    	if(actionsList != null && !actionsList.isEmpty()){
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

package com.bis.sourcelead.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.WhiteListIpAddressService;

@Component
public class UserLoginHelper {
	
	@Autowired 
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired 
	private ModulesService modulesService;
	
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

	public  UsernamePasswordAuthenticationToken redirectToDashboard(UserRole userRole, String username, String password,String verfDone)
	{
		List<Long> modulesId = null;
		User user = userRole.getUser();
		OrgLocations orgLocations = userRole.getOrgLocations();
		  Org org = null;
		  List<SourceleadActions> actionsList = null;
		  List<GrantedAuthority> authorities = null;
		
		  if(null == user.getUserType() || (!user.getUserType().equalsIgnoreCase(Constants.PROFESSIONAL) && !user.getUserType().equalsIgnoreCase(Constants.INDIVIDUAL_RECRUITER))){
			  orgLocations = userRole.getOrgLocations();
			  org = orgLocations.getOrg();
			try {
				modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(orgLocations);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			try {
				//actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }else{
			  try {
				//actionsList = sourceleadActionsService.getLoginCrowdSourcingUserRoleActionsByOrgLocationId(userRole.getRoles().getId());
			  } catch (Exception e) {
				e.printStackTrace();
			} 
		  }
		
		  
		  try {
			  if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
  			  userRole.setUserState(Status.ACTIVE);
  			  userRole.setNewlyLoginUser(Constants.YES);
  			  userRoleService.addUserRole(userRole);

  			  
               /*Update USER DB data as ElasticSearch Documents*/
                 
                Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
                if(null != employee){
                    userService.saveOrUpdateUserElkDocument(userRole,employee,userRole.getCreatedBy());
                }
  		  }
		   } catch (Exception e) {
			  e.printStackTrace();
		   }
		  
		  User loginUser = userService.findByUserName(username);
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
		      request.getSession().setAttribute("userRoleId", userRole.getId());
		  }
		  
		  
		    
		  return new UsernamePasswordAuthenticationToken(username, password, authorities);
		
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

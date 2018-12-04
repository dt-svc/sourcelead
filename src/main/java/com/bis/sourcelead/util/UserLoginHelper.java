package com.bis.sourcelead.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
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

	public  UsernamePasswordAuthenticationToken redirectToDashboard(UserRole userRole, String username, String password,String verfDone)
	{
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
		  return new UsernamePasswordAuthenticationToken(username, password, null);
		
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

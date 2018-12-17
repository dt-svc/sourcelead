package com.bis.sourcelead;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.WhiteListIpAddressService;

/**
 * Custom AuthenticationSuccessHandler
 * 
 * @author: Naresh Bolisetty
 * @date: 25-May-2016
 */
@Configuration
@EnableTransactionManagement
public class SourceleadAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	UserService userService;
	
	 @Autowired
	 NotificationsService notificationsService;
	 
	 @Autowired
	 OrgSpocService orgSpocService;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Autowired
	WhiteListIpAddressService whiteListIpAddressService;
	
	@Autowired
	UserRoleService userRoleService;
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException, AuthenticationException {
		ModelAndView model = new ModelAndView();
		 String UUID = request.getParameter("UUID");
		 String spocUUID = request.getParameter("spocUUID");
		 String requirementUUID = request.getParameter("requirementUUID");
		 String referUUID = request.getParameter("referUUID");
		 User loginUser = userService.findByUserName(authentication.getName());
		 UserRole userRole = null;
			try {
				userRole = userRoleService.getLoginUserPrimaryRole(loginUser.getId());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String ipAddress = getClientIpAddr(request);
			boolean validIp = whiteListIpAddressService.validateIPAddress(ipAddress, userRole,null,null);
		      if(!validIp){
		    	  response.sendRedirect("/login?allowed=false");
		      }else{
		    	  try{
		  			
		 			 if(null != UUID && !"".equalsIgnoreCase(UUID) && null != spocUUID && !"".equalsIgnoreCase(spocUUID)){
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
		 			 }
		 			 
		 			
		 		    if(loginUser != null){
		 		    	UserLoginLogoutSession userLoginSession = new UserLoginLogoutSession();
		 		    	
		 		    	userLoginSession.setIpaddress(ipAddress);
		 			    userLoginSession.setUserId(loginUser);
		 			    userLoginSession.setLoginTime(new Date());
		 			    userLoginSession.setActionUUID(RandomStringUtils.randomAlphanumeric(16));
		 			    userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
		 			    request.getSession().setAttribute("userLoginSession", userLoginSession);
		 		    }
		 		} catch (Exception e) {
		 			e.printStackTrace();
		 		} 
		 	    finally{
		 	    	if(null != UUID && !"".equalsIgnoreCase(UUID) && null != spocUUID && !"".equalsIgnoreCase(spocUUID)){
		 	    		response.sendRedirect("/app/dashboard?invitation=true");
		 	    	}else{
		 	    		if(!isNullOrEmpty(requirementUUID) && isNullOrEmpty(referUUID)){
		 	    			response.sendRedirect("/app/dashboard?requirementUUID="+requirementUUID);
		 	    		}
		 	    		else if(!isNullOrEmpty(requirementUUID) && !isNullOrEmpty(referUUID)){
		 	    			response.sendRedirect("/app/dashboard?requirementUUID="+requirementUUID+"&referUUID="+referUUID);
		 	    		}
		 	    		else{
		 	    			response.sendRedirect("/app/dashboard");
		 	    		}
		 	    		
		 	    	}
		 	    }
		      }
	}

	public static String getClientIpAddr(HttpServletRequest request) {  
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
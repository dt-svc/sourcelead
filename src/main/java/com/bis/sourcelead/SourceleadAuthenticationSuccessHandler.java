package com.bis.sourcelead;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserService;

/**
 * Custom AuthenticationSuccessHandler
 * 
 * @author: Naresh Bolisetty
 * @date: 25-May-2016
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.bis.sourcelead")
public class SourceleadAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	UserService userService;
	
	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		try{
			User loginUser = userService.findByUserName(authentication.getName());
		    if(loginUser != null){
		    	UserLoginLogoutSession userLoginSession = new UserLoginLogoutSession();
		    	String ipAddress = getClientIpAddr(request);
		    	userLoginSession.setIpaddress(ipAddress);
			    userLoginSession.setUserId(loginUser);
			    userLoginSession.setLoginTime(new Date());
			    userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
			    request.getSession().setAttribute("userLoginSession", userLoginSession);
		    }
		}
	    finally{
	    	response.sendRedirect("/sourcelead/dashboard");
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
	    return ip;  
	}
}
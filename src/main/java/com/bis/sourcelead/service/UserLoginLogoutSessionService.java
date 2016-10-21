package com.bis.sourcelead.service;


import com.bis.sourcelead.entity.UserLoginLogoutSession;
public interface UserLoginLogoutSessionService  {
   
	UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession);
	
	UserLoginLogoutSession getLastLoginRecord(Long userId);
	
	
	
}	
		
	

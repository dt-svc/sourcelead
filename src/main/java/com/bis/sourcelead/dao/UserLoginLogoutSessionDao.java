package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.UserLoginLogoutSession;

public interface UserLoginLogoutSessionDao {
	
    UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession);
	
	UserLoginLogoutSession getLastLoginRecord(Long userId);
	
}
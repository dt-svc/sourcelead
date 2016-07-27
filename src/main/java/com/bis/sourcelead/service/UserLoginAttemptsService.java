package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.UserLoginAttempts;

public interface UserLoginAttemptsService {
	
	public UserLoginAttempts addUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception;
	
	public UserLoginAttempts getUserLoginAttemptsByUserId(Long userId) throws Exception;
	
	public UserLoginAttempts deleteUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception;
	

}

package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.UserLoginAttempts;

public interface UserLoginAttemptsDao {

	public UserLoginAttempts addUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception;
	
	public UserLoginAttempts getUserLoginAttemptsByUserId(Long userId) throws Exception;
	
	public UserLoginAttempts deleteUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception;
}

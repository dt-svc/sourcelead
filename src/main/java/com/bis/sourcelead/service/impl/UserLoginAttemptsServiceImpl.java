package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserLoginAttemptsDao;
import com.bis.sourcelead.entity.UserLoginAttempts;
import com.bis.sourcelead.service.UserLoginAttemptsService;

@Service
public class UserLoginAttemptsServiceImpl implements UserLoginAttemptsService{
	
	@Autowired
	private UserLoginAttemptsDao userLoginAttemptsDao;

	@Override
	public UserLoginAttempts addUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception{
		return userLoginAttemptsDao.addUserLoginAttempts(userLoginAttempts);
	}

	@Override
	public UserLoginAttempts getUserLoginAttemptsByUserId(Long userId) throws Exception {
		return userLoginAttemptsDao.getUserLoginAttemptsByUserId(userId);
	}

	@Override
	public UserLoginAttempts deleteUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception {
		return userLoginAttemptsDao.deleteUserLoginAttempts(userLoginAttempts);
	}

}

package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserLoginLogoutSessionDao;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;

/**
 * This Service Class is UserLoginLogoutSession to get UserLoginLogoutSession Object
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */

@Service
public class UserLoginLogoutSessionServiceImpl implements UserLoginLogoutSessionService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginLogoutSessionServiceImpl.class);

	@Autowired
	private UserLoginLogoutSessionDao userLoginLogoutSessionDao;

	@Override
	public UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession) {
		return userLoginLogoutSessionDao.storeUserLoginLogoutSession(userLoginLogoutSession);
	}

	@Override
	public UserLoginLogoutSession getLastLoginRecord(Long userId) {
		return userLoginLogoutSessionDao.getLastLoginRecord(userId);
	}
	
	
}

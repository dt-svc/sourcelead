package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UserService;

/**
 * This Service Class is used to get User Object 
 * 
 * @author: Naresh Bolisetty
 * @date: 27-May-2016
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	

	@Override
	public User findByUserName(String username) {
		logger.debug("In UserService class...");
		return userDao.findByUserName(username);
	}


	@Override
	public User addUser(User user) throws Exception {
		logger.debug("In UserService class...");
		return userDao.addUser(user);
	}


	@Override
	public User getUserById(Long Id) throws Exception{
		logger.debug("In UserService class...");
		return userDao.getUserById(Id);
	}

	
}

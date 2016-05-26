package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.LoginDAO;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.LoginService;

/**
 * This class is used for...
 * 
 * @author: Srinivas Vemula
 * @date: 12-May-2016
 */

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginDAO loginDAO;
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean checkAuthentication(String userName, String userPassword) {
		logger.debug("In Service class...");

		//loginDAO.checkAuthentication(userName, userPassword);
		// TODO: temporary purpose
		if ("admin".equals(userName) && "admin".equals(userPassword)) {
			return true;
		} else {
			return false;
		}

		// return loginDAO.checkAuthentication(userName, userPassword);
		// return false;
	}

	
}

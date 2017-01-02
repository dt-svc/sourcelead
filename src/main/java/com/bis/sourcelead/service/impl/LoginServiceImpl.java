package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserLoginAttemptsDao;
import com.bis.sourcelead.entity.UserLoginAttempts;

@Repository
public class UserLoginAttemptsDaoImpl implements UserLoginAttemptsDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserLoginAttemptsDaoImpl.class);
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserLoginAttempts addUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception {
		logger.debug("Adding UserLoginAttempts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(userLoginAttempts);
			logger.debug("UserLoginAttempts Added/Updated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLoginAttempts;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserLoginAttempts getUserLoginAttemptsByUserId(Long userId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<UserLoginAttempts> userLoginAttempts = new ArrayList<UserLoginAttempts>();
		logger.debug("UserLoginAttempts by UserId start");
		userLoginAttempts = session.createQuery("from UserLoginAttempts loginAttempts where loginAttempts.user.id=:userId " ).setLong( "userId", userId ).list();
		logger.debug("UserLoginAttempts by UserId end");
		if (userLoginAttempts.size() > 0) {
			return userLoginAttempts.get(0);
		} else {
			return null;
		}
   	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserLoginAttempts deleteUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception {
		logger.debug("Deleting UserLoginAttempts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.delete(userLoginAttempts);
			logger.debug("Deleting Added/Updated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLoginAttempts;
	}

}

package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserLoginAttemptsDao;
import com.bis.sourcelead.entity.UserLoginAttempts;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class UserLoginAttemptsDaoImpl implements UserLoginAttemptsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;

	private static final Logger logger = LoggerFactory.getLogger(UserLoginAttemptsDaoImpl.class);
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserLoginAttempts addUserLoginAttempts(UserLoginAttempts userLoginAttempts) throws Exception {
		logger.debug("Adding UserLoginAttempts");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(userLoginAttempts != null){
				session.saveOrUpdate(userLoginAttempts);
				logger.debug("UserLoginAttempts Added/Updated Successfully");
			}
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"UserLoginAttempts"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
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
		} catch (Exception exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(statusDelete,"UserLoginAttempts"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		return userLoginAttempts;
	}

}

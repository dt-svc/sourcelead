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

import com.bis.sourcelead.dao.UserLoginLogoutSessionDao;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class UserLoginLogoutSessionDaoImpl implements UserLoginLogoutSessionDao {

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
	

	private static final Logger logger = LoggerFactory.getLogger(UserLoginLogoutSessionDaoImpl.class);
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserLoginLogoutSession storeUserLoginLogoutSession(UserLoginLogoutSession userLoginLogoutSession){
		logger.debug("Storing UserLoginLogoutSession Record");
		Session session = this.sessionFactory.getCurrentSession();
		if (userLoginLogoutSession != null) {
			session.saveOrUpdate(userLoginLogoutSession);
		}
		logger.debug("UserLoginLogoutSession Added Successfully");
		return userLoginLogoutSession;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserLoginLogoutSession getLastLoginRecord(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserLoginLogoutSession> userLoginLogoutSessionList = new ArrayList<UserLoginLogoutSession>();
		logger.debug("getLastLoginRecord start");
		String query = "from UserLoginLogoutSession uls left join fetch uls.userId where uls.id IN (select max(tempuls.id) from UserLoginLogoutSession tempuls where tempuls.userId.id =:userId) and uls.userId.id =:userId ";
		userLoginLogoutSessionList = session.createQuery(query).setCacheable(true).setLong( "userId", userId ).setCacheable(true).list();
		logger.debug("getLastLoginRecord end");
		if (userLoginLogoutSessionList.size() > 0) {
			return userLoginLogoutSessionList.get(0);
		} else {
			return null;
		}
	}
	
	

}
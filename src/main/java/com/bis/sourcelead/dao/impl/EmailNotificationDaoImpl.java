package com.bis.sourcelead.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmailNotificationDao;
import com.bis.sourcelead.entity.EmailNotification;

/**
 * 
 * @author Prasad salina
 *
 */
@Repository
public class EmailNotificationDaoImpl implements EmailNotificationDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailScenarioDaoImpl.class);

	/**
	 * This method for save the EmailNotification
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailNotification save(EmailNotification emailNotification) throws Exception {
		logger.debug("In EmailNotificationDaoImpl class save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(emailNotification);
		return emailNotification;	
	}

	/**
	 * This method for get the EmailNotification by ID
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailNotification getById(Long emailNotificationd) throws Exception {
		logger.debug("In EmailNotificationDaoImpl class getById method");
		Session session = this.sessionFactory.getCurrentSession();
		EmailNotification emailNotification = session.get(EmailNotification.class, emailNotificationd);
        return session.get(EmailNotification.class, emailNotificationd);
	}

}

package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserMailUnsubscribeDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * @author Venkat Kesav
 *
 */
@Repository
public class UserMailUnsubscribeDaoImpl implements UserMailUnsubscribeDao {

	private static final Logger logger = LoggerFactory.getLogger(UserMailUnsubscribeDaoImpl.class);
	
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
	
	/**
	 * @author Venkat Kesav
	 * @since  March 07 2018
	 * @version 0.0.1
	 * 
	 * This method is used to store User Mail Unsubscription in User_Mail_Unsubscribe table.
	 *  
	 * 
	 * @param UserMailUnsubscribe This is the first parameter to saveOrUpdateUserMailUnsubscription method
	 * 
	 * @return UserMailUnsubscribe
	 * 
	 * @throws NullPointer
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserMailUnsubscribe saveOrUpdateUserMailUnsubscription(UserMailUnsubscribe userMailUnsubscribe) throws Exception {
		logger.debug("Storing Candidate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userMailUnsubscribe != null) {
				session.saveOrUpdate(userMailUnsubscribe);
			}
			logger.debug("UserMailUnsubscription Stored Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"UserMailUnsubscribe"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return userMailUnsubscribe;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserId(Long userId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserMailUnsubscribe umus where umus.userId.id =:userId ";
		List<UserMailUnsubscribe> unsubscriptionList =  session.createQuery(query).setLong("userId", userId).list();
		UserMailUnsubscribe UserMailUnsubscribe = null;
		if(unsubscriptionList != null && !unsubscriptionList.isEmpty()) {
			UserMailUnsubscribe = unsubscriptionList.iterator().next();
		}
		return UserMailUnsubscribe;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmail(String userEmail) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserMailUnsubscribe umus where umus.userId.email =:userEmail ";
		List<UserMailUnsubscribe> unsubscriptionList = session.createQuery(query).setString("userEmail", userEmail).list();
		UserMailUnsubscribe UserMailUnsubscribe = null;
		if(unsubscriptionList != null && !unsubscriptionList.isEmpty()) {
			UserMailUnsubscribe = unsubscriptionList.iterator().next();
		}
		return UserMailUnsubscribe;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserMailUnsubscribe getUserMailUnsubscriptionByUserEmailAndOrgLocationCode(String userEmail, String orgLocationCode) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserMailUnsubscribe umus where umus.userId.email =:userEmail and umus.orgLocationId.locationCode =:orgLocationCode ";
		List<UserMailUnsubscribe> unsubscriptionList = session.createQuery(query).setString("userEmail", userEmail).setString("orgLocationCode", orgLocationCode).list();
		UserMailUnsubscribe UserMailUnsubscribe = null;
		if(unsubscriptionList != null && !unsubscriptionList.isEmpty()) {
			UserMailUnsubscribe = unsubscriptionList.iterator().next();
		}
		return UserMailUnsubscribe;
	}
	
}

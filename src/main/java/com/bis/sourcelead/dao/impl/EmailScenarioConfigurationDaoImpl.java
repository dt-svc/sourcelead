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

import com.bis.sourcelead.dao.EmailScenarioConfigurationDao;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;


@Repository
public class EmailScenarioConfigurationDaoImpl implements EmailScenarioConfigurationDao{

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
	
	private static final Logger logger = LoggerFactory.getLogger(EmailScenarioDaoImpl.class);

	/**
	 * This method for get the EmailScenario
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenarioConfiguration save(EmailScenarioConfiguration configuration) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(configuration != null){
				session.saveOrUpdate(configuration);
				logger.debug("EmailScenario Added Successfully");
			}
		}catch(Exception exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"EmailScenarioConfiguration"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return configuration;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenarioConfiguration getEmailscenarioConByOrgLocId(String email, Long orgLocId) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class getEmailscenarioConByOrgLocId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenarioConfiguration es where es.emailId = :email and es.orgLocationId.id = :orgLocId";
		List<EmailScenarioConfiguration> emailScenariosList =  session.createQuery(query.toString())
				                                         .setString("email", email)
				                                         .setLong("orgLocId", orgLocId).list();
		EmailScenarioConfiguration emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenarioConfiguration) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenarioConfiguration getEmailConfigOrgLocId(Long orgLocId) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class getEmailscenarioConByOrgLocId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenarioConfiguration es where  es.orgLocationId.id = :orgLocId";
		List<EmailScenarioConfiguration> emailScenariosList =  session.createQuery(query.toString())
				                                         .setLong("orgLocId", orgLocId).list();
		EmailScenarioConfiguration emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenarioConfiguration) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailConfigurationFromMail saveFromMail(EmailConfigurationFromMail fromMail) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class saveFromMail method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(fromMail != null){
				session.saveOrUpdate(fromMail);
				logger.debug("EmailConfigurationFromMail Added Successfully");
			}
		}catch(Exception exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"EmailConfigurationFromMail"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return fromMail;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmailConfigurationFromMail> getConfFromMailList(Long orgLocId) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class getConfFromMailList method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailConfigurationFromMail es where  es.orgLocationId.id = :orgLocId";
		List<EmailConfigurationFromMail> emailScenariosList =  session.createQuery(query.toString()) .setLong("orgLocId", orgLocId).list();
		return emailScenariosList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailConfigurationFromMail deleteFromMail(EmailConfigurationFromMail emailConfigurationFromMail) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (emailConfigurationFromMail != null) {
				session.delete(emailConfigurationFromMail);
			}
			logger.debug("emailConfigurationFromMail deleted Successfully");
		}catch (Exception exception) {
		    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"emailConfigurationFromMail"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return emailConfigurationFromMail;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailConfigurationFromMail getFromMailById(Long fromMailId) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class getFromMailById method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailConfigurationFromMail es where  es.id = :fromMailId";
		List<EmailConfigurationFromMail> emailScenariosList =  session.createQuery(query.toString()) .setLong("fromMailId", fromMailId).list();
		EmailConfigurationFromMail emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailConfigurationFromMail) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailConfigurationFromMail getFromMailByUUID(String UUID) throws Exception {
		logger.debug("In EmailScenarioConfigurationDaoImpl class getFromMailById method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailConfigurationFromMail es where  es.email_UUID = :UUID";
		List<EmailConfigurationFromMail> emailScenariosList =  session.createQuery(query.toString()) .setString("UUID", UUID).list();
		EmailConfigurationFromMail emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailConfigurationFromMail) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}
	
}

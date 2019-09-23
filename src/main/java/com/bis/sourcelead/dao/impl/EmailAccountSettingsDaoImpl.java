package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmailAccountSettingsDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class EmailAccountSettingsDaoImpl implements EmailAccountSettingsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(EmailAccountSettingsDaoImpl.class);
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public long storeEmailAccountSettings(EmailAccountSettings emailAccountSettings) throws Exception {
		logger.debug("Storing EmailAccountSettings");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (emailAccountSettings != null) {
				session.saveOrUpdate(emailAccountSettings);
			}
			logger.debug("emailAccountSettings Stored Successfully");
			return emailAccountSettings.getId();
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "EmailAccountSettings"));
			throw new SourceLeadConstraintViolationException(message.toString() + exception.getCause().toString());
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<EmailAccountSettings> emailAccountSettingsList(Long loginUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailAccountSettings emailAccount  where emailAccount.user.id =:loginUserId "; 
		List<EmailAccountSettings> emailAccountsList =session.createQuery(query).setLong("loginUserId", loginUserId).list();
		return emailAccountsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailAccountSettings getEmailAccount(Long id, Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailAccountSettings emailAccount  where emailAccount.id =:id and emailAccount.user.id = :userId"; 
		List<EmailAccountSettings> emailAccountList =  session.createQuery(query) .setLong("id", id).setLong("userId", userId).list();
		EmailAccountSettings emailAccount = null;
		if(emailAccountList != null && !emailAccountList.isEmpty())
		{
			emailAccount = (EmailAccountSettings) emailAccountList.iterator().next();
		}
		return emailAccount;
	}

	@Override
	@Transactional
	public EmailAccountSettings getEmailAccount(String email, String password, long userId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from EmailAccountSettings eas where eas.user.id = :userId and eas.email = :email and eas.password = :password";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("userId", userId);
			queryObj.setString("email", email);
			queryObj.setString("password", password);
			List<EmailAccountSettings> emailAccountSettingsList = queryObj.list();
			if (emailAccountSettingsList != null && !emailAccountSettingsList.isEmpty())
				return emailAccountSettingsList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void deleteEmailAccount(Long id, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try{
		EmailAccountSettings emailAccountSettings = (EmailAccountSettings) session.get(EmailAccountSettings.class, id);
		session.delete(emailAccountSettings);
		}catch(Exception exception){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"email"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
	}

}

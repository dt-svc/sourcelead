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

import com.bis.sourcelead.dao.PasswordResetsDao;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class PasswordResetsDaoImpl implements PasswordResetsDao{
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PasswordResets savePasswordResets(PasswordResets passwordResets) throws Exception {

		logger.debug("Adding PasswordResets");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(passwordResets);
			logger.debug("PasswordResets Added Successfully");
		} catch (Exception exception) {
			  StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return passwordResets;
	
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PasswordResets getPasswordResetsByUserId(Long userId) throws Exception{
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PasswordResets> passwordResets = new ArrayList<PasswordResets>();
		logger.debug("PasswordResets by UserId start");
		passwordResets = session.createQuery("from PasswordResets passwordResets where passwordResets.user.id=:userId and passwordResets.status = 'Open' " ).setLong( "userId", userId ).list();
		logger.debug("PasswordResets by UserId end");
		if (passwordResets.size() > 0) {
			return passwordResets.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode)throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PasswordResets> passwordResets = new ArrayList<PasswordResets>();
		logger.debug("PasswordResets by UserId start");
		passwordResets = session.createQuery("from PasswordResets passwordResets where passwordResets.verificationCode=:verificationCode and passwordResets.status = 'Open' " ).setString( "verificationCode", verificationCode ).list();
		logger.debug("PasswordResets by UserId end");
		if (passwordResets.size() > 0) {
			return passwordResets.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PasswordResets deletePasswordResets(PasswordResets passwordResets) throws Exception{

		logger.debug("Deleting PasswordResets");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.delete(passwordResets);
			logger.debug("PasswordResets Deleting Successfully");
		} catch (Exception e) {
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"PASSWORD_RESET"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		return passwordResets;
	
		
	}
	
	

}

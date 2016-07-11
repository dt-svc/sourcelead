package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PasswordResetsDao;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.User;

@Repository
public class PasswordResetsDaoImpl implements PasswordResetsDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PasswordResets savePasswordResets(PasswordResets passwordResets) {

		logger.debug("Adding PasswordResets");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(passwordResets);
			logger.debug("PasswordResets Added Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passwordResets;
	
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public PasswordResets getPasswordResetsByUserId(Long userId) {
		
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
	public PasswordResets getPasswordResetsByVerificationCode(String verificationCode) {
		
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
	public PasswordResets deletePasswordResets(PasswordResets passwordResets) {

		logger.debug("Deleting PasswordResets");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.delete(passwordResets);
			logger.debug("PasswordResets Deleting Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passwordResets;
	
		
	}
	
	

}

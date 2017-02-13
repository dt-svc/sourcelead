package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailNotificationDao;
import com.bis.sourcelead.entity.EmailNotification;
import com.bis.sourcelead.service.EmailNotificationService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author Prasad salina
 *
 */

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService{

	
private static final Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);
	
	@Autowired
	private EmailNotificationDao emailNotificationDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public EmailNotification save(EmailNotification emailNotification) throws Exception {
		logger.debug("In EmailNotificationServiceImpl class save method");
		emailNotification.setCreatedDate(commonUtil.currentDate());
		return emailNotificationDao.save(emailNotification);
	}

	@Override
	public EmailNotification getById(Long emailNotificationd) throws Exception {
		logger.debug("In EmailNotificationServiceImpl class getById method...");
		return emailNotificationDao.getById(emailNotificationd);
	}

}

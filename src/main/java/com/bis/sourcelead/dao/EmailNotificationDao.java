package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.EmailNotification;

public interface EmailNotificationDao {
	
	EmailNotification save(EmailNotification emailNotification) throws Exception;

	EmailNotification getById(Long emailNotificationd) throws Exception;

}

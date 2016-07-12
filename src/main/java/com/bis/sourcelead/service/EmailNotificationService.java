package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.EmailNotification;

/**
 * 
 * @author Prasad salina
 *
 */
public interface EmailNotificationService {

	EmailNotification save(EmailNotification emailNotification) throws Exception;

	EmailNotification getById(Long emailNotificationd) throws Exception;

}

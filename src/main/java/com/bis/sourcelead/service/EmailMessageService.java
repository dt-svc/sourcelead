package com.bis.sourcelead.service;

import java.util.List;

import javax.mail.internet.InternetAddress;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;

/**
 * 
 * @author Prasad salina
 *
 */
public interface EmailMessageService {
	
	void sendEmail(EmailMessage emailMessage,Long locationsId, InternetAddress[] iAdressArray, List<EmailAttachment> attachmentList) throws Exception;

	//void sendEmailWithAttachment(EmailMessage emailMessage, Long locationsId,InternetAddress[] iAdressArray, List<EmailAttachment> attachmentList, String[] submissionIds) throws Exception;

}

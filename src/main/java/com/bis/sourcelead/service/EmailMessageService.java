package com.bis.sourcelead.service;

import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;

/**
 * 
 * @author Prasad salina
 *
 */
public interface EmailMessageService {
	
	void sendEmail(EmailMessage emailMessage,Long locationsId, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList) throws Exception;

}

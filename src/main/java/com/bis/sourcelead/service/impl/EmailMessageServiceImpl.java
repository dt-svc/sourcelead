package com.bis.sourcelead.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailNotification;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.service.EmailMessageService;
import com.bis.sourcelead.service.EmailNotificationService;
import com.bis.sourcelead.service.SubmissionXlsService;

/**
 * 
 * @author Prasad salina
 *
 */
@Service
public class EmailMessageServiceImpl implements EmailMessageService {
	
	@Autowired
	EmailNotificationService emailNotificationService;
	
	@Autowired
	SubmissionXlsService  submissionXlsService;
	
	@Override
	public String sendEmail(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList) throws Exception {
		if(emailMessage != null)
		{
			Long emailNotificationd = null;
			if(null == attachmentList){
				emailNotificationd = storeEmailInfoInDB(emailMessage,emailRecipientList);
			}
			    
				
			 sendMessage(emailMessage,emailRecipientList,attachmentList);
			    
		 // update send flag true if the no exception, to Email Value
		    if(null == attachmentList){
		    	updateEmailInfoWithDeliveryStatus(emailNotificationd);
		    }
		}
		return "success";
	}
	
	private void sendMessage(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList)
	{
		
		Properties props = new Properties();
		
		
        props.put("mail.smtp.host", "secure.emailsrvr.com");
        props.put("mail.smtp.localhost", "localhost");
        props.put("mail.stmp.user", "qatimesheets@microinfoinc.com");          
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.password", "password");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
	        
	        
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = "qatimesheets@microinfoinc.com";
                String password = "Welcome1";
                return new PasswordAuthentication(username,password); 
            }
        });


		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("qatimesheets@microinfoinc.com", emailMessage.getFromName()));
			
            ArrayList<String> recipientList = new ArrayList<String>();
            String UUID = null;
			
			for(EmailRecipient recipient : emailRecipientList){
				recipientList.add(recipient.getEmail());
			}
			String[] recipients = recipientList.toArray(new String[recipientList.size()]);
			InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++)
            {
                addressTo[i] = new InternetAddress(recipients[i]);
            }
			message.setRecipients(Message.RecipientType.TO, addressTo);
			
			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			message.setSubject(emailMessage.getSubject());
			
			
			if(attachmentList.size() > 0){
				for(EmailAttachment attachment : attachmentList){
					UUID = attachment.getName();
					
					 SubmissionXls xls = submissionXlsService.getSubmissionXlsByUUID(UUID);
			         
			         DataSource dataSource = new ByteArrayDataSource(xls.getDocFile(), "application/pdf");
			         MimeBodyPart xlsBodyPart = new MimeBodyPart();
			         xlsBodyPart.setDataHandler(new DataHandler(dataSource));
			         xlsBodyPart.setFileName(xls.getFileName());

			         MimeMultipart mimeMultipart = new MimeMultipart();
			         mimeMultipart.addBodyPart(xlsBodyPart);
			         
			         message.setContent(mimeMultipart);
					 Transport.send(message);
					
				}
			}else{
				 BodyPart messageBodyPart = new MimeBodyPart();
		         messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
		         Multipart multipart = new MimeMultipart();
		         multipart.addBodyPart(messageBodyPart);
				
		         
		         
		         message.setContent(multipart);
				 Transport.send(message);
			}
			

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
   private Long storeEmailInfoInDB(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList) throws Exception {
		
		EmailNotification emailNotification = new EmailNotification();
		ArrayList<String> recipientList = new ArrayList<String>();
		for(EmailRecipient recipient : emailRecipientList){
			recipientList.add(recipient.getEmail());
		}
		emailNotification.setEmailTo(recipientList.toString());
		emailNotification.setEmailFrom(emailMessage.getFromEmail());
		emailNotification.setSubject(emailMessage.getSubject());
		emailNotification.setBody(emailMessage.getMessageBody());
		emailNotification.setDeliveryStatus("N");
	    Date date= new Date();
		emailNotification.setCreatedDate(new Timestamp(date.getTime()));
		emailNotificationService.save(emailNotification);
		
		return emailNotification.getId();
	}
   
   private void updateEmailInfoWithDeliveryStatus(Long emailNotificationd) throws Exception {
		EmailNotification emailNotification = emailNotificationService.getById(emailNotificationd);
		emailNotification.setId(emailNotificationd);
	     Date date= new Date();
		emailNotification.setSentTime(new Timestamp(date.getTime()));
		emailNotification.setDeliveryStatus("Y");
		
		emailNotificationService.save(emailNotification);
		
	}
	
}

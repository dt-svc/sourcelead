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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailNotification;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.rest.RegistrationRestController;
import com.bis.sourcelead.service.EmailMessageService;
import com.bis.sourcelead.service.EmailNotificationService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SubmissionXlsService;

/**
 * 
 * @author Prasad salina
 *
 */
@Service
public class EmailMessageServiceImpl implements EmailMessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailMessageServiceImpl.class);
	
	@Autowired
	EmailNotificationService emailNotificationService;
	
	@Autowired
	SubmissionXlsService  submissionXlsService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Value("${sendMailForNonProd}")
	private String sendMailForNonProd;
	
	@Value("${env}")
	private String environment;
	
	@Value("${defaultFromEmailAddress}")
	private String defaultFromEmailAddress;
	
	@Value("${defaultFromEmailAddressPwd}")
	private String defaultFromEmailAddressPwd;
	
	
	@Override
	public String sendEmail(EmailMessage emailMessage,Long locationsId, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList) throws Exception {
		if(emailMessage != null)
		{
			Long emailNotificationd = null;
			
			// if non prod and send email in non prod is true then call or prod
			if(environment.equalsIgnoreCase("prod"))
			{
				OrgLocations orgLocations = orgLocationService.getOrgLocations(emailMessage.getOrgLocationId());
				if(orgLocations != null && orgLocations.getFromEmail() !=null && orgLocations.getFromEmailPassword() != null){
					emailMessage.setUserName(orgLocations.getFromEmail());
					emailMessage.setPassword(orgLocations.getFromEmailPassword());
				}else{
					emailMessage.setUserName(defaultFromEmailAddress);
					emailMessage.setPassword(defaultFromEmailAddressPwd);
					logger.info("############## if production email/password not correct then email sent from default email address ###########");
				}
				emailNotificationd = storeEmailInfoInDB(emailMessage,emailRecipientList);
				sendMessage(emailMessage,emailRecipientList,attachmentList);
			}
			else
			{
				if(sendMailForNonProd.equalsIgnoreCase("true"))
				{
					emailMessage.setUserName(defaultFromEmailAddress);
					emailMessage.setPassword(defaultFromEmailAddressPwd);
					emailNotificationd = storeEmailInfoInDB(emailMessage,emailRecipientList);
					sendMessage(emailMessage,emailRecipientList,attachmentList);
				}
			}
			
			updateEmailInfoWithDeliveryStatus(emailNotificationd);
		}
		return "success";
	}
	
	private void sendMessage(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList)
	{
		
		Properties props = new Properties();
		final String username = emailMessage.getUserName();
		final String password = emailMessage.getPassword();
		
        props.put("mail.smtp.host", "secure.emailsrvr.com");
        props.put("mail.smtp.localhost", "localhost");
        props.put("mail.stmp.user", username);          
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
                return new PasswordAuthentication(username,password); 
            }
        });


		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username, emailMessage.getFromName()));
			
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
			         MimeMultipart mimeMultipart = new MimeMultipart();
			         
			         
			         BodyPart messageBodyPart = new MimeBodyPart();
			         messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
			         mimeMultipart.addBodyPart(messageBodyPart);
			         
			         MimeBodyPart xlsBodyPart = new MimeBodyPart();
			         xlsBodyPart.setDataHandler(new DataHandler(dataSource));
			         xlsBodyPart.setFileName(xls.getFileName());

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
		emailNotification.setEmailFrom(emailMessage.getUserName());
		emailNotification.setSubject(emailMessage.getSubject());
		emailNotification.setBody(emailMessage.getMessageBody());
		emailNotification.setDeliveryStatus("N");
	    Date date= new Date();
		emailNotification.setCreatedDate(new Timestamp(date.getTime()));
		emailNotification.setCreatedBy(emailMessage.getCreatedBy());
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

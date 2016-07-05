package com.bis.sourcelead.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.service.EmailMessageService;

/**
 * 
 * @author Prasad salina
 *
 */
@Service
public class EmailMessageServiceImpl implements EmailMessageService {
	
	@Override
	public String sendEmail(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList) {
		if(emailMessage != null)
		{
				sendMessage(emailMessage,emailRecipientList,attachmentList);
		}
		return "success";
	}
	
	private void sendMessage(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList, List<EmailAttachment> attachmentList)
	{
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("peopleaxisindia@gmail.com","Peopleaxis!123");
				}
			});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("peopleaxisindia@gmail.com", emailMessage.getFirstName()+" "+emailMessage.getLastName()));
			
            ArrayList<String> recipientList = new ArrayList<String>();
			
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
			
			
			
	         BodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
			
	         
	         
	        /* messageBodyPart = new MimeBodyPart();
	         String filename = "C:/Users/BIS94/Desktop/EmployeesServiceImpl.java";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("Java File");
	         multipart.addBodyPart(messageBodyPart);*/
	         
	         message.setContent(multipart);
			 Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

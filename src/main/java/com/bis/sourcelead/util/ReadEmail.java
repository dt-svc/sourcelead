package com.bis.sourcelead.util;

import java.io.File;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.springframework.beans.factory.annotation.Value;

import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;


public class ReadEmail {
	//public static Reader rd = new Reader();
	// readMail(host, mailStoreType, username, password);
	public void readEMailAttachments(Long id, EmailScenarioConfiguration confMail, File tempfolder) {
		String saveDirectory = tempfolder.getPath();
		System.out.println(" saveDirectory  path ="+saveDirectory);
		//String saveTo = "C:/sourcleadmails/parsedFiles/";
		try {
			Properties properties = new Properties();
			final String user = confMail.getEmailId();
			final String password = confMail.getEmailPassword();
			final String host = confMail.getServerName();
			final String portNumber = confMail.getPortNumber().toString();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", portNumber);
			properties.put("mail.pop3.starttls.enable", "true");
			System.out.println(" before properties");
			
			Session emailSession = Session.getDefaultInstance(properties);

			Store store = emailSession.getStore("pop3s");

			System.out.println("connect before pop3s ==============>");
			
		//	emailSession.setDebug(true); Enable true for debugg.
			store.connect(host, user, password);
			System.out.println("connection test successfull ==============>");
			Folder emailFolder = store.getFolder("INBOX"); // Reading inbox folder. for gmail need to enable access through the link. https://www.google.com/settings/security/lesssecureapps 
			//Folder emailFolder = store.getFolder("sourcelead"); // Reading inbox folder.
			 emailFolder.open(Folder.READ_WRITE);  // open the folder.
		      System.out.println("emailFolder.getMessageCount() ::::::::::::>  "+emailFolder.getMessageCount());
		      
		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		      
		      System.out.println("messages length   "+messages.length);
		      for (int i = 0, n = messages.length; i < n; i++) {
		    
		         Message message = messages[i];
		  
		         String contentType = message.getContentType();
		        // store attachment file name, separated by comma
		        if (contentType.contains("multipart")) {
					// content may contain attachments
		        	 
					Multipart multiPart = (Multipart) message.getContent();
					int numberOfParts = multiPart.getCount();
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
							// this part is attachment
							String fileName = part.getFileName();
							System.out.println(" saving file name :>>>>>>>>> :"+fileName);
							String fullfilepath = saveDirectory + System.getProperty("file.separator")+ fileName;
							part.saveFile(fullfilepath);
						}
					}
		         } 
		        //Delete mail
	            message.setFlag(Flags.Flag.DELETED, true);
         	}
		      
	      //close the store and folder objects
	      emailFolder.close(true);
	      store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	
}

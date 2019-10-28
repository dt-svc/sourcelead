package com.bis.sourcelead.util;

import java.io.File;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.EmailScenarioConfiguration;

@Component
public class ReadEmail {
	//public static Reader rd = new Reader();
	// readMail(host, mailStoreType, username, password);
	private static final Logger logger = LoggerFactory.getLogger(ReadEmail.class);
	public void readEMailAttachments(Long id, EmailScenarioConfiguration confMail, File tempfolder) {
		String saveDirectory = tempfolder.getPath();
		System.out.println(" saveDirectory  path ="+saveDirectory);
		logger.debug("***** saveDirectory  path *****"+saveDirectory);
		try {
			Properties properties = new Properties();
			final String user = confMail.getEmailId();
			final String password = new PasswordSecurity().decrypt(confMail.getEmailPassword());
			final String host = confMail.getServerName();
			final String portNumber = confMail.getPortNumber().toString();
			/*
			 * properties.put("mail.pop3.host", host); properties.put("mail.pop3.port",
			 * portNumber); properties.put("mail.pop3.starttls.enable", "true");
			 * //System.out.println(" before properties");
			 */ logger.debug("***** before connectiong mail id *****");

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", portNumber);
			properties.put("mail.imap.starttls.enable", "true");

			// create the IMAP store object and connect with the pop server

			// Session emailSession = Session.getDefaultInstance(properties);
			 Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			Store store = session.getStore("imaps");

			System.out.println("**********trying to connect IMAP server  ==============>");
			logger.debug("***** trying to connect IMAP server  ==============> *****");
			//emailSession.setDebug(true); // Enable true for debug.
			store.connect(host, user, password);
			System.out.println("**************connection test successfull ==============>");
			logger.debug("**************connection test successfull ==============>");
			Folder emailFolder = store.getFolder("INBOX"); // Reading inbox folder. for gmail need to enable access
			// For Gmail need to enable IMAP access for the Gmail account using below link
			//https://mail.google.com/mail/u/0/#settings/fwdandpop 
			emailFolder.open(Folder.READ_WRITE); // open the folder.
			System.out.println("emailFolder.getMessageCount() ::::::::::::>  " + emailFolder.getMessageCount());
			logger.debug("***** emailFolder.getMessageCount() ::::::::::::>  " + emailFolder.getMessageCount());

			int start = 1;
			int end = -1;

			Message[] messages = emailFolder.getMessages();

			System.out.println("messages length   " + messages.length);

			int count = emailFolder.getMessageCount();
			if (count == 0) { // No messages in the source folder
				System.out.println(emailFolder.getName() + " is empty");
				// Close folder, store and return
				emailFolder.close(false);
				store.close();
				return;
			}

			if (end == -1)
				end = count;
			// Get the message objects to copy
			Message[] msgs = emailFolder.getMessages(start, end);
			System.out.println("Moving " + msgs.length + " messages");
			logger.debug("Moving " + msgs.length + " messages");
			// Delete mail
			// Open destination folder, create if reqd
			Folder dfolder = store.getFolder("Processed"); // Creating Processed folder to in mail if not existed in mail box.
			if (!dfolder.exists()) {
				dfolder.create(Folder.HOLDS_MESSAGES);
				System.out.println("*****Processed Folder is created>>>>>>>>>>>>> ::  ");
				logger.debug("*****Processed Folder is created>>>>>>>>>>>>> ::  ");
			}
			if (msgs.length != 0) {
				System.out.println("*****Moving  messges to destination folder no Of messages found>>>>:  "+msgs.length);
				logger.debug("*****Moving  messges to destination folder no Of messages found>>>>:  "+msgs.length);
				emailFolder.copyMessages(msgs, dfolder); // moves the INBOX messages to destination folder.
				emailFolder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true); // setting deleted flag for all the MSGS.

				// Dump out the Flags of the moved messages, to insure that
				// all got deleted
				for (int i = 0; i < msgs.length; i++) {
					Message message = msgs[i];
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
								// Allowing only below extension files for parsing.
								if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".pdf")
										|| fileName.endsWith(".zip")) {
									System.out.println("*****saving file name :>>>>>>>>> :" + fileName);
									logger.debug("*****saving file name :>>>>>>>>> :" + fileName);
									String fullfilepath = saveDirectory + System.getProperty("file.separator") + fileName;
									logger.debug("*file fullfilepath saved at location :>>>>>>>>> :" + fullfilepath);
									part.saveFile(fullfilepath); // Save the file in folder.
								}

							}
						}
					}
					if (!msgs[i].isSet(Flags.Flag.DELETED))
						System.out.println("Message # " + msgs[i] + " not deleted");
						logger.debug("Message # " + msgs[i] + " not deleted");
				}
			}
			// close the store and folder objects
			emailFolder.close(true);
			store.close();
			System.out.println("*****email closed successfully************");
			logger.debug("*****email closed successfully************");
		} catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
}

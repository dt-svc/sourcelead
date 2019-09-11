package com.bis.sourcelead.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailNotification;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.entity.UserMailUnsubscribe;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.EmailMessageService;
import com.bis.sourcelead.service.EmailNotificationService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionXlsService;
import com.bis.sourcelead.service.UserMailUnsubscribeService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 *
 * <b>Purpose:</b><br>
 *
 * Email Message class. This class is used to send the emails from SourceLead.
 * 
 *
 * <b>CopyRights:</b><br>
 * Business Intelli Solutions India Pvt Ltd.- 2016<br>
 * 
 * @author: Prasad Salina
 * @version <br>
 *
 *          <b>RevisionHistory:</b>
 *
 *          <pre>
*
* <b>
* Sl No   Modified Date   Author</b>
* ==============================================
* 1.    28-Jun-2016      Prasad Salina
*      -- Base Release
* 2.	01-Dec-2016      Srinivas Vemula
*      -- Converted the senEmail method to async call, 
*      -- Changed the return type to void, as we don't need the return type, and return type is not used anywhere in the application. 
*      -- Corrected the
 *          </pre>
 */
@Service
public class EmailMessageServiceImpl implements EmailMessageService {

	private static final Logger logger = LoggerFactory.getLogger(EmailMessageServiceImpl.class);

	@Autowired
	EmailNotificationService emailNotificationService;

	@Autowired
	SubmissionXlsService submissionXlsService;

	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserMailUnsubscribeService userMailUnsubscribeService;

	@Autowired
	@Lazy
	private CandidateResumesService candidateResumesService;

	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioConfigurationService;

	@Value("${sendMailForNonProd}")
	private String sendMailForNonProd;

	@Value("${env}")
	private String environment;
	
	@Value("${static.path}")
	private String staticPath;

	@Value("${defaultFromEmailAddress}")
	private String defaultFromEmailAddress;

	@Value("${defaultFromEmailAddressPwd}")
	private String defaultFromEmailAddressPwd;

	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;

	@Value("${defaultFromEmailAddressPwdForProd}")
	private String defaultFromEmailAddressPwdForProd;

	@Override
	@Async
	public void sendEmail(EmailMessage emailMessage, Long locationsId, InternetAddress[] iAdressArray, List<EmailAttachment> attachmentList) throws Exception
	{
		if (emailMessage != null) {
			String tabDivGroup = "";
			// the pattern we want to search for
			Pattern p = Pattern.compile("<tabIdDiv>(\\S+)</tabIdDiv>");
			Matcher m = p.matcher(emailMessage.getMessageBody());
			// if we find a match, get the group
			if (m.find()) {
				// get the matching group
				tabDivGroup = m.group(1);
			}
			
			for (InternetAddress toAddressEmail : iAdressArray) {
				UserMailUnsubscribe unsubscriptionUser = userMailUnsubscribeService.getUserMailUnsubscriptionByUserEmail(toAddressEmail.getAddress());
				if (unsubscriptionUser != null && StringUtils.isNotEmpty(unsubscriptionUser.getTabIds())) {
					String[] tabIdsArray = unsubscriptionUser.getTabIds().split(",");
					if (StringUtils.isNotEmpty(tabDivGroup)) {
						if(ArrayUtils.contains( tabIdsArray, tabDivGroup)) {
							iAdressArray = (InternetAddress[]) ArrayUtils.removeElement(iAdressArray, toAddressEmail);
						}else if(ArrayUtils.contains( tabIdsArray, "0") && !ArrayUtils.contains( Constants.UNSUBSCRIPTION_TABID_LISTED, tabDivGroup)) {
							iAdressArray = (InternetAddress[]) ArrayUtils.removeElement(iAdressArray, toAddressEmail);
						}
					}else if(ArrayUtils.contains( tabIdsArray, "0")) {
						iAdressArray = (InternetAddress[]) ArrayUtils.removeElement(iAdressArray, toAddressEmail);
					}
				}
			}
			sendEmailCommon(emailMessage, locationsId, iAdressArray, attachmentList);
		}
	}
	
	
	private void sendEmailCommon(EmailMessage emailMessage, Long locationsId, InternetAddress[] iAdressArray, List<EmailAttachment> attachmentList) throws Exception
	{
		Long emailNotificationId = null;
		// if non prod and send email in non prod is true then call or prod
		if (environment.equalsIgnoreCase("prod")) {
			OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
			
			if (orgLocations != null && orgLocations.getFromEmail() != null
					&& orgLocations.getFromEmailPassword() != null) {
				emailMessage.setUserName(defaultFromEmailAddressForProd);
				emailMessage.setPassword(defaultFromEmailAddressPwdForProd);
			}
			else {
				emailMessage.setUserName(defaultFromEmailAddress);
				emailMessage.setPassword(defaultFromEmailAddressPwd);
				logger.info("######## if production email/password not correct then email sent from default email address ###########");
			}
			emailNotificationId = storeEmailInfoInDBTwo(emailMessage, iAdressArray,orgLocations);
			//sendMessage(emailMessage, iAdressArray, attachmentList);
			sendMessageConfiguredMail(emailMessage, iAdressArray, attachmentList,locationsId);
		}
		else {
			if (sendMailForNonProd.equalsIgnoreCase("true")) {
				OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
				emailMessage.setUserName(defaultFromEmailAddress);
				emailMessage.setPassword(defaultFromEmailAddressPwd);
				emailNotificationId = storeEmailInfoInDBTwo(emailMessage, iAdressArray,orgLocations);
				//sendMessage(emailMessage, iAdressArray, attachmentList);
				sendMessageConfiguredMail(emailMessage, iAdressArray, attachmentList,locationsId);
			}
		}
		
		if(emailNotificationId != null) {
			updateEmailInfoWithDeliveryStatus(emailNotificationId);
		}
		
	}
	
	private void sendMessage(EmailMessage emailMessage, InternetAddress[] iAdressArray,
			List<EmailAttachment> attachmentList) throws Exception {

		Properties props = new Properties();
		final String username = emailMessage.getUserName(); // should be from email of the System. SRINIVAS SABBU 
		final String password = emailMessage.getPassword();// should be password of from email of the System.

		if (environment.equalsIgnoreCase("prod")) {
			props.put("mail.smtp.host", "secure.emailsrvr.com");
		} else {
			props.put("mail.smtp.host", "smtp.sendgrid.net");
		}
		
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
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
//			session.setDebug(true);
			String UUID = null;
			if (null != emailMessage.getEmailType()
					&& emailMessage.getEmailType().equalsIgnoreCase(Constants.EMAIL_CAMPAIGN)) {
				message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
				message.setRecipients(Message.RecipientType.BCC, iAdressArray);
			}
			// here mail forward from custom user mail(not with default mail) by
			// prasad salina
			else if (null != emailMessage.getEmailType()
					&& emailMessage.getEmailType().equalsIgnoreCase(Constants.CUSTOM_USER_EMAIL)) {
				message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
				message.setRecipients(Message.RecipientType.TO, iAdressArray);
			} else {
				if (null != emailMessage.getUserType() && (emailMessage.getUserType().equalsIgnoreCase("Professional")
						|| emailMessage.getUserType().equalsIgnoreCase("Recruiter"))) {
					message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromEmail()));
				} else {
					message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
				}

				message.setRecipients(Message.RecipientType.TO, iAdressArray);
			}

			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			message.setSubject(emailMessage.getSubject());
			MimeMultipart mimeMultipart = new MimeMultipart();;
			if(emailMessage.getSubmissionId()!=null&& emailMessage.getSubmissionId().length>0){
				if (emailMessage != null && Constants.YES.equalsIgnoreCase(emailMessage.getSendResumes())) {
					if (emailMessage.getSubmissionId().length >= 10) {
						mimeMultipart = createResumeZipFile(emailMessage.getSubmissionId());
					} else {
						List<EmailAttachment> attachmentListNewlyAdded = createResumesWithoutZip(emailMessage.getSubmissionId());
						attachmentList.addAll(attachmentListNewlyAdded);
					}
				}
			}
			BodyPart messageBodyPart = new MimeBodyPart();
			
			if (null != attachmentList && attachmentList.size() > 0) {
				messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
				mimeMultipart.addBodyPart(messageBodyPart);
				for (EmailAttachment attachment : attachmentList) {

					DataSource dataSource = null;
					if (null != attachment.getUUID()) {
						UUID = attachment.getUUID();
					}

					SubmissionXls xls = submissionXlsService.getSubmissionXlsByUUID(UUID);

					if (null != xls) {
						dataSource = new ByteArrayDataSource(xls.getDocFile(), "application/pdf");
						MimeBodyPart xlsBodyPart = new MimeBodyPart();
						xlsBodyPart.setDataHandler(new DataHandler(dataSource));
						xlsBodyPart.setFileName(xls.getFileName());
						mimeMultipart.addBodyPart(xlsBodyPart);
					} else {
						dataSource = new ByteArrayDataSource(attachment.getDocFile(), "application/pdf");
						MimeBodyPart bodyPart = new MimeBodyPart();
						bodyPart.setDataHandler(new DataHandler(dataSource));
						bodyPart.setFileName(attachment.getName());
						mimeMultipart.addBodyPart(bodyPart);
					}
					message.setContent(mimeMultipart);
				}
			} else {
				messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
				mimeMultipart.addBodyPart(messageBodyPart);
				message.setContent(mimeMultipart);
			}

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendMessageConfiguredMail(EmailMessage emailMessage, InternetAddress[] iAdressArray,
			List<EmailAttachment> attachmentList,Long orgLocations) throws Exception {
		
		Properties props = new Properties();
		Session session=null;
		
		EmailScenarioConfiguration configuredMail=emailScenarioConfigurationService.getVerifiedConfMail(orgLocations);
		if(configuredMail != null){
			

			final String username = configuredMail.getEmailId();
			final String password = configuredMail.getEmailPassword();
			
			String serverName=configuredMail.getServerName();
			String portNumber=configuredMail.getPortNumber().toString();

			props.put("mail.smtp.host", serverName);
			props.put("mail.smtp.localhost", "localhost");
			props.put("mail.stmp.user", username);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.password", "password");
			props.put("mail.smtp.socketFactory.port", portNumber);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", portNumber);
			
			 session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		

			try {

				MimeMessage message = new MimeMessage(session);

				String UUID = null;
				if (null != emailMessage.getEmailType()
						&& emailMessage.getEmailType().equalsIgnoreCase(Constants.EMAIL_CAMPAIGN)) {
					message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
					message.setRecipients(Message.RecipientType.BCC, iAdressArray);
				}
				// here mail forward from custom user mail(not with default mail) by
				// prasad salina
				else if (null != emailMessage.getEmailType()
						&& emailMessage.getEmailType().equalsIgnoreCase(Constants.CUSTOM_USER_EMAIL)) {
					message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
					message.setRecipients(Message.RecipientType.TO, iAdressArray);
				} else {
					if (null != emailMessage.getUserType() && (emailMessage.getUserType().equalsIgnoreCase("Professional")
							|| emailMessage.getUserType().equalsIgnoreCase("Recruiter"))) {
						message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromEmail()));
					} else {
						message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
					}

					message.setRecipients(Message.RecipientType.TO, iAdressArray);
				}

				message.setHeader("Content-Type", "text/html; charset=UTF-8");
				message.setSubject(emailMessage.getSubject());
				MimeMultipart mimeMultipart = new MimeMultipart();;
				if(emailMessage.getSubmissionId()!=null&& emailMessage.getSubmissionId().length>0){
					if (emailMessage != null && Constants.YES.equalsIgnoreCase(emailMessage.getSendResumes())) {
						if (emailMessage.getSubmissionId().length >= 10) {
							mimeMultipart = createResumeZipFile(emailMessage.getSubmissionId());
						} else {
							List<EmailAttachment> attachmentListNewlyAdded = createResumesWithoutZip(emailMessage.getSubmissionId());
							attachmentList.addAll(attachmentListNewlyAdded);
						}
					}
				}
				BodyPart messageBodyPart = new MimeBodyPart();
				
				if (null != attachmentList && attachmentList.size() > 0) {
					messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
					mimeMultipart.addBodyPart(messageBodyPart);
					for (EmailAttachment attachment : attachmentList) {

						DataSource dataSource = null;
						if (null != attachment.getUUID()) {
							UUID = attachment.getUUID();
						}

						SubmissionXls xls = submissionXlsService.getSubmissionXlsByUUID(UUID);

						if (null != xls) {
							dataSource = new ByteArrayDataSource(xls.getDocFile(), "application/pdf");
							MimeBodyPart xlsBodyPart = new MimeBodyPart();
							xlsBodyPart.setDataHandler(new DataHandler(dataSource));
							xlsBodyPart.setFileName(xls.getFileName());
							mimeMultipart.addBodyPart(xlsBodyPart);
						} else {
							dataSource = new ByteArrayDataSource(attachment.getDocFile(), "application/pdf");
							MimeBodyPart bodyPart = new MimeBodyPart();
							bodyPart.setDataHandler(new DataHandler(dataSource));
							bodyPart.setFileName(attachment.getName());
							mimeMultipart.addBodyPart(bodyPart);
						}
						message.setContent(mimeMultipart);
					}
				} else {
					messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
					mimeMultipart.addBodyPart(messageBodyPart);
					message.setContent(mimeMultipart);
				}

				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			sendMessage(emailMessage,iAdressArray,attachmentList);
		}

	}
	
	private Long storeEmailInfoInDB(EmailMessage emailMessage, InternetAddress[] iAdressArray) throws Exception {

		EmailNotification emailNotification = new EmailNotification();
		if (iAdressArray != null && iAdressArray.length > 0) {
			StringBuilder recipients = new StringBuilder("");
			recipients.append("[");
			int count = 1;
			for (InternetAddress address : iAdressArray) {
				if (count > 1) {
					recipients.append(",");
				}
				count++;
				recipients.append(address.getAddress());
			}
			recipients.append("]");
			emailNotification.setEmailTo(recipients.toString());
		}
		String url=null;
		if(emailMessage != null && emailMessage.getMessageBody() != null ){
			String title="email"+SourceLeadRandomCodeHelper.generateRandomOrgCode();
			File f=new File(staticPath,"/"+"/"+"profile"+"/"+"test.html");
			url=staticPath+"test.html";
			try{
				BufferedWriter bw=new BufferedWriter(new FileWriter(f));
				bw.write(emailMessage.getMessageBody());
				bw.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		

		emailNotification.setEmailFrom(emailMessage.getFromEmail());
		emailNotification.setSubject(emailMessage.getSubject());
		emailNotification.setBody(url);
		emailNotification.setDeliveryStatus("N");
		Date date = new Date();
		emailNotification.setCreatedDate(new Timestamp(date.getTime()));
		emailNotification.setCreatedBy(emailMessage.getCreatedBy());
		emailNotificationService.save(emailNotification);

		return emailNotification.getId();
	}
	
	private Long storeEmailInfoInDBTwo(EmailMessage emailMessage, InternetAddress[] iAdressArray,OrgLocations locaId) throws Exception {

		EmailNotification emailNotification = new EmailNotification();
		if (iAdressArray != null && iAdressArray.length > 0) {
			StringBuilder recipients = new StringBuilder("");
			recipients.append("[");
			int count = 1;
			for (InternetAddress address : iAdressArray) {
				if (count > 1) {
					recipients.append(",");
				}
				count++;
				recipients.append(address.getAddress());
			}
			recipients.append("]");
			emailNotification.setEmailTo(recipients.toString());
		}
		String link=null;
		if(emailMessage != null && emailMessage.getMessageBody() != null ){
			String title=emailMessage.getFirstName()+SourceLeadRandomCodeHelper.generateRandomOrgCode();
			File folder = new File(staticPath+"/"+locaId.getOrg().getId()+"/"+locaId.getId()+"/emailBody/");
			if (!folder.exists())
				folder.mkdirs();
			File file=new File(folder,title+".html");
			link = "/slead-static/"+locaId.getOrg().getId()+"/"+locaId.getId()+"/emailBody/"+file.getName();
			try{
				BufferedWriter bw=new BufferedWriter(new FileWriter(file));
				bw.write(emailMessage.getMessageBody());
				bw.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		

		emailNotification.setEmailFrom(emailMessage.getFromEmail());
		emailNotification.setSubject(emailMessage.getSubject());
		emailNotification.setBody(link);
		emailNotification.setDeliveryStatus("N");
		Date date = new Date();
		emailNotification.setCreatedDate(new Timestamp(date.getTime()));
		emailNotification.setCreatedBy(emailMessage.getCreatedBy());
		emailNotificationService.save(emailNotification);

		return emailNotification.getId();
	}

	private void updateEmailInfoWithDeliveryStatus(Long emailNotificationd) throws Exception {
		EmailNotification emailNotification = emailNotificationService.getById(emailNotificationd);
		emailNotification.setId(emailNotificationd);
		Date date = new Date();
		emailNotification.setSentTime(new Timestamp(date.getTime()));
		emailNotification.setDeliveryStatus("Y");

		emailNotificationService.save(emailNotification);

	}

	@Override
	@Async
	public void sendEmailWithAttachment(EmailMessage emailMessage, Long locationsId, InternetAddress[] iAdressArray,
			List<EmailAttachment> attachmentList, String[] submissionIds) throws Exception {

		if (emailMessage != null) {
			Long emailNotificationId = null;

			// if non prod and send email in non prod is true then call or prod
			if (environment.equalsIgnoreCase("prod")) {
				OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
				if (orgLocations != null && orgLocations.getFromEmail() != null
						&& orgLocations.getFromEmailPassword() != null) {
					emailMessage.setUserName(defaultFromEmailAddressForProd);
					emailMessage.setPassword(defaultFromEmailAddressPwdForProd);
				} else {
					emailMessage.setUserName(defaultFromEmailAddress);
					emailMessage.setPassword(defaultFromEmailAddressPwd);
					logger.info(
							"######## if production email/password not correct then email sent from default email address ###########");
				}
				emailNotificationId = storeEmailInfoInDBTwo(emailMessage, iAdressArray,orgLocations);
				sendMessageAttachment(emailMessage, iAdressArray, attachmentList, submissionIds);
			} else {
				if (sendMailForNonProd.equalsIgnoreCase("true")) {
					OrgLocations orgLocations = orgLocationService.getOrgLocations(locationsId);
					emailMessage.setUserName(defaultFromEmailAddress);
					emailMessage.setPassword(defaultFromEmailAddressPwd);
					emailNotificationId = storeEmailInfoInDBTwo(emailMessage, iAdressArray,orgLocations);
					sendMessageAttachment(emailMessage, iAdressArray, attachmentList, submissionIds);
				}
			}

			if(emailNotificationId != null) {
				updateEmailInfoWithDeliveryStatus(emailNotificationId);
			}
			
		}

	}

	/**
	 * Send emails with attachment
	 * 
	 * @param emailMessage
	 * @param emailRecipientList
	 * @param attachmentList
	 * @param submissionIds
	 * @throws Exception
	 */
	private void sendMessageAttachment(EmailMessage emailMessage, InternetAddress[] iAdressArray,
			List<EmailAttachment> attachmentList, String[] submissionIds) throws Exception {

		Properties props = new Properties();
		final String username = emailMessage.getUserName();
		final String password = emailMessage.getPassword();
		if (environment.equalsIgnoreCase("prod")) {
			props.put("mail.smtp.host", "secure.emailsrvr.com");
		} else {
			props.put("mail.smtp.host", "smtp.sendgrid.net");
		}
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
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
			String UUID = null;
			if (null != emailMessage.getEmailType()
					&& emailMessage.getEmailType().equalsIgnoreCase(Constants.EMAIL_CAMPAIGN)) {
				message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
				message.setRecipients(Message.RecipientType.BCC, iAdressArray);
			}
			// here mail forward from custom user mail(not with default mail) by
			// prasad salina
			else if (null != emailMessage.getEmailType()
					&& emailMessage.getEmailType().equalsIgnoreCase(Constants.CUSTOM_USER_EMAIL)) {
				message.setFrom(new InternetAddress(emailMessage.getFromEmail(), emailMessage.getFromName()));
				message.setRecipients(Message.RecipientType.TO, iAdressArray);
			} else {
				if (null != emailMessage.getUserType() && (emailMessage.getUserType().equalsIgnoreCase("Professional")
						|| emailMessage.getUserType().equalsIgnoreCase("Recruiter"))) {
					message.setFrom(new InternetAddress(username, emailMessage.getFromEmail()));
				} else {
					message.setFrom(new InternetAddress(username, emailMessage.getFromName()));
				}

				message.setRecipients(Message.RecipientType.TO, iAdressArray);
			}
			//message.setFrom(new InternetAddress(username, emailMessage.getFromName()));

			//String UUID = null;
			message.addRecipients(Message.RecipientType.TO, iAdressArray);
			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			message.setSubject(emailMessage.getSubject());
			MimeMultipart mimeMultipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();

			// create resumes zip.
			if (emailMessage != null && Constants.YES.equalsIgnoreCase(emailMessage.getSendResumes())) {
				if (submissionIds.length >= 10) {
					mimeMultipart = createResumeZipFile(submissionIds);
				} else {
					List<EmailAttachment> attachmentListNewlyAdded = createResumesWithoutZip(submissionIds);
					attachmentList.addAll(attachmentListNewlyAdded);
				}
			}

			if (attachmentList.size() > 0) {
				messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
				mimeMultipart.addBodyPart(messageBodyPart);

				for (EmailAttachment attachment : attachmentList) {
					UUID = attachment.getName();
					SubmissionXls xls = submissionXlsService.getSubmissionXlsByUUID(UUID);
					DataSource dataSource = new ByteArrayDataSource(attachment.getDocFile(), "application/pdf");
					MimeBodyPart xlsBodyPart = new MimeBodyPart();
					xlsBodyPart.setDataHandler(new DataHandler(dataSource));
					xlsBodyPart.setFileName(attachment.getName());
					mimeMultipart.addBodyPart(xlsBodyPart);
					message.setContent(mimeMultipart);

				}
			} else {
				messageBodyPart.setContent(emailMessage.getMessageBody(), "text/html");
				mimeMultipart.addBodyPart(messageBodyPart);
				message.setContent(mimeMultipart);
			}
			// TODO remove this statement once the email issues is resolved
			logger.info("################# Email: " + username + " Password: " + password);
			logger.info("################# from Email: " + message.getFrom());
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private List<EmailAttachment> createResumesWithoutZip(String[] submissionIds) throws Exception {
		List<EmailAttachment> emailAttachmentList = new ArrayList<>();
		if (submissionIds != null && !"".equals(submissionIds) && submissionIds.length > 0) {
			List<Long> tempSubmissionIds = new ArrayList<>();
			for (int i = 0; i < submissionIds.length; i++) {
				tempSubmissionIds.add(Long.valueOf(submissionIds[i]));
			}
			List<Long> submissionResumeIds = requirementSubmissionService
					.getResumeIdsByRequirementSubmissionId(tempSubmissionIds);
			if (submissionResumeIds != null && !submissionResumeIds.isEmpty()) {
				try {
					List<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesByIds(submissionResumeIds);
					if (candidateResumesList != null && !candidateResumesList.isEmpty()) {
						for (CandidateResumes wte : candidateResumesList) {
							File file = new File(wte.getFileURL());
							if(file.exists()) {
								EmailAttachment emailAttachment = new EmailAttachment();
								byte[] bytesArray = getByteArray(file);
								emailAttachment.setDocFile(bytesArray);
								emailAttachment.setName(wte.getFileName());
								emailAttachment.setType(wte.getFileContentType());
								emailAttachmentList.add(emailAttachment);	
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return emailAttachmentList;
	}

	/**
	 * Get the bytes from file
	 * @param file
	 * @return byte
	 * @throws IOException
	 */
	private byte[] getByteArray(File file) throws IOException {
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();

	}

	private MimeMultipart createResumeZipFile(String[] submissionIds) throws Exception {
		MimeMultipart mimeMultipart = new MimeMultipart();
		if (submissionIds != null && !"".equals(submissionIds) && submissionIds.length > 0) {
			List<Long> tempSubmissionIds = new ArrayList<>();
			for (int i = 0; i < submissionIds.length; i++) {
				tempSubmissionIds.add(Long.valueOf(submissionIds[i]));
			}
			List<Long> submissionResumeIds = requirementSubmissionService
					.getResumeIdsByRequirementSubmissionId(tempSubmissionIds);
			if (submissionResumeIds != null && !submissionResumeIds.isEmpty()) {
				try {
					List<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesByIds(submissionResumeIds);
					if (candidateResumesList != null && !candidateResumesList.isEmpty()) {
						ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
						ZipOutputStream outZip = new ZipOutputStream(zipBytes);

						int i = 0;
						for (CandidateResumes wte : candidateResumesList) {
							File file = new File(wte.getFileURL());
							if(file.exists()) {
								StringBuilder sb = new StringBuilder();
								String st = wte.getFileName();
								String lastIndex = st.substring(0, st.lastIndexOf("."));
								String newFilename = (sb.append(lastIndex).append("-").append(i).append(st.substring(st.lastIndexOf("."))).toString());
								byte[] docFile = getByteArray(file);
								addToZipFile(docFile, outZip, newFilename);
								i++;
							}
							
						}

						outZip.closeEntry();
						outZip.close();
						DataSource source = new ByteArrayDataSource(zipBytes.toByteArray(), "application/zip");
						MimeBodyPart messageBodyPart1 = new MimeBodyPart();
						messageBodyPart1.setDataHandler(new DataHandler(source));
						DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
						StringBuilder fName = new StringBuilder();
						String fileName = fName.append("resumes")
								.append(SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()).append(" ")
								.append(dateFormat.format(new Date())).append(".zip").toString();
						messageBodyPart1.setFileName(fileName);
						mimeMultipart.addBodyPart(messageBodyPart1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return mimeMultipart;

	}

	public static void addToZipFile(byte[] by, ZipOutputStream zos, String fileName)
			throws FileNotFoundException, IOException {
		byte[] content = by;
		if (content != null) {
			int size = content.length;

			ByteArrayInputStream ins = null;
			ins = new ByteArrayInputStream(content);

			ZipEntry zipEntry = new ZipEntry(fileName);
			zos.putNextEntry(zipEntry);

			final int BUFFER = 4096;
			byte[] bytes = new byte[2048];
			byte data[] = new byte[BUFFER];

			int length = 0;
			while ((length = ins.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}

		}
		zos.closeEntry();
	}

}

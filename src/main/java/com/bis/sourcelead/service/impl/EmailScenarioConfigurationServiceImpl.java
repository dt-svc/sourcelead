package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailScenarioConfigurationDao;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;

@Service
public class EmailScenarioConfigurationServiceImpl implements EmailScenarioConfigurationService {
	
private static final Logger logger = LoggerFactory.getLogger(EmailScenarioConfigurationServiceImpl.class);
	
	@Autowired
	private EmailScenarioConfigurationDao emailScenarioConfigurationDao;
	
	@Value("${defaultFromEmailAddress}")
	private String defaultFromEmailAddress;

	@Override
	public EmailScenarioConfiguration save(EmailScenarioConfiguration configuration) throws Exception {
		return emailScenarioConfigurationDao.save(configuration);
	}

	@Override
	public EmailScenarioConfiguration getEmailscenarioConByOrgLocId(String email, Long orgLocId) throws Exception {
		return emailScenarioConfigurationDao.getEmailscenarioConByOrgLocId(email, orgLocId);
	}

	@Override
	public EmailScenarioConfiguration getEmailConfigOrgLocId(Long orgLocId) throws Exception {
		return emailScenarioConfigurationDao.getEmailConfigOrgLocId(orgLocId);
	}

	@Override
	public EmailConfigurationFromMail saveFromMail(EmailConfigurationFromMail fromMail) throws Exception {
		return emailScenarioConfigurationDao.saveFromMail(fromMail);
	}

	@Override
	public List<EmailConfigurationFromMail> getConfFromMailList(Long orgLocId) throws Exception {
		return emailScenarioConfigurationDao.getConfFromMailList(orgLocId);
	}

	@Override
	public EmailConfigurationFromMail deleteFromMail(Long emailId,Long orgLocId) throws Exception {
		EmailConfigurationFromMail fromMail=emailScenarioConfigurationDao.getFromMailById(emailId);
		EmailConfigurationFromMail deleteMail=emailScenarioConfigurationDao.deleteFromMail(fromMail);
		return deleteMail;
	}

	@Override
	public EmailConfigurationFromMail getFromMailByUUID(String UUID) throws Exception {
		return emailScenarioConfigurationDao.getFromMailByUUID(UUID);
	}

	@Override
	public String verifyConfgFromMail(Long id,EmailScenarioConfiguration confMail) throws Exception {
		if(confMail != null){
			Properties props = new Properties();
			final String username = confMail.getEmailId();
			final String password = confMail.getEmailPassword();
			
			final String serverName = confMail.getServerName();
			final String portNumber = confMail.getPortNumber().toString();

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

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			
			 
			 try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(defaultFromEmailAddress));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(confMail.getEmailId()));
					message.setSubject("Test Mail");
					message.setText("Dear Recipient," + "\n\n Your Mail is verified successfully");

					//Transport.send(message);
					Transport transport = session.getTransport("smtp");
				      transport.connect(serverName, username, password);
				      transport.sendMessage(message, message.getAllRecipients());
				      transport.close();

				} catch (MessagingException e) {
					//throw new RuntimeException(e);
					return "failure";
				}
			 
			 
		}
		return "success";
	}

	@Override
	public EmailConfigurationFromMail getFromMailById(Long id) throws Exception {
		return emailScenarioConfigurationDao.getFromMailById(id);
	}

	@Override
	public int changeDefaultEmailFromMail(long id, long orgId, boolean defaultAccount) {
		return emailScenarioConfigurationDao.changeDefaultEmailFromMail(id, orgId, defaultAccount);
	}

	@Override
	public EmailConfigurationFromMail getConfFromMailByStatus(Long orgLocId) throws Exception {
		return emailScenarioConfigurationDao.getConfFromMailByStatus(orgLocId);
	}

	@Override
	public EmailScenarioConfiguration getVerifiedConfMail(Long orgLocId) throws Exception {
		// TODO Auto-generated method stub
		return emailScenarioConfigurationDao.getVerifiedConfMail(orgLocId);
	}

	

}

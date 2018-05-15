package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailScenarioConfigurationDao;
import com.bis.sourcelead.dao.EmailScenarioDao;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;

@Service
public class EmailScenarioConfigurationServiceImpl implements EmailScenarioConfigurationService {
	
private static final Logger logger = LoggerFactory.getLogger(EmailScenarioConfigurationServiceImpl.class);
	
	@Autowired
	private EmailScenarioConfigurationDao emailScenarioConfigurationDao;

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

	

}

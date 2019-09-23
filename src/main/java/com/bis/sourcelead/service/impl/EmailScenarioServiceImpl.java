package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmailScenarioDao;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.service.EmailScenarioService;
/**
 * 
 * @author Prasad salina
 *
 */
@Service
public class EmailScenarioServiceImpl implements EmailScenarioService {
	
private static final Logger logger = LoggerFactory.getLogger(EmailScenarioServiceImpl.class);
	
	@Autowired
	private EmailScenarioDao emailScenarioDao;

	@Override
	public EmailScenario getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(String uniqueScenarioName,String entityType,
			String orgCode, String locationsCode) throws Exception{
		logger.debug("In EmailScenarioServiceImpl class getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode method");
		EmailScenario emailScenario = emailScenarioDao.getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(uniqueScenarioName, entityType, orgCode, locationsCode);
		return emailScenario;
	}

	@Override
	public EmailScenario save(EmailScenario scenario) throws Exception {
		logger.debug("In EmailScenarioServiceImpl class save method");
		return emailScenarioDao.save(scenario);
	}

	@Override
	public EmailScenario getEmailScenarioByUniqueName(String uniqueScenarioName, String entityType) throws Exception {
		logger.debug("In EmailScenarioServiceImpl class getEmailScenarioByUniqueName method");
		return emailScenarioDao.getEmailScenarioByUniqueName(uniqueScenarioName, entityType);
	}

	
	@Override
	public EmailScenario getEmailTemplateById(Long id) {
		return emailScenarioDao.getEmailTemplateById(id);
	}

	@Override
	public EmailScenario getEmailDefTemplateNo(String uniqueScenarioName, String defaultTemp) throws Exception {
		return emailScenarioDao.getEmailDefTemplateNo(uniqueScenarioName, defaultTemp);
	}

	@Override
	public List<EmailScenario> getEmailScenarioList(String templateType,List<Long> tabIds, List<String> specification) throws Exception {
		
		return emailScenarioDao.getEmailScenarioList(templateType,tabIds,specification);
	}

	@Override
	public EmailScenario getEmailScenarioByUniqueNameAndOrgLocationId(String uniqueScenarioName, String entityType,
			Long orgLocationId) throws Exception {
		return emailScenarioDao.getEmailScenarioByUniqueNameAndOrgLocationId(uniqueScenarioName, entityType, orgLocationId);
	}

	@Override
	public List<EmailScenario> getEmailScenarioListByOrgLocId(Long orgLocId) throws Exception {
		return emailScenarioDao.getEmailScenarioListByOrgLocId(orgLocId);
	}
	@Override
	public EmailScenario getEmailTemplateByUUID(String templateUUID) {
		return emailScenarioDao.getEmailTemplateByUUID(templateUUID);
	}

}

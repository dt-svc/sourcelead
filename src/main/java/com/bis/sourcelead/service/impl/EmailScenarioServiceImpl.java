package com.bis.sourcelead.service.impl;

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
	public EmailScenario getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(String uniqueScenarioName,
			String orgCode, String locationsCode) throws Exception{
		EmailScenario emailScenario = emailScenarioDao.getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(uniqueScenarioName, orgCode, locationsCode);
		return emailScenario;
	}

	@Override
	public EmailScenario save(EmailScenario scenario) throws Exception {
		return emailScenarioDao.save(scenario);
	}

}

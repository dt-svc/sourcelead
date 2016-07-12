package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.EmailScenario;

/**
 * 
 * @author Prasad salina
 *
 */
public interface EmailScenarioService {
	
	EmailScenario getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(String uniqueScenarioName,String entityType, String orgCode, String locationCode) throws Exception;
	
	EmailScenario save(EmailScenario scenario) throws Exception;
	
	EmailScenario getEmailScenarioByUniqueName(String uniqueScenarioName, String entityType) throws Exception;

}
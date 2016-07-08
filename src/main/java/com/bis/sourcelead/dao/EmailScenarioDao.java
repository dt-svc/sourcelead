package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.EmailScenario;

public interface EmailScenarioDao {
	
	EmailScenario getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(String uniqueScenarioName, String entityType, String orgCode, String locationsCode) throws Exception;
 
	EmailScenario save(EmailScenario scenario) throws Exception;

	EmailScenario getEmailScenarioByUniqueName(String uniqueScenarioName, String entityType) throws Exception;
}

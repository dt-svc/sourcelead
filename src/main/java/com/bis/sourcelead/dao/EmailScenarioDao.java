package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.EmailScenario;

public interface EmailScenarioDao {
	
	EmailScenario getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(String uniqueScenarioName, String orgCode, String locationsCode) throws Exception;
 
	EmailScenario save(EmailScenario scenario) throws Exception;
}

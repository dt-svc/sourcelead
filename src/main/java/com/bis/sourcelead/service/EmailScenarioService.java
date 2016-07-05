package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.EmailScenario;

/**
 * 
 * @author Prasad salina
 *
 */
public interface EmailScenarioService {
	
	EmailScenario getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(String uniqueScenarioName, String orgCode, String locationCode) throws Exception;

}
package com.bis.sourcelead.service;

import java.util.List;

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
	
	List<EmailScenario> getEmailScenarioList();
	
	List<EmailScenario> getEmailScenarioList(String sourceLead) throws Exception;
	
	EmailScenario getEmailTemplateById(Long id);
	
	EmailScenario getEmailDefTemplateNo(String uniqueScenarioName, String defaultTemp) throws Exception;
	
	EmailScenario getEmailScenarioByUniqueNameAndOrgLocationId(String uniqueScenarioName, String entityType,Long orgLocationId) throws Exception;

}